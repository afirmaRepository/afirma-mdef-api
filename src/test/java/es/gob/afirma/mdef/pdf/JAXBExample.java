package es.gob.afirma.mdef.pdf;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import es.gob.afirma.mdef.pdf.model.sign.AfirmaConfigType;
import es.gob.afirma.mdef.pdf.model.sign.ForegroundType;
import es.gob.afirma.mdef.pdf.model.sign.ObjectFactory;

public class JAXBExample {

	private static final String PDF_FILE = "src/test/resources/configPrueba.xml";

	public static void main(String[] args) {


		  try {

			File file = new File(PDF_FILE);
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			//SflyConfigType customer = (SflyConfigType) jaxbUnmarshaller.unmarshal(file);
			//System.out.println(customer);
			JAXBElement<AfirmaConfigType> je = (JAXBElement<AfirmaConfigType>) jaxbUnmarshaller.unmarshal(file);
			AfirmaConfigType sflyConfig =je.getValue();
			System.out.println(sflyConfig.getPdfAttributes().getSignaturePosition());
			//BackgroundType background = sflyConfig.getAppearance().getBackground();
			ForegroundType foreground = sflyConfig.getAppearance().getForeground();

		  } catch (JAXBException e) {
			e.printStackTrace();
		  }
		}
	}