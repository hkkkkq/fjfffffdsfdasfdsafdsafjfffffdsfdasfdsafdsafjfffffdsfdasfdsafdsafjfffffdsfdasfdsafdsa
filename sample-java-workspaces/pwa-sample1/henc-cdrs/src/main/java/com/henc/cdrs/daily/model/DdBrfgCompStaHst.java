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
 * 일일 보고 회사 상태 이력 모델
 */
@Getter
@Setter
@NoArgsConstructor
public class DdBrfgCompStaHst extends Domain implements Cloneable {

    private String deptCd;
    private String day;
    private Integer ordrNo;
    private String partnerNo;
    private String ctrcNo;
    private Integer seq;

    @Nullable
    private String ctrcNm;
    private String bfStaCd;
    private String chgStaCd;
    private String bfUpdtId;
    private String chgUpdtId;
    private String chgUpdtName;
    private String chgRsn;

    public DdBrfgCompStaHst copy() throws CopyObjectException {
        try {
            return (DdBrfgCompStaHst) clone();
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

    public DdBrfgCompStaHst(DdBrfgComp comp, String chgRsn) {
        this.setSeq(0);
        this.setChgRsn(chgRsn);
        this.setDay(comp.getDay());
        this.setDeptCd(comp.getDeptCd());
        this.setOrdrNo(comp.getOrdrNo());
        this.setPartnerNo(comp.getPartnerNo());
        this.setCtrcNo(comp.getCtrcNo());
        this.setChgUpdtId(getLoginId());
        this.setChgStaCd(comp.getStaCd());
        this.setChgUpdtName(getLoginName());
    }

}
