begin transaction;

INSERT INTO public.userentity (id,username,"password",roles) VALUES
	 (2,'username','b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86',NULL),
	 (3,'username1','$2a$10$pzVEsUfoEobSjHmWjYeyAejynvWldYmI7ykXhY/GEszz/8GWIN5.W',NULL),
	 (12,'username2','$2a$10$BBWeHTZ3kTdMhKpy1rwyUe/zlqzH4pFFW9QSGMKB.6t72gpelGAeO',NULL),
	 (8,'username6','$2a$10$Tb/LyrA7x50DnvyzBrQCJ.Uj1mEo62cUHPOFbmriu7vChycKXWYcK',NULL),
	 (9,'username7','$2a$10$qrMhbHs1G9aG6ttHoX7ZSOUY8cut9p7hxWtaAI0VDZ3adRExVuhmu',NULL),
	 (4,'username8','$2a$10$LcyJJE.dj/AbYKtYEelBZeq.twDZHtB5FHFl.ABj2DklaP2Mt4dr2',NULL),
	 (18,'username9','$2a$10$o/e0BPPJGkkJXTd3Hgn1/.R478jMvv784QYRbOY6K7EMqGEBw7WdG','USER1,ADMIN');

insert into hibernate_sequences values ('Username', 1);

commit;