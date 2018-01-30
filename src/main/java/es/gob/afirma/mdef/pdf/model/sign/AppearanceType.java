//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.19 a las 01:22:46 PM CET 
//


package es.gob.afirma.mdef.pdf.model.sign;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para AppearanceType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="AppearanceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Rect" type="{}RectType" minOccurs="0"/>
 *         &lt;element name="Foreground" type="{}ForegroundType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppearanceType", propOrder = {
    "rect",
    "foreground"
})
public class AppearanceType {

    @XmlElement(name = "Rect")
    protected RectType rect;
    @XmlElement(name = "Foreground")
    protected ForegroundType foreground;

    /**
     * Obtiene el valor de la propiedad rect.
     * 
     * @return
     *     possible object is
     *     {@link RectType }
     *     
     */
    public RectType getRect() {
        return rect;
    }

    /**
     * Define el valor de la propiedad rect.
     * 
     * @param value
     *     allowed object is
     *     {@link RectType }
     *     
     */
    public void setRect(RectType value) {
        this.rect = value;
    }

    /**
     * Obtiene el valor de la propiedad foreground.
     * 
     * @return
     *     possible object is
     *     {@link ForegroundType }
     *     
     */
    public ForegroundType getForeground() {
        return foreground;
    }

    /**
     * Define el valor de la propiedad foreground.
     * 
     * @param value
     *     allowed object is
     *     {@link ForegroundType }
     *     
     */
    public void setForeground(ForegroundType value) {
        this.foreground = value;
    }

}
