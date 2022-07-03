package com.triple.domain;

import com.triple.exception.NotEnoughPointException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "point")
@NoArgsConstructor
public class Point {

    @Id
    @Column(name = "point_uuid")
    private String uuid;
    private int point;

    private String userId;


//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
////    private String placeId;

    @OneToMany(mappedBy = "point", cascade = CascadeType.ALL)
    private List<PointHistory> historyList = new ArrayList<>();

    /**
     * == 연관관계메서드 ==
     **/
    public void addHistory(PointHistory pointHistory) {
        pointHistory.setPoint(this);
        historyList.add(pointHistory);
    }

    /**
     * ===포인트 생성 메서드====
     * 사용자의 최초 가입시에만 사용된다.
     **/
//    public static Point createPoint(String userId, PointHistory... pointHistories
//    ) {
//        Point point = new Point();
//        point.setUuid(UUID.randomUUID().toString());
//        point.setUserId(userId);
////        point.setPlaceId(placeId);
//        for (PointHistory pointHistory : pointHistories) {
//        point.addHistory(pointHistory);
//        }
//        return point;
//
//    }
    public static Point createPoint(String userId, PointHistory... pointHistories
    ) {
        Point point = new Point();
        point.setUuid(UUID.randomUUID().toString());
//        point.setUser(user);
        point.setUserId(userId);
//        point.setPlaceId(placeId);
        for (PointHistory pointHistory : pointHistories) {
        point.addHistory(pointHistory);
        }
        return point;

    }

    /**
     * 비즈니스 로직
     **/
    // 포인트 증가
    public void increasePoint(int point) {
        this.point += point;
    }

    //포인트 감소
    public void modifiedPoint(int point) {
        int restPoint = this.point+point;
        if(restPoint < 0){
            throw new NotEnoughPointException("포인트를 더 감소시킬 수 없습니다.");
        }
        this.point = restPoint;
    }



}
