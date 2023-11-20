package hu.domparse.DIZ4VX;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;

public class DomModifyDIZ4VX {
    public static void main(String[] args) {
        try {
            // XML-dokumentum beolvasása
            File xmlFile = new File("XMLDIZ4VX.xml");
            Document doc = DomReadDIZ4VX.parseXML(xmlFile);

            // Példa: Adatmódosítás (jelszó megváltoztatása)
            modifyData(doc, "Fiók", "Felhasználónév", "user1", "Jelszó", "newPassword1");
            modifyData(doc, "Fiók", "Felhasználónév", "user2", "Jelszó", "newPassword2");
            modifyData(doc, "Fiók", "Felhasználónév", "user3", "Jelszó", "newPassword3");
            modifyData(doc, "Számítógép", "Név", "Developer PC", "Darab", "5");
            modifyData(doc, "Számítógép", "Név", "Office PC", "Darab", "50");

            // Módosított dokumentum kiírása a konzolra
            System.out.println("Módosított dokumentum:");
            DomReadDIZ4VX.listNodes(doc.getDocumentElement(), "");

            // Az új XML-fájl elkészítése
            File outputFile = new File("ModifiedXMLDIZ4VX.xml");
            DomReadDIZ4VX.writeXML(doc, outputFile);

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
}
