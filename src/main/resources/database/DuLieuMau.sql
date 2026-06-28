USE QuanLyTapHoa;
GO

SET NOCOUNT ON;
SET XACT_ABORT ON;

BEGIN TRY
    BEGIN TRANSACTION;

    INSERT INTO dbo.LoaiMatHang (MaLoai, TenLoai, TrangThai)
    SELECT v.MaLoai, v.TenLoai, v.TrangThai
    FROM (VALUES
        ('L01', N'Thuc pham', 1),
        ('L02', N'Do uong', 1),
        ('L03', N'Hoa pham', 1),
        ('L04', N'Do gia dung', 1),
        ('L05', N'Banh keo', 1)
    ) v(MaLoai, TenLoai, TrangThai)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.LoaiMatHang x WHERE x.MaLoai = v.MaLoai
    );

    INSERT INTO dbo.KhuyenMai
        (MaKM, TenKM, LoaiKM, GiaTriKM, NgayBatDau, NgayKetThuc, TrangThai)
    SELECT v.MaKM, v.TenKM, v.LoaiKM, v.GiaTriKM,
           v.NgayBatDau, v.NgayKetThuc, v.TrangThai
    FROM (VALUES
        ('KM001', N'Giam 10 phan tram', N'Phan tram', N'10%',
            CONVERT(DATE, '2020-01-01'), CAST(NULL AS DATE), 1),
        ('KM002', N'Giam 5000 dong', N'So tien', N'5000',
            CONVERT(DATE, '2020-01-01'), CAST(NULL AS DATE), 1),
        ('KM003', N'Giam 5 phan tram', N'Phan tram', N'5%',
            DATEADD(DAY, -30, CAST(GETDATE() AS DATE)),
            DATEADD(DAY, 30, CAST(GETDATE() AS DATE)), 1),
        ('KM004', N'Giam 10000 dong', N'So tien', N'10000',
            DATEADD(DAY, -30, CAST(GETDATE() AS DATE)),
            DATEADD(DAY, 30, CAST(GETDATE() AS DATE)), 1)
    ) v(MaKM, TenKM, LoaiKM, GiaTriKM, NgayBatDau, NgayKetThuc, TrangThai)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.KhuyenMai x WHERE x.MaKM = v.MaKM
    );

    INSERT INTO dbo.NhaCungCap (MaNCC, TenNCC, DiaChi, SoDienThoai, Email)
    SELECT v.MaNCC, v.TenNCC, v.DiaChi, v.SoDienThoai, v.Email
    FROM (VALUES
        ('NCC001', N'Nha cung cap tong hop', N'TP HCM', '0280000000', 'ncc@example.com'),
        ('NCC002', N'Dai ly do uong', N'Dong Nai', '0251000000', 'douong@example.com'),
        ('NCC003', N'Cong ty gia dung Viet', N'Binh Duong', '0274000001', 'giadung@example.com'),
        ('NCC004', N'Nha phan phoi banh keo', N'Long An', '0272000002', 'banhkeo@example.com')
    ) v(MaNCC, TenNCC, DiaChi, SoDienThoai, Email)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.NhaCungCap x WHERE x.MaNCC = v.MaNCC
    );

    INSERT INTO dbo.NhanVien
        (MaNV, HoTen, SoDienThoai, DiaChi, ChucVu, NgayVaoLam, TrangThai)
    SELECT v.MaNV, v.HoTen, v.SoDienThoai, v.DiaChi,
           v.ChucVu, v.NgayVaoLam, v.TrangThai
    FROM (VALUES
        ('NV001', N'Quan tri vien', '0900000000', N'Cua hang', N'Admin',
            CONVERT(DATE, '2024-01-01'), 1),
        ('NV002', N'Nguyen Van An', '0911111111', N'TP HCM', N'Nhan vien ban hang',
            CONVERT(DATE, '2024-06-01'), 1),
        ('NV003', N'Tran Thi Binh', '0922222222', N'Dong Nai', N'Thu ngan',
            CONVERT(DATE, '2025-01-15'), 1),
        ('NV004', N'Le Minh Khoa', '0933333333', N'Binh Duong', N'Nhan vien kho',
            CONVERT(DATE, '2025-03-10'), 1)
    ) v(MaNV, HoTen, SoDienThoai, DiaChi, ChucVu, NgayVaoLam, TrangThai)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.NhanVien x WHERE x.MaNV = v.MaNV
    );

    INSERT INTO dbo.PhuongThucThanhToan (MaPT, TenPT, MoTa, TrangThai)
    SELECT v.MaPT, v.TenPT, v.MoTa, v.TrangThai
    FROM (VALUES
        ('PT001', N'Tien mat', N'Thanh toan truc tiep', 1),
        ('PT002', N'Chuyen khoan', N'Thanh toan qua ngan hang', 1),
        ('PT003', N'Vi dien tu', N'Thanh toan bang vi dien tu', 1)
    ) v(MaPT, TenPT, MoTa, TrangThai)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.PhuongThucThanhToan x WHERE x.MaPT = v.MaPT
    );

    INSERT INTO dbo.MatHang
        (MaMatHang, TenMatHang, MaLoai, MaKM, DonViTinh,
         GiaNhap, GiaBan, SoLuong, TrangThai)
    SELECT v.MaMatHang, v.TenMatHang, v.MaLoai, v.MaKM, v.DonViTinh,
           v.GiaNhap, v.GiaBan, v.SoLuong, v.TrangThai
    FROM (VALUES
        ('MH001', N'Duong cat', 'L01', NULL, N'kg', 18000, 22000, 100, 1),
        ('MH002', N'Sua hop', 'L02', 'KM001', N'hop', 7000, 10000, 80, 1),
        ('MH003', N'Nuoc rua chen', 'L03', 'KM002', N'chai', 18000, 25000, 40, 1),
        ('MH004', N'Gao thom', 'L01', NULL, N'kg', 16000, 21000, 60, 1),
        ('MH005', N'Mi an lien', 'L01', NULL, N'goi', 3000, 4500, 200, 1),
        ('MH006', N'Nuoc suoi', 'L02', NULL, N'chai', 5000, 7000, 150, 1),
        ('MH007', N'Dau an', 'L01', 'KM003', N'chai', 35000, 45000, 70, 1),
        ('MH008', N'Khan giay', 'L04', NULL, N'goi', 9000, 15000, 90, 1),
        ('MH009', N'Banh quy', 'L05', 'KM004', N'hop', 12000, 18000, 75, 1),
        ('MH010', N'Dau goi', 'L03', NULL, N'chai', 30000, 42000, 45, 1)
    ) v(MaMatHang, TenMatHang, MaLoai, MaKM, DonViTinh,
        GiaNhap, GiaBan, SoLuong, TrangThai)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.MatHang x WHERE x.MaMatHang = v.MaMatHang
    );

    INSERT INTO dbo.TaiKhoan (TenDangNhap, MatKhau, VaiTro, MaNV, TrangThai)
    SELECT v.TenDangNhap, v.MatKhau, v.VaiTro, v.MaNV, v.TrangThai
    FROM (VALUES
        ('admin', '123456', 'ADMIN', 'NV001', 1),
        ('nhanvien', '123456', 'NHAN_VIEN', 'NV002', 1),
        ('thungan', '123456', 'NHAN_VIEN', 'NV003', 1),
        ('kho', '123456', 'NHAN_VIEN', 'NV004', 1)
    ) v(TenDangNhap, MatKhau, VaiTro, MaNV, TrangThai)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.TaiKhoan x
        WHERE x.TenDangNhap = v.TenDangNhap
    );

    INSERT INTO dbo.PhieuNhapHang
        (MaPhieu, NgayNhap, TongTien, MaNCC, MaNV)
    SELECT v.MaPhieu, v.NgayNhap, 0, v.MaNCC, v.MaNV
    FROM (VALUES
        ('PNM001', DATEADD(DAY, -10, CAST(GETDATE() AS DATE)), 'NCC001', 'NV001'),
        ('PNM002', DATEADD(DAY, -6, CAST(GETDATE() AS DATE)), 'NCC002', 'NV003'),
        ('PNM003', DATEADD(DAY, -2, CAST(GETDATE() AS DATE)), 'NCC003', 'NV004')
    ) v(MaPhieu, NgayNhap, MaNCC, MaNV)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.PhieuNhapHang x WHERE x.MaPhieu = v.MaPhieu
    );

    INSERT INTO dbo.ChiTietNhapHang
        (MaPhieu, MaMatHang, SoLuong, DonGiaNhap)
    SELECT v.MaPhieu, v.MaMatHang, v.SoLuong, v.DonGiaNhap
    FROM (VALUES
        ('PNM001', 'MH001', 20, CONVERT(DECIMAL(18, 2), 18000)),
        ('PNM001', 'MH004', 15, CONVERT(DECIMAL(18, 2), 16000)),
        ('PNM002', 'MH002', 30, CONVERT(DECIMAL(18, 2), 7000)),
        ('PNM002', 'MH006', 40, CONVERT(DECIMAL(18, 2), 5000)),
        ('PNM002', 'MH009', 20, CONVERT(DECIMAL(18, 2), 12000)),
        ('PNM003', 'MH003', 10, CONVERT(DECIMAL(18, 2), 18000)),
        ('PNM003', 'MH008', 25, CONVERT(DECIMAL(18, 2), 9000)),
        ('PNM003', 'MH010', 15, CONVERT(DECIMAL(18, 2), 30000))
    ) v(MaPhieu, MaMatHang, SoLuong, DonGiaNhap)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.ChiTietNhapHang x
        WHERE x.MaPhieu = v.MaPhieu AND x.MaMatHang = v.MaMatHang
    );

    INSERT INTO dbo.HoaDonBanHang
        (MaHD, NgayLap, TongTien, GiaTriKM, ThanhTien, MaNV, MaPT)
    SELECT v.MaHD, v.NgayLap, 0, 0, 0, v.MaNV, v.MaPT
    FROM (VALUES
        ('HDM001', DATEADD(DAY, -7, CAST(GETDATE() AS DATE)), 'NV002', 'PT001'),
        ('HDM002', DATEADD(DAY, -4, CAST(GETDATE() AS DATE)), 'NV003', 'PT002'),
        ('HDM003', DATEADD(DAY, -2, CAST(GETDATE() AS DATE)), 'NV002', 'PT003'),
        ('HDM004', DATEADD(DAY, -1, CAST(GETDATE() AS DATE)), 'NV004', 'PT001')
    ) v(MaHD, NgayLap, MaNV, MaPT)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.HoaDonBanHang x WHERE x.MaHD = v.MaHD
    );

    INSERT INTO dbo.ChiTietHoaDon
        (MaHD, MaMatHang, SoLuong, DonGia, GiaTriKM)
    SELECT v.MaHD, v.MaMatHang, v.SoLuong, v.DonGia, v.GiaTriKM
    FROM (VALUES
        ('HDM001', 'MH001', 2, CONVERT(DECIMAL(18, 2), 22000), CONVERT(DECIMAL(18, 2), 0)),
        ('HDM001', 'MH002', 3, CONVERT(DECIMAL(18, 2), 10000), CONVERT(DECIMAL(18, 2), 3000)),
        ('HDM002', 'MH006', 5, CONVERT(DECIMAL(18, 2), 7000), CONVERT(DECIMAL(18, 2), 0)),
        ('HDM002', 'MH007', 2, CONVERT(DECIMAL(18, 2), 45000), CONVERT(DECIMAL(18, 2), 4500)),
        ('HDM003', 'MH009', 4, CONVERT(DECIMAL(18, 2), 18000), CONVERT(DECIMAL(18, 2), 10000)),
        ('HDM003', 'MH003', 1, CONVERT(DECIMAL(18, 2), 25000), CONVERT(DECIMAL(18, 2), 5000)),
        ('HDM004', 'MH005', 10, CONVERT(DECIMAL(18, 2), 4500), CONVERT(DECIMAL(18, 2), 0)),
        ('HDM004', 'MH008', 3, CONVERT(DECIMAL(18, 2), 15000), CONVERT(DECIMAL(18, 2), 0))
    ) v(MaHD, MaMatHang, SoLuong, DonGia, GiaTriKM)
    WHERE NOT EXISTS (
        SELECT 1 FROM dbo.ChiTietHoaDon x
        WHERE x.MaHD = v.MaHD AND x.MaMatHang = v.MaMatHang
    );

    -- Recalculate totals even when the database triggers were disabled earlier.
    UPDATE pn
    SET TongTien = x.TongTien
    FROM dbo.PhieuNhapHang pn
    INNER JOIN (
        SELECT MaPhieu, SUM(ThanhTien) AS TongTien
        FROM dbo.ChiTietNhapHang
        GROUP BY MaPhieu
    ) x ON x.MaPhieu = pn.MaPhieu
    WHERE pn.MaPhieu IN ('PNM001', 'PNM002', 'PNM003');

    UPDATE hd
    SET TongTien = x.TongTien,
        GiaTriKM = x.GiaTriKM,
        ThanhTien = x.ThanhTien
    FROM dbo.HoaDonBanHang hd
    INNER JOIN (
        SELECT MaHD,
               SUM(ThanhTien) AS TongTien,
               SUM(GiaTriKM) AS GiaTriKM,
               SUM(ThanhTienSauKM) AS ThanhTien
        FROM dbo.ChiTietHoaDon
        GROUP BY MaHD
    ) x ON x.MaHD = hd.MaHD
    WHERE hd.MaHD IN ('HDM001', 'HDM002', 'HDM003', 'HDM004');

    COMMIT TRANSACTION;

    SELECT N'Da tao du lieu mau thanh cong' AS KetQua;
    SELECT COUNT(*) AS SoMatHang FROM dbo.MatHang;
    SELECT COUNT(*) AS SoHoaDon FROM dbo.HoaDonBanHang;
    SELECT COUNT(*) AS SoPhieuNhap FROM dbo.PhieuNhapHang;
END TRY
BEGIN CATCH
    IF @@TRANCOUNT > 0
        ROLLBACK TRANSACTION;

    THROW;
END CATCH;
GO
