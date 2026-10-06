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