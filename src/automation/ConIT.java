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
public class ConIT extends Launcher implements LauncherInterface {

	/**
	 *
	 * @param line
	 * @param folder
	 */
	@Override
	public void config(String line, String folder) {
		result = "fail";
		baseUrl = "http://lg.conit.com.au/LookingGlass.pl";
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
			c.data("rt", locVar);
                        c.data("qt", "trace");
			c.data("arg", dstIP);

			c.userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:37.0) Gecko/20100101 Firefox/37.0");
			c.referrer(baseUrl);
			c.timeout(100000);
//			c.followRedirects(true);
//			c.header("Content-Type", "application/x-www-form-urlencoded");

			System.out.println("locVar: " + locVar);
			System.out.println("dstIP: " + dstIP);

			Document doc = c.post();
                        result = doc.getElementsByTag("body").first().html();
                        
                        //Data received isn't very readable, so manually include line separation
                        for(int i = 0; i < result.length() - 2; i++) {
                            if(result.charAt(i) == ' ' && Character.isDigit(result.charAt(i + 1)) && result.charAt(i + 2) == ' ') {
                                result = result.substring(0, i + 1) + "\n" + result.substring(i + 1, result.length());
                            }
                        }
		} catch (IOException ex) {
			Logger.getLogger(ConIT.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(result);
		} finally {
			super.store();
			return result;
		}
	}
}
