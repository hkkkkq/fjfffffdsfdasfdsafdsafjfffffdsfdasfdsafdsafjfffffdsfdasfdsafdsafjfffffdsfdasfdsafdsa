package com.henc.cdrs.coprcp.repository;

import com.henc.cdrs.coprcp.model.CoprcpInfo;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoprcpInfoMapper {

    List<CamelCaseMap> findCoprcpsBy(CoprcpInfo coprcpInfo);

    int insertCoprcp(CoprcpInfo coprcpInfo);

    int updateCoprcp(CoprcpInfo coprcpInfo);

    int deleteCoprcp(CoprcpInfo coprcpInfo);

    /**
     * 협력사의 계약 목록을 조회
     * @param coprcpInfo
     * @return
     */
    List<CamelCaseMap> getCtrcs(CoprcpInfo coprcpInfo);

    /**
     * 협력사 계약 정보 조회
     * @param coprcpInfo
     * @return
     */
    CamelCaseMap getCtrc(CoprcpInfo coprcpInfo);
}
