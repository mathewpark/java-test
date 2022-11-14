package xmlTest;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlTest {
	public static void main(String[] args) throws Exception {
		Document document = XmlUtil.load("file/xml/default_ga_afa.xml");
		document.getDocumentElement().normalize();
		
		Element root = document.getDocumentElement();
		
		// TODO check
		
		// name
		XmlUtil.setTagValue(root, "name", 0, "vm_1234");
		
		// cpu
		Element cpu = XmlUtil.getFirstElementByTagName(root, "cpu");
		
		// devices
		Element devices = XmlUtil.getFirstElementByTagName(root, "devices");
		
		// disk
		Element disk = XmlUtil.getFirstElementByTagName(devices, "disk");
		
		// interface
		Element inf = XmlUtil.createElement(document, devices, "interface", "");
		
//				if (inf == null) {
//					inf = XmlUtil.createElement(devices, "interface", "");
//				}
		
		// agent
		Element channel = XmlUtil.getFirstElementByTagName(devices, "channel");
		XmlUtil.setFirstAttrValue(channel, "source", "path", String.format("/var/lib/libvirt/qemu/%s.agent", "vm_1234"));
		
		// image
		XmlUtil.setFirstAttrValue(disk, "source", "dev", "/dev/mapper/vm_1234");
		
		// busType
		XmlUtil.setFirstAttrValue(disk, "target", "bus", "virtio");
		
		// vcpu
		XmlUtil.setTagValue(root, "vcpu", 0, String.valueOf(1));
		
		// cpu
		XmlUtil.setFirstAttrValue(cpu, "topology", "sockets", String.valueOf(1));
		XmlUtil.setFirstAttrValue(cpu, "topology", "cores", String.valueOf(1));
		
		// memory
		XmlUtil.setTagValue(root, "memory", 0, String.valueOf(1048576));
		
		// bridge
		XmlUtil.setTagAttr(inf, "type", "bridge");
		Element source = XmlUtil.createElement(document, inf, "source", "");
		Element model = XmlUtil.createElement(document, inf, "model", "");
		
		XmlUtil.appendTag(inf, source);
		XmlUtil.appendTag(inf, model);
		
		XmlUtil.addXmlAttribute(inf, "source", "bridge", "br-1-1");
		XmlUtil.addXmlAttribute(inf, "model", "type", "virtio");
		
		// mac
		Element mac = XmlUtil.createElement(document, inf, "mac", "");
		XmlUtil.appendTag(inf, mac);
		XmlUtil.setTagAttr(mac, "address", "aa:bb:cc:dd:ee:ff");
		/*for (int i = 1; i < request.getMacAddress().length; i++) {
			// TODO
		}*/
		
		// nic
		Element target = XmlUtil.createElement(document, inf, "target", "");
		XmlUtil.appendTag(inf, target);
		XmlUtil.setTagAttr(target, "dev", "vm1eth0");

		// disk blocks
//		if (request.getDataBlockPaths() != null) {
//			for (int i = 0; i < request.getDataBlockPaths().length; i++) {
//				String volume = request.getDataBlockPaths()[i];
//	
//				String deviceName = getFreeDeviceName(devices);
//				
//				if (deviceName == null) {
//					throw new Exception(String.format("DefineXmlControl.getFreeDeviceName() failed."));
//				}
//				
//				String busType = getBusType(disk);
//				String cacheType = getCacheType(disk);
//				String ioType = getIoType(disk);
//				
//				LunAttachDevice(busType, cacheType, ioType, volume, deviceName);
//				
//				List<String> storagePathList = XmlUtil.getAttrValuesByTagNameAndAttrName(devices, "source", "dev").stream().filter(str -> !str.contains("vm")).collect(toList());
//				if (!storagePathList.contains(volume)) {
//					throw new Exception(String.format("DefineXmlControl.getStoragePathList().contains(%s) == false.", volume));
//				}
//			}
//		}
		
		System.out.println(XmlUtil.docToStringUsingLSSerializer(document));
	}
}
