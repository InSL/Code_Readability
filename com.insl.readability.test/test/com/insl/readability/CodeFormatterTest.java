package com.insl.readability;

import static com.insl.readability.formatter.token.Assert.assertThat;
import static com.insl.readability.formatter.token.Assert.format;
import static com.insl.readability.formatter.token.Assert.to;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameIdentifier;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNamenew;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class CodeFormatterTest {

	private CodeFormatter formatter;

	@Before
	public void setUp() throws Exception {
		formatter = new CodeFormatter();
	}

	@Test
	public void testThatACodeSeparatorWithoutTokenFormatterDosNotFormatCode() {
		assertThat(
				formatter,
				format("testMethod(testParameterOne, testParameterTwo.value);"),
				to("testMethod(testParameterOne, testParameterTwo.value);"));
	}

	@Test
	public void testThatACodeSeperatorWorksWithACustomizedDefaultTokenFormatter() {
		TokenFormatter tokenFormatter = mock(TokenFormatter.class);
		when(tokenFormatter.format("testMethod")).thenReturn("test Method");
		formatter.setDefaultTokenFormatter(tokenFormatter);

		assertThat(formatter, format("testMethod"), to("test Method"));

		verify(tokenFormatter).format("testMethod");
	}

	@Test
	public void testThatACodeSeperatorCanWorkWithDifferendTokenFormatters() {

		TokenFormatter identifierFormatter = mock(TokenFormatter.class);
		TokenFormatter newFormatter = mock(TokenFormatter.class);
		when(identifierFormatter.format("ClassName")).thenReturn("ClassName");
		when(newFormatter.format("new")).thenReturn("new");
		formatter.addTokenFormatter(TokenNamenew, newFormatter);
		formatter.addTokenFormatter(TokenNameIdentifier, identifierFormatter);

		assertThat(formatter, format("new ClassName();"),
				to("new ClassName();"));

		verify(newFormatter).format("new");
		verify(identifierFormatter).format("ClassName");
	}
}
