/**
 * Project 5: StoreMessages.java
 *
 * Methods to store messages
 *
 * @author Joey, Alisha, Bryan, and Shrung
 *
 * @version 12/10/2023
 *
 */
public class StoreMessages {
    private String name;

    private int count;

    private int sent;

    public StoreMessages(String name) {
        this.name = name;
        count = 0;
        sent = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        count++;
    }

    public int getSent() {
        return sent;
    }

    public void increaseSent() {
        sent++;
    }
}
