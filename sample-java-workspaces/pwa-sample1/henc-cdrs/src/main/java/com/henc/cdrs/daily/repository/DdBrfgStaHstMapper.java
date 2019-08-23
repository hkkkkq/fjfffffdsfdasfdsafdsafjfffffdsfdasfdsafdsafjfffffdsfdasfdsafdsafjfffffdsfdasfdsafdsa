package com.henc.cdrs.daily.repository;

import com.henc.cdrs.daily.model.DdBrfg;
import com.henc.cdrs.daily.model.DdBrfgStaHst;
import org.apache.ibatis.annotations.Mapper;

/**
 * 일일 보고 상태 이력 매퍼
 */
@Mapper
public interface DdBrfgStaHstMapper {

    DdBrfgStaHst top(DdBrfg master);

    int insert(DdBrfgStaHst ddBrfgStaHst);

    /**
     * 변경작성된 내역 삭제
     *
     * @param ddBrfg
     * @return
     */
    int deleteChanged(DdBrfg ddBrfg);
}
