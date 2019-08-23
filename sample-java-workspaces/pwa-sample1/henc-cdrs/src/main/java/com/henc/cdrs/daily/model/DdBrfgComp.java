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
 * 일일 보고 회사 모델
 */
@Getter
@Setter
@NoArgsConstructor
public class DdBrfgComp extends Domain implements Cloneable  {

    /**
     * 현장 코드
     */
    private String deptCd;

    /**
     * 일자
     */
    private String day;

    /**
     * 협력사 번호
     */
    private String partnerNo;

    /**
     * 차수 번호
     */
    @Nullable
    private Integer ordrNo;

    @Nullable
    private String ctrcNo;

    @Nullable
    private String ctrcNm;

    private String perddMainWork;
    private String nmddMainWork;
    private Integer sortNoSeq;
    private String objtYn;
    private String staCd;
    private String submitter;

    public DdBrfgComp(SearchDailyParams searchDailyParams) {
        this.deptCd = searchDailyParams.getDeptCd();
        this.day = searchDailyParams.getDay();
        this.ordrNo = searchDailyParams.getOrdrNo();
        this.partnerNo = searchDailyParams.getPartnerNo();
        this.ctrcNo = searchDailyParams.getCtrcNo();
        this.staCd = DdBrfgCompStaCd.DRAFT.getValue();
    }

    public DdBrfgComp(SaveDailyApprovalParams saveDailyApprovalParams) {
        this.deptCd = saveDailyApprovalParams.getDeptCd();
        this.day = saveDailyApprovalParams.getDay();
        this.ordrNo = saveDailyApprovalParams.getOrdrNo();
        this.partnerNo = saveDailyApprovalParams.getPartnerNo();
        this.ctrcNo = saveDailyApprovalParams.getCtrcNo();
        this.staCd = saveDailyApprovalParams.getStaCd();
    }

    public DdBrfgComp copy() throws CopyObjectException {
        try {
            return (DdBrfgComp) clone();
        } catch (CloneNotSupportedException e) {
            throw new CopyObjectException(e);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updtDm;
    private String updtId;
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wrtrDm;
    private String wrtrId;
}
