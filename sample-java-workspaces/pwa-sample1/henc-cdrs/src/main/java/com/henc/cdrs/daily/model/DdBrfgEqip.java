package com.henc.cdrs.daily.model;

import com.henc.cdrs.exception.CopyObjectException;
import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * 일일 보고 장비 모델
 */
@Data
public class DdBrfgEqip extends IBSheetDomain implements Cloneable {
    private Integer rowId;
    private String deptCd;
    private String partnerNo;
    private String day;
    private Integer ordrNo;
    private String ctrcNo;

    @Nullable
    private String ctrcNm;

    private Integer eqipNo;
    private String eqipNm;
    private String spec;
    private String unit;
    private String bizhdofCd;
    private Integer qty;
    private Integer beforeSumQty;
    private Integer sumQty;

    public DdBrfgEqip copy() throws CopyObjectException {
        try {
            return (DdBrfgEqip) clone();
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
