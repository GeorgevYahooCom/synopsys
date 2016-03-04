package org.georgev.calc.parser;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import org.georgev.calc.domain.Addition;
import org.georgev.calc.domain.Division;
import org.georgev.calc.domain.Expression;
import org.georgev.calc.domain.Let;
import org.georgev.calc.domain.Literal;
import org.georgev.calc.domain.Multiplication;
import org.georgev.calc.domain.Operation;
import org.georgev.calc.domain.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionParser {
	
	private static Logger log = LoggerFactory.getLogger(ExpressionParser.class);
	
	private static HashMap<String, TOKEN_TYPE> tokens = new HashMap<String, TOKEN_TYPE>();
	
	private enum TOKEN_TYPE {
		ADD, MULT, DIV, LET, CLOSE, VARIABLE, LITERAL, COMMA
	}
	
	static {
		tokens.put("add(", TOKEN_TYPE.ADD);
		tokens.put("mult(", TOKEN_TYPE.MULT);
		tokens.put("div(", TOKEN_TYPE.DIV);
		tokens.put("let(", TOKEN_TYPE.LET);
		tokens.put(")", TOKEN_TYPE.CLOSE);
		tokens.put(",", TOKEN_TYPE.COMMA);
	}
	
	private static class Token {
		public final TOKEN_TYPE type;
		public final String value;
		public final int advancePointerBy;
		
		public Token(TOKEN_TYPE type, String value, int advancePointerBy) {
			super();
			this.type = type;
			this.value = value;
			this.advancePointerBy = advancePointerBy;
		}

	}
	
	private static class ParseContext {
		private Operation operation;
		private int currentPosition;

		public ParseContext(Operation operation) {
			super();
			this.operation = operation;
		}
		
	}
	
	public static Expression parse(String expression) {
		Stack<ParseContext> stack = new Stack<ParseContext>();
		return parse(expression, stack);
	}

	private static Expression parse(String expression, Stack<ParseContext> stack) {
		ParseContext context = null;
		int currentChar = 0;
		log.info("Starting to parse expression '{}'", expression);
		while (currentChar < expression.length()) {
			Token token = emitNextToken(expression, currentChar);
			if (log.isDebugEnabled()) {
				log.debug("Token of type {} and value '{}' parsed", token.type, token.value);
			}
			switch (token.type) {
			case ADD:
				if (context != null) {
					stack.push(context);
				}
				context = new ParseContext(new Addition());
				break;
			case MULT:
				if (context != null) {
					stack.push(context);
				}
				context = new ParseContext(new Multiplication());
				break;
			case DIV:
				if (context != null) {
					stack.push(context);
				}
				context = new ParseContext(new Division());
				break;
			case CLOSE:
				//TODO do some checks to see if all arguments were satisfied
				Operation o = context.operation;
				if (!stack.isEmpty()) {
					context = stack.pop();
					context.operation.setArgument(context.currentPosition, o);
				}
				break;
			case COMMA:
				if (context == null) {
					throw new IllegalStateException("Encountered unexpected comma at position: " + currentChar);
				}
				context.currentPosition++;
				break;
			case LITERAL:
				Literal literal = new Literal(Integer.parseInt(token.value));
				if (context == null) {
					return literal;
				}
				context.operation.setArgument(context.currentPosition, literal);
				break;
			case VARIABLE:
				if (context.operation instanceof Let && context.currentPosition == 0) {
					//new variable is defined
					context.operation.setArgument(context.currentPosition, new Variable(token.value));
				} else {
					//operation is used, need to find it
					Variable var = null;
					if (!stack.isEmpty()) {
						for(int i = stack.size()-1; i >= 0 ; i--) {
							ParseContext parseContext = stack.get(i);
							if (parseContext.operation instanceof Let) {
								Let let = (Let) parseContext.operation;
								if (let.getVariable().getName().equals(token.value)) {
									var = let.getVariable();
									break;
								}
							}
						}
					}
					if (var == null) {
						throw new IllegalArgumentException("Undevined variable used: " + token.value);
					}
					context.operation.setArgument(context.currentPosition, var);
					if (log.isDebugEnabled()) {
						log.debug("Variable '{}' resolved", var.getName());
					}

				}
				break;
			case LET:
				if (context != null) {
					stack.push(context);
				}
				context = new ParseContext(new Let());
				break;
			default:
				throw new IllegalStateException("Unable to handle the token type for some reason: " + token.type);
			}
			currentChar += token.advancePointerBy;
		}
		if (context != null) {
			return context.operation;
		}
		return null;
	}

	private static Token emitNextToken(String expression, int currentChar) {
		String substring = expression.substring(currentChar);
		int leadingWhitespaces = 0;
		while(substring.charAt(0) == ' ') {
			substring = substring.substring(1);
			leadingWhitespaces++;
		}
		for (Entry<String, TOKEN_TYPE> entry : tokens.entrySet()) {
			if (substring.startsWith(entry.getKey())) {
				return new Token(entry.getValue(), entry.getKey(), entry.getKey().length() + leadingWhitespaces);
			}
		}
		if (isDigit(substring.charAt(0))) {
			//it's a literal
			StringBuffer buffer = new StringBuffer();
			buffer.append(substring.charAt(0));
			int startPosition = 1;
			while (isDigit(substring.charAt(startPosition))) {
				buffer.append(substring.charAt(startPosition));
				startPosition++;
			}
			return new Token(TOKEN_TYPE.LITERAL, buffer.toString(), buffer.length() + leadingWhitespaces);
		}
		if (isLetter(substring.charAt(0))) {
			//it's a variable
			StringBuffer buffer = new StringBuffer();
			buffer.append(substring.charAt(0));
			int startPosition = 1;
			while (isLetter(substring.charAt(startPosition))) {
				buffer.append(substring.charAt(startPosition));
				startPosition++;
			}
			return new Token(TOKEN_TYPE.VARIABLE, buffer.toString(), buffer.length() + leadingWhitespaces);
		}
		throw new IllegalArgumentException("Unable to parse starting at position: " + currentChar);
	}
	
	private static boolean isDigit(char ch) {
		return ch >= '0' && ch <= '9';
	}
	
	private static boolean isLetter(char ch) {
		return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
	}
}
