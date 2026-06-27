IF DB_ID('QuanLyTapHoa') IS NULL
BEGIN
    CREATE DATABASE QuanLyTapHoa;
END
GO

USE QuanLyTapHoa;
GO

IF OBJECT_ID('dbo.ChiTietHoaDon', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.ChiTietHoaDon
    (
        MaHD VARCHAR(20) NOT NULL,
        MaMatHang VARCHAR(20) NOT NULL,
        SoLuong INT NOT NULL,
        DonGia DECIMAL(18, 2) NOT NULL,
        GiaTriKM DECIMAL(18, 2) NOT NULL DEFAULT 0,
        ThanhTien AS (CONVERT(DECIMAL(18, 2), SoLuong * DonGia)) PERSISTED,
        ThanhTienSauKM AS (CONVERT(DECIMAL(18, 2), (SoLuong * DonGia) - GiaTriKM)) PERSISTED,
        CONSTRAINT PK_ChiTietHoaDon PRIMARY KEY (MaHD, MaMatHang),
        CONSTRAINT CK_ChiTietHoaDon_SoLuong CHECK (SoLuong > 0),
        CONSTRAINT CK_ChiTietHoaDon_DonGia CHECK (DonGia >= 0),
        CONSTRAINT CK_ChiTietHoaDon_GiaTriKM CHECK (GiaTriKM >= 0)
    );
END
GO

IF OBJECT_ID('dbo.HoaDonBanHang', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.HoaDonBanHang
    (
        MaHD VARCHAR(20) NOT NULL PRIMARY KEY,
        NgayLap DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
        TongTien DECIMAL(18, 2) NOT NULL DEFAULT 0,
        GiaTriKM DECIMAL(18, 2) NOT NULL DEFAULT 0,
        ThanhTien DECIMAL(18, 2) NOT NULL DEFAULT 0,
        MaNV VARCHAR(20) NOT NULL,
        MaPT VARCHAR(20) NOT NULL
    );
END
GO

IF OBJECT_ID('dbo.ChiTietNhapHang', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.ChiTietNhapHang
    (
        MaPhieu VARCHAR(20) NOT NULL,
        MaMatHang VARCHAR(20) NOT NULL,
        SoLuong INT NOT NULL,
        DonGiaNhap DECIMAL(18, 2) NOT NULL,
        ThanhTien AS (CONVERT(DECIMAL(18, 2), SoLuong * DonGiaNhap)) PERSISTED,
        CONSTRAINT PK_ChiTietNhapHang PRIMARY KEY (MaPhieu, MaMatHang),
        CONSTRAINT CK_ChiTietNhapHang_SoLuong CHECK (SoLuong > 0),
        CONSTRAINT CK_ChiTietNhapHang_DonGiaNhap CHECK (DonGiaNhap >= 0)
    );
END
GO

IF OBJECT_ID('dbo.PhieuNhapHang', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.PhieuNhapHang
    (
        MaPhieu VARCHAR(20) NOT NULL PRIMARY KEY,
        NgayNhap DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
        TongTien DECIMAL(18, 2) NOT NULL DEFAULT 0,
        MaNCC VARCHAR(20) NOT NULL,
        MaNV VARCHAR(20) NOT NULL
    );
END
GO

IF OBJECT_ID('dbo.TaiKhoan', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.TaiKhoan
    (
        TenDangNhap VARCHAR(50) NOT NULL PRIMARY KEY,
        MatKhau VARCHAR(255) NOT NULL,
        VaiTro VARCHAR(20) NOT NULL,
        MaNV VARCHAR(20) NOT NULL,
        TrangThai BIT NOT NULL DEFAULT 1,
        CONSTRAINT CK_TaiKhoan_VaiTro CHECK (VaiTro IN ('ADMIN', 'NHAN_VIEN'))
    );
END
GO

IF OBJECT_ID('dbo.MatHang', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.MatHang
    (
        MaMatHang VARCHAR(20) NOT NULL PRIMARY KEY,
        TenMatHang NVARCHAR(200) NOT NULL,
        MaLoai VARCHAR(20) NULL,
        MaKM VARCHAR(20) NULL,
        DonViTinh NVARCHAR(20) NULL,
        GiaNhap DECIMAL(18, 2) NOT NULL,
        GiaBan DECIMAL(18, 2) NOT NULL,
        SoLuong INT NOT NULL DEFAULT 0,
        TrangThai BIT NOT NULL DEFAULT 1,
        CONSTRAINT CK_MatHang_GiaNhap CHECK (GiaNhap >= 0),
        CONSTRAINT CK_MatHang_GiaBan CHECK (GiaBan >= 0),
        CONSTRAINT CK_MatHang_SoLuong CHECK (SoLuong >= 0)
    );
END
GO

IF OBJECT_ID('dbo.PhuongThucThanhToan', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.PhuongThucThanhToan
    (
        MaPT VARCHAR(20) NOT NULL PRIMARY KEY,
        TenPT NVARCHAR(100) NOT NULL,
        MoTa NVARCHAR(255) NULL,
        TrangThai BIT NOT NULL DEFAULT 1
    );
END
GO

IF OBJECT_ID('dbo.NhanVien', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.NhanVien
    (
        MaNV VARCHAR(20) NOT NULL PRIMARY KEY,
        HoTen NVARCHAR(100) NOT NULL,
        SoDienThoai VARCHAR(20) NULL,
        DiaChi NVARCHAR(255) NULL,
        ChucVu NVARCHAR(50) NULL,
        NgayVaoLam DATE NULL,
        TrangThai BIT NOT NULL DEFAULT 1
    );
END
GO

IF OBJECT_ID('dbo.NhaCungCap', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.NhaCungCap
    (
        MaNCC VARCHAR(20) NOT NULL PRIMARY KEY,
        TenNCC NVARCHAR(200) NOT NULL,
        DiaChi NVARCHAR(255) NULL,
        SoDienThoai VARCHAR(20) NULL,
        Email VARCHAR(100) NULL
    );
END
GO

IF OBJECT_ID('dbo.KhuyenMai', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.KhuyenMai
    (
        MaKM VARCHAR(20) NOT NULL PRIMARY KEY,
        TenKM NVARCHAR(100) NOT NULL,
        LoaiKM NVARCHAR(50) NOT NULL,
        GiaTriKM NVARCHAR(50) NOT NULL,
        NgayBatDau DATE NULL,
        NgayKetThuc DATE NULL,
        TrangThai BIT NOT NULL DEFAULT 1,
        CONSTRAINT CK_KhuyenMai_Ngay CHECK
        (
            NgayBatDau IS NULL
            OR NgayKetThuc IS NULL
            OR NgayKetThuc >= NgayBatDau
        )
    );
END
GO

IF OBJECT_ID('dbo.LoaiMatHang', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.LoaiMatHang
    (
        MaLoai VARCHAR(20) NOT NULL PRIMARY KEY,
        TenLoai NVARCHAR(100) NOT NULL,
        TrangThai BIT NOT NULL DEFAULT 1
    );
END
GO


IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_MatHang_LoaiMatHang')
BEGIN
    ALTER TABLE dbo.MatHang
    ADD CONSTRAINT FK_MatHang_LoaiMatHang
        FOREIGN KEY (MaLoai) REFERENCES dbo.LoaiMatHang(MaLoai);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_MatHang_KhuyenMai')
BEGIN
    ALTER TABLE dbo.MatHang
    ADD CONSTRAINT FK_MatHang_KhuyenMai
        FOREIGN KEY (MaKM) REFERENCES dbo.KhuyenMai(MaKM);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_TaiKhoan_NhanVien')
BEGIN
    ALTER TABLE dbo.TaiKhoan
    ADD CONSTRAINT FK_TaiKhoan_NhanVien
        FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien(MaNV);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_PhieuNhapHang_NhaCungCap')
BEGIN
    ALTER TABLE dbo.PhieuNhapHang
    ADD CONSTRAINT FK_PhieuNhapHang_NhaCungCap
        FOREIGN KEY (MaNCC) REFERENCES dbo.NhaCungCap(MaNCC);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_PhieuNhapHang_NhanVien')
BEGIN
    ALTER TABLE dbo.PhieuNhapHang
    ADD CONSTRAINT FK_PhieuNhapHang_NhanVien
        FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien(MaNV);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_ChiTietNhapHang_PhieuNhapHang')
BEGIN
    ALTER TABLE dbo.ChiTietNhapHang
    ADD CONSTRAINT FK_ChiTietNhapHang_PhieuNhapHang
        FOREIGN KEY (MaPhieu) REFERENCES dbo.PhieuNhapHang(MaPhieu);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_ChiTietNhapHang_MatHang')
BEGIN
    ALTER TABLE dbo.ChiTietNhapHang
    ADD CONSTRAINT FK_ChiTietNhapHang_MatHang
        FOREIGN KEY (MaMatHang) REFERENCES dbo.MatHang(MaMatHang);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_HoaDonBanHang_NhanVien')
BEGIN
    ALTER TABLE dbo.HoaDonBanHang
    ADD CONSTRAINT FK_HoaDonBanHang_NhanVien
        FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien(MaNV);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_HoaDonBanHang_PhuongThucThanhToan')
BEGIN
    ALTER TABLE dbo.HoaDonBanHang
    ADD CONSTRAINT FK_HoaDonBanHang_PhuongThucThanhToan
        FOREIGN KEY (MaPT) REFERENCES dbo.PhuongThucThanhToan(MaPT);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_ChiTietHoaDon_HoaDonBanHang')
BEGIN
    ALTER TABLE dbo.ChiTietHoaDon
    ADD CONSTRAINT FK_ChiTietHoaDon_HoaDonBanHang
        FOREIGN KEY (MaHD) REFERENCES dbo.HoaDonBanHang(MaHD);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_ChiTietHoaDon_MatHang')
BEGIN
    ALTER TABLE dbo.ChiTietHoaDon
    ADD CONSTRAINT FK_ChiTietHoaDon_MatHang
        FOREIGN KEY (MaMatHang) REFERENCES dbo.MatHang(MaMatHang);
END
GO

IF OBJECT_ID('dbo.seq_HoaDon', 'SO') IS NULL
BEGIN
    EXEC('CREATE SEQUENCE dbo.seq_HoaDon START WITH 1 INCREMENT BY 1');
END
GO

IF OBJECT_ID('dbo.seq_PhieuNhap', 'SO') IS NULL
BEGIN
    EXEC('CREATE SEQUENCE dbo.seq_PhieuNhap START WITH 1 INCREMENT BY 1');
END
GO

CREATE OR ALTER PROCEDURE dbo.sp_TaoHoaDon
    @MaNV VARCHAR(20),
    @MaPT VARCHAR(20),
    @MaHD VARCHAR(20) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    SET @MaHD = 'HD' + RIGHT('000000' + CAST(NEXT VALUE FOR dbo.seq_HoaDon AS VARCHAR(20)), 6);

    INSERT INTO dbo.HoaDonBanHang (MaHD, NgayLap, TongTien, GiaTriKM, ThanhTien, MaNV, MaPT)
    VALUES (@MaHD, CAST(GETDATE() AS DATE), 0, 0, 0, @MaNV, @MaPT);

    SELECT @MaHD AS MaHoaDonMoi;
END
GO

CREATE OR ALTER PROCEDURE dbo.sp_TaoPhieuNhap
    @MaNV VARCHAR(20),
    @MaNCC VARCHAR(20),
    @MaPhieu VARCHAR(20) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    SET @MaPhieu = 'PN' + RIGHT('000000' + CAST(NEXT VALUE FOR dbo.seq_PhieuNhap AS VARCHAR(20)), 6);

    INSERT INTO dbo.PhieuNhapHang (MaPhieu, NgayNhap, TongTien, MaNCC, MaNV)
    VALUES (@MaPhieu, CAST(GETDATE() AS DATE), 0, @MaNCC, @MaNV);

    SELECT @MaPhieu AS MaPhieuMoi;
END
GO

CREATE OR ALTER PROCEDURE dbo.sp_ThemChiTietHoaDon
    @MaHD VARCHAR(20),
    @MaMatHang VARCHAR(20),
    @SoLuong INT,
    @GiaTriKM DECIMAL(18, 2) = 0
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO dbo.ChiTietHoaDon (MaHD, MaMatHang, SoLuong, DonGia, GiaTriKM)
    SELECT @MaHD, MaMatHang, @SoLuong, GiaBan, @GiaTriKM
    FROM dbo.MatHang
    WHERE MaMatHang = @MaMatHang;
END
GO

CREATE OR ALTER PROCEDURE dbo.sp_ThemChiTietNhapHang
    @MaPhieu VARCHAR(20),
    @MaMatHang VARCHAR(20),
    @SoLuong INT,
    @DonGiaNhap DECIMAL(18, 2) = NULL
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO dbo.ChiTietNhapHang (MaPhieu, MaMatHang, SoLuong, DonGiaNhap)
    SELECT @MaPhieu, MaMatHang, @SoLuong, ISNULL(@DonGiaNhap, GiaNhap)
    FROM dbo.MatHang
    WHERE MaMatHang = @MaMatHang;
END
GO

CREATE OR ALTER TRIGGER dbo.trg_ChiTietNhapHang_AfterInsert
ON dbo.ChiTietNhapHang
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE mh
    SET mh.SoLuong = mh.SoLuong + x.SoLuongNhap,
        mh.GiaNhap = x.DonGiaNhapMoi
    FROM dbo.MatHang mh
    INNER JOIN
    (
        SELECT MaMatHang, SUM(SoLuong) AS SoLuongNhap, MAX(DonGiaNhap) AS DonGiaNhapMoi
        FROM inserted
        GROUP BY MaMatHang
    ) x ON mh.MaMatHang = x.MaMatHang;

    UPDATE pn
    SET TongTien = ISNULL(x.TongTien, 0)
    FROM dbo.PhieuNhapHang pn
    LEFT JOIN
    (
        SELECT MaPhieu, SUM(ThanhTien) AS TongTien
        FROM dbo.ChiTietNhapHang
        GROUP BY MaPhieu
    ) x ON pn.MaPhieu = x.MaPhieu
    WHERE pn.MaPhieu IN (SELECT MaPhieu FROM inserted);
END
GO

CREATE OR ALTER TRIGGER dbo.trg_ChiTietHoaDon_InsteadOfInsert
ON dbo.ChiTietHoaDon
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS
    (
        SELECT 1
        FROM inserted i
        INNER JOIN dbo.MatHang mh ON i.MaMatHang = mh.MaMatHang
        WHERE i.SoLuong > mh.SoLuong OR mh.TrangThai = 0
    )
    BEGIN
        RAISERROR('So luong ban vuot qua ton kho hoac mat hang da ngung ban.', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;

    UPDATE mh
    SET mh.SoLuong = mh.SoLuong - x.SoLuongBan
    FROM dbo.MatHang mh
    INNER JOIN
    (
        SELECT MaMatHang, SUM(SoLuong) AS SoLuongBan
        FROM inserted
        GROUP BY MaMatHang
    ) x ON mh.MaMatHang = x.MaMatHang;

    UPDATE hd
    SET TongTien = ISNULL(x.TongTien, 0),
        GiaTriKM = ISNULL(x.TongKM, 0),
        ThanhTien = ISNULL(x.ThanhTien, 0)
    FROM dbo.HoaDonBanHang hd
    LEFT JOIN
    (
        SELECT MaHD,
               SUM(ThanhTien) AS TongTien,
               SUM(GiaTriKM) AS TongKM,
               SUM(ThanhTienSauKM) AS ThanhTien
        FROM dbo.ChiTietHoaDon
        GROUP BY MaHD
    ) x ON hd.MaHD = x.MaHD
    WHERE hd.MaHD IN (SELECT MaHD FROM inserted);
END
GO

IF NOT EXISTS (SELECT 1 FROM dbo.LoaiMatHang WHERE MaLoai = 'L01')
BEGIN
    INSERT INTO dbo.LoaiMatHang (MaLoai, TenLoai, TrangThai)
    VALUES
        ('L01', N'Thuc pham', 1),
        ('L02', N'Do uong', 1),
        ('L03', N'Hoa pham', 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM dbo.KhuyenMai WHERE MaKM = 'KM001')
BEGIN
    INSERT INTO dbo.KhuyenMai (MaKM, TenKM, LoaiKM, GiaTriKM, NgayBatDau, NgayKetThuc, TrangThai)
    VALUES
        ('KM001', N'Giam 10 phan tram', N'phan tram', N'10%', '1970-01-01', NULL, 1),
        ('KM002', N'Giam 5000 dong', N'tien mat', N'5000', '1970-01-01', NULL, 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM dbo.NhaCungCap WHERE MaNCC = 'NCC001')
BEGIN
    INSERT INTO dbo.NhaCungCap (MaNCC, TenNCC, DiaChi, SoDienThoai, Email)
    VALUES
        ('NCC001', N'Nha cung cap tong hop', N'TP HCM', '0280000000', 'ncc@example.com'),
        ('NCC002', N'Dai ly do uong', N'Dong Nai', '0251000000', 'douong@example.com');
END
GO

IF NOT EXISTS (SELECT 1 FROM dbo.NhanVien WHERE MaNV = 'NV001')
BEGIN
    INSERT INTO dbo.NhanVien (MaNV, HoTen, SoDienThoai, DiaChi, ChucVu, NgayVaoLam, TrangThai)
    VALUES
        ('NV001', N'Quan tri vien', '0900000000', N'Cua hang', N'Admin', CAST(GETDATE() AS DATE), 1),
        ('NV002', N'Nhan vien ban hang', '0911111111', N'Cua hang', N'Nhan vien', CAST(GETDATE() AS DATE), 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM dbo.PhuongThucThanhToan WHERE MaPT = 'PT001')
BEGIN
    INSERT INTO dbo.PhuongThucThanhToan (MaPT, TenPT, MoTa, TrangThai)
    VALUES
        ('PT001', N'Tien mat', N'Thanh toan truc tiep', 1),
        ('PT002', N'Chuyen khoan', N'Thanh toan qua ngan hang', 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM dbo.MatHang WHERE MaMatHang = 'MH001')
BEGIN
    INSERT INTO dbo.MatHang
        (MaMatHang, TenMatHang, MaLoai, MaKM, DonViTinh, GiaNhap, GiaBan, SoLuong, TrangThai)
    VALUES
        ('MH001', N'Duong cat', 'L01', NULL, N'kg', 18000, 22000, 100, 1),
        ('MH002', N'Sua hop', 'L02', 'KM001', N'hop', 7000, 10000, 80, 1),
        ('MH003', N'Nuoc rua chen', 'L03', 'KM002', N'chai', 18000, 25000, 40, 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM dbo.TaiKhoan WHERE TenDangNhap = 'admin')
BEGIN
    INSERT INTO dbo.TaiKhoan (TenDangNhap, MatKhau, VaiTro, MaNV, TrangThai)
    VALUES
        ('admin', '123456', 'ADMIN', 'NV001', 1),
        ('nhanvien', '123456', 'NHAN_VIEN', 'NV002', 1);
END
GO
