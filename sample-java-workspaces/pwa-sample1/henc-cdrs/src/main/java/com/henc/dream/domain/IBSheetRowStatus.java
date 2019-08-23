package com.henc.dream.domain;

/**
 * IBSheet의 row 상태
 *
 * @author YongSang
 */
public class IBSheetRowStatus {
    /**
     * 상태 없음
     */
    public static final String NORMAL = "";

    /**
     * 추가된 row
     */
    public static final String INSERTED = "I";

    /**
     * 수정된 row
     */
    public static final String UPDATED = "U";

    /**
     * 삭제된 row
     */
    public static final String DELETED = "D";
}