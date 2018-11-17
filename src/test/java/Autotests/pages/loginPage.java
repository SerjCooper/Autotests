package Autotests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class loginPage {

    private WebDriver driver;
    private By authWindow = By.xpath("//span[@class='chzn-txt-sel'][contains(text(),'Войти')]");
    private By loginField = By.cssSelector("#LLoginForm_phone");
    private By passwordField = By.cssSelector("#LLoginForm_password");
    private By loginButton = By.xpath("/html[1]/body[1]/div[8]/div[2]/div[1]/div[2]/form[1]/div[1]/input[1]");
    private By profileUser = By.xpath("//div[@class='Header__BlockNameUser']");

    public loginPage(WebDriver driver){
        this.driver = driver;
    }

    public void clickAuthWindow() {
        driver.findElement(authWindow).click();
    }

    public void setLoginField(String login){
        WebElement webElement = driver.findElement(loginField);
        webElement.click();
        webElement.sendKeys(login);
    }

    public void setPasswordField(String password){
        WebElement webElement = driver.findElement(passwordField);
        webElement.click();
        webElement.sendKeys(password);
    }

    //Click on login button
    public void clickLogin(){
        driver.findElement(loginButton).click();
    }

    public void loginTo(String login,String password){
        //Fill user name
        this.setLoginField(login);
        //Fill password
        this.setPasswordField(password);
        //Click Login button
        this.clickLogin();
    }
//Получаем имя авторизованного пользователя
    public String authUser() {
        return driver.findElement(profileUser).getText();
    }
}
