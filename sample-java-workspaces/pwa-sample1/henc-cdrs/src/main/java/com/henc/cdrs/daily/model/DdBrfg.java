package com.henc.cdrs.daily.model;

import com.henc.cdrs.exception.CopyObjectException;
import com.henc.dream.domain.Domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * 일일 보고 모델
 */
@Getter
@Setter
@NoArgsConstructor
public class DdBrfg extends Domain implements Cloneable {

    private String deptCd;

    @Nullable
    private String day;
    @Nullable
    private Integer ordrNo;

    @Nullable
    private Integer chgNo;

    @Nullable
    private Integer chgSeq;

    @Nullable
    private String staCd;

    private String lowestTempe;
    private String highestTempe;
    private String ptclMtr;
    private String weatherCd;
    private String weatherNm;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updtDm;
    private String updtId;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wrtrDm;
    private String wrtrId;

    public DdBrfg(SaveDailyApprovalParams saveDailyApprovalParams) {
        this.deptCd = saveDailyApprovalParams.getDeptCd();
        this.day = saveDailyApprovalParams.getDay();
        this.ordrNo = saveDailyApprovalParams.getOrdrNo();
        this.staCd = saveDailyApprovalParams.getStaCd();
        this.lowestTempe = saveDailyApprovalParams.getLowestTempe();
        this.highestTempe = saveDailyApprovalParams.getHighestTempe();
        this.ptclMtr = saveDailyApprovalParams.getPtclMtr();
        this.weatherCd = saveDailyApprovalParams.getWeatherCd();
    }

    public DdBrfg copy() throws CopyObjectException {
        try {
            return (DdBrfg) clone();
        } catch (CloneNotSupportedException e) {
            throw new CopyObjectException(e);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
