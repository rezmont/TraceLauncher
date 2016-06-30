/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author motamedi
 */
public class FluidHosting extends Launcher implements LauncherInterface {

	private String cssSelector = "body > table:nth-child(3) > tbody > tr > td > table";
//	private String cssSelector = "div.section:nth-child(3)";

	/**
	 *
	 * @param line
	 * @param folder
	 */
	@Override
	public void config(String line, String folder) {
		result = "fail";
		baseUrl = "http://www.fluidhosting.com/traceroute.php";
		super.configure(line, folder);
	}

	/**
	 *
	 * @return
	 */
//	query=trace&protocol=IPv4&addr=4.2.2.4&prefix=32&router=Chicago%2C+IL&submit=Submit
//POSTDATA=query=trace&protocol=IPv4&addr=8.8.8.8&prefix=32&router=Amsterdam%2C+Netherlands&submit=Submit
	@Override
	public String launch() {
		try {

			Connection c = Jsoup.connect(baseUrl + "?host=" + dstIP).
					userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:40.0) Gecko/20100101 Firefox/40.0").
					timeout(100000).
					method(Connection.Method.GET);

			System.out.println("locVar: " + locVar);
			System.out.println("dstIP: " + dstIP);

			Document doc = c.post();

			c.header("Content-Type", "application/x-www-form-urlencoded");
			c.followRedirects(true);

//			System.out.println(doc.html());
//			result = 			
			Element table = doc.select(cssSelector).first();
			Elements rows = table.getElementsByTag("tr");
			for (Element row : rows) {
				for (Element cell : row.getElementsByTag("td")) {
					result += cell.text().trim() + "\t";
				}
				result += "\n";
			}
		} catch (IOException ex) {
			Logger.getLogger(FluidHosting.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("==[ exception ]==\n" + result);
		} finally {
			super.store();
			System.err.println("==[ finally ]==\n" + result);
			return result;
		}
	}
}
