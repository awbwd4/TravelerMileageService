package com.triple.pointApi.repository;

import com.triple.pointApi.domain.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointRepository {

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
        Point findPoint = em.createQuery("select p from Point p" +
                        " where p.userId = :userId", Point.class)
                .setParameter("userId", userId).getSingleResult();
        return findPoint;
    }

    //포인트 조회_전체
    public List<Point> getAllPoint() {
        return em.createQuery("select p from Point p", Point.class)
                .getResultList();
    }

    /** Update **/
    //포인트 증가
    public String increasePoint(String userId, int point) {
        Point findPoint = getPointByUserId(userId);
        findPoint.increasePoint(point);
        return findPoint.getUuid();
    }
    //포인트 감소
    public String decreasePoint(String userId, int point) {
        Point findPoint = getPointByUserId(userId);
        findPoint.decreasePoint(point);
        return findPoint.getUuid();
    }




}
