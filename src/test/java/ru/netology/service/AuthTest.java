package ru.netology.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.locks.Condition;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.service.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.service.DataGenerator.Registration.getUser;


class AuthTest {

    @BeforeEach
    void setup() { open("http://localhost:9999"); }

    @Test
    @DisplayName("Should successfuly login with active registered user")
    void shouldSuccessfulyLoginWithActiveRegisteredUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error massage if login with not registered user")
    void shouldgetErrorMassageIfLoginWithNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        com.codeborne.selenide.SelenideElement $ = $("[data-test-id='error-notification'] .notification__content");
        $.shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль").Duration.ofSeconds(10));
        $.shouldBe(Condition.visible);
    }
}
