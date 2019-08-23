package com.henc.cdrs.daily.model;

import com.henc.dream.domain.Domain;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 현장담당자에 의해 일보 공통사항과 협력업체 일보 제출여부 체크항목을 저장처리 하기 위한 파라미터 모델.
 */
@Data
public class SaveDailyFieldMasterParams extends Domain {

    private DdBrfg master;

    @Nullable
    private List<DdBrfgComp> comps;

    private String partnerNo;

    @Nullable
    private String action;
}
