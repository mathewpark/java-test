package securityTest;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestTestMain {
	public static void main(String[] agrs) throws NoSuchAlgorithmException {
		String vmNum = "vm_1234";
		
		 MessageDigest md = MessageDigest.getInstance("MD5");
		 byte[] messageDigest = md.digest(vmNum.getBytes());
		 BigInteger number = new BigInteger(1, messageDigest);
		 String hostName = number.toString(16);
		
		 System.out.println(hostName);
	}
}
