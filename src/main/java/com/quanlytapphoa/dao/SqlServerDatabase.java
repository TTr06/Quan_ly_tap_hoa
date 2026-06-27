package com.quanlytapphoa.dao;

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
import com.quanlytapphoa.model.TaiKhoan;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class SqlServerDatabase {
    public void kiemTraKetNoi() {
        try (Connection ignored = Ketnoi.getConnection()) {
            // Only opens and closes the connection.
        } catch (SQLException ex) {
            throw new DatabaseException("Khong ket noi duoc database", ex);
        }
    }

    public List<LoaiMatHang> layDanhSachLoaiMatHang() {
        List<LoaiMatHang> result = new ArrayList<LoaiMatHang>();
        String sql = "SELECT MaLoai, TenLoai, TrangThai FROM dbo.LoaiMatHang ORDER BY MaLoai";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new LoaiMatHang(
                        rs.getString("MaLoai"),
                        rs.getString("TenLoai"),
                        rs.getBoolean("TrangThai")
                ));
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc LoaiMatHang", ex);
        }
    }

    public List<KhuyenMai> layDanhSachKhuyenMai() {
        List<KhuyenMai> result = new ArrayList<KhuyenMai>();
        String sql = "SELECT MaKM, TenKM, LoaiKM, GiaTriKM, NgayBatDau, NgayKetThuc, TrangThai "
                + "FROM dbo.KhuyenMai ORDER BY MaKM";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new KhuyenMai(
                        rs.getString("MaKM"),
                        rs.getString("TenKM"),
                        rs.getString("LoaiKM"),
                        rs.getString("GiaTriKM"),
                        readDate(rs, "NgayBatDau"),
                        readDate(rs, "NgayKetThuc"),
                        rs.getBoolean("TrangThai")
                ));
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc KhuyenMai", ex);
        }
    }

    public List<MatHang> layDanhSachMatHang(List<KhuyenMai> dsKhuyenMai) {
        List<MatHang> result = new ArrayList<MatHang>();
        String sql = "SELECT MaMatHang, TenMatHang, MaLoai, MaKM, DonViTinh, GiaNhap, GiaBan, SoLuong, TrangThai "
                + "FROM dbo.MatHang ORDER BY MaMatHang";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                MatHang matHang = new MatHang(
                        rs.getString("MaMatHang"),
                        rs.getString("TenMatHang"),
                        rs.getString("MaLoai"),
                        rs.getString("MaKM"),
                        rs.getString("DonViTinh"),
                        rs.getDouble("GiaNhap"),
                        rs.getDouble("GiaBan"),
                        rs.getInt("SoLuong"),
                        rs.getBoolean("TrangThai")
                );
                matHang.setKhuyenMai(timKhuyenMai(dsKhuyenMai, matHang.getMaKM()));
                result.add(matHang);
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc MatHang", ex);
        }
    }

    public List<NhaCungCap> layDanhSachNhaCungCap() {
        List<NhaCungCap> result = new ArrayList<NhaCungCap>();
        String sql = "SELECT MaNCC, TenNCC, DiaChi, SoDienThoai, Email FROM dbo.NhaCungCap ORDER BY MaNCC";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new NhaCungCap(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email")
                ));
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc NhaCungCap", ex);
        }
    }

    public List<NhanVien> layDanhSachNhanVien() {
        List<NhanVien> result = new ArrayList<NhanVien>();
        String sql = "SELECT MaNV, HoTen, SoDienThoai, DiaChi, ChucVu, NgayVaoLam, TrangThai "
                + "FROM dbo.NhanVien ORDER BY MaNV";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("ChucVu"),
                        readDate(rs, "NgayVaoLam"),
                        rs.getBoolean("TrangThai")
                ));
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc NhanVien", ex);
        }
    }

    public List<PhuongThucThanhToan> layDanhSachPhuongThucThanhToan() {
        List<PhuongThucThanhToan> result = new ArrayList<PhuongThucThanhToan>();
        String sql = "SELECT MaPT, TenPT, MoTa, TrangThai FROM dbo.PhuongThucThanhToan ORDER BY MaPT";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new PhuongThucThanhToan(
                        rs.getString("MaPT"),
                        rs.getString("TenPT"),
                        rs.getString("MoTa"),
                        rs.getBoolean("TrangThai")
                ));
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc PhuongThucThanhToan", ex);
        }
    }

    public List<TaiKhoan> layDanhSachTaiKhoan() {
        List<TaiKhoan> result = new ArrayList<TaiKhoan>();
        String sql = "SELECT TenDangNhap, MatKhau, VaiTro, MaNV, TrangThai FROM dbo.TaiKhoan ORDER BY TenDangNhap";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new TaiKhoan(
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("VaiTro"),
                        rs.getString("MaNV"),
                        rs.getBoolean("TrangThai")
                ));
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc TaiKhoan", ex);
        }
    }

    public List<HoaDonBanHang> layDanhSachHoaDonBanHang(
            List<NhanVien> dsNhanVien,
            List<PhuongThucThanhToan> dsPhuongThuc
    ) {
        List<HoaDonBanHang> result = new ArrayList<HoaDonBanHang>();
        String sql = "SELECT MaHD, NgayLap, TongTien, GiaTriKM, ThanhTien, MaNV, MaPT "
                + "FROM dbo.HoaDonBanHang ORDER BY NgayLap, MaHD";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                HoaDonBanHang hoaDon = new HoaDonBanHang();
                hoaDon.setMaHD(rs.getString("MaHD"));
                hoaDon.setNgayLap(readDate(rs, "NgayLap"));
                hoaDon.setTongTien(rs.getDouble("TongTien"));
                hoaDon.setGiaTriKM(rs.getDouble("GiaTriKM"));
                hoaDon.setThanhTien(rs.getDouble("ThanhTien"));
                hoaDon.setNhanVien(timNhanVien(dsNhanVien, rs.getString("MaNV")));
                hoaDon.setPhuongThucThanhToanRef(timPhuongThuc(dsPhuongThuc, rs.getString("MaPT")));
                if (hoaDon.getNhanVien() == null) {
                    hoaDon.setMaNV(rs.getString("MaNV"));
                }
                if (hoaDon.getPhuongThucThanhToanRef() == null) {
                    hoaDon.setMaPT(rs.getString("MaPT"));
                }
                result.add(hoaDon);
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc HoaDonBanHang", ex);
        }
    }

    public List<ChiTietHoaDon> layDanhSachChiTietHoaDon() {
        List<ChiTietHoaDon> result = new ArrayList<ChiTietHoaDon>();
        String sql = "SELECT MaHD, MaMatHang, SoLuong, DonGia, GiaTriKM, ThanhTien, ThanhTienSauKM "
                + "FROM dbo.ChiTietHoaDon ORDER BY MaHD, MaMatHang";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                ChiTietHoaDon chiTiet = new ChiTietHoaDon();
                chiTiet.setMaHD(rs.getString("MaHD"));
                chiTiet.setMaMatHang(rs.getString("MaMatHang"));
                chiTiet.setSoLuong(rs.getInt("SoLuong"));
                chiTiet.setDonGia(rs.getDouble("DonGia"));
                chiTiet.setGiaTriKM(rs.getDouble("GiaTriKM"));
                chiTiet.setThanhTien(rs.getDouble("ThanhTien"));
                chiTiet.setThanhTienSauKM(rs.getDouble("ThanhTienSauKM"));
                result.add(chiTiet);
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc ChiTietHoaDon", ex);
        }
    }

    public List<PhieuNhapHang> layDanhSachPhieuNhapHang(
            List<NhaCungCap> dsNhaCungCap,
            List<NhanVien> dsNhanVien
    ) {
        List<PhieuNhapHang> result = new ArrayList<PhieuNhapHang>();
        String sql = "SELECT MaPhieu, NgayNhap, TongTien, MaNCC, MaNV FROM dbo.PhieuNhapHang ORDER BY NgayNhap, MaPhieu";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                PhieuNhapHang phieuNhap = new PhieuNhapHang();
                phieuNhap.setMaPhieu(rs.getString("MaPhieu"));
                phieuNhap.setNgayNhap(readDate(rs, "NgayNhap"));
                phieuNhap.setTongTien(rs.getDouble("TongTien"));
                phieuNhap.setNhaCungCap(timNhaCungCap(dsNhaCungCap, rs.getString("MaNCC")));
                phieuNhap.setNhanVien(timNhanVien(dsNhanVien, rs.getString("MaNV")));
                if (phieuNhap.getNhaCungCap() == null) {
                    phieuNhap.setMaNCC(rs.getString("MaNCC"));
                }
                if (phieuNhap.getNhanVien() == null) {
                    phieuNhap.setMaNV(rs.getString("MaNV"));
                }
                result.add(phieuNhap);
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc PhieuNhapHang", ex);
        }
    }

    public List<ChiTietNhapHang> layDanhSachChiTietNhapHang() {
        List<ChiTietNhapHang> result = new ArrayList<ChiTietNhapHang>();
        String sql = "SELECT MaPhieu, MaMatHang, SoLuong, DonGiaNhap, ThanhTien "
                + "FROM dbo.ChiTietNhapHang ORDER BY MaPhieu, MaMatHang";
        try (Connection connection = Ketnoi.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                ChiTietNhapHang chiTiet = new ChiTietNhapHang();
                chiTiet.setMaPhieu(rs.getString("MaPhieu"));
                chiTiet.setMaMatHang(rs.getString("MaMatHang"));
                chiTiet.setSoLuong(rs.getInt("SoLuong"));
                chiTiet.setDonGiaNhap(rs.getDouble("DonGiaNhap"));
                chiTiet.setThanhTien(rs.getDouble("ThanhTien"));
                result.add(chiTiet);
            }
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException("Khong doc duoc ChiTietNhapHang", ex);
        }
    }

    public void luuLoaiMatHang(LoaiMatHang loaiMatHang) {
        String sql = "MERGE dbo.LoaiMatHang AS target "
                + "USING (SELECT ? AS MaLoai) AS source ON target.MaLoai = source.MaLoai "
                + "WHEN MATCHED THEN UPDATE SET TenLoai = ?, TrangThai = ? "
                + "WHEN NOT MATCHED THEN INSERT (MaLoai, TenLoai, TrangThai) VALUES (?, ?, ?);";
        executeUpdate(sql, ps -> {
            ps.setString(1, loaiMatHang.getMaLoai());
            ps.setString(2, loaiMatHang.getTenLoai());
            ps.setBoolean(3, loaiMatHang.isTrangThai());
            ps.setString(4, loaiMatHang.getMaLoai());
            ps.setString(5, loaiMatHang.getTenLoai());
            ps.setBoolean(6, loaiMatHang.isTrangThai());
        }, "Khong luu duoc LoaiMatHang");
    }

    public void xoaLoaiMatHang(String maLoai) {
        executeUpdate("UPDATE dbo.LoaiMatHang SET TrangThai = 0 WHERE MaLoai = ?",
                ps -> ps.setString(1, maLoai),
                "Khong xoa duoc LoaiMatHang");
    }

    public void luuMatHang(MatHang matHang) {
        String sql = "MERGE dbo.MatHang AS target "
                + "USING (SELECT ? AS MaMatHang) AS source ON target.MaMatHang = source.MaMatHang "
                + "WHEN MATCHED THEN UPDATE SET TenMatHang = ?, MaLoai = ?, MaKM = ?, DonViTinh = ?, "
                + "GiaNhap = ?, GiaBan = ?, SoLuong = ?, TrangThai = ? "
                + "WHEN NOT MATCHED THEN INSERT "
                + "(MaMatHang, TenMatHang, MaLoai, MaKM, DonViTinh, GiaNhap, GiaBan, SoLuong, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        executeUpdate(sql, ps -> {
            ps.setString(1, matHang.getMaMatHang());
            ps.setString(2, matHang.getTenMatHang());
            setNullableString(ps, 3, matHang.getMaLoai());
            setNullableString(ps, 4, matHang.getMaKM());
            ps.setString(5, matHang.getDonViTinh());
            ps.setDouble(6, matHang.getGiaNhap());
            ps.setDouble(7, matHang.getGiaBan());
            ps.setInt(8, matHang.getSoLuong());
            ps.setBoolean(9, matHang.isTrangThai());
            ps.setString(10, matHang.getMaMatHang());
            ps.setString(11, matHang.getTenMatHang());
            setNullableString(ps, 12, matHang.getMaLoai());
            setNullableString(ps, 13, matHang.getMaKM());
            ps.setString(14, matHang.getDonViTinh());
            ps.setDouble(15, matHang.getGiaNhap());
            ps.setDouble(16, matHang.getGiaBan());
            ps.setInt(17, matHang.getSoLuong());
            ps.setBoolean(18, matHang.isTrangThai());
        }, "Khong luu duoc MatHang");
    }

    public void xoaMatHang(String maMatHang) {
        executeUpdate("UPDATE dbo.MatHang SET TrangThai = 0 WHERE MaMatHang = ?",
                ps -> ps.setString(1, maMatHang),
                "Khong xoa duoc MatHang");
    }

    public void capNhatTonKho(MatHang matHang) {
        executeUpdate("UPDATE dbo.MatHang SET SoLuong = ?, TrangThai = ? WHERE MaMatHang = ?",
                ps -> {
                    ps.setInt(1, matHang.getSoLuong());
                    ps.setBoolean(2, matHang.isTrangThai());
                    ps.setString(3, matHang.getMaMatHang());
                },
                "Khong cap nhat duoc ton kho");
    }

    public void luuNhanVien(NhanVien nhanVien) {
        String sql = "MERGE dbo.NhanVien AS target "
                + "USING (SELECT ? AS MaNV) AS source ON target.MaNV = source.MaNV "
                + "WHEN MATCHED THEN UPDATE SET HoTen = ?, SoDienThoai = ?, DiaChi = ?, ChucVu = ?, "
                + "NgayVaoLam = ?, TrangThai = ? "
                + "WHEN NOT MATCHED THEN INSERT "
                + "(MaNV, HoTen, SoDienThoai, DiaChi, ChucVu, NgayVaoLam, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        executeUpdate(sql, ps -> {
            ps.setString(1, nhanVien.getMaNV());
            ps.setString(2, nhanVien.getHoTen());
            ps.setString(3, nhanVien.getSoDienThoai());
            ps.setString(4, nhanVien.getDiaChi());
            ps.setString(5, nhanVien.getChucVu());
            setDate(ps, 6, nhanVien.getNgayVaoLam());
            ps.setBoolean(7, nhanVien.isTrangThai());
            ps.setString(8, nhanVien.getMaNV());
            ps.setString(9, nhanVien.getHoTen());
            ps.setString(10, nhanVien.getSoDienThoai());
            ps.setString(11, nhanVien.getDiaChi());
            ps.setString(12, nhanVien.getChucVu());
            setDate(ps, 13, nhanVien.getNgayVaoLam());
            ps.setBoolean(14, nhanVien.isTrangThai());
        }, "Khong luu duoc NhanVien");
    }

    public void xoaNhanVien(String maNV) {
        executeUpdate("UPDATE dbo.NhanVien SET TrangThai = 0 WHERE MaNV = ?",
                ps -> ps.setString(1, maNV),
                "Khong xoa duoc NhanVien");
    }

    public void luuNhaCungCap(NhaCungCap nhaCungCap) {
        String sql = "MERGE dbo.NhaCungCap AS target "
                + "USING (SELECT ? AS MaNCC) AS source ON target.MaNCC = source.MaNCC "
                + "WHEN MATCHED THEN UPDATE SET TenNCC = ?, DiaChi = ?, SoDienThoai = ?, Email = ? "
                + "WHEN NOT MATCHED THEN INSERT (MaNCC, TenNCC, DiaChi, SoDienThoai, Email) "
                + "VALUES (?, ?, ?, ?, ?);";
        executeUpdate(sql, ps -> {
            ps.setString(1, nhaCungCap.getMaNCC());
            ps.setString(2, nhaCungCap.getTenNCC());
            ps.setString(3, nhaCungCap.getDiaChi());
            ps.setString(4, nhaCungCap.getSoDienThoai());
            ps.setString(5, nhaCungCap.getEmail());
            ps.setString(6, nhaCungCap.getMaNCC());
            ps.setString(7, nhaCungCap.getTenNCC());
            ps.setString(8, nhaCungCap.getDiaChi());
            ps.setString(9, nhaCungCap.getSoDienThoai());
            ps.setString(10, nhaCungCap.getEmail());
        }, "Khong luu duoc NhaCungCap");
    }

    public void xoaNhaCungCap(String maNCC) {
        executeUpdate("DELETE FROM dbo.NhaCungCap WHERE MaNCC = ?",
                ps -> ps.setString(1, maNCC),
                "Khong xoa duoc NhaCungCap");
    }

    public void luuPhuongThucThanhToan(PhuongThucThanhToan phuongThuc) {
        String sql = "MERGE dbo.PhuongThucThanhToan AS target "
                + "USING (SELECT ? AS MaPT) AS source ON target.MaPT = source.MaPT "
                + "WHEN MATCHED THEN UPDATE SET TenPT = ?, MoTa = ?, TrangThai = ? "
                + "WHEN NOT MATCHED THEN INSERT (MaPT, TenPT, MoTa, TrangThai) VALUES (?, ?, ?, ?);";
        executeUpdate(sql, ps -> {
            ps.setString(1, phuongThuc.getMaPT());
            ps.setString(2, phuongThuc.getTenPT());
            ps.setString(3, phuongThuc.getMoTa());
            ps.setBoolean(4, phuongThuc.isTrangThai());
            ps.setString(5, phuongThuc.getMaPT());
            ps.setString(6, phuongThuc.getTenPT());
            ps.setString(7, phuongThuc.getMoTa());
            ps.setBoolean(8, phuongThuc.isTrangThai());
        }, "Khong luu duoc PhuongThucThanhToan");
    }

    public void xoaPhuongThucThanhToan(String maPT) {
        executeUpdate("UPDATE dbo.PhuongThucThanhToan SET TrangThai = 0 WHERE MaPT = ?",
                ps -> ps.setString(1, maPT),
                "Khong xoa duoc PhuongThucThanhToan");
    }

    public void luuKhuyenMai(KhuyenMai khuyenMai) {
        String sql = "MERGE dbo.KhuyenMai AS target "
                + "USING (SELECT ? AS MaKM) AS source ON target.MaKM = source.MaKM "
                + "WHEN MATCHED THEN UPDATE SET TenKM = ?, LoaiKM = ?, GiaTriKM = ?, "
                + "NgayBatDau = ?, NgayKetThuc = ?, TrangThai = ? "
                + "WHEN NOT MATCHED THEN INSERT "
                + "(MaKM, TenKM, LoaiKM, GiaTriKM, NgayBatDau, NgayKetThuc, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        executeUpdate(sql, ps -> {
            ps.setString(1, khuyenMai.getMaKM());
            ps.setString(2, khuyenMai.getTenKM());
            ps.setString(3, khuyenMai.getLoaiKM());
            ps.setString(4, khuyenMai.getGiaTriKM());
            setDate(ps, 5, khuyenMai.getNgayBatDau());
            setDate(ps, 6, khuyenMai.getNgayKetThuc());
            ps.setBoolean(7, khuyenMai.isTrangThai());
            ps.setString(8, khuyenMai.getMaKM());
            ps.setString(9, khuyenMai.getTenKM());
            ps.setString(10, khuyenMai.getLoaiKM());
            ps.setString(11, khuyenMai.getGiaTriKM());
            setDate(ps, 12, khuyenMai.getNgayBatDau());
            setDate(ps, 13, khuyenMai.getNgayKetThuc());
            ps.setBoolean(14, khuyenMai.isTrangThai());
        }, "Khong luu duoc KhuyenMai");
    }

    public void boKhuyenMai(String maKM) {
        try (Connection connection = Ketnoi.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps1 = connection.prepareStatement("UPDATE dbo.KhuyenMai SET TrangThai = 0 WHERE MaKM = ?");
                    PreparedStatement ps2 = connection.prepareStatement("UPDATE dbo.MatHang SET MaKM = NULL WHERE MaKM = ?")) {
                ps1.setString(1, maKM);
                ps1.executeUpdate();
                ps2.setString(1, maKM);
                ps2.executeUpdate();
                connection.commit();
            } catch (SQLException ex) {
                rollback(connection);
                throw ex;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Khong bo duoc KhuyenMai", ex);
        }
    }

    public void ganKhuyenMaiChoSanPham(String maMatHang, String maKM) {
        executeUpdate("UPDATE dbo.MatHang SET MaKM = ? WHERE MaMatHang = ?",
                ps -> {
                    ps.setString(1, maKM);
                    ps.setString(2, maMatHang);
                },
                "Khong gan duoc KhuyenMai cho MatHang");
    }

    public void luuTaiKhoan(TaiKhoan taiKhoan) {
        String sql = "MERGE dbo.TaiKhoan AS target "
                + "USING (SELECT ? AS TenDangNhap) AS source ON target.TenDangNhap = source.TenDangNhap "
                + "WHEN MATCHED THEN UPDATE SET MatKhau = ?, VaiTro = ?, MaNV = ?, TrangThai = ? "
                + "WHEN NOT MATCHED THEN INSERT (TenDangNhap, MatKhau, VaiTro, MaNV, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?);";
        executeUpdate(sql, ps -> {
            ps.setString(1, taiKhoan.getTenDangNhap());
            ps.setString(2, taiKhoan.getMatKhau());
            ps.setString(3, taiKhoan.getVaiTro());
            ps.setString(4, taiKhoan.getMaNV());
            ps.setBoolean(5, taiKhoan.isTrangThai());
            ps.setString(6, taiKhoan.getTenDangNhap());
            ps.setString(7, taiKhoan.getMatKhau());
            ps.setString(8, taiKhoan.getVaiTro());
            ps.setString(9, taiKhoan.getMaNV());
            ps.setBoolean(10, taiKhoan.isTrangThai());
        }, "Khong luu duoc TaiKhoan");
    }

    public void xoaTaiKhoan(String tenDangNhap) {
        executeUpdate("UPDATE dbo.TaiKhoan SET TrangThai = 0 WHERE TenDangNhap = ?",
                ps -> ps.setString(1, tenDangNhap),
                "Khong xoa duoc TaiKhoan");
    }

    public void themHoaDon(HoaDonBanHang hoaDon, List<ChiTietHoaDon> chiTietHoaDons) {
        try (Connection connection = Ketnoi.getConnection()) {
            connection.setAutoCommit(false);
            try {
                insertHoaDon(connection, hoaDon);
                for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
                    insertChiTietHoaDon(connection, chiTiet);
                }
                connection.commit();
            } catch (SQLException ex) {
                rollback(connection);
                throw ex;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Khong luu duoc HoaDon", ex);
        }
    }

    public void huyHoaDon(String maHD, List<ChiTietHoaDon> chiTietHoaDons) {
        try (Connection connection = Ketnoi.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
                    updateTonKho(connection, chiTiet.getMaMatHang(), chiTiet.getSoLuong());
                }
                try (PreparedStatement ps = connection.prepareStatement("DELETE FROM dbo.ChiTietHoaDon WHERE MaHD = ?")) {
                    ps.setString(1, maHD);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = connection.prepareStatement("DELETE FROM dbo.HoaDonBanHang WHERE MaHD = ?")) {
                    ps.setString(1, maHD);
                    ps.executeUpdate();
                }
                connection.commit();
            } catch (SQLException ex) {
                rollback(connection);
                throw ex;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Khong huy duoc HoaDon", ex);
        }
    }

    public void themPhieuNhap(PhieuNhapHang phieuNhap, List<ChiTietNhapHang> chiTietNhapHangs) {
        try (Connection connection = Ketnoi.getConnection()) {
            connection.setAutoCommit(false);
            try {
                insertPhieuNhap(connection, phieuNhap);
                for (ChiTietNhapHang chiTiet : chiTietNhapHangs) {
                    insertChiTietNhapHang(connection, chiTiet);
                }
                connection.commit();
            } catch (SQLException ex) {
                rollback(connection);
                throw ex;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Khong luu duoc PhieuNhap", ex);
        }
    }

    public void xoaPhieuNhap(String maPhieu, List<ChiTietNhapHang> chiTietNhapHangs) {
        try (Connection connection = Ketnoi.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (ChiTietNhapHang chiTiet : chiTietNhapHangs) {
                    updateTonKho(connection, chiTiet.getMaMatHang(), -chiTiet.getSoLuong());
                }
                try (PreparedStatement ps = connection.prepareStatement("DELETE FROM dbo.ChiTietNhapHang WHERE MaPhieu = ?")) {
                    ps.setString(1, maPhieu);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = connection.prepareStatement("DELETE FROM dbo.PhieuNhapHang WHERE MaPhieu = ?")) {
                    ps.setString(1, maPhieu);
                    ps.executeUpdate();
                }
                connection.commit();
            } catch (SQLException ex) {
                rollback(connection);
                throw ex;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Khong xoa duoc PhieuNhap", ex);
        }
    }

    private void insertHoaDon(Connection connection, HoaDonBanHang hoaDon) throws SQLException {
        String sql = "INSERT INTO dbo.HoaDonBanHang (MaHD, NgayLap, TongTien, GiaTriKM, ThanhTien, MaNV, MaPT) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, hoaDon.getMaHD());
            setDate(ps, 2, hoaDon.getNgayLap());
            ps.setDouble(3, hoaDon.getTongTien());
            ps.setDouble(4, hoaDon.getGiaTriKM());
            ps.setDouble(5, hoaDon.getThanhTien());
            ps.setString(6, hoaDon.getMaNV());
            ps.setString(7, hoaDon.getMaPT());
            ps.executeUpdate();
        }
    }

    private void insertChiTietHoaDon(Connection connection, ChiTietHoaDon chiTiet) throws SQLException {
        String sql = "INSERT INTO dbo.ChiTietHoaDon (MaHD, MaMatHang, SoLuong, DonGia, GiaTriKM) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, chiTiet.getMaHD());
            ps.setString(2, chiTiet.getMaMatHang());
            ps.setInt(3, chiTiet.getSoLuong());
            ps.setDouble(4, chiTiet.getDonGia());
            ps.setDouble(5, chiTiet.getGiaTriKM());
            ps.executeUpdate();
        }
    }

    private void insertPhieuNhap(Connection connection, PhieuNhapHang phieuNhap) throws SQLException {
        String sql = "INSERT INTO dbo.PhieuNhapHang (MaPhieu, NgayNhap, TongTien, MaNCC, MaNV) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phieuNhap.getMaPhieu());
            setDate(ps, 2, phieuNhap.getNgayNhap());
            ps.setDouble(3, phieuNhap.getTongTien());
            ps.setString(4, phieuNhap.getMaNCC());
            ps.setString(5, phieuNhap.getMaNV());
            ps.executeUpdate();
        }
    }

    private void insertChiTietNhapHang(Connection connection, ChiTietNhapHang chiTiet) throws SQLException {
        String sql = "INSERT INTO dbo.ChiTietNhapHang (MaPhieu, MaMatHang, SoLuong, DonGiaNhap) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, chiTiet.getMaPhieu());
            ps.setString(2, chiTiet.getMaMatHang());
            ps.setInt(3, chiTiet.getSoLuong());
            ps.setDouble(4, chiTiet.getDonGiaNhap());
            ps.executeUpdate();
        }
    }

    private void updateTonKho(Connection connection, String maMatHang, int delta) throws SQLException {
        String sql = "UPDATE dbo.MatHang SET SoLuong = SoLuong + ? WHERE MaMatHang = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, delta);
            ps.setString(2, maMatHang);
            ps.executeUpdate();
        }
    }

    private void executeUpdate(String sql, StatementBinder binder, String errorMessage) {
        try (Connection connection = Ketnoi.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            binder.bind(ps);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(errorMessage, ex);
        }
    }

    private java.util.Date readDate(ResultSet rs, String column) throws SQLException {
        Date date = rs.getDate(column);
        return date == null ? null : new java.util.Date(date.getTime());
    }

    private void setDate(PreparedStatement ps, int index, java.util.Date date) throws SQLException {
        if (date == null) {
            ps.setNull(index, Types.DATE);
        } else {
            ps.setDate(index, new Date(date.getTime()));
        }
    }

    private void setNullableString(PreparedStatement ps, int index, String value) throws SQLException {
        if (value == null || value.trim().isEmpty()) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, value);
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ignored) {
            // Preserve the original exception.
        }
    }

    private KhuyenMai timKhuyenMai(List<KhuyenMai> dsKhuyenMai, String maKM) {
        if (maKM == null) {
            return null;
        }
        for (KhuyenMai khuyenMai : dsKhuyenMai) {
            if (maKM.equalsIgnoreCase(khuyenMai.getMaKM())) {
                return khuyenMai;
            }
        }
        return null;
    }

    private NhanVien timNhanVien(List<NhanVien> dsNhanVien, String maNV) {
        if (maNV == null) {
            return null;
        }
        for (NhanVien nhanVien : dsNhanVien) {
            if (maNV.equalsIgnoreCase(nhanVien.getMaNV())) {
                return nhanVien;
            }
        }
        return null;
    }

    private NhaCungCap timNhaCungCap(List<NhaCungCap> dsNhaCungCap, String maNCC) {
        if (maNCC == null) {
            return null;
        }
        for (NhaCungCap nhaCungCap : dsNhaCungCap) {
            if (maNCC.equalsIgnoreCase(nhaCungCap.getMaNCC())) {
                return nhaCungCap;
            }
        }
        return null;
    }

    private PhuongThucThanhToan timPhuongThuc(List<PhuongThucThanhToan> dsPhuongThuc, String maPT) {
        if (maPT == null) {
            return null;
        }
        for (PhuongThucThanhToan phuongThuc : dsPhuongThuc) {
            if (maPT.equalsIgnoreCase(phuongThuc.getMaPT())) {
                return phuongThuc;
            }
        }
        return null;
    }

    private interface StatementBinder {
        void bind(PreparedStatement ps) throws SQLException;
    }
}
