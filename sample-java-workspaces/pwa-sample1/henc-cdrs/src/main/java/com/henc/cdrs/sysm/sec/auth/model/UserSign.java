package com.henc.cdrs.sysm.sec.auth.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-25
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Data
public class UserSign {
    private String userId;
    private String fileDataUrl;
    private String bas64FileDataUrl;
    private byte [] sign;
    private MultipartFile signSelect;
}
