package com.henc.cdrs.mgmt.equipment.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class EquipmentDetl extends IBSheetDomain {

    private int eqipNo;
    private int sameEqipNo;
    private String bizhdofCd;

    private List<EquipmentDetl> equipmentDetls;
}