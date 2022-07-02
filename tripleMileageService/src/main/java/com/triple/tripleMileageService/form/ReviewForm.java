package com.triple.tripleMileageService.form;

import com.triple.tripleMileageService.domain.Photo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class ReviewForm {


    @NotEmpty(message = "사용자 선택은 필수입니다.")
    private String userId;
    @NotEmpty(message = "장소 선택은 필수입니다.")
    private String placeId;
    @NotEmpty(message = "내용 입력은 필수입니다.")
    private String content;
    private List<Photo> photoList;


    private String reviewId;
    private String userName;
    private String placeName;



}
