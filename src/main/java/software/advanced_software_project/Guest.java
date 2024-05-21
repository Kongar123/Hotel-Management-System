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
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import rojeru_san.complementos.RSTableMetro;
import rojerusan.RSComboMetro;

/**
 *
 * @author ALAMIA
 */
public class Guest {

    private int guestID;
    private String name;
    private String phoneNumber;
    private String email;
    private String city;
    private String gender;
    

    // Default constructor
    public Guest() {
    }
    

    // Constructor with all arguments
    public Guest(int guestID, String name, String phoneNumber, String email, String city, String gender) {
        this.guestID = guestID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.city = city;
        this.gender = gender;
    }


    // Getters and Setters (same as before)
    // Insert guest into database
    public boolean insertGuest(String url, RSTableMetro table) throws SQLException {
        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || city.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return false;
        }
        if (!isValidPhone(phoneNumber)) {
            JOptionPane.showMessageDialog(null, "Invalid phone number format (digits only).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection(url, "root", "");
            String query = "INSERT INTO guests (name, phone, email, city, gender) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.name);
            statement.setString(2, this.phoneNumber);
            statement.setString(3, this.email);
            statement.setString(4, this.city);
            statement.setString(5, this.gender);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                JOptionPane.showMessageDialog(null, "Guest is added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                update_guests_table(url, table);
                return true; // insert successful
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add this guest.", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Update failed
            }
        } catch (SQLException ex) {
            // Handle SQLException with JOptionPane
            JOptionPane.showMessageDialog(null, "Error inserting guest: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw ex; // Re-throw the exception for proper handling by the calling code
        }
    }

    // Update guest information in database
    public boolean updateGuest(String url, RSTableMetro table) throws SQLException {

        // ... existing validation logic ...
        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || city.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return false;
        }
        if (!isValidPhone(phoneNumber)) {
            JOptionPane.showMessageDialog(null, "Invalid phone number format (digits only).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to update guest information?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DriverManager.getConnection(url, "root", "");
                String query = "UPDATE guests set name=?, phone=?, email=?, city=?, gender=? where G_id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, this.name);
                statement.setString(2, this.phoneNumber);
                statement.setString(3, this.email);
                statement.setString(4, this.city);
                statement.setString(5, this.gender);
                statement.setInt(6, this.guestID);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 1) {
                    JOptionPane.showMessageDialog(null, "Guest is updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    update_guests_table(url, table);
                    return true; // insert successful
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update this guest.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // Update failed
                }
            } catch (SQLException ex) {
                // Handle SQLException with JOptionPane
                JOptionPane.showMessageDialog(null, "Error updating guest: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw ex; // Re-throw the exception for proper handling by the calling code
            }
        } else {
            return false; //cancelled by user
        }
    }

    // Delete guest from database
    public boolean deleteGuest(String url, RSTableMetro table) throws SQLException {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete guest information?", "Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DriverManager.getConnection(url, "root", "");
                String query = "DELETE FROM guests where G_id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, this.guestID);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 1) {
                    JOptionPane.showMessageDialog(null, "Guest is deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    update_guests_table(url, table);
                    return true; // delete successful
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete this guest.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // delete failed
                }
            } catch (SQLException ex) {
                // Handle SQLException with JOptionPane
                JOptionPane.showMessageDialog(null, "Error deleting guest: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw ex; // Re-throw the exception for proper handling by the calling code
            }
        } else {
            return false; //cancelled by user
        }
    }

    public static boolean update_guests_table(String url, RSTableMetro table) {

        try (Connection con = DriverManager.getConnection(url, "root", "");
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM guests")) {
            ResultSet rs = stmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(null, "An error occurred during Insertion." + ex.getMessage());
            return false;
        }
    }

    public static void clear_guest_information(JTextField txt_name, JTextField txt_phone, JTextField txt_email, JTextField txt_city, ButtonGroup buttonGroup1) {
        txt_name.setText("");
        txt_phone.setText("");
        txt_email.setText("");
        txt_city.setText("");
        txt_name.requestFocus();
        buttonGroup1.clearSelection();
    }

    public static void search(String url, RSComboMetro combo_search, JTextField txt_search, RSTableMetro table_guest) {
        String item =combo_search.getSelectedItem().toString();
        if (item.equals("ID")) {
            String id = txt_search.getText();

            try (Connection con = DriverManager.getConnection(url, "root", ""); 
                    PreparedStatement stmt = con.prepareStatement("SELECT * FROM guests where G_id like ?")) {
                stmt.setString(1, "%" + id + "%");
                ResultSet rs = stmt.executeQuery();
                table_guest.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception
                JOptionPane.showMessageDialog(null, "An error occurred during Insertion." + ex.getMessage());
            }

        } else {
            String name = txt_search.getText();

            try (Connection con = DriverManager.getConnection(url, "root", "");
                    PreparedStatement stmt = con.prepareStatement("SELECT * FROM guests WHERE name like ?")) {
                stmt.setString(1, "%" + name + "%");
                ResultSet rs = stmt.executeQuery();
                table_guest.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception
                JOptionPane.showMessageDialog(null, "An error occurred during Insertion." + ex.getMessage());
            }

        }
    }
        public static void count_nums(String url,JLabel label) {

        try (Connection con = DriverManager.getConnection(url,"root","");
                PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS num_guests FROM guests;")) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int numGuests = rs.getInt("num_guests");
                label.setText(Integer.toString(numGuests));
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(null, "An error occurred during registration." + ex.getMessage());
        }
    }

    // Helper method to validate phone number format (digits only)
    private static boolean isValidPhone(String phone) {
        return phone.matches("^\\d+$");
    }

    // Get guest by ID from database (assuming guestID is unique)
    private static boolean isValidEmail(String email) {
        return email.matches("^.+@.+\\..+$");
    }

    public void setGenderFromRadioButtons(JRadioButton radioMale, JRadioButton radioFemale) {
        if (radioMale.isSelected()) {
            this.gender = "Male";
        } else if (radioFemale.isSelected()) {
            this.gender = "Female";
        } else {
            this.gender = ""; // Set to empty if neither is selected
        }
    }

    public static int getSelectedRow(RSTableMetro table) {
        DefaultTableModel dft = (DefaultTableModel) table.getModel();
        int selected_row = table.getSelectedRow();
        int id = Integer.parseInt(dft.getValueAt(selected_row, 0).toString());
        return id;
    }

    public int getGuestID() {
        return guestID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }

    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Override toString method for better representation
    @Override
    public String toString() {
        return "Guest{" + "guestID=" + guestID + ", name='" + name + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", email='" + email + '\'' + ", city='" + city + '\'' + ", gender='" + gender + '\'' + '}';
    }
}
