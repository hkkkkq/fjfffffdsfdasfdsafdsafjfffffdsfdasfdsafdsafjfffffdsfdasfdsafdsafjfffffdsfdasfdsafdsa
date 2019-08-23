package com.henc.cdrs.partnerConfirm.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.partnerConfirm.model.PartnerConfirm;
import com.henc.dream.util.CamelCaseMap;

@Mapper
public interface PartnerConfirmMapper {

    List<CamelCaseMap> selectPartnerInfo(PartnerConfirm partnerConfirm);

    int updatePartnerInfo(PartnerConfirm partnerConfirm);

    List<CamelCaseMap> selectPartnerDept(PartnerConfirm partnerConfirm);

    int updatePartnerDept(PartnerConfirm partnerConfirm);
}
