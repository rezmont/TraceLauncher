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

public class IPTP extends Launcher implements LauncherInterface {

	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String cssSelector = "pre";

	public IPTP() {
		result = "fail";
	}

	public IPTP(String line, String folder) {
		super(line, folder);
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://www.iptp.net/en/lg.php";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testIPTP() throws Exception {
		driver.get(baseUrl);
		driver.switchTo().frame(driver.findElement(By.id("myFrame")));
		driver.findElement(By.xpath("(//input[@name='query'])[4]")).click();
		driver.findElement(By.name("addr")).clear();
		driver.findElement(By.name("addr")).sendKeys(dstIP);
		new Select(driver.findElement(By.name("router"))).selectByValue(locVar);
		driver.findElement(By.cssSelector("input[type=\"SUBMIT\"]")).click();
	}

	@After
	public void tearDown() throws Exception {
		try {
			driver.switchTo().frame(driver.findElement(By.id("myFrame")));
			result = driver.findElement(By.cssSelector(cssSelector)).getAttribute("innerHTML");
		} catch (Exception e) {
			Logger.getLogger(Peer1.class.getName()).log(Level.SEVERE, null, e);
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
			testIPTP();
			tearDown();
		} catch (Exception ex) {
			Logger.getLogger(IPTP.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}
}
