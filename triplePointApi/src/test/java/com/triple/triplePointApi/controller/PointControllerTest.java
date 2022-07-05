package com.triple.triplePointApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.triplePointApi.domain.PointHistory;
import com.triple.triplePointApi.dto.PointEventRequest;
import com.triple.triplePointApi.repository.PointHistoryRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PointControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @PersistenceContext
    EntityManager em;

    @Autowired
    PointHistoryRepositoryImpl pointHistoryRepository;


    @Test
    @Transactional
    @Rollback(value = false)
    public void 사용자별포인트조회테스트() throws Exception {

        /**given**/
        // 사용자 정보가 존재해야함.
        //신규생성
        PointEventRequest request1 = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request1.setUserId("userId");

        System.out.println(request1.toString());

        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andDo(print());


        // when
        // 리뷰 등록하기
        PointEventRequest request2 = PointEventRequest.create()
                .type("REVIEW")
                .action("ADD");

        request2.setUserId("userId");
        request2.setContent("내용내용");
        request2.setPlaceId("placeId");
        request2.setReviewId("reviewId");
        List<String> photos = new ArrayList<>();//빈 객체라도 들어와야함.
        request2.setAttachedPhotoIds(photos);

        System.out.println(request2.toString());


        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2))
                ).andExpect(status().isOk())
                .andDo(print());

        /**when**/
        MvcResult mvcResult = this.mvc.perform(get("/points/{userId}", "userId"))
                .andExpect(status().isOk()).andReturn();

        /**then**/

        System.out.println("mvcResult = " + mvcResult.getResponse().getContentAsString());
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 포인트이벤트_포인트_생성_테스트() throws Exception {
        /** 신규 가입자 포인트 생성
         *  신규가입시 0점인 포인트 데이터가 하나 생긴다.
         *  포인트id 반환
         * **/
        /**given**/

        PointEventRequest request = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request.setUserId("userId9");
        List<String> photos = new ArrayList<>();
        request.setAttachedPhotoIds(photos);

        System.out.println(request.toString());

        /**then**/
        ResultActions results = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andDo(print());

        System.out.println(results.toString());
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 중복회원예외처리테스트() throws Exception {
        /** 포인트 엔티티는 1회원당 1개씩
         * 중복 회원이 존재할경우 에러 발생
         * **/
        /**given**/

        //신규생성
        PointEventRequest request = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request.setUserId("userId9");
        List<String> photos = new ArrayList<>();

        System.out.println(request.toString());


        /**when**/
        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());



        /**then**/
        // DB 내에 회원이 이미 존재하여 DUP 에러 발생, 500을 리턴한다.
        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andDo(print());

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 포인트이벤트_리뷰생성_테스트() throws Exception {
        /** 리뷰 등록시
         *  ADD로 요청이 들어오며
         *  사용자에 대한 기존의 포인트 데이터에 점수가 합산된다.
         *  포인트 id반환
         * **/

        /**given**/
        // 사용자 정보가 존재해야함.
        //신규생성
        PointEventRequest request1 = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request1.setUserId("userId");

        System.out.println(request1.toString());

        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andDo(print());


        /**when**/
        // 리뷰 등록하기
        PointEventRequest request2 = PointEventRequest.create()
                .type("REVIEW")
                .action("ADD");

        request2.setUserId("userId");
        request2.setContent("내용내용");
        request2.setPlaceId("placeId");
        request2.setReviewId("reviewId");
        List<String> photos = new ArrayList<>();//빈 객체라도 들어와야함.
        request2.setAttachedPhotoIds(photos);

        System.out.println(request2.toString());

        /**then**/
        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2))
                ).andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 포인트이벤트_수정_사진추가_테스트() throws Exception {
        /** 리뷰 수정시
         *  MOD로 요청이 들어오며
         *  사진의 추가 및 삭제 여부에 따라 증감할 포인트 점수가 결정된다.
         *  포인트 id반환
         * **/

        /**given**/
        // 사용자 정보, 기존에 등록한 리뷰정보가 존재해야함.
        //신규생성
        PointEventRequest request1 = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request1.setUserId("userId");

        System.out.println(request1.toString());

        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andDo(print());


        // 리뷰 등록하기
        PointEventRequest request2 = PointEventRequest.create()
                .type("REVIEW")
                .action("ADD");

        request2.setUserId("userId");
        request2.setContent("내용내용");
        request2.setPlaceId("placeId");
        request2.setReviewId("reviewId");
        request2.setAttachedPhotoIds(new ArrayList<>());//빈 객체라도 들어와야함.


        System.out.println(request2.toString());

        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2))
                ).andExpect(status().isOk())
                .andDo(print());


        /**when**/
//        리뷰 수정하기(사진 추가하기)
        PointEventRequest request3 = PointEventRequest.create()
                .type("REVIEW")
                .action("MOD");

        request3.setUserId("userId");
        request3.setContent("수정수정");
        request3.setPlaceId("placeId");
        request3.setReviewId("reviewId");
        List<String> photos = new ArrayList<>();
        photos.add("photoId1");
        request3.setAttachedPhotoIds(photos);



        /**then**/
        System.out.println(request3.toString());
        ResultActions results = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request3))
                ).andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 포인트이벤트_수정_사진삭제_테스트() throws Exception {
        /** 리뷰 수정시
         *  MOD로 요청이 들어오며
         *  사진의 추가 및 삭제 여부에 따라 증감할 포인트 점수가 결정된다.
         *  포인트 id반환
         * **/

        /**given**/
        // 사용자 정보, 기존에 등록한 리뷰정보가 존재해야함.
        //신규생성
        PointEventRequest request1 = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request1.setUserId("userId");

        System.out.println(request1.toString());

        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andDo(print());


        // 리뷰 등록하기
        PointEventRequest request2 = PointEventRequest.create()
                .type("REVIEW")
                .action("ADD");

        request2.setUserId("userId");
        request2.setContent("내용내용");
        request2.setPlaceId("placeId");
        request2.setReviewId("reviewId");
        request2.setAttachedPhotoIds(new ArrayList<>());//빈 객체라도 들어와야함.


        System.out.println(request2.toString());

        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2))
                ).andExpect(status().isOk())
                .andDo(print());

//        리뷰 수정하기(사진 추가하기)
        PointEventRequest request3 = PointEventRequest.create()
                .type("REVIEW")
                .action("MOD");

        request3.setUserId("userId");
        request3.setContent("수정수정");
        request3.setPlaceId("placeId");
        request3.setReviewId("reviewId");
        List<String> photos1 = new ArrayList<>();
        photos1.add("photoId1");
        request3.setAttachedPhotoIds(photos1);

        System.out.println(request3.toString());
        ResultActions results3 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request3))
                ).andExpect(status().isOk())
                .andDo(print());

        /**when**/
//        리뷰 수정하기(사진 삭제하기)
        PointEventRequest request4 = PointEventRequest.create()
                .type("REVIEW")
                .action("MOD");

        request3.setUserId("userId");
        request3.setContent("수정수정");
        request3.setPlaceId("placeId");
        request3.setReviewId("reviewId");
        List<String> photos2 = new ArrayList<>();
        photos2.add("photoId1");
        request3.setAttachedPhotoIds(photos2);


        /**then**/
        System.out.println(request3.toString());
        ResultActions results4 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request3))
                ).andExpect(status().isOk())
                .andDo(print());

    }




    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰삭제테스트() throws Exception {
        /** 리뷰 삭제시
         *  DELETE로 요청이 들어오며
         *  포인트 히스토리를 바탕으로 해당 리뷰에 대한 점수가 차감된다.
         *  포인트가 차감된 이력도 포인트 히스토리에 저장된다.
         *  포인트 id반환
         * **/

        /**given**/
        // 사용자 정보가 존재해야함.
        //신규생성
        PointEventRequest request1 = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request1.setUserId("userId");

        System.out.println(request1.toString());

        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andDo(print());


        // 리뷰 등록하기
        PointEventRequest request2 = PointEventRequest.create()
                .type("REVIEW")
                .action("ADD");

        request2.setUserId("userId");
        request2.setContent("내용내용");// 1점
        request2.setPlaceId("placeId"); // 보너스 1점
        request2.setReviewId("reviewId");
        List<String> photos1 = new ArrayList<>();//빈 객체라도 들어와야함.
        request2.setAttachedPhotoIds(photos1);
        photos1.add("photoId1"); //사진등록 1점

        System.out.println(request2.toString());

        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2))
                ).andExpect(status().isOk())
                .andDo(print());

        /**when**/
        // 리뷰 삭제하기
        PointEventRequest request3 = PointEventRequest.create()
                .type("REVIEW")
                .action("DELETE");

        request3.setUserId("userId");
        request3.setPlaceId("placeId");
        request3.setReviewId("reviewId");

        System.out.println(request3.toString());


        /**then**/
        ResultActions results3 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request3))
                ).andExpect(status().isOk())
                .andDo(print());


    }




    @Test
    @Transactional
    @DisplayName("해당 장소의 첫 리뷰어가 아니면 보너스 점수를 받지 못한다.")
    @Rollback(value = false)
    public void 포인트이벤트_보너스_테스트() throws Exception {
        /** 리뷰 등록시
         * 등록 장소에 이미 다른 리뷰가 있을 경우 보너스 점수를 받지 못한다.
         * **/

        /**given**/

        // 사용자 정보가 존재해야함.
        //사용자 1신규생성
        PointEventRequest request1 = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request1.setUserId("userId1");

        System.out.println(request1.toString());

        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andDo(print());

        //사용자 2신규생성
        PointEventRequest request2 = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request2.setUserId("userId2");

        System.out.println(request2.toString());

        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andDo(print());



        //사용자1 리뷰 등록
        PointEventRequest request3 = PointEventRequest.create()
                .type("REVIEW")
                .action("ADD");

        request3.setUserId("userId1");
        request3.setContent("내용내용");
        request3.setPlaceId("placeId");
        request3.setReviewId("reviewId1");
        List<String> photos1 = new ArrayList<>();//빈 객체라도 들어와야함.
        request3.setAttachedPhotoIds(photos1);

        System.out.println(request3.toString());

        ResultActions results3 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request3))
                ).andExpect(status().isOk())
                .andDo(print());

        /**when**/

        //사용자2 리뷰 등록
        PointEventRequest request4 = PointEventRequest.create()
                .type("REVIEW")
                .action("ADD");

        request4.setUserId("userId2");
        request4.setContent("내용내용");
        request4.setPlaceId("placeId"); //사용자1과 동일한 장소에 대한 리뷰
        request4.setReviewId("reviewId2");
        List<String> photos2 = new ArrayList<>();//빈 객체라도 들어와야함.
        request4.setAttachedPhotoIds(photos2);

        System.out.println(request4.toString());

        /**then**/

        ResultActions results4 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request4))
                ).andExpect(status().isOk())
                .andDo(print());

        //then
        Optional<PointHistory> history = pointHistoryRepository.findMostRecentPointHistory("userId2", "placeId");
        history.get().isBonus();
        Assertions.assertThat(history.get().isBonus()).isEqualTo(false);

    }




    @Test
    @Transactional
    @DisplayName("요청시 null 혹은 공백이 들어왔을 경우 400에러를 낸다.")
    @Rollback(value = false)
    public void 요청시유효성확인테스트_1() throws Exception {
        /** 유효성확인테스트
         *  null 혹은 공백이 들어왔을 경우 400에러를 낸다.
         * **/

        /**given**/

        // 사용자 정보가 존재해야함.
        //사용자 1신규생성
        PointEventRequest request1 = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request1.setUserId("");//빈값

        System.out.println(request1.toString());


        PointEventRequest request2 = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request2.setUserId("              "); //공백

        System.out.println(request2.toString());

        /**when**/

        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isBadRequest())
                .andDo(print());


        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }



    @Test
    @Transactional
    @DisplayName("요청시 잘못된 action이 주어질 경우 400에러를 낸다.")
    @Rollback(value = false)
    public void 요청시유효성확인테스트_2() throws Exception {
        /** 유효성확인테스트
         *  잘못된 action이 주어질 경우 400에러를 낸다.
         * **/

        /**given**/

        // 사용자 정보가 존재해야함.
        //사용자 1신규생성
        PointEventRequest request = PointEventRequest.create()
                .type("REVIEW")
                .action("WRONG");

        request.setUserId("userId");

        System.out.println(request.toString());

        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    @Transactional
    @DisplayName("DB에 없는 사용자에 대한 요청일 경우 500에러를 낸다.")
    @Rollback(value = false)
    public void 미존재사용자예외처리() throws Exception {
        /** DB에 없는 사용자에 대한 요청일 경우 500에러를 낸다.
         * **/

        /**given**/

        // 사용자 정보가 존재해야함.

        PointEventRequest request2 = PointEventRequest.create()
                .type("REVIEW")
                .action("ADD");

        request2.setUserId("userId");
        request2.setUserId("placeId");
        request2.setAttachedPhotoIds(new ArrayList<>());

        System.out.println(request2.toString());

        /** when **/
        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isInternalServerError())
                .andDo(print());


    }

    @Test
    @Transactional
    @DisplayName("DB에 없는 리뷰에 대한 수정요청일 경우 500에러를 낸다.")
    @Rollback(value = false)
    public void 미존재리뷰예외처리() throws Exception {
        /** DB에 없는 리뷰 대한 요청일 경우 500에러를 낸다.
         * **/

        /**given**/

        // 사용자 정보가 존재해야함.
        PointEventRequest request = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request.setUserId("userId");

        System.out.println(request.toString());

        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());


        // 사용자 정보가 존재해야함.
        PointEventRequest request2 = PointEventRequest.create()
                .type("REVIEW")
                .action("MOD");

        request2.setUserId("userId");
        request2.setPlaceId("placeId");
        request2.setReviewId("reviewId");
        request2.setAttachedPhotoIds(new ArrayList<>());

        System.out.println(request2.toString());

        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }



    @Test
    @Transactional
    @DisplayName("DB에 없는 리뷰에 대한 삭제요청일 경우 500에러를 낸다.")
    @Rollback(value = false)
    public void 미존재리뷰삭제시처리() throws Exception {
        /** DB에 없는 리뷰 대한 요청일 경우 500에러를 낸다.
         * **/

        /**given**/

        // 사용자 정보가 존재해야함.
        PointEventRequest request = PointEventRequest.create()
                .type("REVIEW")
                .action("NEW");

        request.setUserId("userId");

        System.out.println(request.toString());

        ResultActions results1 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());


        // 사용자 정보가 존재해야함.
        PointEventRequest request2 = PointEventRequest.create()
                .type("REVIEW")
                .action("DELETE");

        request2.setUserId("userId");
        request2.setPlaceId("placeId");
        request2.setReviewId("reviewId");
        request2.setAttachedPhotoIds(new ArrayList<>());

        System.out.println(request2.toString());

        ResultActions results2 = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }





}