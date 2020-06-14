package br.com.cleandev.whatsapp;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
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

public class SendWhatsAppTest {

	private String phone;
	private String message;
	private WebDriver driver;
	private String urlWhatsApp;
	private String urlAPIWhatsApp;


	public void init() throws Exception {
		phone = "5516991001800";
		message = "Olá tudo bem ? Enviando meu primeiro contato por whatsApp usando Selenium ;D";
		urlWhatsApp = "https://web.whatsapp.com/";
		urlAPIWhatsApp = "https://api.whatsapp.com/send?";
	}

	@Test
	public void sendMessage() throws Exception {
		
		// inicia as variáveis
		this.init();

		// iniciando o driver de conexão com o navegador (O whatsapp deve está logado com QrCode)
		this.startDriverGoogleChrome(true, false, "127.0.0.1:9222");
		
		// abre a url informando a timeout
		this.open(60);
		
		// Envia a mensagem para o numero informado
		this.sendMessageToNumber();

		// finaliza o driver
		this.exit();
		
	}
	
	public void sendMessageToNumber() {
		
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
			(new WebDriverWait(driver, 120)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@contenteditable='true']")));
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
			(new WebDriverWait(driver, 120)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@contenteditable='true']")));
			WebElement buttonElem = driver.findElement(By.className("_1U1xa"));
			if (buttonElem != null) {
				buttonElem.click();
			}

			this.sleep(5000);
		}
	}
	
	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
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
	
	public void open(int timeout) {
		driver.get(urlWhatsApp);
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.id("side")));
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
	
	public void exit() {
		if(driver != null) {
			driver.close();
		}
	}

}
