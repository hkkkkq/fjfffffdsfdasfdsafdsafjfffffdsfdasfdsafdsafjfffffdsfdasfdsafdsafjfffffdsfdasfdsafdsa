package com.henc.cdrs.common.namespace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.dream.util.CamelCaseMap;
import com.henc.cdrs.common.namespace.model.NameSpace;
import com.henc.cdrs.common.namespace.repository.NameSpaceMapper;

@Service
@Transactional
public class NameSpaceService {
	
    @Autowired
    private NameSpaceMapper nameSpaceMapper;		
    
    public CamelCaseMap getNameSpace(NameSpace nameSpace) {
        return nameSpaceMapper.getNameSpace(nameSpace);
    }    
    
}
