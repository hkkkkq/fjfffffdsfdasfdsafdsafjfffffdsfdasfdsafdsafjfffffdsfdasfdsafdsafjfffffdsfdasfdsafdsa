package com.henc.cdrs.daily.repository;

import com.henc.cdrs.daily.model.DdBrfg;
import com.henc.cdrs.daily.model.DdBrfgAprv;
import com.henc.cdrs.daily.model.SearchDailyParams;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 일일 보고 결재 매퍼
 */
@Mapper
public interface DdBrfgAprvMapper {

    DdBrfgAprv get(DdBrfgAprv aprv);

    /**
     * 현재 결재할 대상자 조회.
     * @param master
     * @return
     */
    DdBrfgAprv getCurrentSigner(DdBrfg master);

    /**
     * 최초 결재 상신자 조회.
     * @param master
     * @return
     */
    DdBrfgAprv getFirstSigner(DdBrfgAprv aprv);

    /**
     * 다음 결재자 조회.
     * @param aprv
     * @return
     */
    DdBrfgAprv getNextSigner(DdBrfgAprv aprv);

    int insert(DdBrfgAprv aprv);

    int saveApproval(DdBrfgAprv aprv);

    List<CamelCaseMap> aprvUsers(DdBrfg master);

    /**
     * 변경작성된 내역 삭제
     *
     * @param searchDailyParams
     * @return
     */
    int deleteChanged(SearchDailyParams searchDailyParams);

    int deletePastData(DdBrfgAprv aprv);

}
