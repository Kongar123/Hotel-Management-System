/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframes;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ALAMIA
 */
public class Old extends javax.swing.JFrame {

    private final String url = "jdbc:mysql://localhost:3306/software_project";

    /**
     * Creates new form Home
     */
    public Old() {
        initComponents();
        update_guests_table();
    }

    public void insert_guest() {
        String name = txt_name.getText();
        String phone = txt_phone.getText();
        String email = txt_email.getText();
        String city = txt_city.getText();
        String gender = "";

        if (radio_male.isSelected()) {
            gender = "Male";
        } else if (radio_female.isSelected()) {
            gender = "Female";
        }

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || city.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (!email.matches("^.+@.+\\..+$")) {
            JOptionPane.showMessageDialog(this, "please insert valid email");
            return;
        }

        if (!phone.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "please insert valid phone number");
            return;
        }

        try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("INSERT INTO guests (name, phone, email, city, gender) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, city);
            stmt.setString(5, gender);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 1) {
                JOptionPane.showMessageDialog(this, "Added Successfully");
                update_guests_table();
                clear();
            } else {
                JOptionPane.showMessageDialog(this, "Insertion Failed");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(this, "An error occurred during Insertion." + ex.getMessage());
        }
    }

    public void update_guest() {

        DefaultTableModel dft = (DefaultTableModel) table_guest.getModel();
        int selected_row = table_guest.getSelectedRow();

        int id = Integer.parseInt(dft.getValueAt(selected_row, 0).toString());

        String name = txt_name.getText();
        String phone = txt_phone.getText();
        String email = txt_email.getText();
        String city = txt_city.getText();
        String gender = "";

        if (radio_male.isSelected()) {
            gender = "Male";
        } else if (radio_female.isSelected()) {
            gender = "Female";
        }

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || city.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (!email.matches("^.+@.+\\..+$")) {
            JOptionPane.showMessageDialog(this, "please insert valid email");
            return;
        }

        if (!phone.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "please insert valid phone number");
            return;
        }

        try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("UPDATE guests set name=?,phone=?,email=?,city=?,gender=? where G_id=?")) {

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, city);
            stmt.setString(5, gender);
            stmt.setInt(6, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 1) {
                JOptionPane.showMessageDialog(this, "updated Successfully");
                update_guests_table();
                clear();
            } else {
                JOptionPane.showMessageDialog(this, "update Failed");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(this, "An error occurred during update." + ex.getMessage());
        }
    }

    public void delete_guest() {

        DefaultTableModel dft = (DefaultTableModel) table_guest.getModel();
        int selected_row = table_guest.getSelectedRow();

        int id = Integer.parseInt(dft.getValueAt(selected_row, 0).toString());

        int result = JOptionPane.showConfirmDialog(null, "Do you want to delete this guest?", "Warning", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_NO_OPTION) {
            try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("DELETE FROM guests where G_id=?")) {

                stmt.setInt(1, id);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 1) {
                    JOptionPane.showMessageDialog(this, "Deleted Successfully");
                    update_guests_table();
                    clear();
                } else {
                    JOptionPane.showMessageDialog(this, "Delete Failed");
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception
                JOptionPane.showMessageDialog(this, "An error occurred during update." + ex.getMessage());
            }
        }

    }

    public void update_guests_table() {
        int c;

        try (Connection con = DriverManager.getConnection(url, "root", ""); PreparedStatement stmt = con.prepareStatement("SELECT * FROM guests")) {

            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            DefaultTableModel dft = (DefaultTableModel) table_guest.getModel();
            dft.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();
                for (int i = 1; i <= c; i++) {
                    v.add(rs.getString("G_id"));
                    v.add(rs.getString("name"));
                    v.add(rs.getString("phone"));
                    v.add(rs.getString("email"));
                    v.add(rs.getString("city"));
                    v.add(rs.getString("gender"));
                }
                dft.addRow(v);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(this, "An error occurred during Insertion." + ex.getMessage());
        }
    }

    private void clear() {
        txt_name.setText("");
        txt_phone.setText("");
        txt_email.setText("");
        txt_city.setText("");
        txt_name.requestFocus();
        buttonGroup1.clearSelection();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        reservations = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        rooms = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        dashboard = new javax.swing.JPanel();
        label_dashboard = new javax.swing.JLabel();
        guests = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_logout = new rojeru_san.complementos.RSButtonHover();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        tab_dashboard = new javax.swing.JPanel();
        tab_guests = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_city = new app.bolivia.swing.JCTextField();
        txt_email = new app.bolivia.swing.JCTextField();
        txt_phone = new app.bolivia.swing.JCTextField();
        txt_name = new app.bolivia.swing.JCTextField();
        jLabel12 = new javax.swing.JLabel();
        btn_insert = new rojeru_san.complementos.RSButtonHover();
        btn_update = new rojeru_san.complementos.RSButtonHover();
        btn_clear = new rojeru_san.complementos.RSButtonHover();
        jLabel16 = new javax.swing.JLabel();
        radio_female = new javax.swing.JRadioButton();
        radio_male = new javax.swing.JRadioButton();
        btn_remove = new rojeru_san.complementos.RSButtonHover();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_guest = new rojeru_san.complementos.RSTableMetro();
        tab_rooms = new javax.swing.JPanel();
        tab_reservations = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setPreferredSize(new java.awt.Dimension(280, 650));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reservations.setBackground(new java.awt.Color(0, 153, 153));
        reservations.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        reservations.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\booking.png")); // NOI18N
        jLabel5.setText(" Reservations");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        reservations.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 260, 60));

        jPanel1.add(reservations, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 280, 60));

        rooms.setBackground(new java.awt.Color(0, 153, 153));
        rooms.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        rooms.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\room.png")); // NOI18N
        jLabel4.setText(" Rooms");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        rooms.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 260, 60));

        jPanel1.add(rooms, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 280, 60));

        dashboard.setBackground(new java.awt.Color(0, 102, 102));
        dashboard.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label_dashboard.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        label_dashboard.setForeground(new java.awt.Color(255, 255, 255));
        label_dashboard.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\dashboard.png")); // NOI18N
        label_dashboard.setText(" Dashboard");
        label_dashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_dashboardMouseClicked(evt);
            }
        });
        dashboard.add(label_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 260, 60));

        jPanel1.add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 280, 60));

        guests.setBackground(new java.awt.Color(0, 153, 153));
        guests.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        guests.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\tourist.png")); // NOI18N
        jLabel14.setText("Guests");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        guests.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 260, 60));

        jPanel1.add(guests, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 280, 60));

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\check-out.png")); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 560, -1, -1));

        btn_logout.setBackground(new java.awt.Color(0, 153, 153));
        btn_logout.setText("LogOut");
        btn_logout.setColorHover(new java.awt.Color(255, 51, 102));
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });
        jPanel1.add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 280, 50));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\adminIcons\\icons8_Home_26px_2.png")); // NOI18N
        jLabel2.setText("  Home page");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 180, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 280, 600));

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\adminIcons\\icons8_menu_48px_1.png")); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\delete.png")); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 10, 50, 40));

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Hotel Management System");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 490, 50));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 50));

        tab_dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("dashboard", tab_dashboard);

        tab_guests.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setText("Gender");
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 100, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setText("Name");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel10.setText("Email Address");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 100, 50));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel11.setText("Phone Number");
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, 40));
        jPanel7.add(txt_city, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, -1, -1));
        jPanel7.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, -1, -1));
        jPanel7.add(txt_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, -1, -1));
        jPanel7.add(txt_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\guest-list.png")); // NOI18N
        jPanel7.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, -1, -1));

        btn_insert.setBackground(new java.awt.Color(0, 153, 153));
        btn_insert.setText("Add");
        btn_insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertActionPerformed(evt);
            }
        });
        jPanel7.add(btn_insert, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, -1, -1));

        btn_update.setBackground(new java.awt.Color(0, 153, 153));
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel7.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, -1, -1));

        btn_clear.setBackground(new java.awt.Color(0, 153, 153));
        btn_clear.setText("Clear");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        jPanel7.add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel16.setText("City");
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 100, 40));

        buttonGroup1.add(radio_female);
        radio_female.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_female.setText("Female");
        jPanel7.add(radio_female, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, -1, -1));

        buttonGroup1.add(radio_male);
        radio_male.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_male.setText("Male");
        jPanel7.add(radio_male, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, -1, -1));

        btn_remove.setBackground(new java.awt.Color(0, 153, 153));
        btn_remove.setText("Remove");
        btn_remove.setColorHover(new java.awt.Color(255, 0, 0));
        btn_remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removeActionPerformed(evt);
            }
        });
        jPanel7.add(btn_remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, -1, -1));

        tab_guests.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 820, 280));

        table_guest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Guest Id", "Name", "Phone", "Email", "City", "Gender"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_guest.setColorBackgoundHead(new java.awt.Color(0, 153, 153));
        table_guest.setColorSelBackgound(new java.awt.Color(255, 51, 102));
        table_guest.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        table_guest.setRowHeight(30);
        table_guest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_guestMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_guest);

        tab_guests.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 790, 270));

        jTabbedPane1.addTab("guests", tab_guests);

        tab_rooms.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("rooms", tab_rooms);

        tab_reservations.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("reservations", tab_reservations);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 820, 650));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void label_dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_dashboardMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
        Color dark = new Color(0, 102, 102);
        Color light = new Color(0, 153, 153);
        dashboard.setBackground(dark);
        guests.setBackground(light);
        rooms.setBackground(light);
        reservations.setBackground(light);
    }//GEN-LAST:event_label_dashboardMouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
        Color dark = new Color(0, 102, 102);
        Color light = new Color(0, 153, 153);
        dashboard.setBackground(light);
        guests.setBackground(dark);
        rooms.setBackground(light);
        reservations.setBackground(light);
    }//GEN-LAST:event_jLabel14MouseClicked

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        // TODO add your handling code here:
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertActionPerformed
        // TODO add your handling code here:
        insert_guest();
    }//GEN-LAST:event_btn_insertActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
        update_guest();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removeActionPerformed
        // TODO add your handling code here:
        delete_guest();
    }//GEN-LAST:event_btn_removeActionPerformed

    private void table_guestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_guestMouseClicked
        // TODO add your handling code here:
        DefaultTableModel dft = (DefaultTableModel) table_guest.getModel();
        int selected_row = table_guest.getSelectedRow();

        txt_name.setText(dft.getValueAt(selected_row, 1).toString());
        txt_phone.setText(dft.getValueAt(selected_row, 2).toString());
        txt_email.setText(dft.getValueAt(selected_row, 3).toString());
        txt_city.setText(dft.getValueAt(selected_row, 4).toString());
        String gender = dft.getValueAt(selected_row, 5).toString();
        if (gender.equals("Male")) {
            radio_male.setSelected(true);
            //radio_male.doClick();
        } else {
            radio_female.setSelected(true);
            //radio_female.doClick();
        }
    }//GEN-LAST:event_table_guestMouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
        Color dark = new Color(0, 102, 102);
        Color light = new Color(0, 153, 153);
        dashboard.setBackground(light);
        guests.setBackground(light);
        rooms.setBackground(dark);
        reservations.setBackground(light);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
        Color dark = new Color(0, 102, 102);
        Color light = new Color(0, 153, 153);
        dashboard.setBackground(light);
        guests.setBackground(light);
        rooms.setBackground(light);
        reservations.setBackground(dark);
    }//GEN-LAST:event_jLabel5MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Old.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Old.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Old.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Old.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Old().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.complementos.RSButtonHover btn_clear;
    private rojeru_san.complementos.RSButtonHover btn_insert;
    private rojeru_san.complementos.RSButtonHover btn_logout;
    private rojeru_san.complementos.RSButtonHover btn_remove;
    private rojeru_san.complementos.RSButtonHover btn_update;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel guests;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel label_dashboard;
    private javax.swing.JRadioButton radio_female;
    private javax.swing.JRadioButton radio_male;
    private javax.swing.JPanel reservations;
    private javax.swing.JPanel rooms;
    private javax.swing.JPanel tab_dashboard;
    private javax.swing.JPanel tab_guests;
    private javax.swing.JPanel tab_reservations;
    private javax.swing.JPanel tab_rooms;
    private rojeru_san.complementos.RSTableMetro table_guest;
    private app.bolivia.swing.JCTextField txt_city;
    private app.bolivia.swing.JCTextField txt_email;
    private app.bolivia.swing.JCTextField txt_name;
    private app.bolivia.swing.JCTextField txt_phone;
    // End of variables declaration//GEN-END:variables
}
