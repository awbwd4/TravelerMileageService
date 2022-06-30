package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    @PersistenceContext
    private EntityManager em;

    // 회원 등록
    public Long save(Review review) {
        System.out.println("=======ReviewRepository.save()=======");
        em.persist(review);
        return review.getId();
    }

    // 회원 조회
    public Review findReview(Long id) {
        System.out.println("=======ReviewRepository.findMember()=======");
        return em.find(Review.class, id);
    }



}
