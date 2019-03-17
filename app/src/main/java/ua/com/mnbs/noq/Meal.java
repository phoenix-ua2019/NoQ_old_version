package ua.com.mnbs.noq;

public class Meal {
    private String mMealName;
    private String mMealPrice;
    private boolean mIsChecked;
    private int mQuantity;
    public static int numberOfCheckedItems;

    Meal(String mealName, String mealPrice){
        mMealName = mealName;
        mMealPrice = mealPrice;
        mIsChecked = false;
        mQuantity = 1;
    }
    public String getMealName(){
        return mMealName;
    }
    public String getMealPrice(){
        return mMealPrice;
    }
    protected void setMealPrice() {}
    public boolean getChecked() { return mIsChecked; }
    public void setChecked(boolean isChecked){
        mIsChecked = isChecked;
    }
    public int getQuantity() { return mQuantity; }
    public void setQuantity(int quantity) { mQuantity = quantity; }
}
