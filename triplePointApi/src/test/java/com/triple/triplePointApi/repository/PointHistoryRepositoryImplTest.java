package com.triple.triplePointApi.repository;

import com.triple.triplePointApi.domain.PointHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class PointHistoryRepositoryImplTest {

    @Autowired
    PointRepositoryImpl pointRepositoryImpl;
    @Autowired
    EntityManager em;
    @Autowired
    PointHistoryRepositoryImpl pointHistoryRepository;


    @Test
    @Transactional
    @Rollback(value = false)
    public void 회원_장소로포인트히스토리찾기() {

        Optional<PointHistory> history = pointHistoryRepository.findMostRecentPointHistory("userId2", "placeId");

        System.out.println(history.get().getUserId());

    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰아이디로포인트히스토리찾기() {

        Optional<PointHistory> history = pointHistoryRepository.findMostRecentPointHistoryByReviewId("reviewId");

        System.out.println("*******************"+history);


//        System.out.println(history.isBonus());

    }

}