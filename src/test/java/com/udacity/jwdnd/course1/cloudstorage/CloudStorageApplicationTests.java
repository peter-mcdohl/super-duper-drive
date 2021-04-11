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
		homePage.waitUntil(driver, "//div[@id='logoutDiv']");
		Assertions.assertEquals("Home", driver.getTitle());

		homePage.doLogout();
		marker = loginPage.waitUntil(driver, "//div[contains(@class, 'alert')]");
		Assertions.assertTrue(marker.getText().contains("You have been logged out"));
	}

}
