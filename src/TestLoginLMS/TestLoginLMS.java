package TestLoginLMS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import TestLoginLMS.UIMap;

public class TestLoginLMS {
	WebDriver driver;
	public String workingDir;
	public UIMap datafile;
	public UIMap uimap;

	@Test
	public void f() throws Exception {
		// auto maximized
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		
		// Set up
		
		
		
		workingDir = System.getProperty("user.dir");
		datafile = new UIMap(workingDir + "/src/Resources/datafile.properties");
		// Get the object map file #identifier  MbhUzd
		uimap = new UIMap(workingDir + "/src/Resources/locator.properties");
		System.setProperty("webdriver.chrome.driver", "D:\\FPoly\\KTNC\\Document\\chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.get("http://dn-lms.poly.edu.vn/login.php?target=&client_id=lmsfpoly&auth_stat=");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
//		driver.manage().window().maximize();
		
		WebElement clickAPIGG = driver.findElement(uimap.getLocator("API_btn"));
		clickAPIGG.click();
		
		
		WebElement username = driver.findElement(uimap.getLocator("Username_field"));
		username.sendKeys(datafile.getData("username"));
		
		
		WebElement clickAPINext = driver.findElement(uimap.getLocator("API_btnNext"));
		clickAPINext.click();
//		Thread.sleep(2000);
		// #password > div.aCsJod.oJeWuf > div > div.Xb9hP > input
	//	WebDriverWait wait = new WebDriverWait(driver, 10);
		
//		WebElement password = wait.until(
//			    ExpectedConditions.visibilityOfElementLocated(By.className("password")));
//		
		
		WebElement password1 = driver.findElement(uimap.getLocator("Password_field"));
		password1.sendKeys(datafile.getData("password"));
		
	//	driver.findElement(By.cssSelector("#password")).sendKeys(datafile.getData("password"));
//		WebElement clickAPINextP = driver.findElement(uimap.getLocator("API_btnNextPass"));
//		clickAPINextP.click();
		driver.findElement(By.cssSelector("#passwordNext")).click();
		// driver.close();
	}
}
