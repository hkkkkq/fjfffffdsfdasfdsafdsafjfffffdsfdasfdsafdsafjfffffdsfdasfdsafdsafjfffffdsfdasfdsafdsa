package com.henc.cdrs.sysm.main.menu.repository;

import com.henc.cdrs.sysm.main.menu.model.Menu;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuMapper {

    List<Menu> selectMenuList(@Param("roleId") String roleId);

    CamelCaseMap selectActiveMenuId(@Param("roleId") String roleId,@Param("uri") String uri);

    /**
     * @deprecated
     * @param map
     * @return
     */
    List<CamelCaseMap> selectMenuTagList(Map<String, String> map);

    List<CamelCaseMap> selectUserMenus(String roleId);


}

