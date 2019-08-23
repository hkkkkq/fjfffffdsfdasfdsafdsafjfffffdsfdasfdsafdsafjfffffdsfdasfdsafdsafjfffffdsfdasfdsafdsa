package com.henc.cdrs.mgmt.siteConstType.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.mgmt.siteConstType.model.SiteConstType;
import com.henc.dream.util.CamelCaseMap;

@Mapper
public interface SiteConstTypeMapper {

    List<CamelCaseMap> selectSiteConstTypeGrdList(SiteConstType siteConstType);

    int insertSiteConstType(SiteConstType siteConstType);

    int updateSiteConstType(SiteConstType siteConstType);

    int deleteSiteConstType(SiteConstType siteConstType);

}
