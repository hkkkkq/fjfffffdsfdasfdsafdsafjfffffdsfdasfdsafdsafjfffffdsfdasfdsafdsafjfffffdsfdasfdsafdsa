package com.henc.cdrs.common.notification.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-18
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 *
 * SMS table model
 */
@Data
public class ScTran {
    private int    trNum;
    private Date   trSenddate;
    private String trId;
    @Builder.Default
    private String trSendstat = "0";
    @Builder.Default
    private String trRsltstat = "00";
    @Builder.Default
    private String trMsgtype = "0";
    private String trPhone;
    private String trCallback;
    private Date   trRsltdate;
    private Date   trModified;
    private String trMsg;
    private String trNet;
    private String trEtc1;
    private String trEtc2;
    private String trEtc3;
    private String trEtc4;
    private String trEtc5;
    private String trEtc6;
    private Date   trRealsenddate;
    private String trRouteid;
}
