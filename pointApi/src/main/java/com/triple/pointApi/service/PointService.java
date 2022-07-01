package com.triple.pointApi.service;

import com.triple.pointApi.domain.Point;
import com.triple.pointApi.domain.PointDiscriminator;
import com.triple.pointApi.domain.PointHistory;
import com.triple.pointApi.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    //포인트 생성(사용자 최초 생성시에만)
    @Transactional
    public String createUser(String userId) {

        //중복회원이 있을 경우 에러 발생

        //
        //포인트 히스토리 생성
        PointHistory pointHistory = PointHistory.createPointHistory
                (0, PointDiscriminator.NEW_USER, userId, null, false, false);


        Point point = Point.createPoint(userId, pointHistory);

        return pointRepository.create(point);
    }

    // 리뷰 등록시(add)
    @Transactional
    public String addReviewPoint(String userId, int addPoint) {
        System.out.println("=======pointService.addReviewPoint==========");
        String addPointId =  pointRepository.increasePoint(userId, addPoint);

        return addPointId;
    }




    //포인트 증가

    //포인트 감소

    //포인트 조회_사용자

    //포인트 조회_전체







}
