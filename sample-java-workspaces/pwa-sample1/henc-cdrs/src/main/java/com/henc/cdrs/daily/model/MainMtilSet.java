package com.henc.cdrs.daily.model;

import com.henc.dream.domain.Domain;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * 일일 보고 주요 자재 설정
 */
@Data
public class MainMtilSet extends Domain {
    private String deptCd;
    private String aplyTerm;
    private Integer mtilNo;
    private Integer sortNo;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updtDm;
    private String updtId;
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wrtrDm;
    private String wrtrId;

}
