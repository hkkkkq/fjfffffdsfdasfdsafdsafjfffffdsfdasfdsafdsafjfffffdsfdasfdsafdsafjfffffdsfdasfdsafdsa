
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.DOMBuilder;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathFactory;
import org.w3c.dom.Node;


public class XMLUtils {
	
	
	private static final AtomicReference<XMLUtils> instanceRef
		= new AtomicReference<>();
	private static final ReentrantLock lock = new ReentrantLock();

	private final MessageFactory factory;
	private XMLUtils() throws SOAPException {
		factory = MessageFactory.newInstance();
	}
	
	/**
	 * Singleton 인스턴스를 취득한다.
	 * @return
	 */
	public static final XMLUtils getInstance() {
		XMLUtils instance = instanceRef.get();
		if(instance==null) {
			try {
				lock.lock();
				if( (instance = instanceRef.get()) == null) {
					instance = new XMLUtils();
					instanceRef.set(instance);
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				lock.unlock();
			}
		}
		return instance;
	}
	
	/**
	 * byte배열로 org.jdom2.Document 인스턴스를 생성한다.
	 * @param buf	byte배열
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	public Document makeDocument(byte[] buf) throws IOException, JDOMException {
		
		Document doc = makeDocument(new ByteArrayInputStream(buf));
		return doc;
	}
	
	/**
	 * 스트림으로 org.jdom2.Document 인스턴스를 생성한다.
	 * @param in	InputStream
	 * @return	org.jdom2.Document
	 * @throws JDOMException
	 * @throws IOException
	 */
	public Document makeDocument(InputStream in) throws JDOMException, IOException {
		
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(in);
		} finally {
			in.close();
		}
		return doc;
	}
	
	/**
	 * byte배열로 SOAPMessage 인스턴스를 생성한다.
	 * @param buf	byte배열
	 * @return	SOAPMessage
	 * @throws IOException
	 * @throws SOAPException
	 */
	public SOAPMessage makeSOAPMessage(byte[] buf) throws IOException, SOAPException {
		ByteArrayInputStream in = new ByteArrayInputStream(buf);
		return makeSOAPMessage(in);
	}
	
	/**
	 * 스트림으로 SOAPMessage 인스턴스를 생성한다.
	 * @param in	InputStream
	 * @return	SOAPMessage
	 * @throws IOException
	 * @throws SOAPException
	 */
	public SOAPMessage makeSOAPMessage(InputStream in) throws IOException, SOAPException {
		return factory.createMessage(null, in);
	}
	
	/**
	 * org.jdom2.Document 인스턴스를 SOAPMessage 인스턴스로 변환한다.
	 * @param document	org.jdom2.Document
	 * @return	SOAPMessage
	 * @throws IOException
	 * @throws SOAPException
	 */
	public SOAPMessage convertDocumentToSoap(Document document) throws IOException, SOAPException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		new XMLOutputter(Format.getCompactFormat().setOmitDeclaration(true)).output(document, out);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		SOAPMessage result = factory.createMessage(null, in);
		return result;

	}
	
	/**
	 * SOAPMessage 인스턴스를 org.jdom2.Document 인스턴스로 변환한다.
	 * @param message
	 * @return
	 * @throws SOAPException
	 */
	public Document convertSoapToDocument(SOAPMessage message) throws SOAPException {
		
		DOMBuilder domBuilder = new DOMBuilder();
		Document soapDoc = domBuilder.build(message.getSOAPPart()
				.getEnvelope().getOwnerDocument());
		
		return soapDoc;
	}
	
//	public Map<Namespace, Namespace> makeNamespaceMap(String outboundEpId) {
//		
//		PropUtils propUtils = PropUtils.getInstance();
//		Map<Namespace, Namespace> result = new HashMap<>();
//		
//		Namespace original = Namespace.getNamespace(
//				propUtils.getProperty(outboundEpId + ".comp.namespace.prefix")
//				,propUtils.getProperty(outboundEpId + ".comp.namespace.srcUrl"));
//		Namespace dest = Namespace.getNamespace(
//				propUtils.getProperty(outboundEpId + ".comp.namespace.prefix")
//				,propUtils.getProperty(outboundEpId + ".comp.namespace.destUrl"));
//		result.put(original, dest);
//		
//		return result;
//	}
	
//	public Map<String, String> makeElementNameMap(String outboundEpId) {
//		PropUtils propUtil = PropUtils.getInstance();
//		Map<String, String> result = new HashMap<>();
//		StringBuilder keyBuilder = new StringBuilder();
//		keyBuilder.append(outboundEpId).append(".comp.element.");
//		int prefixLength = keyBuilder.length();
//		
//		int total = Integer.parseInt(propUtil.getProperty(keyBuilder.append("size").toString()));
//		for(int i = 0; i < total; i++) {
//			
//			keyBuilder.delete(prefixLength, keyBuilder.length());
//			String src = propUtil.getProperty(keyBuilder.append(i).append(".src").toString());
//			
//			keyBuilder.delete(prefixLength, keyBuilder.length());
//			String dest = propUtil.getProperty(keyBuilder.append(i).append(".dest").toString());
//			
//			result.put(src, dest);
//		}
//		
//		return result;
//	}
	
//	private void changeMessage(List<Content> contentList
//			,Map<Namespace,Namespace> nsMap, Map<String, String> elementNameMap) {
//		
//		
//		for(Content content : contentList) {
//			if(content.getCType() != CType.Element) {
//				continue;
//			}
//			Element element = (Element)content;
//			
//			// change Namespace
//			if( nsMap.containsKey(element.getNamespace()) ) {
//				element.setNamespace(nsMap.get(element.getNamespace()));
//			}
//			
//			for(Namespace ns : element.getNamespacesIntroduced()) {
//				
//				if(nsMap.containsKey(ns)) {
//					element.removeNamespaceDeclaration(ns);
//					element.addNamespaceDeclaration(nsMap.get(ns));
//				}
//			}
//			
//			// change Element name
//			if( elementNameMap.containsKey(element.getName()) ) {
//				element.setName(elementNameMap.get(element.getName()));
//			}
//			
//			if(element.getContent()!=null) {
//				changeMessage(element.getContent(), nsMap, elementNameMap);
//			}
//		}
//	}
	
	

//	public void changeMessage(SOAPMessage soapMsg, String outboundEpId) throws SOAPException, IOException {
//		Document doc = convertSoapToDocument(soapMsg);
//		
//		Map<Namespace, Namespace> nsMap = makeNamespaceMap(outboundEpId);
//		Map<String, String> elemNameMap = makeElementNameMap(outboundEpId);
//		changeMessage(doc.getContent(), nsMap, elemNameMap);
//		try {
//			soapMsg.getSOAPPart().setContent(new DOMSource(new DOMOutputter().output(doc)));
//		}
//		catch (JDOMException e) {
//			LOG.warning(e, "Exception on setting SOAP Message");
//			throw new RuntimeException(e);
//		}
//	}
	
	/**
	 * 지정된 위치(parentElemXPath)의 하위에 Element를 추가한다.
	 * @param doc	Document
	 * @param parentElemXPath 지정된 위치
	 * @param name		추가할 Element의 이름
	 * @param value		추가할 Element의 값
	 * @throws SOAPException
	 * @throws JDOMException
	 */
	public void insertElement(Document doc, String parentElemXPath, String name, String value)
			throws SOAPException, JDOMException {
		
		XPathFactory xpathFactory = XPathFactory.instance();
		Element targetElem = xpathFactory.compile(parentElemXPath, Filters.element()).evaluateFirst(doc);
		
		Element resField = new Element(name, targetElem.getNamespace());
		resField.setText(value);
		targetElem.addContent(resField);
	}
	
	/**
	 * 인자의 Element의 하위에 Element를 추가한다.
	 * @param parentElem	인자의 Element
	 * @param name			추가할 Element의 이름
	 * @param value			추가할 Element의 값
	 */
	public void insertElement(Element parentElem, String name, String value) {
		Element resField = new Element(name, parentElem.getNamespace());
		resField.setText(value);
		parentElem.addContent(resField);
	}
	
//	public void setElementValue(SOAPMessage message, String elementXPath, String value)
//			throws SOAPException, JDOMException {
//		DOMBuilder builder = new DOMBuilder();
//		Document doc = builder.build(message.getSOAPPart());
//		
//		XPathFactory xpathFactory = XPathFactory.instance();
//		Element targetElem = xpathFactory.compile(elementXPath, Filters.element()).evaluateFirst(doc);
//		
//		targetElem.setText(value);
//		
//		message.getSOAPPart().setContent(new DOMSource(new DOMOutputter().output(doc)));
//	}
//	
//	public void setElementValue(SOAPMessage message, XPathExpression xpathExp, String value)
//			throws XPathExpressionException {
//		Node node = (Node)xpathExp.evaluate(message.getSOAPPart(), XPathConstants.NODE);
//		node.setTextContent(value);
//	}
	
	/**
	 * <pre>
	 * 지정된 위치의 Element에 값을 set한다.
	 * 지정된 위치에 Element가 없을 경우 Exception을 throw함.
	 * </pre>
	 * @param docElem				Document 또는 Element
	 * @param elementXPath	값을 입력할 Element의 위치
	 * @param value					Element에 입력할 값
	 */
	public void setElementValue(Object docElem, String elementXPath, String value) {
		
		Element targetElem = getElement(docElem, elementXPath);
		targetElem.setText(value);
	}
	
	/**
	 * <pre>
	 * 지정된 위치의 Element에 값을 set한다.
	 * 지정된 위치에 Element가 없을 경우 hierarchical하게 Element를 생성시켜 set한다.
	 * </pre>
	 * @param docElem
	 * @param parentElementXPath	값이 입력될 Element의 1depth 상위의 위치
	 * @param name								값이 입력될 Element의 이름
	 * @param value								값이 입력될 Element의 값
	 */
	public void setElementValueRecursively(Document docElem, String parentElementXPath, String name, String value) {
		Element parentTargetElem = getElement(docElem, parentElementXPath);
		if(parentTargetElem==null) {

			String upperParentElemXPath = parentElementXPath.substring(0, parentElementXPath.lastIndexOf("/"));
			String elemName = parentElementXPath.substring(parentElementXPath.lastIndexOf("/"))
					.replaceFirst("/(\\**\\[\\w+\\-\\w+\\(\\)\\=\\')*(\\w+)(\\'\\])*$", "$2");
			setElementValueRecursively(docElem, upperParentElemXPath, elemName, StringUtils.EMPTY);
		}
		
		parentTargetElem = getElement(docElem, parentElementXPath);
		Element element = parentTargetElem.getChild(name, parentTargetElem.getNamespace());
		if(element==null) {
			insertElement(parentTargetElem, name, value);
		}
		else{
			element.setText(value);
		}
	}
	
	/**
	 * 지정된 위치의 Element를 취득한다.
	 * @param docElem				XML전체
	 * @param elementXPath	취득할 Element의 위치
	 * @return							org.jdom2.Element
	 */
	public Element getElement(Object docElem, String elementXPath) {
		XPathFactory xpathFactory = XPathFactory.instance();
		Element targetElement = xpathFactory.compile(elementXPath, Filters.element()).evaluateFirst(docElem);
		
		return targetElement;
	}
	
	/**
	 * 지정된 위치의 Element의 값을 취득한다.
	 * 지정된 위치에 Element가 없을 경우 ""을 리턴한다.
	 * @param docElem				org.jdom2.Document or org.jdom2.Element
	 * @param elementXPath	값을 취득할 Element의 위치
	 * @return							지정된 Element의 text
	 */
	public String getElementStringValue(Object docElem, String elementXPath) {
		
		String result = StringUtils.EMPTY;
		Element targetElem = getElement(docElem, elementXPath);
		if(targetElem!=null) {
			result = targetElem.getText();
		}
		return result;
	}
	
	/**
	 * <pre>
	 * 지정한 xpath 이하의 모든 element의 값을 지움
	 * 지정한 xpath의 element가 없을 경우 Exception을 throw함
	 * </pre>
	 * @param docElem	Document
	 * @param parentElementXPath XPath표현식
	 */
	public void removeElementValueRecursively(Document docElem, String parentElementXPath) {
		Element parentTargetElem = getElement(docElem, parentElementXPath);
		if(parentTargetElem==null) {

			throw new NoSuchElementException(String
					.format("The Element matched the xpath '%s' not existed"
							,parentElementXPath));
		}
		
		removeElementValueRecursively(parentTargetElem);
	}
	
	/**
	 * 지정한 Element 하위의 모든 element의 값을 지움
	 * @param elem
	 */
	public void removeElementValueRecursively(Element elem) {
		List<Element> children = elem.getChildren();
		if(children.size()==0) {
			elem.setText(StringUtils.EMPTY);
		}
		else {
			for(Element child : children) {
				removeElementValueRecursively(child);
			}
		}
	}
	
	/**
	 * SOAPMessage를 byte배열로 변환한다.
	 * @param soap	SOAPMessage
	 * @return			byte배열
	 * @throws SOAPException
	 * @throws IOException
	 */
	public byte[] getBin(SOAPMessage soap) throws SOAPException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		soap.writeTo(out);
		return out.toByteArray();
	}
	
	/**
	 * org.w3c.dom.Document, org.w3c.dom.Element를 byte배열로 변환한다.
	 * @param element		org.w3c.dom.Document or org.w3c.dom.Element
	 * @return					byte배열
	 * @throws IOException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	public byte[] getBin(Node element) throws IOException, TransformerFactoryConfigurationError, TransformerException {
		byte[] result = null;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(element), new StreamResult(out));
			result = out.toByteArray();
		}
		return result;
	}
	
	/**
	 * org.jdom2.Document를 byte배열로 변환한다.
	 * @param doc	org.jdom2.Document
	 * @return
	 * @throws IOException
	 */
	public byte[] getBin(Document doc) throws IOException {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		outputter.output(doc, out);
		return out.toByteArray();
	}
	
	
	/**
	 * org.w3c.dom.Document, org.w3c.dom.Element의 내용을 로그에 표시한다.
	 * @param element
	 */
	public void debug(Node element) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(element), new StreamResult(out));
			System.out.println("=====> XML : " + new String(out.toByteArray()));
		}
		catch (Exception e) {
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
	}
	
	/**
	 * org.jdom2.Document의 xml을 로그에 표시함.
	 * @param doc	org.jdom2.Document
	 */
	public void debug(Document doc) {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		System.out.println("=====> XML : " + outputter.outputString(doc));
	}
	
	
	/**
	 * SOAPMessage의 xml을 로그에 표시함
	 * @param soap	SOAPMessage
	 */
	public void debug(SOAPMessage soap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		StringWriter writer = new StringWriter();
		try {
//			TransformerFactory.newInstance().newTransformer()
//				.transform(new DOMSource(soap.getSOAPPart()), new StreamResult(writer));
//			LOG.info("=====> SOAP : {0}", writer.toString());
			
			soap.writeTo(out);
			System.out.println("=====> SOAP : {0}=>[" + new String(out.toByteArray(),"UTF-8") + "]");
		} catch (SOAPException | IOException e) {
			System.out.println("##### Error on printing SOAP #####");
		}
	}
}
