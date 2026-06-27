package com.quanlytapphoa.bus;

import com.quanlytapphoa.dao.DatabaseSync;
import com.quanlytapphoa.model.MatHang;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MatHangBUS {
    private final List<MatHang> dsMatHang;

    public MatHangBUS(List<MatHang> dsMatHang) {
        this.dsMatHang = dsMatHang;
    }

    public List<MatHang> layDanhSach() {
        return new ArrayList<MatHang>(dsMatHang);
    }

    public MatHang timTheoMa(String maMatHang) {
        String ma = chuanHoa(maMatHang);
        for (MatHang matHang : dsMatHang) {
            if (ma.equalsIgnoreCase(matHang.getMaMatHang())) {
                return matHang;
            }
        }
        return null;
    }

    public List<MatHang> timKiemSanPham(String tuKhoa) {
        String keyword = chuanHoa(tuKhoa).toLowerCase(Locale.ROOT);
        List<MatHang> ketQua = new ArrayList<MatHang>();

        for (MatHang matHang : dsMatHang) {
            if (!matHang.isTrangThai()) {
                continue;
            }
            if (keyword.length() == 0 || coChua(matHang.getMaMatHang(), keyword)
                    || coChua(matHang.getTenMatHang(), keyword)
                    || coChua(matHang.getMaLoai(), keyword)
                    || coChua(matHang.getDonViTinh(), keyword)) {
                ketQua.add(matHang);
            }
        }
        return ketQua;
    }

    public void themMatHang(MatHang matHang) {
        kiemTraMatHang(matHang);
        if (timTheoMa(matHang.getMaMatHang()) != null) {
            throw new BusinessException("Ma mat hang da ton tai");
        }
        DatabaseSync.luuMatHang(matHang);
        dsMatHang.add(matHang);
    }

    public void suaMatHang(MatHang matHang) {
        kiemTraMatHang(matHang);
        MatHang hienCo = timTheoMa(matHang.getMaMatHang());
        if (hienCo == null) {
            throw new BusinessException("Khong tim thay mat hang can sua");
        }

        DatabaseSync.luuMatHang(matHang);
        hienCo.setTenMatHang(matHang.getTenMatHang());
        hienCo.setMaLoai(matHang.getMaLoai());
        hienCo.setMaKM(matHang.getMaKM());
        hienCo.setKhuyenMai(matHang.getKhuyenMai());
        hienCo.setDonViTinh(matHang.getDonViTinh());
        hienCo.setGiaNhap(matHang.getGiaNhap());
        hienCo.setGiaBan(matHang.getGiaBan());
        hienCo.setSoLuong(matHang.getSoLuong());
        hienCo.setTrangThai(matHang.isTrangThai());
    }

    public void xoaMatHang(String maMatHang) {
        MatHang matHang = timTheoMa(maMatHang);
        if (matHang == null) {
            throw new BusinessException("Khong tim thay mat hang can xoa");
        }
        DatabaseSync.xoaMatHang(maMatHang);
        matHang.setTrangThai(false);
    }

    public void capNhatSoLuong(String maMatHang, int soLuongMoi) {
        if (soLuongMoi < 0) {
            throw new BusinessException("So luong moi khong duoc am");
        }
        MatHang matHang = timTheoMa(maMatHang);
        if (matHang == null) {
            throw new BusinessException("Khong tim thay mat hang");
        }
        int soLuongCu = matHang.getSoLuong();
        matHang.setSoLuong(soLuongMoi);
        try {
            DatabaseSync.capNhatTonKho(matHang);
        } catch (RuntimeException ex) {
            matHang.setSoLuong(soLuongCu);
            throw ex;
        }
    }

    public void nhapThemSoLuong(String maMatHang, int soLuongNhap) {
        MatHang matHang = timTheoMa(maMatHang);
        if (matHang == null) {
            throw new BusinessException("Khong tim thay mat hang");
        }
        if (soLuongNhap <= 0) {
            throw new BusinessException("So luong nhap phai lon hon 0");
        }
        int soLuongCu = matHang.getSoLuong();
        matHang.setSoLuong(matHang.getSoLuong() + soLuongNhap);
        try {
            DatabaseSync.capNhatTonKho(matHang);
        } catch (RuntimeException ex) {
            matHang.setSoLuong(soLuongCu);
            throw ex;
        }
    }

    public void xuatSoLuong(String maMatHang, int soLuongXuat) {
        MatHang matHang = timTheoMa(maMatHang);
        if (matHang == null) {
            throw new BusinessException("Khong tim thay mat hang");
        }
        if (soLuongXuat <= 0) {
            throw new BusinessException("So luong xuat phai lon hon 0");
        }
        if (matHang.getSoLuong() < soLuongXuat) {
            throw new BusinessException("So luong ton kho khong du");
        }
        int soLuongCu = matHang.getSoLuong();
        matHang.setSoLuong(matHang.getSoLuong() - soLuongXuat);
        try {
            DatabaseSync.capNhatTonKho(matHang);
        } catch (RuntimeException ex) {
            matHang.setSoLuong(soLuongCu);
            throw ex;
        }
    }

    public boolean duTonKho(String maMatHang, int soLuongCanBan) {
        MatHang matHang = timTheoMa(maMatHang);
        return matHang != null && matHang.isTrangThai() && soLuongCanBan > 0 && matHang.getSoLuong() >= soLuongCanBan;
    }

    private void kiemTraMatHang(MatHang matHang) {
        if (matHang == null) {
            throw new BusinessException("Mat hang khong duoc rong");
        }
        if (chuanHoa(matHang.getMaMatHang()).length() == 0) {
            throw new BusinessException("Ma mat hang khong duoc rong");
        }
        if (chuanHoa(matHang.getTenMatHang()).length() == 0) {
            throw new BusinessException("Ten mat hang khong duoc rong");
        }
        if (matHang.getGiaNhap() < 0 || matHang.getGiaBan() < 0) {
            throw new BusinessException("Gia nhap va gia ban khong duoc am");
        }
        if (matHang.getSoLuong() < 0) {
            throw new BusinessException("So luong khong duoc am");
        }
    }

    private boolean coChua(String value, String keyword) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(keyword);
    }

    private String chuanHoa(String value) {
        return value == null ? "" : value.trim();
    }
}
