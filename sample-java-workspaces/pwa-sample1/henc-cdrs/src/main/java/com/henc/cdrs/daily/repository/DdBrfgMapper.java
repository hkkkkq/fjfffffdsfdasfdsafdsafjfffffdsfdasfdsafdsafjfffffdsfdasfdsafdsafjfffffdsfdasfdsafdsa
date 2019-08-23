package com.henc.cdrs.daily.repository;

import com.henc.cdrs.daily.model.DdBrfg;
import com.henc.cdrs.daily.model.SaveDailyApprovalParams;
import com.henc.cdrs.daily.model.SearchDailyParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 일일 보고 마스터 매퍼
 */
@Mapper
public interface DdBrfgMapper {

    DdBrfg get(DdBrfg ddBrfg);

    int insert(DdBrfg ddBrfg);

    int update(DdBrfg ddBrfg);

    int delete(DdBrfg ddBrfg);

    int top(DdBrfg ddBrfg);

    DdBrfg topGet(DdBrfg ddBrfg);

    /**
     * 결재 상태 저장.
     *
     * @param saveDailyApprovalParams
     * @return
     */
    int saveApproval(SaveDailyApprovalParams saveDailyApprovalParams);

    /**
     * 변경된 데이터의 결재 상태 저장(결재반려 또는 승인 시에 처리 됨).
     * @param ddBrfg
     * @return
     */
    int saveApprovalChangedData(DdBrfg ddBrfg);

    /**
     * 변경작성 대상 범위 조회.
     *
     * @param searchDailyParams
     * @return
     */
    List<DdBrfg> changeAvailiableMasterList(SearchDailyParams searchDailyParams);

    /**
     * 변경작성 된 데이터 조회.
     *
     * @param searchDailyParams
     * @return
     */
    List<DdBrfg> changedMasterList(SearchDailyParams searchDailyParams);

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
    List<DdBrfg> getChanged(SaveDailyApprovalParams saveDailyApprovalParams);

    /**
     * 해당 현장의 상태코드에 맞는 목록 갯수 조회.
     * @param deptCd
     * @param staCd
     * @return
     */
    int getStaCdCount(@Param("deptCd") String deptCd, @Param("staCd") String staCd);
}
