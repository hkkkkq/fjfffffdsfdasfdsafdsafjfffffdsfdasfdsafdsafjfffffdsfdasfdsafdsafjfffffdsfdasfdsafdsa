package com.henc.cdrs.exception;

import com.henc.dream.exception.BaseException;

/**
 * 설정된 디폴트 옵션이 없음을 나타낸다.
 *
 * @author YongSang
 */
@SuppressWarnings("serial")
public class NoDefaultOptionException extends BaseException {
    public NoDefaultOptionException(String message) {
        super(message);
    }
}