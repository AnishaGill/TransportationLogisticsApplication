package persistence;

import org.json.JSONObject;

// Modeled off of JsonSerialization
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
