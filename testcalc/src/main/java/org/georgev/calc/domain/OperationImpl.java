package org.georgev.calc.domain;

public abstract class OperationImpl implements Operation {

	protected Expression firstArgument;
	
	protected Expression secondArgument;

	public void setArgument(int position, Expression expression) {
		if (position > 2 || position < 0) {
			throw new IllegalArgumentException("Invalid position: " + position);
		}
		if (position == 0) {
			firstArgument = expression;
		} else {
			secondArgument = expression;
		}
	}
}
