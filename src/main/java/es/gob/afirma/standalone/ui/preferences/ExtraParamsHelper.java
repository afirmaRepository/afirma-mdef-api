package es.gob.afirma.standalone.ui.preferences;

import java.util.Properties;



public final class ExtraParamsHelper {


	/** Obtiene la configuraci&oacute;n para las firmas PAdES.
	 * @return Propiedades para la configuraci&oacute;n de las firmas PAdES. */
	public static Properties loadPAdESExtraParams() {

		final Properties p = new Properties();


        if(PreferencesManager.getBoolean(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_CONFIGURE, true)){
            /** URL de la autoridad sello de tiempo (si no se indica no se a&ntilde;ade sello de tiempo). */
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_TSA_URL, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsaURL", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_TSA_URL, "") //$NON-NLS-1$
    			);
            }
            
            /** Tipo de sello de tiempo a aplicar:
             * <ul>
        	 *  <li>1 = Solo sello a nivel de firma.</li>
        	 *  <li>2 = Solo sello a nivel de documento.</li>
        	 *  <li>3 = Dos sellos, uno a nivel de firma y otro a nivel de documento.</li>
        	 * </ul> */     
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_STAMP_TYPE, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsType", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_STAMP_TYPE, "") //$NON-NLS-1$
    			);
            }

            /** Pol&iacute;tica de sellado de tiempo. */
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_STAMP_POLICY, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsaPolicy", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_STAMP_POLICY, "") //$NON-NLS-1$
    			);
            }
            
            /** Algoritmo de huella digital a usar para el sello de tiempo (si no se establece se usa SHA-1). */
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_HASHALGORITHM, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsaHashAlgorithm", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_HASHALGORITHM, "") //$NON-NLS-1$
    			);
            }
            
            /** Contrase&ntilde;a del usuario de la TSA. Se ignora si no se ha establecido adem&aacute;s <code>tsaUsr</code>. */
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_TSA_USR, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsaUsr", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_TSA_USR, "") //$NON-NLS-1$
    			);
            }
            
            /** Contrase&ntilde;a del usuario de la TSA. Se ignora si no se ha establecido adem&aacute;s <code>tsaUsr</code>. */
            if (!PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_TSA_PWD, "").trim().isEmpty()) {  //$NON-NLS-1$
            	p.put(
        			"tsaPwd", //$NON-NLS-1$
        			PreferencesManager.get(PreferencesManager.PREFERENCE_PADES_TIMESTAMP_TSA_PWD, "") //$NON-NLS-1$
    			);
            }
            
            return p;
        	
        }else{
            /** URL de la autoridad de sello de tiempo (si no se indica no se a&ntilde;ade sello de tiempo). */
        	p.remove("tsaURL");
            
            /** Tipo de sello de tiempo a aplicar:
             * <ul>
        	 *  <li>1 = Solo sello a nivel de firma.</li>
        	 *  <li>2 = Solo sello a nivel de documento.</li>
        	 *  <li>3 = Dos sellos, uno a nivel de firma y otro a nivel de documento.</li>
        	 * </ul> */     
        	p.remove("tsType"); //$NON-NLS-1$

            /** Pol&iacute;tica de sellado de tiempo. */
        	p.remove("tsaPolicy"); //$NON-NLS-1$
            
            /** Algoritmo de huella digital a usar para el sello de tiempo (si no se establece se usa SHA-1). */
        	p.remove("tsaHashAlgorithm"); //$NON-NLS-1$
            /** Contrase&ntilde;a del usuario de la TSA. Se ignora si no se ha establecido adem&aacute;s <code>tsaUsr</code>. */
        	p.remove("tsaUsr"); //$NON-NLS-1$
            /** Contrase&ntilde;a del usuario de la TSA. Se ignora si no se ha establecido adem&aacute;s <code>tsaUsr</code>. */
        	p.remove("tsaPwd"); //$NON-NLS-1$
            
            return p;
        	
        }

	}

}
