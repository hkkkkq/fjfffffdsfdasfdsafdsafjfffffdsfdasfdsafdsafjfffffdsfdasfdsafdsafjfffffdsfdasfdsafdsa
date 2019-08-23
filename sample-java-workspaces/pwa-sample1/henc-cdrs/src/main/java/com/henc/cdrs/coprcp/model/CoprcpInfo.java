package com.henc.cdrs.coprcp.model;

import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class CoprcpInfo extends IBSheetDomain {

    private String deptCd;
    private String deptNm;

    private String coprcpNo;
    private String coprcpNm;

    private String ctrcNo;
    private String ctrcNm;
    private String ctrcDivCd;

    private String cnstTyp;
    private String cstkndNo;
    private String cstkndNm;

    private String useYn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date constructionStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date constructionEnd;

    private List<CoprcpInfo> coprcpInfos;
}
