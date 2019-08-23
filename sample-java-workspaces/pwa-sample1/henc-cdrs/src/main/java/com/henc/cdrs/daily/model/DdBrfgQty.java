package com.henc.cdrs.daily.model;

import com.henc.cdrs.exception.CopyObjectException;
import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * 일일 보고 물량 모델
 */
@Data
public class DdBrfgQty extends IBSheetDomain implements Cloneable {
    private Integer rowId;
    private String deptCd;
    private String day;
    private Integer ordrNo;
    private String partnerNo;
    private String ctrcNo;
    @Nullable
    private String ctrcNm;

    private Integer qtyNo;
    private String qtyNm;
    private String spec;
    private String unit;
    private String useYn;

    private Integer planQty;
    private Integer perddQty;
    private Integer prgrPscd;
    private Integer beforeSumQty;
    private Integer sumQty;

    public DdBrfgQty copy() throws CopyObjectException {
        try {
            return (DdBrfgQty) clone();
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
