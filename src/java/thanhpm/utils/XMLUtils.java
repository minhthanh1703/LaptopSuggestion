/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.utils;

import java.io.File;
import java.io.Serializable;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 *
 * @author minht
 */
public class XMLUtils implements Serializable{
    public static Document parseFileToDom(String filePath) throws Exception{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(filePath);
            Document doc = db.parse(file);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static XPath creatXPath(){
            XPathFactory xpf = XPathFactory.newInstance();
            XPath xPath = xpf.newXPath();
            return xPath;
    }
    
    public static boolean transformDOMToSteamResult(Node node, String filePath){
        Source src = new DOMSource(node);
        File file = new File(filePath);
        Result result = new StreamResult(file);
        TransformerFactory tff = TransformerFactory.newInstance();
        try {
            Transformer trans = tff.newTransformer();
            trans.transform(src, result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static Document convertStringToXMLDocument(String valueString){
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(valueString)));
            return doc;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
}
