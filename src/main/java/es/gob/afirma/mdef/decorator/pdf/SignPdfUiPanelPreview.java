package es.gob.afirma.mdef.decorator.pdf;

import java.awt.FontMetrics;

public class SignPdfUiPanelPreview {

	private static final char NEWLINE = '\n';
	private static final String SPACE_SEPARATOR = " "; //$NON-NLS-1$
	private static final String SPLIT_REGEXP = "\\s+"; //$NON-NLS-1$
	
	public static String breakLines(final String input, final int maxLineLength, final FontMetrics fm) {
		final String[] tokens = input.split(SPLIT_REGEXP);
		final StringBuilder output = new StringBuilder(input.length());
		int lineLen = 0;
		for (int i = 0; i < tokens.length; i++) {
			String word = tokens[i];

			if (lineLen + fm.stringWidth(SPACE_SEPARATOR + word) > maxLineLength) {
				if (i > 0) {
					output.append(NEWLINE);
				}
				lineLen = 0;
			}
			if (i < tokens.length - 1
					&& lineLen + fm.stringWidth(word + SPACE_SEPARATOR + tokens[i + 1]) <= maxLineLength) {
				word += SPACE_SEPARATOR;
			}
			output.append(word);
			lineLen += fm.stringWidth(word);
		}
		return output.toString();
	}

	
}
