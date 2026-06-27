package com.quanlytapphoa.dao;

import com.quanlytapphoa.model.ChiTietHoaDon;
import com.quanlytapphoa.model.ChiTietNhapHang;
import com.quanlytapphoa.model.HoaDonBanHang;
import com.quanlytapphoa.model.KhuyenMai;
import com.quanlytapphoa.model.LoaiMatHang;
import com.quanlytapphoa.model.MatHang;
import com.quanlytapphoa.model.NhaCungCap;
import com.quanlytapphoa.model.NhanVien;
import com.quanlytapphoa.model.PhieuNhapHang;
import com.quanlytapphoa.model.PhuongThucThanhToan;
import com.quanlytapphoa.model.TaiKhoan;
import java.util.List;

public final class DatabaseSync {
    private static SqlServerDatabase database;

    private DatabaseSync() {
    }

    public static void enable(SqlServerDatabase sqlServerDatabase) {
        database = sqlServerDatabase;
    }

    public static void disable() {
        database = null;
    }

    public static boolean isEnabled() {
        return database != null;
    }

    public static void luuLoaiMatHang(LoaiMatHang loaiMatHang) {
        if (isEnabled()) {
            database.luuLoaiMatHang(loaiMatHang);
        }
    }

    public static void xoaLoaiMatHang(String maLoai) {
        if (isEnabled()) {
            database.xoaLoaiMatHang(maLoai);
        }
    }

    public static void luuMatHang(MatHang matHang) {
        if (isEnabled()) {
            database.luuMatHang(matHang);
        }
    }

    public static void xoaMatHang(String maMatHang) {
        if (isEnabled()) {
            database.xoaMatHang(maMatHang);
        }
    }

    public static void capNhatTonKho(MatHang matHang) {
        if (isEnabled()) {
            database.capNhatTonKho(matHang);
        }
    }

    public static void luuNhanVien(NhanVien nhanVien) {
        if (isEnabled()) {
            database.luuNhanVien(nhanVien);
        }
    }

    public static void xoaNhanVien(String maNV) {
        if (isEnabled()) {
            database.xoaNhanVien(maNV);
        }
    }

    public static void luuNhaCungCap(NhaCungCap nhaCungCap) {
        if (isEnabled()) {
            database.luuNhaCungCap(nhaCungCap);
        }
    }

    public static void xoaNhaCungCap(String maNCC) {
        if (isEnabled()) {
            database.xoaNhaCungCap(maNCC);
        }
    }

    public static void luuPhuongThucThanhToan(PhuongThucThanhToan phuongThuc) {
        if (isEnabled()) {
            database.luuPhuongThucThanhToan(phuongThuc);
        }
    }

    public static void xoaPhuongThucThanhToan(String maPT) {
        if (isEnabled()) {
            database.xoaPhuongThucThanhToan(maPT);
        }
    }

    public static void luuKhuyenMai(KhuyenMai khuyenMai) {
        if (isEnabled()) {
            database.luuKhuyenMai(khuyenMai);
        }
    }

    public static void boKhuyenMai(String maKM) {
        if (isEnabled()) {
            database.boKhuyenMai(maKM);
        }
    }

    public static void ganKhuyenMaiChoSanPham(String maMatHang, String maKM) {
        if (isEnabled()) {
            database.ganKhuyenMaiChoSanPham(maMatHang, maKM);
        }
    }

    public static void luuTaiKhoan(TaiKhoan taiKhoan) {
        if (isEnabled()) {
            database.luuTaiKhoan(taiKhoan);
        }
    }

    public static void xoaTaiKhoan(String tenDangNhap) {
        if (isEnabled()) {
            database.xoaTaiKhoan(tenDangNhap);
        }
    }

    public static void themHoaDon(HoaDonBanHang hoaDon, List<ChiTietHoaDon> chiTietHoaDons) {
        if (isEnabled()) {
            database.themHoaDon(hoaDon, chiTietHoaDons);
        }
    }

    public static void huyHoaDon(String maHD, List<ChiTietHoaDon> chiTietHoaDons) {
        if (isEnabled()) {
            database.huyHoaDon(maHD, chiTietHoaDons);
        }
    }

    public static void themPhieuNhap(PhieuNhapHang phieuNhap, List<ChiTietNhapHang> chiTietNhapHangs) {
        if (isEnabled()) {
            database.themPhieuNhap(phieuNhap, chiTietNhapHangs);
        }
    }

    public static void xoaPhieuNhap(String maPhieu, List<ChiTietNhapHang> chiTietNhapHangs) {
        if (isEnabled()) {
            database.xoaPhieuNhap(maPhieu, chiTietNhapHangs);
        }
    }
}
