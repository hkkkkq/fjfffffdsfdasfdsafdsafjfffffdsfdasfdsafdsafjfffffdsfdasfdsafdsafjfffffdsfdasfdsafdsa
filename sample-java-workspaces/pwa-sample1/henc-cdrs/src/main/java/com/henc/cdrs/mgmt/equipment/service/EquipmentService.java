package com.henc.cdrs.mgmt.equipment.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.mgmt.equipment.model.Equipment;
import com.henc.cdrs.mgmt.equipment.model.EquipmentDetl;
import com.henc.cdrs.mgmt.equipment.repository.EquipmentMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

@Transactional
@Service
public class EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    public List<CamelCaseMap> selectEquipGrdList(Equipment equipment) {
        return equipmentMapper.selectEquipGrdList(equipment);
    }

    public void saveEquipmentList(List<Equipment> equipments, String bizhdofCd) {
        if (CollectionUtils.isNotEmpty(equipments)) {
            for (Equipment equipment : equipments) {
                equipment.setBizhdofCd(bizhdofCd);
                switch (equipment.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        equipmentMapper.insertEquipment(equipment);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        equipmentMapper.updateEquipment(equipment);
                        break;
                    case IBSheetRowStatus.DELETED:
                        equipment.setSameEqipCnt(equipmentMapper.selectEquipmentSameCount(equipment));
                        if(equipment.getSameEqipCnt() > 0 ){
                            equipmentMapper.updateEquipmentSameEquipNoRestore(equipment);
                        }
                        equipmentMapper.deleteEquipment(equipment);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(equipment.getRowStatus());
                }
            }
        }
    }

    public List<CamelCaseMap> selectEquipmentGrdDetlList(EquipmentDetl equipmentDetl) {
        return equipmentMapper.selectEquipmentGrdDetlList(equipmentDetl);
    }

    public void saveEquipmentDetlList(List<EquipmentDetl> equipmentDetls, String bizhdofCd) {
        if (CollectionUtils.isNotEmpty(equipmentDetls)) {
            for (EquipmentDetl equipmentDetl : equipmentDetls) {
                equipmentDetl.setBizhdofCd(bizhdofCd);
                switch (equipmentDetl.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        equipmentMapper.updateEquipmentDetail(equipmentDetl);
                        break;
                    case IBSheetRowStatus.DELETED:
                        equipmentMapper.updateEquipmentDetail(equipmentDetl);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(equipmentDetl.getRowStatus());
                }
            }
        }
    }

    public List<CamelCaseMap> selectEquipmentSynPoplList(Equipment equipment) {
        return equipmentMapper.selectEquipmentSynPoplList(equipment);
    }
}
