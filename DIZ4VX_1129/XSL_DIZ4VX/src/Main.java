import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Main {
    public static void main(String[] args) {
        try {
            String xml = "DIZ4VX_orarend.xml";
            String xsl = "DIZ4VX_orarend.xsl";
            String output = "DIZ4VX_orarend.out.xml";
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsl));
            transformer.transform(new StreamSource(xml), new StreamResult(output));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}