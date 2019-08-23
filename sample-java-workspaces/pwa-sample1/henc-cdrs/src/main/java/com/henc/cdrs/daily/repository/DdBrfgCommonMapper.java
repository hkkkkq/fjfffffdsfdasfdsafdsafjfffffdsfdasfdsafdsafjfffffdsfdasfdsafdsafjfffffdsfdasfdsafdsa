package com.henc.cdrs.daily.repository;

import com.henc.cdrs.daily.model.DdBrfg;
import com.henc.cdrs.daily.model.SearchApprovalUserParams;
import com.henc.cdrs.daily.model.SearchDailyParams;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 일일 보고 공통 매퍼
 */
@Mapper
public interface DdBrfgCommonMapper {

    /**
     * 달력 기준 데이터
     * @param searchDailyParams
     * @return
     */
    List<DdBrfg> monthlyCalendar(SearchDailyParams searchDailyParams);

    /**
     * 협력사 일일보고 달력 기준 데이터
     * @param searchDailyParams
     * @return
     */
    List<CamelCaseMap> monthlyPartnerComp(SearchDailyParams searchDailyParams);

    /**
     * 협력사 제출 또는 승인된 건에 대한 지난 일주일치의 히스토리 복사 대상 조회.
     * @param searchDailyParams
     * @return
     */
    List<CamelCaseMap> historyiesByCtrcNo(SearchDailyParams searchDailyParams);


    /**
     * 공사일보 결재자 찾기 전체 데이터 건수
     * @param searchApprovalUserParams
     * @return
     */
    int getApprovalUserSearchTotal(SearchApprovalUserParams searchApprovalUserParams);

    /**
     * 공사일보 결재자 찾기 페이징 데이터
     * @param searchApprovalUserParams
     * @return
     */
    List<CamelCaseMap> getApprovalUserSearchList(SearchApprovalUserParams searchApprovalUserParams);

    /**
     * 사용자 아이디로 성명 등을 조회.
     * @param userId
     * @return
     */
    CamelCaseMap getUserInfo(@Param("userId") String userId);

}
