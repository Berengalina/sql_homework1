package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }

    public void invalidLogin2() {
        loginButton.click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }

    public void invalidLogin3() {
        loginButton.click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверный пароль введен 3 раза. Вход заблокирован"));
    }

}