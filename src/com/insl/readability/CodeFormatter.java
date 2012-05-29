package com.insl.readability;

import java.util.HashMap;

import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;
import org.eclipse.jdt.core.compiler.InvalidInputException;

import com.insl.readability.formatter.token.NullFormatter;

public class CodeFormatter implements Formatter {
	private static final String WHITESPACE = " ";

	private final IScanner scanner;
	private TokenFormatter defaultFormatter;
	private final HashMap<Integer, TokenFormatter> formatters;

	public CodeFormatter() {
		scanner = ToolFactory.createScanner(true, true, false, true);

		defaultFormatter = new NullFormatter();
		formatters = new HashMap<Integer, TokenFormatter>();
	}

	@Override
	public String format(String code) {
		String formattedCode = "";
		scanner.setSource(code.toCharArray());
		TokenFormatter formatter = new NullFormatter();
		try {
			while (true) {
				int token = scanner.getNextToken();

				if (isEndOfFile(token))
					break;

				if (formatter.appendWithespace())
					formattedCode += WHITESPACE;

				formatter = formatterFor(token);
				formattedCode += formatter.format(tokenSource());
			}

		} catch (InvalidInputException e) {
		}

		return formattedCode;
	}

	private String tokenSource() {
		return new String(scanner.getCurrentTokenSource());
	}

	private boolean isEndOfFile(int token) {
		return token == ITerminalSymbols.TokenNameEOF;
	}

	private TokenFormatter formatterFor(int token) {
		if (formatters.containsKey(token))
			return formatters.get(token);
		else
			return defaultFormatter;
	}

	public void setDefaultTokenFormatter(TokenFormatter formatter) {
		this.defaultFormatter = formatter;
	}

	public void addTokenFormatter(int token, TokenFormatter formatter) {
		formatters.put(token, formatter);
	}
}
