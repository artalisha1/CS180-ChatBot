import java.io.*;
import java.net.Socket;
/**
 * Project 5: ClientHandler.java
 *
 * Calls the methods in SharedArrayLists
 *
 * @author Joey, Alisha, Bryan, and Shrung
 *
 * @version 12/10/2023
 *
 */
public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private SharedArrayLists sharedObject;
    public ClientHandler(Socket clientSocket, SharedArrayLists sharedObject) {
        this.clientSocket = clientSocket;
        this.sharedObject = sharedObject;
    }
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            while (1 + 1 == 2) {
                sharedObject.saveData();
                String input = reader.readLine(); // Info from the client class about method to use and parameters
                String instruction = input.split(":")[0]; // This is the method in this class that will be called
                switch (instruction) {
                    case "login":
                        boolean successfull = sharedObject.login(input.split(" ")[1], input.split(" ")[2]);
                        writer.println(String.valueOf(successfull));
                        writer.flush();
                        break;
                    case "typeOfUser":
                        String infoString = input.split(":")[1];
                        String[] info = infoString.split(" ");
                        sharedObject.typeOfUserAssign(info);
                        break;
                    case "getUserType":
                        String type = sharedObject.mapUser(input.split(":")[1]);
                        writer.println(type);
                        writer.flush();
                        break;
                    case "accountExist":
                        boolean exists = sharedObject.accountExist(input.split(":")[1]);
                        writer.println(String.valueOf(exists));
                        writer.flush();
                        break;
                    case "getCustomers":
                        outputStream.writeObject(sharedObject.getCustomers());
                        outputStream.flush();
                        outputStream.reset();
                        break;
                    case "getStores":
                        outputStream.writeObject(sharedObject.getStores());
                        outputStream.flush();
                        outputStream.reset();
                        break;
                    case "getSellers":
                        outputStream.writeObject(sharedObject.getSellers());
                        outputStream.flush();
                        outputStream.reset();
                        break;
                    case "addStore":
                        String storeInfo = input.split(":")[1];
                        String email = storeInfo.split(" ")[0];
                        String storeName = storeInfo.split(" ")[1];
                        sharedObject.addToStores(email,storeName);
                        break;
                    case "getUsers":
                        outputStream.writeObject(sharedObject.getUserList());
                        outputStream.flush();
                        outputStream.reset();
                        break;
                    case "addMessage":
                        String messageInfo = input.substring(11);
                        String[] splitInfo = messageInfo.split(";");
                        if (splitInfo.length > 1) {
                            String userEmail = messageInfo.split(";")[1];
                            String message = messageInfo.split(";")[0];
                            sharedObject.addReceivedMessage(userEmail, message);
                        }
                        break;
                    case "editMessage":

                        String newMessageInfo = input.substring(12);
                        String newUserEmail = newMessageInfo.split(";")[1];
                        String newMessage = newMessageInfo.split(";")[0];
                        sharedObject.deleteReceivedMessage(newUserEmail);
                        sharedObject.addReceivedMessage(newUserEmail, newMessage);
                        break;
                    case "addProduct":
                        String productInfo = input.split(":")[1];
                        String store = productInfo.split(";")[0];
                        String product = productInfo.split(";")[1];
                        sharedObject.addProduct(store, product);
                        break;
                    case "block":
                        String blockInfo = input.split(":")[1];
                        String curEmail = blockInfo.split(";")[0];
                        String blockEmail = blockInfo.split(";")[1];
                        sharedObject.block(curEmail, blockEmail);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
