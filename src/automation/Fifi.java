/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author motamedi
 */
public class Fifi extends Launcher implements LauncherInterface {

	/**
	 *
	 * @param line
	 * @param folder
	 */
	@Override
	public void config(String line, String folder) {
		result = "fail";
		baseUrl = "http://www.fifi.org/services/traceroute";
		super.configure(line, folder);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String launch() {
		try {
			Document doc = Jsoup.connect(baseUrl + "?hostname=" + dstIP + "&nprobes=3&resolved=no&submit=Traceroute").timeout(100000).get();
//			c.followRedirects(true);
//			c.header("Content-Type", "application/x-www-form-urlencoded");

			System.out.println("locVar: " + locVar);
			System.out.println("dstIP: " + dstIP);

			// Additional parsing logic needed for table
			Elements trs = doc.getElementsByTag("table").last().getElementsByTag("tr");
			StringBuilder l = new StringBuilder();
			for (Element tr : trs.subList(1, trs.size())) {
//				System.err.println(tr.text());
				Elements cols = tr.getElementsByTag("td");
//				System.err.println(cols.size());
				int cnt = 0;
				for (Element c : cols) {
					if ((cnt != 1) && (cnt != 2) && (cnt != 3)) {
						l.append(c.text() + "\t");
					}
					cnt++;
				}
				l.append("\n");
//				System.out.println(l.toString());
			}
			result = l.toString();

//		}
//			String[] temp = Jsoup.parse(doc.getElementsByTag("table").last().html()).text().substring(96).replace("0% 1 1 ", "").split(" ");
//			result = "";
//			for (int i = 0; i < temp.length; i++) {
//				if (i % 4 != 0) {
//					temp[i] = " " + temp[i] + "ms";
//					if (i % 4 == 3) {
//						temp[i] += "\n";
//					}
//				}
//				result += temp[i];
//			}
		} catch (IOException ex) {
			Logger.getLogger(Fifi.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(result);
		} finally {
			super.store();
			return result;
		}
	}
}
