package com.henc.cdrs.common.code.model;

import java.util.List;

import javax.validation.Valid;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class ComMenuPgmBtn  extends IBSheetDomain{

    private String menuId;
    private String roleId;
    private String pgmId;
    private String btnId;
    private String btnGrpId;
    private int btnSeq;
    private String btnUseYn;
    private String pgmSaveMode;
    
    @Valid
    private List<ComMenuPgmBtn> list; 
	
}
