package ua.com.mnbs.noq;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyOrderAdapter extends ArrayAdapter<Meal> {
    MyOrderAdapter(Activity context, ArrayList<Meal> meals) {
        super(context, 0, meals);
    }

    /**
     * цей метод підставляє дані в форму.
     * тобто він для кожної позиції в кожному елементі ListView підставляє те що потрібно.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.my_order_item, parent, false);
        }
        final Meal currentMeal = getItem(position);


        TextView mealNameTextView = (TextView) listItemView.findViewById(R.id.dish_name);
        mealNameTextView.setText(currentMeal.getMealName());

        TextView priceTypeTextView = (TextView) listItemView.findViewById(R.id.dish_price);
        priceTypeTextView.setText(String.valueOf(Integer.parseInt(currentMeal.getMealPrice()) * currentMeal.getQuantity()) + " грн");

        TextView quantityTextView = (TextView) listItemView.findViewById(R.id.dish_quantity);
        quantityTextView.setText(String.valueOf(currentMeal.getQuantity()));
        return listItemView;
    }
}


