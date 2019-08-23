package com.henc.cdrs.partners.applicant.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.partners.applicant.model.Applicant;
import com.henc.cdrs.partners.applicant.repository.PartnerApplicantMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

@Transactional
@Service
public class PartnerApplicantService {

    @Autowired
    private PartnerApplicantMapper partnerApplicantMapper ;

    public List<CamelCaseMap> selectApplicantGrdList(Applicant applicant) {
        return partnerApplicantMapper.selectApplicantGrdList(applicant);
    }

    public void saveApplicantList(List<Applicant> applicants) {
        if (CollectionUtils.isNotEmpty(applicants)) {
            for (Applicant applicant : applicants) {

                switch (applicant.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        partnerApplicantMapper.insertApplicant(applicant);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        partnerApplicantMapper.updateApplicant(applicant);
                        break;
                    case IBSheetRowStatus.DELETED:
                        partnerApplicantMapper.deleteApplicant(applicant);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(applicant.getRowStatus());
                }
            }
        }
    }

    public List<CamelCaseMap> getComboTest(Applicant applicant) {
        List<CamelCaseMap> codeList = new ArrayList<>();

        List<CamelCaseMap> rs = partnerApplicantMapper.selectComboTest(applicant);

        for (CamelCaseMap List : rs) {
            CamelCaseMap codeMap = new CamelCaseMap();
            codeMap.put("key", List.get("ocptNo"));
            codeMap.put("value", List.get("ocptNm"));
            codeList.add(codeMap);
        }

        return codeList;
    }

}
