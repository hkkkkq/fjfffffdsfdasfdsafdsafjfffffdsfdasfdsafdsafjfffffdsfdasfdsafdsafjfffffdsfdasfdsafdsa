package com.henc.cdrs.coprcp.model;

import com.henc.dream.domain.Domain;
import lombok.Data;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-12
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Data
public class RegReqSend extends Domain {

    /*key*/
    private String sendId;
    /*사용할 현장 코드*/
    private String deptCd;
    /*추가할 협력사 번호*/
    private String coprcpNo;
    /*추가할 사용자 id*/
    private String userId;
    /*RegReqSend 유효 기한*/
    private java.sql.Date expiTm;

    /*수신자 전화번호*/
    private String tlno;

    private java.sql.Date wrtrDm;
    private String wrtrId;

}
