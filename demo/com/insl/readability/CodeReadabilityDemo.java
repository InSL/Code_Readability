package com.insl.readability;

import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameAT;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameCOMMENT_BLOCK;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameCOMMENT_JAVADOC;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameCOMMENT_LINE;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameDOT;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameStringLiteral;
import static org.eclipse.jdt.core.compiler.ITerminalSymbols.TokenNameWHITESPACE;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import com.insl.readability.formatter.token.AppendWhitespaceFormatter;
import com.insl.readability.formatter.token.DefaultFormatter;
import com.insl.readability.formatter.token.NullFormatter;
import com.insl.readability.formatter.token.WhitespaceFormatter;

public class CodeReadabilityDemo extends JPanel {

	private static final long serialVersionUID = -1077584979378950262L;

	protected JTextArea textArea;
	private String unformattedCode = "";
	private final CodeFormatter formatter = new CodeFormatter();
	private boolean buttenPressed;

	public CodeReadabilityDemo() {
		super(new GridBagLayout());

		initTestArea();
		initFormatter();

		registerKeyListener();
		addTextAreaToPanel();

		textArea.setText("\n"
				+ "\t@Test\n"
				+ "\tpublic void canWithdrawFromAccount() {\n"
				+ "\t\tEuro thirtyEuros = new Euro(30);\n"
				+ "\t\tEuro nowThirtyEurosInTheRed = new Euro(-30);\n"
				+ "\n"
				+ "\tPerform.withdrawWithAnEmptyAccountAnd()\n"
				+ "\t\t.aCustomerWhoCanOverdrawHisAccount().heWithdraws(thirtyEuros)\n"
				+ "\t\t.gets(thirtyEuros)\n"
				+ "\t\t.andHisAccountBalanceIs(nowThirtyEurosInTheRed).verifyTest();\n"
				+ "\t}" + "\n\n" +

				"\t@Test\n" + "\tpublic void canNotWithdrawFromAccount() {\n"
				+ "\t\tEuro thirtyEuros = new Euro(30);\n"
				+ "\t\tEuro nothing = Euro.ZERO;\n"
				+ "\t\tEuro stillTheSame = Euro.ZERO;\n"
				+ "\t\tPerform.withdrawWithAnEmptyAccountAnd()\n"
				+ "\t\t.aCustomerWhoCanNotOverdrawHisAccount()\n"
				+ "\t\t.heWithdraws(thirtyEuros).gets(nothing)\n"
				+ "\t\t.andHisAccountBalanceIs(stillTheSame).verifyTest();\n"
				+ "\t}");
	}

	private void initTestArea() {
		textArea = new JTextArea(80, 160);
		textArea.setFont(new Font("Monaco", Font.PLAIN, 12));
	}

	private void initFormatter() {
		AppendWhitespaceFormatter tokenFormatterImplementation = new AppendWhitespaceFormatter();

		formatter.addTokenFormatter(TokenNameStringLiteral,
				tokenFormatterImplementation);
		formatter.addTokenFormatter(TokenNameCOMMENT_LINE,
				tokenFormatterImplementation);
		formatter.addTokenFormatter(TokenNameCOMMENT_BLOCK,
				tokenFormatterImplementation);
		formatter.addTokenFormatter(TokenNameCOMMENT_JAVADOC,
				tokenFormatterImplementation);

		formatter.addTokenFormatter(TokenNameWHITESPACE,
				new WhitespaceFormatter());
		formatter.addTokenFormatter(TokenNameDOT, new NullFormatter());
		formatter.addTokenFormatter(TokenNameAT, new NullFormatter());

		formatter.setDefaultTokenFormatter(new DefaultFormatter());
	}

	private void registerKeyListener() {
		Action formatCode = new FormateCodeAction();
		Action undoFormatCode = new UndoFormatCodeAction();

		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),
				"formateCode");
		textArea.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, true),
				"undoFormateCode");
		textArea.getActionMap().put("formateCode", formatCode);
		textArea.getActionMap().put("undoFormateCode", undoFormatCode);
	}

	private void addTextAreaToPanel() {
		JScrollPane scrollPane = new JScrollPane(textArea);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = GridBagConstraints.REMAINDER;

		constraints.fill = GridBagConstraints.HORIZONTAL;

		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		add(scrollPane, constraints);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	public static void createAndShowGUI() {
		JFrame frame = new JFrame("CodeReadabilityDemo");

		frame.add(new CodeReadabilityDemo());

		frame.pack();
		frame.setVisible(true);
	}

	private final class UndoFormatCodeAction extends AbstractAction {

		private static final long serialVersionUID = -6975149914660560777L;

		@Override
		public void actionPerformed(ActionEvent e) {
			textArea.setText(unformattedCode);
			buttenPressed = false;
		}
	}

	private final class FormateCodeAction extends AbstractAction {

		private static final long serialVersionUID = 6319786186334057801L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!buttenPressed) {
				buttenPressed = true;
				unformattedCode = textArea.getText();
				String formattedCode = formatter.format(new String(
						unformattedCode));
				textArea.setText(formattedCode);
			}
		}
	}
}
