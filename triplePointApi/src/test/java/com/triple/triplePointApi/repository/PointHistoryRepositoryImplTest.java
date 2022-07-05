package com.triple.triplePointApi.repository;

import com.triple.triplePointApi.domain.Point;
import com.triple.triplePointApi.domain.PointHistory;
import com.triple.triplePointApi.exception.DuplicateDataException;
import com.triple.triplePointApi.exception.NoSuitableReviewException;
import com.triple.triplePointApi.exception.NoUserPointDataException;
import com.triple.triplePointApi.exception.NotEnoughPointException;
import com.triple.triplePointApi.service.PointService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PointHistoryRepositoryImplTest {

    @Autowired
    PointRepositoryImpl pointRepositoryImpl;
    @Autowired
    EntityManager em;
    @Autowired
    PointHistoryRepositoryImpl pointHistoryRepository;
    @Autowired
    PointService pointService;


    @Test
    @Transactional
    @Rollback(value = false)
    public void 회원_장소로포인트히스토리찾기() throws DuplicateDataException, NoSuitableReviewException, NotEnoughPointException, NoUserPointDataException {

        /**given**/
        //새 사용자 생성
        pointService.createNewUserPoint("userId");
        pointService.addReviewPoint("userId", "placeId", "reviewId", new ArrayList<>());

        /**when**/

        Optional<PointHistory> history = pointHistoryRepository.findMostRecentPointHistory("userId", "placeId");

        /**then**/

        Assertions.assertThat(history.get().getUserId()).isEqualTo("userId");
        Assertions.assertThat(history.get().getPlaceId()).isEqualTo("placeId");

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰아이디로포인트히스토리찾기() throws DuplicateDataException, NoSuitableReviewException, NotEnoughPointException, NoUserPointDataException {

        /**given**/
        //새 사용자 생성
        pointService.createNewUserPoint("userId");
        pointService.addReviewPoint("userId", "placeId", "reviewId", new ArrayList<>());

        /**when**/
        Optional<PointHistory> history = pointHistoryRepository.findMostRecentPointHistoryByReviewId("reviewId");

        /**then**/
        Assertions.assertThat(history.get().getUserId()).isEqualTo("userId");
        Assertions.assertThat(history.get().getPlaceId()).isEqualTo("placeId");

    }

}