package ua.com.mnbs.noq;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CafeAdapter extends ArrayAdapter<Cafe> {

    CafeAdapter(Activity context, ArrayList<Cafe> cafes) {
        super(context, 0, cafes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cafe_list_item, parent, false);
        }
        Cafe currentCafe = getItem(position);

        TextView cafeNameTextView = (TextView) listItemView.findViewById(R.id.cafe_name_text_view);
        cafeNameTextView.setText(currentCafe.getCafeName());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.cafe_icon_view);
        if (currentCafe.hasImage()) {
            imageView.setImageResource(currentCafe.getDrawableId());
        } else {
            imageView.setVisibility(View.GONE);
        }
            TextView cafeLocationTextView = (TextView) listItemView.findViewById(R.id.cafe_location_text_view);
            cafeLocationTextView.setText(currentCafe.getCafeLocation());
            return listItemView;

    }
}
