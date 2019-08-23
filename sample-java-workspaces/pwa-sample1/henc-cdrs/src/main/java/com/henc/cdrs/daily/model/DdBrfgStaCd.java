package com.henc.cdrs.daily.model;

/**
 * 일일 보고 상태 코드 Enum.
 */
public enum DdBrfgStaCd {
    DRAFT("10", "작성"),
    APPROVAL("20", "상신"),
    REJECT("40", "반려"),
    APPROVE("50", "승인"),
    CHANGE("60", "변경작성"),
    HOLD("61", "변경작성(Hold)");

    private final String value;
    private String name;

    DdBrfgStaCd(String value) {
        this.value = value;
    }

    DdBrfgStaCd(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
