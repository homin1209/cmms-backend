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

## 5. Docker 실행 방법

Docker Compose를 이용해 Spring Boot 애플리케이션과 PostgreSQL을 함께 실행할 수 있습니다.

### 5.1 실행 환경

프로젝트 실행을 위해 다음 환경이 필요합니다.

- Java 21
- Docker
- Docker Compose
- Git

Docker를 이용해 애플리케이션과 PostgreSQL을 실행하므로 별도의 PostgreSQL 설치 없이 프로젝트를 실행할 수 있습니다.

### 5.2 프로젝트 다운로드

GitHub Repository를 Clone한 후 프로젝트 디렉터리로 이동합니다.

```bash
git clone <repository-url>
cd <project-directory>
```

`<repository-url>`에는 실제 GitHub Repository 주소를 입력합니다.

### 5.3 환경변수 설정

프로젝트 루트 디렉터리에 `.env` 파일을 생성합니다.

```text
DB_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret
```

- `DB_PASSWORD`: PostgreSQL에서 사용할 비밀번호
- `JWT_SECRET`: JWT 생성 및 검증에 사용할 Secret Key

보안을 위해 `.env` 파일에는 실제 비밀번호와 Secret Key가 포함되므로 Git Repository에 업로드하지 않습니다.

`.gitignore`에 다음 설정이 포함되어 있는지 확인합니다.

```text
.env
```

### 5.4 Docker Compose 실행

프로젝트 루트 디렉터리에서 다음 명령어를 실행합니다.

```bash
docker compose up -d --build
```

이 명령어를 실행하면 다음 컨테이너가 생성되고 실행됩니다.

```text
cmms-app
cmms-db
```

- `cmms-app`: Spring Boot 애플리케이션
- `cmms-db`: PostgreSQL 데이터베이스

Spring Boot 애플리케이션은 Docker 환경에서 `docker` profile을 사용합니다.

데이터베이스 연결은 Docker Compose의 서비스 이름인 `db`를 이용합니다.

```text
jdbc:postgresql://db:5432/cmms
```

### 5.5 컨테이너 실행 확인

다음 명령어를 이용해 실행 중인 컨테이너를 확인합니다.

```bash
docker ps
```

`cmms-app`과 `cmms-db`가 정상적으로 실행되고 있는지 확인합니다.

애플리케이션 로그가 필요한 경우 다음 명령어를 사용할 수 있습니다.

```bash
docker compose logs app
```

실시간으로 로그를 확인하려면 다음과 같이 실행합니다.

```bash
docker compose logs -f app
```

### 5.6 API 실행 확인

애플리케이션이 정상적으로 실행되면 Postman 등의 API 클라이언트를 이용해 API를 테스트할 수 있습니다.

JWT 인증이 필요한 API는 먼저 회원가입 및 로그인을 진행합니다.

```text
회원가입
    ↓
로그인
    ↓
JWT 발급
    ↓
Authorization: Bearer {token}
    ↓
CMMS API 요청
```

로그인으로 발급받은 JWT를 다음 HTTP Header에 추가합니다.

```text
Authorization: Bearer {token}
```

이후 권한에 따라 설비, 점검, 고장, 정비 및 대시보드 API를 사용할 수 있습니다.

### 5.7 Docker Compose 종료

실행 중인 컨테이너를 종료하려면 다음 명령어를 사용합니다.

```bash
docker compose down
```

컨테이너를 다시 실행하려면 다음 명령어를 사용합니다.

```bash
docker compose up -d
```

코드 또는 Docker 이미지 구성이 변경되어 이미지를 다시 빌드해야 하는 경우 다음 명령어를 사용합니다.

```bash
docker compose up -d --build
```

## 6. 환경별 설정

프로젝트는 실행 환경에 따라 Spring Profile을 분리했습니다.

```text
application.yaml
application-local.yaml
application-docker.yaml
```

- `application.yaml`: 공통 설정
- `application-local.yaml`: 로컬 개발 환경 설정
- `application-docker.yaml`: Docker 실행 환경 설정

Docker 환경에서는 PostgreSQL 컨테이너와 연결하기 위해 다음과 같은 데이터베이스 주소를 사용합니다.

```text
jdbc:postgresql://db:5432/cmms
```

민감한 값은 설정 파일에 직접 작성하지 않고 환경변수로 전달합니다.

```text
DB_PASSWORD
JWT_SECRET
```

이를 통해 로컬 환경과 Docker 환경의 설정을 분리하고, 비밀번호와 JWT Secret 같은 민감 정보를 소스 코드와 분리해 관리했습니다.

## 7. API 구성

CMMS API는 설비를 중심으로 점검, 고장, 정비 이력을 관리하도록 구성했습니다.

### User

| Method | Endpoint | 설명 |
| --- | --- | --- |
| POST | `/users` | 회원가입 |
| POST | `/users/login` | 로그인 및 JWT 발급 |

### Equipment

| Method | Endpoint | 설명 |
| --- | --- | --- |
| POST | `/equipments` | 설비 등록 |
| GET | `/equipments` | 설비 목록 조회 |
| GET | `/equipments/{id}` | 설비 상세 조회 |
| PUT | `/equipments/{id}` | 설비 수정 |
| DELETE | `/equipments/{id}` | 설비 삭제 |

설비 목록 조회에서는 상태, 이름 검색과 페이지네이션 및 정렬을 지원합니다.

### Inspection

| Method | Endpoint | 설명 |
| --- | --- | --- |
| POST | `/equipments/{equipmentId}/inspections` | 점검 등록 |
| GET | `/equipments/{equipmentId}/inspections` | 설비별 점검 목록 조회 |

점검 결과를 기준으로 필터링할 수 있으며 점검 일시를 기준으로 정렬합니다.

### Failure

| Method | Endpoint | 설명 |
| --- | --- | --- |
| POST | `/equipments/{equipmentId}/failures` | 고장 등록 |
| GET | `/equipments/{equipmentId}/failures` | 설비별 고장 목록 조회 |
| PUT | `/equipments/{equipmentId}/failures/{id}` | 고장 상태 수정 |

고장 상태를 기준으로 필터링할 수 있으며 고장 발생 일시를 기준으로 정렬합니다.

### Maintenance

| Method | Endpoint | 설명 |
| --- | --- | --- |
| POST | `/equipments/{equipmentId}/maintenances` | 정비 등록 |
| GET | `/equipments/{equipmentId}/maintenances` | 설비별 정비 목록 조회 |
| PUT | `/equipments/{equipmentId}/maintenances/{id}` | 정비 상태 수정 |

정비 상태를 기준으로 필터링할 수 있으며 정비 상태 변경에 따라 설비와 연결된 고장 상태가 함께 변경됩니다.

### Dashboard

| Method | Endpoint | 설명 |
| --- | --- | --- |
| GET | `/dashboard` | 전체 CMMS 현황 통합 조회 |
| GET | `/dashboard/equipments` | 설비 상태 요약 |
| GET | `/dashboard/inspections` | 점검 결과 요약 |
| GET | `/dashboard/failures` | 고장 상태 요약 |
| GET | `/dashboard/maintenances` | 정비 상태 요약 |

> 실제 Endpoint가 위 표와 다른 경우 현재 Controller의 Mapping을 기준으로 수정합니다.

## 8. 인증 및 권한

Spring Security와 JWT를 이용해 Stateless 인증 방식을 구현했습니다.

사용자가 로그인하면 JWT를 발급하고, 이후 인증이 필요한 요청에서는 HTTP Header에 JWT를 전달합니다.

```text
Authorization: Bearer {token}
```

인증 과정은 다음과 같습니다.

```text
로그인 요청
    ↓
사용자 정보 확인
    ↓
JWT 발급
    ↓
Authorization Header에 JWT 전달
    ↓
JwtAuthenticationFilter
    ↓
JWT 검증 및 사용자 조회
    ↓
SecurityContext 인증 정보 등록
    ↓
API 접근 권한 확인
```

사용자 권한은 `USER`와 `ADMIN`으로 구분했습니다.

- `USER`
  - 설비 및 대시보드 조회 가능
- `ADMIN`
  - 조회 기능 사용 가능
  - 설비 등록, 수정, 삭제 가능
  - 관리 기능 접근 가능

인증 정보가 없거나 유효하지 않은 경우 `401 Unauthorized`, 인증은 되었지만 필요한 권한이 없는 경우 `403 Forbidden`으로 처리합니다.

## 9. 프로젝트 구조

프로젝트는 도메인별로 패키지를 분리하고 각 도메인 내부에서 Controller, Service, Repository, Entity, DTO의 역할을 구분했습니다.

```text
src/main/java
└── ...
    ├── user
    ├── equipment
    ├── inspection
    ├── failure
    ├── maintenance
    ├── dashboard
    └── common
        ├── exception
        └── security
```

각 계층의 역할은 다음과 같습니다.

- `Controller`: HTTP 요청 및 응답 처리
- `Service`: 비즈니스 로직 및 상태 변경 처리
- `Repository`: 데이터베이스 접근
- `Entity`: 도메인 데이터 및 관계 표현
- `DTO`: API 요청 및 응답 데이터 분리
- `Security`: JWT 인증 및 Spring Security 설정
- `Exception`: 전역 예외 처리 및 공통 오류 응답

## 10. 핵심 구현 내용

### 도메인 상태 연계

점검, 고장, 정비를 각각 독립적인 CRUD 기능으로 처리하지 않고 실제 설비 관리 업무 흐름에 맞게 상태를 연계했습니다.

고장이 등록되면 설비를 `FAILURE` 상태로 변경하고, 정비가 시작되면 설비를 `MAINTENANCE` 상태로 변경합니다.

고장과 연결된 정비가 완료되면 고장은 `RESOLVED`, 설비는 다시 `RUNNING` 상태로 변경됩니다.

이를 통해 설비의 현재 상태와 고장·정비 진행 상황이 서로 일치하도록 구성했습니다.

### 상태 전이 검증

고장과 정비 상태가 임의의 순서로 변경되지 않도록 허용된 상태 전이 규칙을 적용했습니다.

```text
Failure
REPORTED → IN_PROGRESS → RESOLVED

Maintenance
PLANNED → IN_PROGRESS → COMPLETED
```

허용되지 않은 상태 변경 요청은 `400 Bad Request`로 처리합니다.

### JWT 인증 및 권한 분리

Spring Security와 JWT를 이용해 세션을 사용하지 않는 Stateless 인증 구조를 구현했습니다.

로그인 후 발급받은 JWT를 요청마다 검증하고 사용자 역할에 따라 API 접근 권한을 구분했습니다.

인증 실패와 권한 부족을 각각 `401`, `403`으로 분리해 클라이언트가 실패 원인을 명확하게 확인할 수 있도록 했습니다.

### 일관된 API 예외 처리

전역 예외 처리를 적용해 API에서 발생하는 오류 응답 형식을 통일했습니다.

```json
{
  "status": 400,
  "message": "오류 메시지"
}
```

요청값 검증, 존재하지 않는 리소스, 중복 데이터, 잘못된 상태 전이 등의 예외를 HTTP 상태 코드에 맞게 처리했습니다.

### Docker 실행 환경 구성

Spring Boot 애플리케이션과 PostgreSQL을 각각 컨테이너로 구성하고 Docker Compose를 통해 함께 실행하도록 구성했습니다.

로컬 환경과 Docker 환경의 Spring Profile을 분리했으며 DB 비밀번호와 JWT Secret 등의 민감 정보는 환경변수로 관리했습니다.

이를 통해 개발 환경에 직접 PostgreSQL을 구성하지 않아도 Docker를 이용해 동일한 애플리케이션 실행 환경을 구성할 수 있도록 했습니다.

## 11. 트러블슈팅

### 11.1 JWT 인증 및 권한 처리 문제

**문제**

Spring Security와 JWT 인증 기능 구현 과정에서 로그인 이후 API 요청 시 `401 Unauthorized`가 발생했습니다.

또한 일반 사용자와 관리자 권한을 구분하는 과정에서 인증 실패와 접근 권한 부족에 대한 응답을 명확하게 처리할 필요가 있었습니다.

**원인**

- 초기 로그인 요청에서 잘못된 API 경로를 사용하여 인증 오류 발생
- Spring Security의 URL별 접근 권한 설정 확인 필요
- 인증되지 않은 요청과 권한이 부족한 요청에 대한 예외 처리 구분 필요

**해결**

- 로그인 API 경로를 `/users/login`으로 수정
- `JwtAuthenticationFilter`에서 JWT 검증 후 사용자 정보를 `SecurityContext`에 등록
- `SecurityFilterChain`에서 API별 접근 권한 설정
- `CustomAuthenticationEntryPoint`를 통해 인증 실패 시 `401` 반환
- `CustomAccessDeniedHandler`를 통해 권한 부족 시 `403` 반환

**결과**

- 로그인 후 JWT를 이용한 API 인증 정상 동작
- `USER` 권한으로 설비 조회 요청 시 `200 OK` 확인
- `USER` 권한으로 관리자 전용 API 요청 시 `403 Forbidden` 확인
- `ADMIN` 권한으로 설비 등록 요청 시 `201 Created` 확인

### 11.2 Docker 환경에서 JWT Secret 설정 오류

**문제**

Spring Boot 애플리케이션을 Docker 컨테이너로 실행하는 과정에서 JWT Secret 관련 오류가 발생하여 애플리케이션이 정상적으로 실행되지 않았습니다.

**원인**

- Docker 환경에서 `JWT_SECRET` 환경변수가 설정되지 않음
- JWT 설정에서 필요한 환경변수를 참조하지 못해 `PlaceholderResolutionException` 발생
- JWT Secret의 키 길이 조건과 관련된 `WeakKeyException` 발생

**해결**

- 프로젝트 루트에 `.env` 파일 생성
- `.env`에 `JWT_SECRET` 환경변수 설정
- JWT 서명 알고리즘에 적합한 길이의 Secret Key 사용
- Docker Compose에서 환경변수를 Spring Boot 컨테이너로 전달하도록 설정
- `.env`를 Git 관리 대상에서 제외하여 민감 정보 노출 방지

**결과**

- Docker 환경에서 JWT Secret 정상 적용
- Spring Boot 애플리케이션 정상 실행
- 로그인 API를 통한 JWT 발급 확인

### 11.3 Docker 환경에서 PostgreSQL 연결 오류

**문제**

Docker 환경에서 Spring Boot 애플리케이션 실행 시 PostgreSQL 연결 설정이 정상적으로 적용되지 않아 데이터베이스 관련 오류가 발생했습니다.

**원인**

- Docker 환경에서 사용할 JDBC URL 설정 누락
- 데이터베이스 연결 정보를 확인하지 못하면서 Hibernate Dialect 관련 오류 발생
- 로컬 환경과 Docker 환경의 데이터베이스 연결 주소 차이

**해결**

- `application-docker.yaml`에 Docker 환경용 데이터베이스 연결 설정 추가
- JDBC URL을 `jdbc:postgresql://db:5432/cmms`로 설정
- Docker Compose의 서비스 이름인 `db`를 이용해 PostgreSQL 컨테이너에 연결
- `DB_PASSWORD` 환경변수를 이용해 데이터베이스 비밀번호 관리
- Spring Profile을 `local`, `docker`로 분리하여 실행 환경별 설정 관리

**결과**

- Spring Boot와 PostgreSQL 컨테이너 간 연결 정상 동작
- Docker Compose를 이용한 애플리케이션 실행 성공
- Docker 환경에서 회원가입, 로그인, 설비·점검·고장·정비 API 및 대시보드 조회 정상 동작 확인

---

## 12. 프로젝트를 통해 배운 점

### 12.1 도메인 중심의 비즈니스 로직 구현

설비, 점검, 고장, 정비 기능을 구현하면서 단순한 CRUD뿐만 아니라 도메인 간 관계와 업무 상태의 연계가 중요하다는 것을 배웠습니다.

특히 고장 등록과 정비 진행 및 완료 과정에서 설비 상태가 함께 변경되도록 구현하면서 비즈니스 규칙을 Service 계층에서 관리하는 경험을 쌓았습니다.

### 12.2 Spring Security와 JWT 인증 구조 이해

Spring Security와 JWT를 이용해 Stateless 인증 방식을 구현하면서 인증과 인가의 차이를 이해했습니다.

또한 사용자 역할에 따라 API 접근 권한을 구분하고, 인증 실패와 권한 부족을 각각 `401`, `403`으로 처리하는 방법을 익혔습니다.

### 12.3 API 설계 및 예외 처리 경험

DTO Validation과 전역 예외 처리를 적용하면서 API 요청값 검증과 일관된 오류 응답의 중요성을 배웠습니다.

검색, 필터링, 페이지네이션 및 정렬 기능을 구현하면서 데이터 조회 API의 활용성을 높이는 방법을 익혔습니다.

### 12.4 Docker 기반 실행 환경 구성 경험

Docker와 Docker Compose를 이용해 Spring Boot 애플리케이션과 PostgreSQL을 컨테이너 환경에서 실행했습니다.

이 과정에서 Spring Profile 분리, 환경변수 관리, 컨테이너 간 네트워크 연결 및 실행 오류 해결 과정을 경험했습니다.

### 12.5 프로젝트를 마무리하며

이번 프로젝트를 통해 Java와 Spring Boot를 활용한 REST API 설계부터 데이터베이스 연동, 인증·인가, 비즈니스 로직 구현, Docker 실행 환경 구성까지 백엔드 개발의 전반적인 흐름을 경험했습니다.

특히 제조 설비 관리라는 도메인을 바탕으로 실제 업무 흐름을 고려한 상태 관리 로직을 구현하고, 개발 과정에서 발생한 오류를 분석하고 해결하는 경험을 쌓을 수 있었습니다.

향후에는 이번 프로젝트에서 학습한 내용을 바탕으로 백엔드 서비스의 안정성, 성능 및 유지보수성을 고려한 개발 역량을 발전시키고자 합니다.