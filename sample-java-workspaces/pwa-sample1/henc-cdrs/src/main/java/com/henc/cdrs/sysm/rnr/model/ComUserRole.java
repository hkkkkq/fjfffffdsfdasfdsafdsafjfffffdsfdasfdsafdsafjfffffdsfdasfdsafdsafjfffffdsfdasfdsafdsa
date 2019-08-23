package com.henc.cdrs.sysm.rnr.model;

import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;

@Data
public class ComUserRole extends IBSheetDomain{

	private String userId;
	private String roleId;
	private String nmspcVal;
	private String mainYn;
	private String useYn;
	private String stDt;
	private String endDt;
	private String roleClsCd;
	private String datRangCd;
	private String datRangCdBtn;
    private String wrtrDm;
    private String wrtrId;
    private String updtDm;
    private String updtId;
	
}
