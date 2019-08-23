package com.henc.cdrs.daily.model;

import com.henc.dream.domain.Domain;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Null;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SaveDailyApprovalParams extends Domain {

    private String deptCd;
    private String day;
    private Integer ordrNo;
    private String partnerNo;
    private String ctrcNo;
    private String staCd;

    private String rsn;
    private String empeNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date aprvDt;
    private String action;

    @Nullable
    private String lowestTempe;
    @Nullable
    private String highestTempe;
    @Nullable
    private String ptclMtr;
    @Nullable
    private String weatherCd;

    /**
     * DD_BRFG_APRV.SEQ
     */
    @Nullable
    private Integer seq;

    /**
     * DD_BRFG.CHG_NO, DD_BRFG_CHG_MTR.CHG_NO
     */
    @Nullable
    private Integer chgNo;

    /**
     * DD_BRFG.CHG_SEQ, DD_BRFG_CHG_MTR.CHG_SEQ
     */
    @Nullable
    private Integer chgSeq;

    public SaveDailyApprovalParams(DdBrfg master) {
        this.deptCd = master.getDeptCd();
        this.day = master.getDay();
        this.ordrNo = master.getOrdrNo();
        this.staCd = master.getStaCd();
        this.chgNo = master.getChgNo();
        this.chgSeq = master.getChgSeq();
    }

}
