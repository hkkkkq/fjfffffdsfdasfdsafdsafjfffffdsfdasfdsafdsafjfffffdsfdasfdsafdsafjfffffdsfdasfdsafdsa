package com.henc.cdrs.sysm.sec.usr.model;

import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;

@Data
public class ComUserChgMenuBtn extends IBSheetDomain {
	private String userId;
    private String roleId;
    private String menuId;
    private String pgmId;
    private String btnId;
    private String userBtnUseYn;
    private String wrtrDm;
    private String wrtrId;
    private String updtDm;
    private String updtId;
}
