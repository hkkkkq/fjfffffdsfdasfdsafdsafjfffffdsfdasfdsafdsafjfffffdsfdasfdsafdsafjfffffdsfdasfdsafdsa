package com.henc.cdrs.daily.model;

/**
 * 일일 보고 회사 상태 코드 Enum.
 */
public enum DdBrfgCompStaCd {
    NONE("00", "미작성"),
    DRAFT("10", "작성"),
    APPROVAL("20", "제출"),
    REJECT("40", "반려"),
    APPROVE("50", "승인");

    private final String value;
    private String name;

    DdBrfgCompStaCd(String value) {
        this.value = value;
    }

    DdBrfgCompStaCd(String value, String name) {
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
