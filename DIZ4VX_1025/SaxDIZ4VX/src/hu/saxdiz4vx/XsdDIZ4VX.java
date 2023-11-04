package hu.saxdiz4vx;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XsdDIZ4VX {
    public static void main(String[] args) {
        String xmlFilePath = "DIZ4VX_kurzusfelvetel.xml";
        String xsdFilePath = "DIZ4VX_kurzusfelvetel.xsd";

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema;
        try {
            schema = schemaFactory.newSchema(new File(xsdFilePath));
            Validator validator = schema.newValidator();

            Source source = new StreamSource(new File(xmlFilePath));
            validator.validate(source);

            System.out.println("XSD Validation successful");
        } catch (SAXException e) {
            System.out.println("Unsuccessful validation: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }
}