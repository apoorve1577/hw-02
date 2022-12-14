package com.example.hw2;

import static com.example.hw2.AddDrink.DRINK_KEY;
import static com.example.hw2.ViewDrinksActivity.ALL_DRINKS;
import static com.example.hw2.WeightSet.USER_KEY;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

   public User user ;
   public Drink drink ;
   public ArrayList<Drink> allDrinks = new ArrayList<>();
   public String TAG = "demo";
   public static double genderValue = 0.0 ;
   public static int weightPerson = 0;

   public static int REQ_CODE_WEIGHT = 100;
   public static int REQ_CODE_DRINK = 101;
   public static int REQ_CODE_VIEW = 102;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.set).setOnClickListener(this);
        findViewById(R.id.addDrink).setOnClickListener(this);
        findViewById(R.id.viewDrinks).setOnClickListener(this);
        findViewById(R.id.reset).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Log.d(TAG, "onActivityResult: " + USER_KEY + " req code " + requestCode);
        if(requestCode == REQ_CODE_WEIGHT){
            Log.d(TAG, "onActivityResult: " + USER_KEY );
            if(resultCode == RESULT_OK){
                if(data!=null && data.getExtras() !=null && data.hasExtra(USER_KEY)){
                    Button add = findViewById(R.id.addDrink);
                    Button view = findViewById(R.id.viewDrinks);
                    view.setEnabled(true);
                    add.setEnabled(true);
                    TextView drinkNumber, bacValue ;
                    user = (User)data.getSerializableExtra(USER_KEY);
                    if(user.gender.equals("female")){
                        genderValue = 0.66;
                    }
                    else{
                        genderValue = 0.73;
                    }
                    weightPerson = user.weight;
                    TextView weight = findViewById(R.id.weight);
                    weight.setText(user.weight + "(" + user.gender + ")");
                    drinkNumber = findViewById(R.id.drinkNumber);
                    bacValue = findViewById(R.id.bacValue);
                    bacValue.setText(R.string.def_bac_value);
                    drinkNumber.setText(R.string.def_drink_number);
                    TextView status = findViewById(R.id.status);
                    status.setText("You are safe");
                    status.setBackgroundColor(Color.rgb(0,255,0));
                }
            }
            super.onActivityResult(requestCode, resultCode, data);

        }
        if(requestCode == REQ_CODE_DRINK){
            if(resultCode == RESULT_OK) {
                if (data != null && data.getExtras() != null && data.hasExtra(DRINK_KEY)) {
                    drink = (Drink) data.getSerializableExtra(DRINK_KEY);
                    allDrinks.add(drink);
                    TextView updateDrink = findViewById(R.id.drinkNumber);
                    updateDrink.setText(allDrinks.size() + "");
                    updateBAC();
                }
            }
        }

        if(requestCode == REQ_CODE_VIEW){
            Log.d(TAG, "onActivityResult:  Is it working ???" + resultCode);
            if(resultCode == RESULT_OK){
                Log.d(TAG, "onActivityResult:  Is it working ??");
                if (data.getSerializableExtra("DrinkListBack")!=null){
                    allDrinks = (ArrayList<Drink>)data.getSerializableExtra("DrinkListBack");
                    setView();
                    Log.d(TAG, "onActivityResult: hello " + allDrinks);
                }
            }
            else{
                allDrinks.clear();

            }
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.set){
            Intent intent = new Intent(MainActivity.this, WeightSet.class);
            startActivityForResult(intent, REQ_CODE_WEIGHT);
        }

        if(view.getId() == R.id.addDrink){
            Intent intent = new Intent(MainActivity.this, AddDrink.class);
            startActivityForResult(intent, REQ_CODE_DRINK);
        }

        if(view.getId() == R.id.viewDrinks){
            if(allDrinks.size() == 0) {
                Toast.makeText(this, "User has no drinks", Toast.LENGTH_SHORT).show();
            }

            if(allDrinks.size() !=0) {
                Intent intent = new Intent(MainActivity.this, ViewDrinksActivity.class);
                intent.putExtra("DrinkList",allDrinks);
                startActivityForResult(intent,REQ_CODE_VIEW);
            }

        }

        if(view.getId() == R.id.reset){
            allDrinks.clear();
            TextView resetWeight = findViewById(R.id.weight);
            TextView resetDrink = findViewById(R.id.drinkNumber);
            TextView resetBAC = findViewById(R.id.bacValue);
            TextView resetStatus = findViewById(R.id.status);
            resetDrink.setText("0");
            resetBAC.setText("0.000");
            resetStatus.setText(R.string.def_status);
            resetStatus.setBackgroundColor(Color.rgb(0,255,0));
            resetWeight.setText(R.string.def_weight);
            Button add = findViewById(R.id.addDrink);
            Button views = findViewById(R.id.viewDrinks);
            views.setEnabled(false);
            add.setEnabled(false);
        }


    }

    public void updateBAC(){

        Double a= 0.0;
        int dp=0;
        for(Drink drink : allDrinks){
           dp+= drink.percent*drink.size ;
        }
        a = Double.valueOf(dp/100);
        Log.d(TAG, weightPerson + " gender " + genderValue + " dp " + dp + "A " + a);
        Double bac = (dp*5.14) / (weightPerson*genderValue*100);
        Log.d(TAG,  " bac " + bac);
        bac = Math.round(bac * 1000.0)/1000.0;
        String bacValue = String.valueOf(bac);
        TextView bacText = findViewById(R.id.bacValue);
        bacText.setText(bacValue);
        statusColor(bac);

    }


    public void setView(){
        updateBAC();
        TextView updateDrinks = findViewById(R.id.drinkNumber);
        updateDrinks.setText(allDrinks.size() +"");
    }

    public void statusColor(Double bac){
        TextView status = findViewById(R.id.status);
        Button addDrink = findViewById(R.id.addDrink);
        if(bac <= 0.08){
            status.setText("You are safe");
            status.setBackgroundColor(Color.rgb(0,255,0));
            addDrink.setEnabled(true);
        }
        if(bac > 0.08 && bac<=0.2){
            status.setText("Be careful!");
            status.setBackgroundColor(Color.rgb(204,63,18));
            addDrink.setEnabled(true);
        }
        if(bac > 0.2){
            status.setText("Over the limit");
            status.setBackgroundColor(Color.rgb(255,0,0));
            addDrink.setEnabled(false);
            Toast.makeText(this, "No More Drinks for you", Toast.LENGTH_SHORT).show();
        }




    }
}