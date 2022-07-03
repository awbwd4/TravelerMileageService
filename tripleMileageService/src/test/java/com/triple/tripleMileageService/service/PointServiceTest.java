package com.triple.tripleMileageService.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PointServiceTest {

    @Autowired
    PointService pointService;
    @Autowired
    EntityManager em;


    @Test
    @Transactional
    @Rollback(value = false)
    public void testCreateUserPointTest() {

        pointService.createUser("userId");

    }





//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void testIncreasePointTest() {
//
////        pointService.create("user_id", "place_id");
//        //given
//        Point point = Point.createPoint("user_Id1", "placeId1");
//        em.persist(point);
//
//        //when
//        pointService.increse("user_Id1", 5);
//
//        //then
//
//
//    }

}