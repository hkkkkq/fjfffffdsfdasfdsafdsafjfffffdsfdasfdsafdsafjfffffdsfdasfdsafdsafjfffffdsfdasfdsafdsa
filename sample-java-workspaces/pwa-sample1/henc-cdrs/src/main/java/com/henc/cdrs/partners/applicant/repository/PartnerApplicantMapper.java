package com.henc.cdrs.partners.applicant.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.partners.applicant.model.Applicant;
import com.henc.dream.util.CamelCaseMap;

@Mapper
public interface PartnerApplicantMapper {

    List<CamelCaseMap> selectApplicantGrdList(Applicant applicant);

    int insertApplicant(Applicant applicant);

    int updateApplicant(Applicant applicant);

    int deleteApplicant(Applicant applicant);

    List<CamelCaseMap> selectComboTest(Applicant applicant);
}
