package com.quanlytapphoa.model;

import java.util.Date;

public class PhieuNhapHang {
    private String maPhieu;
    private Date ngayNhap;
    private double tongTien;
    private String maNCC;
    private String maNV;
    private NhaCungCap nhaCungCap;
    private NhanVien nhanVien;

    public PhieuNhapHang() {
    }

    public PhieuNhapHang(String maPhieu, Date ngayNhap, NhaCungCap nhaCungCap, NhanVien nhanVien) {
        this.maPhieu = maPhieu;
        setNgayNhap(ngayNhap);
        setNhaCungCap(nhaCungCap);
        setNhanVien(nhanVien);
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public Date getNgayNhap() {
        return copyDate(ngayNhap);
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = copyDate(ngayNhap);
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        if (tongTien < 0) {
            throw new IllegalArgumentException("Tong tien khong duoc am");
        }
        this.tongTien = tongTien;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
        this.maNCC = nhaCungCap == null ? null : nhaCungCap.getMaNCC();
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        this.maNV = nhanVien == null ? null : nhanVien.getMaNV();
    }

    private Date copyDate(Date date) {
        return date == null ? null : new Date(date.getTime());
    }

    @Override
    public String toString() {
        return "PhieuNhapHang{"
                + "maPhieu='" + maPhieu + '\''
                + ", ngayNhap=" + ngayNhap
                + ", tongTien=" + tongTien
                + ", maNCC='" + maNCC + '\''
                + ", maNV='" + maNV + '\''
                + '}';
    }
}
