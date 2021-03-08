package ru.netology.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidAuthInfo(){
        return new AuthInfo("vasya", "invalid");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }


    public static VerificationCode getVerificationCodefromDB() throws SQLException {
        val usersSQL = "SELECT code FROM auth_codes";
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_db", "app", "pass");
        val auth_code = runner.query(conn, usersSQL, new BeanHandler<>(DB_Code.class));
        return new VerificationCode(auth_code.getCode());
    }

    @Value
    public static class Status {
        private String status;
    }

    public static String getStatusfromDB() throws SQLException {
        val usersSQL = "SELECT status FROM users WHERE login='vasya'";
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_db", "app", "pass");
        val status = runner.query(conn, usersSQL, new BeanHandler<>(DB_Users.class));
        return status.getStatus();
    }


}