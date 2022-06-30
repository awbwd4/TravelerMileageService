package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.Point;
import com.triple.tripleMileageService.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointRepositoryTest {

    @Autowired
    PointRepository pointRepository;
//
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void pointFindTest() {
//        //given
//        Long pointId = 1L;
//
//        //when
//        Point findPoint = pointRepository.findPointById(pointId);
//
//        //then
//        Assertions.assertThat(findPoint.getPoint()).isEqualTo(1);
//
//    }
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void pointFindAllTest() {
//        //given
//
//        //when
//        List<Point> allPoint = pointRepository.findAllPoint();
//
//        //then
//
//        for (Point point : allPoint) {
//            Assertions.assertThat(point.getPoint()).isEqualTo(1);
//        }
//
//    }
//
//
}