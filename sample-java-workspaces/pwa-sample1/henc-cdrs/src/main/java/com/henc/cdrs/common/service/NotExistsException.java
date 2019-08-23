package com.henc.cdrs.common.service;

import com.henc.dream.exception.BaseException;
import com.henc.dream.i18n.I18nMessage;

/**
 * 정보가 없을 경우 exception
 *
 * @author YongSang
 *
 */
@SuppressWarnings("serial")
public class NotExistsException extends BaseException {
    public NotExistsException(I18nMessage i18nMessage) {
        super(i18nMessage);
    }

    public NotExistsException(String message) {
        super(message);
    }
}