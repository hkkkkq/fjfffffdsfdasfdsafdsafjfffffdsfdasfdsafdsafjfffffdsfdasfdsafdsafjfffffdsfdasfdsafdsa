package com.henc.cdrs.common.notification.repository;

import com.henc.cdrs.common.notification.model.ScTran;
import org.apache.ibatis.annotations.Mapper;

/**
 * Push History 테이블
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-01
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Mapper
public interface ScTranMapper {
    int insertScTran(ScTran scTran);
}
