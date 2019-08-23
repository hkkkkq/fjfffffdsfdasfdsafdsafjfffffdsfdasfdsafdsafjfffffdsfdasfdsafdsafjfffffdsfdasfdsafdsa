package com.henc.cdrs.sysm.stdcd.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.sysm.stdcd.model.AttributeDefi;
import com.henc.cdrs.sysm.stdcd.model.AttributeVal;
import com.henc.cdrs.sysm.stdcd.model.StdCd;
import com.henc.cdrs.sysm.stdcd.model.StdDetlCd;

/**
 * 공통 코드를 처리한다.
 */
@Mapper
public interface StdCdMapper {
    List<CamelCaseMap> selectComStdCdList(StdCd stdCd);
    
    int insertComStdCdList(StdCd stdCd);

    int updateComStdCdList(StdCd stdCd);              
    
    int insertComStdCdNameSpaceList(StdCd stdCd);    

    int deleteComStdDetlCdNameSpaceList(StdCd stdCd);
    int deleteComStdCdNameSpaceList(StdCd stdCd);
    int deleteComStdCdDetlList(StdCd stdCd);
    int deleteComStdCdList(StdCd stdCd);
    
    int deleteComStdCdDetlNameSpaceList(StdDetlCd stdDetlCd);  
    
    int insertComStdDetlCdNameSpaceList(StdDetlCd stdDetlCd);
    
    List<CamelCaseMap> selectComStdDetlCdListForManage(StdDetlCd stdDetlCd);
    
    List<CamelCaseMap> selectComAttributeDefi(AttributeDefi attributeDefi);

    int insertComAttributeDefiList(AttributeDefi attributeDefi);

    int updateComAttributeDefiList(AttributeDefi attributeDefi);
    
    int deleteComAttributeDefiList(AttributeDefi attributeDefi);
    
    int insertComStdDetlCdList(StdDetlCd stdDetlCd);

    int updateComStdDetlCdList(StdDetlCd stdDetlCd);
    
    int deleteComStdDetlCdList(StdDetlCd stdDetlCd);
    
    List<CamelCaseMap> selectComAttributeValList(AttributeVal attributeVal);

    /**
     * @param 상세코드 정보
     * @return 업데이트된 수
     */    
    int updateComAttributeValList(AttributeVal attributeVal);
    
    /**
     * @param 상세코드 정보
     * @return 업데이트된 수
     */    
    int deleteComAttributeValList(AttributeVal attributeVal);
    
}
