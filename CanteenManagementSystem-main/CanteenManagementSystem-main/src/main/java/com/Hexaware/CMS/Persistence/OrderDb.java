package com.Hexaware.CMS.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.Hexaware.CMS.Model.Customer;
import com.Hexaware.CMS.Model.Menu;
import com.Hexaware.CMS.Model.OrderDetails;
import com.Hexaware.CMS.Model.Vendor;

import java.sql.PreparedStatement;

/**
 * OrderDb class used to connect to data base.
 * @author hexware
 */
public class OrderDb {
   static int i;
   
   /*
    * * this method is for ordering
    */
    public static double insertDb(String userId, String fid, String vid, int quantity){        
    	double price = 0.0;
    	try{
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
            PreparedStatement stmt1=con.prepareStatement("select food_price from menu where food_id = ?");
	        stmt1.setString(1, fid);
	        ResultSet rs=stmt1.executeQuery();
	        if(rs.next())
	        	price = rs.getDouble(1);
            PreparedStatement stmt=con.prepareStatement("insert into orderdetails (order_no, vendor_id, customer_id, food_id, quantity, date_time, order_value, order_status) values(?,?,?,?,?,CURRENT_TIMESTAMP,?,?)");  
            LocalDateTime obj = LocalDateTime.now();
            DateTimeFormatter format1 = DateTimeFormatter.ofPattern("YYYYMMddHHmmss");
            String part = obj.format(format1);
            String orderNo = "ORD"+part;
            stmt.setString(1,orderNo);  
	        stmt.setString(2,vid);  
	        stmt.setString(3,userId);
	        stmt.setString(4,fid);   
	        stmt.setInt(5,quantity);
	        stmt.setDouble(6, (price * quantity));
	        stmt.setString(7, "Ordered");
	        stmt.executeUpdate();
	        
	        //System.out.println(i+" records inserted");  
        }catch(Exception e){ System.out.println(e);}  
                return (price * quantity);
            }  
    
    /*
     * * this method is for viewing Menu
     */
    public static Menu[] fetchDb(){
        Menu m[]=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
            Statement stmt=con.createStatement();  
                    
            ResultSet rs=stmt.executeQuery("select * from menu");  
            ArrayList<Menu> list=new ArrayList<Menu>();          
            while(rs.next()) { 
            list.add(new Menu(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getDouble(5),rs.getDouble(6)));
                m=new Menu[list.size()];
                m= list.toArray(m);
                  } 
        }catch(Exception e){ System.out.println(e);}  
            
        return m;      
        }
    
    /*
     * * this method is for viewing Customer Profile
     */
    public static Customer customerProfileDb(String email, String password){
    	  Customer c = null;
    	  try{  
              Class.forName("com.mysql.cj.jdbc.Driver");  
             Connection con=DriverManager.getConnection(  
          	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
             String sql = "Select * from customer where customer_email in (select login_mail from customer_login where login_mail = ? and password = ?)";
             
              PreparedStatement stmt = con.prepareStatement(sql);  
              stmt.setString(1, email);
              stmt.setString(2, password);
              ResultSet rs=stmt.executeQuery();
              if(rs.next()) {
            	  c = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6));
              }     
          }catch(Exception e){ System.out.println(e);}
    	  return c;
      }
      
      /*
       * * this method is for registering new customer
       */
    public static void registerNewCustomerDb(/*Customer customer*/String userName, String phone, String email, String password) {
    	  try{  
              Class.forName("com.mysql.cj.jdbc.Driver");  
             Connection con=DriverManager.getConnection(  
          	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
             LocalDateTime obj = LocalDateTime.now();
             DateTimeFormatter format1 = DateTimeFormatter.ofPattern("YYYYMMddHHmmss");
             String part = obj.format(format1);
             String customerId = "USER"+part;
             String sql = "insert into customer values (?, ?, ?, ?, ?, ?)";
             String sql1 = "insert into customer_login values (?, ?)";
             
              PreparedStatement stmt = con.prepareStatement(sql);
              PreparedStatement stmt1 = con.prepareStatement(sql1);
              stmt.setString(1, customerId);
              stmt.setString(2, userName);
              stmt.setString(3, phone);
              stmt.setString(4, email);
              stmt.setString(5, "none");
              stmt.setDouble(6, 0.0);
              stmt.executeUpdate();
              
              stmt1.setString(1, email);
              stmt1.setString(2, password);
              stmt1.executeUpdate();    
          }catch(Exception e){ System.out.println(e);}
      }
      
      /*
       * * this method is for checking if the customerId already exist or not
       */
    public static boolean checkUserIdDb(String email) {
    	  boolean result = false;
    	  try{  
              Class.forName("com.mysql.cj.jdbc.Driver");  
             Connection con=DriverManager.getConnection(  
          	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
             String sql = "Select * from customer where customer_email = ?";
             
              PreparedStatement stmt = con.prepareStatement(sql);  
              stmt.setString(1, email);
              ResultSet rs=stmt.executeQuery(); 
              if(rs.next()) {
            	  result = true;
              }
          }catch(Exception e){ System.out.println(e);}
    	  return result;
      }
    public static void updateUserBalanceDb(String userId, double balance) {
    	try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
            String sql = "update customer set customer_walletbal = ? where customer_id = ?";
           
            PreparedStatement stmt = con.prepareStatement(sql);  
            stmt.setString(2, userId);
            stmt.setDouble(1, balance);
            stmt.executeUpdate();
        }catch(Exception e){ System.out.println(e);}
    }
    public static void updateUserPasswordDb(String userId, String password) {
    	try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
            String sql = "update customer_login set password = ? where login_id = ?";
           
            PreparedStatement stmt = con.prepareStatement(sql);  
            stmt.setString(2, userId);
            stmt.setString(1, password);
            stmt.executeUpdate();
        }catch(Exception e){ System.out.println(e);}
    }
      
      /*
       * * this method is for viewing Vendor Profile
       */
    public static Vendor vendorProfileDb(String loginId, String password){
    	  Vendor v = null;
    	  try{  
              Class.forName("com.mysql.cj.jdbc.Driver");  
             Connection con=DriverManager.getConnection(  
          	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
             String sql = "Select * from vendor where vendor_id in (select login_id from vendor_login where login_id = ? and password = ?)";
             
              PreparedStatement stmt = con.prepareStatement(sql);  
              stmt.setString(1, loginId);
              stmt.setString(2, password);
              ResultSet rs=stmt.executeQuery();
              if(rs.next())
            	  v = new Vendor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));     
          }catch(Exception e){ System.out.println(e);}
    	  return v;
      }
      
      /*
       * * this method is for registering new vendor
       */
    public static String registerNewVendorDb(Vendor vendor, String password) {
    	String vendorId = null;  
    	try{  
              Class.forName("com.mysql.cj.jdbc.Driver");  
             Connection con=DriverManager.getConnection(  
          	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
             
             String sql = "select max(vendor_id) from vendor where vendor_id like \"VEN%\"";
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs=stmt.executeQuery();
             String vid = "";
             if(rs.next()) {
            	 vid = rs.getString(1);
            	 if(vid == null) {
            		 vid = "VEN001";
            	 }
            	 else {
            		 int id = Integer.parseInt(vid.substring(3));
                     id++;
                     String newVid = Integer.toString(id);
                     vid = vid.substring(0, (6 - newVid.length()))+newVid;
            	 }
             }    
             
	          String sql1 = "insert into vendor values (?, ?, ?, ?)";
	          String sql2 = "insert into vendor_login values (?, ?)";
             
              PreparedStatement stmt1 = con.prepareStatement(sql1);
              PreparedStatement stmt2 = con.prepareStatement(sql2);
              
              stmt1.setString(1, vid);
              stmt1.setString(2, vendor.getVendorName());
              stmt1.setString(3, vendor.getVendorPhone());
              stmt1.setString(4, vendor.getVendorSpecs());
              stmt1.executeUpdate();
              
              stmt2.setString(1, vid);
              stmt2.setString(2, password);
              stmt2.executeUpdate();
              vendorId = vid;
          }catch(Exception e){ System.out.println(e);}
    	return vendorId;
      }
    /*
     * * This method is for fetching food id  
     */
    public static boolean foodIdDb(String foodId) {
    	boolean result = false;
  	  try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           String sql = "Select * from menu where food_id = ?";
           
            PreparedStatement stmt = con.prepareStatement(sql);  
            stmt.setString(1, foodId);
            ResultSet rs=stmt.executeQuery(); 
            if(rs.next()) {
          	  result = true;
            }
        }catch(Exception e){ System.out.println(e);}
  	  return result;
    }
    /*
     * * This method is for fetching food price  
     */
    public static double foodPriceDb(String foodId) {
    	double result = 0.0;
  	  try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           String sql = "Select food_price from menu where food_id = ?";
           
            PreparedStatement stmt = con.prepareStatement(sql);  
            stmt.setString(1, foodId);
            ResultSet rs=stmt.executeQuery(); 
            if(rs.next()) {
          	  result = rs.getDouble(1);
            }
        }catch(Exception e){ System.out.println(e);}
  	  return result;
    }
    
    
    public static OrderDetails[] customerOderHistoryDb(String userId){
    	OrderDetails m[]=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           	String sql = "Select * from OrderDetails where customer_id = ?";
           	PreparedStatement stmt = con.prepareStatement(sql);
           	stmt.setString(1, userId);
                    
            ResultSet rs=stmt.executeQuery();  
            ArrayList<OrderDetails> list=new ArrayList<OrderDetails>();          
            while(rs.next()) { 
            list.add(new OrderDetails(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getDouble(7),rs.getString(8),rs.getDouble(9)));
                m=new OrderDetails[list.size()];
                m = list.toArray(m);
                  } 
        }catch(Exception e){ System.out.println(e);}  
            
        return m;
    }
    public static OrderDetails[] vendorOderHistoryDb(String userId){
    	OrderDetails m[]=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           	String sql = "Select * from OrderDetails where vendor_id = ?";
           	PreparedStatement stmt = con.prepareStatement(sql);
           	stmt.setString(1, userId);
                    
            ResultSet rs=stmt.executeQuery();  
            ArrayList<OrderDetails> list=new ArrayList<OrderDetails>();          
            while(rs.next()) { 
            list.add(new OrderDetails(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getDouble(7),rs.getString(8),rs.getDouble(9)));
                m=new OrderDetails[list.size()];
                m = list.toArray(m);
                  } 
        }catch(Exception e){ System.out.println(e);}  
            
        return m;
    }
      // public static String acceptRejectDb(){}
    
    public static String checkOrderStatusDb(String orderId) {
    	String status = null;
    	try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           String sql = "Select order_status from orderdetails where order_no = ?";
           
            PreparedStatement stmt = con.prepareStatement(sql);  
            stmt.setString(1, orderId);
            ResultSet rs=stmt.executeQuery(); 
            if(rs.next()) {
          	  status = rs.getString(1);
            }
        }catch(Exception e){ System.out.println(e);}
    		return status;
    }
    
    public static double cancleOrderDb(String orderId) {
    	double value = 0.0;
    	try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           	String sql = "Select order_value from orderdetails where order_no = ?";
           
            PreparedStatement stmt = con.prepareStatement(sql);  
            stmt.setString(1, orderId);
            ResultSet rs=stmt.executeQuery(); 
            if(rs.next()) {
          	  value = rs.getDouble(1);
            }
            String sql1 = "Update orderdetails set order_status = ? where order_no = ?";
            
            PreparedStatement stmt1 = con.prepareStatement(sql1);
            stmt1.setString(1, "Cancled");
            stmt1.setString(2, orderId);
            
            stmt1.executeUpdate();
            
        }catch(Exception e){ System.out.println(e);}
    		return value;
    }
    public static OrderDetails[] orderHistoryCancleDb(String userId) {
    	OrderDetails m[]=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           	String sql = "Select * from OrderDetails where customer_id = ? and order_status = \"Ordered\"";
           	PreparedStatement stmt = con.prepareStatement(sql);
           	stmt.setString(1, userId);
                    
            ResultSet rs=stmt.executeQuery();  
            ArrayList<OrderDetails> list=new ArrayList<OrderDetails>();          
            while(rs.next()) { 
            list.add(new OrderDetails(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getDouble(7),rs.getString(8),rs.getDouble(9)));
                m=new OrderDetails[list.size()];
                m = list.toArray(m);
                  } 
        }catch(Exception e){ System.out.println(e);}  
            
        return m;
    }
    
    public static void acceptOrderDb(String orderId, String vendorId) {
    	try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
            String sql = "update orderDetails set order_status = ?, vendor_id = ? where order_no = ?";
           
            PreparedStatement stmt = con.prepareStatement(sql);  
            stmt.setString(3, orderId);
            stmt.setString(2, vendorId);
            stmt.setString(1, "Accepted");
            stmt.executeUpdate();
        }catch(Exception e){ System.out.println(e);}
    }

    public static OrderDetails[] generalOrderHistoryDb(String userId){
    	OrderDetails m[]=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           	String sql = "Select * from OrderDetails where vendor_id in (\"000\" , ?) and order_status = \"Ordered\"";
           	PreparedStatement stmt = con.prepareStatement(sql);
           	stmt.setString(1, userId);
                    
            ResultSet rs=stmt.executeQuery();  
            ArrayList<OrderDetails> list=new ArrayList<OrderDetails>();          
            while(rs.next()) { 
            list.add(new OrderDetails(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getDouble(7),rs.getString(8),rs.getDouble(9)));
                m=new OrderDetails[list.size()];
                m = list.toArray(m);
                  } 
        }catch(Exception e){ System.out.println(e);}  
            
        return m;
    }
    public static OrderDetails[] generalOrderedDb(String userId){
    	OrderDetails m[]=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           	String sql = "Select * from OrderDetails where vendor_id = ? and order_status = \"Ordered\"";
           	PreparedStatement stmt = con.prepareStatement(sql);
           	stmt.setString(1, userId);
                    
            ResultSet rs=stmt.executeQuery();  
            ArrayList<OrderDetails> list=new ArrayList<OrderDetails>();          
            while(rs.next()) { 
            list.add(new OrderDetails(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getDouble(7),rs.getString(8),rs.getDouble(9)));
                m=new OrderDetails[list.size()];
                m = list.toArray(m);
                  } 
        }catch(Exception e){ System.out.println(e);}  
            
        return m;
    }
    
    public static void rejectOrderDb(String orderId) {
    	try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
            
            String customer = "select customer_id, order_value from orderdetails where order_no = ?";
            PreparedStatement stmt = con.prepareStatement(customer);
            stmt.setString(1, orderId);
            ResultSet rs=stmt.executeQuery();
            String customer_id = "";
            double value = 0.0;
            if(rs.next()) {
            	customer_id = rs.getString(1);
            	value = rs.getDouble(2);
            }
            
            String customer1 = "select customer_walletbal from customer where customer_id = ?";
            PreparedStatement stmt1 = con.prepareStatement(customer1);
            stmt1.setString(1, customer_id);
            ResultSet rs2=stmt1.executeQuery();
            double balance = 0.0;
            if(rs2.next()) {
            	balance =rs2.getDouble(1);
            }
            balance = balance + value;
            
            String sql = "update orderDetails set order_status = ? where order_no = ?";
            PreparedStatement stmt2 = con.prepareStatement(sql);  
            stmt2.setString(2, orderId);
            stmt2.setString(1, "Rejected");
            stmt2.executeUpdate();
            
            String sql1 = "update customer set customer_walletbal = ? where customer_id = ?";
            PreparedStatement stmt3 = con.prepareStatement(sql1);  
            stmt3.setString(2, customer_id);
            stmt3.setDouble(1, balance);
            stmt3.executeUpdate();
        }catch(Exception e){ System.out.println(e);}
    }
    
    public static OrderDetails[] customerOderRatingDb(String userId){
    	OrderDetails m[]=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           	String sql = "Select * from OrderDetails where customer_id = ? and order_status = \"Delivered\"";
           	PreparedStatement stmt = con.prepareStatement(sql);
           	stmt.setString(1, userId);
                    
            ResultSet rs=stmt.executeQuery();  
            ArrayList<OrderDetails> list=new ArrayList<OrderDetails>();          
            while(rs.next()) { 
            list.add(new OrderDetails(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getDouble(7),rs.getString(8),rs.getDouble(9)));
                m=new OrderDetails[list.size()];
                m = list.toArray(m);
                  } 
        }catch(Exception e){ System.out.println(e);}  
            
        return m;
    }
    public static void setOrderRatingDb(String orderId, double rating) {
    	try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           	String sql = "update orderDetails set order_rating = ? where order_no = ?";
           	PreparedStatement stmt = con.prepareStatement(sql);
           	stmt.setString(2, orderId);
           	stmt.setDouble(1, rating);
           	stmt.executeUpdate(); 
        }catch(Exception e){ System.out.println(e);}
    }
    public static void updateOrderStatusDb(String orderId, String status) {
    	try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
            String sql = "update orderDetails set order_status = ? where order_no = ?";
           
            PreparedStatement stmt = con.prepareStatement(sql);  
            stmt.setString(2, orderId);
            stmt.setString(1, status);
            stmt.executeUpdate();
        }catch(Exception e){ System.out.println(e);}
    }
    public static OrderDetails[] updateOrderHistoryDb(String userId){
    	OrderDetails m[]=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
           Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/CANTEEN","root","Admin123");
           	String sql = "Select * from OrderDetails where vendor_id = ? and order_status = \"Accepted\"";
           	PreparedStatement stmt = con.prepareStatement(sql);
           	stmt.setString(1, userId);
                    
            ResultSet rs=stmt.executeQuery();  
            ArrayList<OrderDetails> list=new ArrayList<OrderDetails>();          
            while(rs.next()) { 
            list.add(new OrderDetails(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getDouble(7),rs.getString(8),rs.getDouble(9)));
                m=new OrderDetails[list.size()];
                m = list.toArray(m);
                  } 
        }catch(Exception e){ System.out.println(e);}  
            
        return m;
    }
}
    
