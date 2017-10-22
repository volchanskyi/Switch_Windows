package core;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SwitchWindows {
    static WebDriver driver;
    By by;
    static String browsers[] = { "Chrome", "HtmlUnit" }; // "HtmlUnit or Chrome"

    public static void main(String[] args) throws InterruptedException {

	String url = "http://alex.academy/exe/links/";

	Logger logger = Logger.getLogger("");
	logger.setLevel(Level.OFF);
	for (String i : browsers) {

	    if (i.equalsIgnoreCase("chrome")) {
		String driverPath = "";
		if (System.getProperty("os.name").toUpperCase().contains("MAC"))
		    driverPath = "./resources/webdrivers/mac/chromedriver";
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
		    driverPath = "./resources/webdrivers/pc/chromedriver.exe";
		else
		    throw new IllegalArgumentException("Unknown OS");

		System.setProperty("webdriver.chrome.driver", driverPath);
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-infobars");
		option.addArguments("--disable-notifications");

		if (System.getProperty("os.name").toUpperCase().contains("MAC"))
		    option.addArguments("-start-fullscreen");
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
		    option.addArguments("--start-maximized");

		else
		    throw new IllegalArgumentException("Unknown OS");
		driver = new ChromeDriver(option);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	    }

	    else {
		driver = new HtmlUnitDriver();
		((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	    }

	    driver.get(url);
	    System.out.println("Browser: " + i);
	    System.out.println("01. Title: " + driver.getTitle() + ";\t URL: " + driver.getCurrentUrl()
		    + ";\t\t Handle: " + driver.getWindowHandle());

	    driver.findElement(By.id("id_link_top")).click();

	    System.out.println("02. Title: " + driver.getTitle() + ";\t\t\t URL: " + driver.getCurrentUrl()
		    + ";\t\t Handle: " + driver.getWindowHandle());

	    driver.navigate().back();
	    driver.findElement(By.id("id_link_blank")).click();

	    if (i == "HtmlUnit") {
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);
	    } else
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);

	    System.out.println("03. Title: " + driver.getTitle() + ";\t\t\t URL: " + driver.getCurrentUrl()
		    + ";\t\t Handle: " + driver.getWindowHandle());

	    if (i == "HtmlUnit")
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);

	    else
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);
	    System.out.println(
		    "------------------------------------------------------------------------------------------------------------------");
	    System.out.println("All Handles: " + driver.getWindowHandles());
	    System.out.println(
		    "------------------------------------------------------------------------------------------------------------------");
	    // System.out.println("All Handles: " + driver.getWindowHandles());
	    driver.quit();
	}

    }
}