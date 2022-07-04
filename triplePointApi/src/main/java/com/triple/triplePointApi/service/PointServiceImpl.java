package com.triple.triplePointApi.service;

import com.triple.triplePointApi.domain.Point;
import com.triple.triplePointApi.domain.PointHistory;
import com.triple.triplePointApi.exception.AbnormalPointException;
import com.triple.triplePointApi.exception.DuplicateDataException;
import com.triple.triplePointApi.repository.PointHistoryRepositoryImpl;
import com.triple.triplePointApi.repository.PointRepository;
import com.triple.triplePointApi.repository.PointRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PointServiceImpl implements PointService{

    @PersistenceContext
    EntityManager em;
    private final PointCalculator calculator;
    private final PointRepository pointRepository;
    private final PointHistoryRepositoryImpl pointHistoryRepositoryImpl;



    /**
     * 사용자 신규 생성시
     **/
    public String createNewUserPoint(String userId) {
        //중복회원이 있을 경우 에러 발생
        validateDuplicateUser(userId);
        //포인트 히스토리 생성
        PointHistory pointHistory = PointHistory.createPointHistory
                (0, "NEW_USER", userId, null, false, false);

        Point point = Point.createPoint(userId, pointHistory);

        return pointRepository.create(point);


    }


    /**
     * 리뷰 최초 등록시
     **/
    public String addReviewPoint(String userId, String placeId, List<String> photos) {
        int addPoint = calculator.createAddPoint(placeId, photos);
        boolean photo = calculator.existPhoto(photos);
        boolean bonus = calculator.bonus(placeId);
        //포인트 서비스 호출
        Point point = getPoint(userId, addPoint);

        //포인트 히스토리 리포지토리 호출
        PointHistory pointHistory = PointHistory.createPointHistory(
                point, addPoint, "ADD", userId, placeId
                , photo, bonus);

        return point.getUuid();
    }



    /**리뷰 수정시**/
    public String modReviewPoint(String userId, String placeId, List<String> photos) {

        int modifiedPoint = calculator.modifiedPoint(userId, placeId, photos);
        boolean photo = calculator.existPhoto(photos);
        boolean bonus = calculator.bonus(placeId);

        //포인트 서비스 호출
        Point point = getPoint(userId, modifiedPoint);

        //포인트 히스토리 리포지토리 호출
        PointHistory pointHistory = PointHistory.createPointHistory(
                point, modifiedPoint, "MOD", userId, placeId
                , photo, bonus);
        pointHistoryRepositoryImpl.create(pointHistory);

        return point.getUuid();

    }


    /**
     * 리뷰 삭제시
     **/
    // 해당 리뷰로 부여한 점수 삭제
    // 포인트 히스토리에서 해당 리뷰 총합계 구한뒤 이를 사용자 포인트에서 차감
    public boolean deleteReview(String userId, String placeId) {
        int deletePoint = calculator.deletePoint(userId, placeId);

        if (deletePoint >= 0) {
            deletePoint *= -1; // 삭제할 포인트이므로 음수로 변경

            Point point = getPoint(userId, deletePoint);

            PointHistory pointHistory = PointHistory.createPointHistory(
                    point, deletePoint, "DELETE", userId, placeId
                    , false, false);
            pointHistoryRepositoryImpl.create(pointHistory);

            return true;

        }else{
            throw new AbnormalPointException("포인트 점수에 이상이 있습니다.");
        }
    }

    /**
     * 포인트 조회시
     **/
    @Transactional(readOnly = true)
    public int getAllPoint() {
        int allPoint = 0;
        List<Point> pointList = pointRepository.getAllPoints();

        for (Point point : pointList) {
            allPoint += point.getPoint();
        }
        return allPoint;
    }

    public int getPointByUserId(String userId) {
        return pointRepository.getPointByUserId(userId).getPoint();
    }




    private Point getPoint(String userId, int deletePoint) {
        String modifiedPointId = pointRepository.modifyPoint(userId, deletePoint);
        Point point = em.find(Point.class, modifiedPointId);
        return point;
    }

    //중복 회원 검사
    private void validateDuplicateUser(String userId) {
        Point pointByUserId = pointRepository.getPointByUserId(userId);

        if (pointByUserId != null) {
            throw new DuplicateDataException("이미 존재하는 회원입니다.");
        }
    }


}
