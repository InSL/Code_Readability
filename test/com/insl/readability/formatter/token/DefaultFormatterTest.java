package com.insl.readability.formatter.token;

import static com.insl.readability.formatter.token.Assert.assertThat;
import static com.insl.readability.formatter.token.Assert.format;
import static com.insl.readability.formatter.token.Assert.to;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DefaultFormatterTest {
	private DefaultFormatter formatter;

	@Before
	public void setUp() throws Exception {
		formatter = new DefaultFormatter();
	}

	@Test
	public void testThatFormatterAppendAWhitespace() {
		assertTrue(formatter.appendWithespace());
	}

	@Test
	public void testThatFormatterDoesNotFormatAConstant() {
		assertThat(formatter, format("CONSTANT"), to("CONSTANT"));
	}

	@Test
	public void testThatFormatterDoesNotFormatAType() {
		assertThat(formatter, format("Type"), to("Type"));
	}

	@Test
	public void testThatFormatterFormatAIdentifier() {
		assertThat(formatter, format("testIdentifier"), to("test identifier"));
	}

}
