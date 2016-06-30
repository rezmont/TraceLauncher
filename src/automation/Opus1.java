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

public class Opus1 extends Launcher implements LauncherInterface {
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String cssSelector = "pre";

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://www.opus1.com/www/traceroute.html";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testOpus1() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.name("query")).clear();
    driver.findElement(By.name("query")).sendKeys(dstIP);
    driver.findElement(By.name("max")).clear();
    driver.findElement(By.name("max")).sendKeys("30");
    driver.findElement(By.name("min")).clear();
    driver.findElement(By.name("min")).sendKeys("1");
    driver.findElement(By.name("num")).clear();
    driver.findElement(By.name("num")).sendKeys("3");
    driver.findElement(By.xpath("(//input[@name='as'])[2]")).click();
    driver.findElement(By.name("nosym")).click();
    driver.findElement(By.name("owner")).click();
    driver.findElement(By.name("spray")).click();
    new Select(driver.findElement(By.name("lsrr"))).selectByVisibleText("Trace from Gridnet link point of view");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.switchTo().frame(driver.findElement(By.cssSelector("frame")));
    result = driver.findElement(By.cssSelector(cssSelector)).getAttribute("innerHTML");
    super.store();
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
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
            testOpus1();
            tearDown();
        } catch (Exception ex) {
            Logger.getLogger(Opus1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
