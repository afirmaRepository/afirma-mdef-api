//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.05.31 a las 10:36:20 AM CEST 
//


package es.gob.afirma.mdef.pdf.model.sign;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para imageEncodingType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="imageEncodingType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="base64"/>
 *     &lt;enumeration value="uri"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "imageEncodingType")
@XmlEnum
public enum ImageEncodingType {

    @XmlEnumValue("base64")
    BASE_64("base64"),
    @XmlEnumValue("uri")
    URI("uri");
    private final String value;

    ImageEncodingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ImageEncodingType fromValue(String v) {
        for (ImageEncodingType c: ImageEncodingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
