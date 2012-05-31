package com.insl.readability.formatter.token;

import static com.insl.readability.formatter.token.Assert.assertThat;
import static com.insl.readability.formatter.token.Assert.format;
import static com.insl.readability.formatter.token.Assert.to;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.insl.readability.TokenFormatter;
import com.insl.readability.formatter.token.WhitespaceFormatter;

public class WhitespaceFormatterTest {

	private TokenFormatter formatter;

	@Before
	public void setUp() throws Exception {
		formatter = new WhitespaceFormatter();
	}

	@Test
	public void testThatFormatterRemoveAWithspace() {

		assertThat(formatter, format(" "), to(""));
	}

	@Test
	public void testThatFormatterFormatsEverythingExceptAWithespace() {
		assertThat(formatter, format("\t"), to("\t"));
		assertThat(formatter, format("\n"), to("\n"));
	}

	@Test
	public void testThatFormatterAppendNoWhitespace() {
		assertFalse(formatter.appendWithespace());
	}
}
