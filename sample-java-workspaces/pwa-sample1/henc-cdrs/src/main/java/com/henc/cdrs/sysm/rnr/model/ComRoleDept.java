package com.henc.cdrs.sysm.rnr.model;

import java.util.List;
import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;

@Data
public class ComRoleDept extends IBSheetDomain{

    private String roleId;
    private String datRangCd;
    private String seq;
    private String deptCd;    
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;    
    private String deptNm;
    private String updtId;
    private List<ComRoleDept> comRoleDepts; 
	
}
