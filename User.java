import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
/**
 * Project 5: User.java
 *
 * Defines a User object
 *
 * @author Joey, Alisha, Bryan, and Shrung
 *
 * @version 12/10/2023
 *
 */
public class User implements Serializable {
    private String email;
    private String password;
    private ArrayList<String> sentMessages = new ArrayList<String>(); // Might need to update this to a Message
    private ArrayList<String> censoredWords = new ArrayList<>();
    private ArrayList<String> replacementWords = new ArrayList<>();
    // object which has String message and User receiver
    private ArrayList<String> receivedMessages = new ArrayList<String>();
    private ArrayList<User> blockedUsers = new ArrayList<>();
    // First arraylist is the list of users the user has message and the second is how many times they did (could
    // have used a hashmap)
    private ArrayList<User> usersMessaged = new ArrayList<>();
    private ArrayList<Integer> messagesSentPerUser = new ArrayList<>();
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public ArrayList<String> getReceivedMessages() {
        return this.receivedMessages;
    }
    public ArrayList<String> getSentMessages() {
        return this.sentMessages;
    }
    // This is needed because the sender's name is supposed to be next to each message
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    // For editing the account details
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String sendMessage(User destination, String message) {
        if (!this.hasBeenBlocked(destination)) {
            message = this.censorMessage(message);

            // corrects message if file
            if (message.contains("_")) {
                String[] messageParts = message.split("_");
                String newMessage = "";
                for (String s: messageParts) {
                    newMessage += s + "\n";
                }
                this.sentMessages.add(this.getTime() + " | " + this.getEmail() + " | " +
                        destination.getEmail() + ": " + newMessage);
            } else {
                this.sentMessages.add(this.getTime() + " | " + this.getEmail()
                        + " | " + destination.getEmail() + ": " + message);
            }
            destination.receiveMessage(this.getTime() + " | " +
                    this.getEmail() + " | " + destination.getEmail() + ": " + message);
            return (this.getTime() + " | " + this.getEmail() + " | " + destination.getEmail() + ": " + message);
        }
        return null;
    }
    public void receiveMessage(String message) {
        String newMessage = this.censorMessage(message);
        this.receivedMessages.add(newMessage);
    }
    public String sendFileMessage(User destination, String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line;
            String message = "";
            while ((line = br.readLine()) != null) {
                message += line + "_";
            }
            JOptionPane.showMessageDialog(null, "Your file has been uploaded and sent!",
                    "Send File", JOptionPane.INFORMATION_MESSAGE);
            return this.sendMessage(destination, message);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "We encountered an error. " +
                            "Please try again with a different file.",
                    "Send File", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    public void exportMessages(ArrayList<User> users, User currentUser) {
        String path;
        do {
            path = JOptionPane.showInputDialog(null,
                    "Enter the path of the file you can to export the messages to:",
                    "Export Message", JOptionPane.QUESTION_MESSAGE);
            if (path.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Cannot enter empty path!",
                        "Export Message", JOptionPane.ERROR_MESSAGE);
                path = "";
            }
            if (path == null) {
                JOptionPane.showMessageDialog(null, "Thank you!",
                        "Farewell", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        } while (path.isEmpty());
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(path)))) {
            pw.println("Time, Sender, Recipient, Message");
            ArrayList<String> receivedMessages = null;
            if (users.size() > 0) {
                for (User u : users) {
                    if (u.getEmail().equals(currentUser.getEmail())) {
                        receivedMessages = u.getReceivedMessages();
                    }
                }
            }
            if (receivedMessages == null) {
                return;
            }
            if (!receivedMessages.isEmpty()) {
                for (String message : receivedMessages) {
                    // Since CSV files separate by comma
                    message = message.replace(" | ", ", ");
                    message = message.replace(": ", ", ");
                    pw.println(message);
                }
            }
            for (String message : this.sentMessages) {
                message = message.replace(" | ", ", ");
                message = message.replace(": ", ", ");
                pw.println(message);
            }
            JOptionPane.showMessageDialog(null, "We have finished exporting your " +
                    "conversation to the file.", "Send File", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String editMessage(User destination, String newMessage) {
        this.sentMessages.remove(this.sentMessages.size() - 1);
        // destination.receivedMessages.remove(destination.receivedMessages.size() - 1); // Deletes the last message
        return this.sendMessage(destination, newMessage) + ";" + destination.getEmail();
    }
    public void deleteMessage(User destination, String message) {
        this.sentMessages.remove(this.sentMessages.size() - 1); // Deletes the message in the sender's sentMessages
        // destination.receivedMessages.remove(destination.receivedMessages.size() - 1); // Deletes the message in the
        // receiver's receivedMessages
    }
    public void block(User user) {
        this.blockedUsers.add(user);
    }
    public String getTime() {
        Date currentTime = new Date();
        return currentTime.getMonth() + "/" + currentTime.getDate() + " " + currentTime.getHours() + ":" +
                String.format("%02d", currentTime.getMinutes());
        // String.format() is used so the minutes come out like 08 instead of 8
    }
    // Checks if this user has been blocked by the person they are talking to
    public boolean hasBeenBlocked(User user) {
        if (user.getBlockedUsers().size() > 0) {
            for (User blockedUser : user.getBlockedUsers()) {
                if (this.equals(blockedUser)) {
                    return true; // True = they are blocked
                }
            }
        }
        return false; // They are not blocked
    }
    public boolean equals(User user) {
        return user.getEmail().equals(this.getEmail())? true: false;
        // True if they are equal and false otherwise
    }
    public ArrayList<User> getBlockedUsers() {
        return this.blockedUsers;
    }
    public void censor(String word, String replacement) {
        this.censoredWords.add(word);
        if (replacement.equals("*")) {
            for (int i = 1; i < word.length(); i++) {
                replacement += "*";
            }
        }
        this.replacementWords.add(replacement);
    }
    public String censorMessage(String message) {
        String newMessage = "";
        if (this.censoredWords.size() > 0) {
            for (int a = 0; a < this.censoredWords.size(); a++) {
                int pos;
                while ((pos = message.toLowerCase().indexOf(this.censoredWords.get(a).toLowerCase())) >= 0) {
                    newMessage = message.substring(0, pos);
                    newMessage += this.replacementWords.get(a);
                    newMessage += message.substring(pos + this.censoredWords.get(a).length());
                    message = newMessage;
                }
            }
        }
        // It used to return an empty string when the list of censoredWords was 0
        return newMessage.length() == 0 ? message : newMessage;
    }
}
