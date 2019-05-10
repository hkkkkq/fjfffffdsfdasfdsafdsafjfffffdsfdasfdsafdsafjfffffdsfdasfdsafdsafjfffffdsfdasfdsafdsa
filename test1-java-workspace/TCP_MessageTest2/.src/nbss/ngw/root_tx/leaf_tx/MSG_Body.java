/*
 * 작성된 날짜: 2015. 6. 12
 * Copyright (c) 2015 TmaxSoft co., Ltd. All rights reserved.
 */
package nbss.ngw.root_tx.leaf_tx;

import proframe.dto.AbstractPfmDataObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import proframe.tool.record.common.*;
import com.tmax.promapper.engine.base.ResourceMeta;


/**
 * @file              nbss.ngw.root_tx.leaf_tx.MSG_Body.java
 * @filetype          java source file
 * @brief			
 * @version           1.0
 * @history
 * 성 명              일 자                     근 거 자 료                      변 경 내 용
 * -----------        --------                  --------------                   --------------------------	
 * InfiniLink :       2015. 6. 12                InfiniLink 개발 :                신규 작성
 * 
 */

public class MSG_Body extends AbstractPfmDataObject implements ResourceMeta {
	private static final long serialVersionUID= 1L;
	public MSG_Body() {
			super();
	}
	private boolean _setFlag = false;
	private String data2 = null;
	
	public String getData2() {
		return data2;
	}
	
	public void setData2(String data2) {
		this.data2 = data2;
		_setFlag = true;
	}
	

			
	private String data3 = null;
	
	public String getData3() {
		return data3;
	}
	
	public void setData3(String data3) {
		this.data3 = data3;
		_setFlag = true;
	}
	

			
	public Object clone() {
		MSG_Body copyObj = new MSG_Body();	
		clone(copyObj);		
		return copyObj;
	}
	public void clone(proframe.dto.DataObject __mSG_Body) {
		MSG_Body _mSG_Body = (MSG_Body) __mSG_Body;
		_mSG_Body.setData2(data2);
		_mSG_Body.setData3(data3);
	}
	public String toString() {
		StringBuilder buffer = new StringBuilder();

			toString_0(buffer);
		
		return buffer.toString();
	}
	
	private void toString_0(StringBuilder buffer) {
		int _size = 0; // field가 array인 경우 array size를 저장하는 변수
		javax.swing.text.MaskFormatter mf;
			buffer.append("data2 : ").append(data2).append("\n");
			buffer.append("data3 : ").append(data3).append("\n");
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
			buffer.append("data2 : ").append(data2).append("\n");
		}
		if(buffer.length() < limit) {
			buffer.append("data3 : ").append(data3).append("\n");
		}
	}	
	private static final Map fieldPropertyMap = null;
	
/*	static {
		fieldPropertyMap = new LinkedHashMap(2);
		fieldPropertyMap.put("data2"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, -1, null, null));
		fieldPropertyMap.put("data3"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, -1, null, null));
	}*/
	
	public Map getFieldPropertyMap() {
		return fieldPropertyMap;
	}
	
	public static Map getFieldPropertyMapByStatic() {
		return fieldPropertyMap;
	}
	private static final List<String> fieldNameList = new ArrayList<String>(2);
	
	static {		
		fieldNameList.add("data2");
		fieldNameList.add("data3");
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
			case 95356360 : return getData2();
			case 95356361 : return getData3();
			default : return null;
		}
	}
	public void set(String fieldName, Object arg) {
		int hashCode = fieldName.hashCode();
		
		switch(hashCode) {
			case 95356360 : setData2((String)arg);break;
			case 95356361 : setData3((String)arg);break;
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
			return "MSG_Body";
		} else if (input.equalsIgnoreCase(META_VERSION_NO)){
			return "34";
		} else if (input.equalsIgnoreCase(META_LOGICAL_NAME)){
			return "MSG_Body";
		} else if (input.equalsIgnoreCase(META_RESOURCE_ID)){
			return "nbss.ngw.root_tx.leaf_tx:MSG_Body.umsg";
		} else if (input.equalsIgnoreCase(META_RESOURCE_TYPE)){
			return "STRUCTURE";
		} else if (input.equalsIgnoreCase(META_RESOURCE_PATH)){
			return "nbss.ngw.root_tx.leaf_tx";
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
