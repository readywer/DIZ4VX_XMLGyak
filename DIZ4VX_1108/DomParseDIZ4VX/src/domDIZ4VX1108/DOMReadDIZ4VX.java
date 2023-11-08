package domDIZ4VX1108;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DOMReadDIZ4VX {
    public static void main(String[] args) {
        try {
            // XML-dokumentum beolvasása
            File xmlFile = new File("DIZ4VX_orarend.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // A dokumentum fastruktúrájának feldolgozása
            doc.getDocumentElement().normalize();

            // Gyökérelem kiírása
            System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());

            // A fa bejárása és kiírása
            printNode(doc.getDocumentElement(), "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, String indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(indent + node.getNodeName());

            // Gyermek elemek bejárása
            NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childNode = childNodes.item(i);
                printNode(childNode, indent + "  ");
            }
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            String text = node.getTextContent().trim();
            if (!text.isEmpty()) {
                System.out.println(indent + text);
            }
        }
    }
}

