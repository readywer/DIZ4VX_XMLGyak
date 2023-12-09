package DIZ4VX;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;

public class JSONWrite {
    public static void main(String[] args) {
        JSONArray lessons = new JSONArray();
        lessons.add(createLesson("Szoftvertesztelés", "hétfő", "10", "12", "Inf. 103", "Dr. Hornyák Olivér", "G3BIW"));
        lessons.add(createLesson("Szoftvertesztelés", "hétfő", "12", "14", "Inf. 103", "Dr. Hornyák Olivér", "G3BIW"));
        lessons.add(createLesson("Web technológiák 1", "hétfő", "14", "16", "XXX. előadó", "Agárdi Anita", "G3BIW"));
        lessons.add(createLesson("Web technológiák 1", "hétfő", "16", "18", "Inf. 202", "Agárdi Anita", "G3BIW"));
        lessons.add(createLesson("Mesterséges intelligencia", "kedd", "10", "12", "XXXII. előadó", "Kunné Dr. Tamás Judit", "G3BIW"));
        lessons.add(createLesson("Adatkezelés XML-ben", "kedd", "12", "14", "XXXII. előadó", "Dr. Bednarik László", "G3BIW"));
        lessons.add(createLesson("Webes alkalmazások (Java)", "kedd", "14", "16", "online", "Selmeci Viktor", "G3BIW"));
        lessons.add(createLesson("Webes alkalmazások (Java)", "kedd", "16", "18", "online", "Selmeci Viktor", "G3BIW"));
        lessons.add(createLesson("Adatkezelés XML-ben", "szerda", "10", "12", "Inf. 101", "Dr. Bednarik László", "G3BIW"));
        lessons.add(createLesson("Mesterséges intelligencia", "csütörtök", "10", "12", "I. előadó", "Fazekas Levente", "G3BIW"));
        JSONObject root = new JSONObject();
        root.put("ora", lessons);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DIZ4VX_orarend", root);
        fileWrite(jsonObject, "DIZ4VXorarend1.json");
        consoleWrite(jsonObject);
    }

    private static void fileWrite(JSONObject jsonObject, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(indentJson(jsonObject.toJSONString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void consoleWrite(JSONObject jsonObject) {
        System.out.println("A JSON file tartalma:\n");
        JSONObject root = (JSONObject) jsonObject.get("DIZ4VX_orarend");
        JSONArray lessons = (JSONArray) root.get("ora");
        for (Object o : lessons) {
            JSONObject lesson = (JSONObject) o;
            JSONObject time = (JSONObject) lesson.get("idopont");
            System.out.println("Tárgy: " + lesson.get("targy"));
            System.out.println("Időpont: " + time.get("nap") + " " + time.get("tol") + "-" + time.get("ig"));
            System.out.println("Helyszín: " + lesson.get("helyszin"));
            System.out.println("Oktató: " + lesson.get("oktato"));
            System.out.println("Szak: " + lesson.get("szak") + "\n");
        }
    }

    private static String indentJson(String json) {
        String out = "";
        int indent = 0;
        for (int i = 0; i < json.length() - 1; i++) {
            out += json.charAt(i);
            if (json.charAt(i) == ',') {
                out += "\n" + "  ".repeat(Math.max(indent, 0));
            } else if (json.charAt(i) == '{' | json.charAt(i) == '[') {
                indent++;
                out += "\n" + "  ".repeat(Math.max(indent, 0));
            } else if ((json.charAt(i + 1) == '}' || json.charAt(i + 1) == ']')) {
                indent--;
                out += "\n" + "  ".repeat(Math.max(indent, 0));
            }
        }
        out += json.charAt(json.length() - 1);
        return out;
    }

    private static JSONObject createLesson(String subject, String day, String from, String to, String place, String teacher, String spec) {
        JSONObject timepart = new JSONObject();
        timepart.put("nap", day);
        timepart.put("tol", from);
        timepart.put("ig", to);
        JSONObject lesson = new JSONObject();
        lesson.put("targy", subject);
        lesson.put("idopont", timepart);
        lesson.put("helyszin", place);
        lesson.put("oktato", teacher);
        lesson.put("szak", spec);
        return lesson;
    }
}
