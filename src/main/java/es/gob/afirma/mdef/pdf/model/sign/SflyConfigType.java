//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.05.31 a las 10:36:20 AM CEST 
//


package es.gob.afirma.mdef.pdf.model.sign;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SflyConfigType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SflyConfigType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PdfAttributes" type="{}PdfAttributesType"/>
 *         &lt;element name="Appearance" type="{}AppearanceType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SflyConfigType", propOrder = {
    "pdfAttributes",
    "appearance"
})
public class SflyConfigType {

    @XmlElement(name = "PdfAttributes", required = true)
    protected PdfAttributesType pdfAttributes;
    @XmlElement(name = "Appearance")
    protected AppearanceType appearance;

    /**
     * Obtiene el valor de la propiedad pdfAttributes.
     * 
     * @return
     *     possible object is
     *     {@link PdfAttributesType }
     *     
     */
    public PdfAttributesType getPdfAttributes() {
        return pdfAttributes;
    }

    /**
     * Define el valor de la propiedad pdfAttributes.
     * 
     * @param value
     *     allowed object is
     *     {@link PdfAttributesType }
     *     
     */
    public void setPdfAttributes(PdfAttributesType value) {
        this.pdfAttributes = value;
    }

    /**
     * Obtiene el valor de la propiedad appearance.
     * 
     * @return
     *     possible object is
     *     {@link AppearanceType }
     *     
     */
    public AppearanceType getAppearance() {
        return appearance;
    }

    /**
     * Define el valor de la propiedad appearance.
     * 
     * @param value
     *     allowed object is
     *     {@link AppearanceType }
     *     
     */
    public void setAppearance(AppearanceType value) {
        this.appearance = value;
    }

}
