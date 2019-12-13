package xmlTest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlTest {
	public static void main(String[] args) throws Exception {
		Document document = XmlUtil.load("file/xml/default_ga_qemu.xml");
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getChildNodes();
		Element domain = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				domain = (Element) nodeList.item(i);
			}
		}
		System.out.println(domain.getTextContent());
	}
}
