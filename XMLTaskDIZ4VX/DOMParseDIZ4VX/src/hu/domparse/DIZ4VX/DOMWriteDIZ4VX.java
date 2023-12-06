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

public class DOMWriteDIZ4VX {
    public static void main(String[] args) {
        try {
            // Új dokumentum létrehozása
            Document doc = createSampleDocument();

            // A dokumentum fastruktúrájának kilistázása a konzolra
            System.out.println("Fa struktúra:");
            listNodes(doc.getDocumentElement(), "");

            // A fájl neve és elérési útja
            File outputFile = new File("XMLDIZ4VX2.xml");
            // XML fájl írása
            writeXML(doc, outputFile);

            System.out.println("Az XMLDIZ4VX2.xml fájl elkészült.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document createSampleDocument() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // Gyökér elem létrehozása
        Element szamitogepboltElement = doc.createElement("Számítógépbolt");
        szamitogepboltElement.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
        szamitogepboltElement.setAttribute("xs:noNamespaceSchemaLocation", "XMLSchemaDIZ4VX.xsd");
        doc.appendChild(szamitogepboltElement);

        // Fiók elemek létrehozása
        szamitogepboltElement.appendChild(createFiokElement(doc, "1", "user1", "password1", "10"));
        szamitogepboltElement.appendChild(createFiokElement(doc, "2", "user2", "password2", "15"));
        szamitogepboltElement.appendChild(createFiokElement(doc, "3", "user3", "password3", "20"));

        // Vásárló elemek létrehozása
        szamitogepboltElement.appendChild(createVasarloElement(doc, "101", "1", "John Doe", "Budapest", "1111", "Main Street 123", "+36 30 123 4567", "+36 30 123 4568"));
        szamitogepboltElement.appendChild(createVasarloElement(doc, "102", "2", "Jane Doe", "Pécs", "2222", "Second Street 456", "+36 30 987 6543"));
        szamitogepboltElement.appendChild(createVasarloElement(doc, "103", "3", "Bob Smith", "Szeged", "3333", "Third Street 789", "+36 30 555 1234", "+36 30 555 1235", "+36 30 555 1236"));

        // Bankkártya elemek létrehozása
        szamitogepboltElement.appendChild(createBankkartyaElement(doc, "201", "Bank of XYZ", "1234-5678-9012-3456", "2025-12-31"));
        szamitogepboltElement.appendChild(createBankkartyaElement(doc, "202", "Another Bank", "9876-5432-1098-7654", "2024-08-15"));
        szamitogepboltElement.appendChild(createBankkartyaElement(doc, "203", "Bank XYZ Again", "1111-2222-3333-4444", "2023-05-20"));

        // Számítógép elemek létrehozása
        szamitogepboltElement.appendChild(createSzamitogepElement(doc, "301", "Gamer PC", "1500", "2"));
        szamitogepboltElement.appendChild(createSzamitogepElement(doc, "302", "Office PC", "800", "1"));
        szamitogepboltElement.appendChild(createSzamitogepElement(doc, "303", "Developer PC", "1200", "3"));

        // Alkatrész elemek létrehozása
        szamitogepboltElement.appendChild(createAlkatreszElement(doc, "401", "RAM modul", "100", "DDR4"));
        szamitogepboltElement.appendChild(createAlkatreszElement(doc, "402", "SSD meghajtó", "120", "SATA"));
        szamitogepboltElement.appendChild(createAlkatreszElement(doc, "403", "Videokártya", "300", "GPU"));

        // Fizetés elemek létrehozása
        szamitogepboltElement.appendChild(createFizetesElement(doc, "1", "201", "Igen"));
        szamitogepboltElement.appendChild(createFizetesElement(doc, "2", "202", "Nem"));
        szamitogepboltElement.appendChild(createFizetesElement(doc, "3", "203", "Igen"));

        // Vásárlás elemek létrehozása
        szamitogepboltElement.appendChild(createVasarlasElement(doc, "1", "301", "2023-11-18"));
        szamitogepboltElement.appendChild(createVasarlasElement(doc, "2", "302", "2023-11-19"));
        szamitogepboltElement.appendChild(createVasarlasElement(doc, "3", "303", "2023-11-20"));

        // Összeszerelés elemek létrehozása
        szamitogepboltElement.appendChild(createOsszeszerelésElement(doc, "301", "401", "2"));
        szamitogepboltElement.appendChild(createOsszeszerelésElement(doc, "302", "402", "1"));
        szamitogepboltElement.appendChild(createOsszeszerelésElement(doc, "303", "403", "3"));

        return doc;
    }

    private static Element createFiokElement(Document doc, String fid, String username, String password, String kedvezmeny) {
        Element fiokElement = doc.createElement("Fiók");
        fiokElement.setAttribute("FID", fid);

        fiokElement.appendChild(createTextElement(doc, "Felhasználónév", username));
        fiokElement.appendChild(createTextElement(doc, "Jelszó", password));
        fiokElement.appendChild(createTextElement(doc, "Kedvezmény", kedvezmeny));

        return fiokElement;
    }

    private static Element createVasarloElement(Document doc, String vid, String fiok, String nev, String varos, String iranyitoszam, String utcaHazszam, String... telefon) {
        Element vasarloElement = doc.createElement("Vásárló");
        vasarloElement.setAttribute("VID", vid);
        vasarloElement.setAttribute("Fiók", fiok);

        vasarloElement.appendChild(createTextElement(doc, "Név", nev));

        Element cimElement = doc.createElement("Cím");
        cimElement.appendChild(createTextElement(doc, "Város", varos));
        cimElement.appendChild(createTextElement(doc, "Irányítószám", iranyitoszam));
        cimElement.appendChild(createTextElement(doc, "Utca_házszám", utcaHazszam));
        vasarloElement.appendChild(cimElement);

        for (String tel : telefon) {
            vasarloElement.appendChild(createTextElement(doc, "Telefonszám", tel));
        }

        return vasarloElement;
    }

    private static Element createBankkartyaElement(Document doc, String bid, String bank, String kartyaszam, String lejaratiDatum) {
        Element bankkartyaElement = doc.createElement("Bankkártya");
        bankkartyaElement.setAttribute("BID", bid);

        bankkartyaElement.appendChild(createTextElement(doc, "Bank", bank));
        bankkartyaElement.appendChild(createTextElement(doc, "Kártyaszám", kartyaszam));
        bankkartyaElement.appendChild(createTextElement(doc, "Lejárati_dátum", lejaratiDatum));

        return bankkartyaElement;
    }

    private static Element createSzamitogepElement(Document doc, String szid, String nev, String ar, String darab) {
        Element szamitogepElement = doc.createElement("Számítógép");
        szamitogepElement.setAttribute("SZID", szid);

        szamitogepElement.appendChild(createTextElement(doc, "Név", nev));
        szamitogepElement.appendChild(createTextElement(doc, "Ár", ar));
        szamitogepElement.appendChild(createTextElement(doc, "Darab", darab));

        return szamitogepElement;
    }

    private static Element createAlkatreszElement(Document doc, String aid, String nev, String ar, String tipus) {
        Element alkatreszElement = doc.createElement("Alkatrész");
        alkatreszElement.setAttribute("AID", aid);

        alkatreszElement.appendChild(createTextElement(doc, "Név", nev));
        alkatreszElement.appendChild(createTextElement(doc, "Ár", ar));
        alkatreszElement.appendChild(createTextElement(doc, "Tipus", tipus));

        return alkatreszElement;
    }

    private static Element createFizetesElement(Document doc, String fiok, String bankkartya, String jovahagyas) {
        Element fizetesElement = doc.createElement("Fizetés");
        fizetesElement.setAttribute("fiók", fiok);
        fizetesElement.setAttribute("bankkártya", bankkartya);

        fizetesElement.appendChild(createTextElement(doc, "Jóváhagyás", jovahagyas));

        return fizetesElement;
    }

    private static Element createVasarlasElement(Document doc, String fiok, String szamitogep, String datum) {
        Element vasarlasElement = doc.createElement("Vásárlás");
        vasarlasElement.setAttribute("fiók", fiok);
        vasarlasElement.setAttribute("számítógép", szamitogep);

        vasarlasElement.appendChild(createTextElement(doc, "Dátum", datum));

        return vasarlasElement;
    }

    private static Element createOsszeszerelésElement(Document doc, String szamitogep, String alkatresz, String darab) {
        Element osszeszerelésElement = doc.createElement("Összeszerelés");
        osszeszerelésElement.setAttribute("számítógép", szamitogep);
        osszeszerelésElement.setAttribute("alkatrész", alkatresz);

        osszeszerelésElement.appendChild(createTextElement(doc, "Darab", darab));

        return osszeszerelésElement;
    }

    private static Element createTextElement(Document doc, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        return element;
    }

    // Az XML fájlba strukturált formában való írása
    private static void writeXML(Document doc, File file) throws Exception {
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
}