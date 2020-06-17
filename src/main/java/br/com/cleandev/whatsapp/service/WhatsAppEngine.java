package br.com.cleandev.whatsapp.service;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WhatsAppEngine {

	private WebDriver driver;
	private final String urlWhatsApp = "https://web.whatsapp.com/";
	private final String urlAPIWhatsApp  = "https://api.whatsapp.com/send?";

	public WhatsAppEngine() {

		// iniciando o driver de conexão com o navegador O whatsapp deve está logado
		this.startDriverGoogleChrome(true, false, "127.0.0.1:9222");

		// abre a url informando a timeout
		this.open(60);
	}

	public void open(int timeout) {
		driver.get(urlWhatsApp);
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.id("side")));
	}

	public void close() {
		this.driver.close();
	}

	public void sendToNewNumber(String phone, String message) {
		if (phone != null && !phone.startsWith("55")) {
			phone = "55" + phone;
		}
		System.out.println("Enviando ZAP para " + phone);

		driver.get(urlAPIWhatsApp + "phone=" + phone + "&text=" + message);
		if (this.isAlertPresent()) {
			this.alertAccept();
		}

		this.executeScript("document.getElementById('action-button').click();");

		try {
			this.sleep(1000);
			WebElement link = driver.findElement(By.linkText("use o WhatsApp Web"));
			link.click();
			this.sleep(5000);
		} catch (NoSuchElementException e1) {
			WebElement link = driver.findElement(By.linkText("use WhatsApp Web"));
			link.click();
			e1.printStackTrace();
		}

		// verifica se o numero é valido
		boolean numeroValido = true;
		try {
			(new WebDriverWait(driver, 120))
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@contenteditable='true']"))
			);
		} catch (Exception e) {
			driver.findElement(By.xpath(
					"(.//*[normalize-space(text()) and normalize-space(.)='O número de telefone compartilhado através de url é inválido.'])[1]/following::div[2]"))
					.click();
			numeroValido = false;
		}

		try {
			WebElement numeroInvalido = driver.findElement(By.className("_3lLzD"));
			if (numeroInvalido != null && numeroInvalido.getText() != null && numeroInvalido.getText()
					.contains("O número de telefone compartilhado através de url é inválido")) {
				numeroValido = false;
				this.executeScript("document.getElementsByClassName('_1WZqU PNlAR')[0].click();");
			}
		} catch (NoSuchElementException e) {
			System.out.println("elemento _2Vo52 não encontrado");
		}

		System.out.println("numero valido ? " + numeroValido);
		if (numeroValido) {
			(new WebDriverWait(driver, 120))
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@contenteditable='true']")));
			WebElement buttonElem = driver.findElement(By.className("_1U1xa"));
			if (buttonElem != null) {
				buttonElem.click();
			}

			this.sleep(5000);
		}
	}

	public void startDriverGoogleChrome(boolean useOptions, boolean browserOcult, String existent) {
		String extension = "";
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			extension = ".exe";
		}

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + File.separator + "driver"
				+ File.separator + "chromedriver" + extension);

		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);

		if (useOptions) {

			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-gpu");
			options.addArguments("chromedriver --whitelisted-ips='*'");
			options.addArguments("disable-infobars");
			if (browserOcult) {
				options.addArguments("--headless");
			}
			if (!(existent == null)) {
				options.setExperimentalOption("debuggerAddress", existent);
			}

			driver = new ChromeDriver(options);

			if (!(existent == null)) {
				String handle = driver.getWindowHandle();
				driver.switchTo().window(handle);
			}

		}
		driver.manage().window().maximize();

	}

	public void executeScript(String script) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
			jsDriver.executeScript(script);
			driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		} else {
			throw new IllegalStateException("Esse driver não suporta javascript");
		}
	}

	public void alertAccept() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
