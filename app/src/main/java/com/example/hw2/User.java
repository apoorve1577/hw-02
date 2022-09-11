package com.example.hw2;

import java.io.Serializable;

public class User implements Serializable {

    public String gender ;
    public Integer weight;

    public User(String gender, Integer weight) {
        this.gender = gender;
        this.weight = weight;
    }

    public User(){

    }

}
