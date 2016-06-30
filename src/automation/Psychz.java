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

/**
 *
 * @author motamedi
 */
public class Psychz extends Launcher implements LauncherInterface {

    /**
     *
     * @param line
     * @param folder
     */
    @Override
    public void config(String line, String folder) {
        result = "fail";
        baseUrl = "http://lg.lax.psychz.net/ajax.php";
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
            c.data("cmd", "traceroute");
            c.data("host", dstIP);

            c.userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:37.0) Gecko/20100101 Firefox/37.0");
            c.referrer(baseUrl);
            c.timeout(100000);
            
            Document doc = c.get();
            result = cleanHTML(doc.html());
        } catch (IOException ex) {
            Logger.getLogger(Princeton.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(result);
        } finally {
            super.store();
            return result;
        }
    }
}
