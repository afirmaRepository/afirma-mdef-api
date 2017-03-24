package es.gob.afirma.mdef.pdf;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:aplicationContext-mdef-api.xml"})
public class KeyOneApiTest {

    private static final String PDF_FILE = "\\pdf-test.pdf";
	
    @Autowired
     private KeyOneApi keyOneApi;
    
    
    @Test
    public void shouldBeInjected() throws Exception {
        assertNotNull(keyOneApi);
    }    
    

    @Test
    public void TestPdfPageNumber() throws Exception {
        int numPage =keyOneApi.getPdfPageNumber(PDF_FILE);
        System.out.println("numero de hojas"+numPage);
    }    
   
    @Test
    public void TestAddBlankPage() throws Exception {
        keyOneApi.addBlankPage("C:\\temp\\pdf-test.pdf");
        int numPage =keyOneApi.getPdfPageNumber(PDF_FILE);
        System.out.println("numero de hojas tras añadirle una en blanco"+numPage);
    }    
    
}
