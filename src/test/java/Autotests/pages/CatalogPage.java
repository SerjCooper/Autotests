package Autotests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class CatalogPage {

    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "/html/body/div[2]/div/div[2]/div[1]/a")
    private static List<WebElement> catalogButtons;

    @FindBy(how = How.CSS, using = ".Page__BlockElementsPageCatalog > .Page__ElementPageCatalog > div > a")
    private static List<WebElement> elementsSelector;

    @FindBy(how = How.CLASS_NAME, using = "Page__TitleActivePage")
    private static WebElement activePageTitle;

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
    }

    public void catalogButtonClick() {
        List<WebElement> cbuttons = catalogButtons; // есть 2 кнопки каталога, одна из которых видима (в зависимости от размера экрана)
        WebElement cbutton = cbuttons.stream()
                .filter(WebElement::isDisplayed) //оставляем только видимые (должен быть 1)
                .findAny() // берем любой видимый
                .orElseThrow(() -> new AssertionError("Не найдена кнопка открытия всех каталогов")); //если не нашли кнопку - ругаемся
        cbutton.click();
    }

    public String getActivePageTitle() {
        return activePageTitle.getText();
    }

    public List<WebElement> getElements() {
        return elementsSelector;
    }
}
