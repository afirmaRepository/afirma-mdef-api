package es.gob.afirma.mdef.pdf;

import java.io.IOException;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.util.List;

import com.aowagie.text.DocumentException;

import es.gob.afirma.keystores.AOCertificatesNotFoundException;
import es.gob.afirma.keystores.AOKeyStoreManagerException;
import es.gob.afirma.keystores.filters.CertificateFilter;
import es.gob.afirma.signers.pades.BadPdfPasswordException;
import es.gob.afirma.signers.pades.PdfHasUnregisteredSignaturesException;
import es.gob.afirma.signers.pades.PdfIsCertifiedException;
import nu.xom.XMLException;
//import es.gob.afirma.mdef.pdf.PdfException;

public interface AutofirmaDefApi {

	/**
	 * Obtiene los nombres de los campos de firma de un documento pdf.
	 * 
	 * @param filePath
	 *            Ruta del documento pdf.
	 * @return Nombres de los campos de firma de un documento pdf.
	 * @throws PdfException
	 *             Error al gestionar el documento pdf.
	 */
	public String camposFirmaPDF(final String filePath) throws PdfException;

	/**
	 * Obtiene el n&uacute;mero de p&aacute;ginas del documento pdf.
	 * 
	 * @param filePath
	 *            Ruta del documento pdf.
	 * @return N&uacute;mero de p&aacute;ginas del documento pdf.
	 * @throws PdfException
	 *             Error al gestionar el documento pdf.
	 */
	public int numeroPaginasPDF(final String filePath) throws PdfException;

	/**
	 * Agrega una p&aacute;gina en blanco al final del documento pdf.
	 * 
	 * @param filePath
	 *            Ruta del fichero pdf.
	 * @throws PdfException
	 *             Error al gestionar el fichero pdf.
	 */
	public void anadirPaginaBlancaPDF (final String filePath) throws PdfException;

	/**
	 * Realiza la firma en lotes sobre los documentos definidos en un fichero
	 * XML.
	 * 
	 * @param xmlPath
	 *            Ruta del fichero XML.
	 * @param alias
	 *            Alias del certificado a usar en la firma.
	 * @param password
	 *            Password para obtener la clave privada de firma.
	 * @return Registro del resultado general del proceso por lote en un XML
	 * @throws Exception
	 *             Error en la firma por lotes
	 */
	public String firmaBatch(final String xmlPath, String alias, String password) throws Exception;

	/**
	 * Firma del fichero pdf.
	 * 
	 * @param originalPath
	 *            Ruta de origen.
	 * @param destinyPath
	 *            Ruta de destino.
	 * @param policyIdentifier
	 *            Identificador de pol&iacute;ticas.
	 * @param fieldName
	 *            Nombre del campo.
	 * @param tsaName
	 *            Nombre tsa.
	 * @param xmlLook
	 *            Apariencia del XML.
	 * @throws PdfException
	 *             Error en la lectua del pdf.
	 * @throws XMLException
	 *             Errir en la lectura del XML.
	 * @throws AOCertificatesNotFoundException
	 *             No se han encontrado certficados.
	 * @throws BadPdfPasswordException
	 *             Password incorrecta.
	 * @throws PdfIsCertifiedException
	 *             Pdf no certificado.
	 * @throws PdfHasUnregisteredSignaturesException
	 *             Firma del pdf no registrada.
	 */
	public void firmaFinal(final String originalPath, final String destinyPath, final String policyIdentifier,
			final String fieldName, final String xmlLook) throws PdfException,
	XMLException, AOCertificatesNotFoundException, BadPdfPasswordException, PdfIsCertifiedException,
			PdfHasUnregisteredSignaturesException;

	/**
	 * Verifica la firma correcta de un fichero.
	 * 
	 * @param filePath
	 *            Ruta del fichero firmado.
	 * @return True si la verificaci&oacute;n es correcta.
	 * @throws PdfException
	 *             Error en la apertura o en el acceso al fichero.
	 */
	public boolean verificarFirmasPDF(final String filePath) throws PdfException;

	/**
	 * Obtiene la clave privada tras aplicar los filtros.
	 * 
	 * @param filters
	 *            Filtros para la obtenci&oacute;n de la clave.
	 * @return Clave privada.
	 * @throws UnrecoverableEntryException
	 *             Error en la inicializaci&oacute;n del almac&eacute;n.
	 * @throws AOCertificatesNotFoundException
	 *             No se han encontrados certificados.
	 * @throws AOKeyStoreManagerException
	 *             Error en la inicializaci&oacute;n del almac&eacute;n.
	 * @throws KeyStoreException
	 *             Error en la inicializaci&oacute;n del almac&eacute;n.
	 * @throws NoSuchAlgorithmException
	 *             No existe el algoritmo.
	 */
	public PrivateKeyEntry recuperaClavePrivada(final List<? extends CertificateFilter> filters)
			throws UnrecoverableEntryException, AOCertificatesNotFoundException, AOKeyStoreManagerException,
			KeyStoreException, NoSuchAlgorithmException;

	/**
	 * Se crea un campo vac&iacute;o de firma para una p&aacute;gina y
	 * posici&oacute;n concretos. La p&aacute;gina puede ser menor a 0 para
	 * comenzar a contar desde la &uacute;ltima p&aacute;gina (-1 =
	 * &uacute;ltima, -2 = pen&uacute;ltima, etc). En caso de ser 0 el campo se
	 * crea en la primera p&aacute;gina.
	 * 
	 * @param filePath
	 *            Fichero pdf sobre el que incluir el campo de firma.
	 * @param page
	 *            N&uacute;mero de p&aacute;gina.
	 * @param leftX
	 *            Coordinada izquierda X de la posici&oacute;n del campo.
	 * @param leftY
	 *            Coordinada izquierda Y de la posici&oacute;n del campo.
	 * @param rightX
	 *            Coordinada derecha X de la posici&oacute;n del campo.
	 * @param rightY
	 *            Coordinada izquierda Y de la posici&oacute;n del campo.
	 * @throws DocumentException
	 *             Error al abrir el documento.
	 * @throws IOException
	 *             Error al encontrar el fichero.
	 * @throws PdfException
	 *             Error al obtener el n&uacute;mero de p&aacute;ginas.
	 * @throws IllegalArgumentException
	 *             N&uacute;mero de p&aacute;gina fuera de los l&iacute;mites.
	 */
	public void anadirCampoFirma(final String filePath, final int page, final int leftX, final int leftY, final int rightX,
			final int rightY) throws DocumentException, IOException;// ,
																	// PdfException;
	
	
	/**
	 * obtener el CN del certificado cargado de una tarjeta (almac&eacute;n de certificados).
	 * 
	 * @param filters
	 *            Filtros para la obtenci&oacute;n de la clave.
	 * @return Cn de la tarjeta.
	 * @throws UnrecoverableEntryException
	 *             Error en la inicializaci&oacute;n del almac&eacute;n.
	 * @throws AOCertificatesNotFoundException
	 *             No se han encontrados certificados.
	 * @throws AOKeyStoreManagerException
	 *             Error en la inicializaci&oacute;n del almac&eacute;n.
	 * @throws KeyStoreException
	 *             Error en la inicializaci&oacute;n del almac&eacute;n.
	 * @throws NoSuchAlgorithmException
	 *             No existe el algoritmo.
	 */
	public String cnTarjeta (final List<? extends CertificateFilter> filters) 
			throws UnrecoverableEntryException, AOCertificatesNotFoundException, AOKeyStoreManagerException,
			KeyStoreException, NoSuchAlgorithmException, PdfException;

}