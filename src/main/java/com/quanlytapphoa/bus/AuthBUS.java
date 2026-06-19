package com.quanlytapphoa.bus;

import com.quanlytapphoa.model.TaiKhoan;
import java.util.List;

public class AuthBUS {
    private final List<TaiKhoan> dsTaiKhoan;
    private TaiKhoan taiKhoanHienTai;

    public AuthBUS(List<TaiKhoan> dsTaiKhoan) {
        this.dsTaiKhoan = dsTaiKhoan;
    }

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        String username = chuanHoa(tenDangNhap);
        String password = matKhau == null ? "" : matKhau;

        if (username.length() == 0 || password.length() == 0) {
            throw new BusinessException("Ten dang nhap va mat khau khong duoc rong");
        }

        for (TaiKhoan taiKhoan : dsTaiKhoan) {
            if (taiKhoan.isTrangThai()
                    && username.equalsIgnoreCase(taiKhoan.getTenDangNhap())
                    && password.equals(taiKhoan.getMatKhau())) {
                taiKhoanHienTai = taiKhoan;
                return taiKhoan;
            }
        }

        throw new BusinessException("Ten dang nhap hoac mat khau khong dung");
    }

    public void dangXuat() {
        taiKhoanHienTai = null;
    }

    public boolean daDangNhap() {
        return taiKhoanHienTai != null;
    }

    public TaiKhoan getTaiKhoanHienTai() {
        return taiKhoanHienTai;
    }

    public boolean coQuyen(String vaiTro) {
        if (taiKhoanHienTai == null || vaiTro == null) {
            return false;
        }
        return vaiTro.equalsIgnoreCase(taiKhoanHienTai.getVaiTro());
    }

    public void yeuCauDangNhap() {
        if (!daDangNhap()) {
            throw new BusinessException("Can dang nhap de thuc hien chuc nang nay");
        }
    }

    public void yeuCauQuyen(String vaiTro) {
        yeuCauDangNhap();
        if (!coQuyen(vaiTro)) {
            throw new BusinessException("Tai khoan khong co quyen thuc hien chuc nang nay");
        }
    }

    private String chuanHoa(String value) {
        return value == null ? "" : value.trim();
    }
}
