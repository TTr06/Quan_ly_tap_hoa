package com.quanlytapphoa.bus;

import com.quanlytapphoa.dao.DatabaseSync;
import com.quanlytapphoa.model.ChiTietNhapHang;
import com.quanlytapphoa.model.MatHang;
import com.quanlytapphoa.model.NhaCungCap;
import com.quanlytapphoa.model.NhanVien;
import com.quanlytapphoa.model.PhieuNhapHang;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuNhapBUS {
    private final List<PhieuNhapHang> dsPhieuNhapHang;
    private final List<ChiTietNhapHang> dsChiTietNhapHang;
    private final MatHangBUS matHangBUS;
    private final NhaCungCapBUS nhaCungCapBUS;
    private final NhanVienBUS nhanVienBUS;

    public PhieuNhapBUS(
            List<PhieuNhapHang> dsPhieuNhapHang,
            List<ChiTietNhapHang> dsChiTietNhapHang,
            MatHangBUS matHangBUS,
            NhaCungCapBUS nhaCungCapBUS,
            NhanVienBUS nhanVienBUS
    ) {
        this.dsPhieuNhapHang = dsPhieuNhapHang;
        this.dsChiTietNhapHang = dsChiTietNhapHang;
        this.matHangBUS = matHangBUS;
        this.nhaCungCapBUS = nhaCungCapBUS;
        this.nhanVienBUS = nhanVienBUS;
    }

    public PhieuNhapHang taoPhieuNhap(
            String maPhieu,
            String maNCC,
            String maNV,
            List<ChiTietNhapHang> chiTietYeuCau
    ) {
        String ma = chuanHoa(maPhieu);
        if (ma.length() == 0) {
            throw new BusinessException("Ma phieu nhap khong duoc rong");
        }
        if (timTheoMa(ma) != null) {
            throw new BusinessException("Ma phieu nhap da ton tai");
        }

        NhaCungCap nhaCungCap = nhaCungCapBUS.timTheoMa(maNCC);
        if (nhaCungCap == null) {
            throw new BusinessException("Nha cung cap khong ton tai");
        }

        NhanVien nhanVien = nhanVienBUS.timTheoMa(maNV);
        if (nhanVien == null || !nhanVien.isTrangThai()) {
            throw new BusinessException("Nhan vien khong ton tai hoac da ngung lam viec");
        }

        if (chiTietYeuCau == null || chiTietYeuCau.isEmpty()) {
            throw new BusinessException("Phieu nhap phai co it nhat mot san pham");
        }

        PhieuNhapHang phieuNhap = new PhieuNhapHang(ma, new Date(), nhaCungCap, nhanVien);
        List<ChiTietNhapHang> chiTietDaTao = new ArrayList<ChiTietNhapHang>();

        for (ChiTietNhapHang yeuCau : chiTietYeuCau) {
            ChiTietNhapHang chiTiet = taoChiTietNhapHang(phieuNhap, yeuCau);
            chiTietDaTao.add(chiTiet);
        }

        capNhatTongTien(phieuNhap, chiTietDaTao);
        DatabaseSync.themPhieuNhap(phieuNhap, chiTietDaTao);
        capNhatTonKhoSauNhap(chiTietDaTao);

        dsPhieuNhapHang.add(phieuNhap);
        dsChiTietNhapHang.addAll(chiTietDaTao);
        return phieuNhap;
    }

    public PhieuNhapHang timTheoMa(String maPhieu) {
        String ma = chuanHoa(maPhieu);
        for (PhieuNhapHang phieuNhap : dsPhieuNhapHang) {
            if (ma.equalsIgnoreCase(phieuNhap.getMaPhieu())) {
                return phieuNhap;
            }
        }
        return null;
    }

    public List<ChiTietNhapHang> layChiTietPhieuNhap(String maPhieu) {
        List<ChiTietNhapHang> ketQua = new ArrayList<ChiTietNhapHang>();
        for (ChiTietNhapHang chiTiet : dsChiTietNhapHang) {
            if (maPhieu != null && maPhieu.equalsIgnoreCase(chiTiet.getMaPhieu())) {
                ketQua.add(chiTiet);
            }
        }
        return ketQua;
    }

    public List<PhieuNhapHang> layDanhSach() {
        return new ArrayList<PhieuNhapHang>(dsPhieuNhapHang);
    }

    public String chiTietPhieuNhap(String maPhieu) {
        PhieuNhapHang phieuNhap = timTheoMa(maPhieu);
        if (phieuNhap == null) {
            throw new BusinessException("Khong tim thay phieu nhap");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Phieu nhap: ").append(phieuNhap.getMaPhieu()).append(System.lineSeparator());
        builder.append("Ngay nhap: ").append(phieuNhap.getNgayNhap()).append(System.lineSeparator());
        if (phieuNhap.getNhaCungCap() != null) {
            builder.append("Nha cung cap: ").append(phieuNhap.getNhaCungCap().getTenNCC()).append(System.lineSeparator());
        }
        if (phieuNhap.getNhanVien() != null) {
            builder.append("Nhan vien: ").append(phieuNhap.getNhanVien().getHoTen()).append(System.lineSeparator());
        }
        builder.append("Chi tiet:").append(System.lineSeparator());
        for (ChiTietNhapHang chiTiet : layChiTietPhieuNhap(maPhieu)) {
            builder.append(chiTiet).append(System.lineSeparator());
        }
        builder.append("Tong tien: ").append(phieuNhap.getTongTien());
        return builder.toString();
    }

    public void xoaPhieuNhap(String maPhieu) {
        PhieuNhapHang phieuNhap = timTheoMa(maPhieu);
        if (phieuNhap == null) {
            throw new BusinessException("Khong tim thay phieu nhap can xoa");
        }

        List<ChiTietNhapHang> chiTietCanXoa = layChiTietPhieuNhap(maPhieu);
        for (ChiTietNhapHang chiTiet : chiTietCanXoa) {
            MatHang matHang = matHangBUS.timTheoMa(chiTiet.getMaMatHang());
            if (matHang == null) {
                continue;
            }
            if (matHang.getSoLuong() < chiTiet.getSoLuong()) {
                throw new BusinessException("Khong the xoa phieu nhap vi ton kho hien tai khong du de tru lai");
            }
        }

        DatabaseSync.xoaPhieuNhap(maPhieu, chiTietCanXoa);
        for (ChiTietNhapHang chiTiet : chiTietCanXoa) {
            MatHang matHang = matHangBUS.timTheoMa(chiTiet.getMaMatHang());
            if (matHang == null) {
                continue;
            }
            matHang.setSoLuong(matHang.getSoLuong() - chiTiet.getSoLuong());
        }

        dsChiTietNhapHang.removeAll(chiTietCanXoa);
        dsPhieuNhapHang.remove(phieuNhap);
    }

    private ChiTietNhapHang taoChiTietNhapHang(PhieuNhapHang phieuNhap, ChiTietNhapHang yeuCau) {
        if (yeuCau == null) {
            throw new BusinessException("Chi tiet nhap hang khong duoc rong");
        }

        String maMatHang = yeuCau.getMaMatHang();
        if ((maMatHang == null || maMatHang.trim().length() == 0) && yeuCau.getMatHang() != null) {
            maMatHang = yeuCau.getMatHang().getMaMatHang();
        }

        MatHang matHang = matHangBUS.timTheoMa(maMatHang);
        if (matHang == null) {
            throw new BusinessException("Khong tim thay mat hang can nhap");
        }
        if (yeuCau.getSoLuong() <= 0) {
            throw new BusinessException("So luong nhap phai lon hon 0");
        }
        if (yeuCau.getDonGiaNhap() < 0) {
            throw new BusinessException("Don gia nhap khong duoc am");
        }

        ChiTietNhapHang chiTiet = new ChiTietNhapHang();
        chiTiet.setMaPhieu(phieuNhap.getMaPhieu());
        chiTiet.setMaMatHang(matHang.getMaMatHang());
        chiTiet.setSoLuong(yeuCau.getSoLuong());
        chiTiet.setDonGiaNhap(yeuCau.getDonGiaNhap());
        chiTiet.setThanhTien(yeuCau.getSoLuong() * yeuCau.getDonGiaNhap());
        return chiTiet;
    }

    private void capNhatTongTien(PhieuNhapHang phieuNhap, List<ChiTietNhapHang> chiTietNhapHangs) {
        double tongTien = 0;
        for (ChiTietNhapHang chiTiet : chiTietNhapHangs) {
            tongTien += chiTiet.getThanhTien();
        }
        phieuNhap.setTongTien(tongTien);
    }

    private void capNhatTonKhoSauNhap(List<ChiTietNhapHang> chiTietNhapHangs) {
        for (ChiTietNhapHang chiTiet : chiTietNhapHangs) {
            MatHang matHang = matHangBUS.timTheoMa(chiTiet.getMaMatHang());
            matHang.setSoLuong(matHang.getSoLuong() + chiTiet.getSoLuong());
        }
    }

    private String chuanHoa(String value) {
        return value == null ? "" : value.trim();
    }
}
