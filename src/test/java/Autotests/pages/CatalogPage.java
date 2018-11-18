package Autotests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CatalogPage {

    private WebDriver driver;

    private By catalogButtons = By.xpath("/html/body/div[2]/div/div[2]/div[1]/a");
    private By elementsSelector = By.cssSelector(".Page__BlockElementsPageCatalog > .Page__ElementPageCatalog > div > a");
    private By activePageTitle = By.className("Page__TitleActivePage");

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
    }

    public void catalogButtonClick() {
        List<WebElement> cbuttons = driver.findElements(catalogButtons); // есть 2 кнопки каталога, одна из которых видима (в зависимости от размера экрана)
        WebElement cbutton = cbuttons.stream()
                .filter(WebElement::isDisplayed) //оставляем только видимые (должен быть 1)
                .findAny() // берем любой видимый
                .orElseThrow(() -> new AssertionError("Не найдена кнопка открытия всех каталогов")); //если не нашли кнопку - ругаемся
        cbutton.click();
    }

    public String getActivePageTitle() {
        return driver.findElement(activePageTitle).getText();
    }

    public List<WebElement> getElements() {
        return driver.findElements(elementsSelector);
    }
}
