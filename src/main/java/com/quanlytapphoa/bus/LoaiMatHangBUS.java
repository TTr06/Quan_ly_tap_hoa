package com.quanlytapphoa.bus;

import com.quanlytapphoa.model.LoaiMatHang;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoaiMatHangBUS {
    private final List<LoaiMatHang> dsLoaiMatHang;

    public LoaiMatHangBUS(List<LoaiMatHang> dsLoaiMatHang) {
        this.dsLoaiMatHang = dsLoaiMatHang;
    }

    public List<LoaiMatHang> layDanhSach() {
        return new ArrayList<LoaiMatHang>(dsLoaiMatHang);
    }

    public LoaiMatHang timTheoMa(String maLoai) {
        String ma = chuanHoa(maLoai);
        for (LoaiMatHang loaiMatHang : dsLoaiMatHang) {
            if (ma.equalsIgnoreCase(loaiMatHang.getMaLoai())) {
                return loaiMatHang;
            }
        }
        return null;
    }

    public List<LoaiMatHang> timKiem(String tuKhoa) {
        String keyword = chuanHoa(tuKhoa).toLowerCase(Locale.ROOT);
        List<LoaiMatHang> ketQua = new ArrayList<LoaiMatHang>();
        for (LoaiMatHang loaiMatHang : dsLoaiMatHang) {
            if (keyword.length() == 0
                    || coChua(loaiMatHang.getMaLoai(), keyword)
                    || coChua(loaiMatHang.getTenLoai(), keyword)) {
                ketQua.add(loaiMatHang);
            }
        }
        return ketQua;
    }

    public void themLoaiMatHang(LoaiMatHang loaiMatHang) {
        kiemTraLoaiMatHang(loaiMatHang);
        if (timTheoMa(loaiMatHang.getMaLoai()) != null) {
            throw new BusinessException("Ma loai mat hang da ton tai");
        }
        dsLoaiMatHang.add(loaiMatHang);
    }

    public void suaLoaiMatHang(LoaiMatHang loaiMatHang) {
        kiemTraLoaiMatHang(loaiMatHang);
        LoaiMatHang hienCo = timTheoMa(loaiMatHang.getMaLoai());
        if (hienCo == null) {
            throw new BusinessException("Khong tim thay loai mat hang can sua");
        }
        hienCo.setTenLoai(loaiMatHang.getTenLoai());
        hienCo.setTrangThai(loaiMatHang.isTrangThai());
    }

    public void xoaLoaiMatHang(String maLoai) {
        LoaiMatHang loaiMatHang = timTheoMa(maLoai);
        if (loaiMatHang == null) {
            throw new BusinessException("Khong tim thay loai mat hang can xoa");
        }
        loaiMatHang.setTrangThai(false);
    }

    private void kiemTraLoaiMatHang(LoaiMatHang loaiMatHang) {
        if (loaiMatHang == null) {
            throw new BusinessException("Loai mat hang khong duoc rong");
        }
        if (chuanHoa(loaiMatHang.getMaLoai()).length() == 0) {
            throw new BusinessException("Ma loai mat hang khong duoc rong");
        }
        if (chuanHoa(loaiMatHang.getTenLoai()).length() == 0) {
            throw new BusinessException("Ten loai mat hang khong duoc rong");
        }
    }

    private boolean coChua(String value, String keyword) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(keyword);
    }

    private String chuanHoa(String value) {
        return value == null ? "" : value.trim();
    }
}
