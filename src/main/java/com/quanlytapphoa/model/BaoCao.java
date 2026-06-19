package com.quanlytapphoa.model;

import java.util.Date;

public class BaoCao {
    private String maBC;
    private String tenBC;
    private String loaiBC;
    private Date ngayBC;

    public BaoCao() {
    }

    public BaoCao(String maBC, String tenBC, String loaiBC, Date ngayBC) {
        this.maBC = maBC;
        this.tenBC = tenBC;
        this.loaiBC = loaiBC;
        setNgayBC(ngayBC);
    }

    public String getMaBC() {
        return maBC;
    }

    public void setMaBC(String maBC) {
        this.maBC = maBC;
    }

    public String getTenBC() {
        return tenBC;
    }

    public void setTenBC(String tenBC) {
        this.tenBC = tenBC;
    }

    public String getLoaiBC() {
        return loaiBC;
    }

    public void setLoaiBC(String loaiBC) {
        this.loaiBC = loaiBC;
    }

    public Date getNgayBC() {
        return copyDate(ngayBC);
    }

    public void setNgayBC(Date ngayBC) {
        this.ngayBC = copyDate(ngayBC);
    }

    private Date copyDate(Date date) {
        return date == null ? null : new Date(date.getTime());
    }

    @Override
    public String toString() {
        return "BaoCao{"
                + "maBC='" + maBC + '\''
                + ", tenBC='" + tenBC + '\''
                + ", loaiBC='" + loaiBC + '\''
                + ", ngayBC=" + ngayBC
                + '}';
    }
}
