package com.Hexaware.CMS.Cli;

import java.util.Scanner;

import com.Hexaware.CMS.Factory.OrderFactory;
import com.Hexaware.CMS.Model.Customer;
import com.Hexaware.CMS.Model.Menu;
import com.Hexaware.CMS.Model.OrderDetails;
import com.Hexaware.CMS.Model.Vendor;

/**
 * CliMain used as Client interface for java coading.
 * @author hexaware
 */
public class CliMain {
    
    static Scanner sc=new Scanner(System.in);
/**
 * main method  used to display the option we had in the application.
 */
    public static void main( String[] args )
    {      
        while(true) {
        	System.out.println( "\n\nCanteen Management System" );
            System.out.println("1.Show Menu");
            //System.out.println("2.Placing Order");
            System.out.println("2.User Login");
            System.out.println("3.Register New User");
            System.out.println("4.Vendor's login" );
            System.out.println("5.Register New vendor");
            System.out.println("6.Exit");

            System.out.print("Enter your choice....  ");
            int choice=sc.nextInt();
            switch(choice){
                case 1:
                    menuList();
                    break;
                case 2:
                	customerProfile();    
                    break;
                case 3:
                	registerCustomer();
                	break;
                case 4:
                	vendorProfile();
                	break;
                case 5:
                	registerVendor();
                	break;
                case 6:
                	System.out.println("Have a great day! bye.");
                	System.exit(0);
                default:
                    System.out.println("Choose an option");
            }
        }
        
    }
    /**
     * this method  is to place food order.
     */
        public static double placeOrder(String userId, double balance){
        	
	        
        	
        	String fId = null;
        	while(fId == null) {
        		System.out.println("Enter Food id");
        		fId=sc.next();
        		boolean result = OrderFactory.foodId(fId);
        		if(!result)
        		{
        			System.out.println("Invalid foodId. Enter a valid foodId.");
        			fId=null;
        		}
        		
        	}
	        
	        System.out.println("Enter preferred Vendor id or else \"000\"");
	        String vId=sc.next();
	        double price = OrderFactory.foodPrice(fId);
	        int  fquan = 0;
	        do {
	        	System.out.println("Enter the Food Quantity");
		        fquan=sc.nextInt();
		        if(balance < (fquan*price))
		        	System.out.println("Balance excided, try with lesser quantity or enter -1 to go to previous menu");
		        if(fquan == -1)
		        	return -1.0;
	        }
	        while(balance < (fquan*price));
	        //Add other attributes to complete the functionality
	        return	OrderFactory.OrderFood(userId, fId.toUpperCase(),vId.toUpperCase(),fquan);
	        //System.out.println(r+"   is inserted.....");
        }
/**
 * this method is to fetch Menu list.
 */
        public static void menuList(){
        Menu m[]=OrderFactory.fetchMenu();
        if(m == null)
        	System.out.println("Menu List is empty!!");
        else {
        	System.out.println("-----------------------------------------------------------------------------------------------------------");
 		   	System.out.println("----------------------------------------------------MENU---------------------------------------------------");
 		   	System.out.println("-----------------------------------------------------------------------------------------------------------");
        	System.out.println("Food Id"+"    "+"Food Name"+"        "+"Veg/Non-vegs"+"      "+"Food Price"+"     "+"Calories"); 
            for(int i=0;i<m.length;i++){
                System.out.printf("%-12s%-20s%-15s%-15s%s\n", m[i].getFoodId(), m[i].getFoodName(), m[i].getIsVeg(), m[i].getFoodPrice(), m[i].getCalories()); 
            	//System.out.println(m[i].getFoodId()+"       "+m[i].getFoodName()+"       "+m[i].getFoodPrice());
               }
            System.out.println();
            System.out.println();
        }
        
    }
/**
 * this method is to acceptRejectOrder.
 */
// public static String acceptRejectOrder(){}

 /**
 * this method is for customerProfile.
 */
        public static void customerProfile(){
			System.out.print("Enter your email: ");
			String loginId = sc.next();
			System.out.print("Enter password: ");
			String password = sc.next();
			Customer c = OrderFactory.customerProfile(loginId, password);
			if(c == null) {
				System.out.println("-----------------------------------------------------------------------------------------------------------");
				System.out.println("<<Invalid user id or password.>>");
				System.out.println("-----------------------------------------------------------------------------------------------------------");
				return;
			}
			else {
				System.out.println("-----------------------------------------------------------------------------------------------------------");
				System.out.println("Welcome Back "+c.getUserName());
		        while(true) {
		        	System.out.println("\n--------------------------------------------------OPTIONS---------------------------------------------------");
		        	System.out.println("\n1.Show Menu");
			        System.out.println("2.Placing Order");
			        System.out.println("3.Order History");
			        System.out.println("4.Wallet Balance");
			        System.out.println("5.Cancle Order" );
			        System.out.println("6.Rating Order");
			        System.out.println("7.Recharge Wallet Balance.");
			        System.out.println("8.Update Password");
			        System.out.println("9.Profile Details");
			        System.out.println("10.Logout");
		        	System.out.print("Enter your choice: ");
		        	int choice=sc.nextInt();
		            switch(choice){
		                case 1:
		                    menuList();
		                    break;
		                case 2:
		                	System.out.println("-----------------------------------------------------------------------------------------------------------");
		                	System.out.println("Your Balance is: "+c.getUserBalance());
		                    double total = placeOrder(c.getUserId(), c.getUserBalance());
		                    if(total == -1.0) {
		                    	break;
		                    }
		                    System.out.println("-----------------------------------------------------------------------------------------------------------");
		                    System.out.println("Your Bill Amount is : "+total);
		                    System.out.println("Your new balance is:"+(c.getUserBalance() - total));
		                    System.out.println("-----------------------------------------------------------------------------------------------------------");
		                    OrderFactory.updateUserBalance(c.getUserId(), (c.getUserBalance() - total));
		                    return;
		                case 3:
		                	boolean t = CustomerOrderHistory(c.getUserId());
		                	if(!t) {
		                		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                		System.out.println("No oreder history found!");
		                		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                	}
		                	break;
		                case 4:
		                	System.out.println("-----------------------------------------------------------------------------------------------------------");
		                	System.out.println("Your Balance is: "+c.getUserBalance());
		                	System.out.println("-----------------------------------------------------------------------------------------------------------");
		                	break;
		                case 5:
		                	boolean tt = orderHistoryCancle(c.getUserId());
		                	if(!tt) {
		                		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                		System.out.println("No oreder history found!");
		                		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                		break;
		                	}
		                	double balance = cancleOrder();
		                	if(balance == 0.0)
		                		break;
		                	OrderFactory.updateUserBalance(c.getUserId(), (c.getUserBalance() + balance));
		                	System.out.println("-----------------------------------------------------------------------------------------------------------");
		                	System.out.println("\nYour order is cancled!");
		                	System.out.println("Your new balance is:"+(c.getUserBalance() + balance));
		                	System.out.println("-----------------------------------------------------------------------------------------------------------");
		                	break;
		                case 6:
		                	//System.out.println("Not available at the moment!");
		                	boolean ttt =CustomerOrderRating(c.getUserId());
		                	if(!ttt) {
		                		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                		System.out.println("No oreder history found!");
		                		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                		break;
		                	}
		                	setOrderRating();
		                	break;
		                case 7:
		                	double amount = 0.0;
		                	do {
		                		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                		System.out.println("Enter the amount (amount must be between 1000.0 and 10000.0):");
		                		amount = sc.nextDouble();
		                	}
		                	while((amount<1000.0) || (amount>10000.0));
		                	OrderFactory.updateUserBalance(c.getUserId(), (c.getUserBalance() + amount));
		                	System.out.println("Your new balance is:"+(c.getUserBalance() + amount));
		                	c.setUserBalance(c.getUserBalance() + amount);
		                	break;
		                case 8:
		                	System.out.println("Enter your password: ");
		                	String pwd = sc.next();
		                	if(!pwd.equals(password)) {
		                		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                		System.out.println("Password mismatch!");
		                		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                	}
		                	else {
		                		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                		System.out.print("Create a password. ");
		                    	String passwd = "";
		                    	while(passwd.length() < 8) {
		                    		System.out.print("(Password length must be between 8 and 20) : ");
		                    		passwd = sc.next();	
		                    	}
		                    	System.out.println("-----------------------------------------------------------------------------------------------------------");
		                    	System.out.print("Enter the password again. ");
		                    	String passwd2 = sc.next();
		                    	if(passwd2.equals(passwd)) {
		                    		OrderFactory.updateUserPasswordDb(c.getUserId(), passwd);
		                    		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                    		System.out.println("Password updated");
		                    		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                    	}
		                    	else {
		                    		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                    		System.out.println("Password mismatch");
		                    		System.out.println("-----------------------------------------------------------------------------------------------------------");
		                    	}
		                    	
		                	}
		                case 9:
		                	System.out.println("-----------------------------------------PROFILE-----------------------------------------------------------");
		                	System.out.println("\n\nUserId              "+"Name                          "
		                +"Phone          "+"Email                              "
		                			+"Coupon              "+"Balance");
		                	System.out.println(c.toString());
		                	System.out.println("-----------------------------------------------------------------------------------------------------------");
		                	break;
		                case 10:
		                	return;
		                default:
		                    System.out.println("Choose an option");
		            
		            }
		        }	
			}
		}
/**
 * this method is for  creating new customerProfile.
 */
        public static void registerCustomer() {
        	System.out.println("-----------------------------------------------------------------------------------------------------------");
        	System.out.print("Enter First Name: ");
        	String firstName = sc.next();
        	System.out.print("Enter Last Name: ");
        	String lastName = sc.next();
        	String userName = firstName+" "+lastName;
        	String email = "";
        	boolean exist = true;
        	do {
        		System.out.print("Enter your email: ");
            	email = sc.next();
        		exist = OrderFactory.checkUserId(email);
        		if(exist) {
        			System.out.println("User email already taken by another user, choose a different one.");
        		}
        	}
        	while(exist);
        	System.out.print("Enter your mobile no: ");
        	String phone = sc.next();
        	System.out.print("Create a password. ");
        	String password = "";
        	while(password.length() < 8) {
        		System.out.print("(Password length must be between 8 and 20) : ");
        		password = sc.next();	
        	}
        	OrderFactory.registerNewCustomer(userName, phone, email.toLowerCase(), password);
        	System.out.println("-----------------------------------------------------------------------------------------------------------");
        	System.out.println("Welcome to our canteen "+userName);
        	System.out.println("-----------------------------------------------------------------------------------------------------------");
        }


/**
 * this method is for VendorProfile.
 */
        public static void vendorProfile(){
        	System.out.print("Enter your login id: ");
			String loginId = sc.next();
			System.out.print("Enter password: ");
			String password = sc.next();
			Vendor v = OrderFactory.vendorProfile(loginId, password);
			if(v == null) {
				System.out.println("-----------------------------------------------------------------------------------------------------------");
				System.out.println("<<Invalid id or password.>>\n\n");
				System.out.println("-----------------------------------------------------------------------------------------------------------");
				return;
			}	
			else {
				System.out.println("Welcome Back "+v.getVendorName());
		        while(true) {
		        	System.out.println("-----------------------------------------------------------------------------------------------------------");
			        System.out.println("\n\n1.Order History");
			        System.out.println("2.Accept Order");
			        System.out.println("3.Reject Order" );
			        System.out.println("4.Update Order Status");
			        System.out.println("5.Logout");
		        	System.out.print("Enter your choice: ");
		        	int choice=sc.nextInt();
		            switch(choice){
		                case 1:
		                    boolean record = vendorOderHistory(v.getVendorId());
		                    if(!record)
		                		System.out.println("No oreder history found!");
		                    break;
		                case 2:
		                	boolean record1 = generalOderHistory(v.getVendorId());
		                    if(!record1)
		                		System.out.println("No oreder history found!");
		                    acceptOrder(v.getVendorId());
		                    break;
		                case 3:
		                	boolean record2 = generalOdered(v.getVendorId());
		                    if(!record2)
		                		System.out.println("No oreder history found!");
		                    rejectOrder();
		                	break;
		                case 4:
		                	boolean record3 = updateOrderHistory(v.getVendorId());
		                	if(!record3) {
		                		System.out.println("No oreder history found!");
		                		break;
		                	}
		                	updateOrderStatus();
		                		
		                case 5:
		                	return;
		                default:
		                    System.out.println("Choose an option");
		            
		            }
		        }	
			}
        }
        
        public static void registerVendor() {
        	System.out.println("-----------------------------------------------------------------------------------------------------------");
        	System.out.print("Enter First Name: ");
        	String firstName = sc.next();
        	System.out.print("Enter Last Name: ");
        	String lastName = sc.next();
        	String userName = firstName+" "+lastName;
        	System.out.print("Enter your mobile no: ");
        	String phone = sc.next();
        	System.out.print("Enter your specs that's veg or non veg: ");
        	String specs = sc.next();
        	Vendor v = new Vendor(userName, phone, specs);
        	System.out.print("Create a password. ");
        	String password = "";
        	while(password.length() < 8) {
        		System.out.print("(Password length must be between 8 and 20) : ");
        		password = sc.next();	
        	}
        	String vendorId = OrderFactory.registerNewVendor(v, password);
        	System.out.println("Welcome to our canteen "+userName);
        	System.out.println("Your vendor id is: "+vendorId+" note it for future references.");
        }

/**
 * this method is for VendorOderHistory.
 */
       public static boolean vendorOderHistory(String userId){
    	   boolean t = true;
    	   OrderDetails order[] = OrderFactory.vendorOrderHistory(userId);
    	   if(order != null) {
    		   System.out.println("-----------------------------------------------------------------------------------------------------------");
    		   System.out.println("-----------------------------------------------ORDER HISTORY-----------------------------------------------");
    		   System.out.println("-----------------------------------------------------------------------------------------------------------");
	    	   System.out.println("Order Id"+"                 "+"Customer"+"       "+"Food Id"+"     "+"Quantity"+"      "+"Total Price"+"      "+"Status"); 
	           for(int i=0;i<order.length;i++){
	               System.out.printf("%-24s%-16s%-15s%-13s%-15s%s\n", order[i].getOrderNo(), order[i].getUserId(), 
	            		   order[i].getFoodId(), order[i].getQuantity(), order[i].getOrderValue(), order[i].getOrderStatus()); 
           	//System.out.println(m[i].getFoodId()+"       "+m[i].getFoodName()+"       "+m[i].getFoodPrice());
              }
           System.out.println();
           System.out.println();
    	   }
    	   else {
    		   t = false;
    	   }
    	   return t;
       }

/**
 * this method is for CustomerOrderHistory.
 */
       public static boolean CustomerOrderHistory(String userId){
    	   boolean t = true;
    	   OrderDetails order[] = OrderFactory.customerOrderHistory(userId);
    	   if(order != null) {
    		   System.out.println("-----------------------------------------------------------------------------------------------------------");
    		   System.out.println("-----------------------------------------------ORDER HISTORY-----------------------------------------------");
    		   System.out.println("-----------------------------------------------------------------------------------------------------------");
    		   System.out.println("Order Id"+"                "+"Vendor"+"    "+"Food Id"+"     "+"Quantity"+"      "+"Total Price"+"       "+"Status"); 
    		   for(int i=0;i<order.length;i++){
    			   System.out.printf("%-25s%-10s%-15s%-13s%-15s%s\n", order[i].getOrderNo(), order[i].getVendorId(), 
    					   order[i].getFoodId(), order[i].getQuantity(), order[i].getOrderValue(), order[i].getOrderStatus()); 
           	//System.out.println(m[i].getFoodId()+"       "+m[i].getFoodName()+"       "+m[i].getFoodPrice());
              }
           System.out.println();
           System.out.println();
    	   }
    	   else {
    		   t = false;
    	   }
    	   return t;
       }
       
       public static boolean orderHistoryCancle(String userId){
    	   boolean t = true;
    	   OrderDetails order[] = OrderFactory.orderHistoryCancle(userId);
    	   if(order != null) {
    		   System.out.println("-----------------------------------------------------------------------------------------------------------");
    		   System.out.println("-----------------------------------------------ORDER HISTORY-----------------------------------------------");
    		   System.out.println("-----------------------------------------------------------------------------------------------------------");
    		   System.out.println("Order Id"+"                "+"Vendor"+"    "+"Food Id"+"     "+"Quantity"+"      "+"Total Price"+"       "+"Status"); 
    		   for(int i=0;i<order.length;i++){
    			   System.out.printf("%-25s%-10s%-15s%-13s%-15s%s\n", order[i].getOrderNo(), order[i].getVendorId(), 
    					   order[i].getFoodId(), order[i].getQuantity(), order[i].getOrderValue(), order[i].getOrderStatus()); 
           	//System.out.println(m[i].getFoodId()+"       "+m[i].getFoodName()+"       "+m[i].getFoodPrice());
              }
           System.out.println();
           System.out.println();
    	   }
    	   else {
    		   t = false;
    	   }
    	   return t;
       }
       
       public static boolean CustomerOrderRating(String userId){
    	   boolean t = true;
    	   OrderDetails order[] = OrderFactory.customerOderRating(userId);
    	   if(order != null) {
    	   System.out.println("Order Id"+"                  "+"Vendor"+"    "+"Food Id"+"     "+"Quantity"+"      "+"Total Price"+"      "+"Status"); 
           for(int i=0;i<order.length;i++){
               System.out.printf("%-25s%-10s%-15s%-13s%-15s%s\n", order[i].getOrderNo(), order[i].getVendorId(), 
            		   order[i].getFoodId(), order[i].getQuantity(), order[i].getOrderValue(), order[i].getOrderStatus()); 
           	//System.out.println(m[i].getFoodId()+"       "+m[i].getFoodName()+"       "+m[i].getFoodPrice());
              }
           System.out.println();
           System.out.println();
    	   }
    	   else {
    		   t = false;
    	   }
    	   return t;
       }
       
       public static double cancleOrder() {
    	   String status = "";
    	   String orderId;
    	   do {
    		   System.out.print("Enter the order is: ");
    		   orderId = sc.next();
    		   orderId = orderId.toUpperCase();
    		   status = OrderFactory.checkOrderStatus(orderId);
    		   if(status == null)
    			   System.out.println("Invalid order id.");
    		   else if(status.equals("Delivared")) {
    			   System.out.println("You can't cancle this order.");
    			   return 0.0;
    		   }
    		   
    	   }while(status == null);
    	   return OrderFactory.cancleOrder(orderId);
    	   
       }
       public static void acceptOrder(String vendorId) {
    	   String status="";
    	   String orderId;
    	   do {
    		   System.out.print("Enter the order id(0 to exit): ");
    		   orderId = sc.next();
    		   if(orderId.equals("0"))
    			   return;
    		   orderId = orderId.toUpperCase();
    		   status = OrderFactory.checkOrderStatus(orderId);
    		   if(status == null)
    			   System.out.println("Invalid order id.");
    		   else if(status.equals("Cancled")) {
    			   System.out.println("You can't accept this order.");
    			   return;
    		   }
    		   
    	   }while(status == null);
    	   if(status.equals("Ordered")) {
    		   OrderFactory.acceptOrder(orderId, vendorId);
    		   System.out.println("Order accepted");
    	   }
       }

       public static boolean generalOderHistory(String userId){
    	   boolean t = true;
    	   OrderDetails order[] = OrderFactory.generalOderHistory(userId);
    	   if(order != null) {
    	   System.out.println("Order Id"+"                 "+"Customer"+"       "+"Food Id"+"     "+"Quantity"+"      "+"Total Price"+"      "+"Status"); 
           for(int i=0;i<order.length;i++){
               System.out.printf("%-24s%-16s%-15s%-13s%-15s%s\n", order[i].getOrderNo(), order[i].getUserId(), 
            		   order[i].getFoodId(), order[i].getQuantity(), order[i].getOrderValue(), order[i].getOrderStatus()); 
           	//System.out.println(m[i].getFoodId()+"       "+m[i].getFoodName()+"       "+m[i].getFoodPrice());
              }
           System.out.println();
           System.out.println();
    	   }
    	   else {
    		   t = false;
    	   }
    	   return t;
       }
       public static boolean generalOdered(String userId){
    	   boolean t = true;
    	   OrderDetails order[] = OrderFactory.generalOdered(userId);
    	   if(order != null) {
    	   System.out.println("Order Id"+"                 "+"Customer"+"       "+"Food Id"+"     "+"Quantity"+"      "+"Total Price"+"      "+"Status"); 
           for(int i=0;i<order.length;i++){
               System.out.printf("%-24s%-16s%-15s%-13s%-15s%s\n", order[i].getOrderNo(), order[i].getUserId(), 
            		   order[i].getFoodId(), order[i].getQuantity(), order[i].getOrderValue(), order[i].getOrderStatus()); 
           	//System.out.println(m[i].getFoodId()+"       "+m[i].getFoodName()+"       "+m[i].getFoodPrice());
              }
           System.out.println();
           System.out.println();
    	   }
    	   else {
    		   t = false;
    	   }
    	   return t;
       }
       

       public static void rejectOrder() {
    	   String status="";
    	   String orderId;
    	   do {
    		   System.out.print("Enter the order id(0 to exit): ");
    		   orderId = sc.next();
    		   if(orderId.equals("0"))
    			   return;
    		   orderId = orderId.toUpperCase();
    		   status = OrderFactory.checkOrderStatus(orderId);
    		   if(status == null)
    			   System.out.println("Invalid order id.");
    		   else if(status.equals("Cancled")) {
    			   System.out.println("You can't accept this order.");
    			   return;
    		   }
    		   
    	   }while(status == null);
    	   if(status.equals("Ordered")) {
    		   OrderFactory.rejectOrder(orderId);
    		   System.out.println("Order rejected");
    	   }
       }
       public static void setOrderRating() {
    	   String status = "";
    	   String orderId;
    	   double rating = 0.0;
    	   do {
    		   System.out.print("Enter the order id: ");
    		   orderId = sc.next();
    		   orderId = orderId.toUpperCase();
    		   status = OrderFactory.checkOrderStatus(orderId);
    		   if(status == null)
    			   System.out.println("Invalid order id.");
    		   else if(status.equals("Delivared")) {
    			   break;
    		   }
    		   
    	   }while(status == null);
    	   
    	   do {
    		   System.out.print("Enter yor rating (rating must be within 1.0 and 5.0): ");
    		   rating = sc.nextDouble();
    	   }while((rating<1.0) || (rating>5.0));
    	   OrderFactory.setOrderRating(orderId, rating);
    	   System.out.println("Rating inserted");
    	   
       }
       
       public static void updateOrderStatus() {
    	   String status="";
    	   String orderId;
    	   do {
    		   System.out.print("Enter the order id(0 to exit): ");
    		   orderId = sc.next();
    		   if(orderId.equals("0"))
    			   return;
    		   orderId = orderId.toUpperCase();
    		   status = OrderFactory.checkOrderStatus(orderId);
    		   if(status == null)
    			   System.out.println("Invalid order id.");
    		   else if(status.equals("Cancled")) {
    			   System.out.println("You can't accept this order.");
    			   return;
    		   }
    		   
    	   }while(status == null);
    	   if(status.equals("Accepted")) {
    		   OrderFactory.updateOrderStatusDb(orderId, "Delivered");
    	   }
       }
       public static boolean updateOrderHistory(String id) {
    	   boolean t = true;
    	   OrderDetails order[] = OrderFactory.updateOrderHistory(id);
    	   if(order != null) {
    	   System.out.println("Order Id"+"                 "+"Customer"+"       "+"Food Id"+"     "+"Quantity"+"      "+"Total Price"+"      "+"Status"); 
           for(int i=0;i<order.length;i++){
               System.out.printf("%-24s%-16s%-15s%-13s%-15s%s\n", order[i].getOrderNo(), order[i].getUserId(), 
            		   order[i].getFoodId(), order[i].getQuantity(), order[i].getOrderValue(), order[i].getOrderStatus()); 
           	//System.out.println(m[i].getFoodId()+"       "+m[i].getFoodName()+"       "+m[i].getFoodPrice());
              }
           System.out.println();
           System.out.println();
    	   }
    	   else {
    		   t = false;
    	   }
    	   return t;
       }
}
