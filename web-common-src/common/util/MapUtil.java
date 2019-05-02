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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.kt.kkos.exception.ITLBusinessException;
import com.kt.kkos.itl.model.common.KkosStdVOType;
import com.kt.kkos.itl.model.common.TrtErrInfoDTO;

/**
 * @author Administrator
 *
 */
public class MapUtil
{

	/**
	 * <pre>
	 * 요청한 타입으로 캐스팅 함.
	 * </pre>
	 *
	 * @param inObject
	 * @param clazz
	 * @return
	 */
	private static <V> V castVO(Object inObject, Class<V> clazz) {
		
		try {
			return clazz.cast(inObject);
		} catch (ClassCastException e) {
			return null;
		}
		
	}
	
	/**
	 * <pre>
	 * List Type인지 확인함. List type은 내부 객체를 다시 순회 함.
	 * </pre>
	 *
	 * @param field
	 * @param inVO
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static boolean canConvertType(Field field, Object inVO) throws IllegalArgumentException, IllegalAccessException {
		
		boolean canConvertType = false;
		Object fieldObject = field.get(inVO);
		String clazzName = "";
		
		if ( fieldObject == null ) {
			
			return true;
		} else {
			
			if ( "serialVersionUID".equals(field.getName()) ) {
				
				return false;
			}
			
			clazzName = fieldObject.getClass().getName();
					
			if ( clazzName.indexOf("String") > -1 ) {
				
				canConvertType = true;
			}
			else if ( clazzName.indexOf("Integer") > -1 ) {
								
				canConvertType = true;
			}
			else if ( clazzName.indexOf("Long") > -1 ) {
				
				canConvertType = true;
			}
			else if ( clazzName.indexOf("Double") > -1 ) {
				
				canConvertType = true;
			}
			else if ( clazzName.indexOf("Float") > -1 ) {
				
				canConvertType = true;
			}
			else {
				canConvertType = false;
			}
		}
		
		return canConvertType;
	}
	
	
	/**
	 * <pre>
	 * ITLBusinessException에 대한 TrtErrInfoDTO로 메세지 복제
	 * </pre>
	 *
	 * @param e
	 * @param trtErrInfoDTO
	 */
	public static void copyErrInfo(ITLBusinessException e, TrtErrInfoDTO trtErrInfoDTO) {
		
		if ( e == null || trtErrInfoDTO == null ) {
			
			return;
		}
		
		trtErrInfoDTO.setResponseType(e.getResponseType());
		trtErrInfoDTO.setResponseCode(e.getResponseCode());
		trtErrInfoDTO.setResponseTitle(e.getResponseTitle());
		trtErrInfoDTO.setResponseBasc(e.getResponseBasc());
	}
	
	
	/**
	 * <pre>
	 * List Type인지 확인함. List type은 내부 객체를 다시 순회 함.
	 * </pre>
	 *
	 * @param field
	 * @param inVO
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static boolean isListType(Field field, Object inVO) throws IllegalArgumentException, IllegalAccessException {
		
		boolean isListType = false;
		
		field.setAccessible(true);
		Object fieldObject = field.get(inVO);
		
		
		if ( fieldObject != null ) {
		
			for ( Class<?> ifs : fieldObject.getClass().getInterfaces() ) {
				
				// VO 객체 안에 있는 List는 KkosStdVOType객체라고 생각함.
				if ( ifs.getName().indexOf("List") > -1 ) {
					
					isListType = true;
				}
			}
		}
		
		return isListType;
	}
	
	/**
	 * <pre>
	 * List Type인지 확인함. List type은 내부 객체를 다시 순회 함.
	 * </pre>
	 *
	 * @param clazz
	 * @return
	 */
	public static boolean isListType(Class<?> clazz) {
		
		boolean isListType = false;
		
		if ( clazz != null ) {
			
			for ( Class<?> ifs : clazz.getInterfaces() ) {
				
				// VO 객체 안에 있는 List는 KkosStdVOType객체라고 생각함.
				if ( ifs.getName().indexOf("List") > -1 ) {
					
					isListType = true;
				}
			}
		}
		
		return isListType;
	}
	
	
	/**
	 * <pre>
	 * @KkosStdVOType을 확장한 케이스 인지 확인함. 해당 확장 객체만 in/Out 객체로 인식하고, 마스킹 처리 함.
	 * </pre>
	 *
	 * @param clazz
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static boolean isKkosStdVOType(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException {
		
		boolean isKkosStdVO = false;
		
		Class<?> parentClazz = clazz.getSuperclass();
		while ( parentClazz != null ) {
			
			if ( parentClazz.getName().indexOf("KkosStdVOType") > -1 ) {
				
				isKkosStdVO = true;
				break;
			}
			
			parentClazz = parentClazz.getSuperclass();
		}
		
		return isKkosStdVO;
	}
	
	
	/**
	 * <pre>
	 * @KkosStdVOType을 확장한 케이스 인지 확인함. 해당 확장 객체만 in/Out 객체로 인식하고, 마스킹 처리 함.
	 * </pre>
	 *
	 * @param field
	 * @param inVO
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static boolean isKkosStdVOType(Field field, Object inVO) throws IllegalArgumentException, IllegalAccessException {
		
		boolean isKkosStdVO = false;
		
		field.setAccessible(true);
		Object fieldObject = field.get(inVO);
		
		
		if ( fieldObject != null ) {
			
			Class<?> clazz = fieldObject.getClass().getSuperclass();
			while ( clazz != null ) {
				
				if ( clazz.getName().indexOf("KkosStdVOType") > -1 ) {
					
					isKkosStdVO = true;
					break;
				}
				
				clazz = clazz.getSuperclass();
			}
		}
		
		return isKkosStdVO;
	}
	

	/**
	 * <pre>
	 * 필드 중에 List 타입이 있는 경우 변환하지 않음.
	 * </pre>
	 *
	 * @param inObject
	 * @return
	 * @throws ITLBusinessException 
	 */
	public static Map<String, Object> convertToMap(Object inObject) throws ITLBusinessException {
		
		Map<String, Object> map = new HashMap<>();
		
		Object resultObject = castVO(inObject, inObject.getClass());
		
		try
		{
			Field[] allFields = resultObject.getClass().getDeclaredFields();
			for ( Field field : allFields ) {
				
				field.setAccessible(true);
				
				// 변환이 가능한 타입만 처리
				if ( canConvertType(field, resultObject) ) {
					
					String fieldName	= field.getName();
					Object fieldValue	= field.get(resultObject);
					
					map.put(fieldName, fieldValue);
				}
				
				field.setAccessible(false);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			
			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR, "KKOS0001", "시스템 오류", "내부기능에 오류가 발생되었습니다.", "copyDtoToDto 함수 진행중 오류 발생");
		}
		
		return map;
	}
	
	public static void copyDtoToDto(final KkosStdVOType fromDto, final KkosStdVOType toDto) throws ITLBusinessException {
		
		try {
			final Map<?, ?> fromMap = PropertyUtils.describe(fromDto);
			final Map<?, ?> toMap = PropertyUtils.describe(toDto);
			
			for (final Object propName : fromMap.keySet())
			{
				if ( "class".equals(propName) ) continue;
				
				if (toMap.containsKey(propName)) {
					
					PropertyUtils.setProperty(toDto, (String)propName, fromMap.get(propName));
				}
			}
			
		} catch ( IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
			
			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR, "KKOS0001", "시스템 오류", "내부기능에 오류가 발생되었습니다.", "copyDtoToDto 함수 진행중 오류 발생");
		}	
	}
	
	public static void copyDtoToDto(final Object fromDto, final Object toDto) throws ITLBusinessException {
		
		try {
			final Map<?, ?> fromMap = PropertyUtils.describe(fromDto);
			final Map<?, ?> toMap = PropertyUtils.describe(toDto);
			
			for (final Object propName : fromMap.keySet())
			{
				if ( "class".equals(propName) ) continue;
				
				if (toMap.containsKey(propName)) {
					
					PropertyUtils.setProperty(toDto, (String)propName, fromMap.get(propName));
				}
			}
			
		} catch ( IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
			
			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR, "KKOS0001", "시스템 오류", "내부기능에 오류가 발생되었습니다.", "copyDtoToDto 함수 진행중 오류 발생");
		}
	}
	
	
	
	public static Object convertToObject(Map<String, Object> fromMap, Class<?> clazz) throws ITLBusinessException {

		Object returnObj = null;

		try {
			
			if ( !isKkosStdVOType(clazz) ) {
				
				throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR, "KKOS0001", "시스템 오류", "내부기능에 오류가 발생되었습니다.", "KKOSStdVOType 인터페이스를 상속하지 않은 DTO는 변환이 제공되지 않습니다.");
			}
			
			returnObj = clazz.newInstance();
			
			final Map<?, ?> toMap = PropertyUtils.describe(returnObj);
			
			for (final Object propName : toMap.keySet()) {
				
				if ( "class".equals(propName) ) continue;
				
				if ( fromMap.containsKey(propName) && !StringUtil.isNull(fromMap.get(propName)) ) {
					
					if ( !isListType(returnObj.getClass().getDeclaredField((String)propName), returnObj) ) {
						PropertyUtils.setProperty(returnObj, (String)propName, String.valueOf(fromMap.get(propName)));
					}
				}
			}

		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException | SecurityException e) {
			
			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR, "KKOS0001", "시스템 오류", "내부기능에 오류가 발생되었습니다.", "convertToObject 함수 진행중 오류 발생");
		}
		
		return returnObj;
	}
	
}
