/*
 * 작성된 날짜: 2015. 6. 5
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
 * @file              nbss.ngw.root_tx.leaf_tx.MSG_CustomLog.java
 * @filetype          java source file
 * @brief			
 * @version           1.0
 * @history
 * 성 명              일 자                     근 거 자 료                      변 경 내 용
 * -----------        --------                  --------------                   --------------------------	
 * InfiniLink :       2015. 6. 5                InfiniLink 개발 :                신규 작성
 * 
 */

public class MSG_CustomLog extends AbstractPfmDataObject implements ResourceMeta {
	private static final long serialVersionUID= 1L;
	public MSG_CustomLog() {
			super();
	}
	private boolean _setFlag = false;
	private String GLOBAL_NO = null;
	
	public String getGLOBAL_NO() {
		return GLOBAL_NO;
	}
	
	public void setGLOBAL_NO(String GLOBAL_NO) {
		this.GLOBAL_NO = GLOBAL_NO;
		_setFlag = true;
	}
	

			
	private String SVR_IP = null;
	
	public String getSVR_IP() {
		return SVR_IP;
	}
	
	public void setSVR_IP(String SVR_IP) {
		this.SVR_IP = SVR_IP;
		_setFlag = true;
	}
	

			
	private String SVR_HOSTNAME = null;
	
	public String getSVR_HOSTNAME() {
		return SVR_HOSTNAME;
	}
	
	public void setSVR_HOSTNAME(String SVR_HOSTNAME) {
		this.SVR_HOSTNAME = SVR_HOSTNAME;
		_setFlag = true;
	}
	

			
	private String SVR_INSTANCE_ID = null;
	
	public String getSVR_INSTANCE_ID() {
		return SVR_INSTANCE_ID;
	}
	
	public void setSVR_INSTANCE_ID(String SVR_INSTANCE_ID) {
		this.SVR_INSTANCE_ID = SVR_INSTANCE_ID;
		_setFlag = true;
	}
	

			
	private String IF_TYPE_CD = null;
	
	public String getIF_TYPE_CD() {
		return IF_TYPE_CD;
	}
	
	public void setIF_TYPE_CD(String IF_TYPE_CD) {
		this.IF_TYPE_CD = IF_TYPE_CD;
		_setFlag = true;
	}
	

			
	private String PROCESS_STATUS_CD = null;
	
	public String getPROCESS_STATUS_CD() {
		return PROCESS_STATUS_CD;
	}
	
	public void setPROCESS_STATUS_CD(String PROCESS_STATUS_CD) {
		this.PROCESS_STATUS_CD = PROCESS_STATUS_CD;
		_setFlag = true;
	}
	

			
	private String REG_DATE = null;
	
	public String getREG_DATE() {
		return REG_DATE;
	}
	
	public void setREG_DATE(String REG_DATE) {
		this.REG_DATE = REG_DATE;
		_setFlag = true;
	}
	

			
	private String REG_DT = null;
	
	public String getREG_DT() {
		return REG_DT;
	}
	
	public void setREG_DT(String REG_DT) {
		this.REG_DT = REG_DT;
		_setFlag = true;
	}
	

			
	public Object clone() {
		MSG_CustomLog copyObj = new MSG_CustomLog();	
		clone(copyObj);		
		return copyObj;
	}
	public void clone(proframe.dto.DataObject __mSG_CustomLog) {
		MSG_CustomLog _mSG_CustomLog = (MSG_CustomLog) __mSG_CustomLog;
		_mSG_CustomLog.setGLOBAL_NO(GLOBAL_NO);
		_mSG_CustomLog.setSVR_IP(SVR_IP);
		_mSG_CustomLog.setSVR_HOSTNAME(SVR_HOSTNAME);
		_mSG_CustomLog.setSVR_INSTANCE_ID(SVR_INSTANCE_ID);
		_mSG_CustomLog.setIF_TYPE_CD(IF_TYPE_CD);
		_mSG_CustomLog.setPROCESS_STATUS_CD(PROCESS_STATUS_CD);
		_mSG_CustomLog.setREG_DATE(REG_DATE);
		_mSG_CustomLog.setREG_DT(REG_DT);
	}
	public String toString() {
		StringBuilder buffer = new StringBuilder();

			toString_0(buffer);
		
		return buffer.toString();
	}
	
	private void toString_0(StringBuilder buffer) {
		int _size = 0; // field가 array인 경우 array size를 저장하는 변수
		javax.swing.text.MaskFormatter mf;
			buffer.append("GLOBAL_NO : ").append(GLOBAL_NO).append("\n");
			buffer.append("SVR_IP : ").append(SVR_IP).append("\n");
			buffer.append("SVR_HOSTNAME : ").append(SVR_HOSTNAME).append("\n");
			buffer.append("SVR_INSTANCE_ID : ").append(SVR_INSTANCE_ID).append("\n");
			buffer.append("IF_TYPE_CD : ").append(IF_TYPE_CD).append("\n");
			buffer.append("PROCESS_STATUS_CD : ").append(PROCESS_STATUS_CD).append("\n");
			buffer.append("REG_DATE : ").append(REG_DATE).append("\n");
			buffer.append("REG_DT : ").append(REG_DT).append("\n");
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
			buffer.append("GLOBAL_NO : ").append(GLOBAL_NO).append("\n");
		}
		if(buffer.length() < limit) {
			buffer.append("SVR_IP : ").append(SVR_IP).append("\n");
		}
		if(buffer.length() < limit) {
			buffer.append("SVR_HOSTNAME : ").append(SVR_HOSTNAME).append("\n");
		}
		if(buffer.length() < limit) {
			buffer.append("SVR_INSTANCE_ID : ").append(SVR_INSTANCE_ID).append("\n");
		}
		if(buffer.length() < limit) {
			buffer.append("IF_TYPE_CD : ").append(IF_TYPE_CD).append("\n");
		}
		if(buffer.length() < limit) {
			buffer.append("PROCESS_STATUS_CD : ").append(PROCESS_STATUS_CD).append("\n");
		}
		if(buffer.length() < limit) {
			buffer.append("REG_DATE : ").append(REG_DATE).append("\n");
		}
		if(buffer.length() < limit) {
			buffer.append("REG_DT : ").append(REG_DT).append("\n");
		}
	}	
	private static final Map fieldPropertyMap = null;
	
/*	static {
		fieldPropertyMap = new LinkedHashMap(8);
		fieldPropertyMap.put("GLOBAL_NO"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, -1, null, null));
		fieldPropertyMap.put("SVR_IP"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, -1, null, null));
		fieldPropertyMap.put("SVR_HOSTNAME"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, -1, null, null));
		fieldPropertyMap.put("SVR_INSTANCE_ID"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, -1, null, null));
		fieldPropertyMap.put("IF_TYPE_CD"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, -1, null, null));
		fieldPropertyMap.put("PROCESS_STATUS_CD"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, -1, null, null));
		fieldPropertyMap.put("REG_DATE"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, -1, null, null));
		fieldPropertyMap.put("REG_DT"
							, FieldPropertyFactory.getFieldProperty( FieldProperty.TYPE_ABSTRACT_STRING, -1, null, null));
	}*/
	
	public Map getFieldPropertyMap() {
		return fieldPropertyMap;
	}
	
	public static Map getFieldPropertyMapByStatic() {
		return fieldPropertyMap;
	}
	private static final List<String> fieldNameList = new ArrayList<String>(8);
	
	static {		
		fieldNameList.add("GLOBAL_NO");
		fieldNameList.add("SVR_IP");
		fieldNameList.add("SVR_HOSTNAME");
		fieldNameList.add("SVR_INSTANCE_ID");
		fieldNameList.add("IF_TYPE_CD");
		fieldNameList.add("PROCESS_STATUS_CD");
		fieldNameList.add("REG_DATE");
		fieldNameList.add("REG_DT");
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
			case -1120391203 : return getGLOBAL_NO();
			case -1836788457 : return getSVR_IP();
			case -450927581 : return getSVR_HOSTNAME();
			case 1184089877 : return getSVR_INSTANCE_ID();
			case -1205197212 : return getIF_TYPE_CD();
			case 764680670 : return getPROCESS_STATUS_CD();
			case 112266393 : return getREG_DATE();
			case -1881445317 : return getREG_DT();
			default : return null;
		}
	}
	public void set(String fieldName, Object arg) {
		int hashCode = fieldName.hashCode();
		
		switch(hashCode) {
			case -1120391203 : setGLOBAL_NO((String)arg);break;
			case -1836788457 : setSVR_IP((String)arg);break;
			case -450927581 : setSVR_HOSTNAME((String)arg);break;
			case 1184089877 : setSVR_INSTANCE_ID((String)arg);break;
			case -1205197212 : setIF_TYPE_CD((String)arg);break;
			case 764680670 : setPROCESS_STATUS_CD((String)arg);break;
			case 112266393 : setREG_DATE((String)arg);break;
			case -1881445317 : setREG_DT((String)arg);break;
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
			return "MSG_CustomLog";
		} else if (input.equalsIgnoreCase(META_VERSION_NO)){
			return "27";
		} else if (input.equalsIgnoreCase(META_LOGICAL_NAME)){
			return "MSG_CustomLog";
		} else if (input.equalsIgnoreCase(META_RESOURCE_ID)){
			return "nbss.ngw.root_tx.leaf_tx:MSG_CustomLog.umsg";
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
