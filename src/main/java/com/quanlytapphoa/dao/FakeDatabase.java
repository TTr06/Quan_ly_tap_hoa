package com.quanlytapphoa.dao;

import com.quanlytapphoa.model.BaoCao;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FakeDatabase {
    public List<LoaiMatHang> layDanhSachLoaiMatHang() {
        List<LoaiMatHang> dsLoaiMatHang = new ArrayList<LoaiMatHang>();
        dsLoaiMatHang.add(new LoaiMatHang("L01", "Thuc pham", true));
        dsLoaiMatHang.add(new LoaiMatHang("L02", "Do uong", true));
        dsLoaiMatHang.add(new LoaiMatHang("L03", "Hoa pham", true));
        return dsLoaiMatHang;
    }

    public List<KhuyenMai> layDanhSachKhuyenMai() {
        List<KhuyenMai> dsKhuyenMai = new ArrayList<KhuyenMai>();
        dsKhuyenMai.add(new KhuyenMai("KM001", "Giam 10 phan tram", "phan tram", "10%", new Date(0), null, true));
        dsKhuyenMai.add(new KhuyenMai("KM002", "Giam 5000 dong", "tien mat", "5000", new Date(0), null, true));
        return dsKhuyenMai;
    }

    public List<MatHang> layDanhSachMatHang(List<KhuyenMai> dsKhuyenMai) {
        List<MatHang> dsMatHang = new ArrayList<MatHang>();
        MatHang duong = new MatHang("MH001", "Duong cat", "L01", null, "kg", 18000, 22000, 100, true);
        MatHang sua = new MatHang("MH002", "Sua hop", "L02", "KM001", "hop", 7000, 10000, 80, true);
        MatHang nuocRuaChen = new MatHang("MH003", "Nuoc rua chen", "L03", "KM002", "chai", 18000, 25000, 40, true);

        sua.setKhuyenMai(timKhuyenMai(dsKhuyenMai, "KM001"));
        nuocRuaChen.setKhuyenMai(timKhuyenMai(dsKhuyenMai, "KM002"));

        dsMatHang.add(duong);
        dsMatHang.add(sua);
        dsMatHang.add(nuocRuaChen);
        return dsMatHang;
    }

    public List<NhanVien> layDanhSachNhanVien() {
        List<NhanVien> dsNhanVien = new ArrayList<NhanVien>();
        dsNhanVien.add(new NhanVien("NV001", "Quan tri vien", "0900000000", "Cua hang", "Admin", new Date(), true));
        dsNhanVien.add(new NhanVien("NV002", "Nhan vien ban hang", "0911111111", "Cua hang", "Nhan vien", new Date(), true));
        return dsNhanVien;
    }

    public List<TaiKhoan> layDanhSachTaiKhoan() {
        List<TaiKhoan> dsTaiKhoan = new ArrayList<TaiKhoan>();
        dsTaiKhoan.add(new TaiKhoan("admin", "123456", "ADMIN", "NV001", true));
        dsTaiKhoan.add(new TaiKhoan("nhanvien", "123456", "NHAN_VIEN", "NV002", true));
        return dsTaiKhoan;
    }

    public List<NhaCungCap> layDanhSachNhaCungCap() {
        List<NhaCungCap> dsNhaCungCap = new ArrayList<NhaCungCap>();
        dsNhaCungCap.add(new NhaCungCap("NCC001", "Nha cung cap tong hop", "TP HCM", "0280000000", "ncc@example.com"));
        dsNhaCungCap.add(new NhaCungCap("NCC002", "Dai ly do uong", "Dong Nai", "0251000000", "douong@example.com"));
        return dsNhaCungCap;
    }

    public List<PhuongThucThanhToan> layDanhSachPhuongThucThanhToan() {
        List<PhuongThucThanhToan> dsPhuongThuc = new ArrayList<PhuongThucThanhToan>();
        dsPhuongThuc.add(new PhuongThucThanhToan("PT001", "Tien mat", "Thanh toan truc tiep", true));
        dsPhuongThuc.add(new PhuongThucThanhToan("PT002", "Chuyen khoan", "Thanh toan qua ngan hang", true));
        return dsPhuongThuc;
    }

    public List<HoaDonBanHang> layDanhSachHoaDonBanHang() {
        return new ArrayList<HoaDonBanHang>();
    }

    public List<ChiTietHoaDon> layDanhSachChiTietHoaDon() {
        return new ArrayList<ChiTietHoaDon>();
    }

    public List<PhieuNhapHang> layDanhSachPhieuNhapHang() {
        return new ArrayList<PhieuNhapHang>();
    }

    public List<ChiTietNhapHang> layDanhSachChiTietNhapHang() {
        return new ArrayList<ChiTietNhapHang>();
    }

    public List<BaoCao> layDanhSachBaoCao() {
        return new ArrayList<BaoCao>();
    }

    private KhuyenMai timKhuyenMai(List<KhuyenMai> dsKhuyenMai, String maKM) {
        for (KhuyenMai khuyenMai : dsKhuyenMai) {
            if (maKM.equalsIgnoreCase(khuyenMai.getMaKM())) {
                return khuyenMai;
            }
        }
        return null;
    }
}
