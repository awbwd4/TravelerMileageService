package com.triple.pointApi.service;

import com.triple.pointApi.domain.PointHistory;
import com.triple.pointApi.dto.CalculateResult;
import com.triple.pointApi.dto.PointInfoDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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


    public CalculateResult calcutator(PointInfoDto dto, CalculateResult result) {


        /**
         * PointInfoDTO로 받은 api데이터를
         * 포인트 증감여부, 포인트 계산 후 CalculateResult로 리턴
         * */


        return result;
    }


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



//    (select max(ph.seq) from point_history ph where ph.userId = :userId and ph.placeId = :placeId)



    // 기존 리뷰 대비 사진이 삭제됐을 경우


    // 기존 리뷰 대비 사진이 추가됐을 경우


    //사진 존재여부 체크
    public boolean existPhoto(List<String> photos) {
        if(photos.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    //보너스 점수 대상 여부 체크
    public boolean bonus(String placeId) {
        List<PointHistory> findPlaceHistory = em.createQuery("select h from PointHistory h" +
                        " where h.placeId = :placeId", PointHistory.class)
                .setParameter("placeId", placeId)
                .getResultList();

        if (findPlaceHistory.size() == 0) {
            return true;
        }else{
            return false;
        }
    }

}

