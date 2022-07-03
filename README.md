# ⌨ 알고리즘 문제 풀이 사이트

> 개인 프로젝트, 2022.06 ~ 
- 소스코드를 컴파일하고 실행해서 저장된 테스트케이스의 입력에 대해 출력이 동일한지 확인하는 시스템
- 소스코드 제출시 비동기적으로 채점되어 실시간으로 채점현황을 볼 수 있도록 구현했습니다.

![example1](https://user-images.githubusercontent.com/76832861/175816771-df7cfc13-935d-4770-9434-e1021b186b5e.gif)


## 1️⃣ 사용 기술
- JAVA 11
- Spring boot 2.7.0
- MySQL 8.0.27
- JPA
- spring security
- Thymeleaf

## 2️⃣ ERD
![erd1](https://user-images.githubusercontent.com/76832861/176327446-c7af09d3-d784-41bd-971b-151cf6f05a53.JPG)


## 3️⃣ 구현 목록
#### 공통 기능
  - 알고리즘 문제 조회
  ![image](https://user-images.githubusercontent.com/76832861/177028253-c5278235-5c35-4fce-a8c1-8e6d18e79a0e.png)

#### 관리자 기능
  - 알고리즘 문제 등록 (샘플 케이스, 테스트 케이스 포함)
  - 알고리즘 문제 수정
  - 알고리즘 문제 삭제
#### 회원 기능
  - 로그인 / 회원가입
  - 소스코드 제출
  - 채점현황 조회

