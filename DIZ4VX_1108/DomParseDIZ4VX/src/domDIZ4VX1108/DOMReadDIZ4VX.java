package domDIZ4VX1108;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
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

            // A fa bejárása és kiírása
            printNode(doc.getDocumentElement(), "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, String indent) {
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
                    printNode(childNode, indent + "  ");
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
}

