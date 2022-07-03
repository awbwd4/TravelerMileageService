package com.triple.triplePointApi.repository;

import com.triple.triplePointApi.domain.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        /**
         *
         * 찾는게 없으면 에러를 안내고 그냥 빈값을 반환하도록 수정해야함!!
         *
         * **/


        return findPoint;
    }

    //포인트 조회_전체
    public List<Point> getAllPoint() {
        return em.createQuery("select p from Point p", Point.class)
                .getResultList();
    }

    /** Update **/
    //포인트 수정
    public String modifiedPoint(String userId, int point) {
        Point findPoint = getPointByUserId(userId);
        findPoint.modifiedPoint(point);
        return findPoint.getUuid();
    }




}
