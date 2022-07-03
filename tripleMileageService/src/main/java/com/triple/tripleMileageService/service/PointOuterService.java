package com.triple.tripleMileageService.service;

import com.triple.tripleMileageService.domain.Point;
import com.triple.tripleMileageService.domain.PointDiscriminator;
import com.triple.tripleMileageService.domain.PointHistory;
import com.triple.tripleMileageService.exception.AbnormalPointException;
import com.triple.tripleMileageService.repository.PointHistoryRepository;
import com.triple.tripleMileageService.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PointOuterService {

    @PersistenceContext
    EntityManager em;
    private final PointCalculator calculator;
    private final PointService pointService;
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;



    /**
     * 사용자 신규 생성시
     **/
    public String createNewUserPoint(String userId) {
        return pointService.createUser(userId);
    }


    /**
     * 리뷰 최초 등록시
     **/
    public void addReviewPoint(String userId, String placeId, List<String> photos) {

        int addPoint = calculator.createAddPoint(placeId, photos);
        boolean photo = calculator.existPhoto(photos);
        boolean bonus = calculator.bonus(placeId);
        //포인트 서비스 호출
        Point point = getPoint(userId, addPoint);

        //포인트 히스토리 리포지토리 호출
        PointHistory pointHistory = PointHistory.createPointHistory(
                point, addPoint, PointDiscriminator.ADD, userId, placeId
                , photo, bonus);
        pointHistoryRepository.create(pointHistory);

    }



    /**리뷰 수정시**/
    public void modReviewPoint(String userId, String placeId, List<String> photos) {

        int modifiedPoint = calculator.modifiedPoint(userId, placeId, photos);
        boolean photo = calculator.existPhoto(photos);
        boolean bonus = calculator.bonus(placeId);

        //포인트 서비스 호출
        Point point = getPoint(userId, modifiedPoint);

        //포인트 히스토리 리포지토리 호출
        PointHistory pointHistory = PointHistory.createPointHistory(
                point, modifiedPoint, PointDiscriminator.MOD, userId, placeId
                , photo, bonus);
        pointHistoryRepository.create(pointHistory);

    }


    /**
     * 리뷰 삭제시
     **/
    // 해당 리뷰로 부여한 점수 삭제
    // 포인트 히스토리에서 해당 리뷰 총합계 구한뒤 이를 사용자 포인트에서 차감
    public void deleteReview(String userId, String placeId) {
        int deletePoint = calculator.deletePoint(userId, placeId);

        if (deletePoint >= 0) {
            deletePoint *= -1; // 삭제할 포인트이므로 음수로 변경

            Point point = getPoint(userId, deletePoint);

            PointHistory pointHistory = PointHistory.createPointHistory(
                    point, deletePoint, PointDiscriminator.DELETE, userId, placeId
                    , false, false);
            pointHistoryRepository.create(pointHistory);

        }else{
            throw new AbnormalPointException("포인트 점수에 이상이 있습니다.");
        }
    }

    /**
     * 포인트 조회시
     **/
    public int getAllPoint() {
        int allPoint = 0;
        List<Point> pointList = pointRepository.getAllPoint();

        for (Point point : pointList) {
            allPoint += point.getPoint();
        }
        return allPoint;
    }

    public int getPointByUserId(String userId) {
        return pointRepository.getPointByUserId(userId).getPoint();
    }




    private Point getPoint(String userId, int deletePoint) {
        String modifiedPointId = pointRepository.modifiedPoint(userId, deletePoint);
        Point point = em.find(Point.class, modifiedPointId);
        return point;
    }




}
