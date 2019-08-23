package com.henc.cdrs.sysm.sec.auth.security.service;

import com.henc.cdrs.common.notification.service.NotificationService;
import com.henc.cdrs.common.util.KISA_SHA256;
import com.henc.cdrs.sysm.main.menu.repository.MenuMapper;
import com.henc.cdrs.sysm.sec.auth.model.CertNoSend;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.repository.AuthMapper;
import com.henc.cdrs.sysm.sec.auth.repository.CertNoSendMapper;
import com.henc.cdrs.sysm.sec.auth.security.dto.UserContext;
import com.henc.cdrs.sysm.sec.usr.repository.UserDeptMapper;
import com.henc.dream.util.CamelCaseMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Service
public class AuthenticationUsersService implements UserDetailsService {

    @Autowired
    AuthMapper authMapper;

    @Autowired
    UserDeptMapper userDeptMapper;

    @Autowired
    CertNoSendMapper certNoSendMapper;

    @Autowired
    NotificationService notificationService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        return loadUserByUsername(userId, null);
    }

    public UserDetails loadUserByUsername(String userId, String userNm) throws UsernameNotFoundException {
        UserDetail userDetail = authMapper.getComUserInfo(userId, "user_id", userNm);
        return userDetail;
    }

    public List<CamelCaseMap> getUserRoles(String username) {
        return authMapper.getUserRoles(username);
    }

    /**
     * 기본권한 조회
     *
     * @param roleId
     * @return
     */
    public CamelCaseMap getDefaultUserRole(String roleId) {
        return authMapper.getDefaultUserRole(roleId);
    }

    public void passWordUpdate(String userId, String encPwd) {
        authMapper.passWordUpdate(userId, encPwd);
    }

    public void changeExpiredPassword(String userId, String encPwd) {
        authMapper.changeExpiredPassword(userId, encPwd);
    }

    /**
     * 포탈 사용자 조회
     *
     * @param userId
     * @return
     */
    public UserDetails getPortalUserInfo(String userId, String userNm) {
        return authMapper.getPortalUserInfo(userId, userNm);
    }

    /**
     * 협력사 조회
     *
     * @param tlno
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails getCoprcpUserInfo(String tlno, String userNm) throws UsernameNotFoundException {
        return authMapper.getComUserInfo(tlno, "tlno", userNm);
    }

    /**
     * 로그인 대상 식별 및 기본데이터 셋업
     * @param userId
     * @return
     */
    public UserDetail whoAreYou(String userId) {

        return whoAreYou(userId, null);
    }

    /**
     * 로그인 대상 식별 및 기본데이터 셋업
     * @param userId, userNm
     * @return
     */
    public UserDetail whoAreYou(String userId, String userNm) {
        UserDetail userDetail = null;

        if (userId.length() > 8) {
            // 협력사 사용자 조회
            userDetail = (UserDetail) getCoprcpUserInfo(userId, userNm);
            if (userDetail != null) {
                userDetail.setLoginSource("CDRS");
            }

        } else {
            // 포탈 사용자 조회
            userDetail = (UserDetail) getPortalUserInfo(userId, userNm);
            if (userDetail != null) {
                userDetail.setLoginSource("PORTAL");
            }

            // COM_USER 사용자 조회
            if (userDetail == null) {
                userDetail = (UserDetail) loadUserByUsername(userId, userNm);
                if (userDetail != null) {
                    userDetail.setLoginSource("CDRS");
                }
            }
        }

        return userDetail;
    }

    /**
     * 인증 사용자 객체 생성
     * @param userDetail
     * @return
     */
    public UserContext createUserContext(UserDetail userDetail) {
        // 권한설정
        List<CamelCaseMap> userRoles = getUserRoles(userDetail.getUserId());
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if (userRoles != null && !userRoles.isEmpty()) {
            for (CamelCaseMap role : userRoles) {
                if (userDetail.getDeptCd()== null && "Y".equals(role.getString("basDeptYn"))) {
                    userDetail.setRoleId(role.getString("roleId"));
                    userDetail.setUserMenus(menuMapper.selectUserMenus(userDetail.getRoleId()));
//                    if (!"ROLE_ADMIN".equals(userDetail.getRoleId())) {
//                        userDetail.setUserMenus(menuMapper.selectUserMenus(userDetail.getRoleId()));
//                    }
                    authorities.add(new SimpleGrantedAuthority((String) role.get("roleId")));
                }

                if (userDetail.getDeptCd() != null && role.getString("deptCd").equals(userDetail.getDeptCd())) {
                    userDetail.setRoleId(role.getString("roleId"));
                    userDetail.setUserMenus(menuMapper.selectUserMenus(userDetail.getRoleId()));
//                    if (!"ROLE_ADMIN".equals(userDetail.getRoleId())) {
//                        userDetail.setUserMenus(menuMapper.selectUserMenus(userDetail.getRoleId()));
//                    }
                    authorities.add(new SimpleGrantedAuthority((String) role.get("roleId")));
                }
            }

            if ((userDetail.getRoleId()==null || "".equals(userDetail.getRoleId())) && userRoles.size() > 0) {
                userDetail.setRoleId(userRoles.get(0).getString("roleId"));
                userDetail.setUserMenus(menuMapper.selectUserMenus(userDetail.getRoleId()));
//                if (!"ROLE_ADMIN".equals(userDetail.getRoleId())) {
//                    userDetail.setUserMenus(menuMapper.selectUserMenus(userDetail.getRoleId()));
//                }
                authorities.add(new SimpleGrantedAuthority(userRoles.get(0).getString("roleId")));
            }

        } else {
            //권한이 없는 경우 ROLE_DEFAULT 권한으로 보여주기로 함.
            String defaultRoleId = "COPRCP".equals(userDetail.getUserDivCd()) ? "ROLE_DEFAULT" : "ROLE_ADMIN";
            userRoles.add(getDefaultUserRole(defaultRoleId));
            userDetail.setRoleId(defaultRoleId);
            userDetail.setUserMenus(menuMapper.selectUserMenus(userDetail.getRoleId()));
//            if (!"ROLE_ADMIN".equals(userDetail.getRoleId())) {
//                userDetail.setUserMenus(menuMapper.selectUserMenus(userDetail.getRoleId()));
//            }
            authorities.add(new SimpleGrantedAuthority(defaultRoleId));

            //throw new InsufficientAuthenticationException("User has no roles assigned");
        }

        List<CamelCaseMap> userDepts = new ArrayList<CamelCaseMap>();
        // 기본현장 설정
        if ("PORTAL".equals(userDetail.getLoginSource())) {
            userDepts = userDeptMapper.getHencDeptList(userDetail.getUserId());

        } else {
            userDepts = userDeptMapper.getUserDeptList(userDetail.getUserId());
        }

        // 포탈사용자의 경우 기본 현장이 없을 수 있기 때문에
        // 발령현장을 기본현장으로 설정한다.
        // 공사일보 사용자는 basDeptYn이 Y인 현장을 기본현장으로 사용
        String compareKey = "PORTAL".equals(userDetail.getLoginSource()) ? "A" : "Y";
        for (CamelCaseMap userDept : userDepts) {
            if (userDetail.getDeptCd() == null) {
                if ( compareKey.equals(userDept.get("basDeptYn"))) {
                    userDetail.setDeptCd(userDept.getString("deptCd"));
                    userDetail.setBizhdofCd(userDept.getString("bizhdofCd"));
                    break;
                }

            } else {
                if ( userDept.getString("deptCd").equals(userDetail.getDeptCd())) {
                    userDetail.setBizhdofCd(userDept.getString("bizhdofCd"));
                    break;
                }
            }
        }


        UserContext userContext = UserContext.create(userDetail.getUserId(), userDetail, (List<GrantedAuthority>) authorities);

        return userContext;
    }


    /**
     * 비밀번호 찾기 인증 번호 발송
     * @param userDetail
     */
    public CertNoSend certNoSend(UserDetail userDetail){
        CertNoSend certNoSend = new CertNoSend();
        /**
         * 1. 인증 번호 생성 및 저장
         * 2. 인증 번호 sms 전송
         * 3. return
         */
        certNoSend.setUserId(userDetail.getUserId());
        certNoSend.setTlno(userDetail.getTlno());
        certNoSend.setCertNo(RandomStringUtils.randomNumeric(6));

        certNoSendMapper.insertCertNoSend(certNoSend);

        String [] arg = {certNoSend.getCertNo()};
        String message = messageSource.getMessage("sms.auth.certmsg", arg, Locale.getDefault());

        notificationService.smsSend(userDetail, userDetail.getTlno(), message);

        return certNoSend;
    }

    public CamelCaseMap checkCertNo(String certNo, String userId){
        /**
         * 1. 인증번호, id로 존재 유무 및 발송시간 비교
         * 2. 신규 비밀번호 생성 및 업데이트
         * 3.
         */
        CamelCaseMap cm = new CamelCaseMap();
        List<CertNoSend> certNoSends = certNoSendMapper.selectCertNoSend(certNo, userId);

        if(certNoSends.size() <= 0){
            cm.put("result_code", "SE");
            cm.put("result_msg", "인증번호가 일치하지 않습니다.");
            return cm;
        }

        CertNoSend certNoSend = certNoSends.get(certNoSends.size()-1);

        if(!certNo.equals(certNoSend.getCertNo())){
            cm.put("result_code", "LE");
            cm.put("result_msg", "인증번호가 일치하지 않습니다.");
            return cm;
        }

        String newPwd = RandomStringUtils.randomAlphanumeric(12);
        String encPwd = KISA_SHA256.encryptPasswordPortalStyle(newPwd);

        //패스워드 변경
        authMapper.changeExpiredPassword(userId, encPwd);
        cm.put("result_code", "S");
        cm.put("result_msg", "");
        cm.put("new_pwd", newPwd);
        return cm;
    }


    /**
     * 비밀번호 오류 카운트 업데이트
     */
    public int failCntUpdate(String userId){
       return authMapper.failCntUpdate(userId);
    }

}
