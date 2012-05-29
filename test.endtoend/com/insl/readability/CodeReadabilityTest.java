package com.insl.readability;

import static com.insl.readability.formatter.token.Assert.assertThat;
import static com.insl.readability.formatter.token.Assert.format;
import static com.insl.readability.formatter.token.Assert.to;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameAT;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameCOMMENT_BLOCK;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameCOMMENT_JAVADOC;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameCOMMENT_LINE;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameDOT;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameStringLiteral;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameWHITESPACE;

import org.junit.Before;
import org.junit.Test;

import com.insl.readability.formatter.token.AppendWhitespaceFormatter;
import com.insl.readability.formatter.token.DefaultFormatter;
import com.insl.readability.formatter.token.NullFormatter;
import com.insl.readability.formatter.token.WhitespaceFormatter;

public class CodeReadabilityTest {

	private CodeFormatter formatter;

	@Before
	public void setUp() throws Exception {
		formatter = new CodeFormatter();
		AppendWhitespaceFormatter nonFormatter = new AppendWhitespaceFormatter();

		formatter.addTokenFormatter(TokenNameStringLiteral, nonFormatter);
		formatter.addTokenFormatter(TokenNameCOMMENT_LINE, nonFormatter);
		formatter.addTokenFormatter(TokenNameCOMMENT_BLOCK, nonFormatter);
		formatter.addTokenFormatter(TokenNameCOMMENT_JAVADOC, nonFormatter);

		formatter.addTokenFormatter(TokenNameWHITESPACE,
				new WhitespaceFormatter());
		formatter.addTokenFormatter(TokenNameDOT, new NullFormatter());
		formatter.addTokenFormatter(TokenNameAT, new NullFormatter());

		formatter.setDefaultTokenFormatter(new DefaultFormatter());
	}

	@Test
	public void testThatACompositeNameGetSplitted() {

		assertThat(formatter, format("int testName;"), to("int test name ;"));
	}

	@Test
	public void testThatACompositeNameWithAOneLetterWord_A_GetSplitted() {

		assertThat(formatter, "int testWithAName;",
				to("int test with a name ;"));
	}

	@Test
	public void testThatAPointOperationGetWellFormatted() {

		assertThat(formatter, "test.variable", to("test .variable"));
	}

	@Test
	public void testThatAMethodGetWellFormatted() throws Exception {

		assertThat(formatter, "testMethod();", to("test method ( ) ;"));
	}

	@Test
	public void testThatOneParameterOfAMethodCallGetWellFormatted() {

		assertThat(formatter, "testMethod(testParameter);",
				to("test method ( test parameter ) ;"));
	}

	@Test
	public void testThatMultipleParametersOfAMethodCallGetWellFormatted() {

		assertThat(formatter,
				"testMethod(testParameterOne, testParameterTwo);",
				to("test method ( test parameter one , test parameter two ) ;"));
	}

	@Test
	public void testThatMultipleCompositeParametersOfAMethodCallGetWellForamtted() {

		assertThat(formatter, "testMethod(testParameterOne,testParameterTwo);",
				to("test method ( test parameter one , test parameter two ) ;"));
	}

	@Test
	public void testThatAConcatenationOfMethodCallsGetWellFormatted() {

		assertThat(
				formatter,
				"testMethodOne(testParameter).testMethodTwo(testParameter);",
				to("test method one ( test parameter ) .test method two ( test parameter ) ;"));
	}

	@Test
	public void testThatAMultiLineCodeLineGetWellFormatted() {

		assertThat(
				formatter,
				"performWithdrawWithAnEmptyAccountAnd()\n"
						+ "\t\t.aCustomerWhoCanNotOverdrawHisAccount()\n"
						+ "\t\t.heWithdraws(thirtyEuros).gets(nothing)\n"
						+ "\t\t.andHisAccountBalanceIs(stillTheSame)\n"
						+ "\t\t.verifyTest();",
				to("perform withdraw with an empty account and ( ) \n"
						+ "\t\t.a customer who can not overdraw his account ( ) \n"
						+ "\t\t.he withdraws ( thirty euros ) .gets ( nothing ) \n"
						+ "\t\t.and his account balance is ( still the same ) \n"
						+ "\t\t.verify test ( ) ;"));
	}

	@Test
	public void testThatAStringGetNotFormatted() {
		assertThat(formatter, "\"testString()\"", to("\"testString()\""));
	}

	@Test
	public void testThatAConstantGetNotFormattedAndSplitted() {
		assertThat(formatter, "testObject.TEST_CONSTANT",
				to("test object .TEST_CONSTANT"));
	}

	@Test
	public void testThatANumberGetNotSplitted() {
		assertThat(formatter, "42", to("42"));
	}

	@Test
	public void testThatATypeGetNotFormattedAndSplitted() {
		assertThat(formatter, "new TestClass()", to("new TestClass ( )"));
	}

	@Test
	public void testThatNoWhitespaceGetsAppendToAAnnotation() {
		assertThat(formatter, "@Test", to("@Test"));
	}

	@Test
	public void testThatASingleLineCommentGetNotFormatted() {
		assertThat(formatter, "//Single line comment",
				to("//Single line comment"));
	}

	@Test
	public void testThatABlockCommentGetNotFormatted() {
		assertThat(formatter, "/* Block comment */", to("/* Block comment */"));
	}

	@Test
	public void testThatAJavadocCommentGetNotFormatted() {
		assertThat(formatter, "/** Javadoc comment **/",
				to("/** Javadoc comment **/"));
	}
}
