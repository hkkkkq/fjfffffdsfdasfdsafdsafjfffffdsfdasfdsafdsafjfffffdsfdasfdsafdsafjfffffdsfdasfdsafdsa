package com.henc.cdrs.mgmt.matrial.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.mgmt.matrial.model.Matrial;
import com.henc.cdrs.mgmt.matrial.model.MatrialDetl;
import com.henc.cdrs.mgmt.matrial.repository.MatrialMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

@Transactional
@Service
public class MatrialService {

    @Autowired
    private MatrialMapper matrialMapper;

    public List<CamelCaseMap> selectMgmtGrdList(Matrial matrial) {
        return matrialMapper.selectMgmtGrdList(matrial);
    }

    public void saveMatrialList(List<Matrial> matrials, String bizhdofCd) {
        if (CollectionUtils.isNotEmpty(matrials)) {
            for (Matrial matrial : matrials) {
                matrial.setBizhdofCd(bizhdofCd);
                switch (matrial.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        matrialMapper.insertMatrial(matrial);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        matrialMapper.updateMatrial(matrial);
                        break;
                    case IBSheetRowStatus.DELETED:

                        matrial.setSameMtilCnt(matrialMapper.selectMatrialSameCount(matrial));
                        if(matrial.getSameMtilCnt() > 0) {
                            matrialMapper.updateMatrialSameMtilNoRestore(matrial);
                        }
                        matrialMapper.deleteMatrial(matrial);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(matrial.getRowStatus());
                }
            }
        }
    }

    public List<CamelCaseMap> selectMgmtGrdDetlList(MatrialDetl matrialdetl) {
        return matrialMapper.selectMgmtGrdDetlList(matrialdetl);
    }

    public List<CamelCaseMap> selectMgmtSynPoplList(Matrial matrial) {
        return matrialMapper.selectMgmtSynPoplList(matrial);
    }

    public void saveMatrialDetail(List<MatrialDetl> matrialDetls, String bizhdofCd) {
        if (CollectionUtils.isNotEmpty(matrialDetls)) {
            for (MatrialDetl matrialDetl : matrialDetls) {
                matrialDetl.setBizhdofCd(bizhdofCd);
                switch (matrialDetl.getRowStatus()) {
/*                    case IBSheetRowStatus.INSERTED:
                        materialMapper.insertMaterial(materialDetl);
                        break;*/
                    case IBSheetRowStatus.INSERTED:
                        matrialMapper.updateMatrialDetail(matrialDetl);
                        break;
                    case IBSheetRowStatus.DELETED:
                        matrialMapper.updateMatrialDetail(matrialDetl);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(matrialDetl.getRowStatus());
                }
            }
        }
    }
}
