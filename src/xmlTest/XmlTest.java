package xmlTest;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlTest {
	public static void main(String[] args) throws Exception {
		Document document = XmlUtil.load("file/xml/default_ga_qemu.xml");
		document.getDocumentElement().normalize();
		
		Element root = document.getDocumentElement();
		
		// toString()
		//System.out.println(XmlUtil.docToStringUsingLSSerializer(document));
		
		// <Tag>Value</Tag>
		System.out.println(XmlUtil.getTagValue(root, "memory", 0));
		
		// replace()
		XmlUtil.setTagValue(root, "name", 0, "instanceId");
		
		XmlUtil.setFirstAttrValue(root, "topology", "sockets", String.valueOf("4"));
		
//		Element e = XmlUtil.getElementByTag(root, "interface", 0);
		
//		XmlUtil.addXmlNode(document, "mac", "", XmlUtil.findNodeByAttribute(root, "interface", "type"));
		
//		XmlUtil.addXmlAttribute(document, "mac", "address", "aa:bb:cc:dd:ee");
		
		System.out.println(XmlUtil.getTagValue(root, "name", 0));
		
		// toString()
		System.out.println(XmlUtil.docToStringUsingLSSerializer(document));
		
		Element elem = XmlUtil.getFirstElementByTagName(root, "pjw");
		
		System.out.println(XmlUtil.getFirstTagParams(document, "pjw").get("django"));
	}
}
