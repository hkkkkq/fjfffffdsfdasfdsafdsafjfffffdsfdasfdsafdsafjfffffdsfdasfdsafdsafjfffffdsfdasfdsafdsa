package com.henc.cdrs.mgmt.equipment.repository;

import com.henc.cdrs.mgmt.equipment.model.Equipment;
import com.henc.cdrs.mgmt.equipment.model.EquipmentDetl;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EquipmentMapper {

    List<CamelCaseMap> selectEquipGrdList(Equipment equipment);

    int insertEquipment(Equipment equipment);

    int updateEquipment(Equipment equipment);

    int deleteEquipment(Equipment equipment);

    List<CamelCaseMap> selectEquipmentGrdDetlList(EquipmentDetl equipmentDetl);

    int updateEquipmentDetail(EquipmentDetl equipmentDetl);

    List<CamelCaseMap> selectEquipmentSynPoplList(Equipment equipment);

    int updateEquipmentSameEquipNoRestore(Equipment equipment);

    int selectEquipmentSameCount(Equipment equipment);

    CamelCaseMap getEquipment(Equipment equipment);
}
