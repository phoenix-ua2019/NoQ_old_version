package ua.com.mnbs.noq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class TimeActivity extends AppCompatActivity {

    ArrayList<Meal> meals;

    TimePicker floatTime;
    TextView orderTime;
    Button submitTime;

    final int closingHour = 22;
    final int openingHour = 7;
    final int preparationTime = 15;
    final int minutesInHour = 60;

    boolean wasShownToastForPast = false;
    boolean wasShownTooEarlyToast = false;
    boolean wasShownTooLateToast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        floatTime = (TimePicker) findViewById(R.id.clock);
        orderTime = (TextView) findViewById(R.id.text_time);

        meals = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String userName = extras.getString("UserName");
        final int numberOfCheckedItems = extras.getInt("number of checked items");
        final String nameOfCafe = extras.getString("cafe name");
        final String cafeAddress = extras.getString("cafe address");
        final String cafeEmail = extras.getString("email");

        String tempName = "";
        String tempPrice = "";
        int tempQuantity;
        for (int i=0; i<numberOfCheckedItems; i++){
            tempName = extras.getString("meal name"+i);
            tempPrice = extras.getString("meal price"+i);
            tempQuantity = extras.getInt("meal quantity"+i);
            meals.add(new Meal(tempName, tempPrice));
            meals.get(i).setQuantity(tempQuantity);
        }

        floatTime.setIs24HourView(true);

        final Integer currentHour = floatTime.getHour();
        final Integer currentMinute = floatTime.getMinute();

      if (isCafeOpen(currentHour, currentMinute)){
            orderTime.setText(updateDisplay());
       }
       else {

            Intent toMainActivity = new Intent(TimeActivity.this, MainActivity.class);
            startActivity(toMainActivity);
        }

        floatTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
               if (isCafeOpen(hourOfDay, minute)) {
                    if (isAllowableTime(hourOfDay, currentHour, minute, currentMinute)) {
                        updateDisplay(hourOfDay, minute);
                    }
                }
            }
        });

        submitTime = (Button) findViewById(R.id.submit_time);

        submitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenMyOrder = new Intent(TimeActivity.this, MyOrdersActivity.class);
                OpenMyOrder.putExtra("UserName", userName);
                OpenMyOrder.putExtra("cafe name", nameOfCafe);
                OpenMyOrder.putExtra("cafe address", cafeAddress);
                OpenMyOrder.putExtra("number of checked items", numberOfCheckedItems);
                OpenMyOrder.putExtra("order time",convertTime(floatTime.getHour(), floatTime.getMinute()));
                OpenMyOrder.putExtra("email", cafeEmail);
                for (int i=0; i<numberOfCheckedItems; i++){
                    OpenMyOrder.putExtra("meal name"+i, meals.get(i).getMealName());
                    OpenMyOrder.putExtra("meal quantity"+i, meals.get(i).getQuantity());
                    OpenMyOrder.putExtra("meal price"+i, meals.get(i).getMealPrice());
                }
                if (checkPreparationTime(floatTime.getHour(), floatTime.getMinute(), currentHour, currentMinute)) {
                    startActivity(OpenMyOrder);
                    overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Май совість, дай хоча б 15 хвилин на приготування", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView backButton = (ImageView) findViewById(R.id.back_from_time) ;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView buttonToMain = (ImageView) findViewById(R.id.horse_icon_from_time);

        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(TimeActivity.this, MainActivity.class);
                startActivity(toMainActivity);
                overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
            }
        });
    }

// функція виконує встановлення поточного часу і передачу його до інших функцій
    private void updateDisplay(int hour, int minute) {
        Integer orderHour = hour;
        Integer orderMinute = minute;
        String mOrderTime = convertTime(orderHour, orderMinute);
        orderTime.setText(mOrderTime);
    }

// функція одержує поточний час
    private String updateDisplay() {
        Integer currentHour = floatTime.getHour();
        Integer currentMinute = floatTime.getMinute();
        String mOrderTime = convertTime(currentHour, currentMinute);
        return mOrderTime;
    }

// запис поточного дійсного числа в форматі String
    private String fixZero(Integer num) {
        String stringNum;
        if (num < 10) {
            stringNum = "0";
            stringNum += num.toString();
        } else {
            stringNum = num.toString();
        }
        return stringNum;
    }


// передача часу у форматі String
    private String convertTime(Integer hour, Integer minute) {
        String convertedTime = fixZero(hour);
        convertedTime += ":";
        convertedTime += fixZero(minute);
        return convertedTime;
    }


// перевірка на правильність часу замовлення
    private boolean isAllowableTime(int orderHour, Integer currentHour, int orderMinute, Integer currentMinute) {

        if (orderHour < currentHour) {
            if (!wasShownToastForPast) {
                Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                wasShownToastForPast = true;
            }
            floatTime.setHour(currentHour);
            floatTime.setMinute(currentMinute);
            return false;

        } else if (orderHour == currentHour) {
            if (orderMinute < currentMinute){
                if (!wasShownToastForPast) {
                    Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                    wasShownToastForPast = true;
                }
                floatTime.setHour(currentHour);
                floatTime.setMinute(currentMinute);
                return false;
            }
        }
        return true;
    }


// каунтер хвилин
    private int cutMinute(int minute){
        if (minute >= minutesInHour){
            minute -= minutesInHour;
        }
        return minute;
    }


// не впевнений, але ця функція перевіряє, скільки часу пройшло з початку прийому замовлення
    private boolean isNearNewHour(Integer currentMinute){
        if (minutesInHour - preparationTime <= currentMinute){
            return true;
        }
        return false;
    }


// вказівник на доступність замовлення в даний момент часу
    private boolean isCafeOpen(int orderHour, int orderMinute) {
        if (orderHour > closingHour) {
            if (!wasShownTooLateToast) {
                Toast.makeText(this, "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
                wasShownTooLateToast = true;
            }
            floatTime.setHour(closingHour);
            floatTime.setMinute(0);
            return false;
        }

        if (orderHour == closingHour && orderMinute > 0) {
            if (!wasShownTooLateToast) {
                Toast.makeText(this, "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
                wasShownTooLateToast = true;
            }
            floatTime.setHour(closingHour);
            floatTime.setMinute(0);
            return false;
        }

        if (orderHour < openingHour) {
            if (!wasShownTooEarlyToast) {
                Toast.makeText(this, "Вибач, але кафе ще зачинено", Toast.LENGTH_SHORT).show();
                wasShownTooEarlyToast = true;
            }
            floatTime.setHour(openingHour);
            floatTime.setMinute(0);
            return false;
        }
        return true;
    }

//перевірка часу приготування
    private boolean checkPreparationTime(int orderHour, int orderMinute, int currentHour, int currentMinute) {
        if (isNearNewHour(currentMinute)) {
            if (orderHour == currentHour) {
                return false;
            }

            if(orderHour == currentHour + 1) {
                if (orderMinute < cutMinute(currentMinute + preparationTime)) {
                    return false;
                }
            }
        }

        else{
            if (orderHour == currentHour) {
                if (orderMinute < currentMinute + preparationTime) {
                    return false;
                }
            }
        }
        return  true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }
}