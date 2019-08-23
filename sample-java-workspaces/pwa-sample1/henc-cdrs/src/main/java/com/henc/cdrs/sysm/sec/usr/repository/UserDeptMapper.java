package com.henc.cdrs.sysm.sec.usr.repository;

import com.henc.cdrs.sysm.sec.usr.model.UserDept;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 사용자 현장
 */
@Mapper
public interface UserDeptMapper {

    List<CamelCaseMap> getUserDeptList(@Param("userId") String userId);

    int insertUserDept(UserDept userDept);

    int updateUserDept(UserDept userDept);

    int deleteUserDept(UserDept userDept);

    /**
     * 한화건설 임직원의 경우
     * 공사일보 시스템에서 사용자 등록과 현장등록이 안된 경우가 존재하므로
     * 공사관리 기준의 발령기준 현장 목록과
     * 공사일보 USER_DEPT 현장을 UNION 하여 조회한다.
     * 주의사항: bas_dept_yn 값은 USER_DEPT에만 존재하게되는데,
     * 조회된 현장 중에서 bas_dept_yn 값이 'Y' 가 없으면
     * bas_dept_yn 이 'A' 인 현장을 'Y'로 판단하면 된다.
     * @param comUser
     * @return
     */
    List<CamelCaseMap> getHencDeptList(@Param("userId") String userId);

    int resetBasDeptYn(UserDept userDept);
    int updateBasDeptYn(UserDept userDept);

}
