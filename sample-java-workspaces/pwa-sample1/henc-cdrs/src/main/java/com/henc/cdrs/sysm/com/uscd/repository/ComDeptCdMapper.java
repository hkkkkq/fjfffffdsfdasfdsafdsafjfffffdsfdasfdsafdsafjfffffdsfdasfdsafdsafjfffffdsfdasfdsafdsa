package com.henc.cdrs.sysm.com.uscd.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.sysm.com.uscd.model.ComDeptCd;

@Mapper
public interface ComDeptCdMapper {

    List<CamelCaseMap> getComDeptCdList(ComDeptCd comDeptCd);

    List<CamelCaseMap> gridComDeptCdHierachyList(Map<String, String> map);

}
