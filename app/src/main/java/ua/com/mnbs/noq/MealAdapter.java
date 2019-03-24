package ua.com.mnbs.noq;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MealAdapter extends ArrayAdapter<Meal> {
    MealAdapter(Activity context, ArrayList<Meal> meals) {
        super(context, 0, meals);
    }

    /**
     * Метод getView відповідає, за вивід: назви страви, ціни та чекбоксу.
     *
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
                    R.layout.menu_list_item, parent, false);
        }
        final Meal currentMeal = getItem(position);


        TextView mealNameTextView = (TextView) listItemView.findViewById(R.id.meal_name_text_view);
        mealNameTextView.setText(currentMeal.getMealName());

        TextView priceTypeTextView = (TextView) listItemView.findViewById(R.id.price_type_text_view);
        priceTypeTextView.setText(currentMeal.getMealPrice() + " грн");

        CheckBox mealCheckBox = (CheckBox) listItemView.findViewById(R.id.meal_checkbox);
        currentMeal.setChecked(mealCheckBox.isChecked());
        mealCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Метод onCheckedChanged відповідає за коректну роботу чекбоксу.
             * @param buttonView
             * @param isChecked
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentMeal.setChecked(isChecked);
                if (isChecked == true)
                currentMeal.numberOfCheckedItems++;
                else currentMeal.numberOfCheckedItems--;
            }
        });
        return listItemView;
    }
}
