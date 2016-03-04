package org.georgev.calc.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class AdditionTest {
	@Test
	public void shouldAddTwoLiterals() throws Exception {
		Addition addition = new Addition(new Literal(2), new Literal(2));
		assertEquals(4, addition.evaluate());
	}
	@Test
	public void shouldEvaluateResursiveAddion() throws Exception {
		Addition addition = new Addition(new Literal(2), new Addition(new Literal(2), new Literal(2)));
		assertEquals(6, addition.evaluate());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldfailIfFirstArgumentUndefiend() throws Exception {
		Addition addition = new Addition(null, new Literal(2));
		addition.evaluate();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldfailISecondArgumentUndefiend() throws Exception {
		Addition addition = new Addition(new Literal(2), null);
		addition.evaluate();
	}
}
