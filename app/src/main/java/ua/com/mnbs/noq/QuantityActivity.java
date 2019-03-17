package ua.com.mnbs.noq;

import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.Path;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class QuantityActivity extends AppCompatActivity {

    ArrayList<Meal> meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_quantity);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String userName = extras.getString("UserName");
        final int numberOfCheckedItems = extras.getInt("number of checked meals");
        final String cafeEmail = extras.getString("email");
        final String currentCafeAddress = extras.getString("cafe address");


        final String nameOfCafeForBackButton = extras.getString("cafe name for intent");
        final int positionForBackButton = extras.getInt("position for intent");

       meals = new ArrayList<>();

        String tempName = "";
        String tempPrice = "";
        for (int i=0; i<numberOfCheckedItems; i++){
            tempName = extras.getString("name"+i);
            tempPrice = extras.getString("price"+i);
            meals.add(new Meal(tempName, tempPrice));
        }

        QuantityAdapter adapter = new QuantityAdapter( this, meals);
        ListView listView = (ListView) findViewById(R.id.quantity_list);
        listView.setAdapter(adapter);

        ImageView buttonToMain = (ImageView) findViewById(R.id.horse_icon_from_quantity);

        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(QuantityActivity.this, MainActivity.class);
                startActivity(toMainActivity);
                overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
            }
        });

        ImageView backButton = (ImageView) findViewById(R.id.back_from_quantity) ;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button chooseQuantity = (Button) findViewById(R.id.choose_quantity_button);

        chooseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent OpenTimeActivity = new Intent(QuantityActivity.this, TimeActivity.class);
                OpenTimeActivity.putExtra("UserName", userName);
                OpenTimeActivity.putExtra("cafe name", nameOfCafeForBackButton);
                OpenTimeActivity.putExtra("cafe address", currentCafeAddress);
                OpenTimeActivity.putExtra("number of checked items", numberOfCheckedItems);
                OpenTimeActivity.putExtra("email", cafeEmail);
                for (int i=0; i<numberOfCheckedItems; i++){
                    OpenTimeActivity.putExtra("meal name"+i, meals.get(i).getMealName());
                    OpenTimeActivity.putExtra("meal quantity"+i, meals.get(i).getQuantity());
                    OpenTimeActivity.putExtra("meal price"+i, meals.get(i).getMealPrice());
                }
                startActivity(OpenTimeActivity);
                overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);

            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }
}
