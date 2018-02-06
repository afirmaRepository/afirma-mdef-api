package es.gob.afirma.mdef.pdf;
import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:aplicationContext-mdef-api.xml"})
public class AutofirmaDefApiBatchPassTest {


    private static final String XMLLOOKSIMENDEF = "src/test/resources/configPrueba2.xml";
	private static final String PDF_FILES_IN = "src/test/resources/batch/in";
	private static final String PDF_FILES_OUT = "src/test/resources/batch/out";
	
    @Autowired
     private AutofirmaDefApi autofirmaDefApi;

    @Test
    //OK
    /*
     * b.	Será posible firmar varios documentos PDF introduciendo sólo una vez el PIN de la tarjeta (Firma Masiva en Lotes de Autorización Única). 
     * Deberá implementarse un mecanismo por el cual se puedan agrupar las peticiones de firma en un lote, de forma que se procese el lote en el cliente sin necesidad 
     * de conexiones de red adicionales, invocaciones adicionales y conservando la sesión contra el almacén de claves y certificados. 
     * El firmante solo tendrá que autorizar una vez el acceso a su clave privada. La seguridad del acceso al almacén de claves debe ser completa, 
     * y deberá advertirse al usuario de que cuando autoriza al uso lo hace para múltiples ficheros. 
		i.	Todas las operaciones de firma deben permitir firmar sólo o firmar con sello de tiempo.
		ii.	Todas las operaciones de firma deben permitir volver a firmar un documento previamente firmado.
		c.	De igual manera, será posible firmar varios documentos PDF exigiendo el PIN para cada uno de ellos.
     */    
    public void testDoBatchSign() throws Exception{
    	File resourcesDirectoryIn = new File(PDF_FILES_IN);
    	String inDirectory = resourcesDirectoryIn.getAbsolutePath();
    	File resourcesDirectoryOut = new File(PDF_FILES_OUT);
    	String OutDirectory = resourcesDirectoryOut.getAbsolutePath();

    	String ret = autofirmaDefApi.firmaBatch(inDirectory, OutDirectory,XMLLOOKSIMENDEF,
    			"USUARIO PRUEBA PKI17 |X00000047/cn=defensa-ec-wpg2016,2.5.4.97=#0c0f56415445532d533238303032333149,ou=pki,o=ministerio de defensa,c=es/97475372309037932331773164525720753402",
				//"USUARIO PRUEBA PKI19 |X00000049/cn=defensa-ec-wpg2016,2.5.4.97=#0c0f56415445532d533238303032333149,ou=pki,o=ministerio de defensa,c=es/59449110274331648111628035804245974603",
				"A111111a",
				false);  
        System.out.println(ret);
    	
    }

}


