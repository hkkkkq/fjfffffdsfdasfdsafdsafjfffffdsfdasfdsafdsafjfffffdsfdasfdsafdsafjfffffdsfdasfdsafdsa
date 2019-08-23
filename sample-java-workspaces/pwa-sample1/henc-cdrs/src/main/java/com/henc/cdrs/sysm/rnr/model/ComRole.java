package com.henc.cdrs.sysm.rnr.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class ComRole extends IBSheetDomain{

    private String roleId;     
    private String menuId;
    private String pgmId;
    private String pgmNm;
    private String uriNm;
    private String nmspcCd;
    private String nmspcVal;
    private String roleClsCd;
    private String roleNote;
    private String datRangCd;
    private String useYn;
    private String wrtrDm;
    private String wrtrId;
    private String updtDm;
    private String updtId;
    private List<ComRole> comRoles;
	
}
