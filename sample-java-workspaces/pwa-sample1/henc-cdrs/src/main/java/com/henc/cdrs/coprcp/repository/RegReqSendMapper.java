package com.henc.cdrs.coprcp.repository;

import com.henc.cdrs.coprcp.model.RegReqSend;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegReqSendMapper {

    List<CamelCaseMap> selectRegReqSend(RegReqSend regReqSend);

    int insertRegReqSend(RegReqSend regReqSend);

}
