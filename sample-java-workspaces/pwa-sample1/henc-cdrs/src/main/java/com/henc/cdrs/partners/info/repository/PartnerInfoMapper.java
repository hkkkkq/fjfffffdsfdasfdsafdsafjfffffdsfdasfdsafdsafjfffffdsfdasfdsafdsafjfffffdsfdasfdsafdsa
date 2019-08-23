package com.henc.cdrs.partners.info.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.partners.info.model.Partner;
import com.henc.cdrs.partners.info.model.PrtnConInfo;
import com.henc.dream.util.CamelCaseMap;

@Mapper
public interface PartnerInfoMapper {

    List<CamelCaseMap> selectCoprcpInfoGrdList(Partner partner);

    List<CamelCaseMap> selectprtnConListPopup(PrtnConInfo prtnConInfo);

    int insertCoprcpInfo(Partner partner);

    int updateCoprcpInfo(Partner partner);

    int deleteCoprcpInfo(Partner partner);

    int selectDdBrfgCompCnt(Partner partner);
}
