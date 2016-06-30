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

public class EvoLink extends Launcher implements LauncherInterface {
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String cssSelector = "div[align='left']";

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://lg.evolink.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testEvoLink() throws Exception {
    driver.get(baseUrl);
    new Select(driver.findElement(By.id("router"))).selectByValue(locVar);
    driver.findElement(By.name("iptype")).click();
    driver.findElement(By.xpath("(//input[@name='options'])[3]")).click();
    driver.findElement(By.name("searchfor")).clear();
    driver.findElement(By.name("searchfor")).sendKeys(dstIP);
    driver.findElement(By.name("Execute")).click();
  }

  @After
  public void tearDown() throws Exception {
    result = driver.findElement(By.cssSelector(cssSelector)).getText();
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
            testEvoLink();
            tearDown();
        } catch (Exception ex) {
            Logger.getLogger(EvoLink.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
