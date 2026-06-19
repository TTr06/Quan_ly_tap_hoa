package com.quanlytapphoa.model;

public class LoaiMatHang {
    private String maLoai;
    private String tenLoai;
    private boolean trangThai;

    public LoaiMatHang() {
    }

    public LoaiMatHang(String maLoai, String tenLoai, boolean trangThai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.trangThai = trangThai;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "LoaiMatHang{"
                + "maLoai='" + maLoai + '\''
                + ", tenLoai='" + tenLoai + '\''
                + ", trangThai=" + trangThai
                + '}';
    }
}
