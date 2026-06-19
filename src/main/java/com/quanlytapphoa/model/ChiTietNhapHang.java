package com.quanlytapphoa.model;

public class ChiTietNhapHang {
    private String maPhieu;
    private String maMatHang;
    private int soLuong;
    private double donGiaNhap;
    private double thanhTien;
    private MatHang matHang;
    private PhieuNhapHang phieuNhapHang;

    public ChiTietNhapHang() {
    }

    public ChiTietNhapHang(String maPhieu, String maMatHang, int soLuong, double donGiaNhap) {
        this.maPhieu = maPhieu;
        this.maMatHang = maMatHang;
        setSoLuong(soLuong);
        setDonGiaNhap(donGiaNhap);
    }

    public ChiTietNhapHang(MatHang matHang, int soLuong, double donGiaNhap) {
        setMatHang(matHang);
        setSoLuong(soLuong);
        setDonGiaNhap(donGiaNhap);
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaMatHang() {
        return maMatHang;
    }

    public void setMaMatHang(String maMatHang) {
        this.maMatHang = maMatHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong < 0) {
            throw new IllegalArgumentException("So luong khong duoc am");
        }
        this.soLuong = soLuong;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        if (donGiaNhap < 0) {
            throw new IllegalArgumentException("Don gia nhap khong duoc am");
        }
        this.donGiaNhap = donGiaNhap;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        if (thanhTien < 0) {
            throw new IllegalArgumentException("Thanh tien khong duoc am");
        }
        this.thanhTien = thanhTien;
    }

    public MatHang getMatHang() {
        return matHang;
    }

    public void setMatHang(MatHang matHang) {
        this.matHang = matHang;
        this.maMatHang = matHang == null ? null : matHang.getMaMatHang();
    }

    public PhieuNhapHang getPhieuNhapHang() {
        return phieuNhapHang;
    }

    public void setPhieuNhapHang(PhieuNhapHang phieuNhapHang) {
        this.phieuNhapHang = phieuNhapHang;
        this.maPhieu = phieuNhapHang == null ? null : phieuNhapHang.getMaPhieu();
    }

    @Override
    public String toString() {
        return "ChiTietNhapHang{"
                + "maPhieu='" + maPhieu + '\''
                + ", maMatHang='" + maMatHang + '\''
                + ", soLuong=" + soLuong
                + ", donGiaNhap=" + donGiaNhap
                + ", thanhTien=" + thanhTien
                + '}';
    }
}
