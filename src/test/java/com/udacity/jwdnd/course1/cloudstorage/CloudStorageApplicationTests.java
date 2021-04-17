package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		//WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		//this.driver = new ChromeDriver();
		this.driver = new FirefoxDriver();
	}

	@AfterEach
	public void afterEach() throws InterruptedException {
		if (this.driver != null) {
			Thread.sleep(500);
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void accessUnauthorisedHome() {
		driver.get("http://localhost:" + this.port + "/");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signupFlow() {
		String firstname = "Jon";
		String lastname = "Snow";
		String username = "jon.snow";
		String password = "superSecret";

		LoginPage loginPage = new LoginPage(driver);
		SignupPage signupPage = new SignupPage(driver);
		HomePage homePage = new HomePage(driver);
		WebElement marker;

		driver.get("http://localhost:" + this.port + "/login");

		loginPage.goToSignupPage();
		Assertions.assertEquals("Sign Up", driver.getTitle());

		signupPage.doSignup(firstname, lastname, username, password);
		marker = signupPage.waitUntil(driver, "//div[contains(@class, 'alert')]");
		Assertions.assertTrue(marker.getText().contains("You successfully signed up!"));

		signupPage.goToLoginPage();
		loginPage.doLogin(username, password);
		homePage.waitUntil(driver, homePage.xpathLogoutButton);
		Assertions.assertEquals("Home", driver.getTitle());

		homePage.doLogout();
		marker = loginPage.waitUntil(driver, "//div[contains(@class, 'alert')]");
		Assertions.assertTrue(marker.getText().contains("You have been logged out"));
	}

	@Test
	public void userNotesFlow() {
		String firstname = "Jon";
		String lastname = "Snow";
		String username = "jon.snow";
		String password = "superSecret";

		String titleNew = "The new title";
		String descNew = "This is the first note";
		String titleEdit = "The edited title";
		String descEdit = "This is the first note and edited";

		LoginPage loginPage = new LoginPage(driver);
		SignupPage signupPage = new SignupPage(driver);
		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		WebElement marker;

		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.doSignup(firstname, lastname, username, password);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage.doLogin(username, password);

		homePage.waitUntil(driver, homePage.xpathLogoutButton);
		Assertions.assertEquals("Home", driver.getTitle());

		homePage.openTabNotes();
		Assertions.assertTrue(homePage.isTabUserNoteDisplayed(driver));
		Assertions.assertEquals(0, homePage.getUserTableItemCount());

		homePage.openFormUserNote();
		Assertions.assertTrue(homePage.isModalUserNoteDisplayed(driver));

		homePage.submitFormUserNote(titleNew, descNew);
		marker = resultPage.waitUntil(driver, resultPage.xpathAnchorContinue);
		Assertions.assertEquals("Result", driver.getTitle());
		marker.click(); // click continue

		homePage.waitUntil(driver, homePage.xpathLogoutButton);
		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertTrue(homePage.isTabUserNoteDisplayed(driver));
		Assertions.assertTrue(homePage.isNoteExistsInTable(titleNew, descNew));

		homePage.clickEditUserNoteByTitle(titleNew);
		Assertions.assertTrue(homePage.isModalUserNoteDisplayed(driver));
		Assertions.assertEquals(titleNew, homePage.inputNoteTitle.getAttribute("value"));
		Assertions.assertEquals(descNew, homePage.inputNoteDescription.getAttribute("value"));

		homePage.submitFormUserNote(titleEdit, descEdit);
		marker = resultPage.waitUntil(driver, resultPage.xpathAnchorContinue);
		Assertions.assertEquals("Result", driver.getTitle());
		marker.click(); // click continue

		homePage.waitUntil(driver, homePage.xpathLogoutButton);
		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertTrue(homePage.isTabUserNoteDisplayed(driver));
		Assertions.assertTrue(homePage.isNoteExistsInTable(titleEdit, descEdit));

		homePage.clickDeleteUserNoteByTitle(titleEdit);
		marker = resultPage.waitUntil(driver, resultPage.xpathAnchorContinue);
		Assertions.assertEquals("Result", driver.getTitle());
		marker.click(); // click continue

		homePage.waitUntil(driver, homePage.xpathLogoutButton);
		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertTrue(homePage.isTabUserNoteDisplayed(driver));
		Assertions.assertFalse(homePage.isNoteExistsInTable(titleEdit, descEdit));
	}

}
