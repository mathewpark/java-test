package networkTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetworkAddressTestMain {
	public static void main(String[] args) throws Exception {
		/**
		 * ip 테스트
		 */
		String subnetworkIp = "10.222.222.1";

		int maskNum = 24;
		int validBit = 32 - maskNum;
		int validMask = 0;
		for (int i = 0; i < validBit; i++) {
			validMask += (1 << i);
		}

		long maxValue = (((NetUtil.dot2hex(subnetworkIp) | validMask)) & 0xFFFFFFFFL) - 1;

		System.out.println(NetUtil.dot2hex(subnetworkIp));
		System.out.println(maxValue);

		int hexNetIp = NetUtil.dot2hex(subnetworkIp);

		System.out.println(NetUtil.hex2dot(hexNetIp + 1));
		System.out.println(NetUtil.hex2dot(hexNetIp + 2));
		System.out.println(NetUtil.hex2dot(hexNetIp + 3));

		String patternRegex = "^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$";

		String subnet = "192.168.0.0";
		String netmask = "255.255.0.0";

		String network = String.format("%s/%d", subnet, NetUtil.convertNetmaskToCIDR(InetAddress.getByName(netmask)));

		SubnetUtil utils = new SubnetUtil(network);

		SubnetUtil.SubnetInfo info = utils.getInfo();
		System.out.println(info.getNetworkAddress());
		System.out.println(info.getAddress());
		System.out.println(info.getBroadcastAddress());
		System.out.println(info.getLowAddress());
		System.out.println(info.getHighAddress());

		String[] temp = info.getLowAddress().split("\\.");
		for (int i = 0; i < temp.length; i++) {
			System.out.println(temp[i]);
		}

		String[] all = info.getAllAddresses();
		for (int i = 0; i < all.length; i++) {
			System.out.println("# : " + all[i]);
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * ip 할당
		 */
		String subnetIp = generateSubnetIp(24);
		long hexa = getFreePrivateIp(new ArrayList<Long>(), subnetIp, 24);
		String ip = transferHexIp2Ip(hexa);

		System.out.println("SubnetIp: " + subnetIp);
		System.out.println("hexa: " + getFreePrivateIp(new ArrayList<Long>(), subnetIp, 24));
		System.out.println("SubnetIp: " + ip);

		validMask = 0;

		for (int i = 0; i < 8; i++) {
			validMask += (1 << i);
		}

		System.out.println(validMask);

		maxValue = (((NetUtil.dot2hex(subnetIp) | validMask)) & 0xFFFFFFFFL) - 1;

		System.out.println(maxValue);
	}
	
	public static String generateSubnetIp(int cidr) { // 24
		int emptyNum = 32 - cidr; // 8
		int fillNum = 24 - (emptyNum); // 16
		byte[] rawBytes = new byte[3];
		Random random = new Random();

		random.nextBytes(rawBytes); // [ # # # # ]

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < fillNum; i++) {
			sb.append(random.nextInt(2));
		}
		for (int i = 0; i < emptyNum; i++) {
			sb.append('0');
		}

		String binStr = sb.toString();

		rawBytes[0] = Byte.parseByte(binStr.substring(0, 7), 2);
		rawBytes[1] = Byte.parseByte(binStr.substring(8, 15), 2);
		rawBytes[2] = Byte.parseByte(binStr.substring(16, 23), 2);

		return String.format("10.%d.%d.%d", rawBytes[0], rawBytes[1], rawBytes[2]);
	}
	
	public static String transferHexIp2Ip(long hexIp) {
		return NetUtil.hex2dot((int) hexIp);
	}
	
	public static long getFreePrivateIp(List<? extends Long> sortedAllocatedHexIp, String subnetworkIp, int maskNum)
			throws Exception {
		/*
		 * if (maskNum > 31) { throw new InvalidInputException( String.format(
		 * "curr_mask_num=%d. maskNum should be less then 32", maskNum)); }
		 */

		if (sortedAllocatedHexIp.size() == 0) {
			long hexNetIp = NetUtil.dot2hex(subnetworkIp);
			return hexNetIp + 2;// Ip start from 2
		} /*
			 * else { long hexNetIp = NetUtil.dot2hex(subnetworkIp); long
			 * hexStartIp = hexNetIp + 2;
			 * 
			 * if ((sortedAllocatedHexIp.get(0)) != hexStartIp) { return
			 * hexStartIp; } }
			 */

		int validBit = 32 - maskNum;
		int validMask = 0;
		for (int i = 0; i < validBit; i++) {
			validMask += (1 << i);
		}

		long maxValue = (((NetUtil.dot2hex(subnetworkIp) | validMask)) & 0xFFFFFFFFL) - 1;

		long expectedNext;
		for (int i = 0; i < sortedAllocatedHexIp.size() - 1; i++) {
			expectedNext = sortedAllocatedHexIp.get(i) + 1;
			if (expectedNext != sortedAllocatedHexIp.get(i + 1)) {

				if (expectedNext > maxValue) {
					throw new Exception();
				} else {
					return expectedNext;
				}
			}
		}

		expectedNext = sortedAllocatedHexIp.get(sortedAllocatedHexIp.size() - 1) + 1;
		if (expectedNext > maxValue) {
			throw new Exception();
		} else {
			return expectedNext;
		}
	}
	
	public static int convertNetmaskToCIDR(InetAddress netmask) {

		byte[] netmaskBytes = netmask.getAddress();
		int cidr = 0;
		boolean zero = false;
		for (byte b : netmaskBytes) {
			int mask = 0x80;

			for (int i = 0; i < 8; i++) {
				int result = b & mask;
				if (result == 0) {
					zero = true;
				} else if (zero) {
					throw new IllegalArgumentException("Invalid netmask.");
				} else {
					cidr++;
				}
				mask >>>= 1;
			}
		}
		return cidr;
	}
}
