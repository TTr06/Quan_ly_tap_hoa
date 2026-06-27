package com.quanlytapphoa.bus;

import com.quanlytapphoa.dao.DatabaseSync;
import com.quanlytapphoa.model.NhanVien;
import com.quanlytapphoa.model.TaiKhoan;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TaiKhoanBUS {
    private final List<TaiKhoan> dsTaiKhoan;
    private final NhanVienBUS nhanVienBUS;

    public TaiKhoanBUS(List<TaiKhoan> dsTaiKhoan, NhanVienBUS nhanVienBUS) {
        this.dsTaiKhoan = dsTaiKhoan;
        this.nhanVienBUS = nhanVienBUS;
    }

    public List<TaiKhoan> layDanhSach() {
        return new ArrayList<TaiKhoan>(dsTaiKhoan);
    }

    public TaiKhoan timTheoTenDangNhap(String tenDangNhap) {
        String username = chuanHoa(tenDangNhap);
        for (TaiKhoan taiKhoan : dsTaiKhoan) {
            if (username.equalsIgnoreCase(taiKhoan.getTenDangNhap())) {
                return taiKhoan;
            }
        }
        return null;
    }

    public List<TaiKhoan> timKiem(String tuKhoa) {
        String keyword = chuanHoa(tuKhoa).toLowerCase(Locale.ROOT);
        List<TaiKhoan> ketQua = new ArrayList<TaiKhoan>();
        for (TaiKhoan taiKhoan : dsTaiKhoan) {
            if (keyword.length() == 0
                    || coChua(taiKhoan.getTenDangNhap(), keyword)
                    || coChua(taiKhoan.getVaiTro(), keyword)
                    || coChua(taiKhoan.getMaNV(), keyword)) {
                ketQua.add(taiKhoan);
            }
        }
        return ketQua;
    }

    public void themTaiKhoan(TaiKhoan taiKhoan) {
        kiemTraTaiKhoan(taiKhoan);
        if (timTheoTenDangNhap(taiKhoan.getTenDangNhap()) != null) {
            throw new BusinessException("Ten dang nhap da ton tai");
        }
        DatabaseSync.luuTaiKhoan(taiKhoan);
        dsTaiKhoan.add(taiKhoan);
    }

    public void suaTaiKhoan(TaiKhoan taiKhoan) {
        kiemTraTaiKhoan(taiKhoan);
        TaiKhoan hienCo = timTheoTenDangNhap(taiKhoan.getTenDangNhap());
        if (hienCo == null) {
            throw new BusinessException("Khong tim thay tai khoan can sua");
        }

        DatabaseSync.luuTaiKhoan(taiKhoan);
        hienCo.setMatKhau(taiKhoan.getMatKhau());
        hienCo.setVaiTro(chuanHoaVaiTro(taiKhoan.getVaiTro()));
        hienCo.setMaNV(taiKhoan.getMaNV());
        hienCo.setTrangThai(taiKhoan.isTrangThai());
    }

    public void xoaTaiKhoan(String tenDangNhap) {
        TaiKhoan taiKhoan = timTheoTenDangNhap(tenDangNhap);
        if (taiKhoan == null) {
            throw new BusinessException("Khong tim thay tai khoan can xoa");
        }
        DatabaseSync.xoaTaiKhoan(tenDangNhap);
        taiKhoan.setTrangThai(false);
    }

    private void kiemTraTaiKhoan(TaiKhoan taiKhoan) {
        if (taiKhoan == null) {
            throw new BusinessException("Tai khoan khong duoc rong");
        }
        if (chuanHoa(taiKhoan.getTenDangNhap()).length() == 0) {
            throw new BusinessException("Ten dang nhap khong duoc rong");
        }
        if (taiKhoan.getMatKhau() == null || taiKhoan.getMatKhau().length() == 0) {
            throw new BusinessException("Mat khau khong duoc rong");
        }

        String vaiTro = chuanHoaVaiTro(taiKhoan.getVaiTro());
        if (!"ADMIN".equals(vaiTro) && !"NHAN_VIEN".equals(vaiTro)) {
            throw new BusinessException("Vai tro chi duoc la ADMIN hoac NHAN_VIEN");
        }
        taiKhoan.setVaiTro(vaiTro);

        String maNV = chuanHoa(taiKhoan.getMaNV());
        if (maNV.length() == 0) {
            throw new BusinessException("Ma nhan vien cua tai khoan khong duoc rong");
        }

        NhanVien nhanVien = nhanVienBUS == null ? null : nhanVienBUS.timTheoMa(maNV);
        if (nhanVien == null || !nhanVien.isTrangThai()) {
            throw new BusinessException("Nhan vien cua tai khoan khong ton tai hoac da nghi");
        }
        taiKhoan.setMaNV(maNV);
    }

    private String chuanHoaVaiTro(String vaiTro) {
        String value = chuanHoa(vaiTro).toUpperCase(Locale.ROOT);
        if ("QUAN LY".equals(value) || "QUAN_LY".equals(value)) {
            return "ADMIN";
        }
        if ("NHAN VIEN".equals(value)) {
            return "NHAN_VIEN";
        }
        return value;
    }

    private boolean coChua(String value, String keyword) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(keyword);
    }

    private String chuanHoa(String value) {
        return value == null ? "" : value.trim();
    }
}
