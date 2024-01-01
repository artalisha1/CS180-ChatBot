import javax.swing.*;
import java.io.Serializable;
import java.util.*;
/**
 * Project 5: Seller.java
 *
 * Defines a Seller object
 *
 * @author Joey, Alisha, Bryan, and Shrung
 *
 * @version 12/10/2023
 *
 */
public class Seller extends User implements Serializable {
    private String name;
    //array list of stores
    ArrayList<Store> stores = new ArrayList<>();

    public Seller(String email, String password, String name) {
        super(email, password);
        this.name = name;
    }

    //sends message to customer via String or file
    public String sendMessage(ArrayList<Customer> customers) {
        //asks user if they would like to view the customer list or search for a customer
        String entered;
        String[] options = {"List", "Search"};
        do  {
            entered = (String) JOptionPane.showInputDialog(null,
                    "Would you like to view the customer list or would you like to search for a customer? ",
                    "Send Message", JOptionPane.PLAIN_MESSAGE, null, options, null);
            if (entered == null) {
                JOptionPane.showMessageDialog(null, "You entered a null value!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return entered;
            } else if (entered.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cannot enter an select an empty option!",
                        "Send Message", JOptionPane.ERROR_MESSAGE);
                entered = "";
            }
        } while (entered.isEmpty());

        String customerName = "";
        boolean customerExists = false;
        switch (entered) {
            //if the user enter "list"
            case "List":
                //then get an arraylist of customer names
                ArrayList<String> customerNamesArr = new ArrayList<String>();
                for (int i = 0; i < customers.size(); i++) {
                    customerNamesArr.add(customers.get(i).getName());
                }
                String[] customerNames = new String[customerNamesArr.size()];
                for (int j = 0; j < customerNamesArr.size(); j++) {
                    customerNames[j] = customerNamesArr.get(j);
                }
                do {
                    customerName = (String) JOptionPane.showInputDialog(null,
                            "Please choose a Customer Name",
                            "Send Message", JOptionPane.PLAIN_MESSAGE, null, customerNames, null);
                    if (customerName == null) {
                        JOptionPane.showMessageDialog(null, "No user selected!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return entered;
                    }
                    if (customerName.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Cannot select an empty option!",
                                "Send Message", JOptionPane.ERROR_MESSAGE);
                        customerName = "";
                    }
                } while (customerName.isEmpty());
                customerExists = true;
                break;
            // if the user enters "search"
            case "Search":
                do {
                    //asks user what customer they would like the message
                    customerName = JOptionPane.showInputDialog(null,
                            "Enter the name you would like to search for", "Send Message",
                            JOptionPane.QUESTION_MESSAGE);
                    if (customerName == null) {
                        JOptionPane.showMessageDialog(null, "No user selected!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return entered;
                    }
                    if (customerName.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Cannot enter an empty name!",
                                "Send Message", JOptionPane.ERROR_MESSAGE);
                        customerName = "";
                    }
                    //checks if customer exists in arraylist customers
                    //this loop goes through customer arraylist and checks whether any of the names is the name the user would
                    // like to message
                    for (int j = 0; j < customers.size(); j++) {
                        if (customerName.toLowerCase().equals(customers.get(j).getName().toLowerCase())) {
                            customerExists = true;
                        } else {
                            customerExists = false;
                            JOptionPane.showMessageDialog(null, "That customer doesn't exist!",
                                    "Send Message", JOptionPane.ERROR_MESSAGE);
                            customerName = "";
                        }
                    }
                } while (customerName.isEmpty());
                break;
        }

        String selected = "";
        if (customerExists) {
            String[] options2 = {"Message", "File"};
            do {
                selected = (String) JOptionPane.showInputDialog(null,
                        "Would you like to send a message or a file?",
                        "Send Message", JOptionPane.PLAIN_MESSAGE, null, options2, null);
                if (selected == null) {
                    JOptionPane.showMessageDialog(null, "No option selected!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return entered;
                }
                if (selected.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Cannot select an empty value!",
                            "Send Message", JOptionPane.ERROR_MESSAGE);
                    selected = "";
                }
            } while (selected.isEmpty());
        }

        switch (selected) {
            //if the option chosen is "message"
            case "Message":
                String message;
                do {
                    message = JOptionPane.showInputDialog(null,
                            "What message would you like to send?", "Send Message",
                            JOptionPane.QUESTION_MESSAGE);
                    if (message == null) {
                        JOptionPane.showMessageDialog(null, "Message is null!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return entered;
                    }
                    if (message.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Cannot enter an empty message!",
                                "Send Message", JOptionPane.ERROR_MESSAGE);
                        message = "";
                    }
                    //calls sendMessage in parent User class with customer object and message
                    for (int j = 0; j < customers.size(); j++) {
                        if (customerName.toLowerCase().equals(customers.get(j).getName().toLowerCase())) {
                            return sendMessage(customers.get(j), message) + ";" + customers.get(j).getEmail();
                        }
                    }
                    //adds message to sentMessages arrayList field in user class
                    ArrayList<String> sentMessages = getSentMessages();
                    sentMessages.add(message);
                    // listStats(customers.get(j), message); // is this necessary?
                    return entered;
                } while (message.isEmpty());
            case "File":
                //if seller chooses filepath, then try-catch block will check if the filepath exists
                String messageFromFile = "";
                String filePath;
                do {
                    try {
                        filePath = JOptionPane.showInputDialog(null,
                                "What is the file path of the file you want to send?", "Send File",
                                JOptionPane.QUESTION_MESSAGE);
                        if (filePath == null) {
                            JOptionPane.showMessageDialog(null, "File path is null!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return entered;
                        }
                        if (filePath.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Cannot enter an empty filepath!",
                                    "Send File", JOptionPane.ERROR_MESSAGE);
                            filePath = "";
                        }
                        //if filepath exists then sendFileMessage in user parent class is called
                        for (int j = 0; j < customers.size(); j++) {
                            if (customerName.toLowerCase().equals(customers.get(j).getName().toLowerCase())) {
                                return sendFileMessage(customers.get(j), filePath) + ";" + customers.get(j).getEmail();
                            }
                        }
                        ArrayList<String> sentMessages = getSentMessages();
                        //adds message in file to sentMessages arraylist field in user class

                        //catches exception
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "That filepath doesn't exist!",
                                "Send File", JOptionPane.ERROR_MESSAGE);
                        filePath = "";
                    }
                } while (filePath.isEmpty());
                return entered;
        }
        return entered;
    }
    public String[] addProduct(ArrayList<Seller> sells) {
        ArrayList<Store> storesArr = null;

        // finds seller
        if (sells.size() > 0) {
            for (Seller s : sells) {
                if (s.getEmail().equals(this.getEmail())) {
                    storesArr = s.getStores();
                }
            }
        }
        Store[] stores = new Store[storesArr.size()];
        //array of store names
        String[] storeNames = new String[storesArr.size()];
        for (int i = 0; i < storesArr.size(); i++) {
            stores[i] = storesArr.get(i);
            storeNames[i] = storesArr.get(i).getName();
        }
        String store;
        String product;
        //asks which store the seller would like to add a product to
        do {
            store = (String) JOptionPane.showInputDialog(null,
                    "Which store would you like to add a product to?",
                    "Add Product", JOptionPane.PLAIN_MESSAGE, null, storeNames, null);
            if (store == null) {
                JOptionPane.showMessageDialog(null, "Store is null!", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
                return null;
            } else if (store.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cannot select an empty value!",
                        "Add Product", JOptionPane.ERROR_MESSAGE);
                store = "";
            } else {
                for (int j = 0; j < stores.length; j++) {
                    //goes through stores array and checks if the store name exists in stores
                    if (stores[j].getName().equals(store)) {
                        //if it does then the seller is asked what product they would like to add
                        do {
                            product = JOptionPane.showInputDialog(null,
                                    "What product would you like to add?", "Add Product",
                                    JOptionPane.QUESTION_MESSAGE);
                            //checks for valid input
                            if (product == null) {
                                JOptionPane.showMessageDialog(null, "Product is null!", "Error",
                                        JOptionPane.INFORMATION_MESSAGE);
                                return null;
                            } else if (product.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Cannot enter empty product!",
                                        "Add Product", JOptionPane.ERROR_MESSAGE);
                                product = "";
                            } else {
                                //add product using setter and getter method for product arraylist in store class
                                ArrayList<String> currentProducts = stores[j].getProducts();
                                currentProducts.add(product);
                                stores[j].setProducts(currentProducts);
                                return new String[]{product, stores[j].getName()};
                            }
                        } while (product.isEmpty());
                    }
                }
            }
        } while (store.isEmpty());
        return null;

    }

    //getter and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }
}
