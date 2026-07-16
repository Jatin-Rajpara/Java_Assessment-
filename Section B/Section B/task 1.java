  /*TOPS TECHNOLOGIES 
  ASSESSMENT FILE: JAVA, RDBMS & JDBC (M2-A1)

Task 1: Menu Item Class with Encapsulation 
	Build a well-encapsulated Java class that models a single item         on a food delivery platform's   menu,demonstrating access         control, constructor overloading, and formatted output.
Java code :*/

class MenuItem {
    private int itemId;
    private String name;
    private double price;
    private boolean isAvailable;
    public MenuItem() {
        this.price = 0.0;
        this.isAvailable = true;
    }
    public MenuItem(int itemId, String name, double price, boolean isAvailable) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }
    public int getItemId() {
        return itemId;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public void displayDetails() {
        if (isAvailable) {
            System.out.println(itemId + " - " + name + " - Rs." + price);
        } else {
            System.out.println(itemId + " - " + name + " [UNAVAILABLE] - Rs." + price);
        }
    }
}
public class Main {
    public static void main(String[] args) {

        MenuItem m1 = new MenuItem(101, "Pizza", 250, true);
        MenuItem m2 = new MenuItem(102, "Burger", 180, false);
        m1.displayDetails();
        m2.displayDetails();
    }
}
Output
	101 - Pizza - Rs.250.0
102 - Burger [UNAVAILABLE] -  Rs.180.0
