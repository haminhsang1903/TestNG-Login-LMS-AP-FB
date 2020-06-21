package TestLoginLMS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class TestLoginAP {
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
		// Get the object map file #identifier MbhUzd
		uimap = new UIMap(workingDir + "/src/Resources/locator.properties");
		System.setProperty("webdriver.chrome.driver", "D:\\FPoly\\KTNC\\Document\\chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.get("https://ap.poly.edu.vn/login");
		String previousURL = driver.getCurrentUrl();
		String currentURL = "";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//			driver.manage().window().maximize();

		Select dropdown = new Select(driver.findElement(By.id("campus_id")));
		dropdown.selectByValue("pc");

		WebElement clickAPIGG = driver.findElement(uimap.getLocator("AP_Google"));
		clickAPIGG.click();

//			
		WebElement username = driver.findElement(uimap.getLocator("Username_field"));
		username.sendKeys(datafile.getData("username"));
//			
//			
		WebElement clickAPINext = driver.findElement(uimap.getLocator("API_btnNext"));
		clickAPINext.click();

//			Thread.sleep(2000);
		// #password > div.aCsJod.oJeWuf > div > div.Xb9hP > input
		WebDriverWait wait = new WebDriverWait(driver, 100);

		WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
//			

		WebElement password1 = driver.findElement(uimap.getLocator("Password_field"));
		password1.sendKeys(datafile.getData("password"));

		// driver.findElement(By.cssSelector("#password")).sendKeys(datafile.getData("password"));

		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			WebElement clickAPINextP = driver.findElement(uimap.getLocator("API_btnNextPass"));

		driver.findElement(By.cssSelector("#passwordNext")).click();
		// System.out.println(driver.getPageSource().contains("taithpc00171@fpt.edu.vn"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return (d.getCurrentUrl() != previousURL);
			}
		};

		wait.until(e);
		currentURL = driver.getCurrentUrl();
		System.out.println(currentURL);
//			 driver.close();
//			 driver.quit();
	}
}
