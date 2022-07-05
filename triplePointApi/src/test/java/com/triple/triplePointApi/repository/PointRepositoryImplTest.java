package com.triple.triplePointApi.repository;

import com.triple.triplePointApi.domain.Point;
import com.triple.triplePointApi.exception.NoUserPointDataException;
import com.triple.triplePointApi.exception.NotEnoughPointException;
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

import static org.junit.Assert.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PointRepositoryImplTest {

    @Autowired
    PointRepositoryImpl pointRepositoryImpl;
    @Autowired
    EntityManager em;


    //Point 객체 생성 테스트
    @Test
    @Transactional
    @Rollback(value = false)
    public void 포인트데이터생성테스트() {
        //given
        String userId = "2e8430c7-bfe4-46c4-a2eb-a38841e9385c";
        Point point1 = Point.createPoint(userId);

        //when
        String createPointUuid1 = pointRepositoryImpl.create(point1);

        //then
        // 포인트 엔티티 아이디 검증
        Assertions.assertThat(createPointUuid1).isEqualTo(point1.getUuid());
        //포인트 엔티티 포인트 검증
        Assertions.assertThat(pointRepositoryImpl.find(createPointUuid1).getPoint()).isEqualTo(point1.getPoint());


    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 전체포인트조회() {

        //given

        //when
        List<Point> allPoint = pointRepositoryImpl.getAllPoints();
        //then
        for (Point point : allPoint) {
            System.out.println("point.getUserId() = " + point.getUserId());
        }

        Assertions.assertThat(allPoint.size()).isEqualTo(3);

    }



    @Test
    @Transactional
    @Rollback(value = false)
    public void testIncreaseTest() throws NotEnoughPointException, NoUserPointDataException {
        //given
        String userId = "2e8430c7-bfe4-46c4-a2eb-a38841e9385c";
        Point point1 = Point.createPoint(userId);


        em.persist(point1);

        //when
        pointRepositoryImpl.modifyPoint(point1.getUserId(), 2);
        pointRepositoryImpl.getPointByUserId("user_id1").getPoint();


        //then
        Assertions.assertThat(pointRepositoryImpl.getPointByUserId("user_id1").getPoint()).isEqualTo(2);


    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void testDecreaseTest() throws NotEnoughPointException, NoUserPointDataException {
        String userId = "2e8430c7-bfe4-46c4-a2eb-a38841e9385c";
        //given
        Point point = Point.createPoint(userId);
        em.persist(point);
        pointRepositoryImpl.modifyPoint(point.getUserId(),5);

        //when
        pointRepositoryImpl.modifyPoint(point.getUserId(), 2);


        //then


    }

    @Test(expected = NotEnoughPointException.class)
    @Transactional
    public void testNoMorePoint() throws Exception{
        //given
        String userId = "2e8430c7-bfe4-46c4-a2eb-a38841e9385c";
        Point point = Point.createPoint(userId);
        em.persist(point);

        //when
        pointRepositoryImpl.modifyPoint(userId,-5);

        System.out.println("********잔여 포인트 : " + pointRepositoryImpl.getPointByUserId("user_Id1").getPoint());

        //then
        fail("포인트 부족 예외가 발생해야 함.");


    }

    @Test(expected = NoUserPointDataException.class)
    @Transactional
    @Rollback(value = false)
    public void 포인트수정시해당사용자가없는경우() throws NotEnoughPointException, NoUserPointDataException {
        //given

        //when
        pointRepositoryImpl.modifyPoint("modifyingUserId", 9);
        //then
        fail("사용자 없음 예외가 발생해야함.");


    }




    @Test
    @Transactional
    @Rollback(value = false)
    public void 유저찾기테스트() {

        pointRepositoryImpl.getPointByUserId("userId4");

    }

}