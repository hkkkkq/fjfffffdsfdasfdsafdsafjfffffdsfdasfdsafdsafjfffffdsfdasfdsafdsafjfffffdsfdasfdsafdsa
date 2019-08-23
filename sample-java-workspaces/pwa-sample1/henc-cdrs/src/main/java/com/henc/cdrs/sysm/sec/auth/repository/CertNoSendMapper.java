package com.henc.cdrs.sysm.sec.auth.repository;

import com.henc.cdrs.sysm.sec.auth.model.CertNoSend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 비밀번호 초기화 인증 번호 발송 테이블
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-18
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Mapper
public interface CertNoSendMapper {
    List<CertNoSend> selectCertNoSend(@Param("certNo") String certNo, @Param("userId") String userId);

    int insertCertNoSend(CertNoSend certNoSend);
}
