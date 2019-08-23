package com.henc.cdrs.daily.model;

import com.henc.dream.domain.Domain;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
public class LabatdPcnt extends Domain {
    private Integer labatdPcntNo;
    private String deptCd;
    private String partnerNo;
    private String name;

    /**
     * 직종번호
     */
    private String ocptNo;

    /**
     * 생년월일
     */
    private String brtdy;

    private String note;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updtDm;
    private String updtId;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wrtrDm;
    private String wrtrId;
}
