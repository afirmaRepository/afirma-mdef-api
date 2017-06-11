/* Copyright (C) 2011 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * Date: 11/01/11
 * You may contact the copyright holder at: soporte.afirma5@mpt.es
 */

package es.gob.afirma.standalone.ui.preferences;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/** Nombre de las preferencias de configuraci&oacute;n del programa.
 * @author Tom&aacute;s Garc&iacute;a-Mer&aacute;s */
public final class PreferencesManager {

	/** Objecto general de preferencias donde se guarda la configuraci&oacute;n de la
	 * aplicaci&oacute;n. */
	private static final Preferences preferences;
	static {
		preferences = Preferences.userNodeForPackage(PreferencesManager.class);
	}

	private PreferencesManager() {
		/** No permitimos la instanciacion */
	}
	
	/** Algoritmo de firma.
	 * Esta preferencia debe tener uno de estos valores:
	 * <ul>
	 *  <li>SHA1withRSA</li>
	 *  <li>SHA256withRSA</li>
	 *  <li>SHA384withRSA</li>
	 *  <li>SHA512withRSA</li>
	 * </ul> */
	public static final String PREFERENCE_GENERAL_SIGNATURE_ALGORITHM = "signatureAlgorithm"; //$NON-NLS-1$
	
	
	//**************** PREFERENCIAS DE SELLOS DE TIEMPO PADES*****************************************************************

	/** Indica si la configuraci&oacute;n de sello de tiempo est&aacute; activada. */
	public static final String PREFERENCE_PADES_TIMESTAMP_CONFIGURE = "isTimeStampConfiguredPades"; //$NON-NLS-1$

	/** Tipo de sello de tiempo a aplicar en las firmas PAdES.
	 * Debe contener un valor nu&eacute;rico entre uno y tres:
	 * <ul>
	 *  <li>1 = Solo sello a nivel de firma.</li>
     *  <li>2 = Solo sello a nivel de documento.</li>
     *  <li>3 = Dos sellos, uno a nivel de firma y otro a nivel de documento.</li>
     * </ul> */
	public static final String PREFERENCE_PADES_TIMESTAMP_STAMP_TYPE = "tsType"; //$NON-NLS-1$

	/** Algoritmo de huella digital a utilizar en sellos de tiempo para las firmas PAdES. */
	public static final String PREFERENCE_PADES_TIMESTAMP_HASHALGORITHM = "tsaHashAlgorithmsPades"; //$NON-NLS-1$

	/** URL de la autoridad de sello de tiempo para las firmas PAdES. */
	public static final String PREFERENCE_PADES_TIMESTAMP_TSA_URL = "tsaUrlPades"; //$NON-NLS-1$

	/** Pol&iacute;tica de sellado de tiempo para las firmas PAdES. */
	public static final String PREFERENCE_PADES_TIMESTAMP_STAMP_POLICY = "tsaPolicyPades"; //$NON-NLS-1$

	/** Nombre de usuario de la TSA para las firmas PAdES. */
	public static final String PREFERENCE_PADES_TIMESTAMP_TSA_USR = "tsaUsrPades"; //$NON-NLS-1$

	/** Contrase&ntilde;a del usuario de la TSA para las firmas PAdES. */
	public static final String PREFERENCE_PADES_TIMESTAMP_TSA_PWD = "tsaPwdPades"; //$NON-NLS-1$

	/** OID de la extensi&oacute;n a a&ntilde;adir a la petici&oacute;n al servidor de sello de tiempo (para las firmas PAdES). */
	public static final String PREFERENCE_PADES_TIMESTAMP_EXTENSION_OID = "tsaExtensionOidPades"; //$NON-NLS-1$

	/** Valor, en binario convertido a Base64, de la extensi&oacute;n a a&ntilde;adir a la petici&oacute;n al
	 * servidor de sello de tiempo (para las firmas PAdES). */
	public static final String PREFERENCE_PADES_TIMESTAMP_EXTENSION_VALUE = "tsaExtensionValueBase64Pades"; //$NON-NLS-1$

	/** Indica si se requiere el certificado de la TSA (para las firmas PAdES). */
	public static final String PREFERENCE_PADES_TIMESTAMP_CERT_REQUIRED = "isCertRequiredPades"; //$NON-NLS-1$

	/** Indica si la extensi&oacute;n indicada en <code>tsaExtensionOid</code> es cr&iacute;tica (para las firmas PAdES). */
	public static final String PREFERENCE_PADES_TIMESTAMP_OID_CRITICAL = "isExtensionCriticalPades"; //$NON-NLS-1$

	//**************** FIN PREFERENCIAS DE SELLOS DE TIEMPO PADES*****************************************************************


	
	/** Recupera el valor de una cadena de texto almacenada entre las preferencias de la
	 * aplicaci&oacute;n.
	 * @param key Clave del valor que queremos recuperar.
	 * @param def Valor que se devolver&aacute;a si la preferencia no se encontraba almacenada.
	 * @return La preferencia almacenada o {@code def} si no se encontr&oacute;. */
	public static String get(final String key, final String def) {
		return preferences.get(key, def);
	}

	/** Recupera el valor {@code true} o {@code false} almacenado entre las preferencias de la
	 * aplicaci&oacute;n.
	 * @param key Clave del valor que queremos recuperar.
	 * @param def Valor que se devolver&aacute;a si la preferencia no se encontraba almacenada.
	 * @return La preferencia almacenada o {@code def} si no se encontr&oacute;. */
	public static boolean getBoolean(final String key, final boolean def) {
		return preferences.getBoolean(key, def);
	}

	/** Recupera el valor {@code true} o {@code false} almacenado entre las preferencias de la
	 * aplicaci&oacute;n.
	 * @param key Clave del valor que queremos recuperar.
	 * @param def Valor que se devolver&aacute;a si la preferencia no se encontraba almacenada.
	 * @return La preferencia almacenada o {@code def} si no se encontr&oacute;. */
	public static byte[] getByteArray(final String key, final byte[] def) {
		return preferences.getByteArray(key, def);
	}
	

	/** Establece una cadena de texto en la configuraci&oacute;n de la aplicaci&oacute;n
	 * identific&aacute;ndola con una clave. Para realizar el guardado completo, es
	 * necesario ejecutar el m&eacute;todo {@code flush()}.
	 * @param key Clave con la que identificaremos el valor.
	 * @param value Valor que se desea almacenar. */
	public static void put(final String key, final String value) {
		preferences.put(key, value);
	}

	/** Establece un {@code true} o {@code false} en la configuraci&oacute;n de la aplicaci&oacute;n
	 * identific&aacute;ndolo con una clave. Para realizar el guardado completo, es
	 * necesario ejecutar el m&eacute;todo {@code flush()}.
	 * @param key Clave con la que identificaremos el valor.
	 * @param value Valor que se desea almacenar. */
	public static void putByteArray(final String key, final byte[] value) {
		preferences.putByteArray(key, value);
	}

	/** Establece un {@code true} o {@code false} en la configuraci&oacute;n de la aplicaci&oacute;n
	 * identific&aacute;ndolo con una clave. Para realizar el guardado completo, es
	 * necesario ejecutar el m&eacute;todo {@code flush()}.
	 * @param key Clave con la que identificaremos el valor.
	 * @param value Valor que se desea almacenar. */
	public static void putBoolean(final String key, final boolean value) {
		preferences.putBoolean(key, value);
	}

	/** Elimina una clave de entre la configuraci&oacute;n de la aplicaci&oacute;n.
	 * @param key Clave que eliminar. */
	public static void remove(final String key) {
		preferences.remove(key);
	}

	/**
	 * Elimina todas las preferencias de la aplicaci&oacute;n.
	 * @throws BackingStoreException Si ocurre un error eliminando las preferencias.
	 */
	public static void clearAll() throws BackingStoreException {
		preferences.clear();
	}

	/** Almacena en las preferencias de la aplicaci&oacute;n todos los valores
	 * establecidos hasta el momento.
	 * @throws BackingStoreException Cuando ocurre un error durante el guardado. */
	public static void flush() throws BackingStoreException {
		preferences.flush();
	}
}
