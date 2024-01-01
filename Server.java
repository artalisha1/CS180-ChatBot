import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
 * Project 5: Server.java
 *
 * Calls all of the ClientHandler threads
 *
 * @author Joey, Alisha, Bryan, and Shrung
 *
 * @version 12/10/2023
 *
 */
public class Server {
    public static void main(String[] args) {
        SharedArrayLists sharedObject = new SharedArrayLists();
        try {
            ServerSocket serverSocket = new ServerSocket(4242); // ServerSocket bound to port 4242
            sharedObject.loadData();
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Wait for a client to connect
               // System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                // Create a new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket,sharedObject);
                new Thread(clientHandler).start();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            sharedObject.saveData();
        }
    }
}
