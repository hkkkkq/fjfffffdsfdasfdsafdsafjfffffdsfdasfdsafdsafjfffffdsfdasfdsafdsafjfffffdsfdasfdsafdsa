package com.henc.cdrs.partners.users.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class Users extends IBSheetDomain{
    private String userId;
    private String name;
    private String tlno;
    private String userDivCd;
    private String pushUseYn;
    private String useYn;
    private String coprcpNo;
    private String corpKor;

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

    private String telName;

    private String deptCd;
    private String basDeptYn;

    private List<Users> userses;
}