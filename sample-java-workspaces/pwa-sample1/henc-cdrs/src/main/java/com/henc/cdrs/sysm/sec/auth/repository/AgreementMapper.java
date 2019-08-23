package com.henc.cdrs.sysm.sec.auth.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.sysm.sec.auth.model.Agreement;
import com.henc.dream.util.CamelCaseMap;

@Mapper
public interface AgreementMapper {

    //UserSign selectUserSign(@Param("userId") String userId);
    List<CamelCaseMap> selectAgreement(Agreement agreement);

    int insertAgreement(Agreement agreement);
    int deleteAgreement(Agreement agreement);
}
