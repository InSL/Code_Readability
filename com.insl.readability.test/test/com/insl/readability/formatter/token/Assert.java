package com.insl.readability.formatter.token;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.hamcrest.MatcherAssert;

import com.insl.readability.Formatter;

public class Assert {

	public static void assertThat(Formatter formatter, String unformatted,
			String formatted) {
		String formattedCode = formatter.format(unformatted);
		MatcherAssert.assertThat(formattedCode, is(equalTo(formatted)));
	}

	public static String format(String string) {
		return string;
	}

	public static String to(String string) {
		return string;
	}
}
