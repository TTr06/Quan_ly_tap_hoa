package com.quanlytapphoa.model;

public class ChiTietHoaDon {
    private String maHD;
    private String maMatHang;
    private int soLuong;
    private double donGia;
    private double giaTriKM;
    private double thanhTien;
    private double thanhTienSauKM;
    private MatHang matHang;
    private HoaDonBanHang hoaDonBanHang;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maHD, String maMatHang, int soLuong, double donGia, double giaTriKM) {
        this.maHD = maHD;
        this.maMatHang = maMatHang;
        setSoLuong(soLuong);
        setDonGia(donGia);
        setGiaTriKM(giaTriKM);
    }

    public ChiTietHoaDon(MatHang matHang, int soLuong) {
        setMatHang(matHang);
        setSoLuong(soLuong);
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
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

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        if (donGia < 0) {
            throw new IllegalArgumentException("Don gia khong duoc am");
        }
        this.donGia = donGia;
    }

    public double getGiaTriKM() {
        return giaTriKM;
    }

    public void setGiaTriKM(double giaTriKM) {
        if (giaTriKM < 0) {
            throw new IllegalArgumentException("Gia tri khuyen mai khong duoc am");
        }
        this.giaTriKM = giaTriKM;
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

    public double getThanhTienSauKM() {
        return thanhTienSauKM;
    }

    public void setThanhTienSauKM(double thanhTienSauKM) {
        if (thanhTienSauKM < 0) {
            throw new IllegalArgumentException("Thanh tien sau khuyen mai khong duoc am");
        }
        this.thanhTienSauKM = thanhTienSauKM;
    }

    public MatHang getMatHang() {
        return matHang;
    }

    public void setMatHang(MatHang matHang) {
        this.matHang = matHang;
        this.maMatHang = matHang == null ? null : matHang.getMaMatHang();
    }

    public HoaDonBanHang getHoaDonBanHang() {
        return hoaDonBanHang;
    }

    public void setHoaDonBanHang(HoaDonBanHang hoaDonBanHang) {
        this.hoaDonBanHang = hoaDonBanHang;
        this.maHD = hoaDonBanHang == null ? null : hoaDonBanHang.getMaHD();
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{"
                + "maHD='" + maHD + '\''
                + ", maMatHang='" + maMatHang + '\''
                + ", soLuong=" + soLuong
                + ", donGia=" + donGia
                + ", giaTriKM=" + giaTriKM
                + ", thanhTien=" + thanhTien
                + ", thanhTienSauKM=" + thanhTienSauKM
                + '}';
    }
}
