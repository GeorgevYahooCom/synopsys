package org.georgev.calc.domain;

public interface Operation extends Expression {
	public void setArgument(int position, Expression expression);
}
