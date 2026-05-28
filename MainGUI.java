/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.*;
import Service.BaoMatFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author dauqu
 */
public class MainGUI extends JFrame {

    // Cac component cua Tab Order
    private DefaultTableModel modelTable;
    private JTable table;
    private JComboBox<DoUong> cboMon;
    private JSpinner spinSL;
    private JLabel lblTongTien;
    
    // Cac component cua Tab Thong ke
    private JLabel lblDoanhThu, lblSoHoaDon, lblMonBanChay;

    // Cac component cua Tab Thuc Don
    private DefaultTableModel modelTableMenu;
    private JTable tableMenu;

    private HoaDon hoaDonHienTai;
    private List<HoaDon> lichSuHoaDon;
    private String role;

    // Data Menu hien tai
    private List<DoUong> danhSachMon;
    private DefaultComboBoxModel<DoUong> comboModelMon;

    public MainGUI(String role) {
        this.role = role;
        setTitle("Phan Mem Quan Ly Quan Cafe - Quyen: " + role);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Load du lieu tu file
        lichSuHoaDon = BaoMatFile.docDanhSachHoaDon();
        danhSachMon = BaoMatFile.docMenu();
        
        // Neu menu trong (chay lan dau), tao data mac dinh va luu file
        if(danhSachMon.isEmpty()) {
            danhSachMon.add(new DoUong("CF01", "Cafe Den", 25000));
            danhSachMon.add(new DoUong("CF02", "Cafe Sua", 30000));
            danhSachMon.add(new DoUong("TS01", "Tra Sua Tran Chau", 45000));
            danhSachMon.add(new DoUong("NC01", "Nuoc Cam Vat", 40000));
            danhSachMon.add(new DoUong("ST01", "Sinh To Bo", 50000));
            BaoMatFile.luuMenu(danhSachMon);
        }
        
        // Khoi tao model cho ComboBox Order
        comboModelMon = new DefaultComboBoxModel<>();
        for (DoUong d : danhSachMon) comboModelMon.addElement(d);

        taoHoaDonMoi();

        // JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        tabbedPane.addTab("QUAN LY ORDER", taoPanelOrder());
        
        if (role.equals("Quan ly")) {
            tabbedPane.addTab("QUAN LY THUC DON", taoPanelThucDon()); // Tab them mon moi
            tabbedPane.addTab("THONG KE DOANH THU", taoPanelThongKe());
            tabbedPane.addChangeListener(e -> capNhatThongKe()); 
        }

        add(tabbedPane);
    }

    private void taoHoaDonMoi() {
        String maHD = "HD-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        hoaDonHienTai = new HoaDon(maHD);
    }

    // ================= TAB 1: BAN HANG (ORDER) =================
    private JPanel taoPanelOrder() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Them Mon Vao Order"));
        
        cboMon = new JComboBox<>(comboModelMon); // Su dung model dong
        spinSL = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        
        JButton btnThem = new JButton("Them Mon");
        btnThem.setBackground(new Color(39, 174, 96));
        btnThem.setForeground(Color.WHITE);

        pnlTop.add(new JLabel("Chon Mon:"));
        pnlTop.add(cboMon);
        pnlTop.add(new JLabel("So Luong:"));
        pnlTop.add(spinSL);
        pnlTop.add(btnThem);

        String[] cols = {"STT", "Ten mon", "So luong", "Don gia", "Thanh tien"};
        modelTable = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(modelTable);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JPanel pnlRight = new JPanel(new GridLayout(6, 1, 10, 10));
        pnlRight.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JButton btnSua = new JButton("Sua So Luong");
        JButton btnXoa = new JButton("Xoa Mon");
        JButton btnThanhToan = new JButton("THANH TOAN & IN");
        btnThanhToan.setBackground(new Color(192, 57, 43));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 14));

        lblTongTien = new JLabel("Tong Tien: 0 VND");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongTien.setForeground(Color.RED);

        pnlRight.add(btnSua);
        pnlRight.add(btnXoa);
        pnlRight.add(new JLabel("")); 
        pnlRight.add(lblTongTien);
        pnlRight.add(btnThanhToan);

        pnl.add(pnlTop, BorderLayout.NORTH);
        pnl.add(new JScrollPane(table), BorderLayout.CENTER);
        pnl.add(pnlRight, BorderLayout.EAST);

        // Su kien
        btnThem.addActionListener(e -> {
            DoUong d = (DoUong) cboMon.getSelectedItem();
            if(d != null) {
                int sl = (int) spinSL.getValue();
                hoaDonHienTai.themMon(d, sl);
                lamMoiBangOrder();
            }
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                hoaDonHienTai.xoaMon(row);
                lamMoiBangOrder();
            } else {
                JOptionPane.showMessageDialog(this, "Chon mot mon trong bang de xoa.");
            }
        });

        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String input = JOptionPane.showInputDialog(this, "Nhap so luong moi:", 
                               hoaDonHienTai.getDs().get(row).getSoLuong());
                try {
                    int sl = Integer.parseInt(input);
                    if (sl > 0) {
                        hoaDonHienTai.capNhatSoLuong(row, sl);
                        lamMoiBangOrder();
                    }
                } catch (Exception ex) {}
            } else {
                JOptionPane.showMessageDialog(this, "Chon mot mon trong bang de sua.");
            }
        });

        btnThanhToan.addActionListener(e -> xulThanhToan());

        return pnl;
    }

    // ================= TAB 2: QUAN LY THUC DON (NEW) =================
    private JPanel taoPanelThucDon() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form nhap lieu
        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thong Tin Mon Moi"));

        JTextField txtMa = new JTextField(7);
        JTextField txtTen = new JTextField(15);
        JTextField txtGia = new JTextField(10);
        
        JButton btnThemMon = new JButton("THEM MON NAY");
        btnThemMon.setBackground(new Color(41, 128, 185));
        btnThemMon.setForeground(Color.WHITE);

        pnlInput.add(new JLabel("Ma mon:")); pnlInput.add(txtMa);
        pnlInput.add(new JLabel("Ten mon:")); pnlInput.add(txtTen);
        pnlInput.add(new JLabel("Gia (VND):")); pnlInput.add(txtGia);
        pnlInput.add(btnThemMon);

        // Bang danh sach mon hien tai
        String[] colsMenu = {"Ma Mon", "Ten Mon", "Gia Tien"};
        modelTableMenu = new DefaultTableModel(colsMenu, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableMenu = new JTable(modelTableMenu);
        tableMenu.setRowHeight(25);
        tableMenu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        lamMoiBangMenu();

        JPanel pnlRightMenu = new JPanel(new FlowLayout());
        JButton btnXoaMon = new JButton("Xoa Mon Da Chon");
        btnXoaMon.setBackground(Color.RED);
        btnXoaMon.setForeground(Color.WHITE);
        pnlRightMenu.add(btnXoaMon);

        pnl.add(pnlInput, BorderLayout.NORTH);
        pnl.add(new JScrollPane(tableMenu), BorderLayout.CENTER);
        pnl.add(pnlRightMenu, BorderLayout.SOUTH);

        // Su kien Them Mon Vao Menu
        btnThemMon.addActionListener(e -> {
            String ma = txtMa.getText().trim();
            String ten = txtTen.getText().trim();
            String giaStr = txtGia.getText().trim();

            if(ma.isEmpty() || ten.isEmpty() || giaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long nhap du thong tin!");
                return;
            }
            try {
                double gia = Double.parseDouble(giaStr);
                DoUong monMoi = new DoUong(ma, ten, gia);
                
                // Cap nhat List, ComboBox va Table
                danhSachMon.add(monMoi);
                comboModelMon.addElement(monMoi);
                lamMoiBangMenu();
                
                // Luu vao File Menu.txt
                BaoMatFile.luuMenu(danhSachMon);
                
                txtMa.setText(""); txtTen.setText(""); txtGia.setText("");
                JOptionPane.showMessageDialog(this, "Them mon thanh cong!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Gia tien phai la so!");
            }
        });

        // Su kien Xoa Mon Khoi Menu
        btnXoaMon.addActionListener(e -> {
            int row = tableMenu.getSelectedRow();
            if(row != -1) {
                DoUong monCanXoa = danhSachMon.get(row);
                danhSachMon.remove(row);
                comboModelMon.removeElement(monCanXoa); // Xoa khoi Order
                lamMoiBangMenu();
                
                // Cap nhat lai file
                BaoMatFile.luuMenu(danhSachMon);
            } else {
                JOptionPane.showMessageDialog(this, "Chon mot mon trong bang de xoa.");
            }
        });

        return pnl;
    }

    // ================= TAB 3: THONG KE =================
    private JPanel taoPanelThongKe() {
        JPanel pnl = new JPanel(new GridLayout(3, 1, 20, 20));
        pnl.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        lblDoanhThu = new JLabel("Doanh thu tong: 0 VND");
        lblSoHoaDon = new JLabel("Tong so hoa don: 0");
        lblMonBanChay = new JLabel("Mon ban chay nhat: Chua co");

        Font fontStats = new Font("Arial", Font.BOLD, 22);
        lblDoanhThu.setFont(fontStats); lblDoanhThu.setForeground(Color.BLUE);
        lblSoHoaDon.setFont(fontStats);
        lblMonBanChay.setFont(fontStats);

        pnl.add(lblDoanhThu);
        pnl.add(lblSoHoaDon);
        pnl.add(lblMonBanChay);

        return pnl;
    }

    // ================= LOGIC FUNCTIONS =================
    private void lamMoiBangOrder() {
        modelTable.setRowCount(0);
        int stt = 1;
        for (ChiTietHoaDon ct : hoaDonHienTai.getDs()) {
            modelTable.addRow(new Object[]{
                    stt++,
                    ct.getDoUong().getTen(),
                    ct.getSoLuong(),
                    String.format("%,.0f", ct.getDoUong().getGia()),
                    String.format("%,.0f", ct.thanhTien())
            });
        }
        lblTongTien.setText("Tong Tien: " + String.format("%,.0f", hoaDonHienTai.tongTien()) + " d");
    }

    private void lamMoiBangMenu() {
        modelTableMenu.setRowCount(0);
        for(DoUong d : danhSachMon) {
            modelTableMenu.addRow(new Object[]{ d.getTen(), d.getTen(), String.format("%,.0f", d.getGia()) }); // Trick hien thi do thuoc tinh 'ma' dang bi private
        }
    }

    private void xulThanhToan() {
        if (hoaDonHienTai.getDs().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chua co mon nao de thanh toan!");
            return;
        }

        StringBuilder bill = new StringBuilder();
        bill.append("========== QUAN CAFE PRO ==========\n");
        bill.append("Ma HD: ").append(hoaDonHienTai.getMaHD()).append("\n");
        bill.append("-----------------------------------\n");
        for (ChiTietHoaDon ct : hoaDonHienTai.getDs()) {
            bill.append(String.format("%-15s x%d  %,.0f\n", 
                    ct.getDoUong().getTen(), ct.getSoLuong(), ct.thanhTien()));
        }
        bill.append("-----------------------------------\n");
        bill.append("TONG TIEN: ").append(String.format("%,.0f", hoaDonHienTai.tongTien())).append(" VND\n");
        bill.append("===================================\n");

        JTextArea txtBill = new JTextArea(bill.toString());
        txtBill.setEditable(false);
        txtBill.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        JOptionPane.showMessageDialog(this, new JScrollPane(txtBill), "In Hoa Don", JOptionPane.INFORMATION_MESSAGE);

        lichSuHoaDon.add(hoaDonHienTai);
        BaoMatFile.luuDanhSachHoaDon(lichSuHoaDon);

        taoHoaDonMoi();
        lamMoiBangOrder();
        spinSL.setValue(1);
    }

    private void capNhatThongKe() {
        if (lichSuHoaDon == null || lichSuHoaDon.isEmpty()) return;

        double tongTien = 0;
        int soHD = lichSuHoaDon.size();
        Map<String, Integer> tanSuatMon = new HashMap<>();

        for (HoaDon hd : lichSuHoaDon) {
            tongTien += hd.tongTien();
            for (ChiTietHoaDon ct : hd.getDs()) {
                String tenMon = ct.getDoUong().getTen();
                tanSuatMon.put(tenMon, tanSuatMon.getOrDefault(tenMon, 0) + ct.getSoLuong());
            }
        }

        String monBestSeller = "";
        int maxSl = 0;
        for (Map.Entry<String, Integer> entry : tanSuatMon.entrySet()) {
            if (entry.getValue() > maxSl) {
                maxSl = entry.getValue();
                monBestSeller = entry.getKey();
            }
        }

        lblDoanhThu.setText("Doanh thu tong: " + String.format("%,.0f", tongTien) + " VND");
        lblSoHoaDon.setText("Tong so hoa don: " + soHD);
        lblMonBanChay.setText(maxSl > 0 ? "Mon ban chay nhat: " + monBestSeller + " (" + maxSl + " ly)" :
            "Mon ban chay nhat: Chua co");
    }
}