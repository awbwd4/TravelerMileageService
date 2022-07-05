package com.triple.triplePointApi.repository;

import com.triple.triplePointApi.domain.PointHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl {

    @PersistenceContext
    EntityManager em;


    /**
     * Create
     **/
    // 포인트 생성
    public Long create(PointHistory pointHistory) {
        log.info("=======PointRepository.create()=======");
        em.persist(pointHistory);
        return pointHistory.getSeq();
    }

    /**
     * Read
     **/
    public Optional<PointHistory> findMostRecentPointHistory(String userId, String placeId) {
        log.info("=======PointRepository.findPointHistoryByReviewId()=======");
        List<PointHistory> historyList =em.createQuery("select h" +
                        " from PointHistory h" +
                        " where h.seq" +
                        " in (select  max(h2.seq)" +
                        " from PointHistory h2" +
                        " where h2.userId = :userId" +
                        " and h2.placeId = :placeId)", PointHistory.class)
                .setParameter("userId", userId)
                .setParameter("placeId", placeId)
                .getResultList();
        return historyList.stream().findAny();
    }
    public Optional<PointHistory> findMostRecentPointHistoryByReviewId(String reviewId) {
        log.info("=======PointRepository.findPointHistoryByReviewId()=======");
        List<PointHistory> historyList =  em.createQuery("select h" +
                        " from PointHistory h" +
                        " where h.seq" +
                        " in (select  max(h2.seq)" +
                        " from PointHistory h2" +
                        " where h2.reviewId = :reviewId)", PointHistory.class)
                .setParameter("reviewId", reviewId)
                .getResultList();
        return historyList.stream().findAny();
    }


}