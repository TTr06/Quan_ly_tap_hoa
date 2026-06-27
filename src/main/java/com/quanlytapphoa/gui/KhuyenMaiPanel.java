package com.quanlytapphoa.gui;

import com.quanlytapphoa.model.KhuyenMai;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class KhuyenMaiPanel extends JPanel {
    private final JTextField txtTimKiem = new JTextField(22);
    private final JTextField txtMa = new JTextField();
    private final JTextField txtTen = new JTextField();
    private final JComboBox<String> cboLoai = new JComboBox<String>(new String[]{"Phan tram", "So tien"});
    private final JTextField txtGiaTri = new JTextField();
    private final JTextField txtNgayBatDau = new JTextField();
    private final JTextField txtNgayKetThuc = new JTextField();
    private final JTextField txtMaMatHang = new JTextField();
    private final JCheckBox chkTrangThai = new JCheckBox("Dang ap dung", true);
    private final ReadOnlyTableModel model = new ReadOnlyTableModel(
            "Ma KM", "Ten khuyen mai", "Loai", "Gia tri", "Ngay bat dau", "Ngay ket thuc", "Trang thai"
    );
    private final JTable table = new JTable(model);

    public KhuyenMaiPanel() {
        setLayout(new BorderLayout(8, 8));
        taoGiaoDien();
        ganSuKien();
        lamMoi();
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
        themTruong(form, "Ma khuyen mai:", txtMa);
        themTruong(form, "Ten khuyen mai:", txtTen);
        form.add(new JLabel("Loai khuyen mai:"));
        form.add(cboLoai);
        themTruong(form, "Gia tri:", txtGiaTri);
        themTruong(form, "Ngay bat dau:", txtNgayBatDau);
        themTruong(form, "Ngay ket thuc:", txtNgayKetThuc);
        themTruong(form, "Ma mat hang gan KM:", txtMaMatHang);
        form.add(new JLabel("Trang thai:"));
        form.add(chkTrangThai);
        phanDau.add(form);

        JPanel thaoTac = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThem = new JButton("Them");
        JButton btnSua = new JButton("Sua");
        JButton btnBo = new JButton("Bo khuyen mai");
        JButton btnGan = new JButton("Gan cho san pham");
        JButton btnMoi = new JButton("Lam moi");
        thaoTac.add(btnThem);
        thaoTac.add(btnSua);
        thaoTac.add(btnBo);
        thaoTac.add(btnGan);
        thaoTac.add(btnMoi);
        phanDau.add(thaoTac);
        add(phanDau, BorderLayout.NORTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnTim.addActionListener(e -> timKiem());
        btnThem.addActionListener(e -> them());
        btnSua.addActionListener(e -> sua());
        btnBo.addActionListener(e -> boKhuyenMai());
        btnGan.addActionListener(e -> ganChoSanPham());
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

    private KhuyenMai docKhuyenMai() {
        return new KhuyenMai(
                GuiUtils.text(txtMa), GuiUtils.text(txtTen), String.valueOf(cboLoai.getSelectedItem()),
                GuiUtils.text(txtGiaTri),
                GuiUtils.parseDateOrToday(GuiUtils.text(txtNgayBatDau), "Ngay bat dau"),
                GuiUtils.parseDateOrNull(GuiUtils.text(txtNgayKetThuc), "Ngay ket thuc"),
                chkTrangThai.isSelected()
        );
    }

    private void them() {
        try {
            GuiContext.khuyenMaiBUS().themKhuyenMai(docKhuyenMai());
            ganNeuCoMaMatHang();
            lamMoi();
            GuiUtils.info(this, "Them khuyen mai thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void sua() {
        try {
            GuiContext.khuyenMaiBUS().suaKhuyenMai(docKhuyenMai());
            ganNeuCoMaMatHang();
            lamMoi();
            GuiUtils.info(this, "Cap nhat khuyen mai thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void boKhuyenMai() {
        String ma = GuiUtils.text(txtMa);
        if (!GuiUtils.confirm(this, "Ngung ap dung khuyen mai " + ma + "?")) {
            return;
        }
        try {
            GuiContext.khuyenMaiBUS().boKhuyenMai(ma);
            lamMoi();
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void ganChoSanPham() {
        try {
            GuiContext.khuyenMaiBUS().ganKhuyenMaiChoSanPham(GuiUtils.text(txtMaMatHang), GuiUtils.text(txtMa));
            GuiUtils.info(this, "Gan khuyen mai cho san pham thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void ganNeuCoMaMatHang() {
        if (GuiUtils.text(txtMaMatHang).length() > 0) {
            GuiContext.khuyenMaiBUS().ganKhuyenMaiChoSanPham(GuiUtils.text(txtMaMatHang), GuiUtils.text(txtMa));
        }
    }

    private void timKiem() {
        taiDuLieu(GuiContext.khuyenMaiBUS().timKiem(GuiUtils.text(txtTimKiem)));
    }

    private void taiDuLieu(List<KhuyenMai> danhSach) {
        model.setRowCount(0);
        for (KhuyenMai khuyenMai : danhSach) {
            model.addRow(new Object[]{
                khuyenMai.getMaKM(), khuyenMai.getTenKM(), khuyenMai.getLoaiKM(), khuyenMai.getGiaTriKM(),
                GuiUtils.formatDate(khuyenMai.getNgayBatDau()), GuiUtils.formatDate(khuyenMai.getNgayKetThuc()),
                khuyenMai.isTrangThai() ? "Dang ap dung" : "Da ngung"
            });
        }
    }

    private void hienThiDongDangChon() {
        int row = GuiUtils.selectedModelRow(table);
        if (row < 0) {
            return;
        }
        KhuyenMai khuyenMai = GuiContext.khuyenMaiBUS().timTheoMa(String.valueOf(model.getValueAt(row, 0)));
        if (khuyenMai == null) {
            return;
        }
        txtMa.setText(khuyenMai.getMaKM());
        txtTen.setText(khuyenMai.getTenKM());
        cboLoai.setSelectedItem(khuyenMai.getLoaiKM());
        txtGiaTri.setText(khuyenMai.getGiaTriKM());
        txtNgayBatDau.setText(GuiUtils.formatDate(khuyenMai.getNgayBatDau()));
        txtNgayKetThuc.setText(GuiUtils.formatDate(khuyenMai.getNgayKetThuc()));
        chkTrangThai.setSelected(khuyenMai.isTrangThai());
        txtMa.setEditable(false);
    }

    private void lamMoi() {
        for (JTextField field : new JTextField[]{txtMa, txtTen, txtGiaTri, txtNgayBatDau, txtNgayKetThuc, txtMaMatHang}) {
            field.setText("");
        }
        txtNgayBatDau.setText(GuiUtils.formatDate(new java.util.Date()));
        txtMa.setEditable(true);
        cboLoai.setSelectedIndex(0);
        chkTrangThai.setSelected(true);
        table.clearSelection();
        taiDuLieu(GuiContext.khuyenMaiBUS().layDanhSach());
    }
}
