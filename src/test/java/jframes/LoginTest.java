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
public class LoginTest {
    
    public LoginTest() {
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
     * Test of validate_login method, of class Login.
     */
    @Test
    public void testValidate_login1() {
        String username = "admin";
        String password = "admin";
        Login login = new Login();
        boolean expResult = true;
        boolean result = login.validate_login(username, password);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testValidate_login2() {
        String username = "invaliduser";
        String password = "admin";
        Login login = new Login();
        boolean expResult = false;
        boolean result = login.validate_login(username, password);
        assertEquals(expResult, result);   
    }
    
    @Test
    public void testValidate_login3() {
        String username = "admin";
        String password = "invalidpwd";
        Login login = new Login();
        boolean expResult = false;
        boolean result = login.validate_login(username, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidate_login4() {
        String username = "invaliduser";
        String password = "invalidpwd";
        Login login = new Login();
        boolean expResult = false;
        boolean result = login.validate_login(username, password);
        assertEquals(expResult, result); 
    }


    /**
     * Test of main method, of class Login.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Login.main(args);
        
    }
    
}
