package DIZ4VX;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class JSONRead {
    public static void main(String[] args) {
        try{
            FileReader fileReader = new FileReader("DIZ4VX_orarend.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(fileReader);
            JSONObject root = (JSONObject) jsonObject.get("DIZ4VX_orarend");
            JSONArray lessons = (JSONArray) root.get("ora");
            System.out.println("Órarend:\n");
            for (Object o : lessons) {
                JSONObject lesson = (JSONObject) o;
                JSONObject time = (JSONObject) lesson.get("idopont");
                System.out.println("Tárgy: " + lesson.get("targy"));
                System.out.println("Időpont: " + time.get("nap") + " " + time.get("tol") + "-" + time.get("ig"));
                System.out.println("Helyszín: " + lesson.get("helyszin"));
                System.out.println("Oktató: " + lesson.get("oktato"));
                System.out.println("Szak: " + lesson.get("szak") + "\n");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}