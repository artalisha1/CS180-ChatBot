import java.io.*;
import java.util.ArrayList;
/**
 * Project 5: SharedArrayLists.java
 *
 * All of the static objects and the methods modifying those objects
 *
 * @author Joey, Alisha, Bryan, and Shrung
 *
 * @version 12/10/2023
 *
 */
public class SharedArrayLists {
    private volatile ArrayList<User> userList = new ArrayList<>();
    private volatile ArrayList<Seller> sellers = new ArrayList<>();
    private volatile ArrayList<Customer> customers = new ArrayList<>();
    private volatile ArrayList<Store> stores = new ArrayList<>();

    public synchronized void block(String currentEmail, String blockEmail) {
        if (userList.size() > 0) {
            for (User u : userList) {
                if (u.getEmail().equals(currentEmail)) {
                    for (User us : userList) {
                        if (us.getEmail().equals(blockEmail)) {
                            u.getBlockedUsers().add(us);
                        }
                    }
                }
            }
        }
    }
    public synchronized void addReceivedMessage(String email, String message) {
        boolean blocked = false;
        User recipient = null;
        String senderEmail = message.split(" ")[3];
        senderEmail = senderEmail.trim();
        if (userList.size() > 0) {
            for (User u : userList) {
                if (u.getEmail().equals(email)) {
                    recipient = u;
                }
            }
        }
        if (recipient.getBlockedUsers().size() > 0) {
            for (User blockedUser : recipient.getBlockedUsers()) {
                if (blockedUser.getEmail().equals(senderEmail)) {
                    blocked = true;
                }
            }
        }
        if (message.contains("_")) {
            String[] oldMessage = message.split("_");
            message = "";
            for (int i = 0; i < oldMessage.length; i++) {
                message += oldMessage[i] + "\n";
            }

        }
        if (!blocked) {
            recipient.getReceivedMessages().add(message);
            ArrayList<String> s = recipient.getReceivedMessages();
        }

    }
    public synchronized void deleteReceivedMessage(String email) {
        if (userList.size() > 0) {
            for (User u : userList) {
                if (u.getEmail().equals(email)) {
                    u.getReceivedMessages().remove(u.getReceivedMessages().size() - 1);
                }
            }
        }
    }

    // Add to Users
    public void addToUsers(User user) {
        userList.add(user);
    }

    // get arrayList of users
    public synchronized ArrayList<User> getUserList() {
        ArrayList<User> users = userList;
        return userList;
    }

    // Add to sellers
    public synchronized void addToSellers(Seller seller) {
        sellers.add(seller);
    }

    // get sellers
    public synchronized ArrayList<Seller> getSellers() {
        return sellers;
    }


    // add to arrayList of customers
    public synchronized void addToCustomers(Customer customer) {
        customers.add(customer);
    }

    // get sellers
    public synchronized ArrayList<Customer> getCustomers() {
        return customers;
    }


    public synchronized boolean addToStores(String email, String storeName) {
        if (sellers.size() > 0) {
            for (Seller s : sellers) {
                if (s.getEmail().equals(email)) {
                    stores.add(new Store(s, storeName));
                    s.getStores().add(new Store(s, storeName));
                    return true;
                }
            }
        }
        return false;
    }

    // get sellers
    public synchronized ArrayList<Store> getStores() {
        return stores;
    }

    public synchronized void addProduct(String store, String product) {
        if (stores.size() > 0) {
            for (Store s : stores) {
                if (s.getName().equals(store)) {
                    s.addProduct(product);
                }
            }
        }
    }

    public void loadData() {
        File f = new File("Userdata.txt");
        //saving the users to a file even when the program stops running
        //logic first read existing data and store into list
        //updates list as the program runs
        //clears the file and transfers the list data to the file
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            // if statement to check if there is anything in the file
            // if exists: take that data and update the list
            // if not: dont do anything and proceed
            if (f.length() != 0) {
                String line = bfr.readLine();
                String[] fields;
                while (line != null) {
                    fields = line.split(",");
                    userList.add(new User(fields[0], fields[1]));
                    line = bfr.readLine();
                }
            }
            //after user adds
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveData() {
        //take from list, clear file, then update the file
        try {
            //append = false so we clear the file
            FileOutputStream fos = new FileOutputStream(new File("Userdata.txt"), false);
            PrintWriter pw = new PrintWriter(fos);
            if (userList.size() > 0) {
                for (User u : userList) {
                    pw.println(u.getEmail() + "," + u.getPassword());
                }
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Checks to see if the user already exists in the user list or not (true = yes)
    public boolean accountExist(String email) {
        boolean exist = false;
        if (userList.size() > 0) {
            for (User u : userList) {
                if (u.getEmail().equals(email)) {
                    exist = true;
                }
            }
        }
        return exist;
    }

    // Adds the new user to the right list
    public void typeOfUserAssign(String[] info) {
        if (info[1].equals("seller")) {
            userList.add(new Seller(info[0], info[2], info[3])); // Adds this new account to the list of users
            sellers.add(new Seller(info[0], info[2], info[3])); // Adds this account to the list of sellers
        } else {
            customers.add(new Customer(info[0], info[2], info[3])); // Adds this account to the list of customers
            userList.add(new Customer(info[0], info[2], info[3])); // Adds this new account to the list of users
        }

    }

    // Assigns the user to their spot in the list with their type and returns the type of user
    public String mapUser(String email) {
        String typeOfUser = null;
        for (int i = 0; i < userList.size(); i++) {
            if (!customers.isEmpty()) {
                for (int j = 0; j < customers.size(); j++) {
                    if (email.equals(customers.get(j).getEmail())) {
                        typeOfUser = "customer";
                    }
                }
            }
            if (!(sellers == null || sellers.isEmpty())) {
                for (int j = 0; j < sellers.size(); j++) {
                    if (email.equals(sellers.get(j).getEmail())) {
                        typeOfUser = "seller";
                    }
                }
            }
        }
        return typeOfUser;
    }

    // Returns true is the email and password match what is in the userList
    public boolean login(String email, String password) {
        User currentUser = null;
        if (userList.size() > 0) {
            for (User u : userList) {
                if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                    currentUser = u;
                    mapUser(currentUser.getEmail());
                }
            }
        }
        if (currentUser == null) {
            return false;
        }
        return true;
    }
}
