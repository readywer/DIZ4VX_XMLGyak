package hu.domparse.DIZ4VX;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DOMWriteDIZ4VX {
    public static void main(String[] args) {
        try {
            // Új dokumentum létrehozása
            Document doc = createSampleDocument();

            // A dokumentum fastruktúrájának kilistázása a konzolra
            System.out.println("Fa struktúra:");
            DomReadDIZ4VX.listNodes(doc.getDocumentElement(), "");

            // Az új XML-fájl elkészítése
            File outputFile = new File("XMLDIZ4VX2.xml");
            DomReadDIZ4VX.writeXML(doc, outputFile);

            System.out.println("A XMLDIZ4VX1.xml fájl elkészült.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Új dokumentum létrehozása előre meghatározott adatokkal
    private static Document createSampleDocument() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // Gyökér elem létrehozása
        Element rootElement = doc.createElement("Fiók");
        rootElement.setAttribute("FID", "1");
        doc.appendChild(rootElement);

        // Felhasználónév elem hozzáadása
        Element felhasznalonevElement = doc.createElement("Felhasználónév");
        felhasznalonevElement.setTextContent("user1");
        rootElement.appendChild(felhasznalonevElement);

        // Jelszó elem hozzáadása
        Element jelszoElement = doc.createElement("Jelszó");
        jelszoElement.setTextContent("password1");
        rootElement.appendChild(jelszoElement);

        // Kedvezmény elem hozzáadása
        Element kedvezmenyElement = doc.createElement("Kedvezmény");
        kedvezmenyElement.setTextContent("10");
        rootElement.appendChild(kedvezmenyElement);

        return doc;
    }
}
