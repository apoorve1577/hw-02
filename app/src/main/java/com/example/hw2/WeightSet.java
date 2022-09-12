package com.example.hw2;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class WeightSet extends AppCompatActivity implements View.OnClickListener {


    public static String USER_KEY = "user";
    public Integer weight = 0;
    public String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_set);
        findViewById(R.id.weightGender).setOnClickListener(this);
        findViewById(R.id.enterWeight).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        RadioGroup genderGroup = findViewById(R.id.radioGroupGender);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i== R.id.male){
                    gender = "male";
                }
                if(i == R.id.female){
                    gender = "female";
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.weightGender){
            EditText weightText = findViewById(R.id.enterWeight);

            if(isEmpty(weightText.getText())) {
                Toast.makeText(this,"Please fill all details", Toast.LENGTH_SHORT);
            } else {
                String s =  weightText.getText().toString();
                if(onlyDigits(s,s.length())){
                    weight = Integer.parseInt(s);
                }
                else{
                    Toast.makeText(this, "Only numbers allowed", Toast.LENGTH_SHORT).show();
                }

            }
            if(weight == 0 || gender == "") {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(WeightSet.this, MainActivity.class);
                intent.putExtra(USER_KEY, new User(gender, weight));
                Log.d("demo", "This is weight set" + weight + "");
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        if(view.getId() == R.id.cancel){
            Intent intent = new Intent(WeightSet.this, MainActivity.class);
            setResult(RESULT_CANCELED,intent);
            finish();
        }

    }
    public static Boolean onlyDigits(String str, int n)
    {

        for (int i = 0; i < n; i++) {

            if (str.charAt(i) < '0'
                    || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

}