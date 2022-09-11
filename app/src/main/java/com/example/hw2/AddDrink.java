package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Date;

public class AddDrink extends AppCompatActivity implements View.OnClickListener{


    public int drinkSize ;
    public int alcoholPercent ;

    public static String DRINK_KEY = "drink";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        findViewById(R.id.addDrinkBut).setOnClickListener(this);

        RadioGroup radioGroupDrink = findViewById(R.id.radioGroupDrink);
        radioGroupDrink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(i == R.id.oz1){
                    drinkSize = 1;
                }
                if(i == R.id.oz5){
                    drinkSize = 5;
                }
                if(i == R.id.oz12){
                    drinkSize = 12;
                }
            }
        });

        SeekBar seekbar = findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView progress = findViewById(R.id.progress);
                progress.setText(i + "%");
                alcoholPercent = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.addDrinkBut){
            Intent intent = new Intent(AddDrink.this, MainActivity.class);
            intent.putExtra(DRINK_KEY, new Drink(drinkSize, alcoholPercent, new Date()));
            setResult(RESULT_OK,intent);
            startActivity(intent);
        }

        if(view.getId() == R.id.cancel){
            Intent intent = new Intent(AddDrink.this, MainActivity.class);
            startActivity(intent);

        }

    }
}