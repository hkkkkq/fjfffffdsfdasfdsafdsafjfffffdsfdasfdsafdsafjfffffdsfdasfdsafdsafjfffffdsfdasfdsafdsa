package com.henc.cdrs.daily.repository;

import com.henc.cdrs.daily.model.DdBrfgChgMtr;
import com.henc.cdrs.daily.model.SearchDailyParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 일일 보고 변경사항 매퍼
 */
@Mapper
public interface DdBrfgChgMtrMapper {

    DdBrfgChgMtr get(DdBrfgChgMtr ddBrfgChgMtr);

    /**
     * 신규 CHG_NO 채번.
     *
     * @return
     */
    int maxChgNo();

    int insert(DdBrfgChgMtr ddBrfgChgMtr);

    int update(DdBrfgChgMtr ddBrfgChgMtr);

    int delete(DdBrfgChgMtr ddBrfgChgMtr);

    /**
     * 변경작성된 내역 삭제
     *
     * @param searchDailyParams
     * @return
     */
    int deleteChanged(SearchDailyParams searchDailyParams);

    /**
     * 변경 번호로 변경된 내역을 조회한다.
     * @param chgNo
     * @return
     */
    List<DdBrfgChgMtr> getChanged(@Param("chgNo") Integer chgNo);
}
