package com.henc.cdrs.common.excel;

import com.henc.dream.exception.BaseException;

/**
 * 엑셀파일 생성 시 에러가 났음을 나타낸다.
 * @author 조용상
 * @version 1.0
 *
 * <pre>
 * 수정일                수정자         수정내용
 * ---------------------------------------------------------------------
 * 2014-11-24            조용상         최초작성
 * </pre>
 */
@SuppressWarnings("serial")
public class ExcelCreateException extends BaseException {
    public ExcelCreateException(String message) {
        super(message);
    }

    public ExcelCreateException(String message, Throwable exception) {
        super(message, exception);
    }
}