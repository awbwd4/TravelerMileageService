package com.triple.triplePointApi.domain;

import com.triple.triplePointApi.exception.NotEnoughPointException;
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
public class Point extends BaseTimeEntity{

    @Id
    @Column(name = "point_uuid")
    private String uuid;

    private int point;

    private String userId;

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
    public static Point createPoint(String userId, PointHistory... pointHistories
    ) {
        Point point = new Point();
        point.setUuid(UUID.randomUUID().toString());
        point.setUserId(userId);
        for (PointHistory pointHistory : pointHistories) {
        point.addHistory(pointHistory);
        }
        return point;

    }

    /**
     * 비즈니스 로직
     **/

    //포인트 변동
    public void modifyPoint(int point) throws NotEnoughPointException {
        int restPoint = this.point+point;
        if(restPoint < 0){
            throw new NotEnoughPointException("포인트를 더 감소시킬 수 없습니다.");
        }
        this.point = restPoint;
    }

}
