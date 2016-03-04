package org.georgev.calc.domain;

public class Let implements Operation {

	private Variable variable;
	
	private Expression value;
	
	private Operation operation;
	
	@Override
	public int evaluate() {
		return operation.evaluate();
	}
	
	public Variable getVariable() {
		return variable;
	}

	@Override
	public void setArgument(int position, Expression expression) {
		if (position == 0) {
			variable = (Variable) expression;
		} else if (position == 1) {
			value = expression;
			variable.setValue(value);
		} else if (position == 2) {
			operation = (Operation) expression;
		} else {
			throw new IllegalArgumentException("Illegal position: " + position);
		}
	}

}
