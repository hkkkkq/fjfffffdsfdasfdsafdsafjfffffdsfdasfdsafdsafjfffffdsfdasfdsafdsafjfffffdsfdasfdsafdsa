package com.henc.cdrs.sysm.com.uscd.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.sysm.com.uscd.model.ComDeptCd;
import com.henc.cdrs.sysm.com.uscd.repository.ComDeptCdMapper;

@Service
@Transactional
public class ComDeptCdService{

	@Autowired
	ComDeptCdMapper comDeptCdMapper;
	
	public List<CamelCaseMap> getComDeptCdList(ComDeptCd comDeptCd) {
		return comDeptCdMapper.getComDeptCdList(comDeptCd);
	}
	
	public List<CamelCaseMap> gridComDeptCdHierachyList(Map<String, String> map) {
		return comDeptCdMapper.gridComDeptCdHierachyList(map);
	}

}
