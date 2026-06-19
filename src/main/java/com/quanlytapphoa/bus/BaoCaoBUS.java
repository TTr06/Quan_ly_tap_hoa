package com.quanlytapphoa.bus;

import com.quanlytapphoa.model.ChiTietHoaDon;
import com.quanlytapphoa.model.HoaDonBanHang;
import com.quanlytapphoa.model.MatHang;
import com.quanlytapphoa.model.PhieuNhapHang;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BaoCaoBUS {
    private final List<HoaDonBanHang> dsHoaDonBanHang;
    private final List<ChiTietHoaDon> dsChiTietHoaDon;
    private final List<PhieuNhapHang> dsPhieuNhapHang;
    private final List<MatHang> dsMatHang;

    public BaoCaoBUS(
            List<HoaDonBanHang> dsHoaDonBanHang,
            List<ChiTietHoaDon> dsChiTietHoaDon,
            List<PhieuNhapHang> dsPhieuNhapHang,
            List<MatHang> dsMatHang
    ) {
        this.dsHoaDonBanHang = dsHoaDonBanHang;
        this.dsChiTietHoaDon = dsChiTietHoaDon;
        this.dsPhieuNhapHang = dsPhieuNhapHang;
        this.dsMatHang = dsMatHang;
    }

    public double tinhDoanhThu(Date tuNgay, Date denNgay) {
        double doanhThu = 0;
        for (HoaDonBanHang hoaDon : dsHoaDonBanHang) {
            if (namTrongKhoang(hoaDon.getNgayLap(), tuNgay, denNgay)) {
                doanhThu += hoaDon.getThanhTien();
            }
        }
        return doanhThu;
    }

    public double tinhTongNhapHang(Date tuNgay, Date denNgay) {
        double tongNhap = 0;
        for (PhieuNhapHang phieuNhap : dsPhieuNhapHang) {
            if (namTrongKhoang(phieuNhap.getNgayNhap(), tuNgay, denNgay)) {
                tongNhap += phieuNhap.getTongTien();
            }
        }
        return tongNhap;
    }

    public double tinhLoiNhuanTamTinh(Date tuNgay, Date denNgay) {
        return tinhDoanhThu(tuNgay, denNgay) - tinhTongNhapHang(tuNgay, denNgay);
    }

    public Map<String, Integer> thongKeMatHangBanChay(Date tuNgay, Date denNgay) {
        Map<String, Integer> soLuongTheoMa = new LinkedHashMap<String, Integer>();
        for (HoaDonBanHang hoaDon : dsHoaDonBanHang) {
            if (!namTrongKhoang(hoaDon.getNgayLap(), tuNgay, denNgay)) {
                continue;
            }

            for (ChiTietHoaDon chiTiet : dsChiTietHoaDon) {
                if (hoaDon.getMaHD().equalsIgnoreCase(chiTiet.getMaHD())) {
                    Integer hienTai = soLuongTheoMa.get(chiTiet.getMaMatHang());
                    soLuongTheoMa.put(chiTiet.getMaMatHang(), (hienTai == null ? 0 : hienTai) + chiTiet.getSoLuong());
                }
            }
        }
        return sapXepGiamDanTheoSoLuong(soLuongTheoMa);
    }

    public String xuatBaoCaoTongHop(Date tuNgay, Date denNgay) {
        StringBuilder builder = new StringBuilder();
        builder.append("Doanh thu: ").append(tinhDoanhThu(tuNgay, denNgay)).append(System.lineSeparator());
        builder.append("Tong nhap hang: ").append(tinhTongNhapHang(tuNgay, denNgay)).append(System.lineSeparator());
        builder.append("Loi nhuan tam tinh: ").append(tinhLoiNhuanTamTinh(tuNgay, denNgay)).append(System.lineSeparator());
        builder.append("So san pham dang ban: ").append(demSanPhamDangBan()).append(System.lineSeparator());
        builder.append("Tong ton kho: ").append(tinhTongTonKho()).append(System.lineSeparator());
        builder.append("Mat hang ban chay: ").append(thongKeMatHangBanChay(tuNgay, denNgay));
        return builder.toString();
    }

    public int demSanPhamDangBan() {
        int soLuong = 0;
        for (MatHang matHang : dsMatHang) {
            if (matHang.isTrangThai()) {
                soLuong++;
            }
        }
        return soLuong;
    }

    public int demHoaDon() {
        return dsHoaDonBanHang.size();
    }

    public int demPhieuNhap() {
        return dsPhieuNhapHang.size();
    }

    public int tinhTongTonKho() {
        int tongTonKho = 0;
        for (MatHang matHang : dsMatHang) {
            if (matHang.isTrangThai()) {
                tongTonKho += matHang.getSoLuong();
            }
        }
        return tongTonKho;
    }

    public List<MatHang> thongKeHangSapHet(int nguongTonKho) {
        List<MatHang> ketQua = new ArrayList<MatHang>();
        for (MatHang matHang : dsMatHang) {
            if (matHang.isTrangThai() && matHang.getSoLuong() <= nguongTonKho) {
                ketQua.add(matHang);
            }
        }
        return ketQua;
    }

    private boolean namTrongKhoang(Date ngay, Date tuNgay, Date denNgay) {
        if (ngay == null) {
            return false;
        }
        boolean sauTuNgay = tuNgay == null || !ngay.before(tuNgay);
        boolean truocDenNgay = denNgay == null || !ngay.after(denNgay);
        return sauTuNgay && truocDenNgay;
    }

    private Map<String, Integer> sapXepGiamDanTheoSoLuong(Map<String, Integer> source) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(source.entrySet());
        entries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        Map<String, Integer> ketQua = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : entries) {
            ketQua.put(entry.getKey(), entry.getValue());
        }
        return ketQua;
    }
}
