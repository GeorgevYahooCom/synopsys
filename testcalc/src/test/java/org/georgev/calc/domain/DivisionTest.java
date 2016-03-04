package org.georgev.calc.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class DivisionTest {
	@Test
	public void shouldMultiply() throws Exception {
		Division addition = new Division(new Literal(6), new Literal(2));
		assertEquals(3, addition.evaluate());
		
	}
}
