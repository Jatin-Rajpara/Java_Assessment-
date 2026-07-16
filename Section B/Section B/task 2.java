
import java.util.ArrayList;
import java.util.NoSuchElementException;

class MenuItem {
    private int itemId;
    private String name;
    private double price;
    private boolean isAvailable;

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
}
class ItemUnavailableException extends Exception {
    public ItemUnavailableException(String itemName) {
        super(itemName + " is currently unavailable.");
    }
}
class CartManager {
    ArrayList<MenuItem> cart = new ArrayList<>();
    public void addItem(MenuItem item) throws ItemUnavailableException {
        if (!item.isAvailable()) {
            throw new ItemUnavailableException(item.getName());
        }
        cart.add(item);
    }
    public void removeItem(int itemId) {
        boolean found = false;

        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getItemId() == itemId) {
                cart.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NoSuchElementException("Item ID " + itemId + " not found.");
        }
    }
    public void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            System.out.println(cart.get(i).getName() + " - Rs." + cart.get(i).getPrice());
            total += cart.get(i).getPrice();
        }
        System.out.println("----------------------");
        System.out.println("Grand Total : Rs." + total);
    }
}

public class Main {
    public static void main(String[] args) {
        CartManager cart = new CartManager();

        MenuItem m1 = new MenuItem(101, "Pizza", 250, true);
        MenuItem m2 = new MenuItem(102, "Burger", 180, false);
        MenuItem m3 = new MenuItem(103, "Pasta", 200, true);
        try {
            cart.addItem(m1);
            cart.addItem(m2);
            cart.addItem(m3);
        } catch (ItemUnavailableException e) {
            System.out.println(e.getMessage());
        }
        cart.displayCart();
    }
}


