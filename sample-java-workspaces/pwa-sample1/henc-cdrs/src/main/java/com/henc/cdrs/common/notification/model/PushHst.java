package com.henc.cdrs.common.notification.model;

import com.henc.dream.domain.Domain;
import lombok.Data;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-10
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Data
public class PushHst extends Domain {

    private int pushHstNo;
    private String pushTitl;
    private String pushCntt;
    private String pushLink;
    private String pushStaCd;
    private String pushErrCntt;
    private String receiverId;
    private int deviceNo;
    private String token;
    private java.sql.Date updtDm;
    private String updtId;
    private java.sql.Date wrtrDm;
    private String wrtrId;


    /**
     * push 발송 업무 key
     */
    private String deptCd;
    private String day;
    private int orderNo;
    private String partnerNo;
    private String ctrcNo;

}
