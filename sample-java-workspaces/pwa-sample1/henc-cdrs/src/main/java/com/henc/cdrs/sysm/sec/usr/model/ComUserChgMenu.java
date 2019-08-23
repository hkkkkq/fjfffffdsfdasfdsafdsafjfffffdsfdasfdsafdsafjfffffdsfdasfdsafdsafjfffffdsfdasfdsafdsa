package com.henc.cdrs.sysm.sec.usr.model;

import java.util.List;

import com.henc.dream.domain.Domain;
import lombok.Data;

@Data
public class ComUserChgMenu extends Domain {
	private String userId;
    private String roleId;
    private String menuId;
    private String chgMenuUseYn;
    private String chgMenuSeq;
    private String chgMenuClsCd;
    private String chgNmspcCd;
    private String chgUprMenuId;
    private String userDefineUseYn;
    private String menuNmspcCd;
    private String wrtrDm;
    private String wrtrId;
    private String updtDm;
    private String updtId;
    private List<ComUserChgMenuBtn> comUserChgMenuBtns; 
	
}
