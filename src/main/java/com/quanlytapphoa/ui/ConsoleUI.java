package com.quanlytapphoa.ui;

import com.quanlytapphoa.bus.AuthBUS;
import com.quanlytapphoa.bus.BaoCaoBUS;
import com.quanlytapphoa.bus.BusinessException;
import com.quanlytapphoa.bus.HoaDonBUS;
import com.quanlytapphoa.bus.KhuyenMaiBUS;
import com.quanlytapphoa.bus.LoaiMatHangBUS;
import com.quanlytapphoa.bus.MatHangBUS;
import com.quanlytapphoa.bus.NhanVienBUS;
import com.quanlytapphoa.bus.NhaCungCapBUS;
import com.quanlytapphoa.bus.PhieuNhapBUS;
import com.quanlytapphoa.bus.PhuongThucThanhToanBUS;
import com.quanlytapphoa.model.ChiTietHoaDon;
import com.quanlytapphoa.model.ChiTietNhapHang;
import com.quanlytapphoa.model.HoaDonBanHang;
import com.quanlytapphoa.model.KhuyenMai;
import com.quanlytapphoa.model.LoaiMatHang;
import com.quanlytapphoa.model.MatHang;
import com.quanlytapphoa.model.NhaCungCap;
import com.quanlytapphoa.model.NhanVien;
import com.quanlytapphoa.model.PhieuNhapHang;
import com.quanlytapphoa.model.PhuongThucThanhToan;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private final AuthBUS authBUS;
    private final LoaiMatHangBUS loaiMatHangBUS;
    private final MatHangBUS matHangBUS;
    private final NhanVienBUS nhanVienBUS;
    private final NhaCungCapBUS nhaCungCapBUS;
    private final PhuongThucThanhToanBUS phuongThucThanhToanBUS;
    private final KhuyenMaiBUS khuyenMaiBUS;
    private final HoaDonBUS hoaDonBUS;
    private final PhieuNhapBUS phieuNhapBUS;
    private final BaoCaoBUS baoCaoBUS;

    public ConsoleUI(
            AuthBUS authBUS,
            LoaiMatHangBUS loaiMatHangBUS,
            MatHangBUS matHangBUS,
            NhanVienBUS nhanVienBUS,
            NhaCungCapBUS nhaCungCapBUS,
            PhuongThucThanhToanBUS phuongThucThanhToanBUS,
            KhuyenMaiBUS khuyenMaiBUS,
            HoaDonBUS hoaDonBUS,
            PhieuNhapBUS phieuNhapBUS,
            BaoCaoBUS baoCaoBUS
    ) {
        this.authBUS = authBUS;
        this.loaiMatHangBUS = loaiMatHangBUS;
        this.matHangBUS = matHangBUS;
        this.nhanVienBUS = nhanVienBUS;
        this.nhaCungCapBUS = nhaCungCapBUS;
        this.phuongThucThanhToanBUS = phuongThucThanhToanBUS;
        this.khuyenMaiBUS = khuyenMaiBUS;
        this.hoaDonBUS = hoaDonBUS;
        this.phieuNhapBUS = phieuNhapBUS;
        this.baoCaoBUS = baoCaoBUS;
        this.dateFormat.setLenient(false);
    }

    public void start() {
        println("=== HE THONG QUAN LY TAP HOA - CLI ===");
        boolean running = true;
        while (running) {
            try {
                hienThiTaiKhoan();
                println("");
                println("1. Dang nhap");
                println("2. Dang xuat");
                println("3. Quan ly hang hoa");
                println("4. Quan ly loai mat hang");
                println("5. Quan ly nhan vien");
                println("6. Quan ly khuyen mai");
                println("7. Quan ly nha cung cap");
                println("8. Quan ly phuong thuc thanh toan");
                println("9. Nhap hang");
                println("10. Ban hang / hoa don");
                println("11. Bao cao thong ke");
                println("0. Thoat");
                int choice = readInt("Chon chuc nang: ");
                kiemTraTruyCapMenuChinh(choice);

                switch (choice) {
                    case 1:
                        dangNhap();
                        break;
                    case 2:
                        dangXuat();
                        break;
                    case 3:
                        menuHangHoa();
                        break;
                    case 4:
                        menuLoaiMatHang();
                        break;
                    case 5:
                        menuNhanVien();
                        break;
                    case 6:
                        menuKhuyenMai();
                        break;
                    case 7:
                        menuNhaCungCap();
                        break;
                    case 8:
                        menuPhuongThucThanhToan();
                        break;
                    case 9:
                        menuNhapHang();
                        break;
                    case 10:
                        menuBanHang();
                        break;
                    case 11:
                        menuBaoCao();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        println("Lua chon khong hop le");
                        break;
                }
            } catch (BusinessException ex) {
                println("Loi nghiep vu: " + ex.getMessage());
            } catch (Exception ex) {
                println("Loi: " + ex.getMessage());
            }
        }
        println("Da thoat chuong trinh");
    }

    private void dangNhap() {
        String username = readLine("Ten dang nhap: ");
        String password = readLine("Mat khau: ");
        authBUS.dangNhap(username, password);
        println("Dang nhap thanh cong");
    }

    private void dangXuat() {
        authBUS.dangXuat();
        println("Da dang xuat");
    }

    private void menuHangHoa() {
        boolean back = false;
        while (!back) {
            println("");
            println("--- QUAN LY HANG HOA ---");
            println("1. Xem danh sach");
            println("2. Tim kiem san pham");
            println("3. Them san pham");
            println("4. Sua san pham");
            println("5. Xoa/ngung ban san pham");
            println("6. Cap nhat so luong");
            println("7. Nhap them so luong");
            println("8. Xuat bot so luong");
            println("0. Quay lai");
            int choice = readInt("Chon: ");
            switch (choice) {
                case 1:
                    inDanhSach(matHangBUS.layDanhSach());
                    break;
                case 2:
                    inDanhSach(matHangBUS.timKiemSanPham(readLine("Tu khoa: ")));
                    break;
                case 3:
                    authBUS.yeuCauQuyen("ADMIN");
                    matHangBUS.themMatHang(nhapMatHang(false));
                    println("Da them san pham");
                    break;
                case 4:
                    authBUS.yeuCauQuyen("ADMIN");
                    matHangBUS.suaMatHang(nhapMatHang(true));
                    println("Da sua san pham");
                    break;
                case 5:
                    authBUS.yeuCauQuyen("ADMIN");
                    matHangBUS.xoaMatHang(readLine("Ma san pham: "));
                    println("Da ngung ban san pham");
                    break;
                case 6:
                    authBUS.yeuCauQuyen("ADMIN");
                    matHangBUS.capNhatSoLuong(readLine("Ma san pham: "), readInt("So luong moi: "));
                    println("Da cap nhat so luong");
                    break;
                case 7:
                    authBUS.yeuCauQuyen("ADMIN");
                    matHangBUS.nhapThemSoLuong(readLine("Ma san pham: "), readInt("So luong nhap them: "));
                    println("Da nhap them so luong");
                    break;
                case 8:
                    authBUS.yeuCauQuyen("ADMIN");
                    matHangBUS.xuatSoLuong(readLine("Ma san pham: "), readInt("So luong xuat: "));
                    println("Da xuat so luong");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    println("Lua chon khong hop le");
                    break;
            }
        }
    }

    private void menuLoaiMatHang() {
        boolean back = false;
        while (!back) {
            println("");
            println("--- QUAN LY LOAI MAT HANG ---");
            println("1. Xem danh sach");
            println("2. Tim kiem");
            println("3. Them");
            println("4. Sua");
            println("5. Xoa/ngung dung");
            println("0. Quay lai");
            int choice = readInt("Chon: ");
            switch (choice) {
                case 1:
                    inDanhSach(loaiMatHangBUS.layDanhSach());
                    break;
                case 2:
                    inDanhSach(loaiMatHangBUS.timKiem(readLine("Tu khoa: ")));
                    break;
                case 3:
                    loaiMatHangBUS.themLoaiMatHang(nhapLoaiMatHang(false));
                    println("Da them loai mat hang");
                    break;
                case 4:
                    loaiMatHangBUS.suaLoaiMatHang(nhapLoaiMatHang(true));
                    println("Da sua loai mat hang");
                    break;
                case 5:
                    loaiMatHangBUS.xoaLoaiMatHang(readLine("Ma loai: "));
                    println("Da ngung dung loai mat hang");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    println("Lua chon khong hop le");
                    break;
            }
        }
    }

    private void menuNhanVien() {
        boolean back = false;
        while (!back) {
            println("");
            println("--- QUAN LY NHAN VIEN ---");
            println("1. Xem danh sach");
            println("2. Tim kiem");
            println("3. Them");
            println("4. Sua");
            println("5. Xoa/ngung lam viec");
            println("0. Quay lai");
            int choice = readInt("Chon: ");
            switch (choice) {
                case 1:
                    inDanhSach(nhanVienBUS.layDanhSach());
                    break;
                case 2:
                    inDanhSach(nhanVienBUS.timKiem(readLine("Tu khoa: ")));
                    break;
                case 3:
                    nhanVienBUS.themNhanVien(nhapNhanVien(false));
                    println("Da them nhan vien");
                    break;
                case 4:
                    nhanVienBUS.suaNhanVien(nhapNhanVien(true));
                    println("Da sua nhan vien");
                    break;
                case 5:
                    nhanVienBUS.xoaNhanVien(readLine("Ma nhan vien: "));
                    println("Da ngung lam viec");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    println("Lua chon khong hop le");
                    break;
            }
        }
    }

    private void menuKhuyenMai() {
        boolean back = false;
        while (!back) {
            println("");
            println("--- QUAN LY KHUYEN MAI ---");
            println("1. Xem danh sach");
            println("2. Them");
            println("3. Sua");
            println("4. Bo khuyen mai");
            println("5. Gan khuyen mai cho san pham");
            println("0. Quay lai");
            int choice = readInt("Chon: ");
            switch (choice) {
                case 1:
                    inDanhSach(khuyenMaiBUS.layDanhSach());
                    break;
                case 2:
                    khuyenMaiBUS.themKhuyenMai(nhapKhuyenMai(false));
                    println("Da them khuyen mai");
                    break;
                case 3:
                    khuyenMaiBUS.suaKhuyenMai(nhapKhuyenMai(true));
                    println("Da sua khuyen mai");
                    break;
                case 4:
                    khuyenMaiBUS.boKhuyenMai(readLine("Ma khuyen mai: "));
                    println("Da bo khuyen mai");
                    break;
                case 5:
                    khuyenMaiBUS.ganKhuyenMaiChoSanPham(readLine("Ma san pham: "), readLine("Ma khuyen mai: "));
                    println("Da gan khuyen mai");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    println("Lua chon khong hop le");
                    break;
            }
        }
    }

    private void menuNhaCungCap() {
        boolean back = false;
        while (!back) {
            println("");
            println("--- QUAN LY NHA CUNG CAP ---");
            println("1. Xem danh sach");
            println("2. Tim kiem");
            println("3. Them");
            println("4. Sua");
            println("5. Xoa");
            println("6. Lich su dat hang");
            println("0. Quay lai");
            int choice = readInt("Chon: ");
            switch (choice) {
                case 1:
                    inDanhSach(nhaCungCapBUS.layDanhSach());
                    break;
                case 2:
                    inDanhSach(nhaCungCapBUS.timKiem(readLine("Tu khoa: ")));
                    break;
                case 3:
                    nhaCungCapBUS.themNhaCungCap(nhapNhaCungCap(false));
                    println("Da them nha cung cap");
                    break;
                case 4:
                    nhaCungCapBUS.suaNhaCungCap(nhapNhaCungCap(true));
                    println("Da sua nha cung cap");
                    break;
                case 5:
                    nhaCungCapBUS.xoaNhaCungCap(readLine("Ma nha cung cap: "));
                    println("Da xoa nha cung cap");
                    break;
                case 6:
                    inDanhSach(nhaCungCapBUS.lichSuDatHang(readLine("Ma nha cung cap: ")));
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    println("Lua chon khong hop le");
                    break;
            }
        }
    }

    private void menuPhuongThucThanhToan() {
        boolean back = false;
        while (!back) {
            println("");
            println("--- QUAN LY PHUONG THUC THANH TOAN ---");
            println("1. Xem danh sach");
            println("2. Xem dang hoat dong");
            println("3. Them");
            println("4. Sua");
            println("5. Xoa/ngung dung");
            println("0. Quay lai");
            int choice = readInt("Chon: ");
            switch (choice) {
                case 1:
                    inDanhSach(phuongThucThanhToanBUS.layDanhSach());
                    break;
                case 2:
                    inDanhSach(phuongThucThanhToanBUS.layDanhSachDangHoatDong());
                    break;
                case 3:
                    phuongThucThanhToanBUS.themPhuongThucThanhToan(nhapPhuongThuc(false));
                    println("Da them phuong thuc");
                    break;
                case 4:
                    phuongThucThanhToanBUS.suaPhuongThucThanhToan(nhapPhuongThuc(true));
                    println("Da sua phuong thuc");
                    break;
                case 5:
                    phuongThucThanhToanBUS.xoaPhuongThucThanhToan(readLine("Ma phuong thuc: "));
                    println("Da ngung dung phuong thuc");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    println("Lua chon khong hop le");
                    break;
            }
        }
    }

    private void menuNhapHang() {
        boolean back = false;
        while (!back) {
            println("");
            println("--- NHAP HANG ---");
            println("1. Xem danh sach phieu nhap");
            println("2. Tao phieu nhap");
            println("3. Xem chi tiet phieu nhap");
            println("4. Xoa phieu nhap");
            println("0. Quay lai");
            int choice = readInt("Chon: ");
            switch (choice) {
                case 1:
                    inDanhSach(phieuNhapBUS.layDanhSach());
                    break;
                case 2:
                    taoPhieuNhap();
                    break;
                case 3:
                    println(phieuNhapBUS.chiTietPhieuNhap(readLine("Ma phieu nhap: ")));
                    break;
                case 4:
                    phieuNhapBUS.xoaPhieuNhap(readLine("Ma phieu nhap: "));
                    println("Da xoa phieu nhap");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    println("Lua chon khong hop le");
                    break;
            }
        }
    }

    private void menuBanHang() {
        boolean back = false;
        while (!back) {
            println("");
            println("--- BAN HANG / HOA DON ---");
            println("1. Tao hoa don");
            println("2. Xem danh sach hoa don");
            println("3. In hoa don");
            println("4. Tra cuu lich su giao dich theo nhan vien");
            println("5. Huy hoa don");
            println("0. Quay lai");
            int choice = readInt("Chon: ");
            switch (choice) {
                case 1:
                    taoHoaDon();
                    break;
                case 2:
                    inDanhSach(hoaDonBUS.layDanhSach());
                    break;
                case 3:
                    println(hoaDonBUS.inHoaDon(readLine("Ma hoa don: ")));
                    break;
                case 4:
                    inDanhSach(hoaDonBUS.traCuuLichSuGiaoDich(maNhanVienTraCuu()));
                    break;
                case 5:
                    authBUS.yeuCauQuyen("ADMIN");
                    hoaDonBUS.huyHoaDon(readLine("Ma hoa don: "));
                    println("Da huy hoa don va hoan ton kho");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    println("Lua chon khong hop le");
                    break;
            }
        }
    }

    private void menuBaoCao() {
        boolean back = false;
        while (!back) {
            println("");
            println("--- BAO CAO THONG KE ---");
            println("1. Bao cao tong hop");
            println("2. Doanh thu");
            println("3. Tong nhap hang");
            println("4. Loi nhuan tam tinh");
            println("5. Mat hang ban chay");
            println("6. Hang sap het");
            println("7. Dem hoa don / phieu nhap / san pham");
            println("0. Quay lai");
            int choice = readInt("Chon: ");
            Date tuNgay;
            Date denNgay;
            switch (choice) {
                case 1:
                    tuNgay = readDateNullable("Tu ngay dd/MM/yyyy (rong = bo qua): ");
                    denNgay = readDateNullable("Den ngay dd/MM/yyyy (rong = bo qua): ");
                    println(baoCaoBUS.xuatBaoCaoTongHop(tuNgay, denNgay));
                    break;
                case 2:
                    println("Doanh thu: " + baoCaoBUS.tinhDoanhThu(
                            readDateNullable("Tu ngay dd/MM/yyyy: "),
                            readDateNullable("Den ngay dd/MM/yyyy: ")));
                    break;
                case 3:
                    println("Tong nhap hang: " + baoCaoBUS.tinhTongNhapHang(
                            readDateNullable("Tu ngay dd/MM/yyyy: "),
                            readDateNullable("Den ngay dd/MM/yyyy: ")));
                    break;
                case 4:
                    println("Loi nhuan tam tinh: " + baoCaoBUS.tinhLoiNhuanTamTinh(
                            readDateNullable("Tu ngay dd/MM/yyyy: "),
                            readDateNullable("Den ngay dd/MM/yyyy: ")));
                    break;
                case 5:
                    println(baoCaoBUS.thongKeMatHangBanChay(
                            readDateNullable("Tu ngay dd/MM/yyyy: "),
                            readDateNullable("Den ngay dd/MM/yyyy: ")).toString());
                    break;
                case 6:
                    inDanhSach(baoCaoBUS.thongKeHangSapHet(readInt("Nguong ton kho: ")));
                    break;
                case 7:
                    println("San pham dang ban: " + baoCaoBUS.demSanPhamDangBan());
                    println("Hoa don: " + baoCaoBUS.demHoaDon());
                    println("Phieu nhap: " + baoCaoBUS.demPhieuNhap());
                    println("Tong ton kho: " + baoCaoBUS.tinhTongTonKho());
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    println("Lua chon khong hop le");
                    break;
            }
        }
    }

    private MatHang nhapMatHang(boolean sua) {
        String ma = readLine("Ma san pham" + (sua ? " can sua" : "") + ": ");
        String ten = readLine("Ten san pham: ");
        String maLoai = readLine("Ma loai: ");
        String maKM = readLine("Ma khuyen mai (rong neu khong co): ");
        String donViTinh = readLine("Don vi tinh: ");
        double giaNhap = readDouble("Gia nhap: ");
        double giaBan = readDouble("Gia ban: ");
        int soLuong = readInt("So luong: ");
        boolean trangThai = readBoolean("Dang ban? (y/n): ");

        MatHang matHang = new MatHang(ma, ten, maLoai, blankToNull(maKM), donViTinh, giaNhap, giaBan, soLuong, trangThai);
        if (maKM != null && maKM.trim().length() > 0) {
            matHang.setKhuyenMai(khuyenMaiBUS.timTheoMa(maKM));
        }
        return matHang;
    }

    private LoaiMatHang nhapLoaiMatHang(boolean sua) {
        return new LoaiMatHang(
                readLine("Ma loai" + (sua ? " can sua" : "") + ": "),
                readLine("Ten loai: "),
                readBoolean("Dang hoat dong? (y/n): ")
        );
    }

    private NhanVien nhapNhanVien(boolean sua) {
        return new NhanVien(
                readLine("Ma nhan vien" + (sua ? " can sua" : "") + ": "),
                readLine("Ho ten: "),
                readLine("So dien thoai: "),
                readLine("Dia chi: "),
                readLine("Chuc vu: "),
                readDateNullable("Ngay vao lam dd/MM/yyyy (rong = hom nay): ", new Date()),
                readBoolean("Dang lam viec? (y/n): ")
        );
    }

    private KhuyenMai nhapKhuyenMai(boolean sua) {
        return new KhuyenMai(
                readLine("Ma khuyen mai" + (sua ? " can sua" : "") + ": "),
                readLine("Ten khuyen mai: "),
                readLine("Loai khuyen mai (phan tram/tien mat): "),
                readLine("Gia tri khuyen mai (vd 10% hoac 5000): "),
                readDateNullable("Ngay bat dau dd/MM/yyyy (rong = bo qua): "),
                readDateNullable("Ngay ket thuc dd/MM/yyyy (rong = bo qua): "),
                readBoolean("Dang ap dung? (y/n): ")
        );
    }

    private NhaCungCap nhapNhaCungCap(boolean sua) {
        return new NhaCungCap(
                readLine("Ma nha cung cap" + (sua ? " can sua" : "") + ": "),
                readLine("Ten nha cung cap: "),
                readLine("Dia chi: "),
                readLine("So dien thoai: "),
                readLine("Email: ")
        );
    }

    private PhuongThucThanhToan nhapPhuongThuc(boolean sua) {
        return new PhuongThucThanhToan(
                readLine("Ma phuong thuc" + (sua ? " can sua" : "") + ": "),
                readLine("Ten phuong thuc: "),
                readLine("Mo ta: "),
                readBoolean("Dang hoat dong? (y/n): ")
        );
    }

    private void taoPhieuNhap() {
        String maPhieu = readLine("Ma phieu nhap: ");
        String maNCC = readLine("Ma nha cung cap: ");
        String maNV = readLine("Ma nhan vien: ");
        List<ChiTietNhapHang> chiTietNhapHangs = new ArrayList<ChiTietNhapHang>();

        int soDong = readInt("So dong chi tiet: ");
        for (int i = 0; i < soDong; i++) {
            println("Chi tiet " + (i + 1));
            String maMatHang = readLine("Ma san pham: ");
            int soLuong = readInt("So luong: ");
            double donGiaNhap = readDouble("Don gia nhap: ");
            chiTietNhapHangs.add(new ChiTietNhapHang(maPhieu, maMatHang, soLuong, donGiaNhap));
        }

        PhieuNhapHang phieuNhap = phieuNhapBUS.taoPhieuNhap(maPhieu, maNCC, maNV, chiTietNhapHangs);
        println("Da tao phieu nhap: " + phieuNhap.getMaPhieu());
    }

    private void taoHoaDon() {
        String maHD = readLine("Ma hoa don: ");
        String maNV = readLine("Ma nhan vien ban hang: ");
        String maPT = readLine("Ma phuong thuc thanh toan: ");
        List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<ChiTietHoaDon>();

        int soDong = readInt("So dong chi tiet: ");
        for (int i = 0; i < soDong; i++) {
            println("Chi tiet " + (i + 1));
            String maMatHang = readLine("Ma san pham: ");
            int soLuong = readInt("So luong: ");
            chiTietHoaDons.add(new ChiTietHoaDon(maHD, maMatHang, soLuong, 0, 0));
        }

        HoaDonBanHang hoaDon = hoaDonBUS.taoHoaDon(maHD, maNV, maPT, chiTietHoaDons);
        println("Da tao hoa don: " + hoaDon.getMaHD());
        println(hoaDonBUS.inHoaDon(hoaDon.getMaHD()));
    }

    private void hienThiTaiKhoan() {
        if (authBUS.daDangNhap()) {
            println("Tai khoan: " + authBUS.getTaiKhoanHienTai().getTenDangNhap()
                    + " - " + authBUS.getTaiKhoanHienTai().getVaiTro());
        } else {
            println("Tai khoan: chua dang nhap");
        }
    }

    private void kiemTraTruyCapMenuChinh(int choice) {
        if (choice == 0 || choice == 1 || choice == 2) {
            return;
        }
        if (choice == 3 || choice == 10) {
            authBUS.yeuCauDangNhap();
            return;
        }
        if (choice >= 4 && choice <= 9 || choice == 11) {
            authBUS.yeuCauQuyen("ADMIN");
        }
    }

    private String maNhanVienTraCuu() {
        if (authBUS.coQuyen("ADMIN")) {
            return readLine("Ma nhan vien (rong = tat ca): ");
        }
        return authBUS.getTaiKhoanHienTai().getMaNV();
    }

    private void inDanhSach(List<?> list) {
        if (list == null || list.isEmpty()) {
            println("Khong co du lieu");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            println((i + 1) + ". " + list.get(i));
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            String input = readLine(prompt);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                println("Vui long nhap so nguyen");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            String input = readLine(prompt);
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException ex) {
                println("Vui long nhap so");
            }
        }
    }

    private boolean readBoolean(String prompt) {
        while (true) {
            String input = readLine(prompt).toLowerCase();
            if ("y".equals(input) || "yes".equals(input) || "co".equals(input) || "true".equals(input)) {
                return true;
            }
            if ("n".equals(input) || "no".equals(input) || "khong".equals(input) || "false".equals(input)) {
                return false;
            }
            println("Nhap y hoac n");
        }
    }

    private Date readDateNullable(String prompt) {
        return readDateNullable(prompt, null);
    }

    private Date readDateNullable(String prompt, Date defaultDate) {
        while (true) {
            String input = readLine(prompt);
            if (input.length() == 0) {
                return defaultDate;
            }
            try {
                return dateFormat.parse(input);
            } catch (ParseException ex) {
                println("Ngay khong hop le, dinh dang dung la dd/MM/yyyy");
            }
        }
    }

    private String blankToNull(String value) {
        return value == null || value.trim().length() == 0 ? null : value.trim();
    }

    private void println(String value) {
        System.out.println(value);
    }
}
