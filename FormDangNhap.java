/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author dauqu
 */
public class FormDangNhap extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;

    public FormDangNhap() {
        setTitle("Dang nhap he thong - Cafe Pro");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Dong User
        JPanel pnlUser = new JPanel(new BorderLayout(10, 10));
        pnlUser.add(new JLabel("Tai khoan:"), BorderLayout.WEST);
        txtUser = new JTextField("admin");
        pnlUser.add(txtUser, BorderLayout.CENTER);

        // Dong Pass
        JPanel pnlPass = new JPanel(new BorderLayout(10, 10));
        pnlPass.add(new JLabel("Mat khau: "), BorderLayout.WEST);
        txtPass = new JPasswordField("12345");
        pnlPass.add(txtPass, BorderLayout.CENTER);

        // Nut Login
        JButton btnLogin = new JButton("DANG NHAP");
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnLogin.addActionListener(e -> login());

        mainPanel.add(pnlUser);
        mainPanel.add(pnlPass);
        mainPanel.add(btnLogin);

        add(mainPanel);
    }

    private void login() {
        String u = txtUser.getText();
        String p = new String(txtPass.getPassword());

        if (u.equals("admin") && p.equals("12345")) {
            JOptionPane.showMessageDialog(this, "Dang nhap thanh cong (Quyen: Quan ly)");
            new MainGUI("Quan ly").setVisible(true);
            this.dispose();
        } else if (u.equals("nhanvien") && p.equals("123")) {
            JOptionPane.showMessageDialog(this, "Dang nhap thanh cong (Quyen: Nhan vien)");
            new MainGUI("Nhan vien").setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tai khoan hoac mat khau!", "Loi", JOptionPane.ERROR_MESSAGE);
        }
    }
}