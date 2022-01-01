package persistence;

import model.Load;
import model.Orderbook;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads orderbook from JSON data stored in file
// Modeled off of JsonSerialization
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Orderbook read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOderBook(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }


    // EFFECTS: parses Orderbook from JSON object and returns it
    private Orderbook parseOderBook(JSONObject jsonObject) {
        Orderbook or =  new Orderbook();
        addLoads(or,jsonObject);
        return or;
    }

    // MODIFIES: or
    // EFFECTS: parses loads from JSON object and adds them to orderbook
    private void addLoads(Orderbook or, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("load");
        for (Object json : jsonArray) {
            JSONObject nextLoad = (JSONObject) json;
            addLoad(or, nextLoad);
        }

    }

    // MODIFIES: or
    // EFFECTS: parses load from JSON object and adds it to orderbook
    private void addLoad(Orderbook or, JSONObject jsonObject) {
        Load ld = new Load();
        int tripNum = jsonObject.getInt("trip number");
        double rate = jsonObject.getDouble("rate");
        boolean isCompleted = jsonObject.getBoolean("status");
        String goods = jsonObject.getString("goods");
        String pickUpDate = jsonObject.getString("pick up date");
        String pickUpLocation = jsonObject.getString("pick up location");
        String dropOffDate = jsonObject.getString("drop off date");
        String dropOffLocation = jsonObject.getString("drop off location");


        ld.setTripNum(tripNum);
        ld.setRate(rate);
        ld.setStatus(isCompleted);
        ld.setGoods(goods);
        ld.setPickUpDate(pickUpDate);
        ld.setPickUpLocation(pickUpLocation);
        ld.setDropOffDate(dropOffDate);
        ld.setDropOffLocation(dropOffLocation);
        or.setLoad(ld);
    }

}
