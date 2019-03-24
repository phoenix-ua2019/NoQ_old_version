package ua.com.mnbs.noq;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListOfMeals extends AppCompatActivity {

    ArrayList<Meal> meals;

    boolean wasShownToast = false;

    /**
     * Метод onCreate викликається операційною системою, коли ініціалізується activity.
     * Встановлює інтерфейс з наступними елементами:
     * Список страв, їх цін та відповідних чекбоксів,
     * Кнопка, яка повертає на MainActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String userName = extras.getString("UserName");
        String currentCafe = extras.getString("cafe name");
        final String currentCafeAddress = extras.getString("cafe address");
        final String cafeEmail = extras.getString("email");
        int position = extras.getInt("position");

        final String cafeNameForIntent = currentCafe;
        final int positionForIntent = position;

        currentCafe = currentCafe.trim();

        if (position == 0)
            currentCafe = currentCafe.substring(1,currentCafe.length());

        final String menuFileDirectory = currentCafe + "_menu.txt";
        final String pricesFileDirectory = currentCafe + "_prices.txt";

        String names = readFile(menuFileDirectory);
        ArrayList<String> name = moveIntoArrayList(names);
        String prices = readFile(pricesFileDirectory);
        ArrayList<String> price = moveIntoArrayList(prices);

        meals = new ArrayList<>();

        if (isMistakeInFiles(name, price))
            Toast.makeText(getApplicationContext(), "Something is wrong with your text files.",
                    Toast.LENGTH_SHORT).show();
        else {
            meals = createMealArrayList(name, price);
            printListOfMeals(meals);
        }


        ListView listView = (ListView) findViewById(R.id.menu_list);
        final Button chooseDishes = (Button) findViewById(R.id.choose_dishes_button);
        meals.get(0).numberOfCheckedItems = 0;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Метод onItemClick відповідає за роботу чекбоксу при виборі страви.
             * @param adapter
             * @param v
             * @param position
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long l) {
                if (v != null) {
                    CheckBox checkBox = (CheckBox)v.findViewById(R.id.meal_checkbox);
                    checkBox.setChecked(!checkBox.isChecked());
                }
            }

        });

        /**
         * Створюється кнопка backButton, яка повертає на MainActivity
         */
        ImageView buttonToMain = (ImageView) findViewById(R.id.horse_icon_from_menu);

        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(ListOfMeals.this, MainActivity.class);
                startActivity(toMainActivity);
                overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
            }
        });

        ImageView backButton = (ImageView) findViewById(R.id.back_from_menu) ;

        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Завершує роботу данної activity.
             * @param v
             */
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * Кнопка, яка відповідає за обробку обраних страв.
         */
        chooseDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (meals.get(0).numberOfCheckedItems == 0) {
                    if (!wasShownToast) {
                        Toast.makeText(getApplicationContext(), "Виберіть, будь ласка, страву",
                                Toast.LENGTH_SHORT).show();
                        wasShownToast = true;
                    }
                }
                else {
                    Intent OpenQuantityActivity = new Intent(ListOfMeals.this, QuantityActivity.class);
                    OpenQuantityActivity.putExtra("UserName", userName);
                    OpenQuantityActivity.putExtra("cafe name for intent", cafeNameForIntent);
                    OpenQuantityActivity.putExtra("position for intent", positionForIntent);
                    OpenQuantityActivity.putExtra("cafe address", currentCafeAddress);
                    OpenQuantityActivity.putExtra("email", cafeEmail);
                    int index = 0;
                    for (int i=0; i<meals.size(); i++) {
                        if (meals.get(i).getChecked()) {
                            OpenQuantityActivity.putExtra("number of checked meals", meals.get(0).numberOfCheckedItems);
                            OpenQuantityActivity.putExtra("name"+index,meals.get(i).getMealName());
                            OpenQuantityActivity.putExtra("price"+index,meals.get(i).getMealPrice());
                            index++;
                        }
                    }
                    startActivity(OpenQuantityActivity);
                    overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);
                }

            }
        });

    }

    private String readFile(String fileName) {
        String text = "";
        try {
            InputStream inputStream = getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
            text += '\0';
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    private ArrayList<String> moveIntoArrayList(String text) {
        ArrayList<String> returnValue = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n' || text.charAt(i) == '\0') {
                returnValue.add(temp);
                temp = "";
                continue;
            }
            temp += text.charAt(i);
        }
        return returnValue;
    }


    private void printListOfMeals(ArrayList<Meal> meals) {
        MealAdapter adapter = new MealAdapter(this, meals);
        ListView listView = (ListView) findViewById(R.id.menu_list);
        listView.setAdapter(adapter);
    }

    private boolean isMistakeInFiles(ArrayList<String> name, ArrayList<String> price) {
        return (name.size() != price.size());
    }

    private ArrayList<Meal> createMealArrayList(ArrayList<String> name, ArrayList<String> price) {
        ArrayList<Meal> meals = new ArrayList<>();
        for (int i = 0; i < name.size(); i++) {
            price.set(i, price.get(i).trim());
            meals.add(new Meal(name.get(i), price.get(i)));
        }
        return meals;
    }

    /**
     * Завершує роботу данної activity.
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }
}
