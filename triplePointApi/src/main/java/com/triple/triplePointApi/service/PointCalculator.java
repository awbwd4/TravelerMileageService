package com.triple.triplePointApi.service;

import com.triple.triplePointApi.domain.PointHistory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@Component
public class PointCalculator {


    @PersistenceContext
    EntityManager em;

    private String type;
    private int point;
    private boolean photo;
    private boolean bonus;


    /**리뷰 생성시**/
    public int createAddPoint(String placeId, List<String> photos) {

        System.out.println("=========PointCalculator.createAddPoint========");

        // 리뷰 생성시 포인트 부여
        String type = "ADD";
        //리뷰 등록 1점 부여
        int resultPoint = 1;
        // 사진이 1장이상 존재시 1점 부여
        if (existPhoto(photos)) {
            resultPoint += 1;
        }
        // 해당 장소 리뷰 기등록 여부, 최초 리뷰일시 1점 부여
        if (bonus(placeId)) {
            resultPoint += 1;
        }

        return resultPoint;
    }

    /**
     * 리뷰 수정시
     **/
    public int modifiedPoint(String userId, String placeId, List<String> photos) {
        System.out.println("=========PointCalculator.modifiedPoint========");
        int modifiedPoint = 0;

        // 수정된 리뷰의 사진 여부
        boolean isPhotoExist = existPhoto(photos);

        // 기존 리뷰의 사진 존재 여부
        PointHistory findPointHistory = em.createQuery("select h from PointHistory h" +
                        " where h.seq =  (select max(ph.seq) from PointHistory ph where ph.userId = :userId and ph.placeId = :placeId)" +
                        " and h.userId = :userId" +
                        " and h.placeId = :placeId", PointHistory.class)
                .setParameter("userId", userId)
                .setParameter("placeId", placeId)
                .getSingleResult();

        if(!findPointHistory.isPhoto() & isPhotoExist){
        // 더해야할 포인트 : 기존에는 사진이 없었고, 수정후 사진 존재
            System.out.println(" // 더해야할 포인트 : 기존에는 사진이 없었고, 수정후 사진 존재");
            modifiedPoint = 1;
        } else if (findPointHistory.isPhoto() & !isPhotoExist) {
        // 빼야 할 포인트 : 기존에는 사진이 있었고, 수정후 사진 미존재
            System.out.println("// 빼야 할 포인트 : 기존에는 사진이 있었고, 수정후 사진 미존재");
            modifiedPoint = -1;
        }

        return modifiedPoint;

    }

    /**
     * 리뷰 삭제시
     **/
    public int deletePoint(String userId, String placeId) {
        System.out.println("=========PointCalculator.deletePoint========");
        //삭제할 점수 계산
        int resultPoint = 0;

        List<PointHistory> resultList = em.createQuery("select h from PointHistory h" +
                        " where h.userId = :userId" +
                        " and h.placeId = :placeId", PointHistory.class)
                .setParameter("userId", userId)
                .setParameter("placeId", placeId)
                .getResultList();

        for (PointHistory pointHistory : resultList) {
            resultPoint += pointHistory.getChangedPoint();
        }
        return resultPoint;
    }

    //사진 존재여부 체크
    public boolean existPhoto(List<String> photos) {
        System.out.println("=========PointCalculator.existPhoto========");
        if(photos.size() > 0){
            return true;
        }else{
            return false;
        }
    }

   /**
    * 보너스 점수대상 여부 확인
    * **/
    public boolean bonus(String placeId) {
        System.out.println("=========PointCalculator.bonus========");

        /* 1. 첫 등록자인가? (히스토리 테이블내 건수 없음)*/

        List<PointHistory> findPlaceHistory = getPointHistories(placeId);
        if(findPlaceHistory.size()==0){
            return true;
        }

         /* 2. 이전의 리뷰가 전부 삭제됐는가?(삭제 내역의수 == 등록자의 수)
         *      - 사용자별 가장 최근 히스토리가 DELETE인 그리드의 수 == 등록자의 수
         *      - 누군가 삭제후 새로 등록했다면? :  그리드의 수 < 등록자의 수
         *      - 따라서 신규 등록자는 보너스 대상이 아니다. */

        // 각 사용자의 최신 히스토리가 DELETE인 그리드의 수
        int statusHistoryCount = getStatusHistoryCount(placeId, "DELETE");
        // 댓글 단 사람의 수
        int reviewerCount = getReviewerCount(placeId);
        System.out.println("statusHistoryCount = " + statusHistoryCount);
        System.out.println("reviewerCount = " + reviewerCount);
        if(statusHistoryCount == reviewerCount){
            return true;
        }
        return false;

    }


    //첫 등록자 여부
    private List<PointHistory> getPointHistories(String placeId) {
        List<PointHistory> findPlaceHistory = em.createQuery("select h from PointHistory h" +
                        " where h.placeId = :placeId", PointHistory.class)
                .setParameter("placeId", placeId)
                .getResultList();
        return findPlaceHistory;
    }



//  사용자별 가장 최근 히스토리가 DELETE인 그리드의 수
    private int getStatusHistoryCount(String placeId, String discriminator) {
        BigInteger count = (BigInteger) em.createNativeQuery("select count(1)" +
                " from point_history" +
                " where (user_id, point_history_id)" +
                " in" +
                    " (select user_id, max(point_history_id)" +
                    " from point_history where place_id = :place_id" +
                    " group by user_id)" +
                " and discriminator = :discriminator")
                .setParameter("place_id", placeId)
                .setParameter("discriminator", discriminator)
                .getSingleResult()
                ;
        return count.intValue();
    }

    // 등록자의 수
    private int getReviewerCount(String placeId) {
        BigInteger userCount = (BigInteger) em.createNativeQuery("select count(1)" +
                        " from" +
                        " (select 1 from point_history" +
                        " where place_id = :place_id group by user_id) ph")
                .setParameter("place_id", placeId)
                .getSingleResult();
        return userCount.intValue();

    }



}

