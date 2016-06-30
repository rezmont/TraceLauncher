/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.FileWriter;

/**
 *
 * @author motamedi
 */
public abstract class Launcher {

    String dstIP;
    String dstASN;
    String baseUrl;

    String line;
    String webID;
    String srcASN;
    String locVar;
    String locInfo;

    String folder;

    WebDriver driver;
    boolean acceptNextAlert = true;

    String result;

    public void configure(String line, String folder) {
        this.result = "Fail";
        this.folder = folder;
        this.line = line;

        String[] tokens = line.split("\t");

        webID = tokens[0];
        srcASN = tokens[1];
        locVar = tokens[2];
        locInfo = tokens[3];

        dstIP = tokens[5];
        dstASN = tokens[6];
    }

    public Launcher() {
    }

    public Launcher(String line, String folder) {
        this.configure(line, folder);
    }

//	public String launch() {
//		return result;
//	}
    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    public static String cleanHTML(String html) {
        String result = br2nl(html);
        result = StringEscapeUtils.unescapeHtml4(result);
        result = result.replaceAll("(?s)<!--.*?-->", "");
        return result;
    }

    public static String br2nl(String html) {
        if (html == null) {
            return html;
        }
        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("p").prepend("\\n\\n");
        document.select("div").prepend("\\n");
        //	System.out.println(document.html());
        document.select("br").append("\\n");
        //	System.out.println(document.html());

        String s = document.html().replaceAll("\\\\n", "\n");
        //	System.out.println(s);
        return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
    }

    void store() {
        this.result = cleanHTML(this.result);

        String finalRes = "\n"
                + "********************* Trace to " + this.dstIP + " (" + this.dstASN + ") from " + this.baseUrl + " (" + this.srcASN + ") collected at " + (new Date().getTime()) + "\n"
                + "********************* >>>> " + this.line + " <<<<\n"
                + "---------------------------------------------------------------------------\n"
                + result
                + "\n---------------------------------------------------------------------------";
        String fileName = webID + "-" + srcASN + "-" + locInfo;
        FileWriter fw = new FileWriter(new File(folder, fileName + ".txt").toString());
        fw.write(finalRes);
    }

    public void waitForText(String locator, String elementText) {
        boolean feedBack = driver.findElement(By.cssSelector(locator)).getText().contains(elementText);
        long beginWaitingTime = new Date().getTime();
        long timeToWait = 30;
        /* 3 seconds to wait, there should be a function to wait in selenium.. */

        while (!feedBack) {
            feedBack = driver.findElement(By.cssSelector(locator)).getText().contains(elementText);
            System.out.println(elementText + " " + feedBack);
            if (new Date().getTime() - beginWaitingTime > timeToWait) {
                break;
            }
        }
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CenturyLink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *********************replace the <br> or <br/> tag with
     * newline**********************************
     */
    public static String replaceBrTag(String rawHtml) {
        return rawHtml.replaceAll("<br>", "\n").replaceAll("<br( |/).*?>", "\n");
    }

    /**
     * *********************replace the &lt or &nbsp within
     * html**********************************
     */
    public static String replaceHtmlString(String rawHtml) {
        return rawHtml.replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;", ">");
    }

    /**
     * ******this may need revise or improve*******
     */
    public static String removeTags(String original) {
        return original.replaceAll("<(\\w|/|!){1}.*?>", "");
    }

}
