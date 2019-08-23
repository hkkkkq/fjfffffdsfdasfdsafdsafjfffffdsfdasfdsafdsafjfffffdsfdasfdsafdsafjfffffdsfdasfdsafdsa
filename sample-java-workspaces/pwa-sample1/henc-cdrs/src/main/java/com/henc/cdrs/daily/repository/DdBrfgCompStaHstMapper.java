package com.henc.cdrs.daily.repository;

import com.henc.cdrs.daily.model.DdBrfg;
import com.henc.cdrs.daily.model.DdBrfgComp;
import com.henc.cdrs.daily.model.DdBrfgCompStaHst;
import com.henc.cdrs.daily.model.SearchDailyParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 일일 보고 회사 매퍼
 */
@Mapper
public interface DdBrfgCompStaHstMapper {

    DdBrfgCompStaHst get(DdBrfgCompStaHst ddBrfgCompStaHst);

    /**
     * 일일보고 변경내역 최종 차수 데이터 조회
     *
     * @param ddBrfgCompStaHst
     * @return
     */
    DdBrfgCompStaHst top(DdBrfgComp comp);

    /**
     * 가장 최근 제출자 조회.
     * @param comp
     * @return
     */
    DdBrfgCompStaHst latestSubmitter(DdBrfgComp comp);

    int insert(DdBrfgCompStaHst ddBrfgCompStaHst);

    int update(DdBrfgCompStaHst ddBrfgCompStaHst);

    int delete(DdBrfgCompStaHst ddBrfgCompStaHst);

    /**
     * 변경대상에 속하는 내역 조회.
     *
     * @param searchDailyParams
     * @return
     */
    List<DdBrfgCompStaHst> getListInChange(DdBrfg ddBrfg);

    /**
     * 변경작성된 내역 삭제
     *
     * @param ddBrfg
     * @return
     */
    int deleteChanged(DdBrfg ddBrfg);


    /**
     * 일보 협력사 변경이력 조회.
     * @param searchDailyParams
     * @return
     */
    List<DdBrfgCompStaHst> getChangedHistories(SearchDailyParams searchDailyParams);
}
