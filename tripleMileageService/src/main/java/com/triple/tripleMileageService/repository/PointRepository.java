package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointRepository {

    @PersistenceContext
    private EntityManager em;


    /**
     * ==포인트 조회
     **/
    //포인트 전체 조회
    public List<Point> findAllPoint() {
        return em.createQuery("select p from Point p", Point.class).getResultList();
    }

    //pointId로 포인트 조회
    public Point findPointById(Long id) {
        System.out.println("=======ReviewRepository.findPointById()=======");
        return em.find(Point.class, id);
    }


}
