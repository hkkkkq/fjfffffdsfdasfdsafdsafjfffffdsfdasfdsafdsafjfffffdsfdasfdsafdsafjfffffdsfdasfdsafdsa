package com.henc.cdrs.mgmt.dailySetup.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.mgmt.dailySetup.model.DsCnstQty;
import com.henc.cdrs.mgmt.dailySetup.model.DsCopyParams;
import com.henc.cdrs.mgmt.dailySetup.model.DsEquip;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainBsnsSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainCnstSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainEqipSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainMtilSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMatrial;
import com.henc.dream.util.CamelCaseMap;

@Mapper
public interface DailySetupMapper {
    // 적용기간
    List<CamelCaseMap> selectAplyTermGrdList(DsMainBsnsSet dsMainBsnsSet);

    int insertAplyTerm(DsMainBsnsSet dsMainBsnsSet);

    int updateAplyTerm(DsMainBsnsSet dsMainBsnsSet);

    int deleteAplyTerm(DsMainBsnsSet dsMainBsnsSet);

    // 주요자재
    List<CamelCaseMap> selectMainMtilSetGrdList(DsMainMtilSet dsMtilSet);

    int insertMainMtilSet(DsMainMtilSet dsMtilSet);

    int updateMainMtilSet(DsMainMtilSet dsMtilSet);

    int deleteMainMtilSet(DsMainMtilSet dsMtilSet);

    // 직영장비, 지입장비
    List<CamelCaseMap> selectMainEqipSetGrdList(DsMainEqipSet dsMainEqipSet);

    int insertMainEqipSet(DsMainEqipSet dsMainEqipSet);

    int updateMainEqipSet(DsMainEqipSet dsMainEqipSet);

    int deleteMainEqipSet(DsMainEqipSet dsMainEqipSet);

    // 공사진행현황
    List<CamelCaseMap> selectMainCnstQtySetGrdList(DsMainCnstSet dsMainCnstSet);

    int insertMainCnstQtySet(DsMainCnstSet dsMainCnstSet);

    int updateMainCnstQtySet(DsMainCnstSet dsMainCnstSet);

    int deleteMainCnstQtySet(DsMainCnstSet dsMainCnstSet);

    // 주요자재 팝업
    List<CamelCaseMap> selectMtilListPopup(DsMatrial dsMatrial);

    // 장비 팝업
    List<CamelCaseMap> selectEqipListPopup(DsEquip dsEquip);

    // 공사진행현황 팝업
    List<CamelCaseMap> selectCnstQtyListPopup(DsCnstQty dsCnstQty);


    // Daily Check
    int selectDailCheckList(DsCopyParams dsCopyParams);

    // 복사
    int insertBsnsDailyCopySet(DsCopyParams dsCopyParams);

    int insertMtilDailyCopySet(DsCopyParams dsCopyParams);

    int insertEqipDailyCopySet(DsCopyParams dsCopyParams);

    int insertCnstQtyDailyCopySet(DsCopyParams dsCopyParams);

}
