package com.henc.cdrs.partners.info.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.common.service.UsedException;
import com.henc.cdrs.partners.info.model.Partner;
import com.henc.cdrs.partners.info.model.PrtnConInfo;
import com.henc.cdrs.partners.info.repository.PartnerInfoMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

@Transactional
@Service
public class PartnerInfoService {

    @Autowired
    private PartnerInfoMapper partnerInfoMapper;

    public List<CamelCaseMap> selectCoprcpInfoGrdList(Partner partner) {
        return partnerInfoMapper.selectCoprcpInfoGrdList(partner);
    }

    public void savePartnerInfoList(List<Partner> partners, String deptCd) {

        if (CollectionUtils.isNotEmpty(partners)) {
            for (Partner partner : partners) {
                partner.setDeptCd(deptCd);
                switch (partner.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        partnerInfoMapper.insertCoprcpInfo(partner);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        partnerInfoMapper.updateCoprcpInfo(partner);
                        break;
                    case IBSheetRowStatus.DELETED:

                        int chkCnt = partnerInfoMapper.selectDdBrfgCompCnt(partner);
                        if(chkCnt >= 2){
                            String message = "삭제할려는 협력사 정보를 사용중입니다. 삭제 처리가 불가합니다.";
                            throw new UsedException(message);
                        }

                        partnerInfoMapper.deleteCoprcpInfo(partner);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(partner.getRowStatus());
                }
            }
        }
    }

    public List<CamelCaseMap> selectprtnConListPopup(PrtnConInfo prtnConInfo) {
        return partnerInfoMapper.selectprtnConListPopup(prtnConInfo);
    }
}
