package com.triple.tripleMileageService.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triple.tripleMileageService.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.triple.tripleMileageService.domain.QPlace.place;
import static com.triple.tripleMileageService.domain.QReview.review;
import static com.triple.tripleMileageService.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    @PersistenceContext
    private EntityManager em;

    // 회원 등록
    public String create(Review review) {
        System.out.println("=======ReviewRepository.save()=======");
        em.persist(review);
        return review.getUuid();
    }

    // 회원 조회
    public Review findReview(String uuid) {
        System.out.println("=======ReviewRepository.findMember()=======");
//        return em.find(Review.class, uuid);
        return em.createQuery("select r from Review r" +
                        " join fetch r.user" +
                        " join fetch r.place" +
                        " where r.uuid = :uuid", Review.class)
                .setParameter("uuid", uuid)
                .getSingleResult();

    }


    public List<Review> findAll() {
        return em.createQuery("select r from Review r" +
                        " join fetch r.user" +
                        " join fetch r.place", Review.class)
                .getResultList();
    }


    @Transactional
    public Review modifyReview(String reviewId, String content) {
        System.out.println("=====ReviewService.modifyReview=====");
        Review findReview = findReview(reviewId);
        findReview.setContent(content);

        return findReview;
    }




//
//    public List<Review> findAll(ReviewSearch reviewSearch) {
//
//        JPAQueryFactory query = new JPAQueryFactory(em);
//        QReview review = QReview.review;
//        QUser user = QUser.user;
//        QPlace place = QPlace.place;
//
//
//        return query.select(review)
//                .from(review)
//                .join(review.user, user)
//                .join(review.place, place)
//                .where(userNameLike(reviewSearch.getUserName())
//                        .and(placeNameLike(reviewSearch.getPlaceName())))
//                .fetch();
//
//    }
//
//        private BooleanExpression userNameLike(String nameCond) {
//          if (!StringUtils.hasText(nameCond)) {
//            return null;
//            }
//            return user.name.contains(nameCond);
//         }
//        private BooleanExpression placeNameLike(String nameCond) {
//          if (!StringUtils.hasText(nameCond)) {
//            return null;
//            }
//            return place.name.contains(nameCond);
//         }








}
