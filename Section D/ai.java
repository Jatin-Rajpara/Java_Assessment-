package project__d;
import java.sql.*;
import java.util.HashMap;

class RestaurantReport {
    int orderCount;
    double revenue;

    RestaurantReport() {
        orderCount = 0;
        revenue = 0;
    }
}

public class ai {

    static final String URL = "jdbc:mysql://localhost:3306/food_delivery1";
    static final String USER = "root";
    static final String PASSWORD = "Man@2004";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(
                    "SELECT restaurant_name,total_amount FROM food_orders");

            HashMap<String, RestaurantReport> map = new HashMap<>();

            boolean found = false;

            while (rs.next()) {

                found = true;

                String restaurant = rs.getString("restaurant_name");
                double amount = rs.getDouble("total_amount");

                RestaurantReport report = map.get(restaurant);

                if (report == null) {
                    report = new RestaurantReport();
                    map.put(restaurant, report);
                }

                report.orderCount++;
                report.revenue += amount;
            }

            if (!found) {
                System.out.println("No orders found.");
            } else {

                System.out.println("-----------------------------------------------");
                System.out.printf("%-20s %-10s %-10s%n",
                        "Restaurant", "Orders", "Revenue");
                System.out.println("-----------------------------------------------");

                for (String key : map.keySet()) {

                    RestaurantReport r = map.get(key);

                    System.out.printf("%-20s %-10d %.2f%n",
                            key,
                            r.orderCount,
                            r.revenue);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}