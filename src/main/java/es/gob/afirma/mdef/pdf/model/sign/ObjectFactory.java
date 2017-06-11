//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.06.11 a las 11:06:16 PM CEST 
//


package es.gob.afirma.mdef.pdf.model.sign;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.gob.afirma.mdef.pdf.model.sign package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AfirmaConfig_QNAME = new QName("", "AfirmaConfig");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.gob.afirma.mdef.pdf.model.sign
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TextType }
     * 
     */
    public TextType createTextType() {
        return new TextType();
    }

    /**
     * Create an instance of {@link ImageType }
     * 
     */
    public ImageType createImageType() {
        return new ImageType();
    }

    /**
     * Create an instance of {@link SignatureInfosType }
     * 
     */
    public SignatureInfosType createSignatureInfosType() {
        return new SignatureInfosType();
    }

    /**
     * Create an instance of {@link PdfAttributesType }
     * 
     */
    public PdfAttributesType createPdfAttributesType() {
        return new PdfAttributesType();
    }

    /**
     * Create an instance of {@link AfirmaConfigType }
     * 
     */
    public AfirmaConfigType createAfirmaConfigType() {
        return new AfirmaConfigType();
    }

    /**
     * Create an instance of {@link TextItemType }
     * 
     */
    public TextItemType createTextItemType() {
        return new TextItemType();
    }

    /**
     * Create an instance of {@link FontType }
     * 
     */
    public FontType createFontType() {
        return new FontType();
    }

    /**
     * Create an instance of {@link RectType }
     * 
     */
    public RectType createRectType() {
        return new RectType();
    }

    /**
     * Create an instance of {@link PositionType }
     * 
     */
    public PositionType createPositionType() {
        return new PositionType();
    }

    /**
     * Create an instance of {@link AppearanceType }
     * 
     */
    public AppearanceType createAppearanceType() {
        return new AppearanceType();
    }

    /**
     * Create an instance of {@link ForegroundType }
     * 
     */
    public ForegroundType createForegroundType() {
        return new ForegroundType();
    }

    /**
     * Create an instance of {@link ArbitraryTextType }
     * 
     */
    public ArbitraryTextType createArbitraryTextType() {
        return new ArbitraryTextType();
    }

    /**
     * Create an instance of {@link TextType.Properties }
     * 
     */
    public TextType.Properties createTextTypeProperties() {
        return new TextType.Properties();
    }

    /**
     * Create an instance of {@link ImageType.ImageSize }
     * 
     */
    public ImageType.ImageSize createImageTypeImageSize() {
        return new ImageType.ImageSize();
    }

    /**
     * Create an instance of {@link SignatureInfosType.SignatureInfo }
     * 
     */
    public SignatureInfosType.SignatureInfo createSignatureInfosTypeSignatureInfo() {
        return new SignatureInfosType.SignatureInfo();
    }

    /**
     * Create an instance of {@link PdfAttributesType.Params }
     * 
     */
    public PdfAttributesType.Params createPdfAttributesTypeParams() {
        return new PdfAttributesType.Params();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AfirmaConfigType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AfirmaConfig")
    public JAXBElement<AfirmaConfigType> createAfirmaConfig(AfirmaConfigType value) {
        return new JAXBElement<AfirmaConfigType>(_AfirmaConfig_QNAME, AfirmaConfigType.class, null, value);
    }

}
