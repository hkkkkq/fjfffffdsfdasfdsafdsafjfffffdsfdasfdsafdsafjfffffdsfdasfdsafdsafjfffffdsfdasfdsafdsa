package com.henc.cdrs.mgmt.constType.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.mgmt.constType.model.ConstType;
import com.henc.cdrs.mgmt.constType.model.Ocpt;
import com.henc.dream.util.CamelCaseMap;

@Mapper
public interface ConstTypeMapper {

    List<CamelCaseMap> selectConstTypeGrdList(ConstType constType);

    int insertConstType(ConstType constType);

    int updateConstType(ConstType constType);

    int deleteConstType(ConstType constType);

    List<CamelCaseMap> selectOcptGrdList(Ocpt ocpt);

    int insertOcpt(Ocpt ocpt);

    int updateOcpt(Ocpt ocpt);

    int deleteOcpt(Ocpt ocpt);

    List<CamelCaseMap> selectLabatdPcntCount(Ocpt ocpt);

    List<CamelCaseMap> selectCoprcpInfoCount(ConstType constType);

    List<CamelCaseMap> selectOcptCount(ConstType constType);
}
