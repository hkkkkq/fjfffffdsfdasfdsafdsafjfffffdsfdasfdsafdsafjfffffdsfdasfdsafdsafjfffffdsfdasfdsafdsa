package com.henc.cdrs.sysm.stdcd.model;

import java.math.BigDecimal;
import java.util.List;
import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;

@Data
public class AttributeDefi extends IBSheetDomain{

    private String stdCd;
    private BigDecimal attributeId;
    private String attributeNm;
    private String attributeType;
    private String attributeFrm;
    private BigDecimal attributeSeq;
    private String attributeDesc;
    private String wrtrId;
    private String updtId;
    private int cnt;
    private List<AttributeDefi> attributeDefis;
	
}
