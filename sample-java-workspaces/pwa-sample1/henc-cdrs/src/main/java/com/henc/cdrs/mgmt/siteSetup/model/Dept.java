package com.henc.cdrs.mgmt.siteSetup.model;

import com.henc.dream.domain.IBSheetDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Dept extends IBSheetDomain {
    private String deptCd;
    private String deptNm;
    private String bizhdofCd;
    private String bizhdofNm;
    private String useYn;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date confrom;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date conto;

    private List<Dept> depts;
}
