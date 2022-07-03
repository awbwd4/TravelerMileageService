package com.triple.tripleMileageService.service;

import com.triple.tripleMileageService.domain.Point;
import com.triple.tripleMileageService.domain.PointDiscriminator;
import com.triple.tripleMileageService.domain.PointHistory;
import com.triple.tripleMileageService.domain.User;
import com.triple.tripleMileageService.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final UserService userService;

    //포인트 생성(사용자 최초 생성시에만)
    @Transactional
    public String createUser(String userId) {
        //중복회원이 있을 경우 에러 발생
//        validateDuplicateUser(userId);
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
        String addPointId =  pointRepository.modifiedPoint(userId, addPoint);

        return addPointId;
    }


    private void validateDuplicateUser(String userId) {
        Point pointByUserId = pointRepository.getPointByUserId(userId);
        if (pointByUserId.getUserId()!=null) {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        }
    }
}
