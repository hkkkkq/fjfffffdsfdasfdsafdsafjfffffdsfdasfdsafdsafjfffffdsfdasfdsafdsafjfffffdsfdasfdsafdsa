package com.henc.dream.domain;

import lombok.Data;

/**
 * IBSheet의 삭제 row, 상태 등을 식별.
 *
 * @author YongSang
 */
@Data
public class IBSheetDomain extends Domain {
    private Boolean rowChecked;
    private String rowStatus;
}