package hu.domparse.DIZ4VX;

import org.w3c.dom.*;

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

public class DomModifyDIZ4VX {
    public static void main(String[] args) {
        try {
            // XML-dokumentum beolvasása
            File xmlFile = new File("XMLDIZ4VX.xml");
            Document doc = parseXML(xmlFile);

            // Példa: Adatmódosítás (jelszó megváltoztatása)
            modifyData(doc, "Fiók", "Felhasználónév", "user1", "Jelszó", "newPassword1");
            modifyData(doc, "Fiók", "Felhasználónév", "user2", "Jelszó", "newPassword2");
            modifyData(doc, "Fiók", "Felhasználónév", "user3", "Jelszó", "newPassword3");
            modifyData(doc, "Számítógép", "Név", "Developer PC", "Darab", "5");
            modifyData(doc, "Számítógép", "Név", "Office PC", "Darab", "50");

            // Módosított dokumentum kiírása a konzolra
            System.out.println("Módosított dokumentum:");
            listNodes(doc.getDocumentElement(), "");

            // Az új XML-fájl elkészítése
            File outputFile = new File("ModifiedXMLDIZ4VX.xml");
            writeXML(doc, outputFile);

            System.out.println("A ModifiedXMLDIZ4VX.xml fájl elkészült.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Általános adatmódosító metódus
    private static void modifyData(Document doc, String tableName, String identifierTag, String identifierValue,
                                   String fieldToModify, String newValue) {
        NodeList nodeList = doc.getElementsByTagName(tableName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String currentIdentifier = element.getElementsByTagName(identifierTag).item(0).getTextContent();

            if (currentIdentifier.equals(identifierValue)) {
                // Adat módosítása
                element.getElementsByTagName(fieldToModify).item(0).setTextContent(newValue);
                System.out.println("Adat módosítva: " + currentIdentifier + ", " + fieldToModify);
                return; // Kilépés, ha a módosítás megtörtént
            }
        }

        // Ha az azonosítót nem találjuk
        System.out.println("Azonosító nem található: " + identifierValue);
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

        // Szöveges tartalom kiírása, ha van
        if (node.hasChildNodes()) {
            NodeList childNodes = node.getChildNodes();

            // Ellenőrizze, hogy a gyermek elemek között van-e ELEMENT_NODE
            boolean hasElementChild = false;
            for (int i = 0; i < childNodes.getLength(); i++) {
                if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    hasElementChild = true;
                    break;
                }
            }

            if (hasElementChild) {
                System.out.println(">");
                // Rekurzív hívás a gyermek elemekre
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);

                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        listNodes(childNode, indent + "  ");
                    }
                }
                System.out.println(indent + "</" + node.getNodeName() + ">");
            } else {
                // Ha nincs más gyermek elem, akkor kiírja a szöveget és a záró címkét
                String text = node.getTextContent().trim();
                if (!text.isEmpty()) {
                    System.out.println(">" + text + "</" + node.getNodeName() + ">");
                } else {
                    System.out.println("/>");
                }
            }
        } else {
            // Ha nincs gyermek eleme, záró címke zárással fejezzük be
            System.out.println("/>");
        }
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
