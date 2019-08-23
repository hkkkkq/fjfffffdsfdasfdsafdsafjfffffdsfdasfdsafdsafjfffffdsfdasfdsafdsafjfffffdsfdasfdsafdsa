package com.henc.cdrs.sysm.com.uscd.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 공사관리 기준의 현장(부서)코드 (ACZ10100, COP030T) 모델
 */
@Data
public class ComDeptCd {
    /**
     * 부서코드
     */
    private String deptCd;

    /**
     * 부서명
     */
    private String deptNm;

    /**
     * 사업본부 코드
     */
    private String acUnitCd;

    /**
     * 진행상태(deadflg 1: 종료, 0: 진행)
     */
    private String status;

    private String useYn;

    /**
     * 시작일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date confrom;

    /**
     * 종료일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date conto;

    /**
     * 협력사 번호
     */
    private String coprcpNo;
}
