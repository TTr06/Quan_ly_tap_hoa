package com.quanlytapphoa.model;

public class MatHang {
    private String maMatHang;
    private String tenMatHang;
    private String maLoai;
    private String maKM;
    private String donViTinh;
    private double giaNhap;
    private double giaBan;
    private int soLuong;
    private boolean trangThai;
    private LoaiMatHang loaiMatHang;
    private KhuyenMai khuyenMai;

    public MatHang() {
    }

    public MatHang(
            String maMatHang,
            String tenMatHang,
            String maLoai,
            String maKM,
            String donViTinh,
            double giaNhap,
            double giaBan,
            int soLuong,
            boolean trangThai
    ) {
        this.maMatHang = maMatHang;
        this.tenMatHang = tenMatHang;
        this.maLoai = maLoai;
        this.maKM = maKM;
        this.donViTinh = donViTinh;
        setGiaNhap(giaNhap);
        setGiaBan(giaBan);
        setSoLuong(soLuong);
        this.trangThai = trangThai;
    }

    public String getMaMatHang() {
        return maMatHang;
    }

    public void setMaMatHang(String maMatHang) {
        this.maMatHang = maMatHang;
    }

    public String getTenMatHang() {
        return tenMatHang;
    }

    public void setTenMatHang(String tenMatHang) {
        this.tenMatHang = tenMatHang;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        if (giaNhap < 0) {
            throw new IllegalArgumentException("Gia nhap khong duoc am");
        }
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        if (giaBan < 0) {
            throw new IllegalArgumentException("Gia ban khong duoc am");
        }
        this.giaBan = giaBan;
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

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public LoaiMatHang getLoaiMatHang() {
        return loaiMatHang;
    }

    public void setLoaiMatHang(LoaiMatHang loaiMatHang) {
        this.loaiMatHang = loaiMatHang;
        this.maLoai = loaiMatHang == null ? null : loaiMatHang.getMaLoai();
    }

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
        this.maKM = khuyenMai == null ? null : khuyenMai.getMaKM();
    }

    @Override
    public String toString() {
        return "MatHang{"
                + "maMatHang='" + maMatHang + '\''
                + ", tenMatHang='" + tenMatHang + '\''
                + ", maLoai='" + maLoai + '\''
                + ", maKM='" + maKM + '\''
                + ", donViTinh='" + donViTinh + '\''
                + ", giaNhap=" + giaNhap
                + ", giaBan=" + giaBan
                + ", soLuong=" + soLuong
                + ", trangThai=" + trangThai
                + '}';
    }
}
