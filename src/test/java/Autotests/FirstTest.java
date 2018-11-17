package Autotests;

import Autotests.pages.loginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.*;

public class FirstTest {

    private static WebDriver driver;

    private String login = "447659060";
    private String password = "testa1qa";

    @BeforeClass
    public static void setup() {
        initConfig configs = new initConfig();
        System.setProperty("webdriver." + configs.browserName +".driver", configs.driverPath);
        if ("Chrome".equalsIgnoreCase(configs.browserName)) {             //заготовка под выбор браузеров
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize(); //передаю веб-драйверу набор методов, для того чтобы ход теста отображался в полностью открытом окне:
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // неявное ожидание Implicit Wait, которое задается вначале теста и будет работать при каждом вызове метода поиска элемента:
        driver.get(configs.targetUrl);
    }
    @Test(priority = 1)
    public void userLogin() {
        loginPage loginPage = new loginPage(driver);
        loginPage.clickAuthWindow();
        loginPage.setLoginField(login);
        loginPage.setPasswordField(password);
        loginPage.clickLogin();
        Assert.assertEquals("userShop.by_20", loginPage.authUser());
    }

    @Test(priority = 2)
    public void openSummaryCatalog() {
        WebElement summaryCatalogButton = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[1]/a"));
        summaryCatalogButton.click();
        Assert.assertEquals("Весь каталог", driver.findElement(By.className("Page__TitleActivePage")).getText());
    }

    @Test(priority = 3)
    public void openSameCatalog() {
       /* List<WebElement> catalogButtons = driver.findElements(By.className("Header__CatalogAllLink")); // есть 2 кнопки каталога, одна из которых видима (в зависимости от размера экрана)
        WebElement catalogButton = catalogButtons.stream()
                .filter(WebElement::isDisplayed) //оставляем только видимые (должен быть 1)
                .findAny() // берем любой видимый
                .orElseThrow(() -> new AssertionError("Не найдена кнопка открытия всех каталогов")); //если не нашли кнопку - ругаемся
        catalogButton.click();*/

        List<WebElement> elements = driver.findElements(By.cssSelector(".Page__BlockElementsPageCatalog > .Page__ElementPageCatalog > div > a"));
    //    Assert.assertTrue("Не найден ни один каталог", !elements.isEmpty());

        int index = new Random().nextInt(elements.size());
        WebElement element = elements.get(index);
        String elementText = element.getText();
        element.click();
        String activePageTitle = driver.findElement(By.className("Page__TitleActivePage")).getText();
        Assert.assertEquals(elementText, activePageTitle);
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