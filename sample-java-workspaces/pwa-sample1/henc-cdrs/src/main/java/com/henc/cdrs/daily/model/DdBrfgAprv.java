package com.henc.cdrs.daily.model;


import com.henc.dream.domain.Domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DdBrfgAprv extends Domain {

    private String deptCd;
    private String day;
    private Integer ordrNo;
    private Integer seq;
    private String empeNo;
    private String staCd;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date aprvDt;
    private String rsn;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updtDm;
    private String updtId;
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wrtrDm;
    private String wrtrId;

    public DdBrfgAprv(SaveDailyApprovalParams saveDailyApprovalParams) {
        this.deptCd = saveDailyApprovalParams.getDeptCd();
        this.day = saveDailyApprovalParams.getDay();
        this.ordrNo = saveDailyApprovalParams.getOrdrNo();
        this.staCd = saveDailyApprovalParams.getStaCd();
        this.aprvDt = saveDailyApprovalParams.getAprvDt();
        this.rsn = saveDailyApprovalParams.getRsn();
        this.seq = saveDailyApprovalParams.getSeq();
    }
}
