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

public class I3D extends Launcher implements LauncherInterface {

	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String cssSelector = "pre code";

	public I3D() {
		result = "fail";
	}

	public I3D(String line, String folder) {
		super(line, folder);
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://noc.i3d.net/lg/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testI3D() throws Exception {
		driver.get(baseUrl);
		driver.switchTo().frame(driver.findElement(By.id("i3dlookingglass")));
		driver.findElement(By.xpath("(//input[@name='query'])[3]")).click();
		new Select(driver.findElement(By.name("protocol"))).selectByVisibleText("IPv4");
		driver.findElement(By.name("addr")).clear();
		driver.findElement(By.name("addr")).sendKeys(dstIP);
		new Select(driver.findElement(By.name("router"))).selectByValue(locVar);
		driver.findElement(By.cssSelector("input[type=\"SUBMIT\"]")).click();
	}

	@After
	public void tearDown() throws Exception {
		try {
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.id("i3dlookingglass")));
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
			testI3D();
			tearDown();
		} catch (Exception ex) {
			Logger.getLogger(NTT.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}
}
