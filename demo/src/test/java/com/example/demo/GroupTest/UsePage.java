package com.example.demo.GroupTest;

import java.time.LocalDate;
import java.time.Month;

public class UsePage {

    public static void main(String args[]){
        long currentLongYear = LocalDate.now().getYear();

        System.out.println(Long.valueOf(currentLongYear));
    }
}
