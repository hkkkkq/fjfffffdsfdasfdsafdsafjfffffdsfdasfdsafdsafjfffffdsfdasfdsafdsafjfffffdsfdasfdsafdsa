package com.henc.cdrs.daily.model;

import com.henc.dream.domain.Domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * 일일 보고 변경사항 모델
 */
@Getter
@Setter
@NoArgsConstructor
public class DdBrfgChgMtr extends Domain {

    private Integer chgNo;
    private Integer chgSeq;
    private String staCd;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updtDm;
    private String updtId;
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wrtrDm;
    private String wrtrId;

    private boolean isNewChgNo;

    public DdBrfgChgMtr(SaveDailyApprovalParams saveDailyApprovalParams) {
        this.chgNo = saveDailyApprovalParams.getChgNo();
        this.chgSeq = saveDailyApprovalParams.getChgSeq();
        this.staCd = saveDailyApprovalParams.getStaCd();
    }
}
