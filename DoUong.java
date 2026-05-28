package Model;
import java.io.Serializable;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dauqu
 */
public class DoUong implements Serializable {
    private String ma;
    private String ten;
    private double gia;

    public DoUong(String ma, String ten, double gia) {
        this.ma = ma;
        this.ten = ten;
        this.gia = gia;
    }

    public String getTen() { return ten; }
    public double getGia() { return gia; }
    
    @Override
    public String toString() { return ten; }
}
