package xmlTest;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.xml.sax.SAXException;

import org.w3c.dom.ls.*;

/**
 * Document Utils -> Xml Utils -> XmlLoader
 */
public class XmlUtil {
	static Logger xmlLogger = Logger.getLogger(XmlUtil.class);
	
	public static Document load(File file) throws SAXException, IOException, ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
	}
	
	public static Document load(String desc) throws SAXException, IOException, ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(desc);
	}
	
	public static String docToStringUsingLSSerializer(Document document) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
	    DOMImplementationRegistry reg = DOMImplementationRegistry.newInstance();
	    DOMImplementationLS impl = (DOMImplementationLS) reg.getDOMImplementation("LS");
	    LSSerializer serializer = impl.createLSSerializer();
	    return serializer.writeToString(document);
	}
	
	public static Element createElement(Document document, String tagName, String tagValue) {
		Element tag = document.createElement(tagName);

		tag.appendChild(document.createTextNode(tagValue));
		
		return tag;
	}
	
	/**
	 * 
	 */
	public static void appendTag(Element parent, Element child) {
		parent.appendChild(child);
	}
	
	/**
	 * @return Element
	 */
	public static Element getElementByTagName(Element element, String tagName, int index) {
		Node node = element.getElementsByTagName(tagName).item(index);
		
		return (Element) node;
	}
	
	/**
	 * @return value, <tag>value</tag>
	 */
	public static String getTagValue(Element element, String tagName, int index) {
		Node node = element.getElementsByTagName(tagName).item(index).getChildNodes().item(0);
		
		return node.getNodeValue();
	}
	
	/**
	 * @set value, <tag>value</tag>
	 */
	public static void setTagValue(Element element, String tagName, int index, String value) {
		Node node = element.getElementsByTagName(tagName).item(index);
		node.setTextContent(value);
	}
	
	
	public static void setTagAttr(Element element, String attrName, String attrValue) {
		element.setAttribute(attrName, attrValue);
	}
	
	/**
	 * @param element, tagName, paramTagName, attrName, value
	 * 
	 * <tag>
	 * 	<param1 attr1="value1" attr2="value2"/>
	 * 	<param2 attr1="value3" attr2="value4"/>
	 * </tag>
	 * 
	 * -->
	 * 
	 * @input param1, attr1, value
	 * @set <param1 attr1="value", .../>
	 */
	public static void setFirstTagParamAttr(Element element, String tagName, String paramTagName, String attrName, String value) {
		NodeList nodeList = element.getElementsByTagName(tagName).item(0).getChildNodes();
		
		Element _element = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				_element = (Element) nodeList.item(i);
				if (!_element.getTagName().equals(paramTagName)) {
					continue;
				}
				
				_element.getAttributes().getNamedItem(attrName).setNodeValue(value);
			}
		}
	}
	
	/**
	 * 
	 */
	public static List<String> getAttrValuesByTagNameAndAttrName(Element parent, String tagName, String attrName) {
		List<String> rtn = new ArrayList<String>();
		
		NodeList nodeList = parent.getChildNodes();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			NodeList _nodeList = nodeList.item(i).getChildNodes();

			for (int j = 0; j < _nodeList.getLength(); j++) {
				if (_nodeList.item(j).getNodeName().equals(tagName)) {
					if (_nodeList.item(j).getAttributes().getNamedItem(attrName) != null) {
						String value = _nodeList.item(j).getAttributes().getNamedItem(attrName).getNodeValue();
						
						rtn.add(value);
					}
				}
			}
		}
		
		return rtn;
	}
	
	/**
	 * @get value, <tag attr="value"/>
	 */
	public static String getFirstAttrValue(Element element, String tagName, String attrName) {
		Node node = findNodeByAttribute(element, tagName, attrName);
		NamedNodeMap attr = node.getAttributes();
		Node nodeAttr = attr.getNamedItem(attrName);
		
		return nodeAttr.getNodeValue();
	}
	
	/**
	 * @set value, <tag attr="value"/>
	 */
	public static void setFirstAttrValue(Element element, String tagName, String attrName, String value) {
		Node node = findNodeByAttribute(element, tagName, attrName);
		NamedNodeMap attr = node.getAttributes();
		Node nodeAttr = attr.getNamedItem(attrName);

		if (value != null) {
			nodeAttr.setTextContent(value);
		}
	}
	
	/**
	 * Attribute의 노드를 검색한다. 없으면 null값을 리턴
	 */
	public static Node findNodeByAttribute(Element element, String tagName, String attrName) {
		Node node = null;
		NodeList list = element.getElementsByTagName(tagName);

		for (int i = 0; i < list.getLength(); i++) {
			node = list.item(i);
			NamedNodeMap attr = node.getAttributes();

			if (attr.getNamedItem(attrName) != null) {
				return node;
			}
		}
		
		return node;
	}
	
	/**
	 * 
	 * @param document
	 * @param eleName
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	public static Element addXmlAttribute(Element element, String eleName, String attributeName, String attributeValue) {
		Element ele = (Element) element.getElementsByTagName(eleName).item(0);
		ele.setAttribute(attributeName, attributeValue);
		return ele;
	}
	
	
	/**
	 * @param element, tagName, paramTagName, attrName
	 * 
	 * <tag>
	 * 	<param1 name="myName" value="myValue"/>
	 * </tag>
	 * 
	 * -->
	 * 
	 * @input param1, attr1
	 * @return Map < {"attr1", "value1"} >
	 */
	public static Map<String, String> getFirstTagParams(Document document, String tagName) {
		Map<String, String> map = new HashMap<String, String>();
		
		NodeList nodeList = document.getElementsByTagName(tagName).item(0).getChildNodes();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Node node = nodeList.item(i);
				
				String paramName = node.getAttributes().getNamedItem("name").getNodeValue();
				String paramValue = node.getAttributes().getNamedItem("value").getNodeValue();
				
				map.put(paramName, paramValue);
			}
		}
		
		return map;
	}
	
	public static Element getFirstElementByTagName(Element element, String tagName) {
		NodeList nodeList = element.getElementsByTagName(tagName);
		
		Element _element = null;
		for (int idx = 0; idx < nodeList.getLength(); idx++) {
			if (nodeList.item(idx).getNodeType() == Node.ELEMENT_NODE) {
				_element = (Element) nodeList.item(idx);
				break;
			}
		}
		
		return _element;
	}
	
	public static List<Node> getChildNodeList(Element element) {
		NodeList nodeList = element.getChildNodes();
		
		List<Node> childNodeList = new ArrayList<Node>();
		
		for (int idx = 0; idx < nodeList.getLength(); idx++) {
			if (nodeList.item(idx).getNodeType() == Node.ELEMENT_NODE) {
				childNodeList.add(nodeList.item(idx));
			}
		}
		
		return childNodeList;
	}
	
	// <"name" attribute, org.w3c.dom.Node>로 매핑
	public static Map<String, Node> fromNodeListToMap(NodeList nodeList, String nameAttr) {
		Map<String, Node> result = new HashMap<String, Node>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			if (node.getNodeName().contains(nameAttr)) {
				result.put(node.getAttributes().getNamedItem("name").getNodeValue(), node);
			}
		}
		
		return result;
	}
}