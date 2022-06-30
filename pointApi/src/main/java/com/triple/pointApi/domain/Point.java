package com.triple.pointApi.domain;

import com.triple.pointApi.exception.NotEnoughPointException;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Table(name = "point")
public class Point {

    @Id
    @Column(name = "point_uuid")
    private String uuid;
    private int point;

    private String memberId;
    private String placeId;

    /**==생성자==**/

    private Point() {
    }

    /**
     * ===포인트 생성 메서드====
     **/
    public static Point createPoint(String memberId, String placeId) {
        Point point = new Point();
        point.setUuid(UUID.randomUUID().toString());
        point.setMemberId(memberId);
        point.setPlaceId(placeId);
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
    public void decreasePoint(int point) {
        if(point <= 0){
            throw new NotEnoughPointException("포인트를 더 감소시킬 수 없습니다.");
        }
        this.point -= point;
    }



}
