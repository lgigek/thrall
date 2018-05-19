package br.com.lgigek.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.lgigek.scenario.Scenario;

public class Element {

	WebDriver driver;
	private By elementLocator;
	private JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

	private static final Logger logger = LogManager.getLogger(Element.class);

	public Element(By locator) {
		elementLocator = locator;
		Browser browser = Browser.getInstance();
		driver = browser.getDriver();
	}

	public By getElementLocator() {
		return elementLocator;
	}

	public void clearValue() {
		logger.info("Clearing {} value", elementLocator);
		if (verifyIfElementIsPresent()) {
			driver.findElement(elementLocator).clear();
		}
	}

	public void click() {
		logger.info("Clicking on {}", elementLocator);
		if (verifyIfElementIsPresent()) {
			driver.findElement(elementLocator).click();
		}
	}

	public void jsExecutorClick() {
		logger.info("Clicking with jsExecutor on {}", elementLocator);
		if (verifyIfElementIsPresent()) {
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(elementLocator));
		}
	}

	public String getAttribute(String name) {
		logger.info("Getting attribute {} of {}", name, elementLocator);
		if (verifyIfElementIsPresent()) {
			String value = driver.findElement(elementLocator).getAttribute(name);
			if (value == null) {
				logger.warn("Tried to get attribute {} from element {}, but attribute was not found", name,
						elementLocator);
				return null;
			} else
				return value;
		}
		return null;
	}

	public String getText() {
		logger.info("Getting text of {}", elementLocator);
		if (verifyIfElementIsPresent()) {
			return driver.findElement(elementLocator).getText();
		}
		return null;
	}

	public void type(String value) {
		logger.info("Typing on {}", elementLocator);
		if (verifyIfElementIsPresent()) {
			driver.findElement(elementLocator).sendKeys(value);
		}
	}

	public Boolean isPresent() {
		logger.info("Verifying if {} is present", elementLocator);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
			return true;
		} catch (Exception e) {
			logger.warn("Verification failed if element {} is present", elementLocator);
			return false;
		}
	}

	public Boolean isDisplayed() {
		logger.info("Verifying if {} is displayed", elementLocator);
		if (verifyIfElementIsPresent()) {
			if (driver.findElement(elementLocator).isDisplayed())
				return true;
			else {
				logger.warn("Verification failed if element {} is displayed", elementLocator);
				return false;
			}
		}
		return false;
	}

	public Boolean isEnabled() {
		logger.info("Verifying if {} is enabled", elementLocator);
		if (verifyIfElementIsPresent())
			if (driver.findElement(elementLocator).isEnabled())
				return true;
			else {
				logger.warn("Verification failed if element {} is enabled", elementLocator);
				return false;
			}
		return false;
	}

	public Boolean isSelected() {
		logger.info("Verifying if {} is selected", elementLocator);
		if (verifyIfElementIsPresent())
			if (driver.findElement(elementLocator).isSelected())
				return true;
			else {
				logger.warn("Verification failed if element {} is selected", elementLocator);
				return false;
			}
		return false;
	}

	public void submit() {
		logger.info("Submitting {}", elementLocator);
		if (verifyIfElementIsPresent())
			driver.findElement(elementLocator).submit();
	}

	public Boolean waitForElementToBePresent(int timeout) {
		if (timeout == 0)
			timeout = 1;
		try {
			logger.info("Waiting for element {} to be present", elementLocator);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
			return true;
		} catch (Exception e) {
			logger.warn("Timeout reached while waiting for element {} to be present", elementLocator);
			return false;
		}
	}

	public Boolean waitForElementToBeVisible(int timeout) {
		if (timeout == 0)
			timeout = 1;
		try {
			logger.info("Waiting for element {} to be visible", elementLocator);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
			return true;
		} catch (Exception e) {
			logger.warn("Timeout reached while waiting for element {} to be visible", elementLocator);
			return false;
		}
	}

	public Boolean waitForElementToBeNotVisible(int timeout) {
		if (timeout == 0)
			timeout = 1;
		try {
			logger.info("Waiting for element {} to be not visible", elementLocator);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
			return true;
		} catch (Exception e) {
			logger.warn("Timeout reached while waiting for element {} to be not visible", elementLocator);
			return false;
		}
	}

	private Boolean verifyIfElementIsPresent() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
			return true;
		} catch (Exception e) {
			Scenario.fail("Element " + elementLocator + " was not found");
			return false;
		}
		
	}

}
