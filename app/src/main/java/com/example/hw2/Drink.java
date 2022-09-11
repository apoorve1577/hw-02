package com.example.hw2;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Drink implements Serializable {

    int size;
    int percent;
    Date createDatetime;

    public Drink(int size, int percent, Date createDatetime) {
        this.size = size;
        this.percent = percent;
        this.createDatetime = createDatetime;
    }

    public Drink(){

    }

}
