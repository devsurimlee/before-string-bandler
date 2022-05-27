# string-bandler-before (구 springStudy)


#### 1. 프로젝트 실행 주의사항
* 기본포트: 8008 (application.yml에서 변경 가능)<br>
* Lombok 설치가 필요합니다.


#### 2. API 테스트
* swagger: http://localhost:8008/swagger-ui/index.html 접속하여 테스트 가능
<br><br>
* postman<br>
![image](https://user-images.githubusercontent.com/53467957/158023321-e601bdb5-cc58-4d84-8676-6a591621a321.png)

#### 3. API 설명
>기능: URL과 출력묶음단위, 파싱타입에 따라 필터링하여 리턴합니다.

* url: 사이트주소
* parsingType: 파싱타입 - 0:태그제외, 1: 텍스트전체
* bundleUnit: 출력묶음단위
* result: 데이터를 bundleUnit으로 나누고 남은 몫 (에러 발생시 해당 필드에서 설명을 볼수있음)
* remainder: 데이터를 bundleUnit으로 나누고 나머지

##### 데이터 파싱방법
1. 필터링한 데이터를 오름차순으로 정렬
2. 알파벳과 숫자를 번갈아가며 교차출력, 둘 중 하나가 없을 경우 나머지 알파벳 혹은 숫자를 그대로 출력함.
3. 2의 데이터를 출력묶음단위로 나누어 몫은 result, 나머지는 remainder에 담음.
4. 출력묶음단위가 데이터 전체크기보다 큰 경우 result에 설명을 적고 전체데이터를 remainder에 담음.

#### 4. 기술스택
* Spring Boot 2.6.5
* Swagger 3.0
* JUnit 5
* Lombok
