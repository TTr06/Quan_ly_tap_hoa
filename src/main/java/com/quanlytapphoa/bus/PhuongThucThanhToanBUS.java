package com.quanlytapphoa.bus;

import com.quanlytapphoa.model.PhuongThucThanhToan;
import java.util.ArrayList;
import java.util.List;

public class PhuongThucThanhToanBUS {
    private final List<PhuongThucThanhToan> dsPhuongThucThanhToan;

    public PhuongThucThanhToanBUS(List<PhuongThucThanhToan> dsPhuongThucThanhToan) {
        this.dsPhuongThucThanhToan = dsPhuongThucThanhToan;
    }

    public List<PhuongThucThanhToan> layDanhSach() {
        return new ArrayList<PhuongThucThanhToan>(dsPhuongThucThanhToan);
    }

    public List<PhuongThucThanhToan> layDanhSachDangHoatDong() {
        List<PhuongThucThanhToan> ketQua = new ArrayList<PhuongThucThanhToan>();
        for (PhuongThucThanhToan phuongThuc : dsPhuongThucThanhToan) {
            if (phuongThuc.isTrangThai()) {
                ketQua.add(phuongThuc);
            }
        }
        return ketQua;
    }

    public PhuongThucThanhToan timTheoMa(String maPT) {
        if (maPT == null) {
            return null;
        }
        for (PhuongThucThanhToan phuongThuc : dsPhuongThucThanhToan) {
            if (maPT.equalsIgnoreCase(phuongThuc.getMaPT())) {
                return phuongThuc;
            }
        }
        return null;
    }

    public void themPhuongThucThanhToan(PhuongThucThanhToan phuongThuc) {
        kiemTraPhuongThuc(phuongThuc);
        if (timTheoMa(phuongThuc.getMaPT()) != null) {
            throw new BusinessException("Ma phuong thuc thanh toan da ton tai");
        }
        dsPhuongThucThanhToan.add(phuongThuc);
    }

    public void suaPhuongThucThanhToan(PhuongThucThanhToan phuongThuc) {
        kiemTraPhuongThuc(phuongThuc);
        PhuongThucThanhToan hienCo = timTheoMa(phuongThuc.getMaPT());
        if (hienCo == null) {
            throw new BusinessException("Khong tim thay phuong thuc thanh toan can sua");
        }
        hienCo.setTenPT(phuongThuc.getTenPT());
        hienCo.setMoTa(phuongThuc.getMoTa());
        hienCo.setTrangThai(phuongThuc.isTrangThai());
    }

    public void xoaPhuongThucThanhToan(String maPT) {
        PhuongThucThanhToan phuongThuc = timTheoMa(maPT);
        if (phuongThuc == null) {
            throw new BusinessException("Khong tim thay phuong thuc thanh toan can xoa");
        }
        phuongThuc.setTrangThai(false);
    }

    private void kiemTraPhuongThuc(PhuongThucThanhToan phuongThuc) {
        if (phuongThuc == null) {
            throw new BusinessException("Phuong thuc thanh toan khong duoc rong");
        }
        if (phuongThuc.getMaPT() == null || phuongThuc.getMaPT().trim().length() == 0) {
            throw new BusinessException("Ma phuong thuc thanh toan khong duoc rong");
        }
        if (phuongThuc.getTenPT() == null || phuongThuc.getTenPT().trim().length() == 0) {
            throw new BusinessException("Ten phuong thuc thanh toan khong duoc rong");
        }
    }
}
