package com.triple.pointApi.repository;

import com.triple.pointApi.domain.Point;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PointRepositoryTest {

    @Autowired
    PointRepository pointRepository;


    //Point 객체 생성 테스트
    @Test
    @Transactional
    @Rollback(value = false)
    public void testPointTest() {
        //given
        Point point = Point.createPoint("memberId", "placeId");
        //when
        String createPointUuid = pointRepository.create(point);
//        Point findPoint = pointRepository.find(savePoint);


        //then
        // 포인트 엔티티 아이디 검증
//        Assertions.assertThat(findPoint.getId()).isEqualTo(point.getId());
        // 포인트 엔티티 포인트 검증
//        Assertions.assertThat(findPoint.getPoint()).isEqualTo(point.getPoint());


    }


}