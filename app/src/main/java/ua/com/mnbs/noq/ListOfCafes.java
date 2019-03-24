package ua.com.mnbs.noq;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ListOfCafes extends AppCompatActivity {

    ArrayList<Cafe> cafes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_cafes);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String userName = extras.getString("UserName");
        String names = readFile("cafe_names.txt");
        String locations = readFile("cafe_locations.txt");
        String icons = readFile("cafe_icons.txt");
        String emails = readFile("cafe_emails.txt");

        ArrayList<String> name = moveIntoArrayList(names);
        ArrayList<String> location = moveIntoArrayList(locations);
        ArrayList<String> icon = moveIntoArrayList(icons);
        ArrayList<String> email = moveIntoArrayList(emails);

        cafes = createCafeArrayList(name, location, icon, email);
        printListOfCafes(cafes);
        ListView listView = findViewById(R.id.cafe_list);

        listView.setOnItemClickListener((adapter, view, position, l) -> {
            String tempEmail;
            tempEmail = cafes.get(position).getCafeEmail();
            if (position == cafes.size()-1) {
                tempEmail = tempEmail.substring(0,tempEmail.length()-1);
            }
            Intent OpenMenu = new Intent(ListOfCafes.this, ListOfMeals.class);
            OpenMenu.putExtra("UserName", userName);
            OpenMenu.putExtra("cafe name", cafes.get(position).getCafeName());
            OpenMenu.putExtra("cafe address", cafes.get(position).getCafeLocation());
            OpenMenu.putExtra("email", tempEmail);
            OpenMenu.putExtra("position", position);
            deleteFile("counter.txt");
            WriteToFile("counter.txt",makeNewOrderFileName(ReadFromFileNotAsset("counter.txt")));
            WriteToFile("Order"+ReadFromFileNotAsset("counter.txt")+".txt",cafes.get(position).getCafeName()+"\n"+cafes.get(position).getCafeLocation());
            startActivity(OpenMenu);
            overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);
        });

        ImageView buttonToMain = findViewById(R.id.horse_icon_from_cafes);

        buttonToMain.setOnClickListener((View v) -> {
            Intent toMainActivity = new Intent(ListOfCafes.this, MainActivity.class);
            startActivity(toMainActivity);
            overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
        });

        ImageView backButton = findViewById(R.id.back_from_cafes) ;

        backButton.setOnClickListener((View v) -> finish());


    }

    //TODO: makeNewOrderFileName(ReadFromFileNotAsset("counter.txt"))

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
    public String ReadFromFileNotAsset(String file){
        String text= "";
        try{
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch (IOException e) {
            if (file == "counter.txt") {
                WriteToFile("counter.txt","0");
            } else {
                Toast.makeText(ListOfCafes.this, "Помилка у читанні файлу",
                        Toast.LENGTH_SHORT).show();
            }
        }
        return text;

    }

    public ArrayList<Integer> transferIntoId(ArrayList<String> icon){
        ArrayList<Integer> returnValue = new ArrayList<>();
        Resources r = getResources();
        int drawableId = -1;
        String temp = "";
        for (int i=0; i<icon.size(); i++){
            temp = icon.get(i);
            if (i != icon.size()-1) {
                temp = temp.substring(0, temp.length() - 1);
            }
            drawableId = r.getIdentifier(temp, "drawable", getPackageName());
            returnValue.add(drawableId);
        }
        return returnValue;
    }


    public String makeNewOrderFileName(String text){
        String smth ="";
        try {
            int counter = Integer.parseInt(text);
            counter++;
            smth = Integer.toString(counter);
        } catch (Exception e) {
            smth="0";
        }
            return smth;
    }

    private ArrayList<String> moveIntoArrayList(String text) {
        return (ArrayList<String>) Arrays.asList(text.split("\\n"));
    }

    protected void WriteToFile(String file, String text)
    {
        try {
            FileOutputStream fos = openFileOutput(file,Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        }
        catch (IOException e){
            Toast.makeText(ListOfCafes.this,"Error Writing to file",Toast.LENGTH_SHORT).show();
        }
    }



    private void printListOfCafes(ArrayList<Cafe> cafes) {
        CafeAdapter adapter = new CafeAdapter(this, cafes);
        ListView listView = findViewById(R.id.cafe_list);
        listView.setAdapter(adapter);
    }


    private boolean isMistakeInFiles(ArrayList<String> name, ArrayList<String> location, ArrayList<String> icon, ArrayList<String> email) {
        return !(name.size() == location.size() && icon.size() == email.size() && name.size() == icon.size());
    }

    private ArrayList<Cafe> createCafeArrayList(ArrayList<String> name, ArrayList<String> location, ArrayList<String> icon, ArrayList<String> email) {
        ArrayList<Cafe> cafes = new ArrayList<>();
        ArrayList<Integer> iconId = transferIntoId(icon);
        if (isMistakeInFiles(name, location, icon, email)) {
            Toast.makeText(getApplicationContext(), "Something is wrong with your text files.",
                    Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < name.size(); i++)
                cafes.add(new Cafe(name.get(i), location.get(i), iconId.get(i), email.get(i)));
        }
        return cafes;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }
}
