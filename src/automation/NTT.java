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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NTT extends Launcher implements LauncherInterface {

	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String cssSelector = "pre code";

	public NTT() {
		result = "fail";
	}

	public NTT(String line, String folder) {
		super(line, folder);
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://www.us.ntt.net/support/looking-glass/";
//		baseUrl = "https://ssp.pme.gin.ntt.net/lg/lg.cgi";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testNTT() throws Exception {
		driver.get(baseUrl);
		if (driver.findElements(By.id("toc")).size() > 0) {
			driver.findElement(By.id("toc")).click();
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		}
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe")));
		new Select(driver.findElement(By.name("router"))).selectByValue(locVar);
		new Select(driver.findElement(By.id("query"))).selectByVisibleText("Traceroute");
		driver.findElement(By.xpath("//input[@name='sourceIP' and @value='IP']")).click();
		driver.findElement(By.name("addr")).clear();
		driver.findElement(By.name("addr")).sendKeys(dstIP);
		driver.findElement(By.cssSelector("input[type=\"SUBMIT\"]")).click();
	}

	@After
	public void tearDown() {
		try {
//			result = driver.findElement(By.tagName("code")).getAttribute("innerHTML");
			driver.switchTo().frame(driver.findElement(By.cssSelector("div.grid_9:nth-child(3) > iframe:nth-child(1)")));
			result = driver.findElement(By.cssSelector(cssSelector)).getAttribute("innerHTML");
			String verificationErrorString = verificationErrors.toString();
			if (!"".equals(verificationErrorString)) {
				fail(verificationErrorString);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			super.store();
			driver.quit();
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
			testNTT();
			tearDown();
		} catch (Exception ex) {
			Logger.getLogger(NTT.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}
}
