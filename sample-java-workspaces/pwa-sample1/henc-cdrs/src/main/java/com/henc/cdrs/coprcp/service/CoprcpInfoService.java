package com.henc.cdrs.coprcp.service;

import com.henc.cdrs.coprcp.model.CoprcpInfo;
import com.henc.cdrs.coprcp.repository.CoprcpInfoMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CoprcpInfoService {

    @Autowired
    private CoprcpInfoMapper coprcpInfoMapper;

    public List<CamelCaseMap> findCoprcpsBy(CoprcpInfo coprcpInfo) {
        return coprcpInfoMapper.findCoprcpsBy(coprcpInfo);
    }

    /**
     * 협력사의 계약목록 조회
     * @param coprcpInfo
     * @return
     */
    public List<CamelCaseMap> getCtrcs(CoprcpInfo coprcpInfo) {
        return coprcpInfoMapper.getCtrcs(coprcpInfo);
    }

    public void saveCoprcpList(List<CoprcpInfo> companies) {
        if (CollectionUtils.isNotEmpty(companies)) {
            for (CoprcpInfo coprcpInfo : companies) {
                switch (coprcpInfo.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        coprcpInfoMapper.insertCoprcp(coprcpInfo);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        coprcpInfoMapper.updateCoprcp(coprcpInfo);
                        break;
                    case IBSheetRowStatus.DELETED:
                        coprcpInfoMapper.deleteCoprcp(coprcpInfo);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(coprcpInfo.getRowStatus());
                }
            }
        }
    }

}