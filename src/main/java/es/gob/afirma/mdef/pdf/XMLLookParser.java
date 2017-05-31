package es.gob.afirma.mdef.pdf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.gob.afirma.core.misc.Base64;
import es.gob.afirma.mdef.pdf.model.sign.AppearanceType;
import es.gob.afirma.mdef.pdf.model.sign.ArbitraryTextType;
import es.gob.afirma.mdef.pdf.model.sign.BackgroundType;
import es.gob.afirma.mdef.pdf.model.sign.ForegroundType;
import es.gob.afirma.mdef.pdf.model.sign.ImageEncodingType;
import es.gob.afirma.mdef.pdf.model.sign.ImageType;
import es.gob.afirma.mdef.pdf.model.sign.ObjectFactory;
import es.gob.afirma.mdef.pdf.model.sign.PdfAttributesType;
import es.gob.afirma.mdef.pdf.model.sign.RectType;
import es.gob.afirma.mdef.pdf.model.sign.SflyConfigType;
import es.gob.afirma.mdef.pdf.model.sign.SignatureInfosType;
import es.gob.afirma.mdef.pdf.model.sign.SignatureInfosType.SignatureInfo;
import es.gob.afirma.mdef.pdf.model.sign.TextItemType;
import es.gob.afirma.mdef.pdf.model.sign.TextType;
import es.gob.afirma.signers.pades.PdfUtil.SignatureField;
import es.gob.afirma.standalone.ui.pdf.ColorResource;
import es.gob.afirma.standalone.ui.pdf.SignPdfUiPanelPreview;

/**
 * Parser del XML.
 * 
 * @author Sergio Mart&iacute;nez Rico.
 */
public final class XMLLookParser {

	static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$
	private final String xml;
	private BufferedImage image;
	private int rectWidth = 0;
	private int rectHeight = 0;
	private String location;
	private String reason;
	private String contactInfo;
	private final SignatureField field;
	private final X509Certificate cer;
	private final Properties prop;

	/**
	 * @return Propiedades del documento XML.
	 */
	public Properties getProperties() {
		return this.prop;
	}

	/**
	 * Parsa el documento XML recibido.
	 * 
	 * @param xml
	 *            Ruta del documento XML.
	 * @param field
	 *            Campo a obtener.
	 * @param p
	 *            Propiedades
	 * @param pke
	 *            Clave privada.
	 */
	public XMLLookParser(final String xml, final SignatureField field, final Properties p, final PrivateKeyEntry pke) {
		this.xml = xml;
		this.image = null;
		this.prop = p;
		this.field = field;
		this.cer = (X509Certificate) pke.getCertificate();
	}

	public XMLLookParser(final String xml, final Properties p) {
		this.xml = xml;
		this.image = null;
		this.prop = p;
		this.field = null;
		this.cer = null;
	}

	/** Parsa el documento XML recibido.
	 * @return Propiedades del documento.
	 * @throws XMLException Error en la lectura del fichero XML.
	 */
	public Properties parse() throws XMLException {
		if (this.xml == null) {
			return null;
		}

		try(final InputStream in = new ByteArrayInputStream(this.xml.getBytes(StandardCharsets.UTF_8))) {
			final FileInputStream fis = new FileInputStream(this.xml);
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			SflyConfigType sflyConfig = (SflyConfigType)((JAXBElement<SflyConfigType>) jaxbUnmarshaller.unmarshal(fis)).getValue();
			
			if (this.field == null) {
				final PdfAttributesType page = sflyConfig.getPdfAttributes();
				if (page != null) {
					this.prop.setProperty("imagePage", page.getSignaturePosition().toString()); //$NON-NLS-1$
				}
				final AppearanceType appearance = sflyConfig.getAppearance();
				if (null != appearance){
					RectType rect =appearance.getRect();
					if(null != rect){
						setRectProp(rect);
					}
				}
			}
			else {
				this.prop.setProperty("signatureField", this.field.getName()); //$NON-NLS-1$
				this.rectWidth = this.field.getSignaturePositionOnPageUpperRightX() - this.field.getSignaturePositionOnPageLowerLeftX();
				this.rectHeight = this.field.getSignaturePositionOnPageUpperRightY() - this.field.getSignaturePositionOnPageLowerLeftY();
			}

			if (null != sflyConfig.getPdfAttributes()) {
				setParams(sflyConfig.getPdfAttributes());
			}
			
			if(null != sflyConfig.getAppearance()){
				final AppearanceType appearance = sflyConfig.getAppearance();
				if(null != appearance.getBackground()){
					setBackgroundImage(appearance.getBackground());
				}
				if(null != appearance.getForeground()){
					setForeground(appearance.getForeground());
				}
			}
				
			if (this.image != null) {
				this.prop.setProperty("signatureRubricImage", getImageInBase64(this.image)); //$NON-NLS-1$
			}
			return this.prop;
		}
		catch (final Exception e) {
			LOGGER.severe("Error analizando el xml de apariencia: " + e); //$NON-NLS-1$
			throw new XMLException("Error analizando el xml de apariencia: " + e, e); //$NON-NLS-1$
		}
	}

	private void setRectProp(final RectType rect) {
		if (null != rect.getX0() && null != rect.getY0() && null != rect.getX1() && null != rect.getY1()) {
			this.prop.setProperty("signaturePositionOnPageLowerLeftX", rect.getX0().toString());
			this.prop.setProperty("signaturePositionOnPageLowerLeftY", rect.getY0().toString());
			this.prop.setProperty("signaturePositionOnPageUpperRightX", rect.getX1().toString());
			this.prop.setProperty("signaturePositionOnPageUpperRightY", rect.getY1().toString());
			this.rectWidth = rect.getX1().intValue() - rect.getX0().intValue();
			this.rectHeight = rect.getY1().intValue() - rect.getY0().intValue();

		}
	}

	private void setParams(final PdfAttributesType params) {
		if (null != params.getParams()) {
			if (null != params.getParams().getLocation()) {
				this.location = params.getParams().getLocation();
			}
			if (null != params.getParams().getReason()) {
				this.reason = params.getParams().getReason();
			}
			if (null != params.getParams().getContactInfo()) {
				this.contactInfo = params.getParams().getContactInfo();
			}
		}
	}

	private void setBackgroundImage(final BackgroundType background) throws DOMException, IOException, XMLException {
	//private void setBackgroundImage(final Node background) throws DOMException, IOException, XMLException {
		if(null != background.getImage()){
			ImageType image = background.getImage();
			BufferedImage im = null;
			int width = 0;
			int height = 0;
			int posX = 0;
			int posY = 0;
			ImageEncodingType encodeType = image.getEncodeType();
			
			if(encodeType.compareTo(encodeType.BASE_64)==0){
				im = getImageFromBase64(image.getData().toString());
			}
			if(encodeType.compareTo(encodeType.URI)==0){
				try {
					im = ImageIO.read(new File(image.getData().toString()));
				} catch (final Exception e) {
					LOGGER.severe("Error extrayendo propiedades del fondo: " + e); //$NON-NLS-1$
					throw new XMLException("Error extrayendo propiedades del fondo: " + e, e); //$NON-NLS-1$
				}
			}
			if(null != image.getImageSize()){
				if(null != image.getImageSize().getHeight() && null != image.getImageSize().getHeight()){
					width = image.getImageSize().getHeight().intValue(); //$NON-NLS-1$
					height = image.getImageSize().getHeight().intValue(); //$NON-NLS-1$
				}
			}
			if(null != image.getPosition()){
				if(null != image.getPosition().getX() && null != image.getPosition().getY()){
					posX = image.getPosition().getX().intValue(); //$NON-NLS-1$
					posY = image.getPosition().getY().intValue(); //$NON-NLS-1$
				}
			}
			if (im != null) {
				paintImage(im, width, height, posX, posY);
				saveImageProperties();
			}
			
		}
	}

	private void setForeground(final ForegroundType foreground) throws DOMException, IOException, XMLException {
		
		if(null != foreground.getImage()){
			ImageType image = foreground.getImage();
			BufferedImage im = null;
			int width = 0;
			int height = 0;
			int posX = 0;
			int posY = 0;
			ImageEncodingType encodeType = image.getEncodeType();
			
			if(encodeType.compareTo(encodeType.BASE_64)==0){
				im = getImageFromBase64(image.getData().toString());
			}
			if(encodeType.compareTo(encodeType.URI)==0){
				try {
					im = ImageIO.read(new File(image.getData().toString()));
				} catch (final Exception e) {
					LOGGER.severe("Error extrayendo propiedades del fondo: " + e); //$NON-NLS-1$
					throw new XMLException("Error extrayendo propiedades del fondo: " + e, e); //$NON-NLS-1$
				}
			}
			if(null != image.getImageSize()){
				if(null != image.getImageSize().getHeight() && null != image.getImageSize().getHeight()){
					width = image.getImageSize().getHeight().intValue(); //$NON-NLS-1$
					height = image.getImageSize().getHeight().intValue(); //$NON-NLS-1$
				}
			}
			if(null != image.getPosition()){
				if(null != image.getPosition().getX() && null != image.getPosition().getY()){
					posX = image.getPosition().getX().intValue(); //$NON-NLS-1$
					posY = image.getPosition().getY().intValue(); //$NON-NLS-1$
				}
			}
			if (im != null) {
				paintImage(im, width, height, posX, posY);
				saveImageProperties();
			}
			if(null != foreground.getText()){
				setText(foreground.getText());
			}
		}
	}

	private void setText(final TextType textItem) throws XMLException {
	//private void setText(final Node textItem) throws XMLException {
		Color color = Color.BLACK;
		int fontSize = 14;
		int x = 0;
		int y = 0;
		String text = null;

		if(null != textItem.getProperties()){
			if(null != textItem.getProperties().getColor() && null != textItem.getProperties().getFontSize()){
				color = getTextColor(textItem.getProperties().getColor());
			}
			//Properties properties = textItem.getProperties();
		}
		if(null != textItem.getPosition()){
			if(null != textItem.getPosition().getX() && null != textItem.getPosition().getY()){
				x = textItem.getPosition().getX().intValue();
				y = textItem.getPosition().getY().intValue();
				
			}
		}		
		if (null != textItem.getSignatureInfos()) { //$NON-NLS-1$
			text = setSignatureInfo(textItem.getSignatureInfos());
			paintText(text, x, y, color, fontSize);
		}
		if (null != textItem.getArbitraryText()) { //$NON-NLS-1$
			setArbitraryText(textItem.getArbitraryText());
		}

	}

	private void setArbitraryText(final ArbitraryTextType arbitrary) {
		String text = ""; //$NON-NLS-1$
		int x = 0;
		int y = 0;
		int fontSize = 0;
		
		if(null != arbitrary){
			for (TextItemType textItem : arbitrary.getTextItem()){
				if(null != textItem.getFont()){
					fontSize = textItem.getFont().getFontSize().intValue();
				}
				if(null != textItem.getPosition()){
					x = textItem.getPosition().getX().intValue();
					y = textItem.getPosition().getY().intValue();
				}
				if(null != textItem.getText()){
					text = textItem.getText();					
				}
				paintText(text, x, y, null, fontSize);
			}
		}
	}

	private String setSignatureInfo(final SignatureInfosType signatureInfos) {
		String text = "";
		for(SignatureInfo signatureInfo : signatureInfos.getSignatureInfo()){
			if(null != signatureInfo.getId() && null != signatureInfo.getTitle()){
				switch (signatureInfo.getTitle()) {
					case "Subject":
						text += signatureInfo.getTitle() + this.cer.getSubjectDN().getName() + "\n";
						break;
					case "Issuer":
						text += signatureInfo.getTitle() + this.cer.getIssuerDN().getName() + "\n";
						break;
					case "SerialNumber":
						text += signatureInfo.getTitle() + this.cer.getSerialNumber() + "\n";
						break;
					case "Reason":
						text += signatureInfo.getTitle() + "\n";
						break;
					case "Location":
						text += signatureInfo.getTitle() + "\n";
						break;
					case "ContactInfo":
						text += signatureInfo.getTitle() + "\n";
						break;
					case "Date":
						text += signatureInfo.getTitle() 
						+ new SimpleDateFormat("yyyy/MM/dd").format(new Date()) + "\n";
						text += signatureInfo.getTitle() + "\n";
						break;
				}
			}
		}
		return text;
	}

	private static Color getTextColor(final String textPropAttrib) throws XMLException {
		final int[] rgb = new int[3];
		final String rgbString[] = textPropAttrib.split(" "); //$NON-NLS-1$ //$NON-NLS-2$
		for (int j = 0; j < 3; j++) {
			rgb[j] = Integer.parseInt(rgbString[j]);
		}
		final Color col = new Color(rgb[0], rgb[1], rgb[2]);
		for (final ColorResource color : ColorResource.getAllColorResources()) {
			if (color.getColor().equals(col)) {
				return color.getColor();
			}
		}
		throw new XMLException("El color indicado para el texto del frente no se ecuentra entre los permitidos"); //$NON-NLS-1$
	}

	private void paintImage(final BufferedImage im, final int width, final int height, final int x, final int y) {

		final BufferedImage newImage = new BufferedImage(this.rectWidth, this.rectWidth, BufferedImage.TYPE_INT_RGB);

		final Graphics2D g = newImage.createGraphics();
		if (this.image != null) {
			g.drawImage(this.image, 0, 0, null);
		} else {
			g.setBackground(Color.WHITE);
			g.clearRect(0, 0, newImage.getWidth(), newImage.getHeight());
		}
		g.drawImage(im, x, y, width, height, null);
		g.dispose();
		this.image = newImage;
	}

	private void paintText(final String text, final int x, final int y0, final Color color, final float size) {
		final BufferedImage newImage = new BufferedImage(this.rectWidth, this.rectHeight, BufferedImage.TYPE_INT_RGB);

		final Graphics2D g = newImage.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		if (this.image != null) {
			g.drawImage(this.image, 0, 0, null);
		} else {
			g.setBackground(Color.WHITE);
			g.clearRect(0, 0, newImage.getWidth(), newImage.getHeight());
		}

		g.setFont(new Font("Courier", Font.PLAIN, 12)); //$NON-NLS-1$
		if (color != null) {
			g.setColor(color);
		} else {
			g.setColor(Color.black);
		}
		if (size != 0) {
			g.setFont(g.getFont().deriveFont(size));
		}
		int textLength;
		int y = y0;
		for (final String line : text.split("\n")) { //$NON-NLS-1$
			textLength = g.getFontMetrics().stringWidth(line);
			if (textLength > newImage.getWidth() - x) {
				final String lineWrapped = SignPdfUiPanelPreview.breakLines(line, newImage.getWidth() - x,
						g.getFontMetrics());
				for (final String s : lineWrapped.split("\n")) { //$NON-NLS-1$
					g.drawString(s, x, y += g.getFontMetrics().getHeight());
				}
			} else {
				g.drawString(line, x, y += g.getFontMetrics().getHeight());
			}
		}
		g.dispose();
		this.image = newImage;
	}

	private void saveImageProperties() {
		this.prop.setProperty("imagePositionOnPageLowerLeftX", //$NON-NLS-1$
				this.field != null ? Integer.toString(this.field.getSignaturePositionOnPageLowerLeftX())
						: this.prop.getProperty("signaturePositionOnPageLowerLeftX") //$NON-NLS-1$
		);

		this.prop.setProperty("imagePositionOnPageLowerLeftY", //$NON-NLS-1$
				this.field != null ? Integer.toString(this.field.getSignaturePositionOnPageLowerLeftY())
						: this.prop.getProperty("signaturePositionOnPageLowerLeftY") //$NON-NLS-1$
		);
		this.prop.setProperty("imagePositionOnPageUpperRightX", //$NON-NLS-1$
				this.field != null ? Integer.toString(this.field.getSignaturePositionOnPageUpperRightX())
						: this.prop.getProperty("signaturePositionOnPageUpperRightX") //$NON-NLS-1$
		);
		this.prop.setProperty("imagePositionOnPageUpperRightY", //$NON-NLS-1$
				this.field != null ? Integer.toString(this.field.getSignaturePositionOnPageUpperRightY())
						: this.prop.getProperty("signaturePositionOnPageUpperRightY") //$NON-NLS-1$
		);
	}

	private static String getImageInBase64(final BufferedImage image) throws IOException {
		try (final ByteArrayOutputStream osImage = new ByteArrayOutputStream()) {
			ImageIO.write(image, "jpg", osImage); //$NON-NLS-1$
			return Base64.encode(osImage.toByteArray());
		} catch (final Exception e) {
			LOGGER.severe("No ha sido posible pasar la imagen a JPG: " + e); //$NON-NLS-1$
			throw new IOException("No ha sido posible pasar la imagen a JPG: " + e, e); //$NON-NLS-1$
		}
	}

	private static BufferedImage getImageFromBase64(final String imageB64) throws IOException {
		try (final ByteArrayInputStream inImage = new ByteArrayInputStream(Base64.decode(imageB64))) {
			return ImageIO.read(inImage);
		} catch (final Exception e) {
			LOGGER.severe("No ha sido posible pasar la imagen a JPG: " + e); //$NON-NLS-1$
			throw new IOException("No ha sido posible pasar la imagen a JPG: " + e, e); //$NON-NLS-1$
		}
	}
}
