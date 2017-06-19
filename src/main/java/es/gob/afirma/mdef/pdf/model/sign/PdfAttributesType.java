//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.06.16 a las 02:19:03 PM CEST 
//


package es.gob.afirma.mdef.pdf.model.sign;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para PdfAttributesType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PdfAttributesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signaturePosition" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="params" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="contactInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PdfAttributesType", propOrder = {
    "signaturePosition",
    "params"
})
public class PdfAttributesType {

    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger signaturePosition;
    protected PdfAttributesType.Params params;

    /**
     * Obtiene el valor de la propiedad signaturePosition.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSignaturePosition() {
        return signaturePosition;
    }

    /**
     * Define el valor de la propiedad signaturePosition.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSignaturePosition(BigInteger value) {
        this.signaturePosition = value;
    }

    /**
     * Obtiene el valor de la propiedad params.
     * 
     * @return
     *     possible object is
     *     {@link PdfAttributesType.Params }
     *     
     */
    public PdfAttributesType.Params getParams() {
        return params;
    }

    /**
     * Define el valor de la propiedad params.
     * 
     * @param value
     *     allowed object is
     *     {@link PdfAttributesType.Params }
     *     
     */
    public void setParams(PdfAttributesType.Params value) {
        this.params = value;
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
     *       &lt;sequence>
     *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="contactInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "location",
        "reason",
        "contactInfo"
    })
    public static class Params {

        protected String location;
        protected String reason;
        protected String contactInfo;

        /**
         * Obtiene el valor de la propiedad location.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLocation() {
            return location;
        }

        /**
         * Define el valor de la propiedad location.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLocation(String value) {
            this.location = value;
        }

        /**
         * Obtiene el valor de la propiedad reason.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReason() {
            return reason;
        }

        /**
         * Define el valor de la propiedad reason.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReason(String value) {
            this.reason = value;
        }

        /**
         * Obtiene el valor de la propiedad contactInfo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContactInfo() {
            return contactInfo;
        }

        /**
         * Define el valor de la propiedad contactInfo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContactInfo(String value) {
            this.contactInfo = value;
        }

    }

}
