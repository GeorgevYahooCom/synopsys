package org.georgev.calc.domain;

public class Variable implements Expression {
	private String name;
	private Expression value;

	public Variable() {
	}
	
	public Variable(String name) {
		this.name = name;
	}
	

	public Expression getValue() {
		return value;
	}

	public void setValue(Expression value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int evaluate() {
		return value.evaluate();
	}

}
