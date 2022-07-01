package com.triple.pointApi.repository;

import com.triple.pointApi.domain.Point;
import com.triple.pointApi.exception.NotEnoughPointException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PointRepositoryTest {

    @Autowired
    PointRepository pointRepository;
    @Autowired
    EntityManager em;

    //Point 객체 생성 테스트
    @Test
    @Transactional
    @Rollback(value = false)
    public void testPointTest() {
        //given
        Point point1 = Point.createPoint("user_id1");
        Point point2 = Point.createPoint("user_id2");
        //when
        String createPointUuid1 = pointRepository.create(point1);
        String createPointUuid2 = pointRepository.create(point2);
        Point findPoint = pointRepository.find(createPointUuid1);


        //then
        // 포인트 엔티티 아이디 검증
        Assertions.assertThat(createPointUuid1).isEqualTo(point1.getUuid());
//         포인트 엔티티 포인트 검증
        Assertions.assertThat(findPoint.getPoint()).isEqualTo(point1.getPoint());


    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testFindAllPoint() {
        //given
        Point point1 = Point.createPoint("user_Id1");
        Point point2 = Point.createPoint("user_Id2");
        Point point3 = Point.createPoint("user_Id3");
        em.persist(point1);
        em.persist(point2);
        em.persist(point3);

        //when
        List<Point> allPoint = pointRepository.getAllPoint();
        //then
        for (Point point : allPoint) {
            System.out.println("point.getUserId() = " + point.getUserId());
        }

        Assertions.assertThat(allPoint.size()).isEqualTo(3);

    }



    @Test
    @Transactional
    @Rollback(value = false)
    public void testIncreaseTest() {
        //given
        Point point = Point.createPoint("user_Id1");
        em.persist(point);

        //when
        pointRepository.increasePoint(point.getUserId(), 2);
        pointRepository.getPointByUserId("user_id1").getPoint();


        //then
        Assertions.assertThat(pointRepository.getPointByUserId("user_id1").getPoint()).isEqualTo(2);


    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void testDecreaseTest() {
        //given
        Point point = Point.createPoint("user_Id1");
        em.persist(point);
        pointRepository.increasePoint(point.getUserId(),5);

        //when
        pointRepository.decreasePoint(point.getUserId(), 2);


        //then


    }

    @Test(expected = NotEnoughPointException.class)
    @Transactional
    public void testNoMorePoint() throws Exception{
        //given
        Point point = Point.createPoint("user_Id1");
        em.persist(point);
        pointRepository.increasePoint(point.getUserId(),5);

        System.out.println("********잔여 포인트 : " + pointRepository.getPointByUserId("user_Id1").getPoint());
        //when
        pointRepository.decreasePoint(point.getUserId(), 9);

        //then
        fail("포인트 부족 예외가 발생해야 함.");


    }


}