package com.henc.cdrs.mgmt.siteSetup.repository;

import com.henc.cdrs.mgmt.siteSetup.model.Dept;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptMapper {

    List<CamelCaseMap> getDeptList(Dept dept);

    int insertDept(Dept dept);

    int updateDept(Dept dept);

    int deleteDept(Dept dept);

}
