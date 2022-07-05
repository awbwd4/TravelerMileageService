package com.triple.triplePointApi.service;

import com.triple.triplePointApi.domain.Point;
import com.triple.triplePointApi.domain.PointHistory;
import com.triple.triplePointApi.exception.*;
import com.triple.triplePointApi.repository.PointHistoryRepositoryImpl;
import com.triple.triplePointApi.repository.PointRepositoryImpl;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointServiceImplTest {

    @Autowired
    PointServiceImpl pointServiceImpl;
    @Autowired
    PointRepositoryImpl pointRepository;
    @Autowired
    PointHistoryRepositoryImpl pointHistoryRepository;



    @Test
    @Transactional
    public void 회원별포인트조회() throws DuplicateDataException, NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException {
        /**given**/
        //사용자 신규 생성
        pointServiceImpl.createNewUserPoint("userId");
        //리뷰 추가(사진 없음)
        pointServiceImpl.addReviewPoint("userId", "placeId", "reviewId", new ArrayList<>());

        /**when**/
        int userPoint = pointServiceImpl.getPointByUserId("userId");

        /**then**/
        assertThat(userPoint).isEqualTo(2);
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 신규회원생성시NEW포인트생성테스트() throws DuplicateDataException {

        /**given**/

        String pointId = pointServiceImpl.createNewUserPoint("userId1");

        /**when**/
        Point point = pointRepository.find(pointId);

        /**then**/
        assertThat(point.getUserId()).isEqualTo("userId1");

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰등록ADD시테스트() throws DuplicateDataException, NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException {
        /**given**/
        //새 사용자 생성

        pointServiceImpl.createNewUserPoint("userId");

        /**when**/

        String pointId = pointServiceImpl.addReviewPoint("userId", "placeId", "reviewId", new ArrayList<>());
        Point point = pointRepository.find(pointId);

        Optional<PointHistory> history = pointHistoryRepository.findMostRecentPointHistory("userId", "placeId");

        /**then**/
        assertThat(point.getUserId()).isEqualTo("userId");
        assertThat(point.getPoint()).isEqualTo(2);//첫 리뷰 보너스 +1

        assertThat(history.get().getDiscriminator()).isEqualTo("ADD");
        assertThat(history.get().getReviewId()).isEqualTo("reviewId");

    }
    @Test(expected = DuplicateDataException.class)
    @Transactional
    @Rollback(value = false)
    public void 리뷰중복등록ADD시테스트() throws DuplicateDataException, NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException {
        /**given**/
        //새 사용자 생성
        pointServiceImpl.createNewUserPoint("userId");
        //리뷰 등록
        String pointId = pointServiceImpl.addReviewPoint("userId", "placeId", "reviewId", new ArrayList<>());

        /**when**/
        //같은 사용자가 같은 장소에 대해 다시 리뷰 등록
        pointServiceImpl.addReviewPoint("userId", "placeId", "reviewId", new ArrayList<>());

        /**then**/
        fail("중복 리뷰 예외가 발생해야함");

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰수정MOD시테스트_사진추가() throws NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException, DuplicateDataException {
        /**given**/

        //새 사용자 생성
        pointServiceImpl.createNewUserPoint("userId");
        //리뷰 등록
        String pointId = pointServiceImpl.addReviewPoint("userId", "placeId", "reviewId", new ArrayList<>());

        /**when**/
        //사진 추가
        List<String> photos = new ArrayList<>();
        photos.add("photo");
        pointServiceImpl.modReviewPoint("userId", "placeId", "reviewId", photos);


        Point point = pointRepository.find(pointId);

        Optional<PointHistory> history = pointHistoryRepository.findMostRecentPointHistory("userId", "placeId");

        /**then**/
        assertThat(point.getUserId()).isEqualTo("userId");
        assertThat(point.getPoint()).isEqualTo(3);//첫리뷰보너스 +1 사진포인트 +1

        assertThat(history.get().getDiscriminator()).isEqualTo("MOD");
        assertThat(history.get().getChangedPoint()).isEqualTo(1);//사진포인트 +1
        assertThat(history.get().isPhoto()).isEqualTo(true);
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰수정MOD시테스트_사진삭제() throws NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException, DuplicateDataException {
        /**given**/

        //새 사용자 생성
        pointServiceImpl.createNewUserPoint("userId");
        //리뷰 등록
        List<String> photos = new ArrayList<>();
        photos.add("photo");
        String pointId = pointServiceImpl.addReviewPoint("userId", "placeId", "reviewId", photos);

        /**when**/
        //사진 삭제
        pointServiceImpl.modReviewPoint("userId", "placeId", "reviewId", new ArrayList<>());


        Point point = pointRepository.find(pointId);

        Optional<PointHistory> history = pointHistoryRepository.findMostRecentPointHistory("userId", "placeId");

        /**then**/
        assertThat(point.getUserId()).isEqualTo("userId");
        assertThat(point.getPoint()).isEqualTo(2);//첫리뷰보너스 +1

        assertThat(history.get().getDiscriminator()).isEqualTo("MOD");
        assertThat(history.get().getChangedPoint()).isEqualTo(-1); //사진포인트 -1
        assertThat(history.get().isPhoto()).isEqualTo(false);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰삭제DEL시테스트() throws AbnormalPointException, NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException, DuplicateDataException {
        /**given**/
        //새 사용자 생성
        pointServiceImpl.createNewUserPoint("userId");
        //리뷰 등록
        List<String> photos = new ArrayList<>();
        photos.add("photo");
        String pointId = pointServiceImpl.addReviewPoint("userId", "placeId", "reviewId", photos);

        /**when**/
        //리뷰 삭제
        boolean isDeleted = pointServiceImpl.deleteReview("userId", "placeId", "reviewId");

        Point point = pointRepository.find(pointId);

        Optional<PointHistory> history = pointHistoryRepository.findMostRecentPointHistory("userId", "placeId");

        /**then**/
        assertThat(isDeleted).isEqualTo(true); // 삭제 성공시 true 리턴

        assertThat(point.getUserId()).isEqualTo("userId");
        assertThat(point.getPoint()).isEqualTo(0);

        assertThat(history.get().getDiscriminator()).isEqualTo("DELETE");
        assertThat(history.get().getChangedPoint()).isEqualTo(-3); //리뷰+사진+보너스
    }



    @Test
    @Transactional
    @Rollback(value = false)
    public void 보너스제외테스트1() throws AbnormalPointException, NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException, DuplicateDataException {

        /**
         * 기존에 등록된 리뷰가 있을 경우, 다음 리뷰는 보너스 점수가 없다.
         * */

        /**given**/
        //새 사용자 생성
        pointServiceImpl.createNewUserPoint("userId1");
        pointServiceImpl.createNewUserPoint("userId2");
        //리뷰 등록 : 첫등록리뷰
        String pointId1 = pointServiceImpl.addReviewPoint("userId1", "placeId", "reviewId1", new ArrayList<>());

        /**when**/
        //리뷰 등록 : 첫등록 리뷰가 아닌 경우
        String pointId2 = pointServiceImpl.addReviewPoint("userId2", "placeId", "reviewId2", new ArrayList<>());



        /**then**/
        //userId1의 포인트는 2점, userId의 포인트는 1점이어야함.

        Point point1 = pointRepository.find(pointId1);
        Point point2 = pointRepository.find(pointId2);
        assertThat(point1.getPoint()).isEqualTo(2);
        assertThat(point2.getPoint()).isEqualTo(1);


        //userId1의 이력에서 bonus는 true, userId2의 이력에서 bonus는 false
        Optional<PointHistory> history1 = pointHistoryRepository.findMostRecentPointHistory("userId1", "placeId");
        Optional<PointHistory> history2 = pointHistoryRepository.findMostRecentPointHistory("userId2", "placeId");
        assertThat(history1.get().isBonus()).isEqualTo(true);
        assertThat(history2.get().isBonus()).isEqualTo(false);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 보너스제외테스트2() throws AbnormalPointException, NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException, DuplicateDataException {

        /**
         * 첫리뷰가 삭제되고
         * 그 이후에 다음 사용자가 리뷰를 등록한 경우
         * 이 다음사용자에게 보너스 포인트를 지급한다.
         * */

        /**given**/
        //새 사용자 생성
        pointServiceImpl.createNewUserPoint("userId1");
        pointServiceImpl.createNewUserPoint("userId2");
        //리뷰 등록 : 첫등록리뷰
        String pointId1 = pointServiceImpl.addReviewPoint("userId1", "placeId", "reviewId1", new ArrayList<>());

        /**when**/
        //첫 등록됐던 리뷰 삭제
        pointServiceImpl.deleteReview("userId1", "placeId", "reviewId1");

        //다음 사용자의 리뷰 등록
        String pointId2 = pointServiceImpl.addReviewPoint("userId2", "placeId", "reviewId2", new ArrayList<>());

        /**then**/
        //userId1의 포인트는 0점, userId의 포인트는 2점이어야함.

        Point point1 = pointRepository.find(pointId1);
        Point point2 = pointRepository.find(pointId2);
        assertThat(point1.getPoint()).isEqualTo(0);
        assertThat(point2.getPoint()).isEqualTo(2);


        //userId1의 이력에서 가장 마지막 데이터의 구분값은 "DELETE"이다.
        //userId2의 이력에서 bonus는 true이다.
        Optional<PointHistory> history1 = pointHistoryRepository.findMostRecentPointHistory("userId1", "placeId");
        Optional<PointHistory> history2 = pointHistoryRepository.findMostRecentPointHistory("userId2", "placeId");
        assertThat(history1.get().getDiscriminator()).isEqualTo("DELETE");
        assertThat(history2.get().isBonus()).isEqualTo(true);
    }









}