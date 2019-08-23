package com.henc.cdrs.common.notification.model;

import com.henc.dream.domain.Domain;
import lombok.Data;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-01
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Data
public class Device extends Domain {

    private int deviceNo;
    private String token;
    private String recvYn;
    private String userId;
    private java.sql.Date updtDm;
    private String updtId;
    private java.sql.Date wrtrDm;
    private String wrtrId;
}
