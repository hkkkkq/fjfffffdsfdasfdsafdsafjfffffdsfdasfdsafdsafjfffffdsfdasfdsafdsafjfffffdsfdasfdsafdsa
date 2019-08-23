package com.henc.cdrs.sysm.sec.usr.repository;

import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.usr.model.ComUser;
import com.henc.cdrs.sysm.sec.usr.model.ComUserSearch;
import com.henc.cdrs.sysm.sec.usr.model.UserDept;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ComUserMapper {

    List<CamelCaseMap> getUserList(ComUserSearch comUserSearch);

    int insertComUser(ComUser comUser);

    ComUser getComUser(String userId);

    /**
     * 협력사 사용자ID Sequence 채번
     * mybatis 에서 insert 후 userId를 리턴해야 하는데
     * choose 문 안에서 채번을 할 수 없기 때문에
     * service에서 직접 호출하여 userId를 전달하기 위해서 사용한다.
     * @return
     */
    String makeUserIdForCoprcp();

    int updateComUser(ComUser comUser);

    int updatePwd(ComUser comUser);

    /**
     * 사용자ID 중복 체크.
     * 협력사의 경우 로그인 ID는 휴대폰 번호(tlno)로 하기 때문에 tlno 조건을 찾고,
     * 건설 임직원 및 관리자는 사번(사용자ID)으로 조회 하여 중복을 확인한다.
     * @param comUser
     * @return
     */
    int userValidCheck(ComUser comUser);

    int updatePushUse(UserDetail userDetail);

}
