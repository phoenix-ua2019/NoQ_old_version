package ua.com.mnbs.noq;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {
    OrderAdapter(Activity context, ArrayList<Order> orders) {
        super(context, 0, orders);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.order_list_item, parent, false);
        }
        Order currentOrder =getItem(position);

        TextView cafeTextView = (TextView) listItemView.findViewById(R.id.cafe_text_view);
        cafeTextView.setText(currentOrder.getmCafe());


        TextView sumTextView = (TextView) listItemView.findViewById(R.id.sum_text_view);
        sumTextView.setText(currentOrder.getmSum());

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);
        timeTextView.setText(currentOrder.getmTime());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(currentOrder.getmDate().toString());

        return listItemView;

    }


}
