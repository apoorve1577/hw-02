package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ViewDrinksActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<Drink> allDrinks = new ArrayList<>();
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drinks);



        Drink a = new Drink(1, 30, new Date(2022, 7, 13, 4, 22));
        Drink b = new Drink(5, 25, new Date(2022, 7, 13, 5, 1));
        Drink c = new Drink(12, 15, new Date(2022, 7, 13, 19, 22));


        allDrinks.add(a);
        allDrinks.add(b);
        allDrinks.add(c);


        setView(currentIndex);


        ImageButton backBtn = findViewById(R.id.backBtn);
        ImageButton nextBtn = findViewById(R.id.nextBtn);
        ImageButton deleteBtn = findViewById(R.id.deleteBtn);
        Button closeBtn = findViewById(R.id.closeBtn);

        backBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

        Log.d("currentIndex", "" + currentIndex);

        switch (view.getId()){

            case R.id.backBtn:
                currentIndex--;
                Log.d("currentIndex", "" + currentIndex);
                if(currentIndex < 0) {
                    currentIndex = allDrinks.size()-1;
                }
                Log.d("currentIndex", "new Index after back" + currentIndex);

                setView(currentIndex);
                break;
            case R.id.nextBtn:
                Log.d("currentIndex", "" + currentIndex);
                currentIndex++;
                if(currentIndex > (allDrinks.size()-1)) {
                    currentIndex = 0;
                }
                Log.d("currentIndex", "new Index after next" + currentIndex);

                setView(currentIndex);
                break;
            case R.id.deleteBtn:
                Log.d("currentIndex", "new Index before delete" + currentIndex);

                allDrinks.remove(currentIndex);
                currentIndex--;
                Log.d("currentIndex", "new Index after delete" + currentIndex);

                if(allDrinks.size() == 0) {
                    Intent i = new Intent(ViewDrinksActivity.this, MainActivity.class);
                    i.putExtra("allDrinks", 0);
                    startActivity(i);
                }
                if(currentIndex < 0) {
                    currentIndex = allDrinks.size();
                }
                if(currentIndex > allDrinks.size()-1) {
                    currentIndex = 0;
                }
                setView(currentIndex);
                break;
            case R.id.closeBtn:

        }
    }


    public void setView(int currentIndex) {

        TextView totalDrinks = findViewById(R.id.totalDrinks);
        totalDrinks.setText("Drink " + (currentIndex+1) + " out of " + allDrinks.size());

        TextView alcoholContent = findViewById(R.id.alcoholContent);
        TextView alcoholPercent = findViewById(R.id.alcoholPercent);

        alcoholPercent.setText(allDrinks.get(currentIndex).percent + "% Alcohol");
        alcoholContent.setText(allDrinks.get(currentIndex).size + "Oz");


        TextView addedDate = findViewById(R.id.addedTime);
        addedDate.setText("Added " + allDrinks.get(currentIndex).createDatetime);
    }
}