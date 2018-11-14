package Autotests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class FirstTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize(); //передаю веб-драйверу набор методов, для того чтобы ход теста отображался в полностью открытом окне:
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // неявное ожидание Implicit Wait, которое задается вначале теста и будет работать при каждом вызове метода поиска элемента:
        driver.get("https://shop.by/");
    }
    @Test
    public void userLogin() {
        WebElement authWindow = driver.findElement(By.xpath("//span[@class='chzn-txt-sel'][contains(text(),'Войти')]"));
        authWindow.click();
        WebElement loginField = driver.findElement(By.cssSelector("#LLoginForm_phone"));
        loginField.sendKeys("447659060");
        WebElement passwordField = driver.findElement(By.cssSelector("#LLoginForm_password"));
        passwordField.sendKeys("testa1qa");
        WebElement loginButton = driver.findElement(By.xpath("/html[1]/body[1]/div[8]/div[2]/div[1]/div[2]/form[1]/div[1]/input[1]")); // Потому что заметил очепятку в слове submit (ы коде сайта написано sumbit)
        loginButton.click();
        WebElement profileUser = driver.findElement(By.xpath("//div[@class='Header__BlockNameUser']"));
        String UserName = profileUser.getText();
        Assert.assertEquals("userShop.by_20", UserName);


    }
    @AfterClass
    public static void tearDown() {
        WebElement menuUser = driver.findElement(By.xpath("//a[@class='Header__BlockLinkOffice Header__LinkPersonalCabinet Page__SelectOnBg Header__LinkShowWapper']//div[@class='chzn-btn glyphicon']"));
        menuUser.click();
        WebElement logoutButton = driver.findElement(By.cssSelector(" #yt0"));
        logoutButton.click();
        driver.quit();


    }
}