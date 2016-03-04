package org.georgev.calc.domain;

public class Multiplication extends OperationImpl implements Operation {

	public Multiplication() {
		super();
	}

	public Multiplication(Expression... arguments) {
		super();
		this.firstArgument = arguments[0];
		this.secondArgument = arguments[1];
	}


	@Override
	public int evaluate() {
		if (firstArgument == null || secondArgument == null) {
			throw new IllegalArgumentException("Addition arguments not defined");
		}
		
		return firstArgument.evaluate() * secondArgument.evaluate();
	}

}
