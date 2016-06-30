package automation;

import static com.thoughtworks.selenium.SeleneseTestBase.fail;
import java.io.File;
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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import utils.FileWriter;

public class Sprint extends Launcher implements LauncherInterface {

	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String cssSelector = "pre pre";

	public Sprint() {
		result = "fail";
	}

	public Sprint(String line, String folder) {
		super(line, folder);
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "https://www.sprint.net";
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@Test
	public void testSprint() throws Exception {
		driver.get(baseUrl + "/lg/");
		driver.findElement(By.linkText("Continue")).click();
		driver.findElement(By.id("ipv4")).click();
		Thread.sleep(1000); //Explicit wait, needed for Ajax request delay. Increase wait time if necessary.
		new Select(driver.findElement(By.id("srcrtr"))).selectByValue(locVar);
		driver.findElement(By.id("cmdopt2")).click();
		driver.findElement(By.id("dstopt2")).click();
		driver.findElement(By.id("dst")).clear();
		driver.findElement(By.id("dst")).sendKeys(dstIP);
		driver.findElement(By.id("submitAll")).click();
	}

	@After
	public void tearDown() throws Exception {
		try {
			driver.switchTo().frame(driver.findElement(By.id("results_iframe")));
			result = driver.findElement(By.cssSelector(cssSelector)).getAttribute("innerHTML");
			System.out.println(result);
		} catch (Exception e) {
			Logger.getLogger(Peer1.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			super.store();
			driver.quit();
			String verificationErrorString = verificationErrors.toString();
			if (!"".equals(verificationErrorString)) {
				fail(verificationErrorString);
			}
			System.out.println("\n\n #### Sprint.net!! sleep 3 minutes to avoid block  #### ");
			Thread.sleep(3 * 60 * 1000);
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

	@Override
	public void config(String line, String folder) {
		configure(line, folder);
	}

	@Override
	public String launch() {
		try {
			setUp();
			testSprint();
			tearDown();
		} catch (Exception ex) {
			Logger.getLogger(Sprint.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}
}
