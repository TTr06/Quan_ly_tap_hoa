package com.quanlytapphoa.gui;

import com.quanlytapphoa.model.MatHang;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class DashboardPanel extends JPanel {
    private final JLabel lblHoaDon = taoGiaTri();
    private final JLabel lblDoanhThu = taoGiaTri();
    private final JLabel lblSanPham = taoGiaTri();
    private final JLabel lblTonKho = taoGiaTri();
    private final ReadOnlyTableModel model = new ReadOnlyTableModel(
            "Ma hang", "Ten mat hang", "Don vi tinh", "Ton kho", "Gia ban"
    );

    public DashboardPanel() {
        setLayout(new BorderLayout(12, 12));
        taoGiaoDien();
        taiDuLieu();
    }

    private void taoGiaoDien() {
        JPanel tieuDe = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel title = new JLabel("TONG QUAN CUA HANG");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        JButton btnLamMoi = new JButton("Lam moi");
        tieuDe.add(title);
        tieuDe.add(btnLamMoi);
        add(tieuDe, BorderLayout.NORTH);

        JPanel noiDung = new JPanel(new BorderLayout(12, 12));
        JPanel thongKe = new JPanel(new GridLayout(1, 4, 10, 10));
        thongKe.add(taoKhoiThongKe("Hoa don", lblHoaDon));
        thongKe.add(taoKhoiThongKe("Doanh thu", lblDoanhThu));
        thongKe.add(taoKhoiThongKe("San pham dang ban", lblSanPham));
        thongKe.add(taoKhoiThongKe("Tong ton kho", lblTonKho));
        noiDung.add(thongKe, BorderLayout.NORTH);

        JPanel sapHet = new JPanel(new BorderLayout(4, 4));
        sapHet.add(new JLabel("HANG SAP HET (TON KHO <= 10)"), BorderLayout.NORTH);
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        sapHet.add(new JScrollPane(table), BorderLayout.CENTER);
        noiDung.add(sapHet, BorderLayout.CENTER);
        add(noiDung, BorderLayout.CENTER);

        btnLamMoi.addActionListener(e -> taiDuLieu());
    }

    private JPanel taoKhoiThongKe(String tieuDe, JLabel giaTri) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        JLabel label = new JLabel(tieuDe, SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        panel.add(label, BorderLayout.NORTH);
        panel.add(giaTri, BorderLayout.CENTER);
        return panel;
    }

    private static JLabel taoGiaTri() {
        JLabel label = new JLabel("0", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 20f));
        return label;
    }

    private void taiDuLieu() {
        lblHoaDon.setText(String.valueOf(GuiContext.baoCaoBUS().demHoaDon()));
        lblDoanhThu.setText(GuiUtils.formatMoney(GuiContext.baoCaoBUS().tinhDoanhThu(null, null)));
        lblSanPham.setText(String.valueOf(GuiContext.baoCaoBUS().demSanPhamDangBan()));
        lblTonKho.setText(String.valueOf(GuiContext.baoCaoBUS().tinhTongTonKho()));
        model.setRowCount(0);
        for (MatHang matHang : GuiContext.baoCaoBUS().thongKeHangSapHet(10)) {
            model.addRow(new Object[]{
                matHang.getMaMatHang(), matHang.getTenMatHang(), matHang.getDonViTinh(),
                matHang.getSoLuong(), matHang.getGiaBan()
            });
        }
    }
}
