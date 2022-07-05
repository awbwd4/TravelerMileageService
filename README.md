# Triple Traveler Club Mileage Service
트리플 여행자 클럽 마일리지 서비스

## 1. 개요
  트리플 사용자들이 장소에 리뷰를 작성할 때 포인트를 부여하고, 전체/개인에 대한 포인트 부여 히스토리와 개인별 누적 포인트를 관리합니다.
  
 
## 2. 사용 기술
  - Language : java 11
  - Framework : Spring Boot 2.7.1
  - ORM : JPA
  - Build Too : Gradle
  - API 문서 : swagger 3.0.0
  - TEST : Junit5, Mockito
  - Database : Mysql 8.0.29
  - 버전관리 : Git
  - IDE : IntelliJ 2022.1 (Community version)
  - 기타 : homebrew, lombok
 
 ## 3. 개발 기간
  - 2022년 6월 29일 ~ 2022년 7월 5일


## 4. API 명세

  1) 사용자 신규 가입시 : "action"에 "NEW"를 추가하였습니다. 요청시 받은 userId로 Point 테이블과 PointHistory 테이블에 데이터를 생성합니다.
![스크린샷 2022-07-06 오전 12 20 59](https://user-images.githubusercontent.com/31840404/177362155-b3ba9453-ab5e-4c73-b334-c22f45fbd013.png)

  2) 리뷰 등록시 : "action"의 "ADD"로 리뷰 생성에 따른 포인트를 부여합니다. 포인트 ID를 반환합니다.
![스크린샷 2022-07-06 오전 12 06 04](https://user-images.githubusercontent.com/31840404/177362172-a6d7a08d-7628-40a3-b29e-c4af90e33956.png)

  3) 리뷰 수정시 : "action"의 "MOD"로 리뷰 수정에 따라 포인트를 증감합니다. 포인트 ID를 반환합니다.
![스크린샷 2022-07-06 오전 12 06 39](https://user-images.githubusercontent.com/31840404/177362201-9a07ea8a-2301-4620-b89f-b9f379cd1b94.png)

  4) 리뷰 삭제시 : "action"의 "DELETE"로 리뷰 등록에 부여한 포인트를 회수합니다. true/false를 반환합니다.
![스크린샷 2022-07-06 오전 12 13 34](https://user-images.githubusercontent.com/31840404/177362224-1b1a3d4e-e354-475f-b18f-9cd794615ffe.png)

  5) 사용자 포인트 조회시 : GET /points/{userId} 로 해당 사용자의 포인트를 조회합니다.
![스크린샷 2022-07-06 오전 12 00 15](https://user-images.githubusercontent.com/31840404/177363717-1d6e3aee-232e-4262-b998-b37b098235a8.png)



## 5. DDL


## 6. ERD
