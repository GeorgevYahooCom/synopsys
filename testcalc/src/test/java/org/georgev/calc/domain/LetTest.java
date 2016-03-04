package org.georgev.calc.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class LetTest {
	@Test
	public void shouldMultiply() throws Exception {
		Let let = new Let();
		Variable var = new Variable("a");
		let.setArgument(0, var);
		let.setArgument(1, new Literal(2));
		let.setArgument(2, new Multiplication(new Literal(6), var));
		assertEquals(12, let.evaluate());
		
	}
}
