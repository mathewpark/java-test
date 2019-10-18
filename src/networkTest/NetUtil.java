package networkTest;
import java.util.*;
import java.util.regex.Pattern;
import java.net.*;
import java.io.*;

import org.apache.log4j.Logger;

public class NetUtil
{
	private static Logger logger = Logger.getLogger(NetUtil.class);

	public static String host2Ip(String strHost)
	{
		String strIpAddr = "";
		if (strHost.isEmpty()) {
			//throw NullPointerException("host is null");
			return null;
		}

		/*
		String strLocalHost = "localhost";
		if(strHost.equals(strLocalHost))
		{
			strIpAddr = "127.0.0.1";
			return strIpAddr;
		}
		*/
	
		try
		{
			InetAddress strAddr = InetAddress.getByName(strHost);
			/*
			byte[] ipAddr = strAddr.getAddress();
			for (int i = 0; i<ipAddr.length ; i++)
			{
				if(i > 0) strIpAddr += ".";
				strIpAddr += ipAddr[i] & 0xFF;
			}*/
			strIpAddr = strAddr.getHostAddress();
		}
		catch (UnknownHostException e)
		{
			logger.warn(e);
		}
		return strIpAddr;
	}

	public static int dot2hex(String ip)
	{
		int value = 0;
	
		try
		{
			value = InetAddress.getByName(ip).hashCode();
		}
		catch (UnknownHostException e)
		{
		}
		return value;
	}

	public static String hex2dot(int in)
	{
		String out;
		int unsignedByte = 0;
		// MSB�� LSB�� �̵�
		unsignedByte = ((in & 0xFF000000)>>24); 
		// �̵��� ���� - �ϰ��(signed) 256�� ���ؼ� unsigned�� �����Ѵ�. 
		unsignedByte = unsignedByte < 0 ? (unsignedByte + 256) : unsignedByte;
		out = Integer.toString(unsignedByte) + ".";

		unsignedByte = 0;
		unsignedByte = ((in & 0x00FF0000)>>16);
		unsignedByte = unsignedByte < 0 ? (unsignedByte + 256) : unsignedByte;		
		out += Integer.toString(unsignedByte) + ".";

		unsignedByte = 0;
		unsignedByte = ((in & 0x0000FF00)>>8);
		unsignedByte = unsignedByte < 0 ? (unsignedByte + 256) : unsignedByte;		
		out += Integer.toString(unsignedByte) + ".";

		unsignedByte = 0;
		unsignedByte = (in & 0x000000FF);
		unsignedByte = unsignedByte < 0 ? (unsignedByte + 256) : unsignedByte;
		out += Integer.toString(unsignedByte);
		return out;
	}

	public static int int2unsigned(int in)
	{
		int unsignedByte = 0;
		// MSB�� LSB�� �̵�
		unsignedByte = ((in & 0xFF000000)>>24);
		unsignedByte = unsignedByte < 0 ? (unsignedByte + 256) : unsignedByte;

		unsignedByte = 0;
		unsignedByte = ((in & 0x00FF0000)>>16);
		unsignedByte = unsignedByte < 0 ? (unsignedByte + 256) : unsignedByte;		

		unsignedByte = 0;
		unsignedByte = ((in & 0x0000FF00)>>8);
		unsignedByte = unsignedByte < 0 ? (unsignedByte + 256) : unsignedByte;		

		unsignedByte = 0;
		unsignedByte = (in & 0x000000FF);
		unsignedByte = unsignedByte < 0 ? (unsignedByte + 256) : unsignedByte;

		return unsignedByte;
	}

	public static boolean maczero(byte[] in)
	{
		byte zeroMac[] = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
		return Arrays.equals(in, zeroMac);
	}
	
	public static boolean machexcmp(String ina, byte[] inb)
	{		
		return true;
	}

	public static boolean mac2hex(String mac, byte[] out)
	{
		/*
		byte tmp[] = new byte[6];
		if (!mac.isEmpty()) {
			return false;
		}

		tmp[0] = 
		*/
		return true;
	}

	/*
	 * log2 �Լ��� �����Ѵ�
	 *
	 */
	public static double log2(double d)
	{
		return (Math.log(d)) / (Math.log(2));
	}

	/*
	 * strDir�� directory���� Ȯ��
	 * 
	 */
	private static boolean check_directory(String strDir)
	{
		if (strDir.isEmpty()) {
			return false;
		}

		File dir = new File(strDir);
		if (dir.isDirectory()) {
			return true;
		}
		return false;
	}

	/*
	 *  device�� ������ �Ǵ��Ѵ�.
	 *  true : deivce ����
	 *  false : device ����
	 */
	public static boolean check_device(String strDev)
	{
		if (strDev.isEmpty()) {
			return false;
		}

		String strDevFullPath;
		strDevFullPath = String.format("/sys/class/net/%s/", strDev);

		if (check_directory(strDevFullPath)) {
			return true;
		}
		return false;
	}

	public static boolean check_deviceup(String strDev)
	{
		String strDevState;
		if (!check_device(strDev)) {
			return false;
		}

		try
		{
			strDevState  = String.format("/sys/class/net/%s/operstate", strDev);
			BufferedReader br = new BufferedReader( new FileReader(strDevState) );
			String strState;
			strState = br.readLine();
			if (!strState.isEmpty())
			{
				if ( strState.equals("up")) {
					return true;
				}	
			}
			br.close();
		}
		catch (IOException e)
		{
		}
		return false;
	}

	public static boolean check_bridge(String strBrName)
	{
		String strFile;
		if (strBrName.isEmpty() || check_device(strBrName)) {
			return false;
		}

		strFile = String.format("/sys/class/net/%s/bridge/", strBrName);
		if (check_directory(strFile))
		{
			return false;
		}
		return true;
	}

	public static boolean safekill(String strPIDFile, String strDHCPdaemon, int signal)
	{
		if (strPIDFile.isEmpty() || strDHCPdaemon.isEmpty() || signal <= 0) {
			return false;
		}

		try
		{
			BufferedReader br = new BufferedReader( new FileReader(strPIDFile) );
			String strPID;
			strPID = br.readLine();
			if (!strPID.isEmpty())
			{
				int pid	= Integer.parseInt(strPID);
				if (pid < 2) {
					return false;
				}

				
				
				//logger.info("pid : " + pid);

				String strLineCheck;
				strLineCheck = String.format("/proc/%d/cmdline", pid);
				File fFile = new File(strLineCheck);
				if (fFile.isFile()) 
				{

					// 임시로 그냥 죽여버림
					String strCmd;
					strCmd = String.format("kill -%d %d", signal, pid);
//					BashExecReturn re = BashExec.run(strCmd);
//					if (re.getExitValue() != 0) {
//						logger.warn(re.getStdErr());
//						return false;
//					}
					return true;
					// 
					
					/*
					br = new BufferedReader( new FileReader(strLineCheck) );
					String strDaemon = br.readLine();
					if( !strDaemon.isEmpty() )
					{
						String strDhcpExecuteCmd = strDHCPdaemon + "-cf/etc/gcloud-dhcp.conf-lf/etc/gcloud-dhcp.leases-pf/var/run/gcloud-dhcp.pid-tf/etc/gcloud-dhcp.trace ";
						//strDaemon.replaceAll("\\p{Space}", " ");
						
						logger.info("===============================================");
						logger.info("origina : " + strDhcpExecuteCmd + " " + strDhcpExecuteCmd.length());
						logger.info("===============================================");
						logger.info("cmdline : " + strDaemon + " " + strDaemon.length());
						logger.info("===============================================");
									
						if ( strDhcpExecuteCmd.equals(strDaemon) )
						{
							String strCmd;
							strCmd = String.format("kill -%d %d", signal, pid);
							BashExecReturn re = BashExec.run(strCmd);
							if (re.getExitValue() != 0) {
								logger.warn(re.getStdErr());
							}
						}
						else 
						{
							logger.info("not equals");
							return false;
						}
					}
					else
						return false;
					*/
				}
				else 
					return false;
			}
			br.close();
		}
		catch (IOException e)
		{
		}
		return true;
	}
	
	public static boolean checkIpGroup(String ip_group) {
        boolean okChk = false;

        // 111.111.111.111/32, 0.0.0.0/0
        String patternRegex = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\/(?:3[012]|[012]?[0-9])$";

        okChk = Pattern.matches(patternRegex, ip_group);
        
        if (okChk) {
        	/*@SuppressWarnings("static-access")
			InetAddressValidator ipAddressValidator = new InetAddressValidator().getInstance();
        	
        	String[] addr = ip_group.split("/");
        	
        	if (!ipAddressValidator.isValid(addr[0])) {
        		return false;
        	}
        	
        	String[] addrSplit = addr[0].split("[.]");
        	
        	if (Integer.parseInt(addrSplit[0]) != 0 && Integer.parseInt(addr[1]) == 0) {
        		okChk = false;
        	}
        	
        	if (addrSplit[0].substring(0,1).equals("0") && addrSplit[0].length() >= 2) {
        		okChk = false;
        	}*/
        	
        	String[] addr = ip_group.split("/");
        	
        	if (Integer.parseInt(addr[1]) != 32 && Integer.parseInt(addr[1]) != 0) {	// cidr 0, 32 pass
            	SubnetUtil utils = new SubnetUtil(ip_group);
            	
            	//For use w/ /32 CIDR subnets
            	utils.setInclusiveHostCount(true);
            	
            	if (utils.getInfo().getNetworkAddress().equals(addr[0])) {
            		return true;
            	} else {
            		return false;
            	}
        	} else if (Integer.parseInt(addr[1]) == 0) {	// cidr 0 is networkaddr(0.0.0.0)
        		if (!addr[0].equals("0.0.0.0")) {
        			return false;
        		} else {
        			return true;
        		}
        	} else {
        		return true;
        	}
        }

        return okChk;
    }

	/*
	public static int checkBridgeDevice(String br, String dev)
	{
	}

	public static int checkBridgestp(String br)
	{
	}

	public static int checkDeviceUp(String dev)
	{
	}

	
	public static String hex2dot(byte[] in)
	{
		
	}
	*/
	
	public static boolean checkFixedIp(String ip) {
		String patternRegex = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
		
		return Pattern.matches(patternRegex, ip);
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
};