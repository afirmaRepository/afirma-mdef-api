package es.gob.afirma.mdef.pdf;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Properties;

import com.aowagie.text.Rectangle;
import com.aowagie.text.pdf.PdfAnnotation;
import com.aowagie.text.pdf.PdfFormField;
import com.aowagie.text.pdf.PdfName;
import com.aowagie.text.pdf.PdfReader;
import com.aowagie.text.pdf.PdfStamper;
import com.aowagie.text.pdf.PdfString;

public class PdfOperations {

	/**
	 * Crea campos de firma en un documento PDF.
	 * @param pdf Documento PDF.
	 * @param options Opciones del campo de firma.
	 * @return PDF con los campos de firma.
	 */
	public static byte[] createSignatureFields(final byte[] pdf, final Properties options) {

		final Rectangle position = new Rectangle(100, 100, 200, 200);
		final int page = 1;
		final String fieldname = "Signature1";

		final Calendar calendar = Calendar.getInstance();

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		/*
		final PdfReader reader = new PdfReader(pdf);
		final PdfStamper stp = new PdfStamper(reader, baos, calendar);
		final PdfFormField field = PdfFormField.createSignature(stp.getWriter());
		field.setFlags(PdfAnnotation.FLAGS_PRINT);
		field.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g")); //$NON-NLS-1$
		// Nombre del campo
		field.setFieldName(fieldname);
		// Posicion
		field.setWidget(position, null);
		// Numero de pagina
		field.setPage(page);
		// Insertamos el campo en la pagina
		stp.addAnnotation(field, page);
		stp.close(calendar);
*/
		return baos.toByteArray();
	}
}
