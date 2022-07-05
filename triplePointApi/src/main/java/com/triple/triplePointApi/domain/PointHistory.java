package com.triple.triplePointApi.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name="POINT_HISTORY_SEQ_GENERATOR",
        sequenceName = "REVIEW_SEQ",
        initialValue = 1,
        allocationSize = 50
)
public class PointHistory extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "POINT_HISTORY_SEQ_GENERATOR")
    @Column(name = "point_history_id")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    private String discriminator; //CREATE, INCREASE, DECREASE

//    @NotEmpty
    private int changedPoint;

    private String userId;
    private String placeId;
    private String reviewId;
    private boolean photo;// 사진 첨부 여부
    private boolean bonus; // 보너스 점수 여부


    /**
     * == 생성자 ==
     **/
    private PointHistory() {

    }

    /**
     * == 생성 메서드 ==
     **/
    //포인트 히스토리 생성
    /**
     * 사용자 신규 생성시
     **/
    public static PointHistory createPointHistory(int changedPoint, String discriminator
            , String userId, String placeId
            , boolean photo, boolean bonus) {

        PointHistory pointHistory = new PointHistory();
        pointHistory.setChangedPoint(changedPoint);
        pointHistory.setDiscriminator(discriminator);
        pointHistory.setUserId(userId);
        pointHistory.setPlaceId(placeId);

        pointHistory.setPhoto(photo);
        pointHistory.setBonus(bonus);

        return pointHistory;
    }

    /**
     * 리뷰 최초 등록시, 리뷰 수정시, 리뷰 삭제시
     **/
    public static PointHistory createPointHistory(Point point, int changedPoint, String discriminator
            , String userId, String placeId, String reviewId
            , boolean photo, boolean bonus) {

        PointHistory pointHistory = new PointHistory();
        pointHistory.setPoint(point);
        pointHistory.setChangedPoint(changedPoint);
        pointHistory.setDiscriminator(discriminator);
        pointHistory.setUserId(userId);
        pointHistory.setPlaceId(placeId);
        pointHistory.setReviewId(reviewId);
        pointHistory.setPhoto(photo);
        pointHistory.setBonus(bonus);

        return pointHistory;
    }






}
