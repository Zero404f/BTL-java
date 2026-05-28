/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.HoaDon;
import Model.DoUong;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dauqu
 */
public class BaoMatFile {
    private static final String ALGORITHM = "AES";
    // Key ma hoa 16 byte - TUYET DOI KHONG DOI SAU KHI DA LUU FILE
    private static final byte[] KEY = "MySecretKey@2026".getBytes(); 
    private static final String FILE_PATH = "Data.txt";
    private static final String MENU_PATH = "Menu.txt"; // File luu danh sach mon

    // ================== QUAN LY HOA DON ==================
    public static void luuDanhSachHoaDon(List<HoaDon> data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            
            SealedObject sealedObject = new SealedObject((Serializable) data, cipher);
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                oos.writeObject(sealedObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<HoaDon> docDanhSachHoaDon() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            SealedObject sealedObject = (SealedObject) ois.readObject();
            
            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            
            return (List<HoaDon>) sealedObject.getObject(cipher);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // ================== QUAN LY MENU (THUC DON) ==================
    public static void luuMenu(List<DoUong> data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            
            SealedObject sealedObject = new SealedObject((Serializable) data, cipher);
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MENU_PATH))) {
                oos.writeObject(sealedObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<DoUong> docMenu() {
        File file = new File(MENU_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            SealedObject sealedObject = (SealedObject) ois.readObject();
            
            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            
            return (List<DoUong>) sealedObject.getObject(cipher);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}