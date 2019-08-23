package com.henc.cdrs.sysm.rnr.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.common.code.model.ComMenuPgmBtn;
import com.henc.cdrs.common.code.model.ComNmspcInfo;
import com.henc.cdrs.sysm.rnr.model.ComRole;
import com.henc.cdrs.sysm.rnr.model.ComRoleDept;
import com.henc.cdrs.sysm.rnr.model.ComRoleMenu;

@Mapper
public interface ComRoleMapper {

	List<CamelCaseMap> selectComRoleList(ComRole comRole);
	
	CamelCaseMap getComRoleMenuPgm(ComRole role);
	
	List<CamelCaseMap> selectComMenuPgmBtnList(ComRole role);
	
	int insertComRole(ComRole comRole);
	
	int updateComRole(ComRole comRole);
	
	int deleteComRole(ComRole comRole);
	
	int mergeRoleNamespace(ComRole comRole);
	
	int deleteRoleNamespace(ComRole comRole);
	
	String getRoleMenuList(Map<String, Object> map);
	
	void createComRoleMenu(ComRoleMenu comRoleMenu);
	
	void deleteComRoleMenu(ComRoleMenu comRoleMenu);
	
	List<CamelCaseMap> selectNameSpaceList(ComNmspcInfo comNmspcInfo);
	
    int insertComNmspcInfo(ComNmspcInfo comNmspcInfo);
    
    int updateComNmspcInfo(ComNmspcInfo comNmspcInfo);
    
    int deleteComNmspcInfo(ComNmspcInfo comNmspcInfo);	
    
    List<CamelCaseMap> selectComPgmList(Map<String, String> map);
    
    void deleteAllComMenuPgmBtn(ComMenuPgmBtn comMenuPgmBtn);
    
    void updateComRoleMenu(ComRoleMenu comRoleMenu);
    
    void deleteComMenuPgmBtn(ComMenuPgmBtn comMenuPgmBtn);
    
    void insertComMenuPgmBtn(ComMenuPgmBtn comMenuPgmBtn);
    
    List<CamelCaseMap> moveComRoleMenu(ComRoleMenu comRoleMenu);
    
    List<CamelCaseMap> selectComRoleDeptList(ComRoleDept comRoleDept);
	
    void deleteComRoleDeptList(ComRoleDept comRoleDept);
    
    void insertComRoleDeptList(ComRoleDept comRoleDept);
    
    void updateComRoleDeptList(ComRoleDept comRoleDept);
    
    List<CamelCaseMap> selectComDeptCdList(ComRoleDept comRoleDept);
    
}
