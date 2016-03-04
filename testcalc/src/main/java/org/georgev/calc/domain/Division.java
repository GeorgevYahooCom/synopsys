package org.georgev.calc.domain;

public class Division extends OperationImpl implements Operation {

	public Division() {
		super();
	}

	public Division(Expression... arguments) {
		super();
		this.firstArgument = arguments[0];
		this.secondArgument = arguments[1];
	}


	@Override
	public int evaluate() {
		if (firstArgument == null || secondArgument == null) {
			throw new IllegalArgumentException("Addition arguments not defined");
		}
		
		return firstArgument.evaluate() / secondArgument.evaluate();
	}

}
