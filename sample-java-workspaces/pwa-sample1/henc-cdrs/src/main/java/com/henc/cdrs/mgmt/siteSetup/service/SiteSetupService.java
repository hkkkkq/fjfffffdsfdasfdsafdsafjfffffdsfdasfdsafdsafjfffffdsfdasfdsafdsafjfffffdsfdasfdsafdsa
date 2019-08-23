package com.henc.cdrs.mgmt.siteSetup.service;

import com.henc.cdrs.mgmt.siteSetup.model.Dept;
import com.henc.cdrs.mgmt.siteSetup.repository.DeptMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SiteSetupService {

    @Autowired
    private DeptMapper deptMapper;

    public List<CamelCaseMap> getDeptList(Dept dept) {
        return deptMapper.getDeptList(dept);
    }

    /**
     * 공사기준 현장코드 저장처리.
     * 사용여부가 "N" 이면 부서 테이블에 삽입하고, "Y" 이면 삭제한다.
     * @param depts
     */
    public void saveDeptList(List<Dept> depts) {
        if (CollectionUtils.isNotEmpty(depts)) {
            for (Dept dept : depts) {
                switch (dept.getRowStatus()) {
                    case IBSheetRowStatus.UPDATED:
                        if ("N".equals(dept.getUseYn())) {
                            deptMapper.deleteDept(dept);
                        } else if ("Y".equals(dept.getUseYn())) {
                            deptMapper.insertDept(dept);
                        }
                        break;

                    default:
                        throw new UnsupportedRowTypeException(dept.getRowStatus());
                }
            }
        }
    }

}
