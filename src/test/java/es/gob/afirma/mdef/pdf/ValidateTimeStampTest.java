package es.gob.afirma.mdef.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import com.sun.jna.platform.win32.Sspi.TimeStamp;

import es.gob.afirma.mdef.pdf.analyzer.TimestampsAnalyzer;

public class ValidateTimeStampTest {

	private static final String PDF_FILE = "src/test/resources/Agenda Codemotion 2016_Timestamp.pdf";
	
    @Test
    public void getTimeStamp() throws Exception {
    	File file = new File(PDF_FILE);
    	byte[] byteArray = new byte[(int) file.length()];
    	FileInputStream fis = new FileInputStream(file);
    	fis.read(byteArray);
    	fis.close();
        assertEquals(TimestampsAnalyzer.getTimestamps(byteArray).size(),1);
    }    
	
}
