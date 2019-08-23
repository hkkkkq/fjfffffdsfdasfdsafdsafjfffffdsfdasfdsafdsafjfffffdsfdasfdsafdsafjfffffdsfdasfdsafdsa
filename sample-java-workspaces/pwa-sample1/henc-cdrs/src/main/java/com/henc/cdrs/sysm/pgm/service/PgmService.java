package com.henc.cdrs.sysm.pgm.service;

import java.util.List;

import com.henc.cdrs.sysm.pgm.model.SearchNmspc;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.sysm.pgm.model.Pgm;
import com.henc.cdrs.sysm.pgm.model.PgmBtn;
import com.henc.cdrs.sysm.pgm.repository.PgmMapper;

@Service
@Transactional
public class PgmService {

    @Autowired
    PgmMapper pgmMapper;

    public List<CamelCaseMap> getComPgmList(Pgm pgm) {
        return pgmMapper.selectComPgmList(pgm);
    }

    public Pgm getPgm(String pgmId) {
        return pgmMapper.selectPgm(pgmId);
    }

    public List<CamelCaseMap> getComPgmBtnList(PgmBtn pgmBtn) {
        return pgmMapper.selectComPgmBtnList(pgmBtn);
    }

    public Pgm createComPgm(Pgm pgm) {
        if (pgm.getNmspcCd() == null) {
            pgm.setRangCd("PGM");
            pgm.setNmspcCd(pgmMapper.makeNmspcCd());
            pgmMapper.insertPgmNamespace(pgm);
        }
        pgmMapper.insertComPgm(pgm);

        saveComPgmBtnList(pgm.getPgmBtns(), pgm.getPgmId());
        return pgm;
    }

    public void modifyComPgm(Pgm pgm) {
        pgmMapper.updateComPgm(pgm);
        pgmMapper.mergeNamespace(pgm);
        saveComPgmBtnList(pgm.getPgmBtns(), pgm.getPgmId());
    }

    public Pgm removeComPgm(Pgm pgm) {
        pgmMapper.deleteComPgmBtn(pgm.getPgmId());
        pgmMapper.deleteComPgm(pgm);
        return pgm;
    }

    public void saveComPgmBtnList(List<PgmBtn> pgmBtns, String pgmId) {
        if (CollectionUtils.isNotEmpty(pgmBtns)) {
            for (PgmBtn pgmBtn : pgmBtns) {
                Pgm pgm = new Pgm();
                switch (pgmBtn.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        pgmBtn.setPgmId(pgmId);
                        pgm.setNmspcCd(pgmBtn.getNmspcCd());
                        pgm.setNmspcVal(pgmBtn.getNmspcVal());
                        pgm.setRangCd("BTN");
                        pgmMapper.mergeNamespace(pgm);
                        pgmMapper.insertComPgmBtnList(pgmBtn);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        pgm.setNmspcCd(pgmBtn.getNmspcCd());
                        pgm.setNmspcVal(pgmBtn.getNmspcVal());
                        pgm.setRangCd("BTN");
                        pgmMapper.mergeNamespace(pgm);
                        pgmMapper.updateComPgmBtnList(pgmBtn);
                        break;
                    case IBSheetRowStatus.DELETED:
                        pgmMapper.deleteComPgmBtnList(pgmBtn);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(pgmBtn.getRowStatus());
                }
            }
        }
    }

    public List<CamelCaseMap> getNmspcList(SearchNmspc searchNmspc) {
        return pgmMapper.selectNmspcList(searchNmspc);
    }

}