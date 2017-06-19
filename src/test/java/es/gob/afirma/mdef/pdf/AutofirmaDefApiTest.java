package es.gob.afirma.mdef.pdf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.security.KeyStore.Entry.Attribute;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.gob.afirma.keystores.filters.CertificateFilter;
import es.gob.afirma.keystores.filters.MultipleCertificateFilter;
import es.gob.afirma.keystores.filters.PseudonymFilter;
import es.gob.afirma.keystores.filters.rfc.KeyUsageFilter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:aplicationContext-mdef-api.xml"})
public class AutofirmaDefApiTest {

	private static final String PDF_FILE = "src/test/resources/Agenda_Codemotion 2016.pdf";
    private static final String PDF_FILE_TEST = "src/test/resources/Agenda_Codemotion 2016forTest.pdf";
    private static final String SING_PDF_FILE_NEW = "src/test/resources/Agenda Codemotion 2016_new_signed.pdf";
    private static final String SING_PDF_FILE = "src/test/resources/Agenda Codemotion 2016_signed.pdf";
    private static final String XMLLOOK = "src/test/resources/XMLLook.xml";
    private static final String XMLLOOKSIMENDEF = "src/test/resources/configPrueba2.xml";
	
    @Autowired
     private AutofirmaDefApi autofirmaDefApi;
    
    //copiamos el fichero que se va a utilzar para pruebas
    //en otro fichero para que este sea el mismo siempre en las pruebas
    @Before
    public void prepareTest() throws IOException {
    	File source = new File(PDF_FILE);
    	File dest = new File(PDF_FILE_TEST);
    	copyFilesForTest(source, dest);
    }
    
    //Se borra el fichero creado anteriormente para que la otra prueba comience de 0 otra vez
    @After
    public void terminateTest() throws IOException {
    	File dest = new File(PDF_FILE_TEST);
    	deleteFileForTest(dest);    	
    }
    
    //@Test
    public void shouldBeInjected() throws Exception {
        assertNotNull(autofirmaDefApi);
    }    
    
    //@Test
    //OK
    /*
     i.	Añadir página: Incorporación de una página en blanco al final de un documento PDF.
		1.	En la práctica, se envía a la herramienta el path y nombre de un fichero y ésta devolverá el 
		documento PDF con una página en blanco añadida. 
     */
    public void testAddBlankPage() throws Exception {
        int numPageBefore = autofirmaDefApi.numeroPaginasPDF(PDF_FILE_TEST);
    	autofirmaDefApi.anadirPaginaBlancaPDF(PDF_FILE_TEST);
        int numPageAfter = autofirmaDefApi.numeroPaginasPDF(PDF_FILE_TEST);
        assertEquals(numPageBefore+1,numPageAfter);
    }    

    //@Test   
    //OK
    /*
    ii.	Contar campos de firma: Obtención del número de campos de firma (vacíos o no) de un documento PDF. 
		1.	En la práctica, se envía a la herramienta el path y nombre de un fichero y ésta devolverá los campos de firma 
		(y adicionalmente los campos de firma en blanco) existentes en el documento. 
    */
	public void testEnumSignatureFieldNames() throws Exception{
    	assertEquals("Signature1"	,autofirmaDefApi.camposFirmaPDF(SING_PDF_FILE));
	}
    
    //@Test
	//OK
	/*
	 * iii.	Contar número de páginas: Obtención del número de páginas de un documento PDF.
	 */
    public void testPdfPageNumber() throws Exception {
        int numPage =autofirmaDefApi.numeroPaginasPDF(PDF_FILE_TEST);
        assertTrue(numPage==2);
    }    
   
    
    //@Test
    //OK
	/*
	 * iv.	Añadir campo de firma: Añadir un campo de firma vacío en cualquier página de un documento PDF, 
	 * especificando posición y tamaño del mismo.
	 */
    public void testAddSignField() throws Exception {
    	autofirmaDefApi.anadirCampoFirma(PDF_FILE_TEST, 2, 24, 13, 60, 27);
    	assertEquals("SIGNATURE",autofirmaDefApi.camposFirmaPDF(PDF_FILE_TEST));
    }   																	// PdfException;
    
    //@Test
    //funciona
    /*
     * v.	El interfaz de programación permitirá seleccionar el certificado de firma por su identificador
     *  de política de certificación (extensión CertificatePolicies).
     */
	public void testGetPrivateKeyEntry() throws Exception{
    	PrivateKeyEntry pke = autofirmaDefApi.recuperaClavePrivada(getCertFilters());
    	assertNotNull(pke);
    }

    //@Test
    //OK (se selecciona para la prueba la tarjeta PKI10)
    /*
     * vi.	Deberá proporcionar la funcionalidad para obtener el CN del certificado cargado 
     * de una tarjeta (almacén de certificados).
     */
	public void testGetCNCert() throws Exception{
		assertEquals("USUARIO PRUEBA PKI10 |X00000040", autofirmaDefApi.cnTarjeta(getCertFilters()));
	}

    @Test
    //no está
    /*
     * vii.	Firmar documento PDF: Firma de un documento PDF, con claves externas, con las siguientes características:
		1.	Podrá ser visible o invisible.
		2.	Podrá especificar un campo de firma vacío para inyectar en él la firma (sólo firma visible).
		3.	Se definirán los atributos de firma y de apariencia de firma a partir del certificado del firmante y de una estructura XML de configuración.
		a.	En la práctica, se envía a la herramienta el path y nombre de un fichero, el path y nombre del fichero destino 
		firmado, la política de certificación, el nombre del campo de firma, un booleano indicando si se requiere sello de tiempo, y un XML que 
		define la apariencia de firma; y devolverá una codificación de resultado.
     */
    public void testPdfSign() throws Exception{
    	autofirmaDefApi.firmaFinal(PDF_FILE, SING_PDF_FILE_NEW, null, null, XMLLOOKSIMENDEF);
    	assertTrue(autofirmaDefApi.verificarFirmasPDF(SING_PDF_FILE_NEW));
    }	
    
	
    //@Test
    //no está (no se como bunciona)
    /*
     * b.	Será posible firmar varios documentos PDF introduciendo sólo una vez el PIN de la tarjeta (Firma Masiva en Lotes de Autorización Única). Deberá implementarse un mecanismo por el cual se puedan agrupar las peticiones de firma en un lote, de forma que se procese el lote en el cliente sin necesidad de conexiones de red adicionales, invocaciones adicionales y conservando la sesión contra el almacén de claves y certificados. El firmante solo tendrá que autorizar una vez el acceso a su clave privada. La seguridad del acceso al almacén de claves debe ser completa, y deberá advertirse al usuario de que cuando autoriza al uso lo hace para múltiples ficheros. 
		i.	Todas las operaciones de firma deben permitir firmar sólo o firmar con sello de tiempo.
		ii.	Todas las operaciones de firma deben permitir volver a firmar un documento previamente firmado.
		c.	De igual manera, será posible firmar varios documentos PDF exigiendo el PIN para cada uno de ellos.
     */    
    public void testDoBatchSign() throws Exception{
		// Se obtiene el xml de la carpeta resources
		URL xmlURL = this.getClass().getResource("/xml/batch-with-countersign.xml"); //$NON-NLS-1$

		// Se cambia el %20 por espacio
		String filePath = xmlURL.getPath().replace("%20", " "); //$NON-NLS-1$ //$NON-NLS-2$
		
		// Alias y contrasena para firmar con el certificado autofirma.pfx
		String ret = autofirmaDefApi.firmaBatch(filePath, "USUARIO PRUEBA PKI10 |X00000040", "A111111a");  //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println(ret);
    	
    }

    //@Test
    //OK
    /*
     * d.	De deben poder verificar documentos PDF: Verifica las firmas de un documento PDF, informando sobre el número de firmas y un resultado de la verificación para cada una de ellas.
		i.	En la práctica, se envía a la herramienta el path y nombre de un fichero y ésta devolverá el resultado de la verificación.
     */
    public void testVerifySignature() throws Exception{
    	assertTrue(autofirmaDefApi.verificarFirmasPDF(SING_PDF_FILE));
    	assertFalse(autofirmaDefApi.verificarFirmasPDF(PDF_FILE_TEST));
    }
    
    
    
    /*
     * con esta función se pretende probar las política de certificación (extensión CertificatePolicies)
     * http://publib.boulder.ibm.com/tividd/td/IBM_TA/SH09-4532-01/es_ES/HTML/iausmst69.HTM
     * aqui se está probando la extensión estandar key usage
     */
	private List<? extends CertificateFilter> getCertFilters() {
		final List<CertificateFilter> filters = new ArrayList<>();
		filters.add(new KeyUsageFilter(KeyUsageFilter.SIGN_CERT_USAGE));
		filters.add(new PseudonymFilter());
		if (filters.size() > 1) {
			return Arrays.asList(
				new MultipleCertificateFilter(filters.toArray(new CertificateFilter[0]))
			);
		}
		else if (filters.size() == 1) {
			return filters;
		}
		return null;
	}
	
	private static void copyFilesForTest(File source, File dest) throws IOException {
		Files.copy(source.toPath(), dest.toPath());
	}	

	private static void deleteFileForTest(File source) throws IOException {
		source.delete();
	}	

}


