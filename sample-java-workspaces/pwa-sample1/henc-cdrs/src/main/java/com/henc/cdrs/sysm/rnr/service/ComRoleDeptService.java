package com.henc.cdrs.sysm.rnr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.sysm.rnr.model.ComRoleDept;
import com.henc.cdrs.sysm.rnr.model.SearchComRoleDept;
import com.henc.cdrs.sysm.rnr.repository.ComRoleDeptMapper;

@Service
@Transactional
public class ComRoleDeptService{

    @Autowired
    ComRoleDeptMapper comRoleDeptMapper; 
    
    public List<ComRoleDept> getComRoleDeptAllList(SearchComRoleDept searchComRoleDept){
        return comRoleDeptMapper.getComRoleDeptAllList(searchComRoleDept);
    }

    public List<ComRoleDept> getComRoleDeptPartialList(SearchComRoleDept searchComRoleDept){
        return comRoleDeptMapper.getComRoleDeptPartialList(searchComRoleDept);
    }

    public List<ComRoleDept> getComRoleDeptIndivisualList(SearchComRoleDept searchComRoleDept){
        return comRoleDeptMapper.getComRoleDeptIndivisualList(searchComRoleDept);
    }

}
