package TestLoginLMS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import TestLoginLMS.UIMap;

public class DemoLoginAP {
	public WebDriver driver;
	public UIMap uimap;
	public UIMap datafile;
	public String workingDir;
	// Declare an excel to work book
	HSSFWorkbook workbook;

	HSSFSheet sheet;
	Map<String, Object[]> TestNGResult;
	public static String driverpath = "";

	@Test(description = "Opens the TestNG Demo web", priority = 1)
	public void lauchWeb() throws Exception {
		try {

			// http://dn-lms.poly.edu.vn/login.php?target=&client_id=lmsfpoly&auth_stat=
			// "https://ap.poly.edu.vn/login"
			driver.get("https://ap.poly.edu.vn/login");
//			driver.manage().window().maximize();
			TestNGResult.put("2", new Object[] { 1d, "Nagative to demo website", "Site get opened", "Pass" });

		} catch (Exception e) {
			TestNGResult.put("2", new Object[] { 1d, "Nagative to demo website", "Site get opened", "Fail" });
			Assert.assertTrue(false);
		}
	}

	@Test(description = "Fill the login Detail", priority = 2)
	public void fillLoginDetail() throws Exception {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Select dropdown = new Select(driver.findElement(By.id("campus_id")));
			dropdown.selectByValue("pc");
			WebElement clickAPIGG = driver.findElement(uimap.getLocator("AP_Google"));
			clickAPIGG.click();
			WebElement username = driver.findElement(uimap.getLocator("Username_field"));
			username.sendKeys(datafile.getData("username"));
			WebElement clickAPINext = driver.findElement(uimap.getLocator("API_btnNext"));
			clickAPINext.click();
			WebDriverWait wait = new WebDriverWait(driver, 100);
			WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
			WebElement password1 = driver.findElement(uimap.getLocator("Password_field"));
			password1.sendKeys(datafile.getData("password"));
			TestNGResult.put("3", new Object[] { 2d, "Fill login from Data (Username/Password)",
					"Login detail gets filled", "Pass" });
		} catch (Exception e) {
			TestNGResult.put("3", new Object[] { 2d, "Fill login from Data (Username/Password)",
					"Login detail gets filled", "Fail" });
			Assert.assertTrue(false);
		}
	}

	@Test(description = "Perform Login", priority = 3)
	public void DoLogin() throws Exception {
		try {
			driver.findElement(By.cssSelector("#passwordNext")).click();
			Thread.sleep(1000);

			// Assert the user login by checking on the Online user
			// WebElement onlineuser = driver.findElement(uimap.getLocator("online_user"));
			TestNGResult.put("4",
					new Object[] { 3d, "Click login and verify welcome message", "Login success", "Pass" });

		} catch (Exception e) {
			TestNGResult.put("4",
					new Object[] { 3d, "Click login and verify welcome message", "Login success", "Fail" });
			Assert.assertTrue(false);
		}
	}

	@BeforeClass(alwaysRun = true)
	public void suiteSetup() {
		// create a new work book
		workbook = new HSSFWorkbook();
		// create a new work sheet
		sheet = workbook.createSheet("TestNG Result Sumary");
		TestNGResult = new LinkedHashMap<String, Object[]>();
		// add test result excel file
		// write the header in the first row
		TestNGResult.put("1", new Object[] { "Test Step No.", "Action", "Expected output", "Actual ouput" });
		try {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			// Get current working directory and load the data file
			workingDir = System.getProperty("user.dir");
			datafile = new UIMap(workingDir + "/src/Resources/datafile.properties");
			// Get the object map file
			uimap = new UIMap(workingDir + "/src/Resources/locator.properties");
			System.setProperty("webdriver.chrome.driver", "D:\\FPoly\\KTNC\\Document\\chromedriver.exe");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			throw new IllegalStateException("Can't Start ", e);
		}
	}

	@AfterClass
	public void suiteTeardow() throws EncryptedDocumentException, IOException {
		// write excel file and file name is SaveTestNGResultToExcel.xls
//		FileInputStream fis = new FileInputStream("SaveTestNGResultToExcel.xls");
//		workbook = (HSSFWorkbook) WorkbookFactory.create(fis);
//		sheet = workbook.getSheet("Sheeti");
		Set<String> keyset = TestNGResult.keySet();
		int rownum = 0;

		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = TestNGResult.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {

				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date)
					cell.setCellValue((Date) obj);
				else if (obj instanceof Boolean)
					cell.setCellValue((Boolean) obj);
				else if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(new File("SaveTestNGResultToExcel.xls"));
			workbook.write(out);
			out.flush();
			out.close();
			System.out.println("Successfully save Selenium Webriver TestNG Result to Excel File");
		} catch (FileNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		driver.close();
		driver.quit();
	}
}
