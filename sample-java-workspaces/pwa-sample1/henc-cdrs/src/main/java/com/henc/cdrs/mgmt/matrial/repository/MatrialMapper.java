package com.henc.cdrs.mgmt.matrial.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.mgmt.matrial.model.Matrial;
import com.henc.cdrs.mgmt.matrial.model.MatrialDetl;
import com.henc.dream.util.CamelCaseMap;

@Mapper
public interface MatrialMapper {

    List<CamelCaseMap> selectMgmtGrdList(Matrial material);

    int insertMatrial(Matrial matrial);

    int updateMatrial(Matrial matrial);

    int deleteMatrial(Matrial matrial);

    List<CamelCaseMap> selectMgmtGrdDetlList(MatrialDetl matrialdetl);

    int updateMatrialDetail(MatrialDetl matrialdetl);

    List<CamelCaseMap> selectMgmtSynPoplList(Matrial matrial);

    int updateMatrialSameMtilNoRestore(Matrial matrial);

    int selectMatrialSameCount(Matrial matrial);

    CamelCaseMap getMatrial(Matrial matrial);
}
