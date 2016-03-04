package org.georgev.calc.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class LiteralTest {
	@Test
	public void testName() throws Exception {
		Expression literal = new Literal(10);
		assertEquals(10, literal.evaluate());
	}
}
