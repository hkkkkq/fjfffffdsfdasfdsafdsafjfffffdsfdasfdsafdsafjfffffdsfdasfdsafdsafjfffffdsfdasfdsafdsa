/*
 * 작성된 날짜: 2015. 6. 3
 * Copyright (c) 2015 TmaxSoft co., Ltd. All rights reserved.
 */
package nbss.ngw.root_tx.leaf_tx;

import proframe.dto.DataObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.tmax.promapper.engine.base.MessageFieldProperty;
import com.tmax.promapper.engine.base.MessageFieldType;
import com.tmax.promapper.engine.base.ResourceMeta;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayOutputStream;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLStreamConstants;
import com.tmax.promapper.engine.exception.UnmarshallException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import com.tmax.promapper.engine.util.Constants;
import com.tmax.promapper.engine.util.XPathProcessor;
import com.tmax.promapper.engine.util.XMLUtil;
import java.math.BigDecimal;

import com.tmax.promapper.engine.base.XMLMessage;

/**
 * @file              nbss.ngw.root_tx.leaf_tx.MSG_BodyXML.java
 * @filetype          java source file
 * @brief			
 * @version           1.0
 * @template-version  2014-11-12
 * @history
 * 성 명              일 자                     근 거 자 료                      변 경 내 용
 * -----------        --------                  --------------                   --------------------------	
 * InfiniLink :       2015. 6. 3                InfiniLink 개발 :                신규 작성
 * 
 */



public class MSG_BodyXML extends com.tmax.promapper.engine.base.XMLMessage implements ResourceMeta {
	private static Logger logger = Logger.getLogger("com.tmax.promapper.XMLMessage"); 
	
	private Boolean isRPCEncoded = new Boolean(false);
	
	private static final QName messageElementName = new QName("urn:infinilink:nbss.ngw.root_tx.leaf_tx", "MSG_Body");

	
	private static final QName complexTypeName = new QName("urn:infinilink:nbss.ngw.root_tx.leaf_tx", "MSG_Body");

	
	public QName getMessageElementName() {
		return messageElementName;
	}
	
	public QName getComplexTypeName() {
		return messageElementName;
	}
	public MSG_BodyXML() {
		super();
	}

	public MSG_BodyXML(int conversionType) {
		super(conversionType);
	}

	public MSG_BodyXML(String charsetName) {
		super(charsetName);
	}
	
	public void setIsRPCEncoded(boolean isRPCEncoded){
		this.isRPCEncoded = isRPCEncoded;
	}
	
	/* marshall method */
	public byte[] marshalObject(Object obj, boolean writeXMLProcessingInstruction) throws Exception {
		return marshalObject(obj, writeXMLProcessingInstruction, true);
	}
	
	public byte[] marshalObject(Object obj, boolean writeXMLProcessingInstruction, boolean selfCloseFlag) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        XMLStreamWriter writer = XMLUtil.createXMLStreamWriter(this, writeXMLProcessingInstruction, bout, selfCloseFlag);
        
        nbss.ngw.root_tx.leaf_tx.MSG_Body  _mSG_BodyXML = (nbss.ngw.root_tx.leaf_tx.MSG_Body)obj;
        
        marshalObject(_mSG_BodyXML, writer, messageElementName);
        
        writer.writeEndDocument();
        writer.flush();
        writer.close();
        
        return bout.toByteArray();
    }
	
	public void marshalObject(Object dataObject, XMLStreamWriter writer) throws Exception {
		marshalObject(dataObject, writer, messageElementName);
	}
	
	public void marshalObject(Object dataObject, XMLStreamWriter writer, QName rootElementName) throws Exception {
		nbss.ngw.root_tx.leaf_tx.MSG_Body _mSG_BodyXML = (nbss.ngw.root_tx.leaf_tx.MSG_Body)dataObject;
		XMLUtil.writeStartElementAndNamspaceDeclaration(rootElementName, writer, null, null);
		
		marshalXmlBody(_mSG_BodyXML, writer);
		
		writer.writeEndElement();//endElement for rootElement
	}
	
	/**
	 * RPC Encoded를 위한 marshal method...element에 type 정보를 첨부해준다.
	 *
	 */
	public void marshalObject(Object dataObject, XMLStreamWriter writer, QName rootElementName, Boolean isRPCEncoded) throws Exception {
		nbss.ngw.root_tx.leaf_tx.MSG_Body _mSG_BodyXML = (nbss.ngw.root_tx.leaf_tx.MSG_Body)dataObject;
		XMLUtil.writeStartElementAndNamspaceDeclaration(rootElementName, writer, null, null);
		this.isRPCEncoded = isRPCEncoded;
		
		marshalXmlBody(_mSG_BodyXML, writer);
		
		writer.writeEndElement();//endElement for rootElement
	}
	
	/** 
	 * messageField들을 위한 startElement, elementContent, endElement를 찍는다.
	 *
	 */
    public void marshalXmlBody(nbss.ngw.root_tx.leaf_tx.MSG_Body _mSG_BodyXML, XMLStreamWriter writer) throws javax.xml.stream.XMLStreamException, com.tmax.promapper.engine.exception.MarshallException {
	
		marshalXmlBody_0(_mSG_BodyXML, writer);
	}
	
	private void marshalXmlBody_0(nbss.ngw.root_tx.leaf_tx.MSG_Body _mSG_BodyXML, XMLStreamWriter writer) throws javax.xml.stream.XMLStreamException, com.tmax.promapper.engine.exception.MarshallException {
		char[] c = null;
		
			
			//element field name is data2. nillable="false", minOccurs="1"
		if(_mSG_BodyXML.getData2() == null) {
			writer.writeStartElement("data2");
			if (isRPCEncoded) {
				writer.writeAttribute(PREFIX_XSI, NS_XSI, "type", "xs:string");
				writer.writeNamespace(PREFIX_SOAP_ENC, URI_NS_SOAP_ENC);
				writer.writeNamespace("xs", "http://www.w3.org/2001/XMLSchema");
			}
			writer.writeEndElement();
		} else {
			writer.writeStartElement("data2");
			if (isRPCEncoded) {
				writer.writeAttribute(PREFIX_XSI, NS_XSI, "type", "xs:string");
				writer.writeNamespace(PREFIX_SOAP_ENC, URI_NS_SOAP_ENC);
				writer.writeNamespace("xs", "http://www.w3.org/2001/XMLSchema");
			}
			if(!_mSG_BodyXML.getData2().equals(""))
				writer.writeCharacters(_mSG_BodyXML.getData2());
			writer.writeEndElement();
		}
			//element field name is data3. nillable="false", minOccurs="1"
		if(_mSG_BodyXML.getData3() == null) {
			XMLUtil.writeStartElementAndNamspaceDeclaration("urn:infinilink:nbss.ngw.root_tx.leaf_tx", "data3", writer, null, null);
			writer.writeEndElement();
		} else {
			XMLUtil.writeStartElementAndNamspaceDeclaration("urn:infinilink:nbss.ngw.root_tx.leaf_tx", "data3", writer, null, null);
			if(!_mSG_BodyXML.getData3().equals(""))
				writer.writeCharacters(_mSG_BodyXML.getData3());
			writer.writeEndElement();
		}
	}
	
	
	/*
	 * xPathProcessor가 startElement를 모두 다 찍고, messageField부분은 marshalXmlBody가 찍는다.
	 */
	public void marshalObject(Object obj, Node node, String xPathExpression) throws Exception {
		marshalObject(obj, node, xPathExpression, true);
	}
	
	public void marshalObject(Object obj, Node node, String xPathExpression, boolean selfCloseFlag) throws Exception {
        Node targetNode = null;
        XMLStreamWriter writer = null;
        if(xPathExpression == null) {
            targetNode = node;
            Result target = new DOMResult(targetNode);
            writer = XMLUtil.createXMLStreamWriter(target, selfCloseFlag);
            writer.writeStartDocument();
            XMLUtil.writeStartElementAndNamspaceDeclaration(messageElementName, writer, null, null);
        } else {
            XPathProcessor xPathProc = new XPathProcessor(xPathExpression);
            targetNode = xPathProc.createNode(node);
            Result target = new DOMResult(targetNode);
            writer = XMLUtil.createXMLStreamWriter(target, selfCloseFlag);
        }
        
        //writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        nbss.ngw.root_tx.leaf_tx.MSG_Body _mSG_BodyXML = (nbss.ngw.root_tx.leaf_tx.MSG_Body)obj;
        marshalXmlBody(_mSG_BodyXML, writer);  
        //writer.writeEndDocument();
        writer.flush();
        writer.close();
        
    }
	
	/*
	 * xPathProcessor가 startElement를 모두 다 찍고, messageField부분은 marshalXmlBody가 찍는다.
	 */
	public byte[] marshalObject(Object obj, String xPathExpression, boolean writeXMLProcessingInstruction) throws Exception{
		return marshalObject(obj, xPathExpression, writeXMLProcessingInstruction, true);
	}
	
	public byte[] marshalObject(Object obj, String xPathExpression, boolean writeXMLProcessingInstruction, boolean selfCloseFlag) throws Exception{
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        XMLStreamWriter writer = XMLUtil.createXMLStreamWriter(this, writeXMLProcessingInstruction, bout, selfCloseFlag);
        
        nbss.ngw.root_tx.leaf_tx.MSG_Body  _mSG_BodyXML = (nbss.ngw.root_tx.leaf_tx.MSG_Body)obj;
        
        int startElementCountByXPath = 0;
        if(xPathExpression != null) {
            XPathProcessor xPathProc = new XPathProcessor(xPathExpression);
            startElementCountByXPath = xPathProc.writeElements(writer);
        }
        
        marshalXmlBody(_mSG_BodyXML, writer);
        
        for(int i = 0; i < startElementCountByXPath; i++) {
            writer.writeEndElement();
        }
        writer.writeEndDocument();
        writer.flush();
        writer.close();
        
        return bout.toByteArray();
    }
	
	public void marshalObjectExceptRootElement(Object obj, XMLStreamWriter writer) throws Exception {
		nbss.ngw.root_tx.leaf_tx.MSG_Body  _mSG_BodyXML = (nbss.ngw.root_tx.leaf_tx.MSG_Body)obj;
		marshalXmlBody(_mSG_BodyXML, writer);
	}
		/*all hashmaps declared below have (String dtoFieldName, Integer caseInt) pairs.
		   These will be used to determine which setter method in Dto will be invoked.		 
		   A hashset named nillableFieldSet, contatins fields declared nillable in the XML schema*/
		static Map<QName, Integer> nillableFieldMap = new HashMap<QName, Integer>(2);
		static Map<QName, Integer> attributeFieldMap = new HashMap<QName, Integer>(2);
		static Map<QName, Integer> simpleTypeFieldMap = new HashMap<QName, Integer>(2);
		static Map<QName, Integer> complexTypeFieldMap = new HashMap<QName, Integer>(2);
		static Map<QName, Integer> customHandlerFieldMap = new HashMap<QName, Integer>();
		
		static{
					//element
			simpleTypeFieldMap.put(new QName("", "data2"), 0);
					//element
			simpleTypeFieldMap.put(new QName("urn:infinilink:nbss.ngw.root_tx.leaf_tx", "data3"), 1);
			//simpleTypeFieldMap.put(XMLMessage.nil, 2);
			//simpleTypeFieldMap.put(XMLMessage.schemaLocation, 3);
			//simpleTypeFieldMap.put(XMLMessage.noNamespaceSchemaLocation, 4);
		}
		
		public static Map<QName, Integer> getComplexTypeFieldMap() {
			return complexTypeFieldMap;
		}
		
		public static Map<QName, Integer> getSimpleTypeFieldMap() {
			return simpleTypeFieldMap;
		}
		public void setSimpleTypeField(QName elementName, Integer fieldIndex, XMLStreamReader reader, nbss.ngw.root_tx.leaf_tx.MSG_Body _structure) throws  UnmarshallException, javax.xml.stream.XMLStreamException{
			String value = reader.getElementText();
		
			if(logger.isLoggable(Level.FINEST)) {
				logger.finest("START_ELEMENT, [" + elementName.toString() + "] elementText= [" + value + "]");
			}
		
			if(fieldIndex >= 0 && fieldIndex < 250)
				setSimpleTypeField_0(elementName, fieldIndex, reader, _structure, value);
				
		}
	
		public void setSimpleTypeField_0(QName elementName, Integer fieldIndex, XMLStreamReader reader, nbss.ngw.root_tx.leaf_tx.MSG_Body _structure, String value) throws  UnmarshallException, javax.xml.stream.XMLStreamException{
						
			switch(fieldIndex) {
			
			case 0:
				_structure.setData2(value);
				break;
			case 1:
				_structure.setData3(value);
				break;
			default:
				throw new UnmarshallException("cannot find structureField corresponding to element named, " + elementName.toString());
			}
		}
	
	/** 
	 * 포함하고있는 elementName이 오면 set후 return true; otherwise return false;
	 */
	public void setComplexTypeField(QName elementName, Integer fieldIndex, XMLStreamReader reader, nbss.ngw.root_tx.leaf_tx.MSG_Body _structure) throws Exception{
		boolean isNil = false;
		
		if(customHandlerFieldMap.get(elementName) != null) {
			setCustomHandlerField(elementName, customHandlerFieldMap.get(elementName), reader, _structure);	
		}	else {
			switch(fieldIndex) {
			default:
				throw new UnmarshallException("cannot find structureField corresponding to element named, " + elementName.toString());
			}
		}
			
	}
	
	/*rootElement의 attribute가 된 field들을 처리*/
	public void setAttributeField(QName attrName, String attrValue, nbss.ngw.root_tx.leaf_tx.MSG_Body _structure) throws  UnmarshallException {
		Integer fieldIndex = (Integer)attributeFieldMap.get(attrName);
		
		if(fieldIndex == null) {
			//throw new UnmarshallException("cannot find structure field corresponding to attribute named " + attrName.toString());
			logger.severe("cannot find structure field corresponding to attribute named " + attrName.toString());
		}
		
	
		switch(fieldIndex) {
		default:
			throw new UnmarshallException("cannot find structure field corresponding to attribute named " + attrName.toString());
		}
	
	}
	
	/* xsi: attribute를 처리하는 method , nil=true를 만나서 null로 set하는 경우 return true;*/
	public boolean setAttribute(QName elementName, QName attrName, String value, nbss.ngw.root_tx.leaf_tx.MSG_Body _structure) throws  UnmarshallException{
		// xsi:nil처리
		if( attrName.equals(XMLMessage.nil)) {
			Integer fieldIndex = (Integer)nillableFieldMap.get(elementName);
			if(fieldIndex == null) {
				//throw new UnmarshallException("cannot find structure field corresponding to element named " + elementName.toString());
				logger.severe("cannot find structure field corresponding to attribute named " + attrName.toString());
			}
			
			switch(fieldIndex) {
			default:
				throw new UnmarshallException("elementName = " + elementName + " is not nillable");	
			}
		} else if(attrName.equals(XMLMessage.type)) {
		} else {
		}
		return false;
	}
	
	public boolean setCustomHandlerField(QName elementName, Integer fieldIndex, XMLStreamReader reader, nbss.ngw.root_tx.leaf_tx.MSG_Body _structure) throws Exception{
		switch(fieldIndex) {
		default:
			throw new UnmarshallException("cannot find structureField corresponding to element named, " + elementName.toString());
		}
	}
	//return true 이면, caller가 object를 null로 set해야한다.
	public boolean unmarshalObject(XMLStreamReader reader, Object object) throws Exception {
		return unmarshalObject(reader, object, new QName("urn:infinilink:nbss.ngw.root_tx.leaf_tx","MSG_Body"));
    }
    
    //return true 이면, caller가 object를 null로 set해야한다. 
	public boolean unmarshalObject(XMLStreamReader reader, Object object, QName rootElement) throws Exception {
		nbss.ngw.root_tx.leaf_tx.MSG_Body  _mSG_BodyXML = (nbss.ngw.root_tx.leaf_tx.MSG_Body)object;
		
		QName currentElem = null;
		int state = XMLMessage.INIT;
		
		boolean afterRootElement = false; // RootElement.equals 매번 call하지 않게 하려고,,
		
		while(state != XMLMessage.FINAL) {
			switch(state) {
			case XMLMessage.INIT:
				if(logger.isLoggable(Level.FINEST)) {
					logger.finest("unmarshall Initiated.");
				}
				
				state = XMLUtil.gotoNextState(reader, state, rootElement);
				break;
				
			case XMLMessage.START_ELEMENT:
				currentElem = reader.getName();
				
				if(logger.isLoggable(Level.FINEST)) {
					logger.finest("START_ELEMENT, [" + currentElem.toString() + "]");
				}
				
				boolean isCurrentElemNil = false;
				
				int attrCount = reader.getAttributeCount();
				if(!afterRootElement && currentElem.equals(rootElement)) {
					for(int i = 0; i < attrCount; i++) {
						QName attrName = reader.getAttributeName(i);
						String attrValue = reader.getAttributeValue(i);
						
						if(logger.isLoggable(Level.FINEST)) {
							logger.finest("START_ELEMENT, [" + currentElem.toString() + "] attribute = [" + attrName.toString() + "], value = " + attrValue);
						}
						
						String attrNSURI = attrName.getNamespaceURI();
						if(attrNSURI.equals(XMLMessage.XSI)) {
							if(attrName.equals(XMLMessage.nil)) {
								//this method return true if there exists a xsi:nil="true" attribute so that caller can set dto as null.
								return Boolean.parseBoolean(attrValue);
							} else if(attrName.equals(XMLMessage.type)) {
								//skip attributes except xsi:nil.
							}
						} else if(attrNSURI.startsWith(XMLMessage.URI_NS_XMLSOAP)) {
							//skip
						} else {
							setAttributeField(attrName, attrValue, _mSG_BodyXML);
						}
					}
					afterRootElement = true;
					 
				} else {
					//currentElement.equals(root Element) == false;
					for(int i = 0; i < attrCount; i++) {
						QName attrName = reader.getAttributeName(i);
						String attrValue = reader.getAttributeValue(i);
						
						if(logger.isLoggable(Level.FINEST)) {
							logger.finest("START_ELEMENT, [" + currentElem.toString() + "] attribute = [" + attrName.toString() + "], value = " + attrValue);
						}
						
						isCurrentElemNil = setAttribute(currentElem, attrName, attrValue, _mSG_BodyXML);
					}
					
					if(isCurrentElemNil) {
						// current element xsi:nil="true" 이면 nextTag.
						reader.nextTag();
					} else {
						Integer fieldIndex = complexTypeFieldMap.get(currentElem);;
						if(fieldIndex == null) {
							//find in super class if this class has a parent Message.
						}
												
						if(fieldIndex != null) {
							setComplexTypeField(currentElem, fieldIndex, reader, _mSG_BodyXML);
						} else {
							fieldIndex = simpleTypeFieldMap.get(currentElem);
		
							if(fieldIndex != null) {
								setSimpleTypeField(currentElem, fieldIndex, reader, _mSG_BodyXML);
							} else {
								throw new UnmarshallException("cannot find structureField corresponding to element named, " + currentElem.toString());
							}
							
						}
					}
				}
				
				/*
				 * rootElement인 경우와 childElement인 경우 각각 처리 후
				 * 다음 event(actually, next Tag)로 move 한 시점. 
				 */
				reader.nextTag();
				state = XMLUtil.gotoNextState(reader, state, rootElement);
				break;
			
			case XMLMessage.END_ELEMENT:
				//종료조건확인
				currentElem = reader.getName();
				
				if(logger.isLoggable(Level.FINEST)) {
					logger.finest("END_ELEMENT, [" + currentElem.toString() + "]");
				}
				
				if(currentElem.equals(rootElement)) {
					state = XMLMessage.FINAL;
				} else {
					reader.nextTag();
					state = XMLUtil.gotoNextState(reader, state, rootElement);
				}
				break;
			}
		}
		
		return false;
	}
    
	/* 
		Stream+XML type인 경우 사용할 수 있다.
		한 번만 call 되어야한다, find XMLProcessingInstruction을 2번 call하는 경우 문제가 생긴다.
	*/
	public Object unmarshalObject(byte[] msg, int offset) throws Exception {
		InputStream bin = new ByteArrayInputStream(msg,offset, msg.length-offset);
		XMLStreamReader reader = XMLUtil.createXMLStreamReader(bin);
		
		nbss.ngw.root_tx.leaf_tx.MSG_Body  struct = new nbss.ngw.root_tx.leaf_tx.MSG_Body();
		boolean isNil = unmarshalObject(reader, struct);
		if(isNil) {
			struct = null;
		}
		
		setOffset(msg.length - bin.available());
		bin.close();

		return struct;
	}
	
	public int unmarshalObject(byte[] msg, int offset, Object obj) throws Exception {
		return unmarshalObject(msg, offset, msg.length-offset, obj);
	}
	
	public int unmarshalObject(byte[] msg, int offset, int length, Object obj) throws Exception {
		InputStream bin = new ByteArrayInputStream(msg, offset, length);
		XMLStreamReader reader = XMLUtil.createXMLStreamReader(bin);
		//unmarshalObject(reader, "urn:MSG_Body", obj);
		boolean isNil = unmarshalObject(reader, obj);
		if(isNil) {
			obj = null;
		}
		bin.close();
		int newOffset = offset+length;
		setOffset(newOffset);
		return newOffset;
	}
	
	public Object unmarshalObject(Node message, QName rootElement) throws Exception {
		Source source = new DOMSource(message);
		XMLStreamReader reader = XMLUtil.createXMLStreamReader(source);
		nbss.ngw.root_tx.leaf_tx.MSG_Body  struct = new nbss.ngw.root_tx.leaf_tx.MSG_Body();
		//unmarshalObject(reader, rootElement, struct);
		boolean isNil = unmarshalObject(reader, struct, rootElement);
		if(isNil) {
			struct = null;
		}
		return struct;
	}
	
	static Map<String, Node> referNodeMap = new HashMap<String, Node>();
	/**
	 *  unmarshal method for RPC Encoded
	 */
	public Object unmarshalObject(NodeList nodeList, QName rootElement, Boolean isRPCEncoded) throws Exception {
	    this.isRPCEncoded = isRPCEncoded;
	    Node node = null;
        if (this.isRPCEncoded) {
            node = XMLUtil.parseXMLRef(nodeList, referNodeMap);
        } else {
            node = nodeList.item(0);
        }

        return unmarshalObject(node, rootElement);
    }
    
	public Object unmarshalObject(Node message) throws Exception {
		return unmarshalObject(message, messageElementName);
	}
	
	public Object unmarshalObject(byte[] msg, int offset, String xPathExpression, QName rootElement) throws Exception {
		InputStream bin = new ByteArrayInputStream(msg,offset, msg.length-offset);
		XMLStreamReader reader = XMLUtil.createXMLStreamReader(bin);
		//xpath 처리하여 root element 뛰어 넘는 logic을 가진 api call.
		XPathProcessor xPathProc = new XPathProcessor(xPathExpression);
		xPathProc.passElements(reader);
		nbss.ngw.root_tx.leaf_tx.MSG_Body  struct = new nbss.ngw.root_tx.leaf_tx.MSG_Body();
		boolean isNil = unmarshalObject(reader, struct, rootElement);
		if(isNil) {
			struct = null;
		}
		bin.close();
		return struct;
	}
	
	public Object unmarshalObject(byte[] msg, int offset, String xPathExpression) throws Exception {
		return unmarshalObject(msg, offset, xPathExpression, messageElementName);
	}
	
	public Object unmarshalObjectExceptRootElement(XMLStreamReader reader) throws Exception{
		nbss.ngw.root_tx.leaf_tx.MSG_Body  struct = new nbss.ngw.root_tx.leaf_tx.MSG_Body();
		QName currentElementName = new QName(reader.getNamespaceURI(), reader.getLocalName());
		boolean isNil = unmarshalObject(reader, struct, currentElementName);
		
		return isNil?null:struct;
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
			return "MSG_BodyXML";
		} else if (input.equalsIgnoreCase(META_VERSION_NO)) {
			return null;
		} else if (input.equalsIgnoreCase(META_LOGICAL_NAME)) {
			return "MSG_Body";
		} else if (input.equalsIgnoreCase(META_RESOURCE_ID)) {
			return "nbss.ngw.root_tx.leaf_tx:MSG_BodyXML.msg";
		} else if (input.equalsIgnoreCase(META_RESOURCE_TYPE)) {
			return "MESSAGE";
		} else if (input.equalsIgnoreCase(META_RESOURCE_PATH)) {
			return "nbss.ngw.root_tx.leaf_tx";
		} else if (input.equalsIgnoreCase(META_RESOURCE_GROUP)) {
			return "";
		} else if (input.equalsIgnoreCase(META_MESSAGE_TYPE)) {
		  	return "XML";
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
