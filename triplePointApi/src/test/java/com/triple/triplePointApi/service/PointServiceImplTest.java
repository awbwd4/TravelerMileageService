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
public class PointServiceImplTest {

    @Autowired
    PointServiceImpl pointServiceImpl;

    @Test
    @Transactional
    @Rollback(value = false)
    public void 신규회원생성시NEW포인트생성테스트() {
        pointServiceImpl.createNewUserPoint("userId1");
        pointServiceImpl.createNewUserPoint("userId2");
        pointServiceImpl.createNewUserPoint("userId3");
        pointServiceImpl.createNewUserPoint("userId4");
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰등록ADD시테스트() {
        //새 사용자 생성
//        pointServiceImpl.createNewUserPoint("userId1");
//        pointServiceImpl.createNewUserPoint("userId2");
//        pointServiceImpl.createNewUserPoint("userId3");
//        pointServiceImpl.createNewUserPoint("userId4");
//        outerService.createNewUserPoint("userId3");

        List<String> photos = new ArrayList<>();
        photos.add("asdfa");

        pointServiceImpl.addReviewPoint("userId1", "placeId", photos);
//        pointServiceImpl.addReviewPoint("userId2", "placeId", photos);
//        pointServiceImpl.addReviewPoint("userId3", "placeId", photos);
//        pointServiceImpl.addReviewPoint("userId4", "placeId", photos);
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
        photos.add("asdfa");
//        //첫리뷰 포인트 생성
//        outterService.addReviewPoint("userId","placeId", photos);

        //when
        pointServiceImpl.modReviewPoint("userId1", "placeId", photos);


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
        pointServiceImpl.deleteReview("userId4", "placeId");

        //then

        //정상적으로 삭제될때

        //이상이 있을때
    }


    @Test
    @Transactional
    public void 회원별포인트조회() {
        //given

        //when
        int userId1 = pointServiceImpl.getPointByUserId("userId1");
        //then

        Assertions.assertThat(userId1).isEqualTo(3);



    }

}