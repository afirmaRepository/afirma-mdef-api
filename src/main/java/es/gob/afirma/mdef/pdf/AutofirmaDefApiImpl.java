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
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.style.BCStyle;
import org.spongycastle.asn1.x500.style.IETFUtils;
import org.spongycastle.cert.jcajce.JcaX509CertificateHolder;
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
import es.gob.afirma.core.AOException;
import es.gob.afirma.core.misc.AOUtil;
import es.gob.afirma.core.misc.Base64;
import es.gob.afirma.core.signers.AOSignConstants;
import es.gob.afirma.core.signers.AOSigner;
import es.gob.afirma.core.signers.AOSignerFactory;
import es.gob.afirma.core.ui.AOUIFactory;
import es.gob.afirma.keystores.AOCertificatesNotFoundException;
import es.gob.afirma.keystores.AOKeyStore;
import es.gob.afirma.keystores.AOKeyStoreDialog;
import es.gob.afirma.keystores.AOKeyStoreManager;
import es.gob.afirma.keystores.AOKeyStoreManagerException;
import es.gob.afirma.keystores.AOKeyStoreManagerFactory;
import es.gob.afirma.keystores.AOKeystoreAlternativeException;
import es.gob.afirma.keystores.AggregatedKeyStoreManager;
import es.gob.afirma.keystores.filters.CertificateFilter;
import es.gob.afirma.keystores.filters.PolicyIdFilter;
import es.gob.afirma.local.BatchSigner;
import es.gob.afirma.signers.pades.BadPdfPasswordException;
import es.gob.afirma.signers.pades.PdfHasUnregisteredSignaturesException;
import es.gob.afirma.signers.pades.PdfIsCertifiedException;
import es.gob.afirma.signers.pades.PdfUtil;
import es.gob.afirma.signers.pades.PdfUtil.SignatureField;
import es.gob.afirma.standalone.SimpleAfirmaMessages;
import es.gob.afirma.standalone.SimpleKeyStoreManager;
import es.gob.afirma.standalone.ui.preferences.ExtraParamsHelper;
import es.gob.afirma.standalone.ui.preferences.PreferencesManager;
import nu.xom.XMLException;


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
			if(localList.size()>0){
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
	public String firmaBatch(String xmlPath, String alias, String password) throws Exception{
		// Conversion del fichero XML a bytes
		byte [] xmlBytes = null;
		File f = new File(xmlPath);
		xmlBytes = new byte[(int)f.length()]; 
		try (
				final FileInputStream fis = new FileInputStream(f); 
				){
			
			fis.read(xmlBytes);  
			if (xmlBytes.length < 1) {
				throw new IllegalArgumentException(
					"El XML de definicion de lote de firmas no puede ser nulo ni vacio" 
				);
			}
			
			AggregatedKeyStoreManager aksm;
		
			//Se obtiene el almacen de Windows (posibilidad de inicializar otro almacen)
			 aksm = AOKeyStoreManagerFactory.getAOKeyStoreManager(
					 AOKeyStore.WINDOWS, 
					 null, 
					 null, 
					 null, 
					 null
					);
			 
			 if(AOUIFactory.showConfirmDialog(
     				null,
     				SimpleAfirmaMessages.getString("Api.1"), 
     				SimpleAfirmaMessages.getString("Api.0"), 
     				JOptionPane.YES_NO_OPTION,
     				JOptionPane.WARNING_MESSAGE
     			) == 0) {
				 //Se obtiene la clave privada del almacen
				 PrivateKeyEntry pke = aksm.getKeyEntry(alias);
			 
				 //Se firma con la clave privada
				 return BatchSigner.sign(
						Base64.encode(xmlBytes), 
						pke.getCertificateChain(), 
						pke.getPrivateKey()
					);
			 }
		} catch (CertificateEncodingException | AOException e) {
			LOGGER.severe("Error durante la firma por lotes: " + e); 
			throw e;
		}
		catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
			LOGGER.severe("No se ha encontrado el alias en el almacen: " + e); 
			throw e;
		}
		catch (AOKeystoreAlternativeException e) {
			LOGGER.severe("No de ha podido inicializar el almacen de windows: " + e); 
			throw e;
		}
		//throw new AOCancelledOperationException("Acceso a la clave privada no permitido"); 
	
		return null;	
	}

	@Override
	public void firmaFinal(String originalPath, String destinyPath, String policyIdentifier, String fieldName,
			String xmlLook) throws XMLException, AOCertificatesNotFoundException,
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
		if (policyFilter != null) {
			filters = new ArrayList<>();
			filters.add(policyFilter);
		}
		PrivateKeyEntry pke = null;
        try {
            pke = recuperaClavePrivada(filters);
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
			e1.printStackTrace();
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
			if(!(localList.size()>0)){
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
			KeyStoreException, NoSuchAlgorithmException {
		final AOKeyStoreManager ksm = SimpleKeyStoreManager.getKeyStore(false, null);
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

	@Override
	public void anadirCampoFirma(String filePath, int page, int leftX, int leftY, int rightX, int rightY)
			throws DocumentException, IOException {
		try (
				//FileOutputStream fos = new FileOutputStream(filePath)
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
			e.printStackTrace();
			throw new IOException("El fichero " + filePath + " no existe: " + e);  //$NON-NLS-2$
		} catch (PdfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String cnTarjeta(List<? extends CertificateFilter> filters) 
			throws UnrecoverableEntryException, AOCertificatesNotFoundException, AOKeyStoreManagerException,
			KeyStoreException, NoSuchAlgorithmException{
		String cnTarjeta = null;
		final AOKeyStoreManager ksm = SimpleKeyStoreManager.getKeyStore(false, null);
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
		return filePath.substring(0 ,  filePath.indexOf("."))+modif+filePath.substring(filePath.indexOf("."),filePath.length());
	}
	
}
