package xpathDIZ4VX;

import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class xPathModify {
    public static void main(String[] args) {
        try {
            // Parse the XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("DIZ4VX_orarend.xml");

            // Make modifications using XPath
            makeModifications(doc);

            // Print modified document to console
            System.out.println("Modified Document:");
            printDocument(doc);

            // Save modified document to a new file
            saveDocument(doc, "orarendNeptunkod1.xml");
            System.out.println("Modified document saved to orarendNeptunkod1.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void makeModifications(Document doc) {
        try {
            // 1. Egy tantárgyat cserél a tantárgylista közepén.
            NodeList subjectNodes = doc.getElementsByTagName("ora");
            if (subjectNodes != null && subjectNodes.getLength() >= 3) {
                Node subjectToReplace = subjectNodes.item(2).getFirstChild();
                subjectToReplace.setTextContent("Módosított Tantárgy");
            }

            // 2. Hozzáad egy új tantárgyat a tantárgylista végéhez.
            Element newSubject = doc.createElement("ora");
            Element newSubjectName = doc.createElement("targy");
            newSubjectName.setTextContent("Új Tantárgy");
            newSubject.appendChild(newSubjectName);

            Element idopont = doc.createElement("idopont");
            Element nap = doc.createElement("nap");
            nap.setTextContent("péntek");
            idopont.appendChild(nap);
            Element tol = doc.createElement("tol");
            tol.setTextContent("14");
            idopont.appendChild(tol);
            Element ig = doc.createElement("ig");
            ig.setTextContent("16");
            idopont.appendChild(ig);
            newSubject.appendChild(idopont);

            Element helyszin = doc.createElement("helyszin");
            helyszin.setTextContent("Inf. 105");
            newSubject.appendChild(helyszin);

            Element oktato = doc.createElement("oktato");
            oktato.setTextContent("Dr. Example Teacher");
            newSubject.appendChild(oktato);

            Element szak = doc.createElement("szak");
            szak.setTextContent("G3BIW");
            newSubject.appendChild(szak);

            doc.getDocumentElement().appendChild(newSubject);

            // 3. Töröl egy tantárgyat a tantárgylista közepéről.
            NodeList subjectNodesAfterAddition = doc.getElementsByTagName("ora");
            if (subjectNodesAfterAddition != null && subjectNodesAfterAddition.getLength() >= 2) {
                Node subjectToDelete = subjectNodesAfterAddition.item(1);
                subjectToDelete.getParentNode().removeChild(subjectToDelete);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printDocument(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveDocument(Document doc, String filePath) {
        try (OutputStream os = new FileOutputStream(filePath)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(os);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
