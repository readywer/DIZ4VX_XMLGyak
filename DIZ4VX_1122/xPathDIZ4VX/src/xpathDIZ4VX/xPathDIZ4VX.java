package xpathDIZ4VX;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class xPathDIZ4VX {
    public static void main(String[] args) {
        try {
            // Parse the XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("studentDIZ4VX.xml");

            // Create XPath
            XPath xpath = XPathFactory.newInstance().newXPath();

            // 1) Válassza ki az összes student element, amely a class gyermekei!
            NodeList students = (NodeList) xpath.compile("/class/student").evaluate(doc, javax.xml.xpath.XPathConstants.NODESET);
            System.out.println("1) Összes student elem, ami a class gyermeke: ");
            printNodeList(students);

            // 2) Válassza ki azt a student elemet, amely rendelkezik "id" attribútummal és értéke "02"!
            Node studentWithId02 = (Node) xpath.compile("/class/student[@id='2']").evaluate(doc, javax.xml.xpath.XPathConstants.NODE);
            System.out.println("\n2) Student elem, ami rendelkezik id='2': ");
            printNode(studentWithId02);

            // 3) Kiválasztja az összes student elemet, függetlenül attól, hogy hol vannak a dokumentumban!
            NodeList allStudents = (NodeList) xpath.compile("//student").evaluate(doc, javax.xml.xpath.XPathConstants.NODESET);
            System.out.println("\n3) Összes student elem, függetlenül attól, hogy hol vannak a dokumentumban: ");
            printNodeList(allStudents);

            // 4) Válassza ki a második student element, amely a class root element gyermeke!
            Node secondStudent = (Node) xpath.compile("/class/student[2]").evaluate(doc, javax.xml.xpath.XPathConstants.NODE);
            System.out.println("\n4) Második student elem, ami a class root element gyermeke: ");
            printNode(secondStudent);

            // 5) Válassza ki az utolsó student elemet, amely a class root element gyermeke!
            Node lastStudent = (Node) xpath.compile("/class/student[last()]").evaluate(doc, javax.xml.xpath.XPathConstants.NODE);
            System.out.println("\n5) Utolsó student elem, ami a class root element gyermeke: ");
            printNode(lastStudent);

            // 6) Válassza ki az utolsó előtti student elemet, amely a class root element gyermeke!
            Node secondLastStudent = (Node) xpath.compile("/class/student[last()-1]").evaluate(doc, javax.xml.xpath.XPathConstants.NODE);
            System.out.println("\n6) Utolsó előtti student elem, ami a class root element gyermeke: ");
            printNode(secondLastStudent);

            // 7) Válassza ki az első két student elemet, amelyek a root element gyermekei!
            NodeList firstTwoStudents = (NodeList) xpath.compile("/class/student[position() <= 2]").evaluate(doc, javax.xml.xpath.XPathConstants.NODESET);
            System.out.println("\n7) Az első két student elem, ami a class root element gyermekei: ");
            printNodeList(firstTwoStudents);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to print a NodeList
    private static void printNodeList(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            printNode(node);
        }
    }

    // Helper method to print a Node
    private static void printNode(Node node) {
        if (node != null) {
            System.out.println(node.getNodeName() + ": " + node.getTextContent());
        }
    }
}
