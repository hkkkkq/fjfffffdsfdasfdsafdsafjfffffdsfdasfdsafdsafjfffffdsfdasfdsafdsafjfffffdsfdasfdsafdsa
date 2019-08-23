package com.henc.cdrs.sysm.sec.usr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.henc.cdrs.sysm.rnr.model.ComUserRole;
import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class ComUser extends IBSheetDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String name;
    private String tlno;
    private String userDivCd;
    private String pushUseYn;
    private String coprcpNo;

    private String pwd;
    private String encPwd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pwdExpiDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date stDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDt;
    private String lastConnTm;
    private String lastConnIp;

    private String pstnCd;
    private String pstnNm;
    private String deptCd;
    private String deptKoNm;
    private String deptNm;
    private String lastDeptCd;
    private String lastDeptNm;
    private String pstn;

    private List<ComUserRole> comUserRoles;

    private List<UserDept> userDepts;

}
