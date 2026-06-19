package com.quanlytapphoa.bus;

import com.quanlytapphoa.model.ChiTietHoaDon;
import com.quanlytapphoa.model.HoaDonBanHang;
import com.quanlytapphoa.model.MatHang;
import com.quanlytapphoa.model.NhanVien;
import com.quanlytapphoa.model.PhuongThucThanhToan;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoaDonBUS {
    private final List<HoaDonBanHang> dsHoaDonBanHang;
    private final List<ChiTietHoaDon> dsChiTietHoaDon;
    private final MatHangBUS matHangBUS;
    private final NhanVienBUS nhanVienBUS;
    private final PhuongThucThanhToanBUS phuongThucThanhToanBUS;
    private final KhuyenMaiBUS khuyenMaiBUS;

    public HoaDonBUS(
            List<HoaDonBanHang> dsHoaDonBanHang,
            List<ChiTietHoaDon> dsChiTietHoaDon,
            MatHangBUS matHangBUS,
            NhanVienBUS nhanVienBUS,
            PhuongThucThanhToanBUS phuongThucThanhToanBUS,
            KhuyenMaiBUS khuyenMaiBUS
    ) {
        this.dsHoaDonBanHang = dsHoaDonBanHang;
        this.dsChiTietHoaDon = dsChiTietHoaDon;
        this.matHangBUS = matHangBUS;
        this.nhanVienBUS = nhanVienBUS;
        this.phuongThucThanhToanBUS = phuongThucThanhToanBUS;
        this.khuyenMaiBUS = khuyenMaiBUS;
    }

    public HoaDonBanHang taoHoaDon(
            String maHD,
            String maNV,
            String maPT,
            List<ChiTietHoaDon> chiTietYeuCau
    ) {
        String maHoaDon = chuanHoa(maHD);
        if (maHoaDon.length() == 0) {
            throw new BusinessException("Ma hoa don khong duoc rong");
        }
        if (timTheoMa(maHoaDon) != null) {
            throw new BusinessException("Ma hoa don da ton tai");
        }

        NhanVien nhanVien = nhanVienBUS.timTheoMa(maNV);
        if (nhanVien == null || !nhanVien.isTrangThai()) {
            throw new BusinessException("Nhan vien khong ton tai hoac da ngung lam viec");
        }

        PhuongThucThanhToan phuongThuc = phuongThucThanhToanBUS.timTheoMa(maPT);
        if (phuongThuc == null || !phuongThuc.isTrangThai()) {
            throw new BusinessException("Phuong thuc thanh toan khong hop le");
        }

        if (chiTietYeuCau == null || chiTietYeuCau.isEmpty()) {
            throw new BusinessException("Hoa don phai co it nhat mot san pham");
        }

        Map<String, Integer> soLuongTheoMatHang = tongHopSoLuong(chiTietYeuCau);
        kiemTraTonKho(soLuongTheoMatHang);

        Date ngayLap = new Date();
        HoaDonBanHang hoaDon = new HoaDonBanHang(maHoaDon, ngayLap, nhanVien, phuongThuc);
        List<ChiTietHoaDon> chiTietDaTao = new ArrayList<ChiTietHoaDon>();

        for (ChiTietHoaDon yeuCau : chiTietYeuCau) {
            MatHang matHang = layMatHangTuChiTiet(yeuCau);
            int soLuong = yeuCau.getSoLuong();
            ChiTietHoaDon chiTiet = taoChiTietHoaDon(hoaDon, matHang, soLuong, ngayLap);
            chiTietDaTao.add(chiTiet);
        }

        capNhatTongTien(hoaDon, chiTietDaTao);
        truTonKho(soLuongTheoMatHang);

        dsHoaDonBanHang.add(hoaDon);
        dsChiTietHoaDon.addAll(chiTietDaTao);
        return hoaDon;
    }

    public HoaDonBanHang timTheoMa(String maHD) {
        String ma = chuanHoa(maHD);
        for (HoaDonBanHang hoaDon : dsHoaDonBanHang) {
            if (ma.equalsIgnoreCase(hoaDon.getMaHD())) {
                return hoaDon;
            }
        }
        return null;
    }

    public List<HoaDonBanHang> layDanhSach() {
        return new ArrayList<HoaDonBanHang>(dsHoaDonBanHang);
    }

    public List<ChiTietHoaDon> layChiTietHoaDon(String maHD) {
        List<ChiTietHoaDon> ketQua = new ArrayList<ChiTietHoaDon>();
        for (ChiTietHoaDon chiTiet : dsChiTietHoaDon) {
            if (maHD != null && maHD.equalsIgnoreCase(chiTiet.getMaHD())) {
                ketQua.add(chiTiet);
            }
        }
        return ketQua;
    }

    public List<HoaDonBanHang> traCuuLichSuGiaoDich(String maNV) {
        List<HoaDonBanHang> ketQua = new ArrayList<HoaDonBanHang>();
        for (HoaDonBanHang hoaDon : dsHoaDonBanHang) {
            if (maNV == null || maNV.trim().length() == 0 || maNV.equalsIgnoreCase(hoaDon.getMaNV())) {
                ketQua.add(hoaDon);
            }
        }
        return ketQua;
    }

    public String inHoaDon(String maHD) {
        HoaDonBanHang hoaDon = timTheoMa(maHD);
        if (hoaDon == null) {
            throw new BusinessException("Khong tim thay hoa don");
        }
        List<ChiTietHoaDon> chiTietHoaDons = layChiTietHoaDon(maHD);
        StringBuilder builder = new StringBuilder();
        builder.append("Hoa don: ").append(hoaDon.getMaHD()).append(System.lineSeparator());
        builder.append("Ngay lap: ").append(hoaDon.getNgayLap()).append(System.lineSeparator());
        if (hoaDon.getNhanVien() != null) {
            builder.append("Nhan vien: ").append(hoaDon.getNhanVien().getHoTen()).append(System.lineSeparator());
        }
        builder.append("Phuong thuc: ")
                .append(hoaDon.getPhuongThucThanhToan() == null ? "" : hoaDon.getPhuongThucThanhToan())
                .append(System.lineSeparator());
        builder.append("Chi tiet:").append(System.lineSeparator());

        for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
            builder.append(chiTiet).append(System.lineSeparator());
        }

        builder.append("Tong tien: ").append(hoaDon.getTongTien()).append(System.lineSeparator());
        builder.append("Khuyen mai: ").append(hoaDon.getGiaTriKM()).append(System.lineSeparator());
        builder.append("Thanh tien: ").append(hoaDon.getThanhTien());
        return builder.toString();
    }

    public void huyHoaDon(String maHD) {
        HoaDonBanHang hoaDon = timTheoMa(maHD);
        if (hoaDon == null) {
            throw new BusinessException("Khong tim thay hoa don can huy");
        }

        List<ChiTietHoaDon> chiTietCanHuy = layChiTietHoaDon(maHD);
        for (ChiTietHoaDon chiTiet : chiTietCanHuy) {
            MatHang matHang = matHangBUS.timTheoMa(chiTiet.getMaMatHang());
            if (matHang != null) {
                matHang.setSoLuong(matHang.getSoLuong() + chiTiet.getSoLuong());
            }
        }

        dsChiTietHoaDon.removeAll(chiTietCanHuy);
        dsHoaDonBanHang.remove(hoaDon);
    }

    private Map<String, Integer> tongHopSoLuong(List<ChiTietHoaDon> chiTietYeuCau) {
        Map<String, Integer> ketQua = new HashMap<String, Integer>();
        for (ChiTietHoaDon chiTiet : chiTietYeuCau) {
            MatHang matHang = layMatHangTuChiTiet(chiTiet);
            int soLuong = chiTiet.getSoLuong();
            if (soLuong <= 0) {
                throw new BusinessException("So luong ban phai lon hon 0");
            }
            Integer hienTai = ketQua.get(matHang.getMaMatHang());
            ketQua.put(matHang.getMaMatHang(), (hienTai == null ? 0 : hienTai) + soLuong);
        }
        return ketQua;
    }

    private void kiemTraTonKho(Map<String, Integer> soLuongTheoMatHang) {
        for (Map.Entry<String, Integer> entry : soLuongTheoMatHang.entrySet()) {
            MatHang matHang = matHangBUS.timTheoMa(entry.getKey());
            if (matHang == null || !matHang.isTrangThai()) {
                throw new BusinessException("Mat hang khong ton tai hoac da ngung ban");
            }
            if (matHang.getSoLuong() < entry.getValue()) {
                throw new BusinessException("So luong ton kho khong du cho mat hang " + matHang.getMaMatHang());
            }
        }
    }

    private ChiTietHoaDon taoChiTietHoaDon(HoaDonBanHang hoaDon, MatHang matHang, int soLuong, Date ngayLap) {
        double donGia = matHang.getGiaBan();
        double thanhTien = soLuong * donGia;
        double giaTriKM = khuyenMaiBUS.tinhTienGiam(matHang.getKhuyenMai(), thanhTien, ngayLap);

        ChiTietHoaDon chiTiet = new ChiTietHoaDon();
        chiTiet.setMaHD(hoaDon.getMaHD());
        chiTiet.setMaMatHang(matHang.getMaMatHang());
        chiTiet.setSoLuong(soLuong);
        chiTiet.setDonGia(donGia);
        chiTiet.setGiaTriKM(giaTriKM);
        chiTiet.setThanhTien(thanhTien);
        chiTiet.setThanhTienSauKM(thanhTien - giaTriKM);
        return chiTiet;
    }

    private void capNhatTongTien(HoaDonBanHang hoaDon, List<ChiTietHoaDon> chiTietHoaDons) {
        double tongTien = 0;
        double tongGiam = 0;
        double thanhTien = 0;

        for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
            tongTien += chiTiet.getThanhTien();
            tongGiam += chiTiet.getGiaTriKM();
            thanhTien += chiTiet.getThanhTienSauKM();
        }

        hoaDon.setTongTien(tongTien);
        hoaDon.setGiaTriKM(tongGiam);
        hoaDon.setThanhTien(thanhTien);
    }

    private void truTonKho(Map<String, Integer> soLuongTheoMatHang) {
        for (Map.Entry<String, Integer> entry : soLuongTheoMatHang.entrySet()) {
            MatHang matHang = matHangBUS.timTheoMa(entry.getKey());
            matHang.setSoLuong(matHang.getSoLuong() - entry.getValue());
        }
    }

    private MatHang layMatHangTuChiTiet(ChiTietHoaDon chiTiet) {
        if (chiTiet == null) {
            throw new BusinessException("Chi tiet hoa don khong duoc rong");
        }

        String maMatHang = chiTiet.getMaMatHang();
        if ((maMatHang == null || maMatHang.trim().length() == 0) && chiTiet.getMatHang() != null) {
            maMatHang = chiTiet.getMatHang().getMaMatHang();
        }

        MatHang matHang = matHangBUS.timTheoMa(maMatHang);
        if (matHang == null || !matHang.isTrangThai()) {
            throw new BusinessException("Mat hang khong ton tai hoac da ngung ban");
        }
        return matHang;
    }

    private String chuanHoa(String value) {
        return value == null ? "" : value.trim();
    }
}
