package com.triple.tripleMileageService.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class UserForm {

    private String uuid;
    @NotEmpty(message = "이름 입력은 필수입니다.")
    private String name;
    private String pointId;


}
