package com.quanlytapphoa;

import com.quanlytapphoa.bus.AuthBUS;
import com.quanlytapphoa.bus.BaoCaoBUS;
import com.quanlytapphoa.bus.HoaDonBUS;
import com.quanlytapphoa.bus.KhuyenMaiBUS;
import com.quanlytapphoa.bus.LoaiMatHangBUS;
import com.quanlytapphoa.bus.MatHangBUS;
import com.quanlytapphoa.bus.NhanVienBUS;
import com.quanlytapphoa.bus.NhaCungCapBUS;
import com.quanlytapphoa.bus.PhieuNhapBUS;
import com.quanlytapphoa.bus.PhuongThucThanhToanBUS;
import com.quanlytapphoa.dao.DatabaseSync;
import com.quanlytapphoa.dao.SqlServerDatabase;
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
import com.quanlytapphoa.ui.ConsoleUI;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static final List<LoaiMatHang> DS_LOAI_MAT_HANG = new ArrayList<LoaiMatHang>();
    public static final List<MatHang> DS_MAT_HANG = new ArrayList<MatHang>();
    public static final List<KhuyenMai> DS_KHUYEN_MAI = new ArrayList<KhuyenMai>();
    public static final List<NhaCungCap> DS_NHA_CUNG_CAP = new ArrayList<NhaCungCap>();
    public static final List<NhanVien> DS_NHAN_VIEN = new ArrayList<NhanVien>();
    public static final List<PhuongThucThanhToan> DS_PHUONG_THUC_THANH_TOAN = new ArrayList<PhuongThucThanhToan>();
    public static final List<HoaDonBanHang> DS_HOA_DON_BAN_HANG = new ArrayList<HoaDonBanHang>();
    public static final List<ChiTietHoaDon> DS_CHI_TIET_HOA_DON = new ArrayList<ChiTietHoaDon>();
    public static final List<PhieuNhapHang> DS_PHIEU_NHAP_HANG = new ArrayList<PhieuNhapHang>();
    public static final List<ChiTietNhapHang> DS_CHI_TIET_NHAP_HANG = new ArrayList<ChiTietNhapHang>();
    public static final List<TaiKhoan> DS_TAI_KHOAN = new ArrayList<TaiKhoan>();

    public static void main(String[] args) {
        khoiTaoDuLieu();

        AuthBUS authBUS = new AuthBUS(DS_TAI_KHOAN);
        LoaiMatHangBUS loaiMatHangBUS = new LoaiMatHangBUS(DS_LOAI_MAT_HANG);
        MatHangBUS matHangBUS = new MatHangBUS(DS_MAT_HANG);
        NhanVienBUS nhanVienBUS = new NhanVienBUS(DS_NHAN_VIEN);
        NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS(DS_NHA_CUNG_CAP, DS_PHIEU_NHAP_HANG);
        PhuongThucThanhToanBUS phuongThucThanhToanBUS = new PhuongThucThanhToanBUS(DS_PHUONG_THUC_THANH_TOAN);
        KhuyenMaiBUS khuyenMaiBUS = new KhuyenMaiBUS(DS_KHUYEN_MAI, DS_MAT_HANG, matHangBUS);
        HoaDonBUS hoaDonBUS = new HoaDonBUS(
                DS_HOA_DON_BAN_HANG,
                DS_CHI_TIET_HOA_DON,
                matHangBUS,
                nhanVienBUS,
                phuongThucThanhToanBUS,
                khuyenMaiBUS
        );
        PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS(
                DS_PHIEU_NHAP_HANG,
                DS_CHI_TIET_NHAP_HANG,
                matHangBUS,
                nhaCungCapBUS,
                nhanVienBUS
        );
        BaoCaoBUS baoCaoBUS = new BaoCaoBUS(
                DS_HOA_DON_BAN_HANG,
                DS_CHI_TIET_HOA_DON,
                DS_PHIEU_NHAP_HANG,
                DS_MAT_HANG
        );

        ConsoleUI consoleUI = new ConsoleUI(
                authBUS,
                loaiMatHangBUS,
                matHangBUS,
                nhanVienBUS,
                nhaCungCapBUS,
                phuongThucThanhToanBUS,
                khuyenMaiBUS,
                hoaDonBUS,
                phieuNhapBUS,
                baoCaoBUS
        );
        consoleUI.start();
    }

    public static void khoiTaoDuLieu() {
        if (!DS_TAI_KHOAN.isEmpty() || !DS_MAT_HANG.isEmpty()) {
            return;
        }
        napDuLieuTuDatabase();
    }

    public static void napDuLieuTuDatabase() {
        xoaDuLieuDangCo();
        napDuLieuTuSqlServer();
    }

    private static void xoaDuLieuDangCo() {
        DS_LOAI_MAT_HANG.clear();
        DS_MAT_HANG.clear();
        DS_KHUYEN_MAI.clear();
        DS_NHA_CUNG_CAP.clear();
        DS_NHAN_VIEN.clear();
        DS_PHUONG_THUC_THANH_TOAN.clear();
        DS_HOA_DON_BAN_HANG.clear();
        DS_CHI_TIET_HOA_DON.clear();
        DS_PHIEU_NHAP_HANG.clear();
        DS_CHI_TIET_NHAP_HANG.clear();
        DS_TAI_KHOAN.clear();
    }

    private static void napDuLieuTuSqlServer() {
        SqlServerDatabase database = new SqlServerDatabase();
        database.kiemTraKetNoi();

        DS_LOAI_MAT_HANG.addAll(database.layDanhSachLoaiMatHang());
        DS_KHUYEN_MAI.addAll(database.layDanhSachKhuyenMai());
        DS_MAT_HANG.addAll(database.layDanhSachMatHang(DS_KHUYEN_MAI));
        DS_NHA_CUNG_CAP.addAll(database.layDanhSachNhaCungCap());
        DS_NHAN_VIEN.addAll(database.layDanhSachNhanVien());
        DS_PHUONG_THUC_THANH_TOAN.addAll(database.layDanhSachPhuongThucThanhToan());
        DS_HOA_DON_BAN_HANG.addAll(database.layDanhSachHoaDonBanHang(DS_NHAN_VIEN, DS_PHUONG_THUC_THANH_TOAN));
        DS_CHI_TIET_HOA_DON.addAll(database.layDanhSachChiTietHoaDon());
        DS_PHIEU_NHAP_HANG.addAll(database.layDanhSachPhieuNhapHang(DS_NHA_CUNG_CAP, DS_NHAN_VIEN));
        DS_CHI_TIET_NHAP_HANG.addAll(database.layDanhSachChiTietNhapHang());
        DS_TAI_KHOAN.addAll(database.layDanhSachTaiKhoan());

        DatabaseSync.enable(database);
    }
}
