package ua.com.mnbs.noq;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuantityAdapter extends ArrayAdapter<Meal> {
    QuantityAdapter(Activity context, ArrayList<Meal> meals) {
        super(context, 0, meals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.quantity_item, parent, false);
        }
        final Meal currentMeal = getItem(position);

        TextView mealNameTextView = (TextView) listItemView.findViewById(R.id.meal_name_text_view);
        mealNameTextView.setText(currentMeal.getMealName());

        final TextView priceTypeTextView = (TextView) listItemView.findViewById(R.id.price_type_text_view);
        priceTypeTextView.setText(currentMeal.getMealPrice() + " грн");

        final TextView quantityTextView = (TextView) listItemView.findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(currentMeal.getQuantity()));

        Button plusButton = (Button) listItemView.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = currentMeal.getQuantity() + 1;
                currentMeal.setQuantity(quantity);
                quantityTextView.setText(String.valueOf(currentMeal.getQuantity()));
                int total = Integer.parseInt(currentMeal.getMealPrice()) * quantity;
                priceTypeTextView.setText(total + " грн");

            }
        });

        Button minusButton = (Button) listItemView.findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMeal.getQuantity() == 1) {
                    Toast.makeText(getContext(), "Не можна обрати від'ємну кількість страв", Toast.LENGTH_SHORT).show();
                } else {
                    int quantity = currentMeal.getQuantity() - 1;
                    currentMeal.setQuantity(quantity);
                    quantityTextView.setText(String.valueOf(currentMeal.getQuantity()));
                    int total = Integer.parseInt(currentMeal.getMealPrice()) * quantity;
                    priceTypeTextView.setText(String.valueOf(total) + " грн");
                }
            }
        });

        return listItemView;
    }
}
