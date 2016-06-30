/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import com.gargoylesoftware.htmlunit.javascript.host.NodeList;
import com.sun.org.apache.xerces.internal.dom.NodeImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.xalan.xsltc.compiler.util.Type;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;

/**
 *
 * @author motamedi
 */
public class VPLS extends Launcher implements LauncherInterface {

	String cssSelector = "#result";

	/**
	 *
	 * @param line
	 * @param folder
	 */
	@Override
	public void config(String line, String folder) {
		result = "fail";
		baseUrl = ".lg.as35908.net/bgplg/traceroute.xml";
//		baseUrl = "http://lg.as35908.net/";
		super.configure(line, folder);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String launch() {
		try {
			Connection c = Jsoup.connect("http://" + locVar + baseUrl);
			c.data("format", "xml");
			c.data("query", "trace4");
			c.data("arg", dstIP);
			c.referrer(baseUrl);
			c.timeout(100000);
			System.out.println("locVar: " + locVar);
			System.out.println("dstIP: " + dstIP);

			Document docHTML = c.post();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			org.w3c.dom.Document doc = dBuilder.parse(docHTML.html().toString());
			String htm = docHTML.html();
			System.err.print(htm);

			ByteArrayInputStream stream = new ByteArrayInputStream(htm.getBytes());

			result = "";
			org.w3c.dom.Document doc = dBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			org.w3c.dom.NodeList nList = doc.getElementsByTagName("hop");
			for (int j = 0; j < nList.getLength(); j++) {
				Node nNode = nList.item(j);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;
//					System.out.println("hostname: " + eElement.getElementsByTagName("hostname").item(0).getTextContent());
//					System.out.println("ip: " + eElement.getElementsByTagName("ip").item(0).getTextContent());
//					System.out.println("as: " + eElement.getElementsByTagName("as").item(0).getTextContent());
					result += (j + 1) + "\t"
							+ eElement.getElementsByTagName("hostname").item(0).getTextContent().trim() + "\t"
							+ "(" + eElement.getElementsByTagName("ip").item(0).getTextContent().trim() + ")\t";
					org.w3c.dom.NodeList ttlList = eElement.getElementsByTagName("ttl");
					for (int i = 0; i < ttlList.getLength(); i++) {
//						System.out.println("ttl: " + ttlList.item(i).getTextContent());
						result += ttlList.item(i).getTextContent().trim() + " ms\t";
					}
				}
				result += "\n";
			}
			System.err.println(result);

//			result = doc;
			//Data was in an inconvenient layout. This minimizes the formatting issues.
//			int counter = -4;
//			for (int i = 0; i < result.length(); i++) {
//				if ((result.charAt(i) == '\n' && (++counter) % 21 != 0) || result.charAt(i) == ' ') {
//					result = result.substring(0, i) + result.substring(i + 1, result.length());
//				}
//			}
//			Element table = doc.select(cssSelector).first();
//			System.out.println("table:" + table.html());
//			Elements rows = table.getElementsByTag("tr");
//			for (Element row : rows) {
//				for (Element cell : row.getElementsByTag("td")) {
//					result += cell.text().trim() + "\t";
//				}
//				result += "\n";
//				System.out.println(result);
//				System.out.println("------");
//			}
			System.out.println(result);
		} catch (IOException ex) {
			Logger.getLogger(VPLS.class.getName()).log(Level.SEVERE, null, ex);
			System.err.println(result);
		} finally {
			super.store();
			return result;
		}
	}
}
