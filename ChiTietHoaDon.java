/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.io.Serializable;
/**
 *
 * @author dauqu
 */
public class ChiTietHoaDon implements Serializable {
    private DoUong doUong;
    private int soLuong;

    public ChiTietHoaDon(DoUong doUong, int soLuong) {
        this.doUong = doUong;
        this.soLuong = soLuong;
    }

    public DoUong getDoUong() { return doUong; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    
    public double thanhTien() { return doUong.getGia() * soLuong; }
}