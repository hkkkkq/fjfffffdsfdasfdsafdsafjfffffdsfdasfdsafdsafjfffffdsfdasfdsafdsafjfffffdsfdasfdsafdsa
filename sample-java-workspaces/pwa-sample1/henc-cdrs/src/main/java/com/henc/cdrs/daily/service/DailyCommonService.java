package com.henc.cdrs.daily.service;

import com.henc.cdrs.daily.model.*;
import com.henc.cdrs.daily.repository.*;
import com.henc.cdrs.exception.DailyPartnerException;
import com.henc.dream.util.CamelCaseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 공사일보 공통 서비스.
 * 현장담당과 협력사 일보 서비스로직의 공통부분을 구현.
 */
@Service
@Transactional
public class DailyCommonService {

    @Autowired
    private DdBrfgMapper masterMapper;

    @Autowired
    private DdBrfgLabatdMapper labatdMapper;

    @Autowired
    private DdBrfgMtilMapper mtilMapper;

    @Autowired
    private DdBrfgEqipMapper eqipMapper;

    @Autowired
    private DdBrfgQtyMapper qtyMapper;

    @Autowired
    private DdBrfgAprvMapper aprvMapper;

    @Autowired
    private DdBrfgStaHstMapper staHstMapper;

    @Autowired
    private DdBrfgCompStaHstMapper compStaHstMapper;

    /**
     * 일일 보고 마스터 정보(조회 후 없으면 차수생성).
     *
     * @param searchDailyParams
     * @return
     */
    public DdBrfg topGetMaster(SearchDailyParams searchDailyParams) {
        DdBrfg ddBrfg = new DdBrfg();
        ddBrfg.setDeptCd(searchDailyParams.getDeptCd());
        ddBrfg.setDay(searchDailyParams.getDay());

        ddBrfg = masterMapper.topGet(ddBrfg);
        if (ddBrfg.getOrdrNo() == null) {
            // 해당일자의 마스터 정보가 존재하지 않으면 신규차수를 생성한다.
            ddBrfg.setStaCd(DdBrfgStaCd.DRAFT.getValue());
            masterMapper.insert(ddBrfg);
            createStaHistory(ddBrfg, DdBrfgStaCd.DRAFT.getName());
        }

        return ddBrfg;
    }

    /**
     * 일보 협력사 상태 히스토리 생성.
     *
     * @param comp
     * @param chgRsn 사유
     * @return
     */
    public boolean createCompStaHistory(DdBrfgComp comp, String chgRsn) {
        DdBrfgCompStaHst ddBrfgCompStaHst = new DdBrfgCompStaHst(comp, chgRsn);
        DdBrfgCompStaHst prevCompStaHst = compStaHstMapper.top(comp);
        if (prevCompStaHst != null) {
            ddBrfgCompStaHst.setSeq(prevCompStaHst.getSeq() + 1);
            ddBrfgCompStaHst.setBfStaCd(prevCompStaHst.getChgStaCd());
            ddBrfgCompStaHst.setBfUpdtId(prevCompStaHst.getChgUpdtId());
        }
        compStaHstMapper.insert(ddBrfgCompStaHst);

        return true;
    }

    /**
     * 일보 상태 히스토리 생성.
     *
     * @param master
     * @param chgRsn
     * @return
     */
    public boolean createStaHistory(DdBrfg master, String chgRsn) {
        DdBrfgStaHst ddBrfgStaHst = new DdBrfgStaHst(master, chgRsn);
        DdBrfgStaHst prevStaHst = staHstMapper.top(master);
        if (prevStaHst != null) {
            ddBrfgStaHst.setSeq(prevStaHst.getSeq() + 1);
            ddBrfgStaHst.setBfStaCd(prevStaHst.getChgStaCd());
            ddBrfgStaHst.setBfUpdtId(prevStaHst.getChgUpdtId());
        }
        staHstMapper.insert(ddBrfgStaHst);

        return true;
    }


    /**
     * 공사일보 결재자 인지 판단.
     *
     * @param master
     * @return
     */
    public int isAprvSigner(DdBrfg master) {
        DdBrfgAprv aprv = aprvMapper.getCurrentSigner(master);
        if (aprv == null) {
            return -1;
        } else {
            return master.getLoginId().equals(aprv.getEmpeNo()) ? aprv.getSeq() : -1;
        }
    }

    /**
     * 공사일보 결재자 목록 조회
     *
     * @param master
     * @return
     */
    public List<CamelCaseMap> aprvUsers(DdBrfg master) {
        return aprvMapper.aprvUsers(master);
    }

    /**
     * 일보 상태 이력 최근 레코드 조회.
     *
     * @param master
     * @return
     */
    public DdBrfgStaHst topGetStaHst(DdBrfg master) {

        DdBrfgStaHst top = staHstMapper.top(master);

        return top;
    }

    /**
     * 자동완성 검색 목록 조회.
     * @param searchDailyParams
     * @return
     */
    public List<CamelCaseMap> autocompleateSearch(SearchDailyParams searchDailyParams) {
        switch (searchDailyParams.getAutocompleteSearchParams().getTarget()) {
            case "labatd":
                return labatdMapper.quickSearchList(searchDailyParams);

            case "mtil":
                return mtilMapper.quickSearchList(searchDailyParams);

            case "eqip":
                return eqipMapper.quickSearchList(searchDailyParams);

            case "qty":
                return qtyMapper.quickSearchList(searchDailyParams);

            default:
                throw new DailyPartnerException("자동완성 기능을 사용할 수 없습니다.");
        }
    }
}