package com.henc.cdrs.sysm.sec.usr.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.sysm.rnr.model.ComUserRole;
import com.henc.cdrs.sysm.sec.usr.model.ComUser;

@Mapper
public interface ComUserRoleMapper {

	List<CamelCaseMap> gridUserRoleList(ComUser comUser);

	int deleteComUserRole(ComUserRole comUserRole);

	int insertComUserRole(ComUserRole comUserRole);

	int updateComUserRole(ComUserRole comUserRole);

	String getUserMenuXmlData(Map map);

}
