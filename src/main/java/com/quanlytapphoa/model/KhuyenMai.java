package com.quanlytapphoa.model;

import java.util.Date;

public class KhuyenMai {
    private String maKM;
    private String tenKM;
    private String loaiKM;
    private String giaTriKM;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private boolean trangThai;

    public KhuyenMai() {
    }

    public KhuyenMai(
            String maKM,
            String tenKM,
            String loaiKM,
            String giaTriKM,
            Date ngayBatDau,
            Date ngayKetThuc,
            boolean trangThai
    ) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.loaiKM = loaiKM;
        this.giaTriKM = giaTriKM;
        setNgayBatDau(ngayBatDau);
        setNgayKetThuc(ngayKetThuc);
        this.trangThai = trangThai;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public String getLoaiKM() {
        return loaiKM;
    }

    public void setLoaiKM(String loaiKM) {
        this.loaiKM = loaiKM;
    }

    public String getGiaTriKM() {
        return giaTriKM;
    }

    public void setGiaTriKM(String giaTriKM) {
        this.giaTriKM = giaTriKM;
    }

    public Date getNgayBatDau() {
        return copyDate(ngayBatDau);
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = copyDate(ngayBatDau);
    }

    public Date getNgayKetThuc() {
        return copyDate(ngayKetThuc);
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = copyDate(ngayKetThuc);
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
        return "KhuyenMai{"
                + "maKM='" + maKM + '\''
                + ", tenKM='" + tenKM + '\''
                + ", loaiKM='" + loaiKM + '\''
                + ", giaTriKM='" + giaTriKM + '\''
                + ", ngayBatDau=" + ngayBatDau
                + ", ngayKetThuc=" + ngayKetThuc
                + ", trangThai=" + trangThai
                + '}';
    }
}
