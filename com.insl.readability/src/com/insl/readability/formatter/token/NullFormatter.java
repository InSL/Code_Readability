package com.insl.readability.formatter.token;

import com.insl.readability.TokenFormatter;

public final class NullFormatter implements TokenFormatter {

	@Override
	public String format(String tokenSource) {
		return tokenSource;
	}

	@Override
	public boolean appendWithespace() {
		return false;
	}
}