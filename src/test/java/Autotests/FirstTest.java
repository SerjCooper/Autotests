package Autotests;

import Autotests.pages.CatalogPage;
import Autotests.pages.HomePage;
import Autotests.pages.LoginPage;
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
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class FirstTest {

    private static WebDriver driver;

    private String login = "447659060";
    private String password = "testa1qa";

    private LoginPage loginPage;
    private CatalogPage catalogPage;
    private HomePage homePage;

    @BeforeClass
    public static void setup() {
        InitConfig configs = new InitConfig();
        System.setProperty("webdriver." + configs.browserName +".driver", configs.driverPath);
        if ("Opera".equalsIgnoreCase(configs.browserName))
            driver = new ChromeDriver();
        else if("ie".equalsIgnoreCase(configs.browserName))
            driver = new InternetExplorerDriver();
        else if("Firefox".equalsIgnoreCase(configs.browserName))
            driver = new FirefoxDriver();
        else
            driver = new ChromeDriver();

        driver.manage().window().maximize(); //передаю веб-драйверу набор методов, для того чтобы ход теста отображался в полностью открытом окне:
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // неявное ожидание Implicit Wait, которое задается вначале теста и будет работать при каждом вызове метода поиска элемента:
        driver.get(configs.targetUrl);
    }

    @Test(priority = 1)
    public void userLogin() {
        loginPage = new LoginPage(driver);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElement(loginPage.getAuthWindow(), "Войти")); //реализация ожидания explicit
        loginPage.loginTo(login, password);

        Assert.assertEquals("userShop.by_20", loginPage.authUser());
    }

    @Test(priority = 2)
    public void openSameCatalog() {
        catalogPage = new CatalogPage(driver);
        catalogPage.catalogButtonClick();
        Assert.assertEquals("Весь каталог", catalogPage.getActivePageTitle());

        List<WebElement> elements = catalogPage.getElements();
        Assert.assertTrue(!elements.isEmpty(), "Не найден ни один каталог");

        int index = new Random().nextInt(elements.size());
        WebElement element = elements.get(index);
        String elementText = element.getText();
        element.click();
        String activePageTitle = catalogPage.getActivePageTitle();
        Assert.assertEquals(elementText, activePageTitle);
    }

    @Test(priority = 3)
    public void openHomePage() {
        homePage = new HomePage(driver);
        homePage.headerLogoClick();
        Assert.assertEquals(homePage.getBaseUri(), driver.getCurrentUrl());
    }

    @Test(priority = 4)
    public void testFeedback() throws IOException {
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
    public void testLogout() {
        loginPage.logout();
        Assert.assertFalse(loginPage.authUser().isEmpty());
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

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}