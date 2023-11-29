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

            // 8) Válassza ki class root element összes gyermek elemét!
            NodeList classChildren = (NodeList) xpath.compile("/class/*").evaluate(doc, javax.xml.xpath.XPathConstants.NODESET);
            System.out.println("\n8) Class root element összes gyermek eleme: ");
            printNodeList(classChildren);

            // 9) Válassza ki az összes student elemet, amely rendelkezik legalább egy bármilyen attribútummal!
            NodeList studentsWithAttributes = (NodeList) xpath.compile("//student[@*]").evaluate(doc, javax.xml.xpath.XPathConstants.NODESET);
            System.out.println("\n9) Összes student elem, amely rendelkezik legalább egy attribútummal: ");
            printNodeList(studentsWithAttributes);

            // 10) Válassza ki a dokumentum összes elemét!
            NodeList allElements = (NodeList) xpath.compile("//*").evaluate(doc, javax.xml.xpath.XPathConstants.NODESET);
            System.out.println("\n10) Dokumentum összes eleme: ");
            printNodeList(allElements);

            // 11) Válassza ki a class root element összes student elemét, amelynél a kor>20!
            NodeList studentsOver20 = (NodeList) xpath.compile("/class/student[kor > 20]").evaluate(doc, javax.xml.xpath.XPathConstants.NODESET);
            System.out.println("\n11) Class root element összes student eleme, ahol a kor>20: ");
            printNodeList(studentsOver20);

            // 12) Válassza ki az összes student elem összes keresztnev or vezeteknev csomópontot!
            NodeList allFirstNamesAndLastNames = (NodeList) xpath.compile("//student/keresztnev | //student/vezeteknev").evaluate(doc, javax.xml.xpath.XPathConstants.NODESET);
            System.out.println("\n12) Összes student elem összes keresztnev és vezeteknev csomópontja: ");
            printNodeList(allFirstNamesAndLastNames);

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
