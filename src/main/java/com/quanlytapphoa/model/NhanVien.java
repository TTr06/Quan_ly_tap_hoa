package com.quanlytapphoa.model;

import java.util.Date;

public class NhanVien {
    private String maNV;
    private String hoTen;
    private String soDienThoai;
    private String diaChi;
    private String chucVu;
    private Date ngayVaoLam;
    private boolean trangThai;

    public NhanVien() {
    }

    public NhanVien(
            String maNV,
            String hoTen,
            String soDienThoai,
            String diaChi,
            String chucVu,
            Date ngayVaoLam,
            boolean trangThai
    ) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        setNgayVaoLam(ngayVaoLam);
        this.trangThai = trangThai;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public Date getNgayVaoLam() {
        return copyDate(ngayVaoLam);
    }

    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = copyDate(ngayVaoLam);
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    private Date copyDate(Date date) {
        return date == null ? null : new Date(date.getTime());
    }

    @Override
    public String toString() {
        return "NhanVien{"
                + "maNV='" + maNV + '\''
                + ", hoTen='" + hoTen + '\''
                + ", soDienThoai='" + soDienThoai + '\''
                + ", diaChi='" + diaChi + '\''
                + ", chucVu='" + chucVu + '\''
                + ", ngayVaoLam=" + ngayVaoLam
                + ", trangThai=" + trangThai
                + '}';
    }
}
