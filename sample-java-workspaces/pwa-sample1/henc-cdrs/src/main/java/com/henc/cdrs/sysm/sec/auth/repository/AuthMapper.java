package com.henc.cdrs.sysm.sec.auth.repository;

import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 사용자 인증
 */
@Mapper
public interface AuthMapper {

    /**
     * 포털 기준 사용자 조회
     * @param userId
     * @return
     */
    UserDetail getPortalUserInfo(@Param("userId") String userId, @Param("userNm") String userNm);

    /**
     * COM 사용자 정보 조회
     * (COM_USER테이블에 등록된 사용자를 기준으로 조회)
     * @param userId
     * @param field 비교필드(user_id, tlno:협력사일 경우 전화번호 로그인 시도)
     * @param userNm
     * @return
     */
    UserDetail getComUserInfo(@Param("userId") String userId, @Param("field") String field, @Param("userNm") String userNm);

    List<CamelCaseMap> getUserRoles(String userId);

    CamelCaseMap getDefaultUserRole(@Param("roleId") String roleId);

    void passWordUpdate(@Param("userId") String userId, @Param("encPwd") String encPwd);

    void changeExpiredPassword(@Param("userId") String userId, @Param("encPwd") String encPwd);

    int failCntUpdate(@Param("userId") String userId);

}
