package org.georgev.calc.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class MultiplicationTest {
	@Test
	public void shouldMultiply() throws Exception {
		Multiplication addition = new Multiplication(new Literal(6), new Literal(2));
		assertEquals(12, addition.evaluate());
		
	}
}
