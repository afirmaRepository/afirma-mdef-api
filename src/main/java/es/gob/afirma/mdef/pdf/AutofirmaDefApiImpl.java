package es.gob.afirma.mdef.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.security.auth.callback.PasswordCallback;

import org.springframework.stereotype.Service;

import com.aowagie.text.DocumentException;
import com.aowagie.text.Rectangle;
import com.aowagie.text.pdf.AcroFields;
import com.aowagie.text.pdf.PdfAnnotation;
import com.aowagie.text.pdf.PdfFormField;
import com.aowagie.text.pdf.PdfName;
import com.aowagie.text.pdf.PdfReader;
import com.aowagie.text.pdf.PdfStamper;
import com.aowagie.text.pdf.PdfString;

import es.gob.afirma.cert.signvalidation.SignValiderFactory;
import es.gob.afirma.cert.signvalidation.SignValidity.SIGN_DETAIL_TYPE;
import es.gob.afirma.core.AOCancelledOperationException;
import es.gob.afirma.core.misc.AOUtil;
import es.gob.afirma.core.signers.AOSignConstants;
import es.gob.afirma.core.signers.AOSigner;
import es.gob.afirma.core.signers.AOSignerFactory;
import es.gob.afirma.keystores.AOCertificatesNotFoundException;
import es.gob.afirma.keystores.AOKeyStore;
import es.gob.afirma.keystores.AOKeyStoreDialog;
import es.gob.afirma.keystores.AOKeyStoreManager;
import es.gob.afirma.keystores.AOKeyStoreManagerException;
import es.gob.afirma.keystores.AOKeyStoreManagerFactory;
import es.gob.afirma.keystores.AOKeystoreAlternativeException;
import es.gob.afirma.keystores.filters.CertificateFilter;
import es.gob.afirma.keystores.filters.PolicyIdFilter;
import es.gob.afirma.keystores.filters.rfc.KeyUsageFilter;
import es.gob.afirma.signers.pades.BadPdfPasswordException;
import es.gob.afirma.signers.pades.PdfHasUnregisteredSignaturesException;
import es.gob.afirma.signers.pades.PdfIsCertifiedException;
import es.gob.afirma.signers.pades.PdfUtil;
import es.gob.afirma.signers.pades.PdfUtil.SignatureField;
import es.gob.afirma.standalone.ui.preferences.ExtraParamsHelper;
import es.gob.afirma.standalone.ui.preferences.PreferencesManager;


@Service
public class AutofirmaDefApiImpl implements AutofirmaDefApi {

	private static final String SEPARATOR = ","; 
	private static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); 

	//@Override
	public String enumSignatureFieldNamesOld(String filePath) throws PdfException {
		final StringBuilder sb = new StringBuilder();
		try (final InputStream fis = new FileInputStream(new File(filePath))) {
			final byte[] data = AOUtil.getDataFromInputStream(fis);
			final List<SignatureField> fields = PdfUtil.getPdfEmptySignatureFields(data);
			int cont =1;
			for (final SignatureField field : fields) {
				sb.append(field.getName());
				if(fields.size()>cont){
					sb.append(SEPARATOR);
				}				
			}
			return sb.toString();
		} catch (final Exception e) {
			LOGGER.severe("Error recuperando los nombres de campos de firma del PDF: " + e); 
			throw new PdfException("Error recuperando los nombres de campos de firma del pdf: " + e, e); 			
		}
		
	}

	@Override
	public String camposFirmaPDF(String filePath) throws PdfException {
		final StringBuilder sb = new StringBuilder();
		try (final InputStream fis = new FileInputStream(new File(filePath))) {
			final byte[] data = AOUtil.getDataFromInputStream(fis);
			final List<SignatureField> fields = PdfUtil.getPdfEmptySignatureFields(data);
			PdfReader localPdfReader = new PdfReader(data);
			AcroFields localAcroFields = localPdfReader.getAcroFields();			
			List<String> localList = localAcroFields.getSignatureNames();
			
			List<String> listNamesEmpty = fields.stream().map(u -> u.getName()).collect(Collectors.toList());
			if(!localList.isEmpty()){
				listNamesEmpty.addAll(localList);
			}
			int cont =1;
			for (String nameSignature : listNamesEmpty){
				sb.append(nameSignature);
				if(fields.size()>cont){
					sb.append(SEPARATOR);
				}								
			}
			
			return sb.toString();
		} catch (final Exception e) {
			LOGGER.severe("Error recuperando los nombres de campos de firma del PDF: " + e); 
			throw new PdfException("Error recuperando los nombres de campos de firma del pdf: " + e, e); 
		}
	}
	
	@Override
	public int numeroPaginasPDF(String filePath) throws PdfException {
		PdfReader pdfReader = null;
		try {
			pdfReader = new PdfReader(filePath);
		} catch (IOException e) {
			LOGGER.severe("Error anadiendo pagina en blanco al PDF: " + e); 
			 throw new PdfException("Error obteniendo el n&uacute;mero de p&aacute;ginas del documento PDF: " + e, e); 
		}
		return pdfReader.getNumberOfPages();
	}

	
	@Override
	public void anadirPaginaBlancaPDF(String filePath) throws PdfException {
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			final PdfReader pdfReader = new PdfReader(filePath);
			final Calendar cal = Calendar.getInstance();
			final PdfStamper stp = new PdfStamper(pdfReader, baos, cal);
			stp.insertPage(pdfReader.getNumberOfPages() + 1,
			pdfReader.getPageSizeWithRotation(1));
			stp.close(cal);
			pdfReader.close();
			final FileOutputStream os = new FileOutputStream(new File(filePath));
			os.write(baos.toByteArray());
			os.close();
		} catch (final Exception e) {
			LOGGER.severe("Error anadiendo pagina en blanco al PDF: " + e); 
			throw new PdfException("Error a&ntilde;adiendo pagina en blanco  al documento PDF: " + e, e); 
		}

	}

	@Override
	public String firmaBatch(String originalPath, String destinyPath, String xmlLook, String alias, String password,
			boolean solicitud) throws BadPdfPasswordException, PdfIsCertifiedException,
			PdfHasUnregisteredSignaturesException, PdfException, AOCertificatesNotFoundException {
		File folder = new File(originalPath);
		File folderDestiny = new File(destinyPath);
		File[] listOfFiles = folder.listFiles();
		int numeroDocumentos = 0;

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	String texto = listOfFiles[i].getName();
		        String origen = folder.getAbsolutePath().concat("\\").concat(listOfFiles[i].getName());
		        int pos = texto.lastIndexOf('.');
		        String salida = folderDestiny.getAbsolutePath().concat("\\").concat(
		        		(texto.substring(0,pos+1).concat("signed.").concat(texto.substring(pos+1))));
		        
		        firmaConSinSolicitud(origen,salida,null, null, xmlLook, solicitud, alias, password);
		        numeroDocumentos = i;
		      } 
		    }	
		return "el numero de documentos firmados es : "+ ((numeroDocumentos > 1)?numeroDocumentos+1:0);	
	}
	
	@Override
	public void firmaFinal(String originalPath, String destinyPath, String policyIdentifier, String fieldName,
			String xmlLook) throws AOCertificatesNotFoundException,
			BadPdfPasswordException, PdfIsCertifiedException, PdfHasUnregisteredSignaturesException, PdfException {

		firmaConSinSolicitud(originalPath,destinyPath,policyIdentifier, fieldName, xmlLook, false, null, null);
	}

	private void firmaConSinSolicitud(String originalPath, String destinyPath, String policyIdentifier, String fieldName,
			String xmlLook, boolean solicitud, String alias, String password) throws AOCertificatesNotFoundException,
			BadPdfPasswordException, PdfIsCertifiedException, PdfHasUnregisteredSignaturesException, PdfException {
		final AOSigner signer = AOSignerFactory.getSigner(AOSignConstants.SIGN_FORMAT_PADES);

		byte[] data = null;
		try ( final InputStream fis = new FileInputStream(new File(originalPath)); ) {
        	data = AOUtil.getDataFromInputStream(fis);
		}
		catch (final Exception e) {
			LOGGER.severe("Error leyendo fichero de entrada: " + e); 
			throw new PdfException("Error leyendo fichero de entrada: " + e, e); 
		}
		SignatureField field = null;
		if (fieldName != null && !fieldName.isEmpty()) {
			final List<SignatureField> list = PdfUtil.getPdfEmptySignatureFields(data);
			for (final SignatureField sf : list) {
				if (sf.getName().equals(fieldName)) {
					field = sf;
				}
			}
		}

		final Properties p = new Properties();

		PolicyIdFilter policyFilter = null;
		if (policyIdentifier != null && !policyIdentifier.isEmpty()) {
			policyFilter = new PolicyIdFilter(policyIdentifier);
			p.setProperty("policyIdentifier", policyIdentifier); 
		}
        final Properties prefProps = ExtraParamsHelper.loadPAdESExtraParams();
		
		ArrayList<CertificateFilter> filters = null;
		filters = new ArrayList<>();
		if (policyFilter != null) {
			filters.add(policyFilter);
		}
		filters.add(new KeyUsageFilter(KeyUsageFilter.SIGN_CERT_USAGE));
		PrivateKeyEntry pke = null;
        try {
        	if(alias.isEmpty() && password.isEmpty()){
        		pke = recuperaClavePrivada(filters);
        	}else{
        		pke = recuperaClavePrivada(filters,
        			alias, 
        			password,solicitud);        		
        	}
        }
        catch (final AOCancelledOperationException e) {
        	throw e; 
        }
        catch(final AOCertificatesNotFoundException e) {
        	LOGGER.severe("El almacen no contiene ningun certificado que se pueda usar para firmar: " + e); 
        	throw e;
        }
        catch (final Exception e) {
        	LOGGER.severe("Ocurrio un error al extraer la clave privada del certificiado seleccionado: " + e); 
        	throw new PdfException("Ocurrio un error al extraer la clave privada del certificiado seleccionado: " + e, e); 
    	}

        final String signatureAlgorithm = PreferencesManager.get(
    		PreferencesManager.PREFERENCE_GENERAL_SIGNATURE_ALGORITHM, "SHA512withRSA" 
		);

        try {
			new XMLLookUnmarsall(xmlLook, field, p, pke).parse();
		} catch (es.gob.afirma.mdef.pdf.XMLException e1) {
        	LOGGER.severe("No se han recuperado los valores del xml: " + e1); 
		}
        p.putAll(prefProps);
        final byte[] signResult;
        try (final FileOutputStream os = new FileOutputStream(new File(destinyPath))
        		){
            signResult = signer.sign(
        		data,
        		signatureAlgorithm,
        		pke.getPrivateKey(),
                pke.getCertificateChain(),
                p
            );
        	os.write(signResult);
        	os.close();
        }
        catch(final AOCancelledOperationException e) {
        	throw new AOCancelledOperationException("Cancelado por el usuario: " + e, e); 
        }
        catch(final PdfIsCertifiedException e) {
        	LOGGER.severe("PDF no firmado por estar certificado: " + e); 
        	throw e;
        }
        catch(final BadPdfPasswordException e) {
        	LOGGER.severe("PDF protegido con contrasena mal proporcionada: " + e); 
        	throw e;
        }
        catch(final PdfHasUnregisteredSignaturesException e) {
        	LOGGER.severe("PDF con firmas no registradas: " + e); 
        	throw e;
        }
        catch(final OutOfMemoryError ooe) {
            LOGGER.severe("Falta de memoria en el proceso de firma: " + ooe); 
            throw new OutOfMemoryError("Falta de memoria en el proceso de firma: " + ooe); 
        }
        catch(final Exception e) {
            LOGGER.severe("Error durante el proceso de firma: " + e); 
            throw new PdfException("Error durante el proceso de firma: " + e, e); 
        }

	}

	@Override
	public boolean verificarFirmasPDF(String filePath) throws PdfException {
		byte[] sign = null;
		try ( final FileInputStream fis = new FileInputStream(new File(filePath)) ) {
			sign = AOUtil.getDataFromInputStream(fis);
			PdfReader localPdfReader = new PdfReader(sign);
			AcroFields localAcroFields = localPdfReader.getAcroFields();			
			List localList = localAcroFields.getSignatureNames();
			if(localList.isEmpty()){
				return false;
			}
			return SignValiderFactory.getSignValider(sign).validate(sign).getValidity().equals(SIGN_DETAIL_TYPE.OK);
		}
		catch(final Exception e) {
			LOGGER.severe("Error validando la firma del PDF: " + e); 
			throw new PdfException("Error validando la firma del PDF: " + e, e); 
		}
	}

	@Override
	public PrivateKeyEntry recuperaClavePrivada(List<? extends CertificateFilter> filters)
			throws UnrecoverableEntryException, AOCertificatesNotFoundException, AOKeyStoreManagerException,
			KeyStoreException, NoSuchAlgorithmException, AOKeystoreAlternativeException, IOException {
		final AOKeyStore ks = AOKeyStore.TEMD;
		AOKeyStoreManager ksm;
			ksm = AOKeyStoreManagerFactory.getAOKeyStoreManager(
					ks,
					null,
					null,
					ks.getStorePasswordCallback(null),
					//pc,
					null);
		final AOKeyStoreDialog dialog = new AOKeyStoreDialog(
				ksm,
				null,
				true,             // Comprobar claves privadas
				false,            // Mostrar certificados caducados
				true,             // Comprobar validez temporal del certificado
				filters, 				// Filtros
				false             // mandatoryCertificate
			);
    	dialog.show();
    	ksm.setParentComponent(null);
    	return ksm.getKeyEntry(
			dialog.getSelectedAlias()
		);
	}

	
	public PrivateKeyEntry recuperaClavePrivada(List<? extends CertificateFilter> filters, String alias,String password, boolean solicitud) 
			throws AOKeyStoreManagerException, AOKeystoreAlternativeException, IOException, 
			KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException {
        final PasswordCallback pc = new PasswordCallback(">", false); 
        pc.setPassword(password.toCharArray());
		final AOKeyStore ks = AOKeyStore.TEMD;
		ks.getStorePasswordCallback(pc);
		AOKeyStoreManager ksm =  AOKeyStoreManagerFactory.getAOKeyStoreManager(
				ks,
				null,
				null,
				//ks.getStorePasswordCallback(null),
				pc,
				null);

		if(!solicitud){
			ksm.getType().getCertificatePasswordCallback(null).getPassword();

		}
		return ksm.getKeyEntry(alias);
		
	}
	
	@Override
	public void anadirCampoFirma(String filePath, int page, int leftX, int leftY, int rightX, int rightY)
			throws DocumentException, IOException {
		try (
				FileOutputStream fos = new FileOutputStream(createNameNewFile(filePath, "New"))
				) {
		int pageNbr = numeroPaginasPDF(filePath);
		if(page > pageNbr || page < pageNbr*-1) {
			throw new IllegalArgumentException("El numero de pagina no puede ser superior al numero total"); 
		}
		final PdfReader reader = new PdfReader(filePath);
			PdfStamper stamper = new PdfStamper(reader, fos, new GregorianCalendar());
			PdfFormField sig = PdfFormField.createSignature(stamper.getWriter()); 
			sig.setWidget(new Rectangle(leftX, leftY, rightX, rightY), null); 
			sig.setFlags(PdfAnnotation.FLAGS_PRINT); 
			sig.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g"));  
			sig.setFieldName("SIGNATURE");  
			int finalPage;
			if(page > 0) {
				finalPage = page;
			}
			else if(page < 0) {
				finalPage = pageNbr + page + 1;
			}
			else {
				finalPage = 1;
			}
			sig.setPage(finalPage); 
			stamper.addAnnotation(sig, finalPage); 
			stamper.close(new GregorianCalendar());
			final File oldPdf = new File(filePath);
			final File newPdf  = new File(createNameNewFile(filePath, "New"));
            oldPdf.delete(); 
            newPdf.renameTo(new File(filePath));			
		}
		catch (IOException e) {
			LOGGER.severe("PDF no existe: " + e);
			throw new IOException("El fichero " + filePath + " no existe: " + e);  //$NON-NLS-2$
		} catch (PdfException e) {
			LOGGER.severe("Error en las operaciones con el PDF: " + e);
		}
	}
	
	@Override
	public String cnTarjeta(List<? extends CertificateFilter> filters) 
			throws UnrecoverableEntryException, AOCertificatesNotFoundException, AOKeyStoreManagerException,
			KeyStoreException, NoSuchAlgorithmException, AOKeystoreAlternativeException, IOException{
		String cnTarjeta = null;
	
		final AOKeyStore ks = AOKeyStore.TEMD;
		AOKeyStoreManager ksm;
			ksm = AOKeyStoreManagerFactory.getAOKeyStoreManager(
					ks,
					null,
					null,
					ks.getStorePasswordCallback(null),
					null);		
//
			final AOKeyStoreDialog dialog = new AOKeyStoreDialog(
				ksm,
				null,
				false,             // Comprobar claves privadas
				false,            // Mostrar certificados caducados
				true,             // Comprobar validez temporal del certificado
				null, 				// Filtros
				false             // mandatoryCertificate
			);
		dialog.show();
		X509Certificate dest = null;
		ksm.setParentComponent(null);
		for (final String alias : ksm.getAliases()) {
			if (alias.equals(dialog.getSelectedAlias())) {
				dest = ksm.getCertificate(alias);
				break;
			}
		}
		
		if(null!= dest){
			cnTarjeta = AOUtil.getCN(dest);
		}
		return cnTarjeta;
	}
	

	private String createNameNewFile(String filePath, String modif){
		int position = filePath.indexOf('.');
		return filePath.substring(0 ,  position)+modif+filePath.substring(position,filePath.length());
	}
	
}
