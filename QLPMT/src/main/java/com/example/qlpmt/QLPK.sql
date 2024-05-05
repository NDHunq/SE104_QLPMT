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
  DiaChi nvarchar(255),
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
  FOREIGN KEY (Thuoc_ID) REFERENCES Thuoc(Thuoc_ID)
);

CREATE TABLE DoanhThu (
  NgayDT datetime not null,
  SoBenhNhan int NOT NULL,
  DoanhThu money NOT NULL,

  PRIMARY KEY (NgayDT)
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
VALUES ('hung123','hung123',N'Quản lý',N'Nguyễn Duy Hưng','hung07092004@gmail.com'),
('duy123','duy123',N'Nhân viên',N'Phạm Hoàng Duy','duy07092004@gmail.com'),
('quyen123','quyen123',N'Nhân viên',N'Trần Hồng Quyền','quyen07092004@gmail.com')
 GO

 INSERT INTO DSKB (DSKB_ID, STT,NgayKham,HoTen,CCCD,GioiTinh,NamSinh,DiaChi,CoPKB)
 VALUES 
 ('KB001',1,'2024-04-20',N'Phạm Khải Hưng','084204011380',1,2004,N'Hồ Chí Minh',1),
('KB002',2,'2024-04-20',N'Trần Hồng Quyền','084204011381',1,2004,N'Đồng Nai',0),
('KB003',3,'2024-04-20',N'Phạm Hoàng Duy','084204011382',1,2003,N'Hồ Chí Minh',1),
('KB004',4,'2024-04-20',N'Ngô Duy Hưng','084204011280',1,2002,N'Hồ Chí Minh',0),
('KB005',5,'2024-04-20',N'Bùi Thái Hoàng','084204021380',1,2000,N'Đồng Nai',0),
('KB006',6,'2024-04-20',N'Cao Văn Hoàng','084204011384',1,2007,N'Bình Dương',1),
('KB007',7,'2024-04-20',N'Mai Hoàng Hưng','084224011385',1,1987,N'Hồ Chí Minh',0),
('KB008',8,'2024-04-20',N'Lê Huy Hoàng','084204011387',1,1999,N'Bình Dương',0),
('KB009',9,'2024-04-20',N'Nguyễn Thanh Tùng','084204011386',1,2004,N'Hồ Chí Minh',1),
('KB010',10,'2024-04-20',N'Vũ Đinh Trọng Thắng','084204011787',1,2004,N'Bình Dương',0),
('KB011',11,'2024-04-20',N'Hà Anh Tuấn','084204017380',1,1980,N'Bình Dương',1),
('KB012',12,'2024-04-20',N'Phan Thị Mỹ Tâm','084204011370',0,1997,N'Hồ Chí Minh',1),
('KB013',1,'2024-04-21',N'Trịnh Trần Phương Tuấn','084704011380',1,1998,N'Đồng Nai',0),
('KB014',2,'2024-04-21',N'Mai Hồng Ngọc','084204011780',0,2002,N'Hồ Chí Minh',1),
('KB015',3,'2024-04-21',N'Bích Lệ Ái Liên','084204071380',0,2002,N'Đồng Nai',0),
('KB016',4,'2024-04-21',N'Bùi Anh Tuấn','084204011370',1,2001,N'Đồng Nai',0),
('KB017',5,'2024-04-21',N'Nguyễn Duy Hưng','084204011380',1,2004,N'Trà Vinh',1)

GO
INSERT INTO DonViThuoc (DVTHuoc_ID,	TenDVTHuoc)
VALUES ('DV01',N'Viên'),
('DV02',N'Chai')

GO
INSERT INTO CachDung (CachDung_ID,TenCachDung)
VALUES ('CD01',N'Uống'),
('CD02',N'Bôi da'),
('CD03',N'Ngậm'),
('CD04',N'Hít')

GO
INSERT INTO LoaiBenh(LoaiBenh_ID,TenBenh)
VALUES ('LB01',N'Bệnh mạch vành'),
('LB02',N'Suy tim'),
('LB03',N'Rối loạn nhịp tim'),
('LB04',N'Bệnh van tim'),
('LB05',N'Cao huyết áp')

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
 ('PKB001','LB01',N'Đau thắt ngực','quyen123','KB001',1),
 ('PKB002','LB03',N'Đau tức vùng ngực','duy123','KB003',2),
 ('PKB003','LB04',N'Tim đập nhanh, đánh trống ngực','quyen123','KB006',3),
 ('PKB004','LB02',N'Khó thở cấp tính','duy123','KB009',4),
 ('PKB005','LB01',N'Đau thắt ngực','quyen123','KB011',5),
 ('PKB006','LB05',N'Nhức đầu, hoa mắt, chóng mặt, ù tai','quyen123','KB012',6),
 ('PKB007','LB01',N'Đau thắt ngực','duy123','KB014',7),
 ('PKB008','LB02',N'Khó thở cấp tính','quyen123','KB017',8)

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
 INSERT INTO DoanhThu (NgayDT,SoBenhNhan,DoanhThu)
 VALUES ('2024-3-1',50,15000000),
 ('2024-3-2',50,15000000),
 ('2024-3-3',50,16000000),
 ('2024-3-4',50,15000000),
 ('2024-3-5',50,19000000),
 ('2024-3-6',50,25000000),
 ('2024-3-8',50,20000000),
 ('2024-3-9',50,15000000),
 ('2024-3-10',50,14000000),
 ('2024-3-11',50,16000000),
 ('2024-3-12',50,18000000),
 ('2024-3-13',50,15000000),
 ('2024-3-15',50,17000000),
 ('2024-3-16',50,19000000),
 ('2024-3-17',50,15000000),
 ('2024-3-18',50,16000000),
 ('2024-3-19',50,14000000),
 ('2024-3-20',50,15000000),
 ('2024-3-22',50,14000000),
 ('2024-3-23',50,13000000),
 ('2024-3-24',50,15000000),
 ('2024-3-25',50,18000000),
 ('2024-3-26',50,10000000),
 ('2024-3-27',50,16000000),
 ('2024-3-29',50,15000000),
 ('2024-3-30',50,16000000),
 ('2024-3-31',50,18000000),
 ('2024-4-1',50,10000000),
 ('2024-4-2',50,15000000),
 ('2024-4-3',50,16000000),
 ('2024-4-5',50,18000000),
 ('2024-4-6',50,19000000),
 ('2024-4-7',50,25000000),
 ('2024-4-8',50,19000000)

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
 VALUES ('TT01',N'Tiền khám', 30000),
 ('TT02',N'Bệnh nhân tối đa', 40)
