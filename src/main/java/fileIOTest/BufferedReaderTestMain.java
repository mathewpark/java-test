package fileIOTest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

public class BufferedReaderTestMain {
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public static Certificate generateCertificate(String type, String cert_code)
			throws UnsupportedEncodingException, CertificateException {
		InputStream cert_code_is = new ByteArrayInputStream(cert_code.getBytes("utf-8"));
		CertificateFactory certificateFactory = CertificateFactory.getInstance(type);

		switch (type) {
		case "X.509":
			return (X509Certificate) certificateFactory.generateCertificate(cert_code_is);
		default:
			// throw new ClusterException();
		}

		return null; // TODO impl
	}
	
	public static void main(String[] args) throws IOException, CertificateException {
		FileReader fr = new FileReader("file/cert.pem");
		BufferedReader br = new BufferedReader(fr);

		StringBuilder sb = new StringBuilder();

		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		
		X509Certificate cert = (X509Certificate) generateCertificate("X.509", sb.toString());
		// X500Principal principal = (X500Principal) cert.getSubjectDN();
		// ASN1InputStream aIn = new ASN1InputStream(new
		// ByteArrayInputStream(principal.getEncoded()));
		// ASN1Sequence dns;

		System.out.println(cert.getSubjectDN().getName());
		System.out.println(dateFormat.format(cert.getNotBefore()));
		System.out.println(dateFormat.format(cert.getNotAfter()));
		System.out.println(cert.getIssuerDN().getName());
		// System.out.println(cert.);

		System.out.println("/////////////////////////////////////////////////////////////////////");
		System.out.println(cert.toString());
		System.out.println("/////////////////////////////////////////////////////////////////////");
		System.out.println(cert.getSubjectAlternativeNames());
		System.out.println("/////////////////////////////////////////////////////////////////////");

		Collection<List<?>> san = cert.getSubjectAlternativeNames();

		Iterator<List<?>> itr = san.iterator();

		while (itr.hasNext()) {
			List<?> list = itr.next();
			for (Object obj : list) {
				System.out.println(obj.toString());
				// String subList = (String) obj;
				//
				// System.out.println(subList);
				//
				// for (Object subObj : subList) {
				// System.out.println(subObj.toString());
				// }
			}
		}

		System.out.println("/////////////////////////////////////////////////////////////////////");

		Collection<List<?>> altNames = cert.getSubjectAlternativeNames();
		if (altNames != null) {
			for (List<?> altName : altNames) {
				Integer altNameType = (Integer) altName.get(0);
				if (altNameType != 2 && altNameType != 7) // dns or ip
					continue;
				System.out.println((String) altName.get(1));
			}
		}

		System.out.println("/////////////////////////////////////////////////////////////////////");

		org.bouncycastle.asn1.x500.X500Name x500name = new JcaX509CertificateHolder(cert).getSubject();

		RDN[] cns = x500name.getRDNs();

		// RDN[] cns = x500name.getRDNs(BCStyle.CN);

		for (int i = 0; i < cns.length; i++) {
			RDN cn = cns[i];

			//
			System.out.println(IETFUtils.valueToString(cn.getFirst().getValue()));
			System.out.println("raw: " + cn.getFirst().getValue());
		}
		
		br.close();
		fr.close();
	}
}
