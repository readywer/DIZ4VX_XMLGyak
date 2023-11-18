import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DomModifyDIZ4VX {
    public static void main(String[] args) {
        try {
            String inputFileName = "DIZ4VX_orarend.xml";
            // a.) Módosítás: Óraadó hozzáadása
            addInstructorToCourse(inputFileName, "orarendModifyNeptunkod.xml");

            // b.) Módosítás: Minden óra típus módosítása
            modifyCourseTypes(inputFileName);

            // c.) Bonyolultabb módosítás
            replaceInstructor(inputFileName, "cfeladat.xml", "Selmeci Viktor", "Epamos tanár");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // a.) Módosítás: Óraadó hozzáadása
    private static void addInstructorToCourse(String inputFileName, String outputFileName)
            throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputFileName);

        // Keresés azok között a példányok között, ahol nincs óraadó
        NodeList courses = doc.getElementsByTagName("ora");
        for (int i = 0; i < courses.getLength(); i++) {
            Element course = (Element) courses.item(i);
            NodeList instructors = course.getElementsByTagName("oratipus");
            if (instructors.getLength() == 0) {
                // Ha nincs óraadó, hozzáadunk egyet
                Element newInstructor = doc.createElement("óraadó");
                newInstructor.appendChild(doc.createTextNode("Bitman"));
                course.appendChild(newInstructor);
            }
        }

        // XML fájl kiírása konzolra
        printXmlToConsole(doc);

        // XML fájl kiírása fájlba
        writeXmlToFile(doc, outputFileName);
    }

    // b.) Módosítás: Minden óra típus módosítása
    private static void modifyCourseTypes(String fileName)
            throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(fileName);

        // Módosítás minden "ora" elem "tipus" attribútumára
        NodeList courses = doc.getElementsByTagName("ora");
        for (int i = 0; i < courses.getLength(); i++) {
            Element course = (Element) courses.item(i);
            course.setAttribute("tipus", "előadás");
        }

        // XML fájl kiírása strukturált formában konzolra
        printXmlToConsoleFormatted(doc);
    }

    // Segédfüggvény: XML fájl kiírása konzolra
    private static void printXmlToConsole(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
        System.out.println("\n\n");
    }

    // Segédfüggvény: XML fájl kiírása fájlba
    private static void writeXmlToFile(Document doc, String fileName) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    // Segédfüggvény: XML fájl kiírása strukturált formában konzolra
    private static void printXmlToConsoleFormatted(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
        System.out.println("\n\n");
    }
    private static void replaceInstructor(String inputFileName, String outputFileName, String oldInstructor, String newInstructor)
            throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputFileName);

        // Keresés azok között a példányok között, ahol az oktató "Selmeci Viktor"
        NodeList courses = doc.getElementsByTagName("oktato");
        for (int i = 0; i < courses.getLength(); i++) {
            Element instructorElement = (Element) courses.item(i);
            String instructorName = instructorElement.getTextContent();
            if (instructorName.equals(oldInstructor)) {
                // Cseréljük ki az oktató nevét
                instructorElement.setTextContent(newInstructor);
            }
        }

        // XML fájl kiírása strukturált formában konzolra
        printXmlToConsoleFormatted(doc);

        // XML fájl kiírása fájlba
        writeXmlToFile(doc, outputFileName);
    }
}
