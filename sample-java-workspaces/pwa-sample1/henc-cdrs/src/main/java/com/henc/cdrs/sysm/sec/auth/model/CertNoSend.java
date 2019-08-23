package com.henc.cdrs.sysm.sec.auth.model;

import lombok.Data;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-18
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Data
public class CertNoSend {

    private int seq;
    private String userId;
    private String certNo;
    private java.sql.Date sendTm;

    private String tlno;

    private java.sql.Date wrtrDm;
    private String wrtrId;
}
