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

import static org.aspectj.bridge.MessageUtil.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OuterServiceTest {

    @Autowired
    OuterService outerService;


    @Test
    @Transactional
    @Rollback(value = false)
    public void 신규회원생성시NEW포인트생성테스트() {
        outerService.createNewUserPoint("userId");
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰등록ADD시테스트() {
        //새 사용자 생성
        outerService.createNewUserPoint("userId");

        List<String> photos = new ArrayList<>();
//        photos.add("asdfa");

        outerService.addReviewPoint("userId","placeId", photos);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰수정MOD시테스트() {
        //given
//        //새 사용자 생성
//        outterService.createNewUserPoint("userId");
//
        List<String> photos = new ArrayList<>();
//        photos.add("asdfa");
//        //첫리뷰 포인트 생성
//        outterService.addReviewPoint("userId","placeId", photos);

        //when
        outerService.modReviewPoint("userId", "placeId", photos);


        //then
        // 점수 변동이 없는경우

        // 점수가 1 증가하는 경우

        // 점수가 1 감소하는 경우
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰삭제DEL시테스트() {
        //given

        //when
        outerService.deleteReview("userId", "placeId");

        //then

        //정상적으로 삭제될때

        //이상이 있을때


    }



}