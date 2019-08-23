package com.henc.cdrs.mgmt.siteConstType.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.mgmt.siteConstType.model.SiteConstType;
import com.henc.cdrs.mgmt.siteConstType.repository.SiteConstTypeMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

@Transactional
@Service
public class SiteConstTypeService {
    @Autowired
    private SiteConstTypeMapper siteConstTypeMapper;

    public List<CamelCaseMap> selectSiteConstTypeGrdList(SiteConstType siteConstType) {
        return siteConstTypeMapper.selectSiteConstTypeGrdList(siteConstType);
    }

    public void saveSiteConstTypeList(List<SiteConstType> siteConstTypes, String deptCd) {
        if (CollectionUtils.isNotEmpty(siteConstTypes)) {
            for (SiteConstType siteConstType : siteConstTypes) {
                siteConstType.setDeptCd(deptCd);
                switch (siteConstType.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        siteConstTypeMapper.insertSiteConstType(siteConstType);
                        break;
                    case IBSheetRowStatus.UPDATED:
                    case IBSheetRowStatus.DELETED:
                        // 화면에서 수정상태는 체크를 해제함으로써 발생하기 때문에
                        // 체크해제는 삭제로직으로 처리한다.
                        siteConstTypeMapper.deleteSiteConstType(siteConstType);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(siteConstType.getRowStatus());
                }
            }
        }
    }

}
