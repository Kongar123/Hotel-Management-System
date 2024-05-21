/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package software.advanced_software_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.sql.*;
import java.sql.SQLException;
//import java.util.Vector;
//import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import rojeru_san.complementos.RSTableMetro;
import rojerusan.RSComboMetro;

/**
 *
 * @author ALAMIA
 */
public class User {

    private int userID;
    private String name;
    private String phone;
    private String email;
    private String password;

    // Default constructor
    public User() {
    }

    // Constructor with all arguments
    public User(int userID, String name, String phone, String email, String password) {
        this.userID = userID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;

    }

    // Getters and Setters (same as before)
    // Insert user into database
    public boolean insertUser(String url, RSTableMetro table) throws SQLException {
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return false;
        }
        if (!isValidPhone(phone)) {
            JOptionPane.showMessageDialog(null, "Invalid phone number format (digits only).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection(url, "root", "");
            String query = "INSERT INTO users (name, password, email, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.name);
            statement.setString(2, this.password);
            statement.setString(3, this.email);
            statement.setString(4, this.phone);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                JOptionPane.showMessageDialog(null, "User is added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                update_users_table(url, table);
                return true; // insert successful
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add this user.", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Update failed
            }
        } catch (SQLException ex) {
            // Handle SQLException with JOptionPane
            JOptionPane.showMessageDialog(null, "Error inserting user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw ex; // Re-throw the exception for proper handling by the calling code
        }
    }

    // Update user information in database
    public boolean updateUser(String url, RSTableMetro table) throws SQLException {

        // ... existing validation logic ...
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return false;
        }
        if (!isValidPhone(phone)) {
            JOptionPane.showMessageDialog(null, "Invalid phone number format (digits only).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to update user information?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DriverManager.getConnection(url, "root", "");
                String query = "UPDATE users set name=?, password=?, email=?, phone=? where id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, this.name);
                statement.setString(2, this.password);
                statement.setString(3, this.email);
                statement.setString(4, this.phone);
                statement.setInt(5, this.userID);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 1) {
                    JOptionPane.showMessageDialog(null, "User is updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    update_users_table(url, table);
                    return true; // insert successful
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update this user.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // Update failed
                }
            } catch (SQLException ex) {
                // Handle SQLException with JOptionPane
                JOptionPane.showMessageDialog(null, "Error updating user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw ex; // Re-throw the exception for proper handling by the calling code
            }
        } else {
            return false; //cancelled by user
        }
    }

    // Delete user from database
    public boolean deleteUser(String url, RSTableMetro table) throws SQLException {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete user information?", "Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DriverManager.getConnection(url, "root", "");
                String query = "DELETE FROM users where id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, this.userID);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 1) {
                    JOptionPane.showMessageDialog(null, "User is deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    update_users_table(url, table);
                    return true; // delete successful
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete this user.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // delete failed
                }
            } catch (SQLException ex) {
                // Handle SQLException with JOptionPane
                JOptionPane.showMessageDialog(null, "Error deleting user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw ex; // Re-throw the exception for proper handling by the calling code
            }
        } else {
            return false; //cancelled by user
        }
    }

    public static boolean update_users_table(String url, RSTableMetro table) {

        try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("SELECT * FROM users")) {
            ResultSet rs = stmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(null, "An error occurred during Insertion." + ex.getMessage());
            return false;
        }
    }

    public static void clear_user_information(JTextField txt_name, JTextField txt_phone, JTextField txt_email, JTextField txt_password) {
        txt_name.setText("");
        txt_phone.setText("");
        txt_email.setText("");
        txt_password.setText("");
        txt_name.requestFocus();

    }

    public static void search(String url, RSComboMetro combo_search, JTextField txt_search, RSTableMetro table_user) {
        String item = (String) combo_search.getSelectedItem();
        if (item.equals("ID")) {
            String id = txt_search.getText();

            try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("SELECT * FROM users where id like ?")) {
                stmt.setString(1, "%" + id + "%");
                ResultSet rs = stmt.executeQuery();
                table_user.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception
                JOptionPane.showMessageDialog(null, "An error occurred during Insertion." + ex.getMessage());
            }

        } else {
            String name = txt_search.getText();

            try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE name like ?")) {
                stmt.setString(1, "%" + name + "%");
                ResultSet rs = stmt.executeQuery();
                table_user.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception
                JOptionPane.showMessageDialog(null, "An error occurred during Insertion." + ex.getMessage());
            }

        }
    }

    // Helper method to validate phone number format (digits only)
    private static boolean isValidPhone(String phone) {
        return phone.matches("^\\d+$");
    }

    // Get user by ID from database (assuming userID is unique)
    private static boolean isValidEmail(String email) {
        return email.matches("^.+@.+\\..+$");
    }

    public static int getSelectedRow(RSTableMetro table) {
        DefaultTableModel dft = (DefaultTableModel) table.getModel();
        int selected_row = table.getSelectedRow();
        int id = Integer.parseInt(dft.getValueAt(selected_row, 0).toString());
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   


}
