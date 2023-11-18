package domDIZ4VX1115;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class DomQueryDIZ4VX {

    public static void main(String[] args) {
        try {
            // XML fájl betöltése
            File xmlFile = new File("DIZ4VX_orarend.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();

            // A) Kurzusok nevének lekérdezése
            List<String> kurzusok = lekerdezKurzusok(document);
            System.out.println("Kurzusnév: " + kurzusok);

            // B) Első példány lekérdezése és kiírása
            Document doc = XMLUtil.parseXML(xmlFile);

            // A dokumentum fastruktúrájának kilistázása a konzolra
            System.out.println("Fa struktúra:");
            listNodes(doc.getDocumentElement(), "");

            // Az új XML-fájl elkészítése
            File outputFile = new File("DIZ4VX_orarend1.xml");
            XMLUtil.writeXML(doc, outputFile);

            // C) Oktatók nevének lekérdezése
            List<String> oktatok = lekerdezOktatok(document);
            System.out.println("Oktató nevek: " + oktatok);

            // D) Összetett lekérdezés
            String oktatoNeve = "Dr. Hornyák Olivér";
            List<String> kurzusokOktatonkent = lekerdezKurzusokOktatonkent(document, oktatoNeve);
            System.out.println(oktatoNeve + " által tartott kurzusok: " + kurzusokOktatonkent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listNodes(Node node, String indent) {
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
    private static List<String> lekerdezKurzusok(Document document) {
        List<String> kurzusok = new ArrayList<>();
        NodeList oraNodeList = document.getElementsByTagName("ora");

        for (int i = 0; i < oraNodeList.getLength(); i++) {
            Element oraElement = (Element) oraNodeList.item(i);
            String kurzusNev = oraElement.getElementsByTagName("targy").item(0).getTextContent();
            kurzusok.add(kurzusNev);
        }

        return kurzusok;
    }

    private static void lekerdezElsopeldany(Document document) {
        Element rootElement = document.getDocumentElement();
        System.out.println("Első példány adatai:");

        NodeList oraNodeList = rootElement.getElementsByTagName("ora");
        if (oraNodeList.getLength() > 0) {
            Element elsoOra = (Element) oraNodeList.item(0);
            System.out.println("Tárgy neve: " + elsoOra.getElementsByTagName("targy").item(0).getTextContent());
            // további adatok kiolvasása
        } else {
            System.out.println("Nincs óra az orarendben.");
        }
    }

    private static List<String> lekerdezOktatok(Document document) {
        List<String> oktatok = new ArrayList<>();
        NodeList oraNodeList = document.getElementsByTagName("ora");

        for (int i = 0; i < oraNodeList.getLength(); i++) {
            Element oraElement = (Element) oraNodeList.item(i);
            String oktatoNev = oraElement.getElementsByTagName("oktato").item(0).getTextContent();
            oktatok.add(oktatoNev);
        }

        return oktatok;
    }

    private static List<String> lekerdezKurzusokOktatonkent(Document document, String oktatoNeve) {
        List<String> kurzusok = new ArrayList<>();
        NodeList oraNodeList = document.getElementsByTagName("ora");

        for (int i = 0; i < oraNodeList.getLength(); i++) {
            Element oraElement = (Element) oraNodeList.item(i);
            String oktatoNev = oraElement.getElementsByTagName("oktato").item(0).getTextContent();

            if (oktatoNev.equals(oktatoNeve)) {
                String kurzusNev = oraElement.getElementsByTagName("targy").item(0).getTextContent();
                kurzusok.add(kurzusNev);
            }
        }

        return kurzusok;
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