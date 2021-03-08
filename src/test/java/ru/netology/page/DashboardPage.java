package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private SelenideElement headingTitle = $(byText("Личный кабинет"));

    public void checkHeadingTitle() {
        headingTitle.shouldBe(Condition.visible);
    }
}