package Autotests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private WebDriver driver;
    private By authWindow = By.xpath("//span[@class='chzn-txt-sel'][contains(text(),'Войти')]");
    private By loginField = By.cssSelector("#LLoginForm_phone");
    private By passwordField = By.cssSelector("#LLoginForm_password");
    private By loginButton = By.xpath("/html[1]/body[1]/div[8]/div[2]/div[1]/div[2]/form[1]/div[1]/input[1]");
    private By profileUser = By.xpath("//div[@class='Header__BlockNameUser']");
    private By menuUser = By.xpath("//a[@class='Header__BlockLinkOffice Header__LinkPersonalCabinet Page__SelectOnBg Header__LinkShowWapper']//div[@class='chzn-btn glyphicon']");
    private By logoutButton = By.cssSelector(" #yt0");

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    private void clickAuthWindow() {
        driver.findElement(authWindow).click();
    }

    public WebElement getAuthWindow() {
        return driver.findElement(authWindow);
    }

    private void setLoginField(String login){
        WebElement webElement = driver.findElement(loginField);
        webElement.click();
        webElement.sendKeys(login);
    }

    private void setPasswordField(String password){
        WebElement webElement = driver.findElement(passwordField);
        webElement.click();
        webElement.sendKeys(password);
    }

    private void clickLogin(){
        driver.findElement(loginButton).click();
    }

    public void loginTo(String login,String password){
        this.clickAuthWindow();
        this.setLoginField(login);
        this.setPasswordField(password);
        this.clickLogin();
    }
    //Получаем имя авторизованного пользователя
    public String authUser() {
        return driver.findElement(profileUser).getText();
    }

    private void menuUserClick() {
        driver.findElement(menuUser).click();
    }

    private void logoutButtonClick() {
        driver.findElement(logoutButton).click();
    }

    public void logout() {
        this.menuUserClick();
        this.logoutButtonClick();
    }
}
