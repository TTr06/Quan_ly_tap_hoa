package com.quanlytapphoa.model;

public class PhuongThucThanhToan {
    private String maPT;
    private String tenPT;
    private String moTa;
    private boolean trangThai;

    public PhuongThucThanhToan() {
    }

    public PhuongThucThanhToan(String maPT, String tenPT, String moTa, boolean trangThai) {
        this.maPT = maPT;
        this.tenPT = tenPT;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public String getMaPT() {
        return maPT;
    }

    public void setMaPT(String maPT) {
        this.maPT = maPT;
    }

    public String getTenPT() {
        return tenPT;
    }

    public void setTenPT(String tenPT) {
        this.tenPT = tenPT;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "PhuongThucThanhToan{"
                + "maPT='" + maPT + '\''
                + ", tenPT='" + tenPT + '\''
                + ", moTa='" + moTa + '\''
                + ", trangThai=" + trangThai
                + '}';
    }
}
