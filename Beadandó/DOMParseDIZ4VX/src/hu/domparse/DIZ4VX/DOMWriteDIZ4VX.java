package hu.domparse.DIZ4VX;

import org.w3c.dom.Document;

import java.io.File;

public class DOMWriteDIZ4VX {
    public static void main(String[] args) {
        try {
            // XML-dokumentum beolvasása
            File xmlFile = new File("XMLDIZ4VX.xml");
            Document doc = DomReadDIZ4VX.parseXML(xmlFile);

            // A dokumentum fastruktúrájának kilistázása a konzolra
            System.out.println("Fa struktúra:");
            DomReadDIZ4VX.listNodes(doc.getDocumentElement(), "");

            // Az új XML-fájl elkészítése
            File outputFile = new File("XMLDIZ4VX1.xml");
            DomReadDIZ4VX.writeXML(doc, outputFile);

            System.out.println("A XMLDIZ4VX1.xml fájl elkészült.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
