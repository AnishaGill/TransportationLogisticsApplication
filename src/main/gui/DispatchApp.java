package gui;

import model.Load;
import model.Orderbook;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

// Represents the transportation logistics application
public class DispatchApp {
    private static final String JSON_STORE =  "./data/orderbook.json";
    private Scanner input;
    private Orderbook orderBook;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Adopted from TellerApp
    // EFFECTS: runs the application
    public DispatchApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runDispatchApp();
    }

    // Adopted from TellerApp
    // MODIFIES: this
    // EFFECTS: process the input from the user
    private void runDispatchApp() {
        boolean keepGoing = true;
        String command;

        orderBook = new Orderbook();
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Bye for now, happy driving!");
    }

    // EFFECTS: displays first menu to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tm -> create new load");
        System.out.println("\to -> view orderbook");
        System.out.println("\tu -> view active loads");
        System.out.println("\ts -> save orderbook to file");
        System.out.println("\tl -> load orderbook from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: process user command
    private void processCommand(String command) {
        switch (command) {
            case "m":
                makeLoad();
                break;
            case "o":
                viewOrderBook();
                break;
            case "u":
                viewUnCompleted();
                break;
            case "s":
                saveOrderbook();
                break;
            case "l":
                loadOrderbook();
                break;
            default:
                System.out.println("Please try another selection");
                break;
        }
    }

    // Modeled off of JsonSerialization
    // EFFECTS: saves the orderbook to file
    private void saveOrderbook() {
        try {
            jsonWriter.open();
            jsonWriter.write(orderBook);
            jsonWriter.close();
            System.out.println("Saved orderbook to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Modeled off of JsonSerialization
    // MODIFIES: this
    // EFFECTS: loads orderbook from file
    private void loadOrderbook() {
        try {
            orderBook = jsonReader.read();
            System.out.println("Loaded orderbook from "  + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // EFFECTS: creates a new load and prompts user to fill all null values, assigns all values to new load
    private void makeLoad() {
        System.out.println("---------- Creating a new load ----------");
        Load newLD = orderBook.addLoad();
        System.out.println("enter in the rate in CAD Dollars:");
        // double v = Double.parseDouble(input.next());
        newLD.setRate(Double.parseDouble(input.next()));
        System.out.println("enter in the pick up date (mm.dd.yy):");
        newLD.setPickUpDate(input.next());
        System.out.println("enter in the pick up location:");
        newLD.setPickUpLocation(input.next());
        System.out.println("enter in the drop off date (mm.dd.yy):");
        newLD.setDropOffDate(input.next());
        System.out.println("enter in the drop off location:");
        newLD.setDropOffLocation(input.next());
        System.out.println("enter in the goods:");
        newLD.setGoods(input.next());
        System.out.println("enter in the driver's name");
        newLD.setDriverName(input.next());
        System.out.println("---------- Load has been created ----------");
        String tripNumberString = Integer.toString(newLD.getTripNum());
        System.out.println("Load Trip #" + tripNumberString);
    }


    // EFFECTS: display all loads in Orderbook, displaying each Trip Number and driver first name
    private void viewOrderBook() {
        System.out.println("---------- Your Orderbook ---------");
        System.out.println("Here are all completed and upcoming Loads:");
        ArrayList<String> tripBook = orderBook.fullOrderbook();
        for (String tripNum : tripBook) {
            Load ld = orderBook.findID((Integer.parseInt(tripNum)));
            System.out.println("Trip #" + tripNum + " ------- " + "Driver: " + ld.getDriverName());
        }

        searchByTripMenu();
        String command2 = input.next();
        secondProcessCommand(command2);
    }

    // EFFECTS: Search for Trip Number Menu
    private void searchByTripMenu() {
        System.out.println("  ");
        System.out.println("\ns -> search load by Trip Number");
    }


    // MODIFIES: this
    // EFFECTS: process user command
    private void secondProcessCommand(String command2) {
        if (command2.equals("s")) {
            searchByTrip();
        }
    }

    //EFFECTS: prompts the user to enter Trip Number, then displays load dispatch
    private void searchByTrip() {
        System.out.println("enter the Trip Number of the Load you would like to view");

        Load foundLoad = orderBook.findID(Integer.parseInt(input.next()));

        if (foundLoad != null) {
            displayDispatch(foundLoad);
        } else {
            System.out.println("The given Trip Number is not valid");
        }
    }


    // REQUIRES: !(null) orderbook
    // MODIFIES: this
    // EFFECTS: make a display of all the Trip Numbers of Loads that are not completed
    private void viewUnCompleted() {
        System.out.println("---------- All active loads ----------");
        ArrayList<String> filteredOrderBook = orderBook.filterUncompleted();

        for (String tripNum : filteredOrderBook) {
            System.out.println("Trip #" + tripNum);
        }
        markAsDoneMenu();
        String command3 = input.next();
        thirdProcessCommand(command3);
    }


    // EFFECTS: displays first menu to user after entering
    private void markAsDoneMenu() {
        System.out.println("\nm -> mark load as complete");
    }


    // MODIFIES: this
    // EFFECTS: process user command
    private void thirdProcessCommand(String command3) {
        if (command3.equals("m")) {
            doMarking();
        }
    }


    // MODIFIES: Load and OrderBook
    // EFFECTS: prompts user for trip number, marks the given load as complete,
    //          and prints dispatch for marked load
    private void doMarking() {
        System.out.println("enter the Trip Number of the load:");
        String tripNum = input.next();
        orderBook.markLoadsAsComplete((Integer.parseInt(tripNum)));
        Load markedLoad = orderBook.findID((Integer.parseInt(tripNum)));
        System.out.println("The following load had been marked as completed: ");
        displayDispatch(markedLoad);
    }


    // EFFECTS: prints dispatch in a readable form
    private void displayDispatch(Load ld) {
        System.out.println("--------- Load Dispatch ---------");
        System.out.println("Trip #" + (ld.getTripNum()));
        System.out.println("Pick up date: " + ld.getPickUpDate());
        System.out.println("Pick up location: " + ld.getPickUpLocation());
        System.out.println(" ");
        System.out.println("Drop off date: " + ld.getDropOffDate());
        System.out.println("Drop off location: " + ld.getDropOffLocation());
        System.out.println(" ");
        System.out.println("Hauled goods: " + ld.getGoods());
        System.out.println("Driver name: " + ld.getDriverName());
        System.out.println("Load rate: " + "$" + ld.getRate());
        System.out.println("----------------------------------");
        System.out.println("Have a safe drive!");
    }
}












