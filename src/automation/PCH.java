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
public class PCH extends Launcher implements LauncherInterface {

	/**
	 *
	 * @param line
	 * @param folder
	 */
	@Override
	public void config(String line, String folder) {
		result = "fail";
		baseUrl = "https://prefix.pch.net/applications/lg/";
		super.configure(line, folder);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String launch() {
		try {
			Document doc = Jsoup.connect(baseUrl + "?query=trace&args=" + dstIP + "&router=" + locVar).timeout(100000).get();

			System.out.println("locVar: " + locVar);
			System.out.println("dstIP: " + dstIP);
			result = doc.getElementsByTag("pre").first().html();
		} catch (IOException ex) {
			Logger.getLogger(PCH.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(result);
		} finally {
			super.store();
			return result;
		}
	}
}
