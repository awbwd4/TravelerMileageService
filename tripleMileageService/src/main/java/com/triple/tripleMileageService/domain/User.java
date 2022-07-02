package com.triple.tripleMileageService.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Getter @Setter
public class User{

    @Id
    @Column(name = "user_id")
    private String uuid;

    @NotEmpty
    private String name;

    private String pointId;

    //===생성자===//

    //==사용자 생성 메서드==//

    public static User createUser(String name, String pointUuid) {
        User user = new User();
        user.setName(name);
        user.setUuid(UUID.randomUUID().toString());
        user.setPointId(pointUuid);
        return user;
    }





}
