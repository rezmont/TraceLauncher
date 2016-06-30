/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import static com.thoughtworks.selenium.SeleneseTestBase.fail;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.FileWriter;

/**
 *
 * @author motamedi
 */
public class HE extends Launcher implements LauncherInterface {

	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
//	private String cssSelector = "#lg_return > pre:nth-child(1)";
	private String cssSelector = "table.tablesorter > tbody";
//	table.tablesorter:nth-child(2) > tbody:nth-child(2)
	
	public HE() {
		result = "fail";
	}

	public HE(String line, String folder) {
		super(line, folder);
	}

	/**
	 *
	 * @param line
	 * @param folder
	 */
	@Override
	public void config(String line, String folder) {
		configure(line, folder);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String launch() {
		try {
			setUp();
			test();
			tearDown();
		} catch (Exception ex) {
			Logger.getLogger(HE.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://lg.he.net/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void test() throws Exception {
		driver.get(baseUrl + "/");

		if (driver.findElement(By.id("routers_core1.fmt1.he.net")).isSelected()) {
			driver.findElement(By.id("routers_core1.fmt1.he.net")).click();  /* Seems to be the selected by default */

		}
		if (driver.findElement(By.id("routers_core1.fmt2.he.net")).isSelected()) {
			driver.findElement(By.id("routers_core1.fmt2.he.net")).click();  /* Seems to be the selected by default */

		}

		driver.findElement(By.id(locVar)).click();
		driver.findElement(By.id("command_traceroute")).click();
		driver.findElement(By.id("ip")).clear();
		driver.findElement(By.id("ip")).sendKeys(dstIP);

		if (driver.findElement(By.id("raw")).isSelected()) {
			driver.findElement(By.id("raw")).click();
		}

		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	}

	@After
	public void tearDown() throws Exception {
		try {
//			result = driver.findElement(By.cssSelector(cssSelector)).getAttribute("innerHTML");
			result = "";
			WebElement table = driver.findElement(By.cssSelector(cssSelector));
//			System.out.println("1" + table.getAttribute("innerHTML"));
//			System.out.println("table:" + table.html());
			List<WebElement> rows = table.findElements(By.tagName("tr"));
//			System.out.println("2");
			for (WebElement row : rows) {
				for (WebElement cell : row.findElements(By.tagName("td"))) {
					result += cell.getText().trim() + "\t";
				}
				result += "\n";
//				System.out.println(result);
//				System.out.println("------");
			}
			System.out.println(result);
			
		} catch (Exception e) {
			Logger.getLogger(BT.class.getName()).log(Level.SEVERE, null, e);
		} finally {

			super.store();

			driver.quit();
			String verificationErrorString = verificationErrors.toString();
			if (!"".equals(verificationErrorString)) {
				fail(verificationErrorString);
			}
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
