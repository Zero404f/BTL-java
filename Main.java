/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import View.FormDangNhap;
import javax.swing.UIManager;

/**
 *
 * @author dauqu
 */
public class Main {
    public static void main(String[] args) {
        // Thiet lap giao dien Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Neu may tinh khong ho tro Nimbus thi tu roi ve giao dien mac dinh cua he dieu hanh
            System.out.println("Khong the load giao dien Nimbus.");
        }

        // Khoi chay ung dung va hien thi Form Dang Nhap
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDangNhap().setVisible(true);
            }
        });
    }
}