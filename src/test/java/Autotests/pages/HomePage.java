package Autotests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class HomePage {

    private WebDriver driver;

    private By headerLogo = By.className("header-logo");

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    public void headerLogoClick() {
        Actions actions = new Actions(driver);
        actions.click(driver.findElement(headerLogo)).perform();
    }

    public String getBaseUri() {
        return driver.findElement(headerLogo).getAttribute("baseURI");
    }
}
