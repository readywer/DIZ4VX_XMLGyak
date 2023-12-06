package hu.domparse.DIZ4VX;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DomQueryDIZ4VX {
    public static void main(String[] args) {
        try {
            // XML-dokumentum beolvasása
            File xmlFile = new File("XMLDIZ4VX.xml");
            Document doc = parseXML(xmlFile);

            // Példa lekérdezések
            System.out.println("Lekérdezések:");

            // Lekérdezés 1:
            queryData(doc, "Vásárló", "VID", "101", "Név");
            // Lekérdezés 2:
            queryData(doc, "Vásárló", "VID", "102", "Cím");
            // Lekérdezés 3:
            queryData(doc, "Számítógép", "SZID", "303", "Ár");
            // Lekérdezés 4:
            queryData(doc, "Számítógép", "SZID", "302", "Darab");
            // Lekérdezés 5:
            queryData(doc, "Fiók", "FID", "3", "Felhasználónév");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Általános adatlekérdező metódus
    private static void queryData(Document doc, String tableName, String identifierAttribute, String identifierValue,
                                  String fieldToQuery) {
        NodeList nodeList = doc.getElementsByTagName(tableName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);

            // Azonosító attribútum lekérése
            String currentIdentifier = element.getAttribute(identifierAttribute);

            if (currentIdentifier.equals(identifierValue)) {
                // Ellenőrzés, hogy a fieldToQuery létezik
                NodeList fieldNodes = element.getElementsByTagName(fieldToQuery);
                if (fieldNodes.getLength() > 0) {
                    // Adat lekérdezése
                    String queryResult = fieldNodes.item(0).getTextContent();
                    System.out.println("Lekérdezés eredménye: " + currentIdentifier + ", " + fieldToQuery + ": " + queryResult);
                } else {
                    System.out.println("A mező nem található: " + fieldToQuery);
                }
                return; // Kilépés, ha a lekérdezés megtörtént
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
}
