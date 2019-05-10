/*
 * 작성된 날짜: 2015. 6. 12
 * Copyright (c) 2015 TmaxSoft co., Ltd. All rights reserved.
 */
package nbss.ngw.root_tx.leaf_tx;

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
 * @file              nbss.ngw.root_tx.leaf_tx.MSG_BodyFixedLength.java
 * @filetype          java source file
 * @brief			
 * @version           1.0
 * @template-version  2014-11-12
 * @history
 * 성 명              일 자                     근 거 자 료                      변 경 내 용
 * -----------        --------                  --------------                   --------------------------	
 * InfiniLink :       2015. 6. 12                InfiniLink 개발 :                신규 작성
 * 
 */



public class MSG_BodyFixedLength extends com.tmax.promapper.engine.base.FixedLengthMessage implements ResourceMeta {
	private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FixedLengthUnmarshalField.class.getName());

	public MSG_BodyFixedLength() {
		super();
	}

	public MSG_BodyFixedLength(int conversionType) {
		super(conversionType);
	}

	public MSG_BodyFixedLength(String charsetName) {
		super(charsetName);
	}
	/* marshall method */
	public byte [] marshalObject(Object obj) throws Exception {
		FixedLengthMarshalFieldV2 flFld = new FixedLengthMarshalFieldV2();
		nbss.ngw.root_tx.leaf_tx.MSG_Body __root = (nbss.ngw.root_tx.leaf_tx.MSG_Body)obj;

		if(__root == null) {
			marshallNullObject(flFld);
		} else {
			
			/** data3 */
			if(logger.isLoggable(java.util.logging.Level.FINEST)) { 
			 	logger.finest("FieldName = data3,  StartOffset = " + flFld.getTotalLength() + " Length=10, fill= , decimal=0, point=false, sign=false, encodeOption=Constants.ENCODE_CHAR, EOD = false");
			}
			flFld.setStringField(this, __root.getData3(), 10, Constants.ALIGN_NON, " ", 0, false, false, Constants.ENCODE_CHAR, null);
			
			/** data2 */
			if(logger.isLoggable(java.util.logging.Level.FINEST)) { 
			 	logger.finest("FieldName = data2,  StartOffset = " + flFld.getTotalLength() + " Length=10, fill=0, decimal=0, point=false, sign=false, encodeOption=Constants.ENCODE_CHAR, EOD = false");
			}
			flFld.setStringField(this, __root.getData2(), 10, Constants.ALIGN_NON, "0", 0, false, false, Constants.ENCODE_CHAR, null);
		}
        
		return flFld.getMessage();
	}

		
	private void marshallNullObject(FixedLengthMarshalFieldV2 flFld) throws Exception {
			
			/** data3 */
			if(logger.isLoggable(java.util.logging.Level.FINEST)) { 
			 	logger.finest("FieldName = data3,  StartOffset = " + flFld.getTotalLength() + " Length=10, fill= , decimal=0, point=false, sign=false, encodeOption=Constants.ENCODE_CHAR, EOD = false");
			}
			
				flFld.padByteArrayField(this, 10, " ");
			
			/** data2 */
			if(logger.isLoggable(java.util.logging.Level.FINEST)) { 
			 	logger.finest("FieldName = data2,  StartOffset = " + flFld.getTotalLength() + " Length=10, fill=0, decimal=0, point=false, sign=false, encodeOption=Constants.ENCODE_CHAR, EOD = false");
			}
			
				flFld.padByteArrayField(this, 10, "0");
	}
	public Object unmarshalObject(byte[] message, int startOffset) throws Exception{
		nbss.ngw.root_tx.leaf_tx.MSG_Body dataObject = new nbss.ngw.root_tx.leaf_tx.MSG_Body();
		setOffset(unmarshalObject(message, startOffset, dataObject));
	    	return dataObject;
	}
    
	public int unmarshalObject(byte[] message, int startOffset, Object dataObject) throws Exception {		
		if(logger.isLoggable(Level.FINEST)) {
			logger.finest("#Unmarshall Start. Message Class = [MSG_BodyFixedLength], byte[] length = " + message.length + ", Start Offset = " + startOffset);
		}

		nbss.ngw.root_tx.leaf_tx.MSG_Body __root = (nbss.ngw.root_tx.leaf_tx.MSG_Body)dataObject;
		FixedLengthUnmarshalField flFld = new FixedLengthUnmarshalField(message, startOffset);
		

		/** data2 */
		if(logger.isLoggable(java.util.logging.Level.FINEST)) {
			logger.finest("FieldName = data2,  StartOffset = " + flFld.getOffset() + " Length=10, fill=0, decimal=0, point=false, sign=false, encodeOption=Constants.ENCODE_CHAR, trimFlag=default, rootTrimFlag=rtrim");
		}
		__root.setData2(flFld.getStringField(this, 10, Constants.ALIGN_NON, "0", 0, false, false, Constants.ENCODE_CHAR, Constants.TRIM_RIGHT));

		/** data3 */
		if(logger.isLoggable(java.util.logging.Level.FINEST)) {
			logger.finest("FieldName = data3,  StartOffset = " + flFld.getOffset() + " Length=10, fill= , decimal=0, point=false, sign=false, encodeOption=Constants.ENCODE_CHAR, trimFlag=default, rootTrimFlag=rtrim");
		}
		__root.setData3(flFld.getStringField(this, 10, Constants.ALIGN_NON, " ", 0, false, false, Constants.ENCODE_CHAR, Constants.TRIM_RIGHT));

		setOffset(flFld.getOffset());
		return getOffset();
	}
	
	protected static java.util.Map<String, MessageFieldProperty> fieldPropertyMap = new java.util.LinkedHashMap();
	public java.util.Map<String, MessageFieldProperty> getFieldPropertyMap() {
		return fieldPropertyMap;
	}
	static {
	MessageFieldProperty fldProp = null;
		fldProp = new MessageFieldProperty("data2", "data2", MessageFieldType.STRING, (String)null, null, "10", Constants.ALIGN_NON, "0", 0, false, false, null, null, null, null, null, Constants.TRIM_RIGHT, Constants.ENCODE_CHAR);
		fieldPropertyMap.put("data2", fldProp);
		
		fldProp = new MessageFieldProperty("data3", "data3", MessageFieldType.STRING, (String)null, null, "10", Constants.ALIGN_NON, " ", 0, false, false, null, null, null, null, null, Constants.TRIM_RIGHT, Constants.ENCODE_CHAR);
		fieldPropertyMap.put("data3", fldProp);
		
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
			return "MSG_BodyFixedLength";
		} else if (input.equalsIgnoreCase(META_VERSION_NO)) {
			return null;
		} else if (input.equalsIgnoreCase(META_LOGICAL_NAME)) {
			return "MSG_Body";
		} else if (input.equalsIgnoreCase(META_RESOURCE_ID)) {
			return "nbss.ngw.root_tx.leaf_tx:MSG_BodyFixedLength.msg";
		} else if (input.equalsIgnoreCase(META_RESOURCE_TYPE)) {
			return "MESSAGE";
		} else if (input.equalsIgnoreCase(META_RESOURCE_PATH)) {
			return "nbss.ngw.root_tx.leaf_tx";
		} else if (input.equalsIgnoreCase(META_RESOURCE_GROUP)) {
			return "";
		} else if (input.equalsIgnoreCase(META_MESSAGE_TYPE)) {
		  	return "FixedLength";
		} else if (input.equalsIgnoreCase(META_MESSAGE_STRUCTURE_NAME)) {
			return "MSG_Body";
		} else if (input.equalsIgnoreCase(META_MESSAGE_STRUCTURE_PATH)) {
			return "nbss.ngw.root_tx.leaf_tx";
		} else if (input.equalsIgnoreCase(META_MESSAGE_STRUCTURE_ID)) {
			return "nbss.ngw.root_tx.leaf_tx:MSG_Body.umsg";
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