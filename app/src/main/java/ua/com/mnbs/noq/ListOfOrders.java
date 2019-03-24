package ua.com.mnbs.noq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ListOfOrders extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_orders);

        //ця вся конструкція спочатку знаходить кнопку по id(тут кнопка це зоюраження),
        // далі встановлює лістенер а потім в анонімному класі задає поведінку лістенера
        // в даному випадку перехід на на мейн актівіті
        ImageView buttonToMain = (ImageView) findViewById(R.id.horse_icon_from_my_list_of_orders);
        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(ListOfOrders.this, MainActivity.class);
                startActivity(toMainActivity);
                overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
            }
        });

        //ця вся конструкція спочатку знаходить кнопку по id(тут кнопка це зоюраження),
        // далі встановлює лістенер а потім в анонімному класі задає поведінку лістенера
        // в даному випадку поведінка це закриття актівіті
        ImageView backButton = (ImageView) findViewById(R.id.back_from_my_list_of_orders) ;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }
}
