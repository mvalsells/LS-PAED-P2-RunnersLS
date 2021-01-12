import com.google.gson.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class Json {
    public static ArrayList<Atleta> llegirAtletes() throws FileNotFoundException {
        Reader read = new FileReader("Datasets/datasets-clubs/datasetS.json");
        JsonArray array = JsonParser.parseReader(read).getAsJsonArray();

        ArrayList<Atleta> atletes = new ArrayList<Atleta>();

        for (JsonElement clubElement: array) {
            JsonObject clubObject = clubElement.getAsJsonObject();
            JsonArray at = clubObject.get("at").getAsJsonArray();
            for (JsonElement atletaElement : at) {
                JsonObject atletaObject = atletaElement.getAsJsonObject();
                String atName = atletaObject.get("name").getAsString();
                String atLastName = atletaObject.get("lastname").getAsString();
                int atAge = atletaObject.get("age").getAsInt();
                String atNation = atletaObject.get("nation").getAsString();
                float atDistance = atletaObject.get("distance").getAsFloat();
                float atTime = atletaObject.get("time").getAsFloat();
                String atType = atletaObject.get("type").getAsString();
                Atleta atleta = new Atleta(atName, atLastName, atAge, atNation, atDistance, atTime, atType);
                atletes.add(atleta);
            }
        }
        return atletes;
    }

    public static ArrayList<Cursa> llegirCurses() throws FileNotFoundException {
        Reader read = new FileReader("Datasets/datasets-activities/datasetXS.json");
        JsonArray array = JsonParser.parseReader(read).getAsJsonArray();
        ArrayList<Cursa> cureses = new ArrayList<Cursa>();
        for (JsonElement cursaElement: array){
            JsonObject cursaObject = cursaElement.getAsJsonObject();
            String name = cursaObject.get("name").getAsString();
            String start = cursaObject.get("start").getAsString();
            String end = cursaObject.get("end").getAsString();
            Cursa cursa = new Cursa(name, start, end);
            cureses.add(cursa);
        }
        return cureses;
    }
}
