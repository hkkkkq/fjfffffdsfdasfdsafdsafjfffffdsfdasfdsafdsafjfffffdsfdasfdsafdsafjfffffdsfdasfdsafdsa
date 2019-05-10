/*
 * 작성된 날짜: 2015. 5. 27
 * Copyright (c) 2015 TmaxSoft co., Ltd. All rights reserved.
 */
package nbss.ngw.root_tx;

import proframe.dto.AbstractPfmDataObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import proframe.tool.record.common.*;
import com.tmax.promapper.engine.base.ResourceMeta;


/**
 * @file              nbss.ngw.root_tx.MSG_Header.java
 * @filetype          java source file
 * @brief			
 * @version           1.0
 * @history
 * 성 명              일 자                     근 거 자 료                      변 경 내 용
 * -----------        --------                  --------------                   --------------------------	
 * InfiniLink :       2015. 5. 27                InfiniLink 개발 :                신규 작성
 * 
 */

public class MSG_Header extends AbstractPfmDataObject implements ResourceMeta {
	private static final long serialVersionUID= 1L;
	public MSG_Header() {
			super();
	}
	private boolean _setFlag = false;
	private String data1 = null;
	
	public String getData1() {
		return data1;
	}
	
	public void setData1(String data1) {
		this.data1 = data1;
		_setFlag = true;
	}
	

			
	public Object clone() {
		MSG_Header copyObj = new MSG_Header();	
		clone(copyObj);		
		return copyObj;
	}
	public void clone(proframe.dto.DataObject __mSG_Header) {
		MSG_Header _mSG_Header = (MSG_Header) __mSG_Header;
		_mSG_Header.setData1(data1);
	}
	public String toString() {
		StringBuilder buffer = new StringBuilder();

			toString_0(buffer);
		
		return buffer.toString();
	}
	
	private void toString_0(StringBuilder buffer) {
		int _size = 0; // field가 array인 경우 array size를 저장하는 변수
		javax.swing.text.MaskFormatter mf;
			buffer.append("data1 : ").append(data1).append("\n");
	}	
	public String toString(int limit) {
		StringBuilder buffer = new StringBuilder();
		int _size = 0; // field가 array인 경우 array size를 저장하는 변수
		
			toString_0(buffer, limit);
		
		return buffer.toString();
	}
	
	private void toString_0(StringBuilder buffer, int limit) {
		int _size = 0; // field가 array인 경우 array size를 저장하는 변수
		javax.swing.text.MaskFormatter mf;
		if(buffer.length() < limit) {
			buffer.append("data1 : ").append(data1).append("\n");
		}
	}	
	private static final Map fieldPropertyMap = null;
	
/*	static {
		fieldPropertyMap = new LinkedHashMap(1);
		fieldPropertyMap.put("data1"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, 4 , null, null));
	}*/
	
	public Map getFieldPropertyMap() {
		return fieldPropertyMap;
	}
	
	public static Map getFieldPropertyMapByStatic() {
		return fieldPropertyMap;
	}
	private static final List<String> fieldNameList = new ArrayList<String>(1);
	
	static {		
		fieldNameList.add("data1");
	}
	
	public List<String> getFieldNameList(){
		return fieldNameList;
	}
	
	public boolean getSetFlag(){
		return _setFlag;
	}

	public Object get(String fieldName) {
		int hashCode = fieldName.hashCode();
		
		switch(hashCode) {
			case 95356359 : return getData1();
			default : return null;
		}
	}
	public void set(String fieldName, Object arg) {
		int hashCode = fieldName.hashCode();
		
		switch(hashCode) {
			case 95356359 : setData1((String)arg);break;
			default : return;
		}
	}
	/**
	 * @param input 
	 *  values of input variable are 
	 * 		   		       			  physicalname
	 * 		   		      			  versionno
	 * 		   		      			  logicalname
	 * 		   		      			  resourceid
	 * 								  resourceType
	 * 								  resourcePath
	 * 								  resourceGroup
	 * @return 
	 */
	public String getMetaData(String input) {

		if (input.equalsIgnoreCase(META_PHYSICAL_NAME)) {
			return "MSG_Header";
		} else if (input.equalsIgnoreCase(META_VERSION_NO)){
			return "16";
		} else if (input.equalsIgnoreCase(META_LOGICAL_NAME)){
			return "MSG_Header";
		} else if (input.equalsIgnoreCase(META_RESOURCE_ID)){
			return "nbss.ngw.root_tx:MSG_Header.umsg";
		} else if (input.equalsIgnoreCase(META_RESOURCE_TYPE)){
			return "STRUCTURE";
		} else if (input.equalsIgnoreCase(META_RESOURCE_PATH)){
			return "nbss.ngw.root_tx";
		} else if (input.equalsIgnoreCase(META_RESOURCE_GROUP)){
			return "";
		} else {
			String msg = "Expecting one of " + META_PHYSICAL_NAME + ", "
					+ META_VERSION_NO + ", " + META_LOGICAL_NAME + ", "
					+ META_RESOURCE_ID + ", " + META_RESOURCE_TYPE + ", "
					+ META_RESOURCE_PATH + ", " + META_RESOURCE_GROUP;
			throw new IllegalArgumentException(msg);
		}
	}
}
