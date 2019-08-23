package com.henc.cdrs.sysm.sec.usr.model;

import java.io.Serializable;
import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;

@Data
public class ComUserRoleDept extends IBSheetDomain implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String userId ;
	private String roleId ;
	private String useYn ;
	private String deptCd ;
	private String deptNm ;
	private String wrtrId ;
	private String wrtrDm ;
	private String updtId ;
	private String updtDm ;
}
