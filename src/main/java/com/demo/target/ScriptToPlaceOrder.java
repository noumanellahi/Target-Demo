package com.demo.target;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ScriptToPlaceOrder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String startingUrl = "https://www.target.com/";
		WebDriver webDriver = initializeChrome();
		if (webDriver != null) {
			// load web page
			try {
				WebDriverWait wait = new WebDriverWait(webDriver, 20);
				webDriver.get(startingUrl);
				searchProductAndApplyFilter(webDriver, wait);
				orderProduct(webDriver, wait);
				logInToWebsite(webDriver, wait);
			} catch (Exception ex) {
				System.out.println("FAILED TO LOAD WEB PAGE");
				ex.printStackTrace();
			}
		} else {
			System.out.println("NOT ABLE TO INTIALIZING CHROME BROWSER");
		}
	}

	private static WebDriver initializeChrome() {
		try {
			// chrome driver setup
			WebDriverManager.chromedriver().setup();

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("safebrowsing.enabled", false);

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox"); // Bypass OS security model
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--start-maximized");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--disable-dev-shm-usage");
//			options.addArguments("user-data-dir=C:/Users/Noman.Alahi/AppData/Local/Google/Chrome/User Data");
			options.addArguments("--incognito");
//			options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
			options.setExperimentalOption("prefs", chromePrefs);

			// Uncomment below line if you want to run the browser in background
//			 options.addArguments("--headless");

			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

			options.merge(capabilities);
			return new ChromeDriver(options);
		} catch (Exception ex) {
			System.out.println("ERROR OCCURED WHILE INTIALIZING CHROME BROWSER");
			ex.printStackTrace();
		}
		return null;
	}

	private static void logInToWebsite(WebDriver webDriver, WebDriverWait wait) throws InterruptedException {
		String email = "nomanellahi1@gmail.com";
		String password = "cy7CGCFwZNRKq7L";

		Actions actions = new Actions(webDriver);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;

		// Drop Down sign in menu button
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#account"))).click();
		
		Thread.sleep(2000);
//		// Sign in button from menu
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
				"#accountNav-signIn > a"))).click();

		// Populate email field of login form
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#username"))).sendKeys(email);

		Thread.sleep(10000);
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#username"))).click();
//		jsExecutor.executeScript("document.getElementById('username').value='nomanellahi1@gmail.com'");
//		jsExecutor.executeScript("document.getElementById('username').setAttribute('value', 'nomanellahi1@gmail.com')");
//		actions.release();
		
//		Thread.sleep(10000);
		// Populate password field of login form
//		wait.until(ExpectedConditions.presenceOfElementLocatASDed(By.cssSelector("#password"))).sendKeys(password);
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#password")));
//		jsExecutor.executeScript("document.getElementById('password').value='cy7CGCFwZNRKq7L'");
		
		// Press sign in button
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#login"))).click();

	}

	private static void searchProductAndApplyFilter(WebDriver webDriver, WebDriverWait wait) {
		String searchTerm = "2021 sports trading cards boxes";

		// Populate search field
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search"))).sendKeys(searchTerm);

		// Search for product
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
				"#headerMain > div.SearchInput-eeyb4b-0.ieICLs > form > button.SearchInputButton-sc-1opoijs-0.eOzuAz")))
				.click();

		// Apply filter of shipping
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
				"div.styles__StyledCol-ct8kx6-0.dfsBuM > div > form > div:nth-child(3) > div > div > label > div")))
				.click();
	}

	private static void orderProduct(WebDriver webDriver, WebDriverWait wait) throws InterruptedException {
		Thread.sleep(5000);
		// Select first product
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
				"li:nth-child(1) > div > div.styles__StyledProductCardBody-mkgs8k-3.cublee > div > div > div > div.styles__ProductCardItemInfoDiv-h3r0um-0.dQRmPf > div.h-display-flex > a")))
				.click();

		// Ship it
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("div.Col-favj32-0.EbfkY.h-padding-l-tiny > button")))
				.click();

		// View cart and check out
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("div.Row-uds8za-0.kZFAeJ.h-margin-v-default > div:nth-child(3) > button"))).click();

		Thread.sleep(5000);
		// Check out
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("#orderSummaryWrapperDiv > div > div > div > button")))
				.click();

	}

}
