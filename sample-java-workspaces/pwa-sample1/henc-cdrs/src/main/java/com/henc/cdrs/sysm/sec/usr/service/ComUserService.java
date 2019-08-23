package com.henc.cdrs.sysm.sec.usr.service;

import com.henc.cdrs.common.service.DuplicateException;
import com.henc.cdrs.common.util.CommUtil;
import com.henc.cdrs.common.util.FileUtil;
import com.henc.cdrs.common.util.KISA_SHA256;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.model.UserSign;
import com.henc.cdrs.sysm.sec.auth.repository.AuthMapper;
import com.henc.cdrs.sysm.sec.auth.repository.UserSignMapper;
import com.henc.cdrs.sysm.sec.usr.model.ComUser;
import com.henc.cdrs.sysm.sec.usr.model.ComUserSearch;
import com.henc.cdrs.sysm.sec.usr.model.UserDept;
import com.henc.cdrs.sysm.sec.usr.repository.ComUserMapper;
import com.henc.cdrs.sysm.sec.usr.repository.UserDeptMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ComUserService {

    @Autowired
    ComUserMapper comUserMapper;

    @Autowired
    UserDeptMapper userDeptMapper;

    @Autowired
    UserSignMapper userSignMapper;

    @Autowired
    AuthMapper authMapper;


    public List<CamelCaseMap> getUserList(ComUserSearch comUserSearch) {
        return comUserMapper.getUserList(comUserSearch);
    }

    public int insertComUser(ComUser comUser) throws DuplicateException {
        String pwd = CommUtil.decode(comUser.getEncPwd());
        String encPwd = KISA_SHA256.encryptPasswordPortalStyle(pwd);
        comUser.setEncPwd(encPwd);

        if ("COPRCP".equals(comUser.getUserDivCd())) {
            comUser.setUserId(comUserMapper.makeUserIdForCoprcp());
        }

        if (userValidCheck(comUser) > 0) {
            String message = "중복된 사용자ID 입니다. [사용자ID:" + comUser.getUserId() + "]";
            if ("COPRCP".equals(comUser.getUserDivCd())) {
                message = "중복된 휴대폰 번호 입니다. [휴대폰 번호:" + comUser.getTlno() + "]";
            }
            throw new DuplicateException(message);
        }

        return comUserMapper.insertComUser(comUser);
    }

    public ComUser getComUser(String userId) {
        return comUserMapper.getComUser(userId);
    }

    public int updateComUser(ComUser comUser) {
        return comUserMapper.updateComUser(comUser);
    }

    public int updatePwd(ComUser comUser) {
        String pwd = CommUtil.decode(comUser.getEncPwd());
        String encPwd = KISA_SHA256.encryptPasswordPortalStyle(pwd);
        comUser.setEncPwd(encPwd);
        return comUserMapper.updatePwd(comUser);
    }

    public int userValidCheck(ComUser comUser) {
        return comUserMapper.userValidCheck(comUser);
    }

    public List<CamelCaseMap> getUserDeptList(ComUser comUser) {
        return userDeptMapper.getUserDeptList(comUser.getUserId());
    }

    public void saveUserDeptList(List<UserDept> userDepts, String userId) {
        if (CollectionUtils.isNotEmpty(userDepts)) {
            for (UserDept userDept : userDepts) {
                userDept.setUserId(userId);
                switch (userDept.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        userDeptMapper.insertUserDept(userDept);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        userDeptMapper.updateUserDept(userDept);
                        break;
                    case IBSheetRowStatus.DELETED:
                        userDeptMapper.deleteUserDept(userDept);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(userDept.getRowStatus());
                }
            }
        }
    }

}
