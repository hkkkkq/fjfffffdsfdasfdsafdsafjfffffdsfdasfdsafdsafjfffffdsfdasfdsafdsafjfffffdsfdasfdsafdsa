package com.henc.cdrs.common.service;

import com.henc.dream.exception.BaseException;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-04
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
public class UsedException extends BaseException {
    public UsedException(String message) {
        super(message);
    }
}
