package com.tom.forecast.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenerateDateTest {
    GenerateDate generateDate=new GenerateDate();

    @Test
    public void getDate(){
        System.out.println("Today, "+ GenerateDate.getDate());
    }

    @Test
    public void getTime(){
        System.out.println(GenerateDate.getTime());
    }

}