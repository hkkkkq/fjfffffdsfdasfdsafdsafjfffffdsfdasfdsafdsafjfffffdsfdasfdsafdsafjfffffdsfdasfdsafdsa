package com.henc.cdrs.sysm.stdcd.model;

import java.util.List;
import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;

@Data
public class StdDetlCd extends IBSheetDomain{

    private String stdCd;
    private String stdDetlCd;
    private String nmspcCd;
    private String nmspcVal; 
    private String detlCdNote;
    private Integer detlCdSeq;
    private String userDefiCd;
    private String useYn ;
    private List<String> includeCodeList;
    private List<String> excludeCodeList;
    
    private Integer attrId;
    private String attrVal;
    private List<StdDetlCd> StdDetlCds; 
	
}
