package com.quanlytapphoa.gui;

import com.quanlytapphoa.model.MatHang;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Date;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class BaoCaoPanel extends JPanel {
    private final JTextField txtTuNgay = new JTextField(10);
    private final JTextField txtDenNgay = new JTextField(10);
    private final JSpinner spnNguongTon = new JSpinner(new SpinnerNumberModel(10, 0, 100000, 1));
    private final JTextArea txtTongHop = new JTextArea();
    private final ReadOnlyTableModel banChayModel = new ReadOnlyTableModel(
            "Xep hang", "Ma hang", "Ten mat hang", "So luong da ban"
    );
    private final ReadOnlyTableModel sapHetModel = new ReadOnlyTableModel(
            "Ma hang", "Ten mat hang", "Don vi tinh", "Ton kho"
    );

    public BaoCaoPanel() {
        setLayout(new BorderLayout(8, 8));
        taoGiaoDien();
        thongKe();
    }

    private void taoGiaoDien() {
        JPanel boLoc = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThongKe = new JButton("Thong ke");
        JButton btnTatCa = new JButton("Tat ca thoi gian");
        boLoc.add(new JLabel("Tu ngay (yyyy-MM-dd):"));
        boLoc.add(txtTuNgay);
        boLoc.add(new JLabel("Den ngay:"));
        boLoc.add(txtDenNgay);
        boLoc.add(new JLabel("Nguong ton kho:"));
        boLoc.add(spnNguongTon);
        boLoc.add(btnThongKe);
        boLoc.add(btnTatCa);
        add(boLoc, BorderLayout.NORTH);

        txtTongHop.setEditable(false);
        txtTongHop.setRows(7);
        JPanel tongHop = new JPanel(new BorderLayout(4, 4));
        tongHop.add(new JLabel("BAO CAO TONG HOP"), BorderLayout.NORTH);
        tongHop.add(new JScrollPane(txtTongHop), BorderLayout.CENTER);

        JTable tblBanChay = new JTable(banChayModel);
        tblBanChay.setAutoCreateRowSorter(true);
        JTable tblSapHet = new JTable(sapHetModel);
        tblSapHet.setAutoCreateRowSorter(true);
        JPanel bang = new JPanel(new GridLayout(1, 2, 8, 8));
        JPanel banChay = new JPanel(new BorderLayout(4, 4));
        banChay.add(new JLabel("MAT HANG BAN CHAY"), BorderLayout.NORTH);
        banChay.add(new JScrollPane(tblBanChay), BorderLayout.CENTER);
        JPanel sapHet = new JPanel(new BorderLayout(4, 4));
        sapHet.add(new JLabel("HANG SAP HET"), BorderLayout.NORTH);
        sapHet.add(new JScrollPane(tblSapHet), BorderLayout.CENTER);
        bang.add(banChay);
        bang.add(sapHet);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tongHop, bang);
        split.setResizeWeight(0.35);
        add(split, BorderLayout.CENTER);

        btnThongKe.addActionListener(e -> thongKe());
        btnTatCa.addActionListener(e -> {
            txtTuNgay.setText("");
            txtDenNgay.setText("");
            thongKe();
        });
        txtTuNgay.addActionListener(e -> thongKe());
        txtDenNgay.addActionListener(e -> thongKe());
    }

    private void thongKe() {
        try {
            Date tuNgay = GuiUtils.parseDateOrNull(GuiUtils.text(txtTuNgay), "Tu ngay");
            Date denNgay = GuiUtils.parseDateOrNull(GuiUtils.text(txtDenNgay), "Den ngay");
            if (tuNgay != null && denNgay != null && denNgay.before(tuNgay)) {
                throw new com.quanlytapphoa.bus.BusinessException("Den ngay khong duoc truoc tu ngay");
            }

            StringBuilder tongHop = new StringBuilder();
            tongHop.append("Doanh thu: ").append(GuiUtils.formatMoney(GuiContext.baoCaoBUS().tinhDoanhThu(tuNgay, denNgay))).append('\n');
            tongHop.append("Tong nhap hang: ").append(GuiUtils.formatMoney(GuiContext.baoCaoBUS().tinhTongNhapHang(tuNgay, denNgay))).append('\n');
            tongHop.append("Loi nhuan tam tinh: ").append(GuiUtils.formatMoney(GuiContext.baoCaoBUS().tinhLoiNhuanTamTinh(tuNgay, denNgay))).append('\n');
            tongHop.append("So hoa don: ").append(GuiContext.baoCaoBUS().demHoaDon()).append('\n');
            tongHop.append("So phieu nhap: ").append(GuiContext.baoCaoBUS().demPhieuNhap()).append('\n');
            tongHop.append("San pham dang ban: ").append(GuiContext.baoCaoBUS().demSanPhamDangBan()).append('\n');
            tongHop.append("Tong ton kho: ").append(GuiContext.baoCaoBUS().tinhTongTonKho());
            txtTongHop.setText(tongHop.toString());

            banChayModel.setRowCount(0);
            int xepHang = 1;
            for (Map.Entry<String, Integer> entry : GuiContext.baoCaoBUS().thongKeMatHangBanChay(tuNgay, denNgay).entrySet()) {
                MatHang matHang = GuiContext.matHangBUS().timTheoMa(entry.getKey());
                banChayModel.addRow(new Object[]{
                    xepHang++, entry.getKey(), matHang == null ? "" : matHang.getTenMatHang(), entry.getValue()
                });
            }

            sapHetModel.setRowCount(0);
            int nguong = ((Number) spnNguongTon.getValue()).intValue();
            for (MatHang matHang : GuiContext.baoCaoBUS().thongKeHangSapHet(nguong)) {
                sapHetModel.addRow(new Object[]{
                    matHang.getMaMatHang(), matHang.getTenMatHang(), matHang.getDonViTinh(), matHang.getSoLuong()
                });
            }
        } catch (Exception ex) {
            GuiUtils.error(this, ex);
        }
    }
}
