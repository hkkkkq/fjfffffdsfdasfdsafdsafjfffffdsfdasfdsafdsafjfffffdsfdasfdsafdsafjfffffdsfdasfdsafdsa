package com.henc.cdrs.common.notification.model;

import com.henc.dream.domain.Domain;
import lombok.Data;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-11
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Data
public class MmsMsg extends Domain {

    private int msgkey;
    /*필수 항목*/
    private String subject;
    /*필수 항목*/
    private String phone;
    /*필수 항목*/
    private String callback;
    /*필수 항목*/
    private String status;
    private String reqdate;
    /*필수 항목*/
    private String msg;
    private String fileCnt;
    private String fileCntReal;
    private String filePath1;
    private String filePath1Siz;
    private String filePath2;
    private String filePath2Siz;
    private String filePath3;
    private String filePath3Siz;
    private String filePath4;
    private String filePath4Siz;
    private String filePath5;
    private String filePath5Siz;
    private String expiretime;
    private String sentdate;
    private String rsltdate;
    private String reportdate;
    private String terminateddate;
    private String rslt;
    /*필수 항목*/
    private String type;
    private String telcoinfo;
    private String routeId;
    /*필수 항목*/
    private String id;
    /*필수 항목*/
    private String post;
    private String etc1;
    private String etc2;
    private String etc3;
    private String etc4;


}
