package com.quanlytapphoa.gui;

import com.quanlytapphoa.model.TaiKhoan;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class TaiKhoanPanel extends JPanel {
    private final JTextField txtTimKiem = new JTextField(22);
    private final JTextField txtTenDangNhap = new JTextField();
    private final JPasswordField txtMatKhau = new JPasswordField();
    private final JTextField txtMaNV = new JTextField();
    private final JComboBox<String> cboVaiTro = new JComboBox<String>(new String[]{"ADMIN", "NHAN_VIEN"});
    private final JCheckBox chkTrangThai = new JCheckBox("Dang hoat dong", true);
    private final ReadOnlyTableModel model = new ReadOnlyTableModel(
            "Ten dang nhap", "Ma nhan vien", "Vai tro", "Trang thai"
    );
    private final JTable table = new JTable(model);

    public TaiKhoanPanel() {
        setLayout(new BorderLayout(8, 8));
        taoGiaoDien();
        ganSuKien();
        taiDuLieu(GuiContext.taiKhoanBUS().layDanhSach());
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
        form.add(new JLabel("Ten dang nhap:"));
        form.add(txtTenDangNhap);
        form.add(new JLabel("Mat khau:"));
        form.add(txtMatKhau);
        form.add(new JLabel("Ma nhan vien:"));
        form.add(txtMaNV);
        form.add(new JLabel("Vai tro:"));
        form.add(cboVaiTro);
        form.add(new JLabel("Trang thai:"));
        form.add(chkTrangThai);
        phanDau.add(form);

        JPanel thaoTac = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThem = new JButton("Them");
        JButton btnSua = new JButton("Sua");
        JButton btnXoa = new JButton("Khoa tai khoan");
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

    private TaiKhoan docTaiKhoan() {
        return new TaiKhoan(
                GuiUtils.text(txtTenDangNhap), new String(txtMatKhau.getPassword()),
                String.valueOf(cboVaiTro.getSelectedItem()), GuiUtils.text(txtMaNV), chkTrangThai.isSelected()
        );
    }

    private void them() {
        try {
            GuiContext.taiKhoanBUS().themTaiKhoan(docTaiKhoan());
            lamMoi();
            GuiUtils.info(this, "Them tai khoan thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void sua() {
        try {
            GuiContext.taiKhoanBUS().suaTaiKhoan(docTaiKhoan());
            lamMoi();
            GuiUtils.info(this, "Cap nhat tai khoan thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void xoa() {
        String tenDangNhap = GuiUtils.text(txtTenDangNhap);
        if (!GuiUtils.confirm(this, "Khoa tai khoan " + tenDangNhap + "?")) {
            return;
        }
        try {
            GuiContext.taiKhoanBUS().xoaTaiKhoan(tenDangNhap);
            lamMoi();
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void timKiem() {
        taiDuLieu(GuiContext.taiKhoanBUS().timKiem(GuiUtils.text(txtTimKiem)));
    }

    private void taiDuLieu(List<TaiKhoan> danhSach) {
        model.setRowCount(0);
        for (TaiKhoan taiKhoan : danhSach) {
            model.addRow(new Object[]{
                taiKhoan.getTenDangNhap(), taiKhoan.getMaNV(), taiKhoan.getVaiTro(),
                taiKhoan.isTrangThai() ? "Hoat dong" : "Da khoa"
            });
        }
    }

    private void hienThiDongDangChon() {
        int row = GuiUtils.selectedModelRow(table);
        if (row < 0) {
            return;
        }
        TaiKhoan taiKhoan = GuiContext.taiKhoanBUS().timTheoTenDangNhap(String.valueOf(model.getValueAt(row, 0)));
        if (taiKhoan == null) {
            return;
        }
        txtTenDangNhap.setText(taiKhoan.getTenDangNhap());
        txtMatKhau.setText(taiKhoan.getMatKhau());
        txtMaNV.setText(taiKhoan.getMaNV());
        cboVaiTro.setSelectedItem(taiKhoan.getVaiTro());
        chkTrangThai.setSelected(taiKhoan.isTrangThai());
        txtTenDangNhap.setEditable(false);
    }

    private void lamMoi() {
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        txtMaNV.setText("");
        txtTenDangNhap.setEditable(true);
        cboVaiTro.setSelectedIndex(0);
        chkTrangThai.setSelected(true);
        table.clearSelection();
        taiDuLieu(GuiContext.taiKhoanBUS().layDanhSach());
    }
}
