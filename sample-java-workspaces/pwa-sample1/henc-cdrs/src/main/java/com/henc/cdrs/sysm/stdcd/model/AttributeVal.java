package com.henc.cdrs.sysm.stdcd.model;

import java.math.BigDecimal;
import java.util.List;
import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;

@Data
public class AttributeVal extends IBSheetDomain{
	
    private String stdCd;
    private String stdDetlCd;
    private BigDecimal attributeId;
    private String attributeVal;
    private String wrtrId;
    private String updtId;  
    private List<AttributeVal> attributeVals; 
}
