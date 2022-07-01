package com.triple.pointApi.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointCalculationTest {

    @Autowired
    PointCalculator pointCalculator;


    @Test
    @Transactional
    @Rollback(value = false)
    public void testPointTest() {

        List<String> photos = new ArrayList<>();
        photos.add("asdf");


        int addPoint = pointCalculator.createAddPoint("place_id", photos);
        System.out.println("===============pointCalculator.getPoint() : "+addPoint);



    }



}