package com.triple.triplePointApi.service;

import org.assertj.core.api.Assertions;
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
    PointService pointService;


    @Test
    @Transactional
    @Rollback(value = false)
    public void 테스트용유저생성() {

        pointService.createUser("user_id");

    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void testPointTest() {

        List<String> photos = new ArrayList<>();
//        photos.add("asdf");

        int addPoint = pointCalculator.createAddPoint("place_id", photos);
        System.out.println("===============pointCalculator.getPoint() : "+addPoint);



    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 수정포인트산정테스() {

        List<String> photos = new ArrayList<>();
        photos.add("asdf");

        int modifiedPoint = pointCalculator.modifiedPoint("userId", "placeId", photos);

        //기존리뷰에 사진이 있는경우 + 수정후에도 사진이 있는 경우 : 수정 포인트 0
//        Assertions.assertThat(modifiedPoint).isEqualTo(0);

        //기존리뷰에 사진이 있는경우 + 수정후에 사진이 없는 경우 : 수정 포인트 -1
//        Assertions.assertThat(modifiedPoint).isEqualTo(-1);

//        기존리뷰에 사진이 없는경우 + 수정후에 사진이 있는 경우 : 수정 포인트 +1
        Assertions.assertThat(modifiedPoint).isEqualTo(1);


    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 삭제포인트산정테스트() {
        //given

        //when
        int deletePoint = pointCalculator.deletePoint("userId", "placeId");

        //then
        System.out.println("%%%%%%%%%%%%%%%%%%deletePoint%%%%%%%%%%%%%%%%% = " + deletePoint);
        Assertions.assertThat(deletePoint).isEqualTo(2);

    }



}