package es.gob.afirma.mdef.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;

public class XMLtestParse {

    private static final String XMLLOOKSIMENDEF = "src/test/resources/configPrueba2.xml";
	
    //@Test
    public void testParseoXML() throws Exception {
    	XMLLookParser xmlLookParser= new XMLLookParser(XMLLOOKSIMENDEF, new Properties());
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
    public void testParseoXMLSIMENDEF() throws Exception {
    	XMLLookParser xmlLookParser= new XMLLookParser(XMLLOOKSIMENDEF, new Properties());
    	xmlLookParser.parse();
    	System.out.println(xmlLookParser.getProperties());
    	assertEquals(xmlLookParser.getProperties().getProperty("imagePage"),"2"); 
		assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageUpperRightY"),"250"); 
		assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageUpperRightX"),"500"); 
		assertEquals(xmlLookParser.getProperties().getProperty("imagePositionOnPageLowerLeftY"),"50"); 
		assertEquals(xmlLookParser.getProperties().getProperty("imagePositionOnPageLowerLeftX"),"100"); 
		assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageLowerLeftY"),"50"); 
		assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageLowerLeftX"),"100"); 
		assertEquals(xmlLookParser.getProperties().getProperty("imagePositionOnPageUpperRightY"),"250"); 
		assertEquals(xmlLookParser.getProperties().getProperty("imagePositionOnPageUpperRightX"),"500");
    }    
    
    //@Test
    public void testParseoXMLSIMENDEF2() throws Exception {
    	XMLLookUnmarsall xmlLookParser= new XMLLookUnmarsall(XMLLOOKSIMENDEF, new Properties());
    	xmlLookParser.parse();
    	assertEquals(xmlLookParser.getProperties().getProperty("imagePage"),"2"); 
		assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageUpperRightY"),"250"); 
		assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageUpperRightX"),"500"); 
		assertEquals(xmlLookParser.getProperties().getProperty("imagePositionOnPageLowerLeftY"),"50"); 
		assertEquals(xmlLookParser.getProperties().getProperty("imagePositionOnPageLowerLeftX"),"100"); 
		assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageLowerLeftY"),"50"); 
		assertEquals(xmlLookParser.getProperties().getProperty("signaturePositionOnPageLowerLeftX"),"100"); 
		assertEquals(xmlLookParser.getProperties().getProperty("imagePositionOnPageUpperRightY"),"250"); 
		assertEquals(xmlLookParser.getProperties().getProperty("imagePositionOnPageUpperRightX"),"500");
		assertEquals(xmlLookParser.getProperties().getProperty("signatureProductionCity"),"Madrid");
		assertEquals(xmlLookParser.getProperties().getProperty("signReason"),"Soy el autor del documento");
		System.out.println(xmlLookParser.getProperties().getProperty("layer2Text"));
		//assertEquals(xmlLookParser.getProperties().getProperty("layer2FontColor"),"black");
    }    

    @Test
    public void testCompareImage() throws Exception {
    	XMLLookParser xmlLookParser= new XMLLookParser(XMLLOOKSIMENDEF, new Properties());
    	xmlLookParser.parse();
    	XMLLookUnmarsall xmlLookParser2= new XMLLookUnmarsall(XMLLOOKSIMENDEF, new Properties());
    	xmlLookParser2.parse();
    	assertEquals(xmlLookParser.getProperties().getProperty("signatureRubricImage"),
    			xmlLookParser.getProperties().getProperty("signatureRubricImage"));
    }

}
