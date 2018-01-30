//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.19 a las 01:22:46 PM CET 
//


package es.gob.afirma.mdef.pdf.model.sign;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SignatureInfoID.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="SignatureInfoID">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Subject"/>
 *     &lt;enumeration value="Issuer"/>
 *     &lt;enumeration value="SerialNumber"/>
 *     &lt;enumeration value="Reason"/>
 *     &lt;enumeration value="Location"/>
 *     &lt;enumeration value="ContactInfo"/>
 *     &lt;enumeration value="Date"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SignatureInfoID")
@XmlEnum
public enum SignatureInfoID {

    @XmlEnumValue("Subject")
    SUBJECT("Subject"),
    @XmlEnumValue("Issuer")
    ISSUER("Issuer"),
    @XmlEnumValue("SerialNumber")
    SERIAL_NUMBER("SerialNumber"),
    @XmlEnumValue("Reason")
    REASON("Reason"),
    @XmlEnumValue("Location")
    LOCATION("Location"),
    @XmlEnumValue("ContactInfo")
    CONTACT_INFO("ContactInfo"),
    @XmlEnumValue("Date")
    DATE("Date");
    private final String value;

    SignatureInfoID(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SignatureInfoID fromValue(String v) {
        for (SignatureInfoID c: SignatureInfoID.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
