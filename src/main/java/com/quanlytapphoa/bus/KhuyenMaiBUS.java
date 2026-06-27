package com.quanlytapphoa.bus;

import com.quanlytapphoa.dao.DatabaseSync;
import com.quanlytapphoa.model.KhuyenMai;
import com.quanlytapphoa.model.MatHang;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KhuyenMaiBUS {
    private final List<KhuyenMai> dsKhuyenMai;
    private final List<MatHang> dsMatHang;
    private final MatHangBUS matHangBUS;

    public KhuyenMaiBUS(List<KhuyenMai> dsKhuyenMai, List<MatHang> dsMatHang, MatHangBUS matHangBUS) {
        this.dsKhuyenMai = dsKhuyenMai;
        this.dsMatHang = dsMatHang;
        this.matHangBUS = matHangBUS;
    }

    public List<KhuyenMai> layDanhSach() {
        return new ArrayList<KhuyenMai>(dsKhuyenMai);
    }

    public KhuyenMai timTheoMa(String maKM) {
        String ma = chuanHoa(maKM);
        for (KhuyenMai khuyenMai : dsKhuyenMai) {
            if (ma.equalsIgnoreCase(khuyenMai.getMaKM())) {
                return khuyenMai;
            }
        }
        return null;
    }

    public List<KhuyenMai> timKiem(String tuKhoa) {
        String keyword = chuanHoa(tuKhoa).toLowerCase(Locale.ROOT);
        List<KhuyenMai> ketQua = new ArrayList<KhuyenMai>();
        for (KhuyenMai khuyenMai : dsKhuyenMai) {
            if (keyword.length() == 0
                    || coChua(khuyenMai.getMaKM(), keyword)
                    || coChua(khuyenMai.getTenKM(), keyword)
                    || coChua(khuyenMai.getLoaiKM(), keyword)
                    || coChua(khuyenMai.getGiaTriKM(), keyword)) {
                ketQua.add(khuyenMai);
            }
        }
        return ketQua;
    }

    public void themKhuyenMai(KhuyenMai khuyenMai) {
        kiemTraKhuyenMai(khuyenMai);
        if (timTheoMa(khuyenMai.getMaKM()) != null) {
            throw new BusinessException("Ma khuyen mai da ton tai");
        }
        DatabaseSync.luuKhuyenMai(khuyenMai);
        dsKhuyenMai.add(khuyenMai);
    }

    public void suaKhuyenMai(KhuyenMai khuyenMai) {
        kiemTraKhuyenMai(khuyenMai);
        KhuyenMai hienCo = timTheoMa(khuyenMai.getMaKM());
        if (hienCo == null) {
            throw new BusinessException("Khong tim thay khuyen mai can sua");
        }

        DatabaseSync.luuKhuyenMai(khuyenMai);
        hienCo.setTenKM(khuyenMai.getTenKM());
        hienCo.setLoaiKM(khuyenMai.getLoaiKM());
        hienCo.setGiaTriKM(khuyenMai.getGiaTriKM());
        hienCo.setNgayBatDau(khuyenMai.getNgayBatDau());
        hienCo.setNgayKetThuc(khuyenMai.getNgayKetThuc());
        hienCo.setTrangThai(khuyenMai.isTrangThai());
    }

    public void boKhuyenMai(String maKM) {
        KhuyenMai khuyenMai = timTheoMa(maKM);
        if (khuyenMai == null) {
            throw new BusinessException("Khong tim thay khuyen mai can bo");
        }
        DatabaseSync.boKhuyenMai(maKM);
        khuyenMai.setTrangThai(false);
        for (MatHang matHang : dsMatHang) {
            if (maKM != null && maKM.equalsIgnoreCase(matHang.getMaKM())) {
                matHang.setKhuyenMai(null);
            }
        }
    }

    public void ganKhuyenMaiChoSanPham(String maMatHang, String maKM) {
        MatHang matHang = matHangBUS.timTheoMa(maMatHang);
        KhuyenMai khuyenMai = timTheoMa(maKM);

        if (matHang == null) {
            throw new BusinessException("Khong tim thay mat hang");
        }
        if (khuyenMai == null || !khuyenMai.isTrangThai()) {
            throw new BusinessException("Khuyen mai khong ton tai hoac da ngung ap dung");
        }

        DatabaseSync.ganKhuyenMaiChoSanPham(maMatHang, maKM);
        matHang.setKhuyenMai(khuyenMai);
    }

    public boolean conHieuLuc(KhuyenMai khuyenMai, Date ngayKiemTra) {
        if (khuyenMai == null || !khuyenMai.isTrangThai()) {
            return false;
        }

        Date ngay = ngayKiemTra == null ? new Date() : ngayKiemTra;
        Date ngayBatDau = khuyenMai.getNgayBatDau();
        Date ngayKetThuc = khuyenMai.getNgayKetThuc();
        boolean sauNgayBatDau = ngayBatDau == null || !ngay.before(ngayBatDau);
        boolean truocNgayKetThuc = ngayKetThuc == null || !ngay.after(ngayKetThuc);
        return sauNgayBatDau && truocNgayKetThuc;
    }

    public double tinhTienGiam(KhuyenMai khuyenMai, double giaTriApDung, Date ngayApDung) {
        if (giaTriApDung <= 0 || !conHieuLuc(khuyenMai, ngayApDung)) {
            return 0;
        }

        double giaTri = docGiaTriKhuyenMai(khuyenMai.getGiaTriKM());
        double tienGiam = laKhuyenMaiPhanTram(khuyenMai)
                ? giaTriApDung * giaTri / 100
                : giaTri;
        return Math.min(Math.max(tienGiam, 0), giaTriApDung);
    }

    private void kiemTraKhuyenMai(KhuyenMai khuyenMai) {
        if (khuyenMai == null) {
            throw new BusinessException("Khuyen mai khong duoc rong");
        }
        if (chuanHoa(khuyenMai.getMaKM()).length() == 0) {
            throw new BusinessException("Ma khuyen mai khong duoc rong");
        }
        if (chuanHoa(khuyenMai.getTenKM()).length() == 0) {
            throw new BusinessException("Ten khuyen mai khong duoc rong");
        }
        if (khuyenMai.getNgayBatDau() != null
                && khuyenMai.getNgayKetThuc() != null
                && khuyenMai.getNgayKetThuc().before(khuyenMai.getNgayBatDau())) {
            throw new BusinessException("Ngay ket thuc khong duoc truoc ngay bat dau");
        }
    }

    private boolean laKhuyenMaiPhanTram(KhuyenMai khuyenMai) {
        String loai = khuyenMai.getLoaiKM() == null ? "" : khuyenMai.getLoaiKM().toLowerCase(Locale.ROOT);
        String giaTri = khuyenMai.getGiaTriKM() == null ? "" : khuyenMai.getGiaTriKM();
        return loai.contains("phan tram")
                || loai.contains("percent")
                || loai.contains("%")
                || giaTri.contains("%");
    }

    private double docGiaTriKhuyenMai(String giaTriKM) {
        if (giaTriKM == null) {
            return 0;
        }

        String normalized = giaTriKM.trim().replace("%", "").replace(",", ".");
        normalized = normalized.replaceAll("[^0-9.\\-]", "");
        if (normalized.length() == 0 || "-".equals(normalized)) {
            return 0;
        }

        try {
            return Double.parseDouble(normalized);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private boolean coChua(String value, String keyword) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(keyword);
    }

    private String chuanHoa(String value) {
        return value == null ? "" : value.trim();
    }
}
