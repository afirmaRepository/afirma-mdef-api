//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.02.06 a las 11:56:43 AM CET 
//


package es.gob.afirma.mdef.pdf.model.sign;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para TextType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TextType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="properties" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="color" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="fontSize" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="position" type="{}PositionType" minOccurs="0"/>
 *         &lt;element name="SignatureInfos" type="{}SignatureInfosType" minOccurs="0"/>
 *         &lt;element name="ArbitraryText" type="{}ArbitraryTextType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextType", propOrder = {
    "properties",
    "position",
    "signatureInfos",
    "arbitraryText"
})
public class TextType {

    protected TextType.Properties properties;
    protected PositionType position;
    @XmlElement(name = "SignatureInfos")
    protected SignatureInfosType signatureInfos;
    @XmlElement(name = "ArbitraryText")
    protected ArbitraryTextType arbitraryText;

    /**
     * Obtiene el valor de la propiedad properties.
     * 
     * @return
     *     possible object is
     *     {@link TextType.Properties }
     *     
     */
    public TextType.Properties getProperties() {
        return properties;
    }

    /**
     * Define el valor de la propiedad properties.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType.Properties }
     *     
     */
    public void setProperties(TextType.Properties value) {
        this.properties = value;
    }

    /**
     * Obtiene el valor de la propiedad position.
     * 
     * @return
     *     possible object is
     *     {@link PositionType }
     *     
     */
    public PositionType getPosition() {
        return position;
    }

    /**
     * Define el valor de la propiedad position.
     * 
     * @param value
     *     allowed object is
     *     {@link PositionType }
     *     
     */
    public void setPosition(PositionType value) {
        this.position = value;
    }

    /**
     * Obtiene el valor de la propiedad signatureInfos.
     * 
     * @return
     *     possible object is
     *     {@link SignatureInfosType }
     *     
     */
    public SignatureInfosType getSignatureInfos() {
        return signatureInfos;
    }

    /**
     * Define el valor de la propiedad signatureInfos.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureInfosType }
     *     
     */
    public void setSignatureInfos(SignatureInfosType value) {
        this.signatureInfos = value;
    }

    /**
     * Obtiene el valor de la propiedad arbitraryText.
     * 
     * @return
     *     possible object is
     *     {@link ArbitraryTextType }
     *     
     */
    public ArbitraryTextType getArbitraryText() {
        return arbitraryText;
    }

    /**
     * Define el valor de la propiedad arbitraryText.
     * 
     * @param value
     *     allowed object is
     *     {@link ArbitraryTextType }
     *     
     */
    public void setArbitraryText(ArbitraryTextType value) {
        this.arbitraryText = value;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="color" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="fontSize" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Properties {

        @XmlAttribute(name = "color", required = true)
        protected String color;
        @XmlAttribute(name = "fontSize", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger fontSize;

        /**
         * Obtiene el valor de la propiedad color.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getColor() {
            return color;
        }

        /**
         * Define el valor de la propiedad color.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setColor(String value) {
            this.color = value;
        }

        /**
         * Obtiene el valor de la propiedad fontSize.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getFontSize() {
            return fontSize;
        }

        /**
         * Define el valor de la propiedad fontSize.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setFontSize(BigInteger value) {
            this.fontSize = value;
        }

    }

}
