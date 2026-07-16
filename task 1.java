

// CREATE TABLE menu_items
// (
//     item_id INT PRIMARY KEY,
//     name VARCHAR(50),
//     price DOUBLE,
//     is_available BOOLEAN
// );

// INSERT INTO menu_items VALUES
// (101,'Pizza',250,true),
// (102,'Burger',180,true),
// (103,'Pasta',220,true),
// (104,'Sandwich',150,false);

// Step 2: Java Program :

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

class MenuItem
{
    private int itemId;
    private String name;
    private double price;
    private boolean isAvailable;

    public MenuItem(int itemId, String name, double price, boolean isAvailable)
    {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getItemId()
    {
        return itemId;
    }

    public String getName()
    {
        return name;
    }

    public double getPrice()
    {
        return price;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }
}

public class FoodDeliveryApp
{
    static final String DB_URL="jdbc:mysql://localhost:3306/test";
    static final String USER="root";
    static final String PASSWORD="Jat***";

    static ArrayList<MenuItem> menu=new ArrayList<>();

    
    public static void loadMenu()
    {
        String sql="select * from menu_items";

        try(
            Connection con=DriverManager.getConnection(DB_URL,USER,PASSWORD);
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
        )
        {

            while(rs.next())
            {
                MenuItem item=new MenuItem(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available")
                );

                menu.add(item);
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }

   
    public static void browseMenu()
    {
        System.out.println("\n------ MENU ------");

        for(int i=0;i<menu.size();i++)
        {
            MenuItem item=menu.get(i);

            System.out.print(item.getItemId()+" ");
            System.out.print(item.getName()+" ");
            System.out.print("Rs."+item.getPrice()+" ");

            if(item.isAvailable())
            {
                System.out.println("Available");
            }
            else
            {
                System.out.println("Unavailable");
            }

        }

    }

    public static void placeOrder(String customerId)
{
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter Restaurant Name : ");
    String restaurant = sc.nextLine();

    System.out.print("Enter Order Amount : ");
    double amount = sc.nextDouble();

    String sql1 = "UPDATE customers SET wallet_balance = wallet_balance - ? WHERE customer_id = ?";
    String sql2 = "INSERT INTO food_orders(customer_id,restaurant_name,amount,order_date) VALUES(?,?,?,CURDATE())";

    try(Connection con = DriverManager.getConnection(DB_URL,USER,PASSWORD))
    {
        con.setAutoCommit(false);

        try(
            PreparedStatement ps1 = con.prepareStatement(sql1);
            PreparedStatement ps2 = con.prepareStatement(sql2);
        )
        {
            
            ps1.setDouble(1, amount); 
            ps1.setString(2, customerId);
            ps1.executeUpdate();

            
            ps2.setString(1, customerId);
            ps2.setString(2, restaurant);
            ps2.setDouble(3, amount); 
            ps2.executeUpdate();

            con.commit();

            System.out.println("Order Placed Successfully.");

        }
        catch(Exception e)
        {
            con.rollback();
            System.out.println("Transaction Failed...");
        }

    }
    catch(Exception e)
    {
        System.out.println(e);
    }
}

   public static void viewOrderHistory(String customerId)
{
    String sql = "SELECT * FROM food_orders WHERE customer_id=?";

    try(
        Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        PreparedStatement ps = con.prepareStatement(sql);
    )
    {
        ps.setString(1, customerId);

        try(ResultSet rs = ps.executeQuery())
        {
            boolean found = false;

            System.out.println("\n----------- ORDER HISTORY -----------");
            System.out.println("ID\tRestaurant\tAmount\tDate");

            while(rs.next())
            {
                found = true;

                System.out.println(
                        rs.getInt("order_id") + "\t"   
                      + rs.getString("restaurant_name") + "\t"
                      + rs.getDouble("amount") + "\t"
                      + rs.getDate("order_date")        
                );
            }

            if(!found)
            {
                System.out.println("No Orders Found...");
            }
        }

    }
    catch(Exception e)
    {
        System.out.println(e);
    }
}

    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);

        loadMenu();

        int choice;

        do
        {
            System.out.println("\n===== Food Delivery App =====");
            System.out.println("1. Browse Menu");
            System.out.println("2. Place Order");
            System.out.println("3. View Order History");
            System.out.println("4. Exit");

            System.out.print("Enter Choice : ");
            choice=sc.nextInt();

            switch(choice)
            {
                case 1:
                    browseMenu();
                    break;

                case 2:
    		    placeOrder("C101");
    		    break;

                case 3:
   		   viewOrderHistory("C101");
 		   break;

                case 4:
                    System.out.println("Thank You...");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        }while(choice!=4);

    }

}