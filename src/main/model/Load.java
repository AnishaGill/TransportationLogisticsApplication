package model;

//Represents a Load with a unique number, containing the rate the driver will be paid for the load
// in CAD, pick-up location and date, delivery location and date, name of goods, and the name of the
// driver that will be transporting the load. Loads are either completed or uncompleted, loads are uncompleted by
// default.


import org.json.JSONObject;
import persistence.Writable;

public class Load implements Writable {

    private int tripNum;
    private double rate;
    private boolean isCompleted;
    private String goods;
    private String pickUpDate;
    private String driverName;
    private String dropOffDate;
    private String pickUpLocation;
    private String dropOffLocation;


    // EFFECTS: constructs a Load
    public Load() {
        tripNum = 0;
        isCompleted = false;
        rate = 0;
        pickUpDate = null;
        pickUpLocation = null;
        dropOffDate = null;
        dropOffLocation = null;
        goods = null;
        driverName = null;
    }


    // EFFECTS: returns true if load is completed
    public boolean status() {
        return isCompleted;
    }

    public void setCompleted() {
        isCompleted = true;
    }

    public void setStatus(Boolean b) {
        isCompleted = b;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public void setDropOffDate(String dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public void setTripNum(int tripNum) {
        this.tripNum = tripNum;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public double getRate() {
        return rate;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public String getDropOffDate() {
        return dropOffDate;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public String getGoods() {
        return goods;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getTripNum() {
        return tripNum;
    }



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("trip number",tripNum);
        json.put("pick up date",pickUpDate);
        json.put("pick up location",pickUpLocation);
        json.put("drop off date",dropOffDate);
        json.put("drop off location",dropOffLocation);
        json.put("goods",goods);
        json.put("driver name",driverName);
        json.put("rate",rate);
        json.put("status",isCompleted);
        return json;
    }

}



