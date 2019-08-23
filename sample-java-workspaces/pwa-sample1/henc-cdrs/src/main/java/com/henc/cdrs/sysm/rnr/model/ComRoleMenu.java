package com.henc.cdrs.sysm.rnr.model;

import lombok.Data;

@Data
public class ComRoleMenu {

    private String roleId;
    private String menuId;
    private String uprMenuId;
    private String menuClsCd;
    private int menuSeq;
    private String nmspcCd;
    private String pgmId;
    private String pgmNm;
    private String uriNm;
    private String useYn;  
    private String pgmSaveMode;
	
}
