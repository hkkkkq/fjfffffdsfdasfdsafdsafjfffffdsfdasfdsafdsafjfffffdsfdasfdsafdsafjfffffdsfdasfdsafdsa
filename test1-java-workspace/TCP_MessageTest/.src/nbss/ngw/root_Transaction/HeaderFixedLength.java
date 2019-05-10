/*
 * 작성된 날짜: 2015. 5. 26
 * Copyright (c) 2015 TmaxSoft co., Ltd. All rights reserved.
 */
package nbss.ngw.root_Transaction;

import proframe.dto.DataObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.tmax.promapper.engine.base.MessageFieldProperty;
import com.tmax.promapper.engine.base.MessageFieldType;
import com.tmax.promapper.engine.base.ResourceMeta;
import com.tmax.promapper.engine.base.Message;
import com.tmax.promapper.engine.util.Constants;
import com.tmax.promapper.engine.util.FixedLengthMarshalField;
import com.tmax.promapper.engine.util.FixedLengthMarshalFieldV2;
import com.tmax.promapper.engine.util.FixedLengthUnmarshalField;

import com.tmax.promapper.engine.base.FixedLengthMessage;

/**
 * @file              nbss.ngw.root_Transaction.HeaderFixedLength.java
 * @filetype          java source file
 * @brief			
 * @version           1.0
 * @template-version  2014-11-12
 * @history
 * 성 명              일 자                     근 거 자 료                      변 경 내 용
 * -----------        --------                  --------------                   --------------------------	
 * InfiniLink :       2015. 5. 26                InfiniLink 개발 :                신규 작성
 * 
 */



public class HeaderFixedLength extends com.tmax.promapper.engine.base.FixedLengthMessage implements ResourceMeta {
	private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FixedLengthUnmarshalField.class.getName());

	public HeaderFixedLength() {
		super();
	}

	public HeaderFixedLength(int conversionType) {
		super(conversionType);
	}

	public HeaderFixedLength(String charsetName) {
		super(charsetName);
	}
	/* marshall method */
	public byte [] marshalObject(Object obj) throws Exception {
		FixedLengthMarshalFieldV2 flFld = new FixedLengthMarshalFieldV2();
		nbss.ngw.root_Transaction.Header __root = (nbss.ngw.root_Transaction.Header)obj;

		if(__root == null) {
			marshallNullObject(flFld);
		} else {
			
			/** length */
			if(logger.isLoggable(java.util.logging.Level.FINEST)) { 
			 	logger.finest("FieldName = length,  StartOffset = " + flFld.getTotalLength() + " Length=4, fill= , decimal=0, point=false, sign=false, encodeOption=Constants.ENCODE_CHAR, EOD = false");
			}
			flFld.setStringField(this, __root.getLength(), 4, Constants.ALIGN_NON, " ", 0, false, false, Constants.ENCODE_CHAR, null);
		}
        
		return flFld.getMessage();
	}

		
	private void marshallNullObject(FixedLengthMarshalFieldV2 flFld) throws Exception {
			
			/** length */
			if(logger.isLoggable(java.util.logging.Level.FINEST)) { 
			 	logger.finest("FieldName = length,  StartOffset = " + flFld.getTotalLength() + " Length=4, fill= , decimal=0, point=false, sign=false, encodeOption=Constants.ENCODE_CHAR, EOD = false");
			}
			
				flFld.padByteArrayField(this, 4, " ");
	}
	public Object unmarshalObject(byte[] message, int startOffset) throws Exception{
		nbss.ngw.root_Transaction.Header dataObject = new nbss.ngw.root_Transaction.Header();
		setOffset(unmarshalObject(message, startOffset, dataObject));
	    	return dataObject;
	}
    
	public int unmarshalObject(byte[] message, int startOffset, Object dataObject) throws Exception {		
		if(logger.isLoggable(Level.FINEST)) {
			logger.finest("#Unmarshall Start. Message Class = [HeaderFixedLength], byte[] length = " + message.length + ", Start Offset = " + startOffset);
		}

		nbss.ngw.root_Transaction.Header __root = (nbss.ngw.root_Transaction.Header)dataObject;
		FixedLengthUnmarshalField flFld = new FixedLengthUnmarshalField(message, startOffset);
		

		/** length */
		if(logger.isLoggable(java.util.logging.Level.FINEST)) {
			logger.finest("FieldName = length,  StartOffset = " + flFld.getOffset() + " Length=4, fill= , decimal=0, point=false, sign=false, encodeOption=Constants.ENCODE_CHAR, trimFlag=default, rootTrimFlag=rtrim");
		}
		__root.setLength(flFld.getStringField(this, 4, Constants.ALIGN_NON, " ", 0, false, false, Constants.ENCODE_CHAR, Constants.TRIM_RIGHT));

		setOffset(flFld.getOffset());
		return getOffset();
	}
	
	protected static java.util.Map<String, MessageFieldProperty> fieldPropertyMap = new java.util.LinkedHashMap();
	public java.util.Map<String, MessageFieldProperty> getFieldPropertyMap() {
		return fieldPropertyMap;
	}
	static {
	MessageFieldProperty fldProp = null;
		fldProp = new MessageFieldProperty("length", "length", MessageFieldType.STRING, (String)null, null, "4", Constants.ALIGN_NON, " ", 0, false, false, null, null, null, null, null, Constants.TRIM_RIGHT, Constants.ENCODE_CHAR);
		fieldPropertyMap.put("length", fldProp);
		
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
			return "HeaderFixedLength";
		} else if (input.equalsIgnoreCase(META_VERSION_NO)) {
			return null;
		} else if (input.equalsIgnoreCase(META_LOGICAL_NAME)) {
			return "Header";
		} else if (input.equalsIgnoreCase(META_RESOURCE_ID)) {
			return "nbss.ngw.root_Transaction:HeaderFixedLength.msg";
		} else if (input.equalsIgnoreCase(META_RESOURCE_TYPE)) {
			return "MESSAGE";
		} else if (input.equalsIgnoreCase(META_RESOURCE_PATH)) {
			return "nbss.ngw.root_Transaction";
		} else if (input.equalsIgnoreCase(META_RESOURCE_GROUP)) {
			return "";
		} else if (input.equalsIgnoreCase(META_MESSAGE_TYPE)) {
		  	return "FixedLength";
		} else if (input.equalsIgnoreCase(META_MESSAGE_STRUCTURE_NAME)) {
			return "Header";
		} else if (input.equalsIgnoreCase(META_MESSAGE_STRUCTURE_PATH)) {
			return "nbss.ngw.root_Transaction";
		} else if (input.equalsIgnoreCase(META_MESSAGE_STRUCTURE_ID)) {
			return "nbss.ngw.root_Transaction:Header.umsg";
		} else {
			String msg = "Expecting one of " + META_PHYSICAL_NAME + ", "
					+ META_VERSION_NO + ", " + META_LOGICAL_NAME + ", "
					+ META_RESOURCE_ID + ", " + META_RESOURCE_TYPE + ", "
					+ META_RESOURCE_PATH + ", " + META_RESOURCE_GROUP + " , "
					+ META_MESSAGE_TYPE + " , " + META_MESSAGE_STRUCTURE_NAME
					+ " , " + META_MESSAGE_STRUCTURE_PATH + " , "
					+ META_MESSAGE_STRUCTURE_ID;
			
			throw new IllegalArgumentException(msg);
		}
	}		
}
