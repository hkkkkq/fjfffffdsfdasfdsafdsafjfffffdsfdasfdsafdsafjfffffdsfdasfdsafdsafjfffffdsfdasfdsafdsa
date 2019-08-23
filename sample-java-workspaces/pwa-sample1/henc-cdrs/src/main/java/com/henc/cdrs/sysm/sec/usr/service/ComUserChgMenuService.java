package com.henc.cdrs.sysm.sec.usr.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.sysm.sec.usr.model.ComUserChgMenu;
import com.henc.cdrs.sysm.sec.usr.repository.ComUserChgMenuMapper;

@Service
@Transactional
public class ComUserChgMenuService{

	@Autowired
	ComUserChgMenuMapper comUserChgMenuMapper;
	
	@Autowired
	HttpServletRequest request;
	
	
	public void mergeComUserChgMenu(ComUserChgMenu comUserChgMenu) {
		comUserChgMenuMapper.mergeComUserChgMenu(comUserChgMenu);
	}
	
	public void deleteComUserChgMenu(ComUserChgMenu comUserChgMenu) {
		comUserChgMenuMapper.deleteComUserChgMenu(comUserChgMenu);
	}

}
