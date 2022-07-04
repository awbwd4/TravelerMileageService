package com.triple.triplePointApi.repository;

import com.triple.triplePointApi.domain.Point;
import com.triple.triplePointApi.exception.NoUserPointDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository{

    @PersistenceContext
    EntityManager em;


    /** Create **/
    // 포인트 생성
    public String create(Point point) {
        System.out.println("=======PointRepository.create()=======");
        em.persist(point);
        return point.getUuid();
    }


    /** Read **/
    // 포인트 검색
    public Point find(String uuid) {
        System.out.println("=======PointRepository.find()=======");
        return em.find(Point.class, uuid);
    }

    //포인트 조회_사용자
    public Point getPointByUserId(String userId) {
            return em.createQuery("select p from Point p" +
                            " where p.userId = :userId", Point.class)
                    .setParameter("userId", userId)
                    .getResultList().stream().findFirst().orElse(null);
    }


    //포인트 조회_전체
    public List<Point> getAllPoints() {
        return em.createQuery("select p from Point p", Point.class)
                .getResultList();
    }



    /** Update **/
    //포인트 수정
    public String modifyPoint(String userId, int point) {
        Point findPoint = getPointByUserId(userId);
        if (findPoint == null){
            throw new NoUserPointDataException("해당 사용자에 대한 포인트 정보가 없습니다.");
        }
        findPoint.modifyPoint(point);
        return findPoint.getUuid();
    }




}
