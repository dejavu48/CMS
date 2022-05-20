package com.Hexaware.CMS.Model;

/**
 * food class used to display food information.
 * @author hexware
 */
public class Menu {
    private String foodId;
    private String foodName;
    private String isVeg;
    private double foodPrice;
    private double calories;
    private double ratings;

    public Menu(){}

    public Menu(String foodId, String foodName, String isVeg, double foodPrice, double calories, double ratings){
        this.foodId=foodId;
        this.foodName=foodName;
        this.isVeg = isVeg;
        this.foodPrice=foodPrice;
        this.calories = calories;
        this.ratings = ratings;

    }
    public void setFoodId(String foodId){
        this.foodId=foodId;
    }

    public String getFoodId(){
        return foodId;
    }

    public void setFoodName(String foodName){
        this.foodName=foodName;
    }

    public String getFoodName(){
        return foodName;
    }

    public void setFoodPrice(int foodPrice){
        this.foodPrice=foodPrice;
    }

    public double getFoodPrice(){
        return foodPrice;
    }
    
    public String getIsVeg() {
		return isVeg;
	}

	public void setIsVeg(String isVeg) {
		this.isVeg = isVeg;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getRatings() {
		return ratings;
	}

	public void setRatings(double ratings) {
		this.ratings = ratings;
	}

	public String toString(){
        return "food id:"+foodId+"food Name:"+foodName+"veg/nonveg:"+isVeg+"food Price"+foodPrice+"Calories:"+calories+"Rating"+ratings;
    }
}