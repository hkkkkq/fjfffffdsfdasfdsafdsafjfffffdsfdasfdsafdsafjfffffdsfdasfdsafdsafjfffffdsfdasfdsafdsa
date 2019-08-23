package com.henc.cdrs.daily.model;

import com.henc.dream.domain.Domain;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * 일일 보고 주요 업무 설정
 */
@Data
public class MainBsnsSet extends Domain {

    private Integer ctrcNo;
    private String deptCd;
    private String aplyTerm;
    private String coprcpNo;
    @Nullable
    private String ctrcNm;
    private String sortNo;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updtDm;
    private String updtId;
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wrtrDm;
    private String wrtrId;
}
