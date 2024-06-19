CREATE DATABASE Radianite

USE Radianite

-- Master --

CREATE TABLE ms_developer(
	id_developer VARCHAR(10) PRIMARY KEY,
	nama_developer VARCHAR(100),
	status INT
)

INSERT INTO ms_developer VALUES('DVL001','GenG',1)
INSERT INTO ms_developer VALUES('DVL002','Liquid',1)
SELECT * FROM ms_developer

CREATE TABLE ms_perumahan(
	id_perumahan VARCHAR(10) PRIMARY KEY,
	id_developer VARCHAR(10) FOREIGN KEY REFERENCES ms_developer(id_developer),
	nama_perumahan VARCHAR(25),
	status INT
)

INSERT INTO ms_perumahan VALUES('PRM001','DVL001','Emerald',1)
INSERT INTO ms_perumahan VALUES('PRM002','DVL002','Acropolis',1)
SELECT * FROM ms_perumahan

CREATE TABLE ms_tipe_rumah(
	id_tipe VARCHAR(10) PRIMARY KEY,
	nama_tipe VARCHAR(50),
	status INT
)
EXEC sp_inputTipeRumah 'TRM001','Luxury'
SELECT * FROM ms_tipe_rumah
DELETE FROM ms_tipe_rumah

CREATE TABLE ms_role(
	id_role VARCHAR(10) PRIMARY KEY,
	nama_role VARCHAR(100),
	status INT
)

INSERT INTO ms_role VALUES('RLE001','Manager',1)
INSERT INTO ms_role VALUES('RLE002','Agent',1)
SELECT * FROM ms_role

CREATE TABLE ms_user(
	username VARCHAR(20) PRIMARY KEY,
	password VARCHAR(20),
	id_perumahan VARCHAR(10) FOREIGN KEY REFERENCES ms_perumahan(id_perumahan),
	id_role VARCHAR(10) FOREIGN KEY REFERENCES ms_role(id_role),
	nama_lengkap VARCHAR(50),
	email VARCHAR(30),
	alamat VARCHAR(25),
	jenis_kelamin VARCHAR(20),
	umur INT,
	photo VARBINARY(MAX),
	status INT
)


CREATE TABLE ms_rumah(
	id_rumah VARCHAR(10) PRIMARY KEY,
	id_perumahan VARCHAR(10) FOREIGN KEY REFERENCES ms_perumahan(id_perumahan),
	foto_rumah VARBINARY(MAX),
	blok VARCHAR(25),
	daya_listrik INT,
	interior VARCHAR(15),
	jml_kmr_tdr INT,
	jml_kmr_mdn INT,
	id_tipe VARCHAR(10) FOREIGN KEY REFERENCES ms_tipe_rumah(id_tipe),
	descrption VARCHAR(100),
	uang_muka MONEY,
	harga MONEY,
	thn_bangun DATE,
	ketersediaan INT,
	status INT
)

CREATE TABLE ms_ruko(
	id_ruko VARCHAR(10) PRIMARY KEY,
	id_perumahan VARCHAR(10) FOREIGN KEY REFERENCES ms_perumahan(id_perumahan),
	foto_ruko VARBINARY(MAX),
	blok VARCHAR(25),
	daya_listrik INT,
	jml_kmr_mdn INT,
	descrption VARCHAR(100),
	harga_sewa MONEY,
	ketersediaan INT,
	status INT
)

SELECT * FROM ms_ruko
EXEC sp_viewRuko

CREATE TABLE ms_bank(
	id_bank VARCHAR(10) PRIMARY KEY,
	nama_bank VARCHAR(25),
	suku_bunga INT,
	status INT
)


SELECT * FROM ms_bank

-- Transaction --
CREATE TABLE tr_ruko(
	id_trRuko VARCHAR(10) PRIMARY KEY,
	tgl_transaksi DATE,
	id_ruko VARCHAR(10) FOREIGN KEY REFERENCES ms_ruko(id_ruko),
	username VARCHAR(20) FOREIGN KEY REFERENCES ms_user(username),
	NIK VARCHAR(16),
	nama VARCHAR(55),
	telp VARCHAR(13),
	jenis_pembayaran VARCHAR(20),
	id_bank VARCHAR(10) FOREIGN KEY REFERENCES ms_bank(id_bank) NULL,
	rekening VARCHAR(15),
	periode_sewa INT,
	total_pembayaran MONEY,
	dokumen_kontrak VARBINARY(MAX),
	status_kontrak INT,
	tgl_expired DATE
)



CREATE TABLE tr_rumah(
	id_trRumah VARCHAR(10) PRIMARY KEY,
	tgl_transaksi DATE,
	id_rumah VARCHAR(10) FOREIGN KEY REFERENCES ms_rumah(id_rumah),
	username VARCHAR(20) FOREIGN KEY REFERENCES ms_user(username),
	NIK VARCHAR(16),
	nama VARCHAR(55),
	telp VARCHAR(13),
	jenis_pembayaran VARCHAR(20),
	id_bank VARCHAR(10) FOREIGN KEY REFERENCES ms_bank(id_bank) NULL,
	rekening VARCHAR(15),
	suku_bunga INT,
	total_pembayaran MONEY,
	status_pelunasan INT,
	dokumen_pembelian VARBINARY(MAX),
	min_cicilan MONEY,
	periode_cicilan_kpr INT,
	sisa_cicilan MONEY,
	tgl_cicilan DATE,
	tgl_pelunasan DATE
)



CREATE TABLE CicilRumah(
	id_cicil INT IDENTITY,
	id_trRumah VARCHAR(10) FOREIGN KEY REFERENCES tr_rumah(id_trRumah),
	tgl_cicil DATE,
	nominal_cicil MONEY
)

-- Other
CREATE TABLE LoginLog(
	id_log INT IDENTITY PRIMARY KEY,
	username VARCHAR(20) FOREIGN KEY REFERENCES ms_user(username),
	logDate DATETIME
)


--  Stored Procedure -- 

--INSERT
CREATE PROCEDURE sp_inputDeveloper
	@id VARCHAR(10),
	@nama VARCHAR(100)
AS
BEGIN
	INSERT INTO ms_developer VALUES(@id,@nama,1)
END

CREATE PROCEDURE sp_inputPerumahan
	@id VARCHAR(10),
	@idf VARCHAR(10),
	@nama VARCHAR(25)
AS
BEGIN
	INSERT INTO ms_perumahan VALUES(@id,@idf,@nama,1)
END

SELECT * FROM ms_perumahan

CREATE PROCEDURE sp_inputTipeRumah
	@id VARCHAR(10),
	@nama VARCHAR(50)
AS
BEGIN
	INSERT INTO ms_tipe_rumah VALUES(@id,@nama,1)
END

CREATE PROCEDURE sp_inputRole
	@id VARCHAR(10),
	@nama VARCHAR(100)
AS
BEGIN
	INSERT INTO ms_role VALUES(@id,@nama,1)
END

CREATE PROCEDURE sp_inputUser
	@usn VARCHAR(20),
	@pass VARCHAR(20),
	@idp VARCHAR(10),
	@idr VARCHAR(10),
	@name VARCHAR(50),
	@email VARCHAR(30),
	@alamat VARCHAR(25),
	@jenis_kelamin VARCHAR(20),
	@umur INT,
	@photo VARBINARY(MAX)
AS
BEGIN
	INSERT INTO ms_user VALUES(@usn,@pass,@idp,@idr,@name,@email,@alamat,@jenis_kelamin,@umur,@photo,1)
END

CREATE PROCEDURE sp_inputRumah
	@id VARCHAR(10),
	@idp VARCHAR(10),
	@foto VARBINARY(MAX),
	@blok VARCHAR(25),
	@daya INT,
	@interior VARCHAR(15),
	@kmr_tdr INT,
	@kmr_mdn INT,
	@idt VARCHAR(10),
	@desc VARCHAR(100),
	@dp MONEY,
	@harga MONEY,
	@tbangun DATE
AS
BEGIN
	INSERT INTO ms_rumah VALUES(@id,@idp,@foto,@blok,@daya,@interior,@kmr_tdr,@kmr_mdn,@idt,@desc,@dp,@harga,@tbangun,1,1)
END

CREATE PROCEDURE sp_inputRuko
	@id VARCHAR(10),
	@idp VARCHAR(10),
	@foto VARBINARY(MAX),
	@blok VARCHAR(25),
	@listrik INT,
	@kmr_mdn INT,
	@desc VARCHAR(100),
	@harga MONEY
AS
BEGIN
	INSERT INTO ms_ruko VALUES(@id,@idp,@foto,@blok,@listrik,@kmr_mdn,@desc,@harga,1,1)
END


-- UPDATE
CREATE PROCEDURE sp_updateDeveloper
	@id VARCHAR(10),
	@nama VARCHAR(100)
AS
BEGIN
	UPDATE ms_developer SET nama_developer=@nama WHERE id_developer = @id
END

CREATE PROCEDURE sp_updatePerumahan
	@id VARCHAR(10),
	@idf VARCHAR(10),
	@nama VARCHAR(25)
AS
BEGIN
	UPDATE ms_perumahan SET id_developer=@idf, nama_perumahan=@nama WHERE id_perumahan=@id
END

CREATE PROCEDURE sp_updateTipeRumah
	@id VARCHAR(10),
	@nama VARCHAR(50)
AS
BEGIN
	UPDATE ms_tipe_rumah SET nama_tipe=@nama WHERE id_tipe=@id
END

CREATE PROCEDURE sp_updateRole
	@id VARCHAR(10),
	@nama VARCHAR(100)
AS
BEGIN
	UPDATE ms_role SET nama_role=@nama WHERE id_role=@id
END

CREATE PROCEDURE sp_updateUser
	@usn VARCHAR(20),
	@pass VARCHAR(20),
	@idp VARCHAR(10),
	@idr VARCHAR(10),
	@name VARCHAR(50),
	@email VARCHAR(30),
	@alamat VARCHAR(25),
	@jenis_kelamin VARCHAR(20),
	@umur INT,
	@photo VARBINARY(MAX)
AS
BEGIN
	UPDATE ms_user SET password=@pass,id_perumahan=@idp,id_role=@idr,nama_lengkap=@name,email=@email,alamat=@alamat,jenis_kelamin=@jenis_kelamin,umur=@umur,photo=@photo WHERE username=@usn
END

CREATE PROCEDURE sp_updateRumah
	@id VARCHAR(10),
	@idp VARCHAR(10),
	@foto VARBINARY(MAX),
	@blok VARCHAR(25),
	@daya INT,
	@interior VARCHAR(15),
	@kmr_tdr INT,
	@kmr_mdn INT,
	@idt VARCHAR(10),
	@desc VARCHAR(100),
	@dp MONEY,
	@harga MONEY,
	@tbangun DATE
AS
BEGIN
	UPDATE ms_rumah SET id_perumahan=@idp,foto_rumah=@foto,blok=@blok,daya_listrik=@daya,interior=@interior,jml_kmr_tdr=@kmr_tdr,jml_kmr_mdn=@kmr_mdn,id_tipe=@idt,descrption=@desc,uang_muka=@dp,harga=@harga,thn_bangun=@tbangun WHERE id_rumah=@id
END

CREATE PROCEDURE sp_updateRuko
	@id VARCHAR(10),
	@idp VARCHAR(10),
	@foto VARBINARY(MAX),
	@blok VARCHAR(25),
	@listrik INT,
	@kmr_mdn INT,
	@desc VARCHAR(100),
	@harga MONEY
AS
BEGIN
	UPDATE ms_ruko SET id_perumahan=@idp,foto_ruko=@foto,blok=@blok,daya_listrik=@listrik,jml_kmr_mdn=@kmr_mdn,descrption=@desc,harga_sewa=@harga WHERE id_ruko = @id
END

-- DELETE

CREATE PROCEDURE sp_deleteDeveloper
	@id VARCHAR(10)
AS
BEGIN
	UPDATE ms_developer SET status=0 WHERE id_developer = @id
END

CREATE PROCEDURE sp_deletePerumahan
	@id VARCHAR(10)
AS
BEGIN
	UPDATE ms_perumahan SET status=0 WHERE id_perumahan=@id
END

CREATE PROCEDURE sp_deleteTipeRumah
	@id VARCHAR(10)
AS
BEGIN
	UPDATE ms_tipe_rumah SET status=0 WHERE id_tipe=@id
END

CREATE PROCEDURE sp_deleteRole
	@id VARCHAR(10)
AS
BEGIN
	UPDATE ms_role SET status=0 WHERE id_role=@id
END

CREATE PROCEDURE sp_deleteUser
	@usn VARCHAR(20)
AS
BEGIN
	UPDATE ms_user SET status=0 WHERE username=@usn
END

CREATE PROCEDURE sp_deleteRumah
	@id VARCHAR(10)
AS
BEGIN
	UPDATE ms_rumah SET status=0 WHERE id_rumah=@id
END

CREATE PROCEDURE sp_deleteRuko
	@id VARCHAR(10)
AS
BEGIN
	UPDATE ms_ruko SET status=0 WHERE id_ruko = @id
END

CREATE PROCEDURE sp_inputBank
	@id VARCHAR(10),
	@nama VARCHAR(25),
	@bunga INT
AS
BEGIN
	INSERT INTO ms_bank VALUES(@id,@nama,@bunga,1)
END

CREATE PROCEDURE sp_updateBank
	@id VARCHAR(10),
	@nama VARCHAR(25),
	@bunga INT
AS
BEGIN
	UPDATE ms_bank SET nama_bank=@nama,suku_bunga=@bunga WHERE id_bank =@id
END

CREATE PROCEDURE sp_deleteBank
	@id VARCHAR(10)
AS
BEGIN
	UPDATE ms_bank SET status=0 WHERE id_bank=@id
END


--VIEW

SELECT * FROM ms_user

CREATE PROCEDURE sp_viewUser
AS
BEGIN
	SELECT u.username,u.password,u.id_perumahan,u.id_role,u.nama_lengkap
		,u.email,u.alamat,u.jenis_kelamin,u.umur,u.photo,u.status,p.nama_perumahan,r.nama_role 
		FROM ms_user u
		JOIN ms_perumahan p ON p.id_perumahan = u.id_perumahan
		JOIN ms_role r ON r.id_role = u.id_role
END


CREATE PROCEDURE sp_viewRuko
AS
BEGIN
	SELECT r.*,p.nama_perumahan FROM ms_ruko r
	JOIN ms_perumahan p ON p.id_perumahan = r.id_perumahan
END

CREATE PROCEDURE sp_viewPerumahan
AS
BEGIN
	SELECT p.*,d.nama_developer
	FROM ms_perumahan p
	JOIN ms_developer d ON d.id_developer = p.id_developer
END

-- SP Transaction
-- RUKO
CREATE PROCEDURE sp_inputTrRuko
	@id VARCHAR(10),
	@idr VARCHAR(10),
	@uname VARCHAR(20),
	@NIK VARCHAR(16),
	@nama VARCHAR(55),
	@telp VARCHAR(13),
	@jns VARCHAR(20),
	@idb VARCHAR(10),
	@rek VARCHAR(15),
	@periode INT,
	@total MONEY,
	@dokumen VARBINARY(MAX),
	@expired DATE
AS
BEGIN
	INSERT INTO tr_ruko VALUES(@id,GETDATE(),@idr,@uname,@NIK,@nama,@telp,@jns,@idb,@rek,@periode,@total,@dokumen,1,@expired)
END

-- SP load app
CREATE PROCEDURE sp_statusRuko
AS
BEGIN
	UPDATE ms_ruko SET ketersediaan = 1 WHERE id_ruko IN (SELECT id_ruko FROM tr_ruko WHERE GETDATE() > tgl_expired);
	UPDATE tr_ruko SET status_kontrak = 0 WHERE GETDATE() > tgl_expired
END

EXEC sp_statusRuko

SELECT * FROM ms_user
SELECT * FROM ms_ruko
SELECT * FROM tr_ruko

-- Rumah
-- Pembayaran
CREATE PROCEDURE sp_inputTrRumah
	@id VARCHAR(10),
	@idr VARCHAR(10),
	@uname VARCHAR(20),
	@NIK VARCHAR(16),
	@nama VARCHAR(55),
	@telp VARCHAR(13),
	@jns VARCHAR(20),
	@idb VARCHAR(10),
	@rek VARCHAR(15),
	@bunga INT,
	@total MONEY,
	@status INT,
	@dokumen VARBINARY(MAX),
	@minCicil MONEY,
	@periode INT,
	@sisa MONEY,
	@tglCicil DATE,
	@tglLunas DATE
AS
BEGIN
	INSERT INTO tr_rumah VALUES (@id,GETDATE(),@idr,@uname,@NIK,@nama,@telp,@jns,@idb,@rek,@bunga,@total,@status,@dokumen,@minCicil,@periode,@sisa,@tglCicil,@tglLunas)
END

-- CICILAN PERBULAN
CREATE PROCEDURE sp_cicilanRumah
	@idr VARCHAR(10),
	@nominal MONEY
AS
BEGIN
	INSERT INTO CicilRumah VALUES(@idr,GETDATE(),@nominal)
END

-- Log Login
CREATE PROCEDURE sp_inputLogin
	@uname VARCHAR(20)
AS
BEGIN
	INSERT INTO LoginLog VALUES(@uname,GETDATE())
END


-- User Defined Function --



-- Trigger --
--Transaction
-- Update status ketersedian kavling

-- Update status ketersedian ruko
CREATE TRIGGER trg_Ruko
ON tr_ruko
AFTER INSERT
AS
BEGIN
	UPDATE ms_ruko SET ketersediaan = 0 WHERE id_ruko IN (SELECT id_ruko FROM inserted)
END

-- Update status ketersedian rumah
CREATE TRIGGER trg_Rumah
ON tr_rumah
AFTER INSERT
AS
BEGIN
	UPDATE ms_rumah SET ketersediaan = 0 WHERE id_rumah IN (SELECT id_rumah FROM inserted)
END

-- Cicil Rumah
CREATE TRIGGER trg_cicilRumah
ON CicilRumah
AFTER INSERT
AS
BEGIN
	UPDATE tr_rumah SET sisa_cicilan = sisa_cicilan - (SELECT nominal_cicil FROM inserted), tgl_cicilan = DATEADD(MONTH,1,tgl_cicilan) 
	WHERE id_trRumah IN (SELECT id_trRumah FROM inserted)
END

