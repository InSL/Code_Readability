package com.insl.readability.formatter.token;

import static com.insl.readability.formatter.token.Assert.assertThat;
import static com.insl.readability.formatter.token.Assert.format;
import static com.insl.readability.formatter.token.Assert.to;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.insl.readability.TokenFormatter;

public class NullFormatterTest {

	private TokenFormatter formatter;

	@Before
	public void setUp() throws Exception {
		formatter = new NullFormatter();
	}

	@Test
	public void testThatNothingGetsFormatted() {
		assertThat(formatter, format("testMethod();"), to("testMethod();"));
	}

	@Test
	public void testThatFormatterAppendNoWhitespace() {
		assertFalse(formatter.appendWithespace());
	}
}
