package com.henc.cdrs.daily.model;

import com.henc.dream.domain.Domain;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class SaveDailyParams extends Domain {

    private DdBrfgComp comp;

    private List<DdBrfgLabatd> labatds;

    private List<DdBrfgMtil> mtils;

    private List<DdBrfgEqip> eqips;

    @Nullable
    private List<DdBrfgQty> qties;

    @Nullable
    private Integer ctrcRenderingPosition;

    @Nullable
    private String chgRsn;

    @Nullable
    private String action;
}
