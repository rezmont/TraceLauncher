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
public class Level3 extends Launcher implements LauncherInterface {

	private String cssSelector = "html body pre font";
//	private String cssSelector = "div.section:nth-child(3)";

	/**
	 *
	 * @param line
	 * @param folder
	 */
	@Override
	public void config(String line, String folder) {
		result = "fail";
		baseUrl = "http://lookingglass.level3.net/traceroute/lg_tr_output.php";
		super.configure(line, folder);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String launch() {
		try {
			Connection c = Jsoup.connect(baseUrl);
			c.method(Connection.Method.POST);
			c.data("sitename", locVar);
			c.data("address", dstIP);

			c.userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:37.0) Gecko/20100101 Firefox/37.0");
			c.referrer("http://lookingglass.level3.net/traceroute/lg_tr_output.php");
			c.timeout(100000);
//			c.followRedirects(true);
//			c.header("Content-Type", "application/x-www-form-urlencoded");

			System.out.println("locVar: " + locVar);
			System.out.println("dstIP: " + dstIP);

			Document doc = c.post();
			result = doc.getElementsByTag("pre").first().html();
		} catch (IOException ex) {
			Logger.getLogger(Level3.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(result);
		} finally {
			super.store();
			System.err.println(result);
			return result;
		}
	}
}
