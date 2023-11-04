package hu.saxdiz4vx;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SaxDIZ4VX {
    public static void main(String[] args) {
        try {
            String xmlFileName = "DIZ4VX_kurzusfelvetel.xml";
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            saxParser.parse(xmlFileName, handler);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}

class SaxHandler extends DefaultHandler {
    private int indent = 0;

    private String formatAttributes(Attributes attributes) {
        int attributesLength = attributes.getLength();
        if (attributesLength == 0) {
            return "";
        }
        StringBuilder output = new StringBuilder(" {");
        for (int i = 0; i < attributesLength; i++) {
            output.append(attributes.getLocalName(i) + "=" + attributes.getValue(i));
            if (i < attributesLength - 1) {
                output.append(", ");
            }
        }
        output.append("}");
        return output.toString();
    }

    private void indent() {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        indent();
        System.out.print(qName);
        System.out.print(formatAttributes(attributes));
        System.out.print(" start");
        System.out.println();
        indent++;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        indent--;
        indent();
        System.out.print(qName);
        System.out.print(" end");
        System.out.println();
    }

    @Override
    public void characters(char ch[], int start, int length) {
        String textContent = new String(ch, start, length).trim();
        if (!textContent.isEmpty()) {
            indent();
            System.out.println(textContent);
        }
    }
}
