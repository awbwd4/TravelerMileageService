package com.triple.triplePointApi.service;

import com.triple.triplePointApi.exception.DuplicateDataException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointCalculationTest {

    @Autowired
    PointCalculator pointCalculator;
    @Autowired
    PointServiceImpl pointServiceImpl;
    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    public void 테스트용유저생성() throws DuplicateDataException {

        pointServiceImpl.createNewUserPoint("user_id");

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
        Assertions.assertThat(modifiedPoint).isEqualTo(0);

        //기존리뷰에 사진이 있는경우 + 수정후에 사진이 없는 경우 : 수정 포인트 -1
        Assertions.assertThat(modifiedPoint).isEqualTo(-1);

        //기존리뷰에 사진이 없는경우 + 수정후에 사진이 있는 경우 : 수정 포인트 +1
        Assertions.assertThat(modifiedPoint).isEqualTo(1);


    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 삭제포인트산정테스트() {
        //given

        //when
        int deletePoint = pointCalculator.deletePoint("userId3", "placeId");

        //then
        System.out.println("%%%%%%%%%%%%%%%%%%deletePoint%%%%%%%%%%%%%%%%% = " + deletePoint);
        Assertions.assertThat(deletePoint).isEqualTo(2);

    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰수구하기테스트() {

        BigInteger count = (BigInteger) em.createNativeQuery("select count(1)" +
                " from point_history" +
                " where (user_id, point_history_id)" +
                " in" +
                    " (select user_id, max(point_history_id)" +
                    " from point_history where place_id =\"placeId\"" +
                    " group by user_id)" +
                " and discriminator = \"DELETE\"").getSingleResult()
                ;

//                .setParameter("placeId", placeId)
        System.out.println("count = " + count.intValue());
        System.out.println("뭔타입?? = " + count.getClass());
    }
    @Test
    @Transactional
    @Rollback(value = false)
    public void 댓글단사람수구하기() {

        BigInteger count = (BigInteger) em.createNativeQuery("select count(1)" +
                " from" +
                    " (select 1 from point_history" +
                    " where place_id = \"placeId\" group by user_id" +
                    ") ph")
                .getSingleResult()
                ;

//                .setParameter("placeId", placeId)
        System.out.println("count = " + count.intValue());
        System.out.println("뭔타입?? = " + count.getClass());
    }



    @Test
    @Transactional
    @Rollback(value = false)
    public void 보너스부여여부테스트() {

        String placeId = "placeId";

        boolean bonus = pointCalculator.bonus(placeId);

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&bonus = " + bonus);


    }






}


//select * from point_history where (user_id, point_history_id) in (select user_id, max(point_history_id) from point_history where place_id = "placeId" group by user_id) and discriminator = "DELETE"



//    select count(*) from (select 1 from point_history ph where place_id = "placeId" group by user_id)