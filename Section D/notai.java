package project__d;
import java.sql.*;

public class notai {

    static final String URL = "jdbc:mysql://localhost:3306/food_delivery1";
    static final String USER = "root";
    static final String PASSWORD = "Man@2004";

    public static void main(String[] args) {

        String sql =
                "SELECT COALESCE(restaurant_name,'Unknown') AS restaurant_name," +
                " COUNT(*) AS order_count," +
                " SUM(total_amount) AS total_revenue " +
                "FROM food_orders " +
                "GROUP BY restaurant_name";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            ) {

                boolean found = false;

                System.out.println("------------------------------------------------------------");
                System.out.printf("%-20s %-15s %-15s%n",
                        "Restaurant", "Orders", "Revenue");
                System.out.println("------------------------------------------------------------");

                while (rs.next()) {

                    found = true;

                    System.out.printf("%-20s %-15d %.2f%n",
                            rs.getString("restaurant_name"),
                            rs.getInt("order_count"),
                            rs.getDouble("total_revenue"));
                }

                if (!found) {
                    System.out.println("No orders found.");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// The AI version grouped records using a Java HashMap, which is less efficient because all rows must first be loaded into the application. I replaced this with an SQL GROUP BY query so MySQL performs the aggregation. I also used try-with-resources to automatically close JDBC resources and COALESCE() to safely handle NULL restaurant names. These changes make the program more efficient, reliable, and easier to maintain.