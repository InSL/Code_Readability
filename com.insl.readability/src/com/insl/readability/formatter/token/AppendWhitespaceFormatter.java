package com.insl.readability.formatter.token;

import com.insl.readability.TokenFormatter;

public class AppendWhitespaceFormatter implements TokenFormatter {

	@Override
	public String format(String tokenSource) {
		return tokenSource;
	}

	@Override
	public boolean appendWithespace() {
		return true;
	}
}
