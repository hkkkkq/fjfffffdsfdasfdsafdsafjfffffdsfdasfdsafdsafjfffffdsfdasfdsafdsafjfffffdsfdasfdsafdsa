/****************************************************************************************************
 * 
 *  Copyright ⓒ 2015 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 *
 * @Desction Vo 객체 임시 저장 처리 Service
 *
 *****************************************************************************************************
 * date        Modifier Description
 *****************************************************************************************************
 * 2017.09.20. 장종호
 * 
 *****************************************************************************************************/
package com.kt.kkos.common.util;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.kkos.exception.ITLBusinessException;


/**
 * @author Administrator
 * Json Object Converter 
 */
public final class ObjectConverter
{
	private static final Logger logger = LoggerFactory.getLogger(ObjectConverter.class);
	
	/**
	 * Convert byte Array To JSONObject
	 */
	public static JSONObject byteToJson (final byte [] byteArray) throws ITLBusinessException
	{
		try {
			if ( byteArray.length > 1) {
				return (JSONObject)(new JSONParser().parse(new String(byteArray)));
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	
	
	/**
	 * Convert StringBuilder To JSONObject
	 */
	public static JSONObject stringBuilderToJson (final StringBuilder stringBuilder) throws ITLBusinessException
	{
		try {
			if ( ( stringBuilder != null) && (stringBuilder.length() > 1) ) {
				return (JSONObject)(new JSONParser().parse(stringBuilder.toString()));
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	
	
	/**
	 * Convert String To JSONObject
	 */
	public static JSONObject stringToJson (final String string) throws ITLBusinessException
	{
		try {
			if ( ( string != null) && (string.length() > 1) ) {
				return (JSONObject)(new JSONParser().parse(string));
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	
	
	/**
	 * Convert String To JSONObject
	 */
	public static JSONArray stringToJsonArray (final String string) throws ITLBusinessException
	{
		try {
			if ( ( string != null) && (string.length() > 1) ) {
				return (JSONArray)(new JSONParser().parse(string));
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	
	
	/**
	 * Convert Object(VO) To JSONObject
	 */
	public static JSONObject objectToJson (final Object object) throws ITLBusinessException
	{
		try {
			String jsonString = new ObjectMapper().writeValueAsString(object);
			
			if ( ( jsonString != null) && (jsonString.length() > 1) ) {
				return (JSONObject)(new JSONParser().parse(jsonString));
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	
	
	/**
	 * Convert JSONObject To Object(VO)
	 */
	public static Object jsonToObject (final JSONObject jsonObject, final Class<?> clasz) throws ITLBusinessException
	{
		try {
			return clasz.cast(new ObjectMapper().readValue(jsonObject.toString(), clasz));
//			return new ObjectMapper().readValue(jsonObject.toString(), clasz);
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	
	
	/**
	 * Convert String(json Format) To Object(DTO)
	 */
	public static Object stringToObject (final String string, final Class<?> clasz) throws ITLBusinessException
	{
		try {
			// 필드명 대/소문자 구분함
			return clasz.cast(new ObjectMapper().readValue(string, clasz));
			
			// 필드명 대/소문자 구분하지 않음
//			return clasz.cast(new ObjectMapper().configure(Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	
	
	/**
	 * Convert String(json Format) To Object(DTO)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList stringToArrayList (final String string, final Class<?> clasz) throws ITLBusinessException
	{
		try {
			
			ArrayList arrayList = new ArrayList();
			
			JSONArray jsonArray = (JSONArray) new JSONParser().parse(string);
			
//			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
			
			for (int ii=0; ii<jsonArray.size(); ii++) {
				logger.debug("jsonArray(" + ii + ") : " + jsonArray.get(ii));
				arrayList.add(clasz.cast(jsonToObject((JSONObject)jsonArray.get(ii), clasz)));

//				arrayList.add(gson.fromJson(jsonArray.get(ii).toString(), clasz));
			}
			
			// 필드명 대/소문자 구분함
			return arrayList;
			
			// 필드명 대/소문자 구분하지 않음
//			return clasz.cast(new ObjectMapper().configure(Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	/**
	 * Convert String(ugly json Format) To String(pretty json Format)
	 */
	public static String prettyPrintJsonString(String uglyStringJson) {
		
		String prettyString = "";
		ObjectMapper mapper = new ObjectMapper();
		
		
		Object json;
		try {
			json = mapper.readValue(uglyStringJson, Object.class);
			prettyString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("### uglyStringJson to prettyStringJson Parsing Fail!!!");
			ITLBusinessException.printErrorStack(e);
		}
		
		return prettyString;
	}
	
}
