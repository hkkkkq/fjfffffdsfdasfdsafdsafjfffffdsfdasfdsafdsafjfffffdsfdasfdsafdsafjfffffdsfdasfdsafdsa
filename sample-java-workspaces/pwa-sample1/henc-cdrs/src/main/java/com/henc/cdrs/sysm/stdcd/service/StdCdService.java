package com.henc.cdrs.sysm.stdcd.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.sysm.stdcd.model.AttributeDefi;
import com.henc.cdrs.sysm.stdcd.model.AttributeVal;
import com.henc.cdrs.sysm.stdcd.model.StdCd;
import com.henc.cdrs.sysm.stdcd.model.StdDetlCd;
import com.henc.cdrs.sysm.stdcd.repository.StdCdMapper;

@Service
@Transactional
public class StdCdService{

	@Autowired
	StdCdMapper stdCdMapper;
	
	@Autowired
	HttpServletRequest request;
	
    public List<CamelCaseMap> getComStdCdList(StdCd stdCd) {
        return stdCdMapper.selectComStdCdList(stdCd);
    }      
    
    public void saveComStdCdList(List<StdCd> stdCds) {
        if (CollectionUtils.isNotEmpty(stdCds)) {
            for (StdCd stdCd : stdCds) {                
                switch (stdCd.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:       
                        stdCdMapper.insertComStdCdList(stdCd);
                        stdCdMapper.insertComStdCdNameSpaceList(stdCd);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        stdCdMapper.updateComStdCdList(stdCd);
                        break;
                    case IBSheetRowStatus.DELETED:
                        stdCdMapper.deleteComStdCdList(stdCd);
                        
                        stdCdMapper.deleteComStdDetlCdNameSpaceList(stdCd);
                        stdCdMapper.deleteComStdCdNameSpaceList(stdCd);
                        stdCdMapper.deleteComStdCdDetlList(stdCd);
                        stdCdMapper.deleteComStdCdList(stdCd);                        
                        break;
                    default:
                        throw new UnsupportedRowTypeException(stdCd.getRowStatus());
                }
            }
        }    
    }    
    
    public void insertComStdCdNameSpaceList(List<StdCd> insList) {
        for(int i=0;i<insList.size();i++){
            StdCd stdCd = (StdCd)insList.get(i);          
            stdCdMapper.insertComStdCdNameSpaceList(stdCd);
        }    
    }      
    
    public void deleteComStdDetlCdNameSpaceList(List<StdCd> delList) {
        for(int i=0;i<delList.size();i++){
            StdCd stdCd = (StdCd)delList.get(i);          
            stdCdMapper.deleteComStdDetlCdNameSpaceList(stdCd);
        }    
    }          
    
    public void deleteComStdCdNameSpaceList(List<StdCd> delList) {
        for(int i=0;i<delList.size();i++){
            StdCd stdCd = (StdCd)delList.get(i);          
            stdCdMapper.deleteComStdCdNameSpaceList(stdCd);
        }    
    }     
    
    public void deleteComStdCdDetlList(List<StdCd> delList) {
        for(int i=0;i<delList.size();i++){
            StdCd stdCd = (StdCd)delList.get(i);          
            stdCdMapper.deleteComStdCdDetlList(stdCd);
        }    
    }  
    
    public void deleteComStdCdList(List<StdCd> delList) {
        for(int i=0;i<delList.size();i++){
            StdCd stdCd = (StdCd)delList.get(i);          
            stdCdMapper.deleteComStdCdList(stdCd);
        }    
    }      
    
    public List<CamelCaseMap> getComStdDetlCdList(StdDetlCd stdDetlCd) {
        return stdCdMapper.selectComStdDetlCdListForManage(stdDetlCd);
    }        

    public List<CamelCaseMap> getComStdCdEtcInfoList(AttributeDefi attributeDefi) {
        return stdCdMapper.selectComAttributeDefi(attributeDefi);
    }            
    
    public void saveComAttributeDefi(List<AttributeDefi> attributeDefis) {
        if (CollectionUtils.isNotEmpty(attributeDefis)) {
            for (AttributeDefi attributeDefi : attributeDefis) {                
                switch (attributeDefi.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:       
                        stdCdMapper.insertComAttributeDefiList(attributeDefi);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        stdCdMapper.updateComAttributeDefiList(attributeDefi);
                        break;
                    case IBSheetRowStatus.DELETED:
                        stdCdMapper.deleteComAttributeDefiList(attributeDefi);                   
                        break;
                    default:
                        throw new UnsupportedRowTypeException(attributeDefi.getRowStatus());
                }
            }
        }           
    }    
    
    public void deleteComStdCdDetlNameSpaceList(List<StdDetlCd> delList) {
        for(int i=0;i<delList.size();i++){       
            stdCdMapper.deleteComStdCdDetlNameSpaceList((StdDetlCd)delList.get(i));
        }    
    }       
        
    public void insertComStdDetlCdNameSpaceList(List<StdDetlCd> insList) {
        for(int i=0;i<insList.size();i++){
            stdCdMapper.insertComStdDetlCdNameSpaceList((StdDetlCd)insList.get(i));
        }    
    }     
    
    public void saveComStdDetlCdList(List<StdDetlCd> stdDetlCds) {    
        if (CollectionUtils.isNotEmpty(stdDetlCds)) {
            for (StdDetlCd stdDetlCd : stdDetlCds) {                
                switch (stdDetlCd.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:       
                        stdCdMapper.insertComStdDetlCdList(stdDetlCd);
                        stdCdMapper.insertComStdDetlCdNameSpaceList(stdDetlCd);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        stdCdMapper.updateComStdDetlCdList(stdDetlCd);
                        break;
                    case IBSheetRowStatus.DELETED:
                        stdCdMapper.deleteComStdDetlCdList(stdDetlCd);                  
                        break;
                    default:
                        throw new UnsupportedRowTypeException(stdDetlCd.getRowStatus());
                }
            }
        }                
    }
    
    public void deleteComStdDetlCdList(List<StdDetlCd> delList) {
        for(int i=0;i<delList.size();i++){
            stdCdMapper.deleteComStdDetlCdList(delList.get(i));
        }         
    }
    
    public List<CamelCaseMap> getComAttributeValList(AttributeVal attributeVal) {
        return stdCdMapper.selectComAttributeValList(attributeVal);
    }     
    
    public void saveComAttributeVal(List<AttributeVal> attributeVals) {
        if (CollectionUtils.isNotEmpty(attributeVals)) {
            for (AttributeVal attributeVal : attributeVals) {  
                switch (attributeVal.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:       
                        stdCdMapper.updateComAttributeValList(attributeVal);
                        break;                
                    case IBSheetRowStatus.UPDATED:
                        stdCdMapper.updateComAttributeValList(attributeVal);
                        break;
                    case IBSheetRowStatus.DELETED:
                        stdCdMapper.deleteComAttributeValList(attributeVal);                  
                        break;
                    default:
                        throw new UnsupportedRowTypeException(attributeVal.getRowStatus());
                }
            }
        }        
    }
}