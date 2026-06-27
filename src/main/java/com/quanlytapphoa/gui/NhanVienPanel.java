package com.quanlytapphoa.gui;

import com.quanlytapphoa.model.NhanVien;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class NhanVienPanel extends JPanel {
    private final JTextField txtTimKiem = new JTextField(22);
    private final JTextField txtMa = new JTextField();
    private final JTextField txtHoTen = new JTextField();
    private final JTextField txtSoDienThoai = new JTextField();
    private final JTextField txtDiaChi = new JTextField();
    private final JTextField txtChucVu = new JTextField();
    private final JTextField txtNgayVaoLam = new JTextField();
    private final JCheckBox chkTrangThai = new JCheckBox("Dang lam viec", true);
    private final ReadOnlyTableModel model = new ReadOnlyTableModel(
            "Ma NV", "Ho ten", "So dien thoai", "Dia chi", "Chuc vu", "Ngay vao lam", "Trang thai"
    );
    private final JTable table = new JTable(model);

    public NhanVienPanel() {
        setLayout(new BorderLayout(8, 8));
        taoGiaoDien();
        ganSuKien();
        taiDuLieu(GuiContext.nhanVienBUS().layDanhSach());
    }

    private void taoGiaoDien() {
        JPanel phanDau = new JPanel(new GridLayout(0, 1, 4, 4));
        JPanel timKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTim = new JButton("Tim kiem");
        timKiem.add(new JLabel("Tu khoa:"));
        timKiem.add(txtTimKiem);
        timKiem.add(btnTim);
        phanDau.add(timKiem);

        JPanel form = new JPanel(new GridLayout(0, 4, 8, 6));
        themTruong(form, "Ma nhan vien:", txtMa);
        themTruong(form, "Ho ten:", txtHoTen);
        themTruong(form, "So dien thoai:", txtSoDienThoai);
        themTruong(form, "Dia chi:", txtDiaChi);
        themTruong(form, "Chuc vu:", txtChucVu);
        themTruong(form, "Ngay vao lam (yyyy-MM-dd):", txtNgayVaoLam);
        form.add(new JLabel("Trang thai:"));
        form.add(chkTrangThai);
        phanDau.add(form);

        JPanel thaoTac = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThem = new JButton("Them");
        JButton btnSua = new JButton("Sua");
        JButton btnXoa = new JButton("Xoa");
        JButton btnMoi = new JButton("Lam moi");
        thaoTac.add(btnThem);
        thaoTac.add(btnSua);
        thaoTac.add(btnXoa);
        thaoTac.add(btnMoi);
        phanDau.add(thaoTac);
        add(phanDau, BorderLayout.NORTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnTim.addActionListener(e -> timKiem());
        btnThem.addActionListener(e -> them());
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnMoi.addActionListener(e -> lamMoi());
    }

    private void ganSuKien() {
        txtTimKiem.addActionListener(e -> timKiem());
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiDongDangChon();
            }
        });
    }

    private void themTruong(JPanel panel, String nhan, JTextField field) {
        panel.add(new JLabel(nhan));
        panel.add(field);
    }

    private NhanVien docNhanVien() {
        return new NhanVien(
                GuiUtils.text(txtMa), GuiUtils.text(txtHoTen), GuiUtils.text(txtSoDienThoai),
                GuiUtils.text(txtDiaChi), GuiUtils.text(txtChucVu),
                GuiUtils.parseDateOrToday(GuiUtils.text(txtNgayVaoLam), "Ngay vao lam"), chkTrangThai.isSelected()
        );
    }

    private void them() {
        try {
            GuiContext.nhanVienBUS().themNhanVien(docNhanVien());
            lamMoi();
            GuiUtils.info(this, "Them nhan vien thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void sua() {
        try {
            GuiContext.nhanVienBUS().suaNhanVien(docNhanVien());
            lamMoi();
            GuiUtils.info(this, "Cap nhat nhan vien thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void xoa() {
        String ma = GuiUtils.text(txtMa);
        if (!GuiUtils.confirm(this, "Xoa nhan vien " + ma + "?")) {
            return;
        }
        try {
            GuiContext.nhanVienBUS().xoaNhanVien(ma);
            lamMoi();
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void timKiem() {
        taiDuLieu(GuiContext.nhanVienBUS().timKiem(GuiUtils.text(txtTimKiem)));
    }

    private void taiDuLieu(List<NhanVien> danhSach) {
        model.setRowCount(0);
        for (NhanVien nhanVien : danhSach) {
            model.addRow(new Object[]{
                nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getSoDienThoai(), nhanVien.getDiaChi(),
                nhanVien.getChucVu(), GuiUtils.formatDate(nhanVien.getNgayVaoLam()),
                nhanVien.isTrangThai() ? "Dang lam" : "Da nghi"
            });
        }
    }

    private void hienThiDongDangChon() {
        int row = GuiUtils.selectedModelRow(table);
        if (row < 0) {
            return;
        }
        NhanVien nhanVien = GuiContext.nhanVienBUS().timTheoMa(String.valueOf(model.getValueAt(row, 0)));
        if (nhanVien == null) {
            return;
        }
        txtMa.setText(nhanVien.getMaNV());
        txtHoTen.setText(nhanVien.getHoTen());
        txtSoDienThoai.setText(nhanVien.getSoDienThoai());
        txtDiaChi.setText(nhanVien.getDiaChi());
        txtChucVu.setText(nhanVien.getChucVu());
        txtNgayVaoLam.setText(GuiUtils.formatDate(nhanVien.getNgayVaoLam()));
        chkTrangThai.setSelected(nhanVien.isTrangThai());
        txtMa.setEditable(false);
    }

    private void lamMoi() {
        for (JTextField field : new JTextField[]{txtMa, txtHoTen, txtSoDienThoai, txtDiaChi, txtChucVu, txtNgayVaoLam}) {
            field.setText("");
        }
        txtNgayVaoLam.setText(GuiUtils.formatDate(new java.util.Date()));
        txtMa.setEditable(true);
        chkTrangThai.setSelected(true);
        table.clearSelection();
        taiDuLieu(GuiContext.nhanVienBUS().layDanhSach());
    }
}
