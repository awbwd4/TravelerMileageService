package com.triple.pointApi.service;

import com.triple.pointApi.domain.Point;
import com.triple.pointApi.domain.PointDiscriminator;
import com.triple.pointApi.domain.PointHistory;
import com.triple.pointApi.repository.PointHistoryRepository;
import com.triple.pointApi.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OutterService {

    @PersistenceContext
    EntityManager em;
    private final PointCalculator calculator;
    private final PointService pointService;
    private final PointHistoryRepository pointHistoryRepository;



    /**
     * 사용자 신규 생성시
     **/
    //아무것도 안함.
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
        String addPointId = pointService.addReviewPoint(userId, addPoint);
        Point point = em.find(Point.class, addPointId);

        //포인트 히스토리 리포지토리 호출
        PointHistory pointHistory = PointHistory.createPointHistory(
                point, addPoint, PointDiscriminator.ADD, userId, placeId
                , photo, bonus);
        pointHistoryRepository.create(pointHistory);

    }



    /**리뷰 수정시**/
    public void modReviewPoint(String userId, String placeId, List<String> photo) {

    }



    /**리뷰 삭제시**/


    /**포인트 조회시**/

}
