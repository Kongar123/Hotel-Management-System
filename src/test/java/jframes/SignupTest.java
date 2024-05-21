/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package jframes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ALAMIA
 */
public class SignupTest {

    public SignupTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of insert_signup method, of class Signup.
     */
    @Test
    public void testInsert_signup() {
        System.out.println("insert_signup");
        String name = "zaki";
        String pwd = "123";
        String confirm_pwd = "123";
        String email = "zaki@gmail.com";
        String phone = "01209890909";
        Signup signup = new Signup();
        assertTrue(signup.insert_signup(name, pwd, confirm_pwd, email, phone));
    }

    @Test
    public void testInsert_signup_isempty() {
        System.out.println("Insert_signup_isempty");
        String name = "";
        String pwd = "";
        String confirm_pwd = "";
        String email = "";
        String phone = "";
        Signup signup = new Signup();
        assertFalse(signup.insert_signup(name, pwd, confirm_pwd, email, phone));
    }

    @Test
    public void testInsert_signup_passwords_does_not_match() {
        System.out.println("Insert_signup_passwords_does_not_match");
        String name = "zaki";
        String pwd = "123";
        String confirm_pwd = "1515";
        String email = "zaki@gmail.com";
        String phone = "01209890909";
        Signup signup = new Signup();
        assertFalse(signup.insert_signup(name, pwd, confirm_pwd, email, phone));
    }

    @Test
    public void testInsert_signup_invalid_email() {
        System.out.println("Insert_signup_invalid_email");
        String name = "zaki";
        String pwd = "123";
        String confirm_pwd = "123";
        String email = "this is an invalid email";
        String phone = "01209890909";
        Signup signup = new Signup();
        assertFalse(signup.insert_signup(name, pwd, confirm_pwd, email, phone));
    }

    @Test
    public void testInsert_signup_invalid_phone() {
        System.out.println("Insert_signup_invalid_phone");
        String name = "zaki";
        String pwd = "123";
        String confirm_pwd = "123";
        String email = "zaki@gmail.com";
        String phone = "this is an invalid phone";
        Signup signup = new Signup();
        assertFalse(signup.insert_signup(name, pwd, confirm_pwd, email, phone));

    }

    /**
     * Test of check_duplicate_users method, of class Signup.
     */
    @Test
    public void testisExist_users() {
        System.out.println("check_duplicate_emails");
        String username = "anything";
        String email = "anything";
        Signup signup = new Signup();
        assertFalse(signup.isExist_users(username, email));
    }

    @Test
    public void testisExist_users_username() {
        System.out.println("check_duplicate_usernsmes");
        String username = "admin";
        String email = "anything";
        Signup signup = new Signup();
        assertTrue(signup.isExist_users(username, email));
    }

    public void testisExist_users_email() {
        System.out.println("check_duplicate_emails");
        String username = "anything";
        String email = "admin@hotel.edu";
        Signup signup = new Signup();
        assertTrue(signup.isExist_users(username, email));
    }

    /**
     * Test of main method, of class Signup.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Signup.main(args);
    }

}
