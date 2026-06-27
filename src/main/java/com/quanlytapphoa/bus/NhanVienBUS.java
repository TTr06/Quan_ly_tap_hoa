package com.quanlytapphoa.bus;

import com.quanlytapphoa.dao.DatabaseSync;
import com.quanlytapphoa.model.NhanVien;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NhanVienBUS {
    private final List<NhanVien> dsNhanVien;

    public NhanVienBUS(List<NhanVien> dsNhanVien) {
        this.dsNhanVien = dsNhanVien;
    }

    public List<NhanVien> layDanhSach() {
        return new ArrayList<NhanVien>(dsNhanVien);
    }

    public NhanVien timTheoMa(String maNV) {
        String ma = chuanHoa(maNV);
        for (NhanVien nhanVien : dsNhanVien) {
            if (ma.equalsIgnoreCase(nhanVien.getMaNV())) {
                return nhanVien;
            }
        }
        return null;
    }

    public List<NhanVien> timKiem(String tuKhoa) {
        String keyword = chuanHoa(tuKhoa).toLowerCase(Locale.ROOT);
        List<NhanVien> ketQua = new ArrayList<NhanVien>();
        for (NhanVien nhanVien : dsNhanVien) {
            if (keyword.length() == 0
                    || coChua(nhanVien.getMaNV(), keyword)
                    || coChua(nhanVien.getHoTen(), keyword)
                    || coChua(nhanVien.getSoDienThoai(), keyword)
                    || coChua(nhanVien.getChucVu(), keyword)) {
                ketQua.add(nhanVien);
            }
        }
        return ketQua;
    }

    public void themNhanVien(NhanVien nhanVien) {
        kiemTraNhanVien(nhanVien);
        if (timTheoMa(nhanVien.getMaNV()) != null) {
            throw new BusinessException("Ma nhan vien da ton tai");
        }
        DatabaseSync.luuNhanVien(nhanVien);
        dsNhanVien.add(nhanVien);
    }

    public void suaNhanVien(NhanVien nhanVien) {
        kiemTraNhanVien(nhanVien);
        NhanVien hienCo = timTheoMa(nhanVien.getMaNV());
        if (hienCo == null) {
            throw new BusinessException("Khong tim thay nhan vien can sua");
        }

        DatabaseSync.luuNhanVien(nhanVien);
        hienCo.setHoTen(nhanVien.getHoTen());
        hienCo.setSoDienThoai(nhanVien.getSoDienThoai());
        hienCo.setDiaChi(nhanVien.getDiaChi());
        hienCo.setChucVu(nhanVien.getChucVu());
        hienCo.setNgayVaoLam(nhanVien.getNgayVaoLam());
        hienCo.setTrangThai(nhanVien.isTrangThai());
    }

    public void xoaNhanVien(String maNV) {
        NhanVien nhanVien = timTheoMa(maNV);
        if (nhanVien == null) {
            throw new BusinessException("Khong tim thay nhan vien can xoa");
        }
        DatabaseSync.xoaNhanVien(maNV);
        nhanVien.setTrangThai(false);
    }

    private void kiemTraNhanVien(NhanVien nhanVien) {
        if (nhanVien == null) {
            throw new BusinessException("Nhan vien khong duoc rong");
        }
        if (chuanHoa(nhanVien.getMaNV()).length() == 0) {
            throw new BusinessException("Ma nhan vien khong duoc rong");
        }
        if (chuanHoa(nhanVien.getHoTen()).length() == 0) {
            throw new BusinessException("Ho ten nhan vien khong duoc rong");
        }
    }

    private boolean coChua(String value, String keyword) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(keyword);
    }

    private String chuanHoa(String value) {
        return value == null ? "" : value.trim();
    }
}
