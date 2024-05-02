Create database QLPK
go
use  QLPK
go
CREATE TABLE DSKB (
  DSKB_ID varchar(50) PRIMARY KEY,
  STT int,
  NgayKham datetime NOT NULL,
  HoTen nvarchar(50) NOT NULL,
  CCCD varchar(20) NOT NULL,
  GioiTinh int NOT NULL,
  NamSinh int NOT NULL,
  DiaChi varchar(255),
  CoPKB INT
);

CREATE TABLE TaiKhoan (
  username varchar(50) PRIMARY KEY,
  mk varchar(255) NOT NULL,
  ChucVu nvarchar(50) NOT NULL,
  HoTen nvarchar(50) NOT NULL,
  Email varchar(100)
);
CREATE TABLE CachDung (
  CachDung_ID varchar(50) PRIMARY KEY,
  TenCachDung nvarchar(50) NOT NULL
);
CREATE TABLE DonViThuoc (
  DVTHuoc_ID varchar(50) PRIMARY KEY,
   TenDVTHuoc nvarchar(50)
  );
  CREATE TABLE LoaiBenh (
  LoaiBenh_ID varchar(50) PRIMARY KEY,
   TenBenh nvarchar(50)
  );
CREATE TABLE PKB (
  PKB_ID varchar(50) PRIMARY KEY,
  LoaiBenh_ID varchar(50) NOT NULL,
  TrieuChung nvarchar(255),
  NguoiKham varchar(50) NOT NULL,
  DSKB_ID varchar(50) NOT NULL,
  STT int,

  FOREIGN KEY (LoaiBenh_ID) REFERENCES LoaiBenh(LoaiBenh_ID),
  FOREIGN KEY (NguoiKham) REFERENCES TaiKhoan(username),
  FOREIGN KEY (DSKB_ID) REFERENCES DSKB(DSKB_ID)
);
CREATE TABLE Thuoc (
  Thuoc_ID varchar(50) PRIMARY KEY,
  TenThuoc varchar(50) NOT NULL,
  GiaMua money NOT NULL,
  GiaBan money NOT NULL,
  TonKho int NOT NULL,
  CachDung_ID varchar(50),
  DonViThuoc_ID varchar(50),

  FOREIGN KEY (CachDung_ID) REFERENCES CachDung(CachDung_ID),
  FOREIGN KEY (DonViThuoc_ID) REFERENCES DonViThuoc(DVTHuoc_ID)
);
CREATE TABLE DSTHuoc_PKB (
  PKB_ID varchar(50) NOT NULL,
  Thuoc_ID varchar(50) NOT NULL,
  SoLuong int NOT NULL,

  PRIMARY KEY (PKB_ID, Thuoc_ID),

  FOREIGN KEY (PKB_ID) REFERENCES PKB(PKB_ID),
  FOREIGN KEY (Thuoc_ID) REFERENCES Thuoc(Thuoc_ID)
);
CREATE TABLE HD (
  HD_ID varchar(50) PRIMARY KEY,
  PKB_ID varchar(50) NOT NULL,
  TienKham money NOT NULL,
  TienThuoc money NOT NULL,

  FOREIGN KEY (PKB_ID) REFERENCES PKB(PKB_ID)
);
CREATE TABLE DoanhThu (
  Ngay int NOT NULL,
  Thang int NOT NULL,
  SoBenhNhan int NOT NULL,
  DoanhThu money NOT NULL,

  PRIMARY KEY (Ngay, Thang)
);

CREATE TABLE BCT (
  Thang int NOT NULL,
  Nam int NOT NULL,
  Thuoc_ID varchar(50) NOT NULL,
  SoLuong int NOT NULL,
  SoLanDung int NOT NULL,

  PRIMARY KEY (Thang,Nam,Thuoc_ID),

  FOREIGN KEY (Thuoc_ID) REFERENCES Thuoc(Thuoc_ID)
);

CREATE TABLE ThongTinPK (
IdTT varchar(10) NOT NULL,
TenTT varchar(50) not null,
Gtri int not null,
  PRIMARY KEY (IdTT)
  );
  GO

INSERT INTO TaiKhoan (username,mk,ChucVu,HoTen,Email)
VALUES ('hung123','hung123','Quản lý','Nguyễn Duy Hưng','hung07092004@gmail.com'),
('duy123','duy123','Nhân viên','Phạm Hoàng Duy','duy07092004@gmail.com'),
('quyen123','quyen123','Nhân viên','Trần Hồng Quyền','quyen07092004@gmail.com')
 GO

 INSERT INTO DSKB (DSKB_ID, STT,NgayKham,HoTen,CCCD,GioiTinh,NamSinh,DiaChi,CoPKB)
 VALUES 
 ('KB001',1,'2024-04-20','Phạm Khải Hưng','084204011380',1,2004,'Hồ Chí Minh',1),
('KB002',1,'2024-04-20','Trần Hồng Quyền','084204011381',1,2004,'Đồng Nai',0),
('KB003',1,'2024-04-20','Phạm Hoàng Duy','084204011382',1,2003,'Hồ Chí Minh',1),
('KB004',1,'2024-04-20','Ngô Duy Hưng','084204011280',1,2002,'Hồ Chí Minh',0),
('KB005',1,'2024-04-20','Bùi Thái Hoàng','084204021380',1,2000,'Đồng Nai',0),
('KB006',1,'2024-04-20','Cao Văn Hoàng','084204011384',1,2007,'Bình Dương',1),
('KB007',1,'2024-04-20','Mai Hoàng Hưng','084224011385',1,1987,'Hồ Chí Minh',0),
('KB008',1,'2024-04-20','Lê Huy Hoàng','084204011387',1,1999,'Bình Dương',0),
('KB009',1,'2024-04-20','Nguyễn Thanh Tùng','084204011386',1,2004,'Hồ Chí Minh',1),
('KB010',1,'2024-04-20','Vũ Đinh Trọng Thắng','084204011787',1,2004,'Bình Dương',0),
('KB011',1,'2024-04-20','Hà Anh Tuấn','084204017380',1,1980,'Bình Dương',1),
('KB012',1,'2024-04-20','Phan Thị Mỹ Tâm','084204011370',0,1997,'Hồ Chí Minh',1),
('KB013',1,'2024-04-21','Trịnh Trần Phương Tuấn','084704011380',1,1998,'Đồng Nai',0),
('KB014',1,'2024-04-21','Mai Hồng Ngọc','084204011780',0,2002,'Hồ Chí Minh',1),
('KB015',1,'2024-04-21','Bích Lệ Ái Liên','084204071380',0,2002,'Đồng Nai',0),
('KB016',1,'2024-04-21','Bùi Anh Tuấn','084204011370',1,2001,'Đồng Nai',0),
('KB017',1,'2024-04-20','Nguyễn Duy Hưng','084204011380',1,2004,'Trà Vinh',1)

GO
INSERT INTO DonViThuoc (DVTHuoc_ID,	TenDVTHuoc)
VALUES ('DV01','Viên'),
('DV02','Chai')

GO
INSERT INTO CachDung (CachDung_ID,TenCachDung)
VALUES ('CD01','Uống'),
('CD02','Bôi da'),
('CD03','Ngậm'),
('CD04','Hít')

GO
INSERT INTO LoaiBenh(LoaiBenh_ID,TenBenh)
VALUES ('LB01','Bệnh mạch vành'),
('LB02','Suy tim'),
('LB03','Rối loạn nhịp tim'),
('LB04','Bệnh van tim'),
('LB05','Cao huyết áp')

GO
INSERT INTO Thuoc(Thuoc_ID,TenThuoc,GiaMua,GiaBan,TonKho,CachDung_ID,DonViThuoc_ID)
VALUES ('T001','Accupril', 20000,23000,100,'CD01','DV02'),
 ('T002','Coversyl', 25000,27000,130,'CD02','DV01'),
 ('T003','Adalat', 26000,29000,140,'CD01','DV02'),
 ('T004','Altace', 23000,29000,170,'CD03','DV01'),
 ('T005','Apresoline', 22000,29000,107,'CD04','DV02'),
 ('T006','Aspirin', 24000,29000,180,'CD01','DV01'),
('T007','Benicar', 30000,33000,100,'CD01','DV02'),
 ('T008','Brilinta', 40000,43000,140,'CD02','DV01'),
 ('T009','Caduet', 24000,27000,100,'CD01','DV02'),
('T010','Capoten', 40000,43000,120,'CD01','DV02'),
('T011','Coreg', 24000,28000,100,'CD03','DV01'),
('T012','Cozaar', 25000,29000,110,'CD01','DV02'),
 ('T013','Dilatrate-SR', 20000,23000,100,'CD01','DV02'),
 ('T014','Diovan', 20000,23000,102,'CD01','DV01'),
 ('T015','Effient', 20000,23000,100,'CD02','DV02'),
('T016','Heparin', 20000,23000,107,'CD01','DV02'),
('T017','Imdur', 23000,29000,100,'CD01','DV01'),
 ('T018','Inspra', 20000,23000,100,'CD01','DV02'),
 ('T019','IsoDitrate', 20000,23000,120,'CD03','DV02'),
 ('T020','Isordil', 20000,23000,100,'CD01','DV02'),
 ('T021','Isotrate', 20000,23000,100,'CD01','DV01'),
 ('T022','Lopressor', 20000,23000,100,'CD02','DV02'),
 ('T023','Lotensin', 20000,23000,100,'CD01','DV02'),
 ('T024','Lotrel', 20000,23000,100,'CD01','DV02'),
 ('T025','Lovenox', 20000,23000,100,'CD02','DV01'),
 ('T026','Mavik', 20000,23000,100,'CD01','DV02'),
 ('T027','Monopril', 20000,23000,100,'CD01','DV02'),
 ('T028','Nitroglycerin', 20000,23000,100,'CD01','DV01'),
 ('T029','Norvasc', 20000,23000,100,'CD01','DV02'),
 ('T030','Plavix', 20000,23000,100,'CD01','DV02')

 GO
 INSERT INTO PKB(PKB_ID,LoaiBenh_ID,TrieuChung,NguoiKham,DSKB_ID,STT)
 VALUES 
 ('PKB001','LB01','Đau thắt ngực','quyen123','KB001',1),
 ('PKB002','LB03','Đau tức vùng ngực','duy123','KB003',2),
 ('PKB003','LB04','Tim đập nhanh, đánh trống ngực','quyen123','KB006',3),
 ('PKB004','LB02','Khó thở cấp tính','duy123','KB009',4),
 ('PKB005','LB01','Đau thắt ngực','quyen123','KB011',5),
 ('PKB006','LB05','Nhức đầu, hoa mắt, chóng mặt, ù tai','quyen123','KB012',6),
 ('PKB007','LB01','Đau thắt ngực','duy123','KB014',7),
 ('PKB008','LB02','Khó thở cấp tính','quyen123','KB017',8)

 GO 
 INSERT INTO DSTHuoc_PKB (PKB_ID,Thuoc_ID,SoLuong)
 VALUES ('PKB001','T001', 3),
 ('PKB001','T003', 4),
 ('PKB002','T001', 3),
 ('PKB002','T002', 2),
 ('PKB002','T003', 3),
 ('PKB002','T004', 4),
 ('PKB003','T001', 5),
 ('PKB003','T004', 2),
 ('PKB003','T002', 3),
 ('PKB003','T006', 4),
 ('PKB004','T001', 3),
 ('PKB004','T004', 3),
 ('PKB005','T002', 3),
 ('PKB005','T005', 2),
 ('PKB005','T006', 3),
 ('PKB005','T008', 3),
 ('PKB006','T002', 5),
 ('PKB006','T004', 3),
 ('PKB007','T001', 6),
 ('PKB007','T002', 3),
 ('PKB008','T001', 6),
 ('PKB008','T002', 3),
 ('PKB008','T004', 2),
 ('PKB008','T009', 2)

 GO
 INSERT INTO HD (HD_ID,PKB_ID,TienKham,TienThuoc)
 VALUES ('HD001','PKB001',30000,250000),
 ('HD002','PKB002',30000,240000),
 ('HD003','PKB003',30000,250000),
 ('HD004','PKB004',30000,260000),
 ('HD005','PKB005',30000,280000),
 ('HD006','PKB006',30000,250000),
 ('HD007','PKB007',30000,290000),
 ('HD008','PKB008',30000,350000)

 GO 
 INSERT INTO DoanhThu (Ngay,Thang,SoBenhNhan,DoanhThu)
 VALUES (1,3,50,15000000),
 (2,3,50,15000000),
 (3,3,50,16000000),
 (4,3,50,15000000),
 (5,3,50,19000000),
 (6,3,50,25000000),
 (8,3,50,20000000),
 (9,3,50,15000000),
 (10,3,50,14000000),
 (11,3,50,16000000),
 (12,3,50,18000000),
 (13,3,50,15000000),
 (14,3,50,17000000),
 (15,3,50,19000000),
 (17,3,50,15000000),
 (18,3,50,16000000),
 (19,3,50,14000000),
 (20,3,50,15000000),
 (21,3,50,14000000),
 (22,3,50,13000000),
 (24,3,50,15000000),
 (25,3,50,18000000),
 (26,3,50,10000000),
 (27,3,50,16000000),
 (28,3,50,15000000),
 (29,3,50,16000000),
 (30,3,50,18000000),
 (1,4,50,10000000),
 (2,4,50,15000000),
 (3,4,50,16000000),
 (4,4,50,18000000),
 (5,4,50,19000000),
 (6,4,50,25000000),
 (7,4,50,19000000)

 GO
 INSERT INTO BCT (Thang,Nam,Thuoc_ID,SoLuong,SoLanDung)
 VALUES (3,2024,'T001',300,200),
 (3,2024,'T002',300,220),
 (3,2024,'T003',320,230),
 (3,2024,'T004',310,200),
 (3,2024,'T005',320,220),
 (3,2024,'T006',310,200),
 (3,2024,'T007',320,200),
 (3,2024,'T008',310,240),
 (3,2024,'T009',320,200),
 (3,2024,'T010',310,230),
 (3,2024,'T011',330,220),
 (3,2024,'T012',310,200),
 (3,2024,'T013',300,200),
 (3,2024,'T014',310,280),
 (3,2024,'T015',300,260),
 (4,2024,'T001',320,230),
 (4,2024,'T002',300,200),
 (4,2024,'T003',320,230),
 (4,2024,'T004',300,240),
 (4,2024,'T005',320,200),
 (4,2024,'T006',300,260),
 (4,2024,'T007',330,260),
 (4,2024,'T008',310,240)

 GO
 INSERT INTO ThongTinPK(IdTT,TenTT,Gtri)
 VALUES ('TT01','Tiền khám', 30000),
 ('TT02','Bệnh nhân tối đa', 40)
