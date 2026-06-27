package com.quanlytapphoa.gui;

import com.quanlytapphoa.model.MatHang;
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

public class MatHangPanel extends JPanel {
    private final JTextField txtTimKiem = new JTextField(22);
    private final JTextField txtMa = new JTextField();
    private final JTextField txtTen = new JTextField();
    private final JTextField txtMaLoai = new JTextField();
    private final JTextField txtMaKM = new JTextField();
    private final JTextField txtDonViTinh = new JTextField();
    private final JTextField txtGiaNhap = new JTextField();
    private final JTextField txtGiaBan = new JTextField();
    private final JTextField txtSoLuong = new JTextField();
    private final JCheckBox chkTrangThai = new JCheckBox("Dang kinh doanh", true);
    private final ReadOnlyTableModel model = new ReadOnlyTableModel(
            "Ma hang", "Ten mat hang", "Ma loai", "Ma KM", "Don vi tinh",
            "Gia nhap", "Gia ban", "So luong", "Trang thai"
    );
    private final JTable table = new JTable(model);

    public MatHangPanel() {
        taoGiaoDien();
        ganSuKien();
        taiDuLieu(GuiContext.matHangBUS().layDanhSach());
    }

    private void taoGiaoDien() {
        setLayout(new BorderLayout(8, 8));

        JPanel phanDau = new JPanel(new GridLayout(0, 1, 4, 4));
        JPanel timKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTim = new JButton("Tim kiem");
        btnTim.setActionCommand("TIM");
        timKiem.add(new JLabel("Tu khoa:"));
        timKiem.add(txtTimKiem);
        timKiem.add(btnTim);
        phanDau.add(timKiem);

        JPanel form = new JPanel(new GridLayout(0, 4, 8, 6));
        themTruong(form, "Ma mat hang:", txtMa);
        themTruong(form, "Ten mat hang:", txtTen);
        themTruong(form, "Ma loai:", txtMaLoai);
        themTruong(form, "Ma khuyen mai:", txtMaKM);
        themTruong(form, "Don vi tinh:", txtDonViTinh);
        themTruong(form, "Gia nhap:", txtGiaNhap);
        themTruong(form, "Gia ban:", txtGiaBan);
        themTruong(form, "So luong:", txtSoLuong);
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

    private MatHang docMatHang() {
        return new MatHang(
                GuiUtils.text(txtMa), GuiUtils.text(txtTen), GuiUtils.text(txtMaLoai),
                rongThanhNull(GuiUtils.text(txtMaKM)), GuiUtils.text(txtDonViTinh),
                GuiUtils.parseDouble(GuiUtils.text(txtGiaNhap), "Gia nhap"),
                GuiUtils.parseDouble(GuiUtils.text(txtGiaBan), "Gia ban"),
                GuiUtils.parseInt(GuiUtils.text(txtSoLuong), "So luong"), chkTrangThai.isSelected()
        );
    }

    private void them() {
        try {
            GuiContext.matHangBUS().themMatHang(docMatHang());
            lamMoi();
            GuiUtils.info(this, "Them mat hang thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void sua() {
        try {
            GuiContext.matHangBUS().suaMatHang(docMatHang());
            lamMoi();
            GuiUtils.info(this, "Cap nhat mat hang thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void xoa() {
        String ma = GuiUtils.text(txtMa);
        if (!GuiUtils.confirm(this, "Xoa mat hang " + ma + "?")) {
            return;
        }
        try {
            GuiContext.matHangBUS().xoaMatHang(ma);
            lamMoi();
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void timKiem() {
        taiDuLieu(GuiContext.matHangBUS().timKiemSanPham(GuiUtils.text(txtTimKiem)));
    }

    private void taiDuLieu(List<MatHang> danhSach) {
        model.setRowCount(0);
        for (MatHang matHang : danhSach) {
            model.addRow(new Object[]{
                matHang.getMaMatHang(), matHang.getTenMatHang(), matHang.getMaLoai(), matHang.getMaKM(),
                matHang.getDonViTinh(), matHang.getGiaNhap(), matHang.getGiaBan(), matHang.getSoLuong(),
                matHang.isTrangThai() ? "Dang ban" : "Ngung ban"
            });
        }
    }

    private void hienThiDongDangChon() {
        int row = GuiUtils.selectedModelRow(table);
        if (row < 0) {
            return;
        }
        MatHang matHang = GuiContext.matHangBUS().timTheoMa(String.valueOf(model.getValueAt(row, 0)));
        if (matHang == null) {
            return;
        }
        txtMa.setText(matHang.getMaMatHang());
        txtTen.setText(matHang.getTenMatHang());
        txtMaLoai.setText(matHang.getMaLoai());
        txtMaKM.setText(matHang.getMaKM() == null ? "" : matHang.getMaKM());
        txtDonViTinh.setText(matHang.getDonViTinh());
        txtGiaNhap.setText(String.valueOf(matHang.getGiaNhap()));
        txtGiaBan.setText(String.valueOf(matHang.getGiaBan()));
        txtSoLuong.setText(String.valueOf(matHang.getSoLuong()));
        chkTrangThai.setSelected(matHang.isTrangThai());
        txtMa.setEditable(false);
    }

    private void lamMoi() {
        for (JTextField field : new JTextField[]{txtMa, txtTen, txtMaLoai, txtMaKM, txtDonViTinh, txtGiaNhap, txtGiaBan, txtSoLuong}) {
            field.setText("");
        }
        txtMa.setEditable(true);
        chkTrangThai.setSelected(true);
        table.clearSelection();
        taiDuLieu(GuiContext.matHangBUS().layDanhSach());
    }

    private String rongThanhNull(String value) {
        return value.length() == 0 ? null : value;
    }
}
