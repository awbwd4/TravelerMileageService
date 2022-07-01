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
public class OutterServiceTest {

    @Autowired
    OutterService outterService;


    @Test
    @Transactional
    @Rollback(value = false)
    public void 신규회원생성시NEW포인트생성테스트() {
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰등록ADD시테스트() {
        outterService.createNewUserPoint("userId_신규회원생성시포인트생성테스트");

        List<String> photos = new ArrayList<>();
        photos.add("asdfa");

        outterService.addReviewPoint("userId_신규회원생성시포인트생성테스트","placeId", photos);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰수정MOD시테스트() {
        outterService.createNewUserPoint("userId_신규회원생성시포인트생성테스트");
    }

}