package com.henc.cdrs.daily.repository;

import com.henc.cdrs.daily.model.MainEqipSet;
import com.henc.cdrs.daily.model.SearchDailyParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 일일 보고 주요 장비 설정 매퍼
 */
@Mapper
public interface MainEqipSetMapper {

    /**
     * 최근에 적용가능한 기간에 속한 주요 장비 설정 데이터 조회
     * @param mainBsnsSet
     * @return
     */
    List<MainEqipSet> latestSetupList(SearchDailyParams searchDailyParams);

}
