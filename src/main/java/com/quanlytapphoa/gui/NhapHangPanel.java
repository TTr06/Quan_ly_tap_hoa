package com.quanlytapphoa.gui;

import com.quanlytapphoa.bus.BusinessException;
import com.quanlytapphoa.model.ChiTietNhapHang;
import com.quanlytapphoa.model.MatHang;
import com.quanlytapphoa.model.NhaCungCap;
import com.quanlytapphoa.model.NhanVien;
import com.quanlytapphoa.model.PhieuNhapHang;
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

public class NhapHangPanel extends JPanel {
    private final JTextField txtMaPhieu = new JTextField(16);
    private final JTextField txtNgayNhap = new JTextField(10);
    private final JTextField txtMaNhanVien = new JTextField(10);
    private final JComboBox<NhaCungCapItem> cboNhaCungCap = new JComboBox<NhaCungCapItem>();
    private final JTextField txtTimSanPham = new JTextField(20);
    private final JTextField txtDonGiaNhap = new JTextField(10);
    private final JSpinner spnSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100000, 1));
    private final JLabel lblTongTien = new JLabel("0");
    private final ReadOnlyTableModel sanPhamModel = new ReadOnlyTableModel(
            "Ma hang", "Ten mat hang", "Don vi", "Gia nhap hien tai", "Ton kho"
    );
    private final ReadOnlyTableModel chiTietModel = new ReadOnlyTableModel(
            "Ma hang", "Ten mat hang", "So luong", "Don gia nhap", "Thanh tien"
    );
    private final JTable tblSanPham = new JTable(sanPhamModel);
    private final JTable tblChiTiet = new JTable(chiTietModel);

    public NhapHangPanel() {
        setLayout(new BorderLayout(8, 8));
        taoGiaoDien();
        napNhaCungCap();
        taiSanPham(GuiContext.matHangBUS().layDanhSach());
        lamMoiPhieu();
    }

    private void taoGiaoDien() {
        JPanel phanDau = new JPanel(new GridLayout(0, 1, 4, 4));
        JPanel thongTinPhieu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtNgayNhap.setEditable(false);
        txtMaNhanVien.setEditable(false);
        thongTinPhieu.add(new JLabel("Ma phieu:"));
        thongTinPhieu.add(txtMaPhieu);
        thongTinPhieu.add(new JLabel("Ngay nhap:"));
        thongTinPhieu.add(txtNgayNhap);
        thongTinPhieu.add(new JLabel("Ma NV:"));
        thongTinPhieu.add(txtMaNhanVien);
        thongTinPhieu.add(new JLabel("Nha cung cap:"));
        thongTinPhieu.add(cboNhaCungCap);
        phanDau.add(thongTinPhieu);

        JPanel timSanPham = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTim = new JButton("Tim san pham");
        timSanPham.add(new JLabel("Tu khoa:"));
        timSanPham.add(txtTimSanPham);
        timSanPham.add(btnTim);
        timSanPham.add(new JLabel("So luong:"));
        timSanPham.add(spnSoLuong);
        timSanPham.add(new JLabel("Don gia nhap:"));
        timSanPham.add(txtDonGiaNhap);
        phanDau.add(timSanPham);
        add(phanDau, BorderLayout.NORTH);

        tblSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblSanPham.setAutoCreateRowSorter(true);
        tblChiTiet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblChiTiet.setAutoCreateRowSorter(true);
        JPanel sanPham = new JPanel(new BorderLayout(4, 4));
        sanPham.add(new JLabel("DANH SACH SAN PHAM"), BorderLayout.NORTH);
        sanPham.add(new JScrollPane(tblSanPham), BorderLayout.CENTER);
        JPanel chiTiet = new JPanel(new BorderLayout(4, 4));
        chiTiet.add(new JLabel("CHI TIET PHIEU NHAP"), BorderLayout.NORTH);
        chiTiet.add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sanPham, chiTiet);
        split.setResizeWeight(0.5);
        add(split, BorderLayout.CENTER);

        JPanel phanCuoi = new JPanel(new GridLayout(0, 1, 4, 4));
        JPanel chiTietActions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThem = new JButton("Them chi tiet");
        JButton btnSua = new JButton("Cap nhat chi tiet");
        JButton btnXoa = new JButton("Xoa chi tiet");
        JButton btnXoaHet = new JButton("Xoa het");
        chiTietActions.add(btnThem);
        chiTietActions.add(btnSua);
        chiTietActions.add(btnXoa);
        chiTietActions.add(btnXoaHet);
        phanCuoi.add(chiTietActions);
        JPanel luuPhieu = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLuu = new JButton("Luu phieu nhap");
        JButton btnMoi = new JButton("Phieu moi");
        luuPhieu.add(new JLabel("Tong tien:"));
        luuPhieu.add(lblTongTien);
        luuPhieu.add(btnLuu);
        luuPhieu.add(btnMoi);
        phanCuoi.add(luuPhieu);
        add(phanCuoi, BorderLayout.SOUTH);

        btnTim.addActionListener(e -> timSanPham());
        txtTimSanPham.addActionListener(e -> timSanPham());
        btnThem.addActionListener(e -> themChiTiet());
        btnSua.addActionListener(e -> capNhatChiTiet());
        btnXoa.addActionListener(e -> xoaChiTiet());
        btnXoaHet.addActionListener(e -> {
            chiTietModel.setRowCount(0);
            capNhatTongTien();
        });
        btnLuu.addActionListener(e -> luuPhieuNhap());
        btnMoi.addActionListener(e -> lamMoiPhieu());
        tblSanPham.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = GuiUtils.selectedModelRow(tblSanPham);
                if (row >= 0) {
                    txtDonGiaNhap.setText(String.valueOf(sanPhamModel.getValueAt(row, 3)));
                    spnSoLuong.setValue(1);
                }
            }
        });
        tblChiTiet.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = GuiUtils.selectedModelRow(tblChiTiet);
                if (row >= 0) {
                    spnSoLuong.setValue(chiTietModel.getValueAt(row, 2));
                    txtDonGiaNhap.setText(String.valueOf(chiTietModel.getValueAt(row, 3)));
                }
            }
        });
    }

    private void napNhaCungCap() {
        cboNhaCungCap.removeAllItems();
        for (NhaCungCap nhaCungCap : GuiContext.nhaCungCapBUS().layDanhSach()) {
            cboNhaCungCap.addItem(new NhaCungCapItem(nhaCungCap));
        }
    }

    private void timSanPham() {
        taiSanPham(GuiContext.matHangBUS().timKiemSanPham(GuiUtils.text(txtTimSanPham)));
    }

    private void taiSanPham(List<MatHang> danhSach) {
        sanPhamModel.setRowCount(0);
        for (MatHang matHang : danhSach) {
            sanPhamModel.addRow(new Object[]{
                matHang.getMaMatHang(), matHang.getTenMatHang(), matHang.getDonViTinh(),
                matHang.getGiaNhap(), matHang.getSoLuong()
            });
        }
    }

    private void themChiTiet() {
        try {
            int row = GuiUtils.selectedModelRow(tblSanPham);
            if (row < 0) {
                throw new BusinessException("Hay chon san pham can nhap");
            }
            String maMatHang = String.valueOf(sanPhamModel.getValueAt(row, 0));
            int dongChiTiet = timDongChiTiet(maMatHang);
            int soLuongThem = ((Number) spnSoLuong.getValue()).intValue();
            int soLuongMoi = soLuongThem + (dongChiTiet < 0 ? 0 : ((Number) chiTietModel.getValueAt(dongChiTiet, 2)).intValue());
            capNhatDongChiTiet(maMatHang, soLuongMoi, docDonGia(), dongChiTiet);
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void capNhatChiTiet() {
        try {
            int row = GuiUtils.selectedModelRow(tblChiTiet);
            if (row < 0) {
                throw new BusinessException("Hay chon chi tiet can cap nhat");
            }
            String maMatHang = String.valueOf(chiTietModel.getValueAt(row, 0));
            capNhatDongChiTiet(maMatHang, ((Number) spnSoLuong.getValue()).intValue(), docDonGia(), row);
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private double docDonGia() {
        return GuiUtils.parseDouble(GuiUtils.text(txtDonGiaNhap), "Don gia nhap");
    }

    private void capNhatDongChiTiet(String maMatHang, int soLuong, double donGia, int row) {
        MatHang matHang = GuiContext.matHangBUS().timTheoMa(maMatHang);
        if (matHang == null) {
            throw new BusinessException("Khong tim thay san pham");
        }
        if (soLuong <= 0 || donGia < 0) {
            throw new BusinessException("So luong va don gia khong hop le");
        }
        Object[] data = {matHang.getMaMatHang(), matHang.getTenMatHang(), soLuong, donGia, soLuong * donGia};
        if (row < 0) {
            chiTietModel.addRow(data);
        } else {
            for (int column = 0; column < data.length; column++) {
                chiTietModel.setValueAt(data[column], row, column);
            }
        }
        capNhatTongTien();
    }

    private int timDongChiTiet(String maMatHang) {
        for (int row = 0; row < chiTietModel.getRowCount(); row++) {
            if (maMatHang.equalsIgnoreCase(String.valueOf(chiTietModel.getValueAt(row, 0)))) {
                return row;
            }
        }
        return -1;
    }

    private void xoaChiTiet() {
        int row = GuiUtils.selectedModelRow(tblChiTiet);
        if (row >= 0) {
            chiTietModel.removeRow(row);
            capNhatTongTien();
        }
    }

    private void capNhatTongTien() {
        double tong = 0;
        for (int row = 0; row < chiTietModel.getRowCount(); row++) {
            tong += ((Number) chiTietModel.getValueAt(row, 4)).doubleValue();
        }
        lblTongTien.setText(GuiUtils.formatMoney(tong));
    }

    private void luuPhieuNhap() {
        try {
            NhaCungCapItem item = (NhaCungCapItem) cboNhaCungCap.getSelectedItem();
            if (item == null) {
                throw new BusinessException("Chua co nha cung cap");
            }
            PhieuNhapHang phieu = GuiContext.phieuNhapBUS().taoPhieuNhap(
                    GuiUtils.text(txtMaPhieu), item.nhaCungCap.getMaNCC(),
                    GuiUtils.text(txtMaNhanVien), taoChiTietNhapHang()
            );
            String noiDung = GuiContext.phieuNhapBUS().chiTietPhieuNhap(phieu.getMaPhieu());
            lamMoiPhieu();
            taiSanPham(GuiContext.matHangBUS().layDanhSach());
            hienThiNoiDung("Phieu nhap " + phieu.getMaPhieu(), noiDung);
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private List<ChiTietNhapHang> taoChiTietNhapHang() {
        List<ChiTietNhapHang> chiTiet = new ArrayList<ChiTietNhapHang>();
        for (int row = 0; row < chiTietModel.getRowCount(); row++) {
            MatHang matHang = GuiContext.matHangBUS().timTheoMa(String.valueOf(chiTietModel.getValueAt(row, 0)));
            int soLuong = ((Number) chiTietModel.getValueAt(row, 2)).intValue();
            double donGia = ((Number) chiTietModel.getValueAt(row, 3)).doubleValue();
            chiTiet.add(new ChiTietNhapHang(matHang, soLuong, donGia));
        }
        return chiTiet;
    }

    private void lamMoiPhieu() {
        txtMaPhieu.setText(taoMa("PN"));
        txtNgayNhap.setText(GuiUtils.formatDate(new Date()));
        try {
            txtMaNhanVien.setText(layMaNhanVienHienTai());
        } catch (Exception ex) {
            txtMaNhanVien.setText("");
        }
        txtTimSanPham.setText("");
        txtDonGiaNhap.setText("");
        spnSoLuong.setValue(1);
        chiTietModel.setRowCount(0);
        capNhatTongTien();
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

    private String taoMa(String prefix) {
        return prefix + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    private void hienThiNoiDung(String tieuDe, String noiDung) {
        JTextArea area = new JTextArea(noiDung, 20, 55);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), tieuDe, JOptionPane.INFORMATION_MESSAGE);
    }

    private static final class NhaCungCapItem {
        private final NhaCungCap nhaCungCap;

        private NhaCungCapItem(NhaCungCap nhaCungCap) {
            this.nhaCungCap = nhaCungCap;
        }

        @Override
        public String toString() {
            return nhaCungCap.getMaNCC() + " - " + nhaCungCap.getTenNCC();
        }
    }
}
