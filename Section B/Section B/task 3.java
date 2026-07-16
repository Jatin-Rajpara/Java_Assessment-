
import java.sql.*;
import java.util.ArrayList;

public class FoodOrderLogger {

    static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "jat***";

    public static int insertOrder(String customerId, String restaurantName, double amount) {
        String sql = "INSERT INTO food_orders(customer_id, restaurant_name, amount)    VALUES(?,?,?)";
        try (
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, customerId);
            ps.setString(2, restaurantName);
            ps.setDouble(3, amount);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static ArrayList<String> getOrdersByCustomer(String customerId) {

        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT * FROM food_orders WHERE customer_id=?";
        try (
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setString(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String order =
                        "ORDER#" + rs.getInt("order_id")
                        + " - "
                        + rs.getString("restaurant_name")
                        + " - Rs."
                        + rs.getDouble("amount");
                list.add(order);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        int id = insertOrder("C101", "Dominos", 450);
        System.out.println("Generated Order ID : " + id);

        ArrayList<String> orders = getOrdersByCustomer("C101");
        for(int i=0; i<orders.size(); i++) {
            System.out.println(orders.get(i));
        }
    }
}

