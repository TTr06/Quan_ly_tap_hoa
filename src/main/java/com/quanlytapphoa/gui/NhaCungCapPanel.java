package com.quanlytapphoa.gui;

import com.quanlytapphoa.model.NhaCungCap;
import com.quanlytapphoa.model.PhieuNhapHang;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class NhaCungCapPanel extends JPanel {
    private final JTextField txtTimKiem = new JTextField(22);
    private final JTextField txtMa = new JTextField();
    private final JTextField txtTen = new JTextField();
    private final JTextField txtDiaChi = new JTextField();
    private final JTextField txtSoDienThoai = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final ReadOnlyTableModel model = new ReadOnlyTableModel(
            "Ma NCC", "Ten nha cung cap", "Dia chi", "So dien thoai", "Email"
    );
    private final JTable table = new JTable(model);

    public NhaCungCapPanel() {
        setLayout(new BorderLayout(8, 8));
        taoGiaoDien();
        ganSuKien();
        taiDuLieu(GuiContext.nhaCungCapBUS().layDanhSach());
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
        themTruong(form, "Ma NCC:", txtMa);
        themTruong(form, "Ten NCC:", txtTen);
        themTruong(form, "Dia chi:", txtDiaChi);
        themTruong(form, "So dien thoai:", txtSoDienThoai);
        themTruong(form, "Email:", txtEmail);
        phanDau.add(form);

        JPanel thaoTac = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThem = new JButton("Them");
        JButton btnSua = new JButton("Sua");
        JButton btnXoa = new JButton("Xoa");
        JButton btnLichSu = new JButton("Lich su dat hang");
        JButton btnMoi = new JButton("Lam moi");
        thaoTac.add(btnThem);
        thaoTac.add(btnSua);
        thaoTac.add(btnXoa);
        thaoTac.add(btnLichSu);
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
        btnLichSu.addActionListener(e -> xemLichSu());
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

    private NhaCungCap docNhaCungCap() {
        return new NhaCungCap(
                GuiUtils.text(txtMa), GuiUtils.text(txtTen), GuiUtils.text(txtDiaChi),
                GuiUtils.text(txtSoDienThoai), GuiUtils.text(txtEmail)
        );
    }

    private void them() {
        try {
            GuiContext.nhaCungCapBUS().themNhaCungCap(docNhaCungCap());
            lamMoi();
            GuiUtils.info(this, "Them nha cung cap thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void sua() {
        try {
            GuiContext.nhaCungCapBUS().suaNhaCungCap(docNhaCungCap());
            lamMoi();
            GuiUtils.info(this, "Cap nhat nha cung cap thanh cong");
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void xoa() {
        String ma = GuiUtils.text(txtMa);
        if (!GuiUtils.confirm(this, "Xoa nha cung cap " + ma + "?")) {
            return;
        }
        try {
            GuiContext.nhaCungCapBUS().xoaNhaCungCap(ma);
            lamMoi();
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void xemLichSu() {
        try {
            List<PhieuNhapHang> danhSach = GuiContext.nhaCungCapBUS().lichSuDatHang(GuiUtils.text(txtMa));
            StringBuilder noiDung = new StringBuilder("LICH SU DAT HANG\n\n");
            for (PhieuNhapHang phieu : danhSach) {
                noiDung.append(phieu.getMaPhieu()).append(" | ")
                        .append(GuiUtils.formatDate(phieu.getNgayNhap())).append(" | ")
                        .append(GuiUtils.formatMoney(phieu.getTongTien())).append('\n');
            }
            JTextArea area = new JTextArea(noiDung.toString(), 14, 45);
            area.setEditable(false);
            javax.swing.JOptionPane.showMessageDialog(this, new JScrollPane(area), "Lich su dat hang", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }

    private void timKiem() {
        taiDuLieu(GuiContext.nhaCungCapBUS().timKiem(GuiUtils.text(txtTimKiem)));
    }

    private void taiDuLieu(List<NhaCungCap> danhSach) {
        model.setRowCount(0);
        for (NhaCungCap nhaCungCap : danhSach) {
            model.addRow(new Object[]{
                nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(), nhaCungCap.getDiaChi(),
                nhaCungCap.getSoDienThoai(), nhaCungCap.getEmail()
            });
        }
    }

    private void hienThiDongDangChon() {
        int row = GuiUtils.selectedModelRow(table);
        if (row < 0) {
            return;
        }
        NhaCungCap nhaCungCap = GuiContext.nhaCungCapBUS().timTheoMa(String.valueOf(model.getValueAt(row, 0)));
        if (nhaCungCap == null) {
            return;
        }
        txtMa.setText(nhaCungCap.getMaNCC());
        txtTen.setText(nhaCungCap.getTenNCC());
        txtDiaChi.setText(nhaCungCap.getDiaChi());
        txtSoDienThoai.setText(nhaCungCap.getSoDienThoai());
        txtEmail.setText(nhaCungCap.getEmail());
        txtMa.setEditable(false);
    }

    private void lamMoi() {
        for (JTextField field : new JTextField[]{txtMa, txtTen, txtDiaChi, txtSoDienThoai, txtEmail}) {
            field.setText("");
        }
        txtMa.setEditable(true);
        table.clearSelection();
        taiDuLieu(GuiContext.nhaCungCapBUS().layDanhSach());
    }
}
