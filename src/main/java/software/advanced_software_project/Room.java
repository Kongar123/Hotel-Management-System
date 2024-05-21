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
public class Room {

    private int roomID;
    private String room_number;
    private int bedrooms;
    private String room_type;
    private String status;
    private double price;

    // Default constructor
    public Room() {
    }

    // Constructor with all arguments
    public Room(int roomID, String room_number, int bedrooms, String room_type, String status, double price) {
        this.roomID = roomID;
        this.room_number = room_number;
        this.bedrooms = bedrooms;
        this.room_type = room_type;
        this.status = status;
        this.price = price;
    }

    // Getters and Setters (same as before)
    // Insert room into database
    public boolean insertRoom(String url, RSTableMetro table) throws SQLException {
        if (room_number.isEmpty() || bedrooms == 0 || room_type.isEmpty() || status.isEmpty() || price == 0.0) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return false;
        }
        if (!isValidPrice(price)) {
            JOptionPane.showMessageDialog(null, "Invalid price(digits only).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection(url, "root", "");
            String query = "INSERT INTO rooms (room_number, bedrooms, room_type, status, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.room_number);
            statement.setInt(2, this.bedrooms);
            statement.setString(3, this.room_type);
            statement.setString(4, this.status);
            statement.setDouble(5, this.price);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                JOptionPane.showMessageDialog(null, "Room is added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                update_rooms_table(url, table);
                return true; // insert successful
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add this room.", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Update failed
            }
        } catch (SQLException ex) {
            // Handle SQLException with JOptionPane
            JOptionPane.showMessageDialog(null, "Error inserting room: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw ex; // Re-throw the exception for proper handling by the calling code
        }
    }

    // Update room information in database
    public boolean updateRoom(String url, RSTableMetro table) throws SQLException {

        // ... existing validation logic ...
        if (room_number.isEmpty() || bedrooms == 0 || room_type.isEmpty() || status.isEmpty() || price == 0.0) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return false;
        }
        if (!isValidPrice(price)) {
            JOptionPane.showMessageDialog(null, "Invalid price(digits only).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to update room information?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DriverManager.getConnection(url, "root", "");
                String query = "UPDATE rooms set room_number=?, bedrooms=?, room_type=?, status=?, price=? where room_id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, this.room_number);
                statement.setInt(2, this.bedrooms);
                statement.setString(3, this.room_type);
                statement.setString(4, this.status);
                statement.setDouble(5, this.price);
                statement.setInt(6, this.roomID);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 1) {
                    JOptionPane.showMessageDialog(null, "Room is updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    update_rooms_table(url, table);
                    return true; // insert successful
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update this room.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // Update failed
                }
            } catch (SQLException ex) {
                // Handle SQLException with JOptionPane
                JOptionPane.showMessageDialog(null, "Error updating room: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw ex; // Re-throw the exception for proper handling by the calling code
            }
        } else {
            return false; //cancelled by user
        }
    }

    // Delete room from database
    public boolean deleteRoom(String url, RSTableMetro table) throws SQLException {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete room information?", "Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DriverManager.getConnection(url, "root", "");
                String query = "DELETE FROM rooms where room_id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, this.roomID);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 1) {
                    JOptionPane.showMessageDialog(null, "Room is deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    update_rooms_table(url, table);
                    return true; // delete successful
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete this room.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // delete failed
                }
            } catch (SQLException ex) {
                // Handle SQLException with JOptionPane
                JOptionPane.showMessageDialog(null, "Error deleting room: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw ex; // Re-throw the exception for proper handling by the calling code
            }
        } else {
            return false; //cancelled by user
        }
    }

    public static boolean update_rooms_table(String url, RSTableMetro table) {

        try (Connection con = DriverManager.getConnection(url, "root", ""); 
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM rooms")) {
            ResultSet rs = stmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(null, "An error occurred." + ex.getMessage());
            return false;
        }
    }

    public static void clear_room_information(JTextField txt_room_number, JComboBox combo_no_rooms, JComboBox combo_room_type, JTextField txt_price, ButtonGroup btnGroup_Room) {
        txt_room_number.setText("");
        combo_no_rooms.setSelectedIndex(-1);
        combo_room_type.setSelectedIndex(-1);
        txt_price.setText("");
        btnGroup_Room.clearSelection();
        txt_room_number.requestFocus();

    }

        public static void status_search(String url, RSComboMetro combo_search, RSTableMetro table_room) {
        String item = combo_search.getSelectedItem().toString();
       
            try (Connection con = DriverManager.getConnection(url, "root", ""); 
                    PreparedStatement stmt = con.prepareStatement("SELECT * FROM rooms where status =?")) {
                stmt.setString(1,item);
                ResultSet rs = stmt.executeQuery();
                table_room.setModel(DbUtils.resultSetToTableModel(rs));
                

            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception
                JOptionPane.showMessageDialog(null, "An error occurred during Insertion." + ex.getMessage());
            }

        } 
    
            public static void room_type_search(String url, RSComboMetro combo_search, RSTableMetro table_room) {
        String item = combo_search.getSelectedItem().toString();
       
            try (Connection con = DriverManager.getConnection(url, "root", ""); 
                    PreparedStatement stmt = con.prepareStatement("SELECT * FROM rooms where room_type =?")) {
                stmt.setString(1,item);
                ResultSet rs = stmt.executeQuery();
                table_room.setModel(DbUtils.resultSetToTableModel(rs));
                

            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception
                JOptionPane.showMessageDialog(null, "An error occurred during Insertion." + ex.getMessage());
            }

        } 


    // Helper method to validate phone number format (digits only)
    private static boolean isValidPrice(double price) {
        return String.valueOf(price).matches("^(\\d+)?\\.*\\d+$");
    }

    // Get room by ID from database (assuming roomID is unique)
    public void setStatusFromRadioButtons(JRadioButton radio_free, JRadioButton radio_booked) {
        if (radio_free.isSelected()) {
            this.status = "Free";
        } else if (radio_booked.isSelected()) {
            this.status = "Booked";
        } else {
            this.status = ""; // Set to empty if neither is selected
        }
    }


    public static void count_free_rooms(String url, JLabel label) {
       
        try (Connection con = DriverManager.getConnection(url, "root", ""); 
                PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS free_rooms_count FROM rooms WHERE status = 'Free';")) {
           
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int numFreeRooms = rs.getInt("free_rooms_count");
                label.setText(Integer.toString(numFreeRooms));
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(null, "An error occurred during registration." + ex.getMessage());
        }
    }

    public static void count_booked_rooms(String url, JLabel label) {

        
        try (Connection con = DriverManager.getConnection(url, "root", "");
                PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS booked_rooms_count FROM rooms WHERE status = 'Booked';")) {
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int numBookedRooms = rs.getInt("booked_rooms_count");
                label.setText(Integer.toString(numBookedRooms));
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(null, "An error occurred during registration." + ex.getMessage());
        }
    }

    public static int getSelectedRow(RSTableMetro table) {
        DefaultTableModel dft = (DefaultTableModel) table.getModel();
        int selected_row = table.getSelectedRow();
        int id = Integer.parseInt(dft.getValueAt(selected_row, 0).toString());
        return id;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getRoom_number() {
        return room_number;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public String getRoom_type() {
        return room_type;
    }

    public String getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
