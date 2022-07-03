package com.triple.triplePointApi.repository;

import com.triple.triplePointApi.domain.PointHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepository {

    @PersistenceContext
    EntityManager em;


    /** Create **/
    // 포인트 생성
    public Long create(PointHistory pointHistory) {
        System.out.println("=======PointRepository.create()=======");
        em.persist(pointHistory);
        return pointHistory.getSeq();
    }



}
