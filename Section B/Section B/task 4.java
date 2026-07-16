
import java.sql.*;
public class TransactionDemo {

    static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "Jat***";

    public static void placeOrder(String customerId, int restaurantId, double orderAmount) {

        String sql1 = "UPDATE customers SET wallet_balance = wallet_balance - ? WHERE   customer_id = ?";
        String sql2 = "INSERT INTO orders(customer_id, restaurant_id, amount) VALUES(?,?,?)";
        String sql3 = "UPDATE restaurants SET pending_orders = pending_orders + 1 WHERE restaurant_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            con.setAutoCommit(false);

            try (
                PreparedStatement ps1 = con.prepareStatement(sql1);
                PreparedStatement ps2 = con.prepareStatement(sql2);
                PreparedStatement ps3 = con.prepareStatement(sql3);
            ) 
                ps1.setDouble(1, orderAmount);
                ps1.setString(2, customerId);
                ps1.executeUpdate();
               
                ps2.setString(1, customerId);
                ps2.setInt(2, restaurantId);
                ps2.setDouble(3, orderAmount);
                ps2.executeUpdate();

                ps3.setInt(1, restaurantId);
                int rows = ps3.executeUpdate();
                if (rows == 0) {
                    throw new SQLException("Restaurant not found.");
                }
                con.commit();
                System.out.println("Order Placed Successfully.");
            }
            catch (Exception e) {
                con.rollback();
             	   throw new RuntimeException("Transaction Failed. All changes rolled back.", e);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        placeOrder("C101", 9999, 500);
    }
}


