package org.georgev.calc.parser;

import static org.junit.Assert.*;

import org.georgev.calc.domain.Expression;
import org.junit.Test;

public class ExpressionParserTest {
	@Test
	public void shouldParseAddExpression() throws Exception {
		Expression expression = ExpressionParser.parse("add(2,2)");
		assertEquals(4, expression.evaluate());
	}
	
	@Test
	public void shouldHandleSpaces() throws Exception {
		Expression expression = ExpressionParser.parse(" add(2, 2)");
		assertEquals(4, expression.evaluate());
	}
	
	@Test
	public void shouldParseAddInsideAddExpression() throws Exception {
		Expression expression = ExpressionParser.parse("add(2,add(2,2))");
		assertEquals(6, expression.evaluate());
	}
	
	@Test
	public void shouldMultiplyDivideAdd() throws Exception {
		Expression expression = ExpressionParser.parse("div(mult(16,add(2,2)),8)");
		assertEquals(8, expression.evaluate());
	}
	
	@Test
	public void shouldEvaluateSimpleLet() throws Exception {
		Expression expression = ExpressionParser.parse("let(a,2,add(a,2))");
		assertEquals(4, expression.evaluate());
	}
	
	@Test
	public void shouldEvaluateInlineLet() throws Exception {
		Expression expression = ExpressionParser.parse("let(a, 5, let(b, mult(a, 10), add(b, a)))");
		assertEquals(55, expression.evaluate());
	}
	
	@Test
	public void shouldEvaluateComplexLet() throws Exception {
		Expression expression = ExpressionParser.parse("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))");
		assertEquals(40, expression.evaluate());
	}

}
