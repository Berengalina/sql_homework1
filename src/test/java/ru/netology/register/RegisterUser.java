package ru.netology.register;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import java.sql.DriverManager;
import java.sql.SQLException;
import static com.codeborne.selenide.Selenide.open;

public class RegisterUser {

    @BeforeEach
            void SetUp(){
        open("http://localhost:9999/");
    }


    @Test
    void shouldLoginAndVerify() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodefromDB();
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingTitle();
    }


    @Test
    void shouldBlockAfterThreeWrongPassword(){
        val loginPage = new LoginPage();
        val invalidAuthInfo = DataHelper.getInvalidAuthInfo();
        loginPage.invalidLogin(DataHelper.getInvalidAuthInfo());
        loginPage.invalidLogin2();
        loginPage.invalidLogin3();

    };

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