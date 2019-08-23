package com.henc.cdrs.common.namespace.repository;

import org.apache.ibatis.annotations.Mapper;
import com.henc.dream.util.CamelCaseMap;
import com.henc.cdrs.common.namespace.model.NameSpace;

@Mapper
public interface NameSpaceMapper {	
	CamelCaseMap getNameSpace(NameSpace nameSpace);
}
