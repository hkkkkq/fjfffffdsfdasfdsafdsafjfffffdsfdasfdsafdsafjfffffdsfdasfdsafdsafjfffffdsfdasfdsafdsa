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
public class DdBrfgStaHst extends Domain {

    private String deptCd;
    private String day;
    private Integer ordrNo;
    private Integer seq;

    private String bfStaCd;
    private String chgStaCd;
    private String bfUpdtId;
    private String chgUpdtId;
    private String chgUpdtName;
    private String chgRsn;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updtDm;
    private String updtId;
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wrtrDm;
    private String wrtrId;

    public DdBrfgStaHst(DdBrfg master, String chgRsn) {
        this.setSeq(0);
        this.setChgRsn(chgRsn);
        this.setDay(master.getDay());
        this.setDeptCd(master.getDeptCd());
        this.setOrdrNo(master.getOrdrNo());
        this.setChgUpdtId(getLoginId());
        this.setChgStaCd(master.getStaCd());
        this.setChgUpdtName(getLoginName());
    }
}
