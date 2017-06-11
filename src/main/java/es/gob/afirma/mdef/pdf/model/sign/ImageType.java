//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.06.11 a las 11:06:16 PM CEST 
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
 * <p>Clase Java para ImageType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ImageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="imageSize" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="width" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                 &lt;attribute name="height" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="position" type="{}PositionType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="encodeType" use="required" type="{}imageEncodingType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageType", propOrder = {
    "data",
    "imageSize",
    "position"
})
public class ImageType {

    @XmlElement(required = true)
    protected String data;
    protected ImageType.ImageSize imageSize;
    protected PositionType position;
    @XmlAttribute(name = "encodeType", required = true)
    protected ImageEncodingType encodeType;

    /**
     * Obtiene el valor de la propiedad data.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getData() {
        return data;
    }

    /**
     * Define el valor de la propiedad data.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(String value) {
        this.data = value;
    }

    /**
     * Obtiene el valor de la propiedad imageSize.
     * 
     * @return
     *     possible object is
     *     {@link ImageType.ImageSize }
     *     
     */
    public ImageType.ImageSize getImageSize() {
        return imageSize;
    }

    /**
     * Define el valor de la propiedad imageSize.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageType.ImageSize }
     *     
     */
    public void setImageSize(ImageType.ImageSize value) {
        this.imageSize = value;
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
     * Obtiene el valor de la propiedad encodeType.
     * 
     * @return
     *     possible object is
     *     {@link ImageEncodingType }
     *     
     */
    public ImageEncodingType getEncodeType() {
        return encodeType;
    }

    /**
     * Define el valor de la propiedad encodeType.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageEncodingType }
     *     
     */
    public void setEncodeType(ImageEncodingType value) {
        this.encodeType = value;
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
     *       &lt;attribute name="width" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *       &lt;attribute name="height" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ImageSize {

        @XmlAttribute(name = "width", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger width;
        @XmlAttribute(name = "height", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger height;

        /**
         * Obtiene el valor de la propiedad width.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getWidth() {
            return width;
        }

        /**
         * Define el valor de la propiedad width.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setWidth(BigInteger value) {
            this.width = value;
        }

        /**
         * Obtiene el valor de la propiedad height.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getHeight() {
            return height;
        }

        /**
         * Define el valor de la propiedad height.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setHeight(BigInteger value) {
            this.height = value;
        }

    }

}
