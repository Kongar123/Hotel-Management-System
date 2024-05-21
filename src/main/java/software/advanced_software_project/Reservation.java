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
import java.time.temporal.ChronoUnit;
import javax.swing.table.DefaultTableModel;
import rojeru_san.complementos.RSTableMetro;
import rojeru_san.componentes.RSDateChooser;
import rojerusan.RSComboMetro;

/**
 *
 * @author ALAMIA
 */
public class Reservation {

    private int rsvID;
    private String check_in_date;
    private String check_out_date;
    private double total_cost;
    private String guest_id;
    private String room_id;

    // Default constructor
    public Reservation() {
    }

    
    public static void getRooms(String url, JComboBox combo) throws SQLException {
        Connection connection = DriverManager.getConnection(url, "root", "");
        String query = "SELECT * from rooms where status = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "Free");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            int R_id = rs.getInt("room_id");
            combo.addItem(R_id);
        }

    }

    public static void getGuests(String url, JComboBox combo) throws SQLException {
        Connection connection = DriverManager.getConnection(url, "root", "");
        String query = "SELECT * from guests";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            int G_id = rs.getInt("G_id");
            combo.addItem(G_id);
        }

    }

    public static void getCost(String url, JComboBox combo, JTextField txt_cost, RSDateChooser date_in, RSDateChooser date_out) throws SQLException {
        // Calculate the difference in days
        java.util.Date checkIn = date_in.getDatoFecha();
        java.util.Date checkOut = date_out.getDatoFecha();
        if (checkIn == null || checkOut == null) {
            // Handle the case where one or both date choosers are empty
            JOptionPane.showMessageDialog(null, "Please select both check-in and check-out dates");
            return; // Exit the method early
        }
        long numberOfDays = ChronoUnit.DAYS.between(checkIn.toInstant(), checkOut.toInstant());
        // Convert to int
        int numberOfDaysInt = (int) numberOfDays + 1;
        Connection connection = DriverManager.getConnection(url, "root", "");
        String query = "SELECT * FROM rooms where room_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        int G_id = Integer.parseInt(combo.getSelectedItem().toString());
        statement.setInt(1, G_id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            double price = rs.getDouble("price");
            double result = price * numberOfDaysInt;
            txt_cost.setText(String.valueOf(result));

        }
    }

    public static void roomStatusBooked(String url, JComboBox combo) throws SQLException {

        String S = "Booked";

        Connection connection = DriverManager.getConnection(url, "root", "");
        String query = "UPDATE rooms set status = ? where room_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, S);
        statement.setInt(2, Integer.valueOf(combo.getSelectedItem().toString()));
        statement.executeUpdate();
    }

    public static void roomStatusFree(String url, RSTableMetro table) throws SQLException {

        String S = "Free";

        Connection connection = DriverManager.getConnection(url, "root", "");
        String query = "UPDATE rooms set status = ? where room_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, S);
        statement.setInt(2, getSelectedRow(table, 2));
        statement.executeUpdate();
    }

    public boolean insertReservation(String url, RSTableMetro table) throws SQLException {
        if (room_id.isEmpty() || guest_id.isEmpty() || check_in_date == null || check_out_date == null || total_cost == 0.0) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return false;
        }

        try {
            Connection connection = DriverManager.getConnection(url, "root", "");
            String query = "INSERT INTO reservations (guest_id, room_id, check_in_date, check_out_date, cost) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.guest_id);
            statement.setString(2, this.room_id);
            statement.setString(3, this.check_in_date);
            statement.setString(4, this.check_out_date);
            statement.setDouble(5, this.total_cost);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                JOptionPane.showMessageDialog(null, "Reservation is added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                update_reservation_table(url, table);
                return true; // insert successful
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add this reservation.", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Update failed
            }
        } catch (SQLException ex) {
            // Handle SQLException with JOptionPane
            JOptionPane.showMessageDialog(null, "Error inserting reservation: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw ex; // Re-throw the exception for proper handling by the calling code
        }
    }

    // Delete room from database
    public boolean deleteReservation(String url, RSTableMetro table) throws SQLException {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this reservation?", "Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            roomStatusFree(url, table);
            try {
                Connection connection = DriverManager.getConnection(url, "root", "");
                String query = "DELETE FROM reservations where reservation_id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, this.rsvID);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 1) {
                    JOptionPane.showMessageDialog(null, "Reservation is deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    update_reservation_table(url, table);
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

    public static boolean update_reservation_table(String url, RSTableMetro table) {

        try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("SELECT * FROM reservations")) {
            ResultSet rs = stmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(null, "An error occurred." + ex.getMessage());
            return false;
        }
    }

    public static void search(String url, RSComboMetro combo_search, JTextField txt_search, RSTableMetro table_room) {
        String item = combo_search.getSelectedItem().toString();
        if (item.equals("Guest ID")) {
            String id = txt_search.getText();

            try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("SELECT * FROM reservations where guest_id like ?")) {
                stmt.setString(1, "%" + id + "%");
                ResultSet rs = stmt.executeQuery();
                table_room.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception
                JOptionPane.showMessageDialog(null, "An error occurred during Insertion." + ex.getMessage());
            }

        } else {
            String name = txt_search.getText();

            try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("SELECT * FROM reservations WHERE room_id like ?")) {
                stmt.setString(1, "%" + name + "%");
                ResultSet rs = stmt.executeQuery();
                table_room.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception
                JOptionPane.showMessageDialog(null, "An error occurred during Insertion." + ex.getMessage());
            }

        }
    }

    public static void count_total_income(String url, JLabel label) {

        try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("SELECT SUM(cost) AS total_cost FROM reservations;")) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double numIncomecost = rs.getDouble("total_cost");
                label.setText(Double.toString(numIncomecost) + "$");
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(null, "An error occurred during registration." + ex.getMessage());
        }
    }

    public static int getSelectedRow(RSTableMetro table, int index) {
        DefaultTableModel dft = (DefaultTableModel) table.getModel();
        int selected_row = table.getSelectedRow();
        int id = Integer.parseInt(dft.getValueAt(selected_row, index).toString());
        return id;
    }

    public void setRsvID(int rsvID) {
        this.rsvID = rsvID;
    }

    public void setCheck_in_date(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public void setCheck_out_date(String check_out_date) {
        this.check_out_date = check_out_date;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public void setGuest_id(String guest_id) {
        this.guest_id = guest_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public int getRsvID() {
        return rsvID;
    }

    public String getCheck_in_date() {
        return check_in_date;
    }

    public String getCheck_out_date() {
        return check_out_date;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public String getGuest_id() {
        return guest_id;
    }

    public String getRoom_id() {
        return room_id;
    }

}
