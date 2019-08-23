package com.henc.cdrs.sysm.rnr.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.sysm.rnr.model.ComRoleDept;
import com.henc.cdrs.sysm.rnr.model.SearchComRoleDept;

@Mapper
public interface ComRoleDeptMapper {

	List<ComRoleDept> getComRoleDeptAllList(SearchComRoleDept searchComRoleDept);

	List<ComRoleDept> getComRoleDeptPartialList(SearchComRoleDept searchComRoleDept);

	List<ComRoleDept> getComRoleDeptIndivisualList(SearchComRoleDept searchComRoleDept);

	List<CamelCaseMap> getComUserRoleDeptList(Map<?, ?> map);

}
