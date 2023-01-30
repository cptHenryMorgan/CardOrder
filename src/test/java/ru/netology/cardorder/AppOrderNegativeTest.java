package ru.netology.cardorder;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppOrderNegativeTest {
    private WebDriver driver;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver()
                .setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldBeFailedIncorrectNameInput() {
        driver.findElement(By.cssSelector("[data-test-id=name] input"))
                .sendKeys("Vano");
        driver.findElement(By.cssSelector("[data-test-id=phone] input"))
                .sendKeys("+79256546767");
        driver.findElement(By.cssSelector("[data-test-id=agreement]"))
                .click();
        driver.findElement(By.cssSelector("button.button"))
                .click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"))
                .getText()
                .trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText);
    }

    @Test
    public void shouldBeFailedIncorrectPhoneInput() {
        driver.findElement(By.cssSelector("[data-test-id=name] input"))
                .sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input"))
                .sendKeys("+7925654676");
        driver.findElement(By.cssSelector("[data-test-id=agreement]"))
                .click();
        driver.findElement(By.cssSelector("button.button"))
                .click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"))
                .getText()
                .trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
    }

    @Test
    public void shouldBeFailedEmptyNameInput() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input"))
                .sendKeys("+7925654676");
        driver.findElement(By.cssSelector("[data-test-id=agreement]"))
                .click();
        driver.findElement(By.cssSelector("button.button"))
                .click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"))
                .getText()
                .trim();
        assertEquals("Поле обязательно для заполнения", actualText);
    }

    @Test
    public void shouldBeFailedEmptyPhoneInput() {
        driver.findElement(By.cssSelector("[data-test-id=name] input"))
                .sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=agreement]"))
                .click();
        driver.findElement(By.cssSelector("button.button"))
                .click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"))
                .getText()
                .trim();
        assertEquals("Поле обязательно для заполнения", actualText);
    }

    @Test
    public void shouldBeFailedCheckBoxClick() {
        driver.findElement(By.cssSelector("[data-test-id=name] input"))
                .sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input"))
                .sendKeys("+7925654676");
        driver.findElement(By.cssSelector("button.button"))
                .click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text"))
                .getText()
                .trim();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actualText);
    }
}
