package com.henc.cdrs.sysm.sec.usr.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.sysm.sec.usr.model.ComUserChgMenuBtn;
import com.henc.cdrs.sysm.sec.usr.repository.ComUserChgMenuBtnMapper;

@Service
@Transactional
public class ComUserChgMenuBtnService {

	@Autowired
	ComUserChgMenuBtnMapper comUserChgMenuBtnMapper;
	
	public List<CamelCaseMap> getComUserChgMenuBtnList(ComUserChgMenuBtn comUserChgMenuBtn) {
		return comUserChgMenuBtnMapper.getComUserChgMenuBtnList(comUserChgMenuBtn);
	}
	
	public void saveComUserChgMenuBtnList(List<ComUserChgMenuBtn> comUserChgMenuBtns, String userId) {
        if (CollectionUtils.isNotEmpty(comUserChgMenuBtns)) {
            for (ComUserChgMenuBtn comUserChgMenuBtn : comUserChgMenuBtns) {
                comUserChgMenuBtn.setUserId(userId);
                switch (comUserChgMenuBtn.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:                               
                        comUserChgMenuBtnMapper.mergeComUserChgMenuBtnList(comUserChgMenuBtn);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        comUserChgMenuBtnMapper.mergeComUserChgMenuBtnList(comUserChgMenuBtn);
                        break;
                    case IBSheetRowStatus.DELETED:
                        comUserChgMenuBtnMapper.deleteComUserChgMenuBtnList(comUserChgMenuBtn);                  
                        break;
                    default:
                        throw new UnsupportedRowTypeException(comUserChgMenuBtn.getRowStatus());
                }
            }
        }	    
	}

}
