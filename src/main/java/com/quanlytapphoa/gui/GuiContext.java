package com.quanlytapphoa.gui;

import com.quanlytapphoa.App;
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
import com.quanlytapphoa.bus.TaiKhoanBUS;

public final class GuiContext {
    private static boolean initialized;
    private static AuthBUS authBUS;
    private static LoaiMatHangBUS loaiMatHangBUS;
    private static MatHangBUS matHangBUS;
    private static NhanVienBUS nhanVienBUS;
    private static NhaCungCapBUS nhaCungCapBUS;
    private static PhuongThucThanhToanBUS phuongThucThanhToanBUS;
    private static KhuyenMaiBUS khuyenMaiBUS;
    private static HoaDonBUS hoaDonBUS;
    private static PhieuNhapBUS phieuNhapBUS;
    private static BaoCaoBUS baoCaoBUS;
    private static TaiKhoanBUS taiKhoanBUS;

    private GuiContext() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        App.khoiTaoDuLieu();
        authBUS = new AuthBUS(App.DS_TAI_KHOAN);
        loaiMatHangBUS = new LoaiMatHangBUS(App.DS_LOAI_MAT_HANG);
        matHangBUS = new MatHangBUS(App.DS_MAT_HANG);
        nhanVienBUS = new NhanVienBUS(App.DS_NHAN_VIEN);
        nhaCungCapBUS = new NhaCungCapBUS(App.DS_NHA_CUNG_CAP, App.DS_PHIEU_NHAP_HANG);
        phuongThucThanhToanBUS = new PhuongThucThanhToanBUS(App.DS_PHUONG_THUC_THANH_TOAN);
        khuyenMaiBUS = new KhuyenMaiBUS(App.DS_KHUYEN_MAI, App.DS_MAT_HANG, matHangBUS);
        hoaDonBUS = new HoaDonBUS(
                App.DS_HOA_DON_BAN_HANG,
                App.DS_CHI_TIET_HOA_DON,
                matHangBUS,
                nhanVienBUS,
                phuongThucThanhToanBUS,
                khuyenMaiBUS
        );
        phieuNhapBUS = new PhieuNhapBUS(
                App.DS_PHIEU_NHAP_HANG,
                App.DS_CHI_TIET_NHAP_HANG,
                matHangBUS,
                nhaCungCapBUS,
                nhanVienBUS
        );
        baoCaoBUS = new BaoCaoBUS(
                App.DS_HOA_DON_BAN_HANG,
                App.DS_CHI_TIET_HOA_DON,
                App.DS_PHIEU_NHAP_HANG,
                App.DS_MAT_HANG
        );
        taiKhoanBUS = new TaiKhoanBUS(App.DS_TAI_KHOAN, nhanVienBUS);
        initialized = true;
    }

    public static AuthBUS authBUS() {
        init();
        return authBUS;
    }

    public static LoaiMatHangBUS loaiMatHangBUS() {
        init();
        return loaiMatHangBUS;
    }

    public static MatHangBUS matHangBUS() {
        init();
        return matHangBUS;
    }

    public static NhanVienBUS nhanVienBUS() {
        init();
        return nhanVienBUS;
    }

    public static NhaCungCapBUS nhaCungCapBUS() {
        init();
        return nhaCungCapBUS;
    }

    public static PhuongThucThanhToanBUS phuongThucThanhToanBUS() {
        init();
        return phuongThucThanhToanBUS;
    }

    public static KhuyenMaiBUS khuyenMaiBUS() {
        init();
        return khuyenMaiBUS;
    }

    public static HoaDonBUS hoaDonBUS() {
        init();
        return hoaDonBUS;
    }

    public static PhieuNhapBUS phieuNhapBUS() {
        init();
        return phieuNhapBUS;
    }

    public static BaoCaoBUS baoCaoBUS() {
        init();
        return baoCaoBUS;
    }

    public static TaiKhoanBUS taiKhoanBUS() {
        init();
        return taiKhoanBUS;
    }
}
