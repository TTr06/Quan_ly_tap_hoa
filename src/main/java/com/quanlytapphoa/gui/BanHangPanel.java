package com.quanlytapphoa.gui;

import com.quanlytapphoa.bus.BusinessException;
import com.quanlytapphoa.model.ChiTietHoaDon;
import com.quanlytapphoa.model.HoaDonBanHang;
import com.quanlytapphoa.model.KhuyenMai;
import com.quanlytapphoa.model.MatHang;
import com.quanlytapphoa.model.NhanVien;
import com.quanlytapphoa.model.PhuongThucThanhToan;
import com.quanlytapphoa.model.TaiKhoan;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

public class BanHangPanel extends JPanel {
    private final JTextField txtTimKiem = new JTextField(24);
    private final JSpinner spnSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100000, 1));
    private final JComboBox<PhuongThucItem> cboPhuongThuc = new JComboBox<PhuongThucItem>();
    private final JLabel lblTongTien = new JLabel("0");
    private final JLabel lblGiamGia = new JLabel("0");
    private final JLabel lblThanhTien = new JLabel("0");
    private final ReadOnlyTableModel sanPhamModel = new ReadOnlyTableModel(
            "Ma hang", "Ten mat hang", "Don vi", "Gia ban", "Ton kho", "Khuyen mai"
    );
    private final ReadOnlyTableModel gioHangModel = new ReadOnlyTableModel(
            "Ma hang", "Ten mat hang", "So luong", "Don gia", "Giam gia", "Thanh tien"
    );
    private final JTable tblSanPham = new JTable(sanPhamModel);
    private final JTable tblGioHang = new JTable(gioHangModel);
    private String maHoaDonCuoi;

    public BanHangPanel() {
        setLayout(new BorderLayout(8, 8));
        taoGiaoDien();
        napPhuongThucThanhToan();
        taiSanPham(GuiContext.matHangBUS().layDanhSach());
        capNhatTongTien();
    }

    private void taoGiaoDien() {
        JPanel timKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTim = new JButton("Tim san pham");
        JButton btnThem = new JButton("Them vao gio");
        timKiem.add(new JLabel("Tu khoa:"));
        timKiem.add(txtTimKiem);
        timKiem.add(btnTim);
        timKiem.add(new JLabel("So luong:"));
        timKiem.add(spnSoLuong);
        timKiem.add(btnThem);
        add(timKiem, BorderLayout.NORTH);

        tblSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblSanPham.setAutoCreateRowSorter(true);
        tblGioHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblGioHang.setAutoCreateRowSorter(true);

        JPanel sanPham = new JPanel(new BorderLayout(4, 4));
        sanPham.add(new JLabel("DANH SACH SAN PHAM"), BorderLayout.NORTH);
        sanPham.add(new JScrollPane(tblSanPham), BorderLayout.CENTER);
        JPanel gioHang = new JPanel(new BorderLayout(4, 4));
        gioHang.add(new JLabel("GIO HANG"), BorderLayout.NORTH);
        gioHang.add(new JScrollPane(tblGioHang), BorderLayout.CENTER);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sanPham, gioHang);
        split.setResizeWeight(0.5);
        add(split, BorderLayout.CENTER);

        JPanel phanCuoi = new JPanel(new GridLayout(0, 1, 4, 4));
        JPanel gioHangActions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCapNhat = new JButton("Cap nhat so luong");
        JButton btnXoa = new JButton("Xoa khoi gio");
        JButton btnXoaHet = new JButton("Xoa het");
        gioHangActions.add(btnCapNhat);
        gioHangActions.add(btnXoa);
        gioHangActions.add(btnXoaHet);
        phanCuoi.add(gioHangActions);

        JPanel tongTien = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tongTien.add(new JLabel("Tong tien:"));
        tongTien.add(lblTongTien);
        tongTien.add(new JLabel("  Giam gia:"));
        tongTien.add(lblGiamGia);
        tongTien.add(new JLabel("  Thanh tien:"));
        tongTien.add(lblThanhTien);
        phanCuoi.add(tongTien);

        JPanel thanhToan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnThanhToan = new JButton("Thanh toan");
        JButton btnIn = new JButton("In hoa don cuoi");
        thanhToan.add(new JLabel("Phuong thuc:"));
        thanhToan.add(cboPhuongThuc);
        thanhToan.add(btnThanhToan);
        thanhToan.add(btnIn);
        phanCuoi.add(thanhToan);
        add(phanCuoi, BorderLayout.SOUTH);

        btnTim.addActionListener(e -> timSanPham());
        txtTimKiem.addActionListener(e -> timSanPham());
        btnThem.addActionListener(e -> themVaoGio());
        btnCapNhat.addActionListener(e -> capNhatSoLuong());
        btnXoa.addActionListener(e -> xoaKhoiGio());
        btnXoaHet.addActionListener(e -> xoaHetGioHang());
        btnThanhToan.addActionListener(e -> thanhToan());
        btnIn.addActionListener(e -> inHoaDonCuoi());
        tblGioHang.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = GuiUtils.selectedModelRow(tblGioHang);
                if (row >= 0) {
                    spnSoLuong.setValue(gioHangModel.getValueAt(row, 2));
                }
            }
        });
    }

    private void napPhuongThucThanhToan() {
        cboPhuongThuc.removeAllItems();
        for (PhuongThucThanhToan phuongThuc : GuiContext.phuongThucThanhToanBUS().layDanhSachDangHoatDong()) {
            cboPhuongThuc.addItem(new PhuongThucItem(phuongThuc));
        }
    }

    private void timSanPham() {
        taiSanPham(GuiContext.matHangBUS().timKiemSanPham(GuiUtils.text(txtTimKiem)));
    }

    private void taiSanPham(List<MatHang> danhSach) {
        sanPhamModel.setRowCount(0);
        for (MatHang matHang : danhSach) {
            if (!matHang.isTrangThai()) {
                continue;
            }
            sanPhamModel.addRow(new Object[]{
                matHang.getMaMatHang(), matHang.getTenMatHang(), matHang.getDonViTinh(),
                matHang.getGiaBan(), matHang.getSoLuong(), matHang.getMaKM()
            });
        }
    }

    private void themVaoGio() {
        try {
            int row = GuiUtils.selectedModelRow(tblSanPham);
            if (row < 0) {
                throw new BusinessException("Hay chon san pham can them");
            }
            String maMatHang = String.valueOf(sanPhamModel.getValueAt(row, 0));
            int soLuongThem = ((Number) spnSoLuong.getValue()).intValue();
            int dongGio = timDongGioHang(maMatHang);
            int soLuongMoi = soLuongThem + (dongGio < 0 ? 0 : ((Number) gioHangModel.getValueAt(dongGio, 2)).intValue());
            capNhatDongGioHang(maMatHang, soLuongMoi, dongGio);
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void capNhatSoLuong() {
        try {
            int row = GuiUtils.selectedModelRow(tblGioHang);
            if (row < 0) {
                throw new BusinessException("Hay chon dong trong gio hang");
            }
            String maMatHang = String.valueOf(gioHangModel.getValueAt(row, 0));
            capNhatDongGioHang(maMatHang, ((Number) spnSoLuong.getValue()).intValue(), row);
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void capNhatDongGioHang(String maMatHang, int soLuong, int row) {
        MatHang matHang = GuiContext.matHangBUS().timTheoMa(maMatHang);
        if (matHang == null || !matHang.isTrangThai()) {
            throw new BusinessException("San pham khong con kinh doanh");
        }
        if (!GuiContext.matHangBUS().duTonKho(maMatHang, soLuong)) {
            throw new BusinessException("So luong vuot qua ton kho");
        }
        double tong = matHang.getGiaBan() * soLuong;
        KhuyenMai khuyenMai = matHang.getKhuyenMai();
        if (khuyenMai == null && matHang.getMaKM() != null) {
            khuyenMai = GuiContext.khuyenMaiBUS().timTheoMa(matHang.getMaKM());
        }
        double giam = GuiContext.khuyenMaiBUS().tinhTienGiam(khuyenMai, tong, new Date());
        Object[] data = {matHang.getMaMatHang(), matHang.getTenMatHang(), soLuong, matHang.getGiaBan(), giam, tong - giam};
        if (row < 0) {
            gioHangModel.addRow(data);
        } else {
            for (int column = 0; column < data.length; column++) {
                gioHangModel.setValueAt(data[column], row, column);
            }
        }
        capNhatTongTien();
    }

    private int timDongGioHang(String maMatHang) {
        for (int row = 0; row < gioHangModel.getRowCount(); row++) {
            if (maMatHang.equalsIgnoreCase(String.valueOf(gioHangModel.getValueAt(row, 0)))) {
                return row;
            }
        }
        return -1;
    }

    private void xoaKhoiGio() {
        int row = GuiUtils.selectedModelRow(tblGioHang);
        if (row >= 0) {
            gioHangModel.removeRow(row);
            capNhatTongTien();
        }
    }

    private void xoaHetGioHang() {
        gioHangModel.setRowCount(0);
        capNhatTongTien();
    }

    private void capNhatTongTien() {
        double tong = 0;
        double giam = 0;
        for (int row = 0; row < gioHangModel.getRowCount(); row++) {
            tong += ((Number) gioHangModel.getValueAt(row, 2)).intValue()
                    * ((Number) gioHangModel.getValueAt(row, 3)).doubleValue();
            giam += ((Number) gioHangModel.getValueAt(row, 4)).doubleValue();
        }
        lblTongTien.setText(GuiUtils.formatMoney(tong));
        lblGiamGia.setText(GuiUtils.formatMoney(giam));
        lblThanhTien.setText(GuiUtils.formatMoney(tong - giam));
    }

    private void thanhToan() {
        try {
            if (gioHangModel.getRowCount() == 0) {
                throw new BusinessException("Gio hang dang rong");
            }
            PhuongThucItem item = (PhuongThucItem) cboPhuongThuc.getSelectedItem();
            if (item == null) {
                throw new BusinessException("Chua co phuong thuc thanh toan hop le");
            }
            String maHD = taoMa("HD");
            HoaDonBanHang hoaDon = GuiContext.hoaDonBUS().taoHoaDon(
                    maHD, layMaNhanVienHienTai(), item.phuongThuc.getMaPT(), taoChiTietHoaDon()
            );
            maHoaDonCuoi = hoaDon.getMaHD();
            xoaHetGioHang();
            taiSanPham(GuiContext.matHangBUS().layDanhSach());
            hienThiNoiDung("Hoa don " + maHoaDonCuoi, GuiContext.hoaDonBUS().inHoaDon(maHoaDonCuoi));
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private List<ChiTietHoaDon> taoChiTietHoaDon() {
        List<ChiTietHoaDon> chiTiet = new ArrayList<ChiTietHoaDon>();
        for (int row = 0; row < gioHangModel.getRowCount(); row++) {
            MatHang matHang = GuiContext.matHangBUS().timTheoMa(String.valueOf(gioHangModel.getValueAt(row, 0)));
            int soLuong = ((Number) gioHangModel.getValueAt(row, 2)).intValue();
            chiTiet.add(new ChiTietHoaDon(matHang, soLuong));
        }
        return chiTiet;
    }

    private String layMaNhanVienHienTai() {
        TaiKhoan taiKhoan = GuiContext.authBUS().getTaiKhoanHienTai();
        if (taiKhoan != null && taiKhoan.getMaNV() != null && taiKhoan.getMaNV().trim().length() > 0) {
            return taiKhoan.getMaNV();
        }
        for (NhanVien nhanVien : GuiContext.nhanVienBUS().layDanhSach()) {
            if (nhanVien.isTrangThai()) {
                return nhanVien.getMaNV();
            }
        }
        throw new BusinessException("Khong co nhan vien dang hoat dong");
    }

    private void inHoaDonCuoi() {
        try {
            if (maHoaDonCuoi == null) {
                throw new BusinessException("Chua co hoa don nao vua tao");
            }
            hienThiNoiDung("Hoa don " + maHoaDonCuoi, GuiContext.hoaDonBUS().inHoaDon(maHoaDonCuoi));
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private String taoMa(String prefix) {
        return prefix + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    private void hienThiNoiDung(String tieuDe, String noiDung) {
        JTextArea area = new JTextArea(noiDung, 20, 55);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), tieuDe, JOptionPane.INFORMATION_MESSAGE);
    }

    private static final class PhuongThucItem {
        private final PhuongThucThanhToan phuongThuc;

        private PhuongThucItem(PhuongThucThanhToan phuongThuc) {
            this.phuongThuc = phuongThuc;
        }

        @Override
        public String toString() {
            return phuongThuc.getMaPT() + " - " + phuongThuc.getTenPT();
        }
    }
}
