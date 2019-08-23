package com.henc.cdrs.mgmt.progress.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.mgmt.progress.model.Progress;
import com.henc.dream.util.CamelCaseMap;

@Mapper
public interface ProgressMapper {

    List<CamelCaseMap> selectProgressGrdList(Progress progress);

    int insertProgress(Progress progress);

    int updateProgress(Progress progress);

    int deleteProgress(Progress progress);
}
