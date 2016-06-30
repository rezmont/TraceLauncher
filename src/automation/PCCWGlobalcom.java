/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import java.util.logging.Level;
import java.util.logging.Logger;
import static com.thoughtworks.selenium.SeleneseTestBase.fail;
import java.util.Date;
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
import org.openqa.selenium.support.ui.Select;
import utils.FileWriter;

/**
 *
 * @author motamedi
 */
public class PCCWGlobalcom extends Launcher implements LauncherInterface {

	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String cssSelector = "#test_results";

	public PCCWGlobalcom() {
		result = "fail";
	}

	public PCCWGlobalcom(String line, String folder) {
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
			Logger.getLogger(CenturyLink.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://lookingglass.pccwglobal.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void test() throws Exception {
		driver.get(baseUrl + "/");
		new Select(driver.findElement(By.id("network"))).selectByVisibleText("Off Net");
		new Select(driver.findElement(By.id("src"))).selectByValue(locVar);
		new Select(driver.findElement(By.id("service"))).selectByVisibleText("traceroute");
		driver.findElement(By.id("dst")).clear();
		driver.findElement(By.id("dst")).sendKeys(dstIP);
		driver.findElement(By.id("submit")).click();
	}

	@After
	public void tearDown() throws Exception {
		Thread.sleep(60 * 1000);
		try {
			WebElement resultElement = driver.findElement(By.id("test_results"));
			List<WebElement> rows = resultElement.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				result += row.getText().trim() + "\n";

			}
			System.out.println(result);

//			result = resultElement.getAttribute("innerHTML");
//			result = result.replaceAll("[\u0000-\u0008]", "");
//			result = result.replaceAll("[\u000B-\u001f]", "");
//			result = replaceBrTag(result);
//			result = removeTags(result);
//			result = replaceHtmlString(result);
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
