package domDIZ4VX1108;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomWriteDIZ4VX {
    public static void main(String[] args) {
        try {
            // XML-dokumentum beolvasása
            File xmlFile = new File("DIZ4VX_orarend.xml");
            Document doc = XMLUtil.parseXML(xmlFile);

            // A dokumentum fastruktúrájának kilistázása a konzolra
            System.out.println("Fa struktúra:");
            listNodes(doc.getDocumentElement(), "");

            // Az új XML-fájl elkészítése
            File outputFile = new File("DIZ4VX_orarend1.xml");
            XMLUtil.writeXML(doc, outputFile);

            System.out.println("Az DIZ4VX_orarend1.xml fájl elkészült.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listNodes(Node node, String indent) {
        if (node instanceof Element) {
            System.out.println(indent + node.getNodeName());

            // Gyermek elemek bejárása
            NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                listNodes(childNodes.item(i), indent + "  ");
            }
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            String text = node.getTextContent().trim();
            if (!text.isEmpty()) {
                System.out.println(indent + text);
            }
        }
    }
}

class XMLUtil {
    public static Document parseXML(File file) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }

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
