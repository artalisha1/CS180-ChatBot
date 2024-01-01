import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Project 5: Customer.java
 *
 * Defines a Store object
 *
 * @author Joey, Alisha, Bryan, and Shrung
 *
 * @version 12/10/2023
 *
 */
public class Customer extends User implements Serializable {
    private String name;
    // tracks number of messages sent to stores
    private ArrayList<String> cart = new ArrayList<>();
    String[] options2 = {"Message", "File"};

    public Customer(String email, String password, String name) {
        super(email, password);
        this.name = name;
    }
    public String sendMessage(ArrayList<Store> stores2, ArrayList<Seller>sellers) {
        // user decides to view the sellers or stores
        String choice;
        String[] options = {"Stores", "Sellers"};
        do {
            choice = (String) JOptionPane.showInputDialog(null,
                    "Would you like to message a store or a seller?",
                    "Send Message", JOptionPane.PLAIN_MESSAGE, null, options, null);
            if (choice.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cannot enter an select an empty option!",
                        "Send Message", JOptionPane.ERROR_MESSAGE);
                choice = "";
            }
            if (choice == null) {
                JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                        JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
        } while (choice.isEmpty());

        // if user decides to view stores
        if (choice.equals("Stores")) {
            // displays list of stores
            ArrayList<Store> storesArr = stores2;
            // ends method if there are no stores to message
            if (storesArr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "There are currently no stores available to message", "Send Message",
                        JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            //array of stores
            Store[] stores = new Store[storesArr.size()];
            //array of store names
            String[] storeNames = new String[storesArr.size()];
            for (int i = 0; i < storesArr.size(); i++) {
                stores[i] = storesArr.get(i);
                storeNames[i] = storesArr.get(i).getName();
            }
            // chooses the store that they would message
            String storeChoice;
            do {
                storeChoice = (String) JOptionPane.showInputDialog(null,
                        "Pick which store you would like to message",
                        "Send Message", JOptionPane.PLAIN_MESSAGE, null, storeNames, null);
                if (storeChoice.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Cannot select an empty value!",
                            "Send Message", JOptionPane.ERROR_MESSAGE);
                    storeChoice = "";
                }
                if (storeChoice == null) {
                    JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                            JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
            } while (storeChoice.isEmpty());
            // determines storeOwner
            //if the name of the selected store is equal to name of store in array, gets the store owner
            Seller storeOwner = null;
            Store store = null;
            for (int j = 0; j < stores.length; j++) {
                if (storeChoice.equals(stores[j].getName())) {
                    store = stores[j];
                }
            }
            storeOwner = store.getOwner();
            // determines if they are sending text or a file
            String message = "";
            String filepath = "";
            String type;
            do {
                type = (String) JOptionPane.showInputDialog(null,
                        "Would you like to send a message or a file?",
                        "Send Message", JOptionPane.PLAIN_MESSAGE, null, options2, null);
                if (type.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Cannot select an empty value!",
                            "Send Message", JOptionPane.ERROR_MESSAGE);
                    type = "";
                }
                if (type == null) {
                    JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                            JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
            } while (type.isEmpty());
            // gets message contents based on message type
            if (type.equals("Message")) {
                do {
                    message = JOptionPane.showInputDialog(null,
                            "What message would you like to send to the owner of this store?",
                            "Send Message", JOptionPane.QUESTION_MESSAGE);
                    if (message.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Cannot enter an empty message!",
                                "Send Message", JOptionPane.ERROR_MESSAGE);
                        message = "";
                    }
                    if (message == null) {
                        JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                    //calls sendMessage in parent class
                    return sendMessage(storeOwner, message) + ";" + storeOwner.getEmail();

                } while (message.isEmpty());
            } else {
                do {
                    try {
                        filepath = JOptionPane.showInputDialog(null,
                                "What is the file path of the file you want to send?", "Send File",
                                JOptionPane.QUESTION_MESSAGE);
                        if (filepath.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Cannot enter an empty filepath!",
                                    "Send File", JOptionPane.ERROR_MESSAGE);
                            filepath = "";
                        }
                        if (filepath == null) {
                            JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return null;
                        }
                        //calls sendFileMessage in parent class
                        return sendFileMessage(storeOwner, filepath) + ";" + storeOwner.getEmail();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "That filepath doesn't exist!",
                                "Send File", JOptionPane.ERROR_MESSAGE);
                        filepath = "";
                    }
                } while (filepath.isEmpty());
            }
            // if user decides to view the sellers
        } else {
            String sellerEmail;
            // Gets the seller you would like to message
            do {
                //asks user what seller they would like the message based on their email
                sellerEmail = JOptionPane.showInputDialog(null,
                        "What is the email of the seller you would like to message?", "Send Message",
                        JOptionPane.QUESTION_MESSAGE);
                if (sellerEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Cannot enter an empty email!",
                            "Send Message", JOptionPane.ERROR_MESSAGE);
                    sellerEmail = "";
                } else if (!sellerEmail.contains("@") || !sellerEmail.contains(".")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid email!",
                            "Send Message", JOptionPane.ERROR_MESSAGE);
                    sellerEmail = "";
                } else if (sellerEmail == null) {
                    JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                            JOptionPane.INFORMATION_MESSAGE);
                    return null;
                } else {
                    //checks if seller exists
                    Seller seller = null;
                    for (int i = 0; i < sellers.size(); i++) {
                        if (sellerEmail.equals(sellers.get(i).getEmail())) {
                            seller = sellers.get(i);
                        }
                    }
                    if (seller == null) {
                        //error message is shown if seller doesn't exist
                        JOptionPane.showMessageDialog(null, "That seller doesn't exist!",
                                "Send Message", JOptionPane.ERROR_MESSAGE);
                        sellerEmail = "";
                    } else {
                        String message = "";
                        String filepath = "";
                        String type;
                        do {
                            type = (String) JOptionPane.showInputDialog(null,
                                    "Would you like to send a message or a file?",
                                    "Send Message", JOptionPane.PLAIN_MESSAGE, null, options2, null);
                            if (type.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Cannot select an empty value!",
                                        "Send Message", JOptionPane.ERROR_MESSAGE);
                                type = "";
                            }
                            if (type == null) {
                                JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                        JOptionPane.INFORMATION_MESSAGE);
                                return null;
                            }
                        } while (type.isEmpty());
                        // gets message contents based on message type
                        if (type.equals("Message")) {
                            do {
                                message = JOptionPane.showInputDialog(null,
                                        "What message would you like to send to the seller?",
                                        "Send Message", JOptionPane.QUESTION_MESSAGE);
                                if (message.isEmpty()) {
                                    JOptionPane.showMessageDialog(null,
                                            "Cannot enter an empty message!", "Send Message",
                                            JOptionPane.ERROR_MESSAGE);
                                    message = "";
                                }
                                if (message == null) {
                                    JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    return null;
                                }
                                //calls sendMessage in parent class
                                return sendMessage(seller, message) +  ";" + seller.getEmail();
                            } while (message.isEmpty());
                        } else {
                            do {
                                try {
                                    filepath = JOptionPane.showInputDialog(null,
                                            "What is the file path of the file you want to send?", "Send File",
                                            JOptionPane.QUESTION_MESSAGE);
                                    if (filepath.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Cannot enter an empty filepath!",
                                                "Send File", JOptionPane.ERROR_MESSAGE);
                                        filepath = "";
                                    }
                                    if (filepath == null) {
                                        JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        return null;
                                    }
                                    //calls sendFileMessage in parent class
                                    return sendFileMessage(seller, filepath) + ";" + seller.getEmail();
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, "That filepath doesn't exist!",
                                            "Send File", JOptionPane.ERROR_MESSAGE);
                                    filepath = "";
                                }
                            } while (filepath.isEmpty());
                        }
                    }
                }
            } while (sellerEmail.isEmpty());

        }
        return null;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void addToCart(String s) {
        cart.add(s);
    }
    public void addStoreMessage(String name) {

    }
    public void buyItem(ArrayList<Store> stores2) {
        ArrayList<Store> storesArr = stores2;
        // ends method if no stores can be messaged
        if (storesArr.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "There are currently no stores available to buy from", "Buy Item",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] storeNames = new String[storesArr.size()];
        Store[] stores = new Store[storesArr.size()];
        // displays list of stores
        for (int i = 0; i < stores.length; i++) {
            stores[i] = storesArr.get(i);
            storeNames[i] = storesArr.get(i).getName();
        }
        // chooses the store that they would message
        String storeChoice;
        do {
            storeChoice = (String) JOptionPane.showInputDialog(null,
                    "Pick which store you would like to purchase from",
                    "Buy Product", JOptionPane.PLAIN_MESSAGE, null, storeNames, null);
            if (storeChoice.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cannot select an empty value!",
                        "Buy Product", JOptionPane.ERROR_MESSAGE);
                storeChoice = "";
            }
            if (storeChoice == null) {
                JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        } while (storeChoice.isEmpty());

        Store store = null;
        for (int j = 0; j < stores.length; j++) {
            if (storeChoice.equals(stores[j].getName())) {
                store = stores[j];
            }
        }
        ArrayList<String> storeProductsArr = store.getProducts();
        String[] storeProducts = new String[storeProductsArr.size()];
        for (int i = 0; i < storeProducts.length; i++) {
            storeProducts[i] = storeProductsArr.get(i);
        }
        String productChoice;
        do {
            productChoice = (String) JOptionPane.showInputDialog(null,
                    "Pick what product you would like to purchase",
                    "Buy Product", JOptionPane.PLAIN_MESSAGE, null, storeProducts, null);
            if (productChoice.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cannot select an empty value!",
                        "Buy Product", JOptionPane.ERROR_MESSAGE);
                productChoice = "";
            }
            if (productChoice == null) {
                JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        } while (productChoice.isEmpty());
        cart.add(productChoice);
    }
}
