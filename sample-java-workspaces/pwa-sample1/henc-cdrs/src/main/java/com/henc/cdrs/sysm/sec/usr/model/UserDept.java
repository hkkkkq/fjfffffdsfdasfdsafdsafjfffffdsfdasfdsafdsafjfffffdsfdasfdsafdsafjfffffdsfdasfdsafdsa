package com.henc.cdrs.sysm.sec.usr.model;

import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;

import java.util.List;

@Data
public class UserDept extends IBSheetDomain {

    private String userId;
    private String deptCd;
    private String deptNm;
    private String roleId;
    private String basDeptYn;

    private List<UserDept> userDepts;

}
