package automation;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class FDC extends Launcher implements LauncherInterface {

	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String cssSelector = "pre";

	public FDC() {
		result = "fail";
	}

	public FDC(String line, String folder) {
		super(line, folder);
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://lg.fdcservers.net";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testFDC() throws Exception {
		driver.get(baseUrl + "/");
		new Select(driver.findElement(By.xpath("//select"))).selectByValue(locVar);
		new Select(driver.findElement(By.id("proto"))).selectByVisibleText("IPv4");
		new Select(driver.findElement(By.id("util"))).selectByVisibleText("Trace");
		driver.findElement(By.name("host")).clear();
		driver.findElement(By.name("host")).sendKeys(dstIP);
		driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
	}

	@After
	public void tearDown() throws Exception {
		try {
			result = driver.findElement(By.cssSelector(cssSelector)).getAttribute("innerHTML");
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

	@Override
	public void config(String line, String folder) {
		configure(line, folder);
	}

	@Override
	public String launch() {
		try {
			setUp();
			testFDC();
			tearDown();
		} catch (Exception ex) {
			Logger.getLogger(FDC.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}
}
