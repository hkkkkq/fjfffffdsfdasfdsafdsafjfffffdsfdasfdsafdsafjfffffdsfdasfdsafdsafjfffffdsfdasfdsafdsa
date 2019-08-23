package com.henc.cdrs.partners.users.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.partners.users.model.PrtnInfo;
import com.henc.cdrs.partners.users.model.Users;
import com.henc.dream.util.CamelCaseMap;

@Mapper
public interface PartnerUsersMapper {

    List<CamelCaseMap> selectUsersGrdList(Users users);

    List<CamelCaseMap> selectPartnerPopList(PrtnInfo prtnInfo);

    String selectUserSeq();



    List<CamelCaseMap> selectDupCheck();



    int insertPartnerUsers(Users users);

    int updatePartnerUsers(Users users);

    int deletePartnerUsers(Users users);

    int insertPartnerUserDept(Users users);

    int deletePartnerUserDept(Users users);
}
