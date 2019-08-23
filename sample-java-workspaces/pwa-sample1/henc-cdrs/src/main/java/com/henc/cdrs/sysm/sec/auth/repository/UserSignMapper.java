package com.henc.cdrs.sysm.sec.auth.repository;

import com.henc.cdrs.sysm.sec.auth.model.UserSign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-25
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Mapper
public interface UserSignMapper {

    UserSign selectUserSign(@Param("userId") String userId);

    int insertUserSign(UserSign userSign);
    int deleteUserSign(UserSign userSign);
}
