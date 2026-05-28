/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import Model.DoUong;
import Model.ChiTietHoaDon;
import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @author dauqu
 */
public class HoaDon implements Serializable {
    private String maHD;
    private long thoiGian;
    private ArrayList<ChiTietHoaDon> ds;

    public HoaDon(String maHD) {
        this.maHD = maHD;
        this.thoiGian = System.currentTimeMillis();
        this.ds = new ArrayList<>();
    }

    public void themMon(DoUong d, int sl) {
        // Neu mon da co, cong don so luong
        for (ChiTietHoaDon ct : ds) {
            if (ct.getDoUong().getTen().equals(d.getTen())) {
                ct.setSoLuong(ct.getSoLuong() + sl);
                return;
            }
        }
        ds.add(new ChiTietHoaDon(d, sl));
    }
    
    public void xoaMon(int index) { ds.remove(index); }
    public void capNhatSoLuong(int index, int sl) { ds.get(index).setSoLuong(sl); }

    public double tongTien() {
        return ds.stream().mapToDouble(ChiTietHoaDon::thanhTien).sum();
    }

    public ArrayList<ChiTietHoaDon> getDs() { return ds; }
    public String getMaHD() { return maHD; }
}