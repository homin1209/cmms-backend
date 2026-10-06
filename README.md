# CMMS

설비의 점검, 고장, 정비 이력을 관리하고 상태 변화를 추적할 수 있는 **Spring Boot 기반 CMMS(Computerized Maintenance Management System) 백엔드 API 프로젝트**입니다.

## 1. 프로젝트 소개

제조 현장의 설비 관리 업무를 주제로 **설비 등록 → 점검 → 고장 발생 → 정비 → 정상화**까지의 업무 흐름을 REST API로 구현했습니다.

단순 CRUD 구현에 그치지 않고 점검, 고장, 정비 과정에서 발생하는 **설비 상태와 업무 상태의 연계**를 구현했습니다.

또한 실제 백엔드 서비스에 필요한 JWT 기반 인증·인가, 요청값 검증, 전역 예외 처리, 검색 및 페이지네이션, 대시보드 요약 API를 구현했으며 Docker Compose를 이용해 Spring Boot 애플리케이션과 PostgreSQL을 함께 실행할 수 있는 환경을 구성했습니다.

## 2. 개발 환경 및 기술 스택

| 구분 | 기술 |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 4.1.0 |
| Database | PostgreSQL 18 |
| ORM | Spring Data JPA |
| Security | Spring Security, JWT |
| Validation | Jakarta Validation |
| Build | Gradle |
| API Test | Postman |
| Container | Docker, Docker Compose |
| IDE | IntelliJ IDEA |
| Version Control | Git, GitHub |

## 3. 주요 기능

### 설비 관리

- 설비 등록, 조회, 수정, 삭제
- 설비 코드 중복 검증
- 설비 상태 관리
    - `RUNNING`
    - `FAILURE`
    - `MAINTENANCE`
- 설비 상태 및 이름 기반 검색
- 페이지네이션 및 정렬

### 점검 관리

- 설비별 점검 이력 등록 및 조회
- 점검 결과 관리
    - `NORMAL`
    - `ABNORMAL`
- 점검 결과 기반 필터링
- 페이지네이션 및 점검 일시 기준 정렬

### 고장 관리

- 설비별 고장 이력 등록 및 조회
- 점검 이력과 고장 이력 연계
- 고장 상태 관리
    - `REPORTED`
    - `IN_PROGRESS`
    - `RESOLVED`
- 고장 상태 전이 검증
- 고장 등록 시 설비 상태를 `FAILURE`로 변경
- 페이지네이션 및 고장 발생 일시 기준 정렬

### 정비 관리

- 설비별 정비 이력 등록 및 조회
- 고장 이력과 정비 이력 연계
- 정비 상태 관리
    - `PLANNED`
    - `IN_PROGRESS`
    - `COMPLETED`
- 정비 상태 전이 검증
- 정비 진행 시 설비와 연결된 고장 상태 동기화
- 정비 완료 시 설비를 `RUNNING`, 연결된 고장을 `RESOLVED` 상태로 변경

### 인증 및 권한 관리

- 사용자 회원가입 및 로그인
- BCrypt 기반 비밀번호 암호화
- JWT 기반 인증
- `USER`, `ADMIN` 역할 기반 API 접근 제어
- 인증 실패 `401 Unauthorized` 처리
- 권한 부족 `403 Forbidden` 처리

### API 품질 및 예외 처리

- DTO Validation을 이용한 요청값 검증
- 전역 예외 처리
- 공통 `ErrorResponse` 형식 적용
- 존재하지 않는 리소스에 대한 `404 Not Found` 처리
- 중복 데이터에 대한 `409 Conflict` 처리
- 잘못된 상태 전이에 대한 `400 Bad Request` 처리

### 대시보드

- 전체 설비 현황 조회
- 정상 / 고장 / 정비 중 설비 수 조회
- 정상 / 비정상 점검 현황 조회
- 고장 상태별 현황 조회
- 정비 상태별 현황 조회
- 설비, 점검, 고장, 정비 현황 통합 조회 API 제공

### Docker 실행 환경

- Spring Boot 애플리케이션 Docker 이미지 구성
- PostgreSQL 컨테이너 구성
- Docker Compose 기반 애플리케이션 및 데이터베이스 통합 실행
- Docker 환경별 Spring Profile 분리
- 환경변수를 이용한 DB 비밀번호 및 JWT Secret 관리

## 4. 주요 업무 흐름

CMMS의 핵심 기능은 점검, 고장, 정비 상태와 설비 상태가 서로 연계되어 변경되는 구조입니다.

```text
설비
RUNNING
   ↓
비정상 점검
ABNORMAL
   ↓
고장 등록
REPORTED
설비 → FAILURE
   ↓
정비 시작
IN_PROGRESS
설비 → MAINTENANCE
고장 → IN_PROGRESS
   ↓
정비 완료
COMPLETED
설비 → RUNNING
고장 → RESOLVED
```

정비 상태는 다음 순서로만 변경할 수 있습니다.

```text
PLANNED → IN_PROGRESS → COMPLETED
```

고장 상태는 다음 순서로 변경됩니다.

```text
REPORTED → IN_PROGRESS → RESOLVED
```

허용되지 않은 상태 전이 요청은 `400 Bad Request`로 처리하여 잘못된 업무 상태 변경을 방지했습니다.