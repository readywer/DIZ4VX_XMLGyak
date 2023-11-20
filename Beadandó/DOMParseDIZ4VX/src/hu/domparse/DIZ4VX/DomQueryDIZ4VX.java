package hu.domparse.DIZ4VX;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;

public class DomQueryDIZ4VX {
    public static void main(String[] args) {
        try {
            // XML-dokumentum beolvasása
            File xmlFile = new File("XMLDIZ4VX.xml");
            Document doc = DomReadDIZ4VX.parseXML(xmlFile);

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
}
