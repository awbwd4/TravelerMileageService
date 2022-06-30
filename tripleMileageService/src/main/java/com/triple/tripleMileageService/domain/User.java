package com.triple.tripleMileageService.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter @Setter
@SequenceGenerator(
        name="USER_SEQ_GENERATOR",
        sequenceName = "USER_SEQ",
        initialValue = 1,
        allocationSize = 50
)
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "USER_SEQ_GENERATOR"
    )
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "point_id")
    private Point point;

    //===생성자===//
    private User() {
    }

    public User(String name) {
        this.name = name;
    }


    //===Getter Setter===//

    private void setPoint(Point point) {
        this.point = point;
    }


    //==사용자 생성 메서드==//

    public static User createUser(String name, Point point) {
        User user = new User(name);
        user.setPoint(point);
        return user;
    }





}
