package es.gob.afirma.mdef.pdf;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

public class XMLtestParse {

    private static final String XMLLOOK = "src/test/resources/XMLLook.xml";
    private static final String XMLLOOKWHITIMAGE = "src/test/resources/XMLLook.xml";
	
    @Test
    public void testParseoXML() throws Exception {
    	XMLLookParser xmlLookParser= new XMLLookParser(XMLLOOK, new Properties());
    	xmlLookParser.parse();
    	assertEquals(xmlLookParser.getProperties().getProperty("imagePage"),"2");
    	assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageLowerLeftX"),"24");
    	assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageLowerLeftY"),"13");
    	assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageUpperRightX"),"60");
    	assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageUpperRightY"),"27");
    	assertNotNull(xmlLookParser.getProperties().getProperty("signatureRubricImage"));    	
    	assertNotNull(xmlLookParser.getProperties().getProperty("signatureRubricImage"));    	
    	assertNotNull(xmlLookParser.getProperties().getProperty("signatureRubricImage"));    	
    }    

    //@Test
    public void testParseoXMLWhitImage() throws Exception {
    	XMLLookParser xmlLookParser= new XMLLookParser(XMLLOOK, new Properties());
    	xmlLookParser.parse();
    }    


}
