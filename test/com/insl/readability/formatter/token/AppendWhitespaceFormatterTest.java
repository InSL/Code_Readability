package com.insl.readability.formatter.token;

import static com.insl.readability.formatter.token.Assert.assertThat;
import static com.insl.readability.formatter.token.Assert.format;
import static com.insl.readability.formatter.token.Assert.to;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.insl.readability.TokenFormatter;

public class AppendWhitespaceFormatterTest {

	private TokenFormatter formatter;

	@Before
	public void setUp() throws Exception {
		formatter = new AppendWhitespaceFormatter();
	}

	@Test
	public void testFormatterDoesNotFormat() {
		assertThat(new AppendWhitespaceFormatter(), format("test"), to("test"));
	}

	@Test
	public void testThatFormatterAppendWhitespaceBehindAString() {
		assertTrue(formatter.appendWithespace());
	}
}
