package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import ru.netology.domain.GenerateUsers;
import ru.netology.domain.RegistrationDto;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.cssSelector;

public class AuthTest {

    @Test
    void shouldSubmitIfValidUser() {
        RegistrationDto user = GenerateUsers.generateValidActiveUser();
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name='login']")).sendKeys(user.getLogin());
        form.$(cssSelector("[name='password']")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Личный кабинет")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotSubmitIfBlocked() {
        RegistrationDto user = GenerateUsers.generateValidBlockedUser();
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name='login']")).sendKeys(user.getLogin());
        form.$(cssSelector("[name='password']")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Ошибка")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotSubmitIfLoginInvalid() {
        RegistrationDto user = GenerateUsers.generateUserInvalidLogin();
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name='login']")).sendKeys(user.getLogin());
        form.$(cssSelector("[name='password']")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Ошибка")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotSubmitIfPasswordInvalid() {
        RegistrationDto user = GenerateUsers.generateUserInvalidPassword();
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name='login']")).sendKeys(user.getLogin());
        form.$(cssSelector("[name='password']")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Ошибка")).waitUntil(Condition.visible, 15000);
    }
}
