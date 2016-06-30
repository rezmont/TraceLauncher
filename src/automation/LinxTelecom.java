/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author motamedi
 */
public class LinxTelecom extends Launcher implements LauncherInterface {

	@Override
	public void config(String line, String folder) {
		result = "fail";
		baseUrl = "http://lg.linxtelecom.com/?query=trace&protocol=IPv4";
		super.configure(line, folder);
	}

	@Override
	public String launch() {
		try {
			String url = baseUrl + "&addr=" + dstIP + "&router=" + locVar;
			Document doc = Jsoup.connect(url).timeout(100000).get();
			System.out.println("locVar: " + locVar);
			System.out.println("dstIP: " + dstIP);

			result = doc.getElementsByTag("pre").first().html();
		} catch (IOException ex) {
			Logger.getLogger(AOps.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(result);
		} finally {
			super.store();
			return result;
		}
	}

}
