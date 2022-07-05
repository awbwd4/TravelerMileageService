package com.triple.triplePointApi.repository;

import com.triple.triplePointApi.domain.Point;
import com.triple.triplePointApi.exception.NoUserPointDataException;
import com.triple.triplePointApi.exception.NotEnoughPointException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        /**given**/
        String userId = "UserId";
        Point point1 = Point.createPoint(userId);

        /**when**/
        String createPointUuid1 = pointRepositoryImpl.create(point1);

        /**then**/
        // 포인트 엔티티 아이디 검증
        assertThat(createPointUuid1).isEqualTo(point1.getUuid());
        //포인트 엔티티 포인트 검증
        assertThat(pointRepositoryImpl.find(createPointUuid1).getPoint()).isEqualTo(point1.getPoint());


    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 포인트증가테스트() throws NotEnoughPointException, NoUserPointDataException {

        /**given**/
        String userId = "userId";
        Point point1 = Point.createPoint(userId);

        em.persist(point1);

        /**when**/
        pointRepositoryImpl.modifyPoint(point1.getUserId(), 2);
        pointRepositoryImpl.getPointByUserId("userId").getPoint();

        /**then**/
        assertThat(pointRepositoryImpl.getPointByUserId("userId").getPoint()).isEqualTo(2);

    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 포인트차감테스트() throws NotEnoughPointException, NoUserPointDataException {
        /**given**/

        String userId = "userId";
        Point point = Point.createPoint(userId);
        em.persist(point);

        /**when**/
        pointRepositoryImpl.modifyPoint(point.getUserId(),5);
        pointRepositoryImpl.modifyPoint(point.getUserId(), -2);

        /**then**/

        assertThat(pointRepositoryImpl.getPointByUserId(userId).getPoint()).isEqualTo(3);

    }

    @Test(expected = NotEnoughPointException.class)
    @Transactional
    public void 포인트차감에러테스트() throws Exception{

        /**given**/

        String userId = "userId";
        Point point = Point.createPoint(userId);
        em.persist(point);

        /**when**/
        pointRepositoryImpl.modifyPoint(point.getUserId(),2);
        pointRepositoryImpl.modifyPoint(point.getUserId(), -5);

        //then
        /**then**/
        fail("포인트 부족 예외가 발생해야 함.");

    }

    @Test(expected = NoUserPointDataException.class)
    @Transactional
    @Rollback(value = false)
    public void 포인트수정시해당사용자가없는경우() throws NotEnoughPointException, NoUserPointDataException {
        /**given**/

        /**when**/
        pointRepositoryImpl.modifyPoint("userId", 9);

        /**then**/
        fail("사용자 없음 예외가 발생해야함.");


    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 유저찾기테스트() {

        /**given**/
        String userId = "userId";
        Point point = Point.createPoint(userId);
        String createPointUuid1 = pointRepositoryImpl.create(point);

        /**when**/

        Point userPoint = pointRepositoryImpl.getPointByUserId("userId");

        /**then**/
        assertThat(userPoint.getUuid()).isEqualTo(point.getUuid());
        assertThat(userPoint.getUserId()).isEqualTo(point.getUserId());
        assertThat(userPoint.getPoint()).isEqualTo(0);



    }

}