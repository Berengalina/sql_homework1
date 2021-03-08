package ru.netology.register;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterUser {

    @BeforeEach
    void SetUp() {
        open("http://localhost:9999/");
    }

    @Order(1)
    @Test
    void shouldLoginAndVerify() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodefromDB();
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingTitle();
    }

    @Order(2)
    @Test
    void shouldNotVerifyInvalidCode() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerifyCodes();

    }

    @Order(3)
    @Test
    void shouldNotVerifyAfterThreeCodes() throws SQLException {   //проверяем, что система не дает пройти верификацию, если перед этим уже было сгенерировано 3 успешных кода
        DataHelper.clearVerificationCodefromDB(); //очищаем таблицу auth_codes
        DataHelper.insertThreeCodes(); //вставляем в таблицу auth_codes три записи для нужного user_id
        val loginPage = new LoginPage();
        val authInfo1 = DataHelper.getAuthInfo();
        val verificationPage1 = loginPage.validLogin(authInfo1);
        val verificationCode1 = DataHelper.getVerificationCodefromDB();
        verificationPage1.invalidVerifyAfterThreeCodes(verificationCode1);
    }

    @Order(4)
    @Test
    void shouldBlockAfterThreeWrongPassword() throws SQLException {
        val loginPage = new LoginPage();
        val invalidAuthInfo = DataHelper.getInvalidAuthInfo();
        loginPage.invalidLogin(DataHelper.getInvalidAuthInfo());
        loginPage.invalidLogin2();
        loginPage.invalidLogin3();
        val actual = DataHelper.getStatusfromDB();
        assertEquals("blocked", actual);
    }

    @AfterAll
    static void cleaningDB() throws SQLException {
        String dataSQL_users = "DELETE FROM users";
        String dataSQL_cards = "DELETE FROM cards";
        String dataSQL_auth_codes = "DELETE FROM auth_codes";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_db", "app", "pass");
        ) {
            runner.update(conn, dataSQL_cards);
            runner.update(conn, dataSQL_auth_codes);
            runner.update(conn, dataSQL_users);
        }
    }

}