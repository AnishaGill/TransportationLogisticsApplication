package gui;

import model.Load;
import model.Orderbook;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

// Dispatch Application Graphical User Interface
public class DispatchAppUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JPanel dispatchBoard;
    private Orderbook orderbook;

    private DefaultListModel listModel = new DefaultListModel();
    private JList list = new JList<String>(listModel);

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/orderbook.json";

    // Dispatch Application GUI constructor
    public DispatchAppUI() throws FileNotFoundException {
        orderbook = new Orderbook();
        dispatchBoard = new JPanel();
        dispatchBoard.setLayout(new GridLayout(0, 2));

        setContentPane(dispatchBoard);
        setTitle("AG Dispatch");
        setSize(WIDTH, HEIGHT);

        addButtons();
        JComponent newContentPane = new AddAllActiveLoadDisplay();
        dispatchBoard.add(newContentPane);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    // MODIFIES: this (buttons are added to panel)
    // EFFECTS: creates button panel for buttons to interact with Load, and Orderbook, adds new panel to
    //          dispatchBoard
    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(new JButton(new AddLoadAction()));
        buttonPanel.add(new JButton(new ViewOrderbookAction()));
        buttonPanel.add(new JButton(new SaveOrderbookAction()));
        buttonPanel.add(new JButton(new LoadOrderbookAction()));
        dispatchBoard.add(buttonPanel, BorderLayout.WEST);
    }

    // MODIFIES: this, buttonPanel
    // EFFECTS: AddLoad button functionality, model and GUI
    private class AddLoadAction extends AbstractAction {

        AddLoadAction() {
            super("Add Load");
        }

        @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField rateField = new JTextField(5);
            JTextField pickUpDateField = new JTextField(5);
            JTextField pickUpLocationField = new JTextField(5);
            JTextField dropOffDateField = new JTextField(5);
            JTextField dropOffLocationField = new JTextField(5);
            JTextField goodsField = new JTextField(5);
            JTextField driverNameField = new JTextField(5);
            Load load;
            JPanel p1 = new JPanel();

            load = orderbook.addLoad();

            p1.add(new JLabel("Rate:"));
            p1.add(rateField);

            p1.add(new JLabel("Pick up date:"));
            p1.add(pickUpDateField);

            p1.add(new JLabel("Pick up location:"));
            p1.add(pickUpLocationField);

            p1.add(new JLabel("Drop off date:"));
            p1.add(dropOffDateField);

            p1.add(new JLabel("Drop off location:"));
            p1.add(dropOffLocationField);

            p1.add(new JLabel("Goods:"));
            p1.add(goodsField);

            p1.add(new JLabel("Driver Name:"));
            p1.add(driverNameField);


            int result = JOptionPane.showConfirmDialog(
                    null, p1,
                    "New Load", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                double r1 = Double.parseDouble(rateField.getText());
                load.setRate(r1);
                load.setPickUpDate(pickUpDateField.getText());
                load.setPickUpLocation(pickUpLocationField.getText());
                load.setDropOffDate(dropOffDateField.getText());
                load.setDropOffLocation(dropOffLocationField.getText());
                load.setDropOffLocation(dropOffLocationField.getText());
                load.setGoods(goodsField.getText());
                load.setDriverName(driverNameField.getText());
            }

            String s = "Trip #" + load.getTripNum() + " | " + "Rate: $" + load.getRate()
                    + " | " + "Pickup Location: " + load.getPickUpLocation() + " | "
                    + "Pickup Date: " + load.getPickUpDate() + " | "
                    + "Drop Off Location: " + load.getDropOffLocation() + " | "
                    + "Drop Off Date: " + load.getDropOffDate() + " | "
                    + "Goods: " + load.getGoods() + " | "
                    + "Driver: " + load.getDriverName();

            listModel.addElement(s);

        }

        private void doRate() {
        }


    }

    private class ViewOrderbookAction extends AbstractAction {
        // Viewing Orderbook functionality

        ViewOrderbookAction() {
            super("View OrderBook");
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates Orderbook display window, and displays all loads in orderbook
        public void actionPerformed(ActionEvent e) {
            // String orderbookDisplay =
            JOptionPane.showMessageDialog(
                    null, orDisplay(), "Orderbook", JOptionPane.INFORMATION_MESSAGE);
        }

        // MODIFIES: this
        // EFFECTS: Creates Orderbook line display
        public String orDisplay() {
            String text = "<html>";
            ArrayList<String> tripBook = orderbook.fullOrderbook();

            for (String t : tripBook) {
                Load ld = orderbook.findID((Integer.parseInt(t)));
                String s1 = "<br> Trip #: " + t + " ------- " + ld.getDriverName() + "</br>";
                text += s1;
            }
            return text;

        }


    }


    public class SaveOrderbookAction extends AbstractAction {
        // Saving functionality

        SaveOrderbookAction() {
            super("Save Orderbook");
        }


        @Override
        // MODIFIES: this
        // EFFECTS: Save orderbook button functionality, model and GUI
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(orderbook);
                jsonWriter.close();
                System.out.println("Saved orderbook to " + JSON_STORE);
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(
                        null, "Unable to write to file: " + JSON_STORE,
                        "Saving error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(
                    null, "Your Orderbook has been saved!",
                    "Saved", JOptionPane.INFORMATION_MESSAGE);

        }
    }


    private class LoadOrderbookAction extends AbstractAction {
        // Load Orderbook functionality

        LoadOrderbookAction() {
            super("Load Orderbook");
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Load orderbook button functionality, model and GUI
        public void actionPerformed(ActionEvent e) {
            try {
                orderbook = jsonReader.read();
                System.out.println("Loaded orderbook from " + JSON_STORE);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(
                        null, "Unable to read to file: " + JSON_STORE,
                        "Loading error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(
                    null, "Your Orderbook has been loaded, you are ready to roll!",
                    "Loaded", JOptionPane.INFORMATION_MESSAGE);

        }
    }



    public class AddAllActiveLoadDisplay extends JPanel implements ListSelectionListener {
        // UI of the active loads

        private JButton deliveredButton;

        // MODIFIES: this
        // EFFECTS: Creates and added JList, and JScrollPane
        public AddAllActiveLoadDisplay() {
            super(new BorderLayout());
            String deliveredString = "Delivered";

            // create the list and put it in the scroll pane
            list = new JList<String>(listModel);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setSelectedIndex(0);
            list.addListSelectionListener(this);
            list.setVisibleRowCount(5);
            JScrollPane listScrollPane = new JScrollPane(list);

            deliveredButton = new JButton(deliveredString);
            deliveredButton.setActionCommand(deliveredString);
            deliveredButton.addActionListener(new DeliveredAction());

            //Create a panel that uses BoxLayout.
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new BoxLayout(buttonPane,
                    BoxLayout.LINE_AXIS));
            buttonPane.add(deliveredButton);
            buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            add(buttonPane, BorderLayout.PAGE_END);
            add(listScrollPane, BorderLayout.CENTER);
        }


        @Override
        // From ListDemo
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {

                if (list.getSelectedIndex() == -1) {
                    deliveredButton.setEnabled(false);

                } else {
                    deliveredButton.setEnabled(true);
                }
            }
        }


        class DeliveredAction implements ActionListener {
            // Deliver button functionality

            @Override
            // MODIFIES: this
            // EFFECTS: Deliver button functionality, model and GUI
            public void actionPerformed(ActionEvent e) {
                int size = listModel.getSize();

                int index = list.getSelectedIndex();
                ArrayList<String> filteredOrderBook = orderbook.filterUncompleted();
                String tripNum = filteredOrderBook.get(index);
                Load selectedLoad = orderbook.findID(Integer.parseInt(tripNum));
                selectedLoad.setCompleted();
                listModel.remove(index);


                if (size == 0) {
                    deliveredButton.setEnabled(false);

                } else {
                    if (index == listModel.getSize()) {
                        //removed item in last position
                        index--;
                    }

                    list.setSelectedIndex(index);
                    list.ensureIndexIsVisible(index);

                }
            }

        }
    }


    // Starts the application
    public static void main(String[] args) {
        try {

            // Splash Screen
            JWindow window = new JWindow();
            ImageIcon icon = new ImageIcon("src/main/gui/flameTruck.png");
            JLabel label = new JLabel("Launching", icon, JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setHorizontalTextPosition(JLabel.CENTER);
            window.add(label);
            window.setBounds(300, 50, 700, 300);
            window.setVisible(true);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            window.dispose();


            new DispatchAppUI();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    null, "Unable to run application: file not found",
                    "Orderbook", JOptionPane.INFORMATION_MESSAGE);
        }

    }


}


