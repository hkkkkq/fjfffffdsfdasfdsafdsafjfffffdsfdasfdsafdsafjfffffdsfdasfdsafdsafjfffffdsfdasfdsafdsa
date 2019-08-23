package com.henc.cdrs.sysm.sec.usr.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.sysm.rnr.model.SearchComRoleDept;
import com.henc.cdrs.sysm.sec.usr.model.ComUserRoleDept;

@Mapper
public interface ComUserRoleDeptMapper {

	List<ComUserRoleDept> getComUserRoleDeptList(SearchComRoleDept searchComRoleDept);

	void insertComUserRoleDeptList(ComUserRoleDept comUserRoleDept);

	void deleteComUserRoleDeptList(ComUserRoleDept comUserRoleDept);

}
