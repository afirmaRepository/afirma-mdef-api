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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para RectType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="x0" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="y0" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="x1" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="y1" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RectType")
public class RectType {

    @XmlAttribute(name = "x0", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger x0;
    @XmlAttribute(name = "y0", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger y0;
    @XmlAttribute(name = "x1", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger x1;
    @XmlAttribute(name = "y1", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger y1;

    /**
     * Obtiene el valor de la propiedad x0.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getX0() {
        return x0;
    }

    /**
     * Define el valor de la propiedad x0.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setX0(BigInteger value) {
        this.x0 = value;
    }

    /**
     * Obtiene el valor de la propiedad y0.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getY0() {
        return y0;
    }

    /**
     * Define el valor de la propiedad y0.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setY0(BigInteger value) {
        this.y0 = value;
    }

    /**
     * Obtiene el valor de la propiedad x1.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getX1() {
        return x1;
    }

    /**
     * Define el valor de la propiedad x1.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setX1(BigInteger value) {
        this.x1 = value;
    }

    /**
     * Obtiene el valor de la propiedad y1.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getY1() {
        return y1;
    }

    /**
     * Define el valor de la propiedad y1.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setY1(BigInteger value) {
        this.y1 = value;
    }

}
