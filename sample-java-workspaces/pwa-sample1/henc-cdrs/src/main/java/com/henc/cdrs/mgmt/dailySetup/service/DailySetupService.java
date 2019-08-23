package com.henc.cdrs.mgmt.dailySetup.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.common.service.UsedException;
import com.henc.cdrs.mgmt.dailySetup.model.DsCnstQty;
import com.henc.cdrs.mgmt.dailySetup.model.DsCopyParams;
import com.henc.cdrs.mgmt.dailySetup.model.DsEquip;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainBsnsSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainCnstSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainEqipSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainMtilSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMatrial;
import com.henc.cdrs.mgmt.dailySetup.model.SearchDsParams;
import com.henc.cdrs.mgmt.dailySetup.repository.DailySetupMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

@Transactional
@Service
public class DailySetupService {

    @Autowired
    private DailySetupMapper dailySetupMapper;
    private DsCopyParams dsCopyParams;
    private SearchDsParams targetDayParams;

    // 적용기간 Service
    public List<CamelCaseMap> selectAplyTermGrdList(DsMainBsnsSet dsMainBsnsSet) {
        return dailySetupMapper.selectAplyTermGrdList(dsMainBsnsSet);
    }

    public void saveDailySetupList(List<DsMainBsnsSet> dsMainBsnsSets) {
        if (CollectionUtils.isNotEmpty(dsMainBsnsSets)) {
            for (DsMainBsnsSet dsMainBsnsSet : dsMainBsnsSets) {
                switch (dsMainBsnsSet.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        dailySetupMapper.insertAplyTerm(dsMainBsnsSet);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        dailySetupMapper.updateAplyTerm(dsMainBsnsSet);
                        break;
                    case IBSheetRowStatus.DELETED:
                        dsCopyParams = new DsCopyParams();
                        targetDayParams = new SearchDsParams();

                        targetDayParams.setAplyDt(dsMainBsnsSet.getAplyTerm());
                        targetDayParams.setDeptCd(dsMainBsnsSet.getDeptCd());

                        dsCopyParams.setTargetDayParams(targetDayParams);

                        int dailyCnt = dailySetupMapper.selectDailCheckList(dsCopyParams);
                        System.out.println("T E S T : " + dailyCnt);
                        if(dailyCnt >= 2){
                            String message = "이미 해당 적용기간에 데이터가 있습니다. 설정 데이터 삭제 후 삭제 바랍니다.";
                            throw new UsedException(message);
                        }
                        dailySetupMapper.deleteAplyTerm(dsMainBsnsSet);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(dsMainBsnsSet.getRowStatus());
                }
            }
        }
    }

    // 주요자재 Service
    public List<CamelCaseMap> selectMainMtilSetGrdList(DsMainMtilSet dsMainMtilSet) {
        return dailySetupMapper.selectMainMtilSetGrdList(dsMainMtilSet);
    }

    public void saveMtilList(List<DsMainMtilSet> dsMainMtilSets) {
        if (CollectionUtils.isNotEmpty(dsMainMtilSets)) {
            for (DsMainMtilSet dsMainMtilSet : dsMainMtilSets) {
                switch (dsMainMtilSet.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        dailySetupMapper.insertMainMtilSet(dsMainMtilSet);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        dailySetupMapper.updateMainMtilSet(dsMainMtilSet);
                        break;
                    case IBSheetRowStatus.DELETED:
                        dailySetupMapper.deleteMainMtilSet(dsMainMtilSet);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(dsMainMtilSet.getRowStatus());
                }
            }
        }
    }


    // 직영장비, 지입장비 Service
    public List<CamelCaseMap> selectMainEqipSetGrdList(DsMainEqipSet dsMainEqipSet) {
        return dailySetupMapper.selectMainEqipSetGrdList(dsMainEqipSet);
    }

    public void saveEqipList(List<DsMainEqipSet> dsMainEqipSets) {
        if (CollectionUtils.isNotEmpty(dsMainEqipSets)) {
            for (DsMainEqipSet dsMainEqipSet : dsMainEqipSets) {
                switch (dsMainEqipSet.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        dailySetupMapper.insertMainEqipSet(dsMainEqipSet);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        dailySetupMapper.updateMainEqipSet(dsMainEqipSet);
                        break;
                    case IBSheetRowStatus.DELETED:
                        dailySetupMapper.deleteMainEqipSet(dsMainEqipSet);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(dsMainEqipSet.getRowStatus());
                }
            }
        }
    }


    // 공사진행현황 Service
    public List<CamelCaseMap> selectMainCnstQtySetGrdList(DsMainCnstSet dsMainCnstSet) {
        return dailySetupMapper.selectMainCnstQtySetGrdList(dsMainCnstSet);
    }

    public void saveCnstQtyList(List<DsMainCnstSet> dsMainCnstSets) {
        if (CollectionUtils.isNotEmpty(dsMainCnstSets)) {
            for (DsMainCnstSet dsMainCnstSet : dsMainCnstSets) {
                switch (dsMainCnstSet.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        dailySetupMapper.insertMainCnstQtySet(dsMainCnstSet);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        dailySetupMapper.updateMainCnstQtySet(dsMainCnstSet);
                        break;
                    case IBSheetRowStatus.DELETED:
                        dailySetupMapper.deleteMainCnstQtySet(dsMainCnstSet);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(dsMainCnstSet.getRowStatus());
                }
            }
        }
    }

    // 주요자재 조회 쿼리
    public List<CamelCaseMap> selectMtilListPopup(DsMatrial dsMatrial) {
        return dailySetupMapper.selectMtilListPopup(dsMatrial);
    }

    // 장비 조회 쿼리
    public List<CamelCaseMap> selectEqipListPopup(DsEquip dsEquip) {
        return dailySetupMapper.selectEqipListPopup(dsEquip);
    }

    // 공사진행현황 조회 쿼리
    public List<CamelCaseMap> selectCnstQtyListPopup(DsCnstQty dsCnstQty) {
        return dailySetupMapper.selectCnstQtyListPopup(dsCnstQty);
    }

    // 현장일보설정 복사
    public boolean copyDailySetupData(DsCopyParams dsCopyParams) {

        int dailyCnt = dailySetupMapper.selectDailCheckList(dsCopyParams);
        System.out.println("T E S T : " + dailyCnt);
        if(dailyCnt > 0){
            String message = "이미 해당 적용기간에 데이터가 있습니다. [적용기간 : " + dsCopyParams.getTargetDayParams().getAplyDt() + "]";
            throw new UsedException(message);
        }

        // 적용기간 생성
        dailySetupMapper.insertBsnsDailyCopySet(dsCopyParams);
        // 자재 생성
        dailySetupMapper.insertMtilDailyCopySet(dsCopyParams);
        // 장비 생성
        dailySetupMapper.insertEqipDailyCopySet(dsCopyParams);
        // 현장일보설정 복사 생성
        dailySetupMapper.insertCnstQtyDailyCopySet(dsCopyParams);

        return true;
    }

}
