package com.quanlytapphoa.model;

import java.util.Date;

public class HoaDonBanHang {
    private String maHD;
    private Date ngayLap;
    private double tongTien;
    private double giaTriKM;
    private double thanhTien;
    private String phuongThucThanhToan;
    private String maNV;
    private String maPT;
    private NhanVien nhanVien;
    private PhuongThucThanhToan phuongThucThanhToanRef;

    public HoaDonBanHang() {
    }

    public HoaDonBanHang(
            String maHD,
            Date ngayLap,
            NhanVien nhanVien,
            PhuongThucThanhToan phuongThucThanhToanRef
    ) {
        this.maHD = maHD;
        setNgayLap(ngayLap);
        setNhanVien(nhanVien);
        setPhuongThucThanhToanRef(phuongThucThanhToanRef);
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public Date getNgayLap() {
        return copyDate(ngayLap);
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = copyDate(ngayLap);
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

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaPT() {
        return maPT;
    }

    public void setMaPT(String maPT) {
        this.maPT = maPT;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        this.maNV = nhanVien == null ? null : nhanVien.getMaNV();
    }

    public PhuongThucThanhToan getPhuongThucThanhToanRef() {
        return phuongThucThanhToanRef;
    }

    public void setPhuongThucThanhToanRef(PhuongThucThanhToan phuongThucThanhToanRef) {
        this.phuongThucThanhToanRef = phuongThucThanhToanRef;
        this.maPT = phuongThucThanhToanRef == null ? null : phuongThucThanhToanRef.getMaPT();
        this.phuongThucThanhToan = phuongThucThanhToanRef == null ? null : phuongThucThanhToanRef.getTenPT();
    }

    private Date copyDate(Date date) {
        return date == null ? null : new Date(date.getTime());
    }

    @Override
    public String toString() {
        return "HoaDonBanHang{"
                + "maHD='" + maHD + '\''
                + ", ngayLap=" + ngayLap
                + ", tongTien=" + tongTien
                + ", giaTriKM=" + giaTriKM
                + ", thanhTien=" + thanhTien
                + ", phuongThucThanhToan='" + phuongThucThanhToan + '\''
                + ", maNV='" + maNV + '\''
                + ", maPT='" + maPT + '\''
                + '}';
    }
}
