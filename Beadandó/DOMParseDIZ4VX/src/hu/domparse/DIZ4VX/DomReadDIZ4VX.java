package hu.domparse.DIZ4VX;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class DomReadDIZ4VX {
    public static void main(String[] args) {
        try {
            // XML-dokumentum beolvasása
            File xmlFile = new File("XMLDIZ4VX.xml");
            Document doc = parseXML(xmlFile);

            // A dokumentum fastruktúrájának kilistázása a konzolra
            System.out.println("Fa struktúra:");
            listNodes(doc.getDocumentElement(), "");

            // Az új XML-fájl elkészítése
            File outputFile = new File("XMLDIZ4VX1.xml");
            writeXML(doc, outputFile);

            System.out.println("A XMLDIZ4VX.xml fájl elkészült.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Rekurzív módon kilistázza a dokumentum fastruktúráját
    public static void listNodes(Node node, String indent) {
        // Nyitó címke kiírása attribútumokkal
        System.out.print(indent + "<" + node.getNodeName());

        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            System.out.print(" " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
        }

        System.out.println(">");

        // Szöveges tartalom kiírása, ha van
        if (node.hasChildNodes()) {
            NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childNode = childNodes.item(i);

                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    // Rekurzív hívás a gyermek elemre
                    listNodes(childNode, indent + "  ");
                } else if (childNode.getNodeType() == Node.TEXT_NODE) {
                    // Szöveges tartalom kiírása
                    String text = childNode.getTextContent().trim();
                    if (!text.isEmpty()) {
                        System.out.println(indent + "  " + text);
                    }
                }
            }
        }

        // Záró címke kiírása
        System.out.println(indent + "</" + node.getNodeName() + ">");
    }

    // XML dokumentum beolvasása
    public static Document parseXML(File file) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }

    // XML dokumentum kiírása fájlba strukturált formában
    public static void writeXML(Document doc, File file) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Properties outputProperties = new Properties();
        outputProperties.setProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperties(outputProperties);
        DOMSource source = new DOMSource(doc);
        OutputStream os = new FileOutputStream(file);
        StreamResult result = new StreamResult(os);
        transformer.transform(source, result);
    }
}
