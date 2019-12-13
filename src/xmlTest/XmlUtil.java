package xmlTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Document Utils -> Xml Utils -> XmlLoader
 */
public class XmlUtil {
	static Logger xmlLogger = Logger.getLogger(XmlUtil.class);
	
	public static Document load(String fPath) throws SAXException, IOException, ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(fPath));
	}
	
	/**
	 * 
	 * <Default>
	 * 	<param name="name" type="type" value="value"/>
	 * </Default>
	 * 
	 * ->
	 * 
	 * Map < "name", "value" >
	 */
	public static Map<String, String> getDefaultValue(String tagName, Element element) {
		Map<String, String> map = new HashMap<String, String>();
		
		NodeList nodeList = element.getElementsByTagName(tagName).item(0).getChildNodes();
		
		Element _element = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				_element = (Element) nodeList.item(i);
				String paramName = _element.getAttributes().getNamedItem("name").getNodeValue().toString();
				String paramValue = _element.getAttributes().getNamedItem("value").getNodeValue().toString();
				
				map.put(paramName, paramValue);
			}
		}
		
		return map;
	}
	
	public static String getTagValue(String tagName, Element element) {
		NodeList nodeList = element.getElementsByTagName(tagName).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		
		return node.getNodeValue();
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