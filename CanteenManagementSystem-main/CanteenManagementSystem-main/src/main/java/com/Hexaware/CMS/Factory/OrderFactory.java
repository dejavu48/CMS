package com.Hexaware.CMS.Factory;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;

import com.Hexaware.CMS.Model.Customer;
import com.Hexaware.CMS.Model.Menu;
import com.Hexaware.CMS.Model.OrderDetails;
import com.Hexaware.CMS.Model.Vendor;
import com.Hexaware.CMS.Persistence.OrderDb;

/**
 * OrderFactory class used to fetch and insert data to database.
 * @author hexware
 */
public class OrderFactory {
    
    public static double OrderFood(String userId, String fid, String vid, int quantity){
       double result= OrderDb.insertDb(userId, fid, vid, quantity);
       return result;
    }

    public static Menu[] fetchMenu(){
        Menu menu[]=OrderDb.fetchDb();
        return menu;
    }

    public static Customer customerProfile(String loginId, String password){
    	Customer customer = OrderDb.customerProfileDb(loginId, password);
    	return customer;
    }
    
    public static void registerNewCustomer(String userName, String phone, String email, String password) {
    	OrderDb.registerNewCustomerDb(userName, phone, email, password);
    }
    
    public static boolean checkUserId(String userId) {
    	return OrderDb.checkUserIdDb(userId);
    }
    
    public static Vendor vendorProfile(String loginId, String password){
    	Vendor vendor = OrderDb.vendorProfileDb(loginId, password);
    	return vendor;
    }
    public static String registerNewVendor(Vendor vendor, String password) {
    	return OrderDb.registerNewVendorDb(vendor, password);
    }
    public static boolean foodId(String foodId) {
    	return OrderDb.foodIdDb(foodId);
    }
    /*
     * * This method is for fetching food price  
     */
    public static double foodPrice(String foodId) {
    	return OrderDb.foodPriceDb(foodId);
    }
    public static void updateUserBalance(String userId, double balance) {
    	OrderDb.updateUserBalanceDb(userId, balance);
    }
    public static OrderDetails[] customerOrderHistory(String userId){
    	return OrderDb.customerOderHistoryDb(userId);
    }
    public static OrderDetails[] customerPendingOrderHistory(String userId){
    	return OrderDb.customerOderHistoryDb(userId);
    }
    public static OrderDetails[] vendorOrderHistory(String userId){
    	return OrderDb.vendorOderHistoryDb(userId);
    }
    public static OrderDetails[] generalOderHistory(String userId) {
    	return OrderDb.generalOrderHistoryDb(userId);
    }
    public static OrderDetails[] generalOdered(String userId) {
    	return OrderDb.generalOrderedDb(userId);
    }
    public static String checkOrderStatus(String orderId) {
    	return OrderDb.checkOrderStatusDb(orderId);
    }
    
    public static double cancleOrder(String orderId) {
    	return OrderDb.cancleOrderDb(orderId);
    }
    
    public static void acceptOrder(String orderId, String vendorId) {
    	OrderDb.acceptOrderDb(orderId, vendorId);
    }
    public static void rejectOrder(String orderId) {
    	OrderDb.rejectOrderDb(orderId);
    }
    public static OrderDetails[] customerOderRating(String userId) {
    	return OrderDb.customerOderRatingDb(userId);
    }
    public static void setOrderRating(String orderId, double rating){
    	OrderDb.setOrderRatingDb(orderId,rating);
    }
    public static void updateOrderStatusDb(String orderId, String status) {
    	OrderDb.updateOrderStatusDb(orderId,status);
    }
    public static OrderDetails[] updateOrderHistory(String userId) {
    	return OrderDb.updateOrderHistoryDb(userId);
    }
    public static void updateUserPasswordDb(String userId, String password) {
    	OrderDb.updateUserPasswordDb(userId, password);
    }
    public static OrderDetails[] orderHistoryCancle(String userId) {
    	return OrderDb.orderHistoryCancleDb(userId);
    }
    // public static String acceptRejectOrder(){}
}
