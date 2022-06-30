package com.triple.tripleMileageService.domain;

import com.triple.tripleMileageService.exception.NotEnoughPointException;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@SequenceGenerator(
        name="POINT_SEQ_GENERATOR",
        sequenceName = "POINT_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@Table(name = "point_x")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "POINT_SEQ_GENERATOR"
    )
    @Column(name = "point_id")
    private Long id;

    private int point;


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
