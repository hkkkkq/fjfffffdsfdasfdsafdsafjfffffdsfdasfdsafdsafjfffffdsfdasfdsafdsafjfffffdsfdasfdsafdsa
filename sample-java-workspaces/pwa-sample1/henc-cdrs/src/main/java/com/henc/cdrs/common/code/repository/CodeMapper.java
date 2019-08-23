package com.henc.cdrs.common.code.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.common.code.model.Code;

@Mapper
public interface CodeMapper {
	
	List<CamelCaseMap> selectCodeList(Map<String, Object> map);
    
	List<CamelCaseMap> selectCodeList(Code code);
    
	List<CamelCaseMap> selectCodeAttributeList(Code code);
}
