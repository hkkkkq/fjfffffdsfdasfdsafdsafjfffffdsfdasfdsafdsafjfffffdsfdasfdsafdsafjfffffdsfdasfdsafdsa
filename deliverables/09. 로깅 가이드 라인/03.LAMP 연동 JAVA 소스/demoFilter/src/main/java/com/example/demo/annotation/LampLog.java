package com.example.demo.annotation;


public @interface LampLog {
    Type[] value() default {Type.IN_REQ, Type.IN_RES};
    Operation operation() default Operation.NOT_AVAILABLE;        //표준 로그 오퍼레이션 코드 (기본 N/A)
    ResponseType responseType() default ResponseType.INFORMATION; //응답로그 유형 (기본값 : Information)
    SecureType secureType() default SecureType.NONE;              // 보안로그 유형 (기본값 : None)
    SecureEventType secureEventType() default SecureEventType.READ; //보안로그 이벤트 유형 (기본값 : 읽기 및 조회)


    /**
     * 표준 로그 오퍼레이션 명ㅣ
     */
    enum Operation {
        /**
         * 로그인
         */
        LOGIN("login"),
        /**
         * 2차 인증
         */
        TWO_FACTOR_AUTHENTICATION("2FA"),
        /**
         * 로그아웃
         */
        LOGOUT("logout"),
        /**
         * 사용자 권한신청
         */
        AUTH_REQUEST("authRequest"),
        /**
         * 대시보드 저장 & 삭제
         */
        DASHBOARD("dashBoard"),
        /**
         * 위젓 저장 & 삭제
         */
        WIDGET("widget"),
        /**
         * 사용자 권한승인
         */
        USER_AUTH("userAuth"),
        /**
         * 사용자 정보조회
         */
        USER_INFO("userInfo"),
        /**
         * 사용자 목록조회
         */
        USER_LIST("userList"),
        /**
         * 로그 조회
         */
        LOG("log"),
        /**
         * 접속 로그조회
         */
        ACCESS_LOG("accessLog"),
        /**
         * 개인정보처리 로그조회
         */
        PRIVATE_LOG("privateLog"),
        /**
         * 개인정보취급자관리 로그조회
         */
        MANAGEMENT_LOG("managementLog"),
        /**
         * 사용자 이력 조회
         */
        LOGIN_LOG("loginLog"),
        /**
         * 모니터링 - 호스트 조회
         */
        MONITOR_HOST("monitorHost"),
        /**
         * 모니터링 - 기능 조회
         */
        MONITOR_OPERATION("monitorOperation"),
        /**
         * 모니터링 - 연동 조회
         */
        MONITOR_DEST("monitorDest"),
        /**
         * 모니터링 - 에러 조회
         */
        MONITOR_ERROR("monitorError"),
        /**
         * 모니터링 - 채널 조회
         */
        MONITOR_CHANNEL("monitorChannel"),
        /**
         * 통계 - 호스트 조회
         */
        STATISTICS_HOST("statisticsHost"),
        /**
         * 통계 - 기능 조회
         */
        STATISTICS_OPERATION("statisticsOperation"),
        /**
         * 통계 - 연동 조회
         */
        STATISTICS_DEST("statisticsDest"),
        /**
         * 통계 - 에러 조회
         */
        STATISTICS_ERROR("statisticsError"),
        /**
         * 통계 - 채널 조회
         */
        STATISTICS_CHANNEL("statisticsChannel"),
        /**
         * 메타데이터 - 호스트 등록/삭제
         */
        HOST("host"),
        /**
         * 메타데이터 - 기능 등록/삭제
         */
        OPERATION("operation"),
        /**
         * 메타데이터 - 채널 등록/삭제
         */
        CHANNEL("channel"),
        /**
         * 메타데이터 - 에러 등록/삭제
         */
        ERROR("error"),
        /**
         * 메타데이터 - 연동 등록/삭제
         */
        DEST("dest"),
        /**
         * 엑셀 VIEW
         */
        EXCEL("excel"),
        /**
         * AP MONITOR
         */
        AP_MONITOR("systemMonitor"),
        /**
         * 내부에러
         */
        EXCEPTION("exception"),
        /**
         * N/A
         */
        NOT_AVAILABLE("N/A");

        private String name;

        Operation(String name){
            this.name = name;
        }

        public String value() {
            return name;
        }
    }

    /**
     * 표준 로그 유형
     */
    enum Type {
        /**
         * 오퍼레이션에 요청이 들어온 경우(전처리)
         */
        IN_REQ,
        /**
         * 요청에 대한 응답처리(후처리)
         */
        IN_RES,
        /**
         * 외부 특정서비스 요청 전처리
         */
        OUT_REQ,
        /**
         * 외부 특정서비스 요청에 대한 응답처리(후처리)
         */
        OUT_RES,
        /**
         * REQ, RES 쌍 없이, 에러 또는 알림 로그를 발생시켜야 하는 경우 별도 트랜젝션 그룹핑 과정없이 1개의 트랜젝션 로그처리
         */
        NOTIFY,
        /**
         * REQ, RES 쌍 없이, 비동기 작업을 하는 경우 별도 트랜젝션 그룹핑 과정없이 1개의 트랜젝션 로그처리
         */
        ASYNC,
        /**
         * 요청에 대한 REQ, RES 의 쌍을 한개의 로그로 처리하는 경우
         */
        IN_MSG,
        /**
         * 응답에 대한 REQ, RES 의 쌍을 한개의 로그로 처리하는 경우
         */
        OUT_MSG,
    }

    /**
     * 보안로그 유형
     */
    enum SecureType {
        /**
         * 사용자접속로그
         */
        ACCESS,
        /**
         * 개인정보처리로그
         */
        //PRIV,
        PRCS,
        /**
         * 개인정보취급자관리로그
         */
        MNGT,
        /**
         * 해당없음
         */
        NONE
    }

    /**
     * 보안로그 이벤트 유형
     */
    enum SecureEventType {
        /**
         * 로그인
         */
        LOGIN,
        /**
         * 로그아웃
         */
        LOGOUT,
        /**
         * 읽기
         */
        READ,
        /**
         * 업데이트
         */
        UPDATE,
        /**
         * 자료반출
         */
        EXPORT,
        /**
         * 생성
         */
        CREATE,
        /**
         * 삭제
         */
        DELETE
    }

    /**
     * 표준 로그 응답유형
     */
    enum ResponseType {
        /**
         * 시스템 에러
         */
        SYSTEM_ERROR("S"),
        /**
         * 비즈니스 에러
         */
        BUSINESS_ERROR("E"),
        /**
         * INFORMATION
         */
        INFORMATION("I");

        private String value;
        ResponseType(String value){
            this.value = value;
        }
        public String value() {
            return value;
        }
    }
}
