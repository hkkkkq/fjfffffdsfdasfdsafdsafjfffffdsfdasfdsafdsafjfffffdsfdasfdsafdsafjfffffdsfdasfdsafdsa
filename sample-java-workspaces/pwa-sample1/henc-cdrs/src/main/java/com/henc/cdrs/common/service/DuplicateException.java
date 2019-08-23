package com.henc.cdrs.common.service;

import com.henc.dream.exception.BaseException;

/**
 * 중복 exception
 *
 * @author YongSang
 */
@SuppressWarnings("serial")
public class DuplicateException extends BaseException {
    public DuplicateException(String message) {
        super(message);
    }
}