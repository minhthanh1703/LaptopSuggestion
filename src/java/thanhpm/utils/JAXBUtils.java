/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import thanhpm.entities.Product;

/**
 *
 * @author minht
 */
public class JAXBUtils {

    public static boolean validateXml(String filePath, Product product) {
        try {
            JAXBContext context = JAXBContext.newInstance(Product.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Marshaller marshaller = context.createMarshaller();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(filePath));
            XmlValidatorHandler handler = new XmlValidatorHandler();
            
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(handler);
            
            StringWriter writer = new StringWriter();
            marshaller.marshal(product, writer);
//            xmlMarshal(product);
            marshaller.setEventHandler(handler);
            Validator validator = schema.newValidator();
            validator.validate(new JAXBSource(marshaller, product));
            return true;
            
        } catch (JAXBException e) {
//            e.printStackTrace();
            return false;
        } catch (SAXException e){
//            e.printStackTrace();
            return false;
        } catch (IOException e){
//            e.printStackTrace();
            return false;
        }
    }
    
    public static void xmlMarshal(Product product) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Product.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(product, writer);
        System.out.println("xml: " +writer);
    }

}
