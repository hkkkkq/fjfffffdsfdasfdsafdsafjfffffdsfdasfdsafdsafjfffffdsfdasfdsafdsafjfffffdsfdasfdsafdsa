package com.henc.cdrs.sysm.pgm.repository;

import java.util.List;

import com.henc.cdrs.sysm.pgm.model.SearchNmspc;
import org.apache.ibatis.annotations.Mapper;
import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.sysm.pgm.model.Pgm;
import com.henc.cdrs.sysm.pgm.model.PgmBtn;

@Mapper
public interface PgmMapper {
    List<CamelCaseMap> selectComPgmList(Pgm pgm);
    
    Pgm selectPgm(String pgmId);
	
    List<CamelCaseMap> selectComPgmBtnList(PgmBtn pgmBtn);
    
    int insertComPgm(Pgm pgm); 
    
    void updateComPgm(Pgm pgm);
    
    void deleteComPgmBtn(String pgmId);
    
    void deleteComPgm(Pgm pgm);
    
    int insertComPgmBtnList(PgmBtn pgmBtn);

    int updateComPgmBtnList(PgmBtn pgmBtn);    
   
    int deleteComPgmBtnList(PgmBtn pgmBtn);

    /**
     * @deprecated
     * @param pgm
     */
    void mergeNamespace(Pgm pgm);

    /**
     * 프로그램 생성 후 해당 네임스페이스 자동 생성
     * @param pgm
     * @return
     */
    int insertPgmNamespace(Pgm pgm);

    List<CamelCaseMap> selectNmspcList(SearchNmspc searchNmspc);

    String makeNmspcCd();
}
