package com.triple.pointApi.repository;

import com.triple.pointApi.domain.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class PointRepository {

    @PersistenceContext
    EntityManager em;

    //포인트 증가
    //포인트 감소



    // 포인트 객체 생성
    public String create(Point point) {
        System.out.println("=======PointRepository.create()=======");
        em.persist(point);
        return point.getUuid();
    }

    // 포인트 객체 찾기
    public Point find(Long id) {
        System.out.println("=======PointRepository.find()=======");
        return em.find(Point.class, id);
    }


}
