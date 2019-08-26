package com.henc.cdrs.mgmt.constType.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class ConstType extends IBSheetDomain {
    private int cstkndNo;                    // seq -> CSTKND_NO ( 공종번호 )
    private int uprCstkndNo;                 // parentWorktypeSeq -> UPR_CSTKND_NO ( 상위 공종 번호 )
    private String bizhdofCd;                // dept -> BIZHDOF_CD ( 사업본부코드 )
    private String cstkndNm;                 // worktypeName -> CSTKND_NM ( 공종명 )
    private String description;              // X ( 설명 )
    private int sortNo;                      // sort -> SORT_NO ( 순서 )
    private String useYn;                    // X ( 사용여부 )
    private int levl;                        // X ( 단계 )

    private String deptCd;
    private String site;
    private int siteSeq;

    private List<ConstType> constTypes;
}