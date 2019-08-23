package com.henc.cdrs.mgmt.constType.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.common.service.UsedException;
import com.henc.cdrs.mgmt.constType.model.ConstType;
import com.henc.cdrs.mgmt.constType.model.Ocpt;
import com.henc.cdrs.mgmt.constType.repository.ConstTypeMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

@Transactional
@Service
public class ConstTypeService {
    @Autowired
    private ConstTypeMapper constTypeMapper;

    public List<CamelCaseMap> selectConstTypeGrdList(ConstType constType) {
        return constTypeMapper.selectConstTypeGrdList(constType);
    }

    public void saveConstTypeList(List<ConstType> constTypes) {
        if (CollectionUtils.isNotEmpty(constTypes)) {
            for (ConstType constType : constTypes) {
                switch (constType.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        constTypeMapper.insertConstType(constType);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        constTypeMapper.updateConstType(constType);
                        break;
                    case IBSheetRowStatus.DELETED:

                        if(constTypeMapper.selectCoprcpInfoCount(constType).size() > 0 ||
                           constTypeMapper.selectOcptCount(constType).size() > 0 ){
                            String message = "이미 사용중인 공종명 입니다. [공종명 : " + constType.getCstkndNm() + "]";
                            throw new UsedException(message);
                        }

                        constTypeMapper.deleteConstType(constType);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(constType.getRowStatus());
                }
            }
        }
    }

    public List<CamelCaseMap> selectOcptGrdList(Ocpt ocpt) {
        return constTypeMapper.selectOcptGrdList(ocpt);
    }

    public void saveOcptList(List<Ocpt> ocpts) {
        if (CollectionUtils.isNotEmpty(ocpts)) {
            for (Ocpt ocpt : ocpts) {
                switch (ocpt.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        constTypeMapper.insertOcpt(ocpt);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        constTypeMapper.updateOcpt(ocpt);
                        break;
                    case IBSheetRowStatus.DELETED:

                        if(constTypeMapper.selectLabatdPcntCount(ocpt).size() > 0){
                            String message = "이미 사용중인 직종명 입니다. [직종명 : " + ocpt.getOcptNm() + "]";
                            throw new UsedException(message);
                        }

                        constTypeMapper.deleteOcpt(ocpt);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(ocpt.getRowStatus());
                }
            }
        }
    }

}
