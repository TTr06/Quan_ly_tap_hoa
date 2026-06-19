package com.quanlytapphoa.bus;

import com.quanlytapphoa.model.NhaCungCap;
import com.quanlytapphoa.model.PhieuNhapHang;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NhaCungCapBUS {
    private final List<NhaCungCap> dsNhaCungCap;
    private final List<PhieuNhapHang> dsPhieuNhapHang;

    public NhaCungCapBUS(List<NhaCungCap> dsNhaCungCap, List<PhieuNhapHang> dsPhieuNhapHang) {
        this.dsNhaCungCap = dsNhaCungCap;
        this.dsPhieuNhapHang = dsPhieuNhapHang;
    }

    public List<NhaCungCap> layDanhSach() {
        return new ArrayList<NhaCungCap>(dsNhaCungCap);
    }

    public NhaCungCap timTheoMa(String maNCC) {
        String ma = chuanHoa(maNCC);
        for (NhaCungCap nhaCungCap : dsNhaCungCap) {
            if (ma.equalsIgnoreCase(nhaCungCap.getMaNCC())) {
                return nhaCungCap;
            }
        }
        return null;
    }

    public List<NhaCungCap> timKiem(String tuKhoa) {
        String keyword = chuanHoa(tuKhoa).toLowerCase(Locale.ROOT);
        List<NhaCungCap> ketQua = new ArrayList<NhaCungCap>();
        for (NhaCungCap nhaCungCap : dsNhaCungCap) {
            if (keyword.length() == 0
                    || coChua(nhaCungCap.getMaNCC(), keyword)
                    || coChua(nhaCungCap.getTenNCC(), keyword)
                    || coChua(nhaCungCap.getSoDienThoai(), keyword)
                    || coChua(nhaCungCap.getEmail(), keyword)) {
                ketQua.add(nhaCungCap);
            }
        }
        return ketQua;
    }

    public void themNhaCungCap(NhaCungCap nhaCungCap) {
        kiemTraNhaCungCap(nhaCungCap);
        if (timTheoMa(nhaCungCap.getMaNCC()) != null) {
            throw new BusinessException("Ma nha cung cap da ton tai");
        }
        dsNhaCungCap.add(nhaCungCap);
    }

    public void suaNhaCungCap(NhaCungCap nhaCungCap) {
        kiemTraNhaCungCap(nhaCungCap);
        NhaCungCap hienCo = timTheoMa(nhaCungCap.getMaNCC());
        if (hienCo == null) {
            throw new BusinessException("Khong tim thay nha cung cap can sua");
        }

        hienCo.setTenNCC(nhaCungCap.getTenNCC());
        hienCo.setDiaChi(nhaCungCap.getDiaChi());
        hienCo.setSoDienThoai(nhaCungCap.getSoDienThoai());
        hienCo.setEmail(nhaCungCap.getEmail());
    }

    public void xoaNhaCungCap(String maNCC) {
        NhaCungCap nhaCungCap = timTheoMa(maNCC);
        if (nhaCungCap == null) {
            throw new BusinessException("Khong tim thay nha cung cap can xoa");
        }
        if (!lichSuDatHang(maNCC).isEmpty()) {
            throw new BusinessException("Khong the xoa nha cung cap da co lich su dat hang");
        }
        dsNhaCungCap.remove(nhaCungCap);
    }

    public List<PhieuNhapHang> lichSuDatHang(String maNCC) {
        List<PhieuNhapHang> ketQua = new ArrayList<PhieuNhapHang>();
        for (PhieuNhapHang phieuNhapHang : dsPhieuNhapHang) {
            if (maNCC != null && maNCC.equalsIgnoreCase(phieuNhapHang.getMaNCC())) {
                ketQua.add(phieuNhapHang);
            }
        }
        return ketQua;
    }

    private void kiemTraNhaCungCap(NhaCungCap nhaCungCap) {
        if (nhaCungCap == null) {
            throw new BusinessException("Nha cung cap khong duoc rong");
        }
        if (chuanHoa(nhaCungCap.getMaNCC()).length() == 0) {
            throw new BusinessException("Ma nha cung cap khong duoc rong");
        }
        if (chuanHoa(nhaCungCap.getTenNCC()).length() == 0) {
            throw new BusinessException("Ten nha cung cap khong duoc rong");
        }
    }

    private boolean coChua(String value, String keyword) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(keyword);
    }

    private String chuanHoa(String value) {
        return value == null ? "" : value.trim();
    }
}
