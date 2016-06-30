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

public class Space extends Launcher implements LauncherInterface {
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String cssSelector = "div.traceroute";

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://www.space.net/traceroute/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSpace() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.id("host")).clear();
    driver.findElement(By.id("host")).sendKeys(dstIP);
    driver.findElement(By.name("tx_snnettools_traceroute[submit_button]")).click();
  }

  @After
  public void tearDown() throws Exception {
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
            testSpace();
            tearDown();
        } catch (Exception ex) {
            Logger.getLogger(Space.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
