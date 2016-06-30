package automation;

import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Savvis extends Launcher implements LauncherInterface {

	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String cssSelector = "blockquote table:nth-child(3)";

	public Savvis() {
		result = "fail";
	}

	public Savvis(String line, String folder) {
		super(line, folder);
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://as3561lg.savvis.net/lg.html";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testSavvis() throws Exception {
		driver.get(baseUrl);
		driver.switchTo().frame(driver.findElement(By.name("left")));
		new Select(driver.findElement(By.id("custlist"))).selectByValue(locVar);
		driver.findElement(By.xpath("//input[@value='trace']")).click();
		driver.findElement(By.name("addr")).clear();
		driver.findElement(By.name("addr")).sendKeys(dstIP);
		driver.findElement(By.id("Submit1")).click();
	}

	@After
	public void tearDown() throws Exception {
		try {
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.name("rbottom")));

//		result = driver.findElement(By.cssSelector(cssSelector)).getAttribute("innerHTML");
			WebElement table = driver.findElement(By.cssSelector(cssSelector));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			result = "";
			for (WebElement row : rows) {
				for (WebElement cell : row.findElements(By.tagName("td"))) {
					result += cell.getText() + "\t";
				}
				result += "\n";
			}
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
			testSavvis();
			tearDown();
		} catch (Exception ex) {
			Logger.getLogger(Savvis.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}
}
