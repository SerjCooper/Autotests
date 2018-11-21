package Autotests;

import Autotests.pages.CatalogPage;
import Autotests.pages.HomePage;
import Autotests.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class FirstTest {

    private static WebDriver driver;

    private static String login = "447659060";
    private static String password = "testa1qa";

    private LoginPage loginPage;
    private CatalogPage catalogPage;
    private HomePage homePage;

    @BeforeClass
    public static void setup() {
        InitConfig configs = new InitConfig();
        System.setProperty("webdriver." + configs.browserName +".driver", configs.driverPath);
        if("ie".equalsIgnoreCase(configs.browserName))
            driver = new InternetExplorerDriver();
        else if("gecko".equalsIgnoreCase(configs.browserName))
            driver = new FirefoxDriver();
        else
            driver = new ChromeDriver();

        driver.manage().window().maximize(); //передаю веб-драйверу набор методов, для того чтобы ход теста отображался в полностью открытом окне:
        driver.manage().timeouts().implicitlyWait(10, SECONDS); // неявное ожидание Implicit Wait, которое задается вначале теста и будет работать при каждом вызове метода поиска элемента:
        driver.get(configs.targetUrl);
    }

    @Test(priority = 1)
    public void userLoginTest() {
        loginPage = new LoginPage(driver);
        (new WebDriverWait(driver, 15))
                .until(ExpectedConditions.textToBePresentInElement(loginPage.getAuthWindow(), "Войти")); //реализация ожидания explicit
        loginPage.loginTo(login, password);
        Assert.assertEquals("userShop.by_20", loginPage.authUser());
    }

    @Test(priority = 2)
    public void openSameCatalogTest() {
        catalogPage = PageFactory.initElements(driver, CatalogPage.class);
        catalogPage.catalogButtonClick();
        Assert.assertEquals("Весь каталог", catalogPage.getActivePageTitle());

        List<WebElement> elements = catalogPage.getElements();
        Assert.assertTrue(!elements.isEmpty(), "Не найден ни один каталог");

        int index = new Random().nextInt(elements.size());

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(10, SECONDS)
                .pollingEvery(2, SECONDS)
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(driver -> elements.get(index));

        String elementText = element.getText();
        element.click();

        String activePageTitle = catalogPage.getActivePageTitle();
        Assert.assertEquals(elementText, activePageTitle);
    }

    @Test(priority = 3)
    public void openHomePageTest() {
        openHomePage();
        Assert.assertEquals(homePage.getBaseUri(), driver.getCurrentUrl());
    }

    @Test(priority = 4)
    public void FeedbackTest() throws IOException {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(10, SECONDS)
                .pollingEvery(2, SECONDS)
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(driver -> driver.findElement(By.className("ModelReviewsHome__NameModel")));
        String page = driver.getPageSource();
        String regexp = "<a class=\"ModelReviewsHome__NameModel\" href.*?>(.*?)</a>";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(page);
        List<String> reviews = new ArrayList<>();
        while (matcher.find()) {
            reviews.add(matcher.group(1));
        }
        Assert.assertTrue(!reviews.isEmpty(), "Не найден ни один отзыв");
        printToFile(reviews);
    }

    @Test(priority = 5)
    public void userLogoutTest() {
        loginPage.logout();
        Assert.assertFalse(loginPage.authUser().isEmpty());
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    private void printToFile(List<String> reviews) throws IOException {
        Files.deleteIfExists(Paths.get("Review.csv")); //если такой файл есть - удаляем
        try (FileOutputStream stream = new FileOutputStream("Review.csv"); //создаем стрим для записи в файл
             OutputStreamWriter writer = new OutputStreamWriter(stream, Charset.forName("UTF-8"))) //испльзуем writer для записи строк
        {
            for (String f : reviews) {
                writer.append(f).append(";");
            }
        }
    }

    public void openHomePage() {
        homePage = new HomePage(driver);
        homePage.headerLogoClick();
    }
}