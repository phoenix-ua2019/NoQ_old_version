package ua.com.mnbs.noq;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ListOfOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_orders);

        ImageView buttonToMain = (ImageView) findViewById(R.id.horse_icon_from_my_list_of_orders);

        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(ListOfOrders.this, MainActivity.class);
                startActivity(toMainActivity);
                overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
            }
        });

        ImageView backButton = (ImageView) findViewById(R.id.back_from_my_list_of_orders) ;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void printListOfOrders(ArrayList<Order> orders) {
        OrderAdapter adapter = new OrderAdapter(this, orders);
        ListView listView = (ListView) findViewById(R.id.order_list);
        listView.setAdapter(adapter);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }
}
