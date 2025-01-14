/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframes;

import java.sql.*;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author ALAMIA
 */
public class Signup extends javax.swing.JFrame {

    private final String url = "jdbc:mysql://localhost:3306/software_project";
    // for sqlserver >> url =  "jdbc:sqlserver://localhost;databaseName=YourDatabaseName;integratedSecurity=true;encode=false";
    



    public Signup() {
        initComponents();
        signup_background();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Can't Load JDBC Driver !!!");
        }
    }

    public boolean insert_signup(String name, String pwd, String confirm_pwd, String email, String phone) {


        if (name.isEmpty() || pwd.isEmpty() || confirm_pwd.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return false;
        }
        
        if (!pwd.equals(confirm_pwd)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return false;
        }

        if (!email.matches("^.+@.+\\..+$")) {
            JOptionPane.showMessageDialog(this, "please insert valid email");
            return false;
        }
        if (!phone.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "please insert valid phone number");
            return false;
        }

        try (Connection con = DriverManager.getConnection(url,"root",""); PreparedStatement stmt = con.prepareStatement("INSERT INTO users (name, password, email, phone) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, pwd);
            stmt.setString(3, email);
            stmt.setString(4, phone);

            
            //insert   delete  update  
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 1) {
                JOptionPane.showMessageDialog(this, "Registration Successful");
                Login login = new Login();
                login.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            JOptionPane.showMessageDialog(this, "An error occurred during registration." + ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean isExist_users(String username, String email) {

        try {
            Connection con = DriverManager.getConnection(url,"root","");
            PreparedStatement name_stmt = con.prepareStatement("SELECT * FROM users WHERE name = ?");
            name_stmt.setString(1, username);
            PreparedStatement email_stmt = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            email_stmt.setString(1, email);
            
            //select 
            ResultSet rs1 = name_stmt.executeQuery();
            ResultSet rs2 = email_stmt.executeQuery();
            if (rs1.next()) {
                JOptionPane.showMessageDialog(this, "this user already exist");
                txt_username.requestFocus();
                return true;
            }
            if (rs2.next()) {
                JOptionPane.showMessageDialog(this, "this email already exist");
                txt_email.requestFocus();
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;

    }

    public void signup_background() {
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons/hotel3.jpeg");
        Image image = imageIcon.getImage().getScaledInstance(signup_bg.getWidth(), signup_bg.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledicon = new ImageIcon(image);
        signup_bg.setIcon(scaledicon);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        signup_bg = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_phone = new app.bolivia.swing.JCTextField();
        txt_email = new app.bolivia.swing.JCTextField();
        txt_username = new app.bolivia.swing.JCTextField();
        btn_login = new rojerusan.RSMaterialButtonCircle();
        btn_signup = new rojerusan.RSMaterialButtonCircle();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_confirm_password = new rojerusan.RSPasswordTextPlaceHolder();
        txt_password = new rojerusan.RSPasswordTextPlaceHolder();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 650));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\delete.png")); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, 50, 40));

        signup_bg.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\hotel.jpeg")); // NOI18N
        jPanel1.add(signup_bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 740, 570));

        jLabel14.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Hotel Management System");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 310, 50));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Welcome To ");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 110, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 740, -1));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Signup");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 140, 50));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Phone");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 160, 30));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Confirm Password");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 160, 30));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Email");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 160, 30));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Username");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 160, 30));

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Password");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 160, 30));

        txt_phone.setBackground(new java.awt.Color(0, 153, 153));
        txt_phone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_phone.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txt_phone.setPlaceholder("Your Phone Number...");
        txt_phone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_phoneKeyPressed(evt);
            }
        });
        jPanel2.add(txt_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, -1, -1));

        txt_email.setBackground(new java.awt.Color(0, 153, 153));
        txt_email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_email.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txt_email.setPlaceholder("Your Email Address...");
        txt_email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_emailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_emailFocusLost(evt);
            }
        });
        txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_emailKeyPressed(evt);
            }
        });
        jPanel2.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, -1, -1));

        txt_username.setBackground(new java.awt.Color(0, 153, 153));
        txt_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_username.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txt_username.setPlaceholder("Enter your Username...");
        txt_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_usernameFocusLost(evt);
            }
        });
        txt_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_usernameKeyPressed(evt);
            }
        });
        jPanel2.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, -1));

        btn_login.setBackground(new java.awt.Color(255, 255, 255));
        btn_login.setForeground(new java.awt.Color(0, 0, 0));
        btn_login.setText("Login");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });
        jPanel2.add(btn_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 560, 200, 60));

        btn_signup.setText("SignUp");
        btn_signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_signupMouseClicked(evt);
            }
        });
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });
        jPanel2.add(btn_signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 490, 200, 60));

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));
        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\icons8_Google_Mobile_50px.png")); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, -1, -1));

        jLabel9.setBackground(new java.awt.Color(204, 204, 204));
        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\icons8_Secured_Letter_50px.png")); // NOI18N
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\icons8_Secure_50px.png")); // NOI18N
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel11.setBackground(new java.awt.Color(204, 204, 204));
        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\icons8_Secure_50px.png")); // NOI18N
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        jLabel12.setBackground(new java.awt.Color(204, 204, 204));
        jLabel12.setIcon(new javax.swing.ImageIcon("C:\\Users\\ALAMIA\\Desktop\\Software Project\\Library Management System icons-20240416T231307Z-001\\Library Management System icons\\icons\\icons8_Account_50px.png")); // NOI18N
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        txt_confirm_password.setBackground(new java.awt.Color(0, 153, 153));
        txt_confirm_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_confirm_password.setForeground(new java.awt.Color(0, 0, 0));
        txt_confirm_password.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txt_confirm_password.setPhColor(new java.awt.Color(0, 0, 0));
        txt_confirm_password.setPlaceholder("Confirm your Password...");
        txt_confirm_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confirm_passwordKeyPressed(evt);
            }
        });
        jPanel2.add(txt_confirm_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, -1));

        txt_password.setBackground(new java.awt.Color(0, 153, 153));
        txt_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_password.setForeground(new java.awt.Color(0, 0, 0));
        txt_password.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txt_password.setPhColor(new java.awt.Color(0, 0, 0));
        txt_password.setPlaceholder("Enter your Password...");
        txt_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_passwordKeyPressed(evt);
            }
        });
        jPanel2.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 650));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        // TODO add your handling code here:
        new Login().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_loginActionPerformed

    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed
        // TODO add your handling code here:
        insert_signup(txt_username.getText(), txt_password.getText(), txt_confirm_password.getText(), txt_email.getText(), txt_phone.getText());
    }//GEN-LAST:event_btn_signupActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void txt_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusLost
        // TODO add your handling code here:
        isExist_users(txt_username.getText(),txt_email.getText());
    }//GEN-LAST:event_txt_usernameFocusLost

    private void txt_emailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailFocusGained

    private void txt_emailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusLost
        // TODO add your handling code here:
       isExist_users(txt_username.getText(),txt_email.getText());
       
    }//GEN-LAST:event_txt_emailFocusLost

    private void txt_usernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_usernameKeyPressed
        // TODO add your handling code here:
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txt_password.requestFocusInWindow(); 
    }
    }//GEN-LAST:event_txt_usernameKeyPressed

    private void txt_emailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyPressed
        // TODO add your handling code here:
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txt_phone.requestFocusInWindow(); // Move focus to the phone field
    }
    }//GEN-LAST:event_txt_emailKeyPressed

    private void txt_phoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_phoneKeyPressed
        // TODO add your handling code here:
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        insert_signup(txt_username.getText(), txt_password.getText(), txt_confirm_password.getText(), txt_email.getText(), txt_phone.getText());
    }
    }//GEN-LAST:event_txt_phoneKeyPressed

    private void btn_signupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_signupMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_signupMouseClicked

    private void txt_confirm_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confirm_passwordKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_email.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_confirm_passwordKeyPressed

    private void txt_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passwordKeyPressed
        // TODO add your handling code here:
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_confirm_password.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_passwordKeyPressed

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
            java.util.logging.Logger.getLogger(Signup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Signup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Signup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Signup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Signup().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonCircle btn_login;
    private rojerusan.RSMaterialButtonCircle btn_signup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel signup_bg;
    private rojerusan.RSPasswordTextPlaceHolder txt_confirm_password;
    private app.bolivia.swing.JCTextField txt_email;
    private rojerusan.RSPasswordTextPlaceHolder txt_password;
    private app.bolivia.swing.JCTextField txt_phone;
    private app.bolivia.swing.JCTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
