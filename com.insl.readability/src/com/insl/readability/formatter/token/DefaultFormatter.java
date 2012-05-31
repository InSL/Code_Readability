package com.insl.readability.formatter.token;

import java.util.Scanner;

import com.insl.readability.TokenFormatter;

public class DefaultFormatter implements TokenFormatter {
	private static final String WHITESPACE = " ";
	private static final String TYPE_PATTERN = "[A-Z].*";
	private static final String CONSTANT_PATTERN = "[^a-z]*";
	private final String WORD_SEPERATOR = "(?=[a-zA-Z0-9])(?=[_A-Z0-9])";

	@Override
	public String format(String token) {
		if (isConstant(token) || isType(token))
			return token;

		Scanner scanner = new Scanner(token);
		scanner.useDelimiter(WORD_SEPERATOR);

		String formattedCode = "";
		while (scanner.hasNext()) {
			formattedCode += scanner.next();
			if (scanner.hasNext())
				formattedCode += WHITESPACE;
		}

		return formattedCode.toLowerCase();
	}

	private boolean isConstant(String token) {
		return token.matches(CONSTANT_PATTERN);
	}

	private boolean isType(String token) {
		return token.matches(TYPE_PATTERN);
	}

	@Override
	public boolean appendWithespace() {
		return true;
	}
}
