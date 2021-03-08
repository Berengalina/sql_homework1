package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    VerificationPage(){codeField.shouldBe(Condition.visible);}

    public DashboardPage validVerify (DataHelper.VerificationCode code){
        codeField.setValue(code.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerifyAfterThreeCodes(DataHelper.VerificationCode code){
        codeField.setValue(code.getCode());
        verifyButton.click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Превышено количество попыток ввода кода"));
    }

    public void invalidVerifyCodes(){
        codeField.setValue(DataHelper.getInvalidCode());
        verifyButton.click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан код"));
    }

}