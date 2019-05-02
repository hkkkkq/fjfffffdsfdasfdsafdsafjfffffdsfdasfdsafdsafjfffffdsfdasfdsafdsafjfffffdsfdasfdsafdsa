/****************************************************************************************************
 * 
 * Copyright ⓒ 2017 kt corp. All rights reserved.
 *
 * This is a proprietary software of kt corp, and you may not use this file except in 
 * compliance with license agreement with kt corp. Any redistribution or use of this 
 * software, with or without modification shall be strictly prohibited without prior written 
 * approval of kt corp, and the copyright notice above does not evidence any actual or 
 * intended publication of such software. 
 * 
 * @Desction Vo 객체 임시 저장 처리 Service
 *
 *****************************************************************************************************
 * date        Modifier Description
 *****************************************************************************************************
 * 2017.09.20. 장종호
 * 
 *****************************************************************************************************/
package com.kt.kkos.common.content;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kt.kkos.common.util.Constants;
import com.kt.kkos.exception.ITLBusinessException;

@Service
public class SessionDataManager {

	private static final Logger logger = LoggerFactory.getLogger(SessionDataManager.class);
	

	/**
	 * HttpSession 에 다 건의 Attribute 등록 (Map 연동)
	 * @param param
	 * @throws ITLBusinessException
	 */
	public void setAttributes (Map<String, Object> param) throws ITLBusinessException
	{
		
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpSession httpSession = httpServletRequest.getSession(true);
		
		Set<String> keySet = param.keySet();
		Iterator<String> iter = keySet.iterator();
		String keyName;
		
		while (iter.hasNext()) {
			keyName = iter.next();
			httpSession.setAttribute(keyName, param.get(keyName));
		}
	}
	
	
	/**
	 * HttpSession 에 Attribute 단 건 등록
	 * @param param
	 * @throws ITLBusinessException
	 */
	public void setAttribute (String keyName, Object object) throws ITLBusinessException
	{
		
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession httpSession = httpServletRequest.getSession(true);
		
		httpSession.setAttribute(keyName, object);
	}
	
	
	/**
	 * HttpSession Attribute 단 건 삭제
	 * @param param
	 * @throws ITLBusinessException
	 */
	public void removeAttribute (String keyName) throws ITLBusinessException
	{
		
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession httpSession = httpServletRequest.getSession(true);
		
		httpSession.removeAttribute(keyName);
	}
	
	
	/**
	 * HttpSession 에 등록된 모든 Attribute 삭제
	 * @param param
	 * @throws ITLBusinessException
	 */
	@SuppressWarnings("rawtypes")
	public void removeAllAttributes () throws ITLBusinessException
	{
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession httpSession = httpServletRequest.getSession(true);
		
		Enumeration enu = httpSession.getAttributeNames();
		String keyName;
		while (enu.hasMoreElements()) {
			keyName = (String) enu.nextElement();
			httpSession.removeAttribute(keyName);
		}
	}
	
	
	
	/**
	 * HttpSession 에 관리되는 VO Attribute value 에 DTO 추가 또는 DTO Replace
	 * @param param
	 * @throws ITLBusinessException
	 */
	@SuppressWarnings("unchecked")
	public void mergeVO (JSONObject jsonObject) throws ITLBusinessException
	{
		
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpSession httpSession = httpServletRequest.getSession(true);
		JSONObject sessonJsonObject;
		
		if (httpSession.getAttribute(Constants.SESSION_ATTNAME_VO) != null) {
			sessonJsonObject = (JSONObject) httpSession.getAttribute(Constants.SESSION_ATTNAME_VO);
		}
		else {
			sessonJsonObject = new JSONObject();
		}
		
		Set<String> keySet = jsonObject.keySet();
		Iterator<String> iter = keySet.iterator();
		String keyName;
		
		while (iter.hasNext()) {
			keyName = iter.next();
			sessonJsonObject.put(keyName, jsonObject.get(keyName));
		}
		
		httpSession.setAttribute(Constants.SESSION_ATTNAME_VO, sessonJsonObject);
	}
	
	
//	@SuppressWarnings("unchecked")
//	public void mergeVO (HttpServletRequest httpServletRequest, JSONObject jsonObject) throws ITLBusinessException
//	{
//		
//		HttpSession httpSession = httpServletRequest.getSession(true);
//		
//		JSONObject sessonJsonObject;
//		
//		if (httpSession.getAttribute(Constants.SESSION_ATTNAME_VO) != null) {
//			sessonJsonObject = (JSONObject) httpSession.getAttribute(Constants.SESSION_ATTNAME_VO);
//		}
//		else {
//			sessonJsonObject = new JSONObject();
//		}
//		
//		Set<String> keySet = jsonObject.keySet();
//		Iterator<String> iter = keySet.iterator();
//		String keyName;
//		
//		if (sessonJsonObject == null) {
//			logger.debug("mergeVO... sessonJsonObject is NULL");
//			sessonJsonObject = new JSONObject();
//		}
//		
//		while (iter.hasNext()) {
//			keyName = iter.next();
//			logger.debug("mergeVO... jsonObject.get(" + keyName + ") : " + jsonObject.get(keyName));
//			sessonJsonObject.put(keyName, jsonObject.get(keyName));
//		}
//		
//		httpSession.setAttribute(Constants.SESSION_ATTNAME_VO, sessonJsonObject);
//	}
	
	
	/**
	 * HttpSession Attribute 관리되는 VO Replace 또는 추가
	 * @param param
	 * @throws ITLBusinessException
	 */
	public void replaceVO (JSONObject jsonObject) throws ITLBusinessException
	{
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpSession httpSession = httpServletRequest.getSession(true);
		
		httpSession.setAttribute(Constants.SESSION_ATTNAME_VO, jsonObject);
	}
	

	
	
	/**
	 * HttpSession 에 관리되는 Attribute 단 건 조회
	 * @param param
	 * @throws ITLBusinessException
	 */
	public Object getAttribute (String keyName) throws ITLBusinessException
	{
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpSession httpSession = httpServletRequest.getSession(true);
		
		return httpSession.getAttribute(keyName);
	}
	

	
	
	/**
	 * HttpSession 에 관리되는 VO Attribute 조회
	 * @param param
	 * @throws ITLBusinessException
	 */
	public Object getVO () throws ITLBusinessException
	{
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpSession httpSession = httpServletRequest.getSession(true);
		
		return httpSession.getAttribute(Constants.SESSION_ATTNAME_VO);
	}
	

	
	
	/**
	 * HttpSession ID 조회
	 */
	public String getID ()
	{
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpSession httpSession = httpServletRequest.getSession(true);
		
		return httpSession.getId();
	}
}

