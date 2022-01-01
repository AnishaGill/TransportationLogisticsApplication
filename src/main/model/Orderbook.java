package model;

// Represents list of all loads, both completed and uncompleted. The Orderbook keeps track of
// trip numbers.

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Orderbook implements Writable {
    private int tripNumNext = 1;
    private final ArrayList<Load> orderbook;

    public Orderbook() {
        orderbook = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: creates a new load and adds it to the orderbook, also assigns each load a Trip Number
    public Load addLoad() {
        Load ld = new Load();
        ld.setTripNum(tripNumNext++);
        orderbook.add(ld);
        return ld;
    }




    // EFFECTS: add given load to orderbook
    public void setLoad(Load ld) {
        orderbook.add(ld);
    }

    // EFFECTS: returns size of Orderbook
    public int size() {
        return orderbook.size();
    }

    // EFFECTS: returns the load with the given Trip Number
    public Load findID(int tripNum) {
        Load found = null;
        for (Load ld : orderbook) {
            if (tripNum == ld.getTripNum()) {
                found = ld;
            }
        }
        return found;
    }


    // EFFECTS: creates new list only containing the Trip Numbers of the loads that are not completed
    public ArrayList<String> filterUncompleted() {
        ArrayList<String> filtered = new ArrayList<>();

        for (Load ld : orderbook) {
            if (!ld.status()) {
                filtered.add(Integer.toString((ld.getTripNum())));
            }
        }
        return filtered;
    }

    // EFFECTS: creates a new list containing all the Trip Numbers for all loads in
    //          the orderbook
    public ArrayList<String> fullOrderbook() {
        ArrayList<String> tripBook = new ArrayList<>();

        for (Load ld : orderbook) {
            int trip = ld.getTripNum();
            tripBook.add(Integer.toString(trip));
        }
        return tripBook;
    }
    
    // MODIFIES: Load
    // EFFECTS: finds a load given the trip number and marks it as completed
    public void markLoadsAsComplete(int tripNom) {
        Load foundLoad = findID(tripNom); // first find the load from the list
        foundLoad.setCompleted();         // set given load as completed
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("load",loadToJson());
        return json;
    }

    // EFFECTS: returns loads in this orderbook as a JSON array
    private JSONArray loadToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Load l : orderbook) {
            jsonArray.put(l.toJson());
        }
        return jsonArray;

    }





}









