# Triple Traveler Club Mileage Service
트리플 여행자 클럽 마일리지 서비스

## 1. 개요
  트리플 사용자들이 장소에 리뷰를 작성할 때 포인트를 부여하고, 전체/개인에 대한 포인트 부여 히스토리와 개인별 누적 포인트를 관리합니다.
  
 
## 2. 사용 기술
  - Language : java 11
  - Framework : Spring Boot 2.7.1
  - ORM : JPA
  - Build Tool : Gradle
  - API 문서 : swagger 3.0.0
  - TEST : Junit5, Mockito
  - Database : Mysql 8.0.29
  - 버전관리 : Git/GitHub
  - IDE : IntelliJ 2022.1 (Community version)
  - 기타 : homebrew, lombok, postman
 
 ## 3. 개발 기간
  - 2022년 6월 29일 ~ 2022년 7월 5일


## 4. API 명세

  ### 1) 사용자 신규 가입시 : "action"에 "NEW"를 추가하였습니다. 요청시 받은 userId로 Point 테이블과 PointHistory 테이블에 데이터를 생성합니다.
![스크린샷 2022-07-06 오전 12 20 59](https://user-images.githubusercontent.com/31840404/177362155-b3ba9453-ab5e-4c73-b334-c22f45fbd013.png)

  ### 2) 리뷰 등록시 : "action"의 "ADD"로 리뷰 생성에 따른 포인트를 부여합니다. 포인트 ID를 반환합니다.
![스크린샷 2022-07-06 오전 12 06 04](https://user-images.githubusercontent.com/31840404/177362172-a6d7a08d-7628-40a3-b29e-c4af90e33956.png)

  ### 3) 리뷰 수정시 : "action"의 "MOD"로 리뷰 수정에 따라 포인트를 증감합니다. 포인트 ID를 반환합니다.
![스크린샷 2022-07-06 오전 12 06 39](https://user-images.githubusercontent.com/31840404/177362201-9a07ea8a-2301-4620-b89f-b9f379cd1b94.png)

  ### 4) 리뷰 삭제시 : "action"의 "DELETE"로 리뷰 등록에 부여한 포인트를 회수합니다. true/false를 반환합니다.
![스크린샷 2022-07-06 오전 12 13 34](https://user-images.githubusercontent.com/31840404/177362224-1b1a3d4e-e354-475f-b18f-9cd794615ffe.png)

  ### 5) 사용자 포인트 조회시 : GET /points/{userId} 로 해당 사용자의 포인트를 조회합니다.
![스크린샷 2022-07-06 오전 12 00 15](https://user-images.githubusercontent.com/31840404/177363717-1d6e3aee-232e-4262-b998-b37b098235a8.png)



## 5. DDL

  ### 1) point 테이블
  
          create table point (
            point_uuid varchar(255) not null
          , user_id varchar(255)
          , point integer not null
          , created_date datetime
          , modified_date datetime
        , primary key (point_uuid))
        engine=InnoDB;
  
  ### 2) point_history 테이블

        create table point_history2 (
            point_history_id bigint not null
          , point_id varchar(255) ##fk
          , user_id varchar(255)
          , place_id varchar(255)
          , review_id varchar(255)
          , discriminator varchar(255)
          , changed_point integer not null
          , photo bit not null
          , bonus bit not null
          , created_date datetime
          , modified_date datetime
        , primary key (point_history_id)
        ,foreign key (point_id) references point2(point_uuid)
        )
        engine=InnoDB; 

  ### 3) index
 
       CREATE INDEX IDX_POINT_USERID ON point 
      (user_id);
      CREATE INDEX IDX_POINT_POINTID ON point 
      (point_uuid);


      CREATE INDEX IDX_POINT_USER_ID ON point_history 
      (user_id, place_id);
      CREATE INDEX IDX_POINT_POINT_ID ON point_history 
      (point_id);
      CREATE INDEX IDX_POINT_REVIEW_ID ON point_history 
      (review_id);





## 6. ERD

![스크린샷 2022-07-06 오전 1 19 04](https://user-images.githubusercontent.com/31840404/177372729-7bea58cb-a4b2-4bc7-9c87-aa12bdb5ebe6.png)



## 7. TEST
  * JPA DDL-AUTO : "create"
  
    - 사용자별 포인트 조회
    - 포인트 이벤트 발생시 포인트 생성
    - 중복회원 예외처리
    - 포인트이벤트 리뷰생성
    - 포인트이벤트 수정 사진추가
    - 포인트이벤트 수정 사진삭제
    - 리뷰 삭제
    - 리뷰 삭제 후 재등록
    - 포인트 이벤트 해당 장소 첫 리뷰 등록시 보너스 부여
    - 요청시 유효성 확인 테스트 : 요청시 null 혹은 공백이 들어왔을 경우 400에러를 낸다.
    - 요청시 유효성 확인 테스트 : 요청시 잘못된 action이 주어질 경우 400에러를 낸다.
    - 미존재 사용자 예외처리 : DB에 없는 사용자에 대한 요청일 경우 500에러를 낸다.
    - 미존재 리뷰 예외처리 : DB에 없는 리뷰에 대한 수정요청일 경우 500에러를 낸다.
    - 미존재 리뷰 삭제시 예외처리 : DB에 없는 리뷰에 대한 삭제요청일 경우 500에러를 낸다.

  ### 1) pointController
![스크린샷 2022-07-06 오전 1 38 24](https://user-images.githubusercontent.com/31840404/177376179-f2ae3921-6325-421a-868d-02798d4f34f4.png)

  ### 2) pointServiceImpl
![스크린샷 2022-07-06 오전 1 39 42](https://user-images.githubusercontent.com/31840404/177376182-2d8cc1af-3fc4-4007-bed2-4c778c0a8cda.png)

  ### 3) pointReposotoryImpl
![스크린샷 2022-07-06 오전 1 41 01](https://user-images.githubusercontent.com/31840404/177376190-6b40b5e2-2e30-4c73-8b44-742960a5fe1a.png)

  ### 4) pointHistoryReposotoryImpl
![스크린샷 2022-07-06 오전 1 40 22](https://user-images.githubusercontent.com/31840404/177376184-32073e8f-51f5-45f0-a90b-ae182360bc22.png)

