package com.henc.cdrs.sysm.sec.usr.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;

import com.henc.cdrs.sysm.rnr.model.SearchComRoleDept;
import com.henc.cdrs.sysm.sec.usr.model.ComUserRoleDept;
import com.henc.cdrs.sysm.sec.usr.repository.ComUserRoleDeptMapper;

@Service
@Transactional
public class ComUserRoleDeptService{

	@Autowired
	ComUserRoleDeptMapper comUserRoleDeptMapper;
	
	@Autowired
	HttpServletRequest request;
	
	
	public List<ComUserRoleDept> getComUserRoleDeptList(SearchComRoleDept searchComRoleDept) {
		return comUserRoleDeptMapper.getComUserRoleDeptList(searchComRoleDept);
	}

	
	public void gridUserRoleDeptSave(List<ComUserRoleDept> comUserRoleDeptsYes, List<ComUserRoleDept> comUserRoleDeptsNo, String userId) {
        if (CollectionUtils.isNotEmpty(comUserRoleDeptsYes)) {
            for (ComUserRoleDept comUserRoleDept : comUserRoleDeptsYes) {
                comUserRoleDept.setUserId(userId);
                switch (comUserRoleDept.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:                               
                        comUserRoleDeptMapper.insertComUserRoleDeptList(comUserRoleDept);
                        break;
                    case IBSheetRowStatus.DELETED:
                        comUserRoleDeptMapper.deleteComUserRoleDeptList(comUserRoleDept);                  
                        break;
                    default:
                        throw new UnsupportedRowTypeException(comUserRoleDept.getRowStatus());
                }
            }
        }
        
        if (CollectionUtils.isNotEmpty(comUserRoleDeptsNo)) {
            for (ComUserRoleDept comUserRoleDept : comUserRoleDeptsNo) {
                comUserRoleDept.setUserId(userId);
                switch (comUserRoleDept.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:                               
                        comUserRoleDeptMapper.insertComUserRoleDeptList(comUserRoleDept);
                        break;
                    case IBSheetRowStatus.DELETED:
                        comUserRoleDeptMapper.deleteComUserRoleDeptList(comUserRoleDept);                  
                        break;
                    default:
                        throw new UnsupportedRowTypeException(comUserRoleDept.getRowStatus());
                }
            }
        }        
	}

}
