import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
/**
 * Project 5: Client.java
 *
 * The Client class will send and receive information to the ClientHandler class. It will not store any data.
 *
 * @author Joey, Alisha, Bryan, and Shrung
 *
 * @version 12/10/2023
 *
 */
public class Client {
    // Static variables
    static String[] buttons = {"Cancel", "Refresh"};
    static Socket socket;
    static BufferedReader reader;
    static PrintWriter writer;
    static ObjectInputStream ois;
    static {
        try {
            socket = new Socket("localhost", 4242);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // gets customers ArrayList from server
    public static ArrayList<Customer> getCustomers() throws IOException {
        writer.println("getCustomers");
        writer.flush();
        ArrayList<Customer> customers;
        try {
            customers = (ArrayList<Customer>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
    // gets customers ArrayList from server
    public static ArrayList<Store> getStores() throws IOException {
        writer.println("getStores");
        writer.flush();
        ArrayList<Store> stores;
        try {
            stores = (ArrayList<Store>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return stores;
    }
    // gets sellers ArrayList from server
    public static ArrayList<Seller> getSellers() throws IOException {
        writer.println("getSellers");
        writer.flush();
        ArrayList<Seller> sellers;
        try {
            sellers = (ArrayList<Seller>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sellers;
    }
    // gets userList from server
    public static ArrayList<User> getUsers() throws IOException {
        writer.println("getUsers");
        writer.flush();
        ArrayList<User> users;
        try {
            users = (ArrayList<User>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    /*
        @description method for the user to login (is a method because you log in after you create an account or have
         already made an account)
        @return email + " " + password so I don't need to make static variables and change the while loop condition
     */
    public static String login() throws IOException {
        //just asks user to enter email and password and sends server login information
        // Gets the email
        String email;
        do {
            //asks user to enter their email
            email = JOptionPane.showInputDialog(null,
                    "Enter your email to login", "Login", JOptionPane.QUESTION_MESSAGE);
            //if empty email is entered --> error message is shown
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cannot enter empty input!",
                        "Email", JOptionPane.ERROR_MESSAGE);
                email = "";
            }
            //if invalid email is entered --> error message is shown
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email!",
                        "Email", JOptionPane.ERROR_MESSAGE);
                email = "";
            }
            //if user exits --> farewell message is shown
            if (email == null) {
                JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                        JOptionPane.INFORMATION_MESSAGE);
                return "stop";
            }
        } while (email.isEmpty());
        // Gets the password
        String password;
        do {
            //asks user to enter their password
            password = JOptionPane.showInputDialog(null,
                    "Enter your password to login in", "Login", JOptionPane.QUESTION_MESSAGE);
            //if empty password is entered --> error message is shown
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cannot enter empty input!",
                        "Password", JOptionPane.ERROR_MESSAGE);
                password = "";
            }
            //if user exits --> farewell message is shown
            if (password == null) {
                JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                        JOptionPane.INFORMATION_MESSAGE);
                return "stop";
            }
        } while (password.isEmpty());
        writer.println("login: " + email + " " + password);
        writer.flush();
        boolean authenticated = Boolean.parseBoolean(reader.readLine()); // True if the login is correct
        if (authenticated) {
            writer.println("getUserType: " + email);
            writer.flush();
            String typeOfAccount = reader.readLine(); // True if the login is correct
            return (email + " " + password + typeOfAccount);
        }
        return null;
    }
    public static void main(String[] args) throws IOException{
        boolean continueProgram = true;
        // gets the type of the account
        String typeOfAccount = "";
        do {
            //welcome message
            int begin;
            String[] beginButton = {"Ok"};
            begin = JOptionPane.showOptionDialog(null,
                    "Welcome to ChatBot!", "Welcome",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    beginButton, null);
            if (begin == -1) {
                JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // User that is currently using the application
            User currentUser = null;
            boolean exist = true;
            do {
                // asks whether they would like to create an account or login
                int create;
                String[] createButtons = {"Create", "Login"};
                create = JOptionPane.showOptionDialog(null,
                        "Would you like to create an account or login?", "Create/Login",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        createButtons, null);
                if (create == -1) {
                    JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (create == 0) { //if create button is pressed
                    int typeAccountInt;
                    String[] account = {"Seller", "Customer"};
                    typeAccountInt = JOptionPane.showOptionDialog(null,
                            "Would you like to create a seller account or a customer account?",
                            "Create Account", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                            account, null);
                    if (typeAccountInt == 0) {
                        typeOfAccount = "seller";
                    } else if (typeAccountInt == 1) {
                        typeOfAccount = "customer";
                    } else {
                        JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    //enter email
                    String email;
                    do {
                        //asks user to enter their email
                        email = JOptionPane.showInputDialog(null,
                                "Enter your email", "Email", JOptionPane.QUESTION_MESSAGE);
                        //if empty email is entered --> error message is shown
                        if (email.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Cannot enter empty input!",
                                    "Email", JOptionPane.ERROR_MESSAGE);
                            email = "";
                        }
                        //if invalid email is entered --> error message is shown
                        if (!email.contains("@") || !email.contains(".")) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid email!",
                                    "Email", JOptionPane.ERROR_MESSAGE);
                            email = "";
                        }
                        //if user exits --> farewell message is shown
                        if (email == null) {
                            JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    } while (email.isEmpty());
                    //enter password
                    String password;
                    do {
                        //asks user to enter their password
                        password = JOptionPane.showInputDialog(null,
                                "Enter your password", "Password", JOptionPane.QUESTION_MESSAGE);
                        //if empty password is entered --> error message is shown
                        if (password.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Cannot enter empty input!",
                                    "Password", JOptionPane.ERROR_MESSAGE);
                            password = "";
                        }
                        //if user exits --> farewell message is shown
                        if (password == null) {
                            JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    } while (password.isEmpty());
                    //enter name
                    String name;
                    do {
                        //asks user to enter their name
                        name = JOptionPane.showInputDialog(null,
                                "Enter the name for your account", "Name", JOptionPane.QUESTION_MESSAGE);
                        name = name.toLowerCase();
                        //if empty name is entered --> error message is shown
                        if (name.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Cannot enter empty input!",
                                    "Name", JOptionPane.ERROR_MESSAGE);
                            name = "";
                        }
                        //if user exits --> farewell message is shown
                        if (name == null) {
                            JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    } while (name.isEmpty());
                    //check if email and password exists in userList
                    //if not create account
                    // if exists, do not create account
                    writer.println("accountExist: " + email);
                    writer.flush();
                    exist = Boolean.parseBoolean(reader.readLine());
                    if (!exist) { // Account does not exist
                        writer.println("typeOfUser:" + email + " " + typeOfAccount + " " + password + " " + name);
                        writer.flush();
                    } else {
                        JOptionPane.showMessageDialog(null, "Your Account Already Exists!",
                                "Account Exists", JOptionPane.ERROR_MESSAGE);
                    }
                    String loginResults = login();
                    if (loginResults != null) {
                        if (loginResults.equals("stop")) { // User exited
                            return;
                        } else { // Successfully logged in
                            String[] loginInfo = loginResults.split(" ");
                            email = loginInfo[0];
                            password = loginInfo[1];
                            currentUser = new User(email, password);
                            if (typeOfAccount.equals("seller")) {
                                currentUser = new Seller(email, password, name);
                            } else {
                                currentUser = new Customer(email, password, name);
                            }
                        } // Last option is invalid credentials which they will have to enter again
                    }
                } else { // Logging in
                    String loginResults = login();
                    if (loginResults != null) {
                        if (loginResults.equals("stop")) { // User exited
                            return;
                        } else { // Successfully logged in
                            String[] loginInfo = loginResults.split(" ");
                            String email = loginInfo[0];
                            String password = loginInfo[1];
                            if (typeOfAccount.equals("seller")) {
                                currentUser = new Seller(email, password, "");
                            } else {
                                currentUser = new Customer(email, password, "");
                            }
                        }
                    } // Last option is invalid credentials which they will have to enter again
                }
            } while (currentUser == null);

            boolean continueAccount = true;
            if (typeOfAccount.equals("customer")) { // if current user is customer
                do {
                    String[] options = {"Send Message", "View Message", "Edit message", "Export message", "Delete " +
                            "message", "Block Seller", "Buy product", "Log out"};
                    String selected = "";
                    do {
                        selected = (String) JOptionPane.showInputDialog(null,
                                "Select An Option", "Select", JOptionPane.PLAIN_MESSAGE,
                                null, options, null);
                        if (selected.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Cannot select empty option!",
                                    "Select", JOptionPane.ERROR_MESSAGE);
                            selected = "";
                        } else if (selected == null) {
                            JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                    JOptionPane.INFORMATION_MESSAGE);
                            continueProgram = false;
                            return;
                        }
                    } while (selected.isEmpty());
                    //goes on to switch statement and executes methods according to the selection
                    switch (selected) {
                        case "Send Message": // send message
                            String response = ((Customer) currentUser).sendMessage(getStores(),getSellers());
                            if (response != null) {
                                writer.println("addMessage:" + response);
                                writer.flush();
                            }
                            break;
                        case "View Message":
                            viewMessage(currentUser);
                            break;
                        case "Edit message": // edit message
                            String email;
                            do {
                                //asks user for the email of the seller
                                email = JOptionPane.showInputDialog(null,
                                        "What is the email of the seller that you last messaged?",
                                        "Edit Message", JOptionPane.QUESTION_MESSAGE);
                                if (!email.contains("@") || !email.contains(".")) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid email!", "Edit Message",
                                            JOptionPane.ERROR_MESSAGE);
                                    email = "";
                                }
                                //if empty email is entered --> error message is shown
                                else if (email.isEmpty()) {
                                    JOptionPane.showMessageDialog(null,
                                            "Cannot enter empty input!", "Edit Message",
                                            JOptionPane.ERROR_MESSAGE);
                                    email = "";
                                }
                                //if user exits --> farewell message is shown
                                else if (email == null) {
                                    JOptionPane.showMessageDialog(null,
                                            "Thank you!", "Farewell",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                } else {
                                    Seller seller = null;
                                    ArrayList<Seller> sellers = getSellers();
                                    for (Seller sel : sellers) {
                                        if (email.equals(sel.getEmail())) {
                                            seller = sel;
                                        }
                                    }
                                    String newMessage;
                                    do {
                                        //asks user to for the message they would like to change
                                        newMessage = JOptionPane.showInputDialog(null,
                                                "What would you like to change the message to?",
                                                "Edit Message", JOptionPane.QUESTION_MESSAGE);
                                        //if empty message is entered --> error message is shown
                                        if (newMessage.isEmpty()) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Cannot enter empty message!",
                                                    "Edit Message", JOptionPane.ERROR_MESSAGE);
                                            newMessage = "";
                                        }
                                        //if user exits --> farewell message is shown
                                        if (newMessage == null) {
                                            JOptionPane.showMessageDialog(null, "Thank you!",
                                                    "Farewell", JOptionPane.INFORMATION_MESSAGE);
                                            return;
                                        }
                                    } while (newMessage.isEmpty());

                                    if (seller != null) {
                                        String response2 = currentUser.editMessage(seller, newMessage);
                                        writer.println("editMessage:" + response2);
                                        writer.flush();
                                    } else {
                                        JOptionPane.showMessageDialog(null,
                                                "That user could not be found!", "Edit Message",
                                                JOptionPane.ERROR_MESSAGE);
                                        email = "";
                                    }
                                }
                            } while (email.isEmpty());
                            break;
                        case "Export message": // export message
                            ArrayList<User> users = getUsers();
                            currentUser.exportMessages(users, currentUser);
                            break;
                        case "Delete message": // delete message
                            String content;
                            do {
                                //asks user what the contents of the message they want to delete
                                content = JOptionPane.showInputDialog(null,
                                        "What are the contents of the message that you want to delete?",
                                        "Delete Message", JOptionPane.QUESTION_MESSAGE);
                                //if empty content is entered --> error message is shown
                                if (content.isEmpty()) {
                                    JOptionPane.showMessageDialog(null,
                                            "Cannot enter empty message!",
                                            "Delete Message", JOptionPane.ERROR_MESSAGE);
                                    content = "";
                                }
                                //if user exits --> farewell message is shown
                                if (content == null) {
                                    JOptionPane.showMessageDialog(null, "Thank you!",
                                            "Farewell", JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                            } while (content.isEmpty());
                            currentUser.deleteMessage(currentUser, content);
                            break;
                        case "Block Seller": // block seller
                            String sellerEmail;
                            do {
                                //asks user what seller they would like to block
                                sellerEmail = JOptionPane.showInputDialog(null,
                                        "What is the email of the user that you want to block?",
                                        "Block Seller", JOptionPane.QUESTION_MESSAGE);
                                //if empty password is entered --> error message is shown
                                if (sellerEmail.isEmpty()) {
                                    JOptionPane.showMessageDialog(null,
                                            "Cannot enter empty message!",
                                            "Block Seller", JOptionPane.ERROR_MESSAGE);
                                    sellerEmail = "";
                                }
                                //if invalid email is entered --> error message is shown
                                else if (!sellerEmail.contains("@") || !sellerEmail.contains(".")) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid email!", "Block Seller",
                                            JOptionPane.ERROR_MESSAGE);
                                    sellerEmail = "";
                                }
                                //if user exits --> farewell message is shown
                                else if (sellerEmail == null) {
                                    JOptionPane.showMessageDialog(null, "Thank you!",
                                            "Farewell", JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                                else {
                                    ArrayList<Seller> sellers = getSellers();
                                    Seller seller1 = null;
                                    for (Seller value : sellers) {
                                        if (sellerEmail.equals(value.getEmail())) {
                                            seller1 = value;
                                        }
                                    }
                                    if (seller1 != null) {
                                        currentUser.block(seller1);
                                        writer.println("block:" + currentUser.getEmail() + ";" + seller1.getEmail());
                                        writer.flush();
                                    } else {
                                        //if account isn't found --> error message is printed
                                        JOptionPane.showMessageDialog(null,
                                                "That account could not be found!", "Block Seller",
                                                JOptionPane.ERROR_MESSAGE);
                                        sellerEmail = "";
                                    }
                                }
                            } while (sellerEmail.isEmpty());
                            break;
                        case "Buy product": // buy product
                            ((Customer) currentUser).buyItem(getStores());
                            break;
                        case "Log out": // logout
                            continueAccount = false;
                            break;
                    }
                } while (continueAccount);
            } else { // If the user is a seller
                do {
                    String[] options = {"Send Message", "View Message", "Create Store", "Edit message", "Export " +
                            "message", "Delete message",
                            "Block Customer", "Add product", "Log out"};
                    String selected = "";
                    do {
                        selected = (String) JOptionPane.showInputDialog(null,
                                "Select An Option", "Select", JOptionPane.PLAIN_MESSAGE,
                                null, options, null);
                        if (selected.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Cannot select empty option!",
                                    "Select", JOptionPane.ERROR_MESSAGE);
                            selected = "";
                        } else if (selected == null) {
                            JOptionPane.showMessageDialog(null, "Thank you!", "Farewell",
                                    JOptionPane.INFORMATION_MESSAGE);
                            continueProgram = false;
                            return;
                        }
                    } while (selected.isEmpty());
                    switch (selected) {
                        case "Send Message": // send message
                            ArrayList<Customer> customers = getCustomers();
                            String response = ((Seller) currentUser).sendMessage(customers);
                            if (response != null) {
                                writer.println("addMessage:" + response);
                                writer.flush();
                            }
                            break;
                        case "View Message":
                            viewMessage(currentUser);
                            break;
                        case "Create Store": // create store
                            String storeName;
                            do {
                                //asks user what they would like name their store
                                storeName = JOptionPane.showInputDialog(null,
                                        "Enter Store Name",
                                        "Create Store", JOptionPane.QUESTION_MESSAGE);
                                //if empty store name is entered --> error message is shown
                                if (storeName.isEmpty()) {
                                    JOptionPane.showMessageDialog(null,
                                            "Cannot enter empty store name!",
                                            "Create Store", JOptionPane.ERROR_MESSAGE);
                                    storeName = "";
                                }
                                //if user exits --> farewell message is shown
                                if (storeName == null) {
                                    JOptionPane.showMessageDialog(null, "Thank you!",
                                            "Farewell", JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }

                            } while (storeName.isEmpty());
                            writer.println("addStore:" + currentUser.getEmail() + " " + storeName);
                            writer.flush();

                            break;
                        case "Edit message": // edit message
                            String email;
                            do {
                                //asks user to enter the email of the customer
                                email = JOptionPane.showInputDialog(null,
                                        "What is the email of the customer that you last messaged?",
                                        "Edit Message", JOptionPane.QUESTION_MESSAGE);
                                //if invalid email is entered --> error message is shown
                                if (!email.contains("@") || !email.contains(".")) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid email!", "Edit Message",
                                            JOptionPane.ERROR_MESSAGE);
                                    email = "";
                                }
                                //if empty email is entered --> error message is shown
                                else if (email.isEmpty()) {
                                    JOptionPane.showMessageDialog(null,
                                            "Cannot enter empty input!", "Edit Message",
                                            JOptionPane.ERROR_MESSAGE);
                                    email = "";
                                }
                                //if user exits --> farewell message is shown
                                else if (email == null) {
                                    JOptionPane.showMessageDialog(null,
                                            "Thank you!", "Farewell",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                                else {
                                    Customer cus = null;
                                    customers = getCustomers();
                                    for (Customer c : customers) {
                                        if (email.equals(c.getEmail())) {
                                            cus = c;
                                        }
                                    }
                                    String newMessage;
                                    do {
                                        //asks user what they would like to change the message to
                                        newMessage = JOptionPane.showInputDialog(null,
                                                "What would you like to change the message to?",
                                                "Edit Message", JOptionPane.QUESTION_MESSAGE);
                                        //if empty message is entered --> error message is shown
                                        if (newMessage.isEmpty()) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Cannot enter empty message!",
                                                    "Edit Message", JOptionPane.ERROR_MESSAGE);
                                            newMessage = "";
                                        }
                                        //if user exits --> farewell message is shown
                                        if (newMessage == null) {
                                            JOptionPane.showMessageDialog(null, "Thank you!",
                                                    "Farewell", JOptionPane.INFORMATION_MESSAGE);
                                            return;
                                        }
                                    } while (newMessage.isEmpty());

                                    if (cus != null) {
                                        String response3 = currentUser.editMessage(cus, newMessage);
                                        writer.println("editMessage:" + response3);
                                        writer.flush();
                                    } else {
                                        JOptionPane.showMessageDialog(null,
                                                "That user could not be found!", "Edit Message",
                                                JOptionPane.ERROR_MESSAGE);
                                        email = "";
                                    }
                                }
                            } while (email.isEmpty());
                            break;
                        case "Export message": // export message
                            ArrayList<User> users = getUsers();
                            currentUser.exportMessages(users, currentUser);
                            break;
                        case "Delete message": // delete message
                            String content;
                            do {
                                //asks user what the contents of the message they want to delete
                                content = JOptionPane.showInputDialog(null,
                                        "What are the contents of the message that you want to delete?",
                                        "Delete Message", JOptionPane.QUESTION_MESSAGE);
                                //if empty content is entered --> error message is shown
                                if (content.isEmpty()) {
                                    JOptionPane.showMessageDialog(null,
                                            "Cannot enter empty message!",
                                            "Delete Message", JOptionPane.ERROR_MESSAGE);
                                    content = "";
                                }
                                //if user exits --> farewell message is shown
                                if (content == null) {
                                    JOptionPane.showMessageDialog(null, "Thank you!",
                                            "Farewell", JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                            } while (content.isEmpty());
                            currentUser.deleteMessage(currentUser, content);
                            break;
                        case "Block Customer": // Block customer
                            String customerEmail;
                            do {
                                //asks user what seller they would like to block
                                customerEmail = JOptionPane.showInputDialog(null,
                                        "What is the email of the user that you want to block?",
                                        "Block Customer", JOptionPane.QUESTION_MESSAGE);
                                //if empty email is entered --> error message is shown
                                if (customerEmail.isEmpty()) {
                                    JOptionPane.showMessageDialog(null,
                                            "Cannot enter empty message!",
                                            "Block Customer", JOptionPane.ERROR_MESSAGE);
                                    customerEmail = "";
                                }
                                //if invalid email is entered --> error message is shown
                                else if (!customerEmail.contains("@") || !customerEmail.contains(".")) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid email!", "Block Customer",
                                            JOptionPane.ERROR_MESSAGE);
                                    customerEmail = "";
                                }
                                //if user exits --> farewell message is shown
                                else if (customerEmail == null) {
                                    JOptionPane.showMessageDialog(null, "Thank you!",
                                            "Farewell", JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                                else {
                                    customers = getCustomers();
                                    Customer customer1 = null;
                                    for (Customer c : customers) {
                                        if (customerEmail.equals(c.getEmail())) {
                                            customer1 = c;
                                        }
                                    }
                                    if (customer1 != null) {
                                        currentUser.block(customer1);
                                        writer.println("block:" + currentUser.getEmail() + ";" + customer1.getEmail());
                                        writer.flush();
                                    } else {
                                        //if account isn't found --> error message is printed
                                        JOptionPane.showMessageDialog(null,
                                                "That account could not be found!", "Block Customer",
                                                JOptionPane.ERROR_MESSAGE);
                                        customerEmail = "";
                                    }
                                }
                            } while (customerEmail.isEmpty());
                            break;
                        case "Add product": // add product
                            String[] pInfo = ((Seller) (currentUser)).addProduct(getSellers());
                            if (pInfo != null && pInfo.length > 1) {
                                writer.println("addProduct:" + pInfo[1] + ";" + pInfo[0]);
                                writer.flush();
                            }
                            break;
                        case "Log out": // logout
                            continueAccount = false;
                            break;
                    }
                } while (continueAccount);
            }
        } while (continueProgram);
        writer.println("end:");
        writer.flush();
    }
    public static void viewMessage(User currentUser) {
        String messagesCombined = "";
        ArrayList<User> users;
        try {
            users = getUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> receivedMessages = null;
        if (users.size() > 0) {
            for (User u : users) {
                if (u.getEmail().equals(currentUser.getEmail())) {
                    receivedMessages = u.getReceivedMessages();
                }
            }
        }
        for (int i = 0; i < receivedMessages.size(); i++) {
            messagesCombined += receivedMessages.get(i) + "\n";
        }
        JOptionPane.showMessageDialog(null,
                messagesCombined, "View Messages",
                JOptionPane.INFORMATION_MESSAGE);
        int refresh;
        refresh = JOptionPane.showOptionDialog(null, "Would you like to " +
                        "refresh?", "Refresh",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                buttons, buttons[1]);
        // Goes back to view message when the user clicks refresh
        if (refresh == 1) {
            viewMessage(currentUser);
        }
    }
}
