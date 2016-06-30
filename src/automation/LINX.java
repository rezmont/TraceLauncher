/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author motamedi
 */
public class LINX extends Launcher implements LauncherInterface {

	/**
	 *
	 * @param line
	 * @param folder
	 */
	@Override
	public void config(String line, String folder) {
		result = "fail";
		baseUrl = "https://stats.linx.net/cgi-pub/xlg.pl";
		super.configure(line, folder);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String launch() {
		try {
			Document doc = Jsoup.connect(baseUrl + "?run=true&site=LINX-London&query_type=+Trace&address=" + dstIP + "&Submit=Submit&.cgifields=query_type").timeout(100000).get();
//			c.followRedirects(true);
//			c.header("Content-Type", "application/x-www-form-urlencoded");

			System.out.println("locVar: " + locVar);
			System.out.println("dstIP: " + dstIP);

			result = doc.getElementsByTag("pre").first().html();
		} catch (IOException ex) {
			Logger.getLogger(LINX.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(result);
		} finally {
			super.store();
			return result;
		}
	}
}
