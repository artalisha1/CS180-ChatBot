import java.io.Serializable;
import java.util.ArrayList;
/**
 * Project 5: Store.java
 *
 * Defines a Store object
 *
 * @author Joey, Alisha, Bryan, and Shrung
 *
 * @version 12/10/2023
 *
 */
public class Store implements Serializable {
    private Seller owner;
    private String name;
    private ArrayList<String> products = new ArrayList<>();
    public Store(Seller owner, String name) {
        this.owner = owner;
        this.name = name;
    }
    public Store() {

    }
    public Seller getOwner() {
        return owner;
    }
    public void setOwner(Seller owner) {
        this.owner = owner;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<String> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }
    public void addProduct(String s) {
        products.add(s);
    }
}
