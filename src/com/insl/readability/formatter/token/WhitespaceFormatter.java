package com.insl.readability.formatter.token;

import com.insl.readability.TokenFormatter;

public class WhitespaceFormatter implements TokenFormatter {
	private static final String WHITESPACE = " ";

	@Override
	public String format(String tokenSource) {
		if (!tokenSource.matches(WHITESPACE)) {
			return tokenSource;
		}
		return "";
	}

	@Override
	public boolean appendWithespace() {
		return false;
	}
}