package ua.com.mnbs.noq;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyOrderAdapter extends ArrayAdapter<Meal> {
    MyOrderAdapter(Activity context, ArrayList<Meal> meals) {
        super(context, 0, meals);
    }

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


