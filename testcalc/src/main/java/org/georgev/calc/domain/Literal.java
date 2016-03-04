package org.georgev.calc.domain;

public class Literal implements Expression {
	
	int value;

	public Literal() {
	}
	
	public Literal(int value) {
		this.value = value;
	}
	
	@Override
	public int evaluate() {
		return value;
	}

}
