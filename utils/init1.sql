CREATE EXTENSION IF NOT EXISTS "pgcrypto";

DROP TABLE IF EXISTS lesson_question;
DROP TABLE IF EXISTS standalone_question;
DROP TABLE IF EXISTS lesson_term;
DROP TABLE IF EXISTS term;
DROP TABLE IF EXISTS progress;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS standalone_task;
DROP TABLE IF EXISTS lesson_task;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS file;
DROP TABLE IF EXISTS lesson;
DROP TABLE IF EXISTS _user;

CREATE TABLE _user
(
   email VARCHAR(320) NOT NULL,
   name VARCHAR(50) NOT NULL,
   surname VARCHAR(50) NOT NULL,
   date_of_birth DATE NOT NULL,
   gender VARCHAR(1) NOT NULL CHECK ( gender IN ('M', 'F') ),
   password VARCHAR(100) NOT NULL,
   theme_color VARCHAR(10) NOT NULL CHECK ( theme_color IN ('LIGHT', 'DARK', 'SYSTEM') ) DEFAULT 'SYSTEM',
   date_format VARCHAR(20) NOT NULL DEFAULT 'DD.MM.YYYY',
   PRIMARY KEY (email)
);

CREATE TABLE lesson
(
   id BIGSERIAL NOT NULL,
   lesson_name_cro VARCHAR(50) NOT NULL,
   lesson_name_jpn VARCHAR(50) NOT NULL,
   description_cro VARCHAR(200) NOT NULL,
   description_jpn VARCHAR(200) NOT NULL,
   difficulty INT NOT NULL CHECK ( difficulty BETWEEN 1 AND 5),
   PRIMARY KEY (id)
);

CREATE TABLE file
(
   UUID UUID NOT NULL DEFAULT gen_random_uuid(),
   data BYTEA NOT NULL,
   media_type VARCHAR(50) NOT NULL,
   file_name VARCHAR(50) NOT NULL,
   PRIMARY KEY (UUID)
);

CREATE TABLE task
(
   id BIGSERIAL NOT NULL,
   task_name_cro VARCHAR(80) NOT NULL,
   task_name_jpn VARCHAR(80) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE lesson_task
(
   id BIGINT NOT NULL,
   lesson_id BIGINT NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (id) REFERENCES task(id),
   FOREIGN KEY (lesson_id) REFERENCES lesson(id)
);

CREATE TABLE standalone_task
(
   id BIGINT NOT NULL,
   difficulty INT NOT NULL CHECK ( difficulty BETWEEN 1 AND 5 ),
   PRIMARY KEY (id),
   FOREIGN KEY (id) REFERENCES task(id)
);

CREATE TABLE question
(
   id BIGSERIAL NOT NULL,
   question_cro VARCHAR(80) NOT NULL,
   question_jpn VARCHAR(80) NOT NULL,
   hint_cro VARCHAR(80) NOT NULL,
   hint_jpn VARCHAR(80) NOT NULL,
   explanation_cro VARCHAR(150) NOT NULL,
   explanation_jpn VARCHAR(150) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE progress
(
   lesson_id BIGINT NOT NULL,
   email VARCHAR(320) NOT NULL,
   term_num INT NOT NULL,
   question_num INT,
   PRIMARY KEY (lesson_id, email),
   FOREIGN KEY (lesson_id) REFERENCES lesson(id),
   FOREIGN KEY (email) REFERENCES _user(email)
);

CREATE TABLE term
(
   id BIGSERIAL NOT NULL,
   difficulty INT NOT NULL CHECK ( difficulty BETWEEN 1 AND 5 ),
   term_name_cro VARCHAR(50) NOT NULL,
   term_name_jpn VARCHAR(50) NOT NULL,
   description_cro VARCHAR(150) NOT NULL,
   description_jpn VARCHAR(150) NOT NULL,
   audio_UUID UUID,
   image_UUID UUID,
   PRIMARY KEY (id),
   FOREIGN KEY (audio_UUID) REFERENCES file(UUID),
   FOREIGN KEY (image_UUID) REFERENCES file(UUID)
);

CREATE TABLE lesson_term
(
   term_id BIGINT NOT NULL,
   lesson_id BIGINT NOT NULL,
   term_num INT NOT NULL,
   PRIMARY KEY (term_id, lesson_id),
   FOREIGN KEY (term_id) REFERENCES term(id),
   FOREIGN KEY (lesson_id) REFERENCES lesson(id)
);

CREATE TABLE standalone_question
(
   id BIGINT NOT NULL,
   task_id BIGINT NOT NULL,
   term_id BIGINT NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (id) REFERENCES question(id),
   FOREIGN KEY (task_id) REFERENCES standalone_task(id),
   FOREIGN KEY (term_id) REFERENCES term(id)
);

CREATE TABLE lesson_question
(
   id BIGINT NOT NULL,
   task_id BIGINT NOT NULL,
   term_id BIGINT NOT NULL,
   lesson_id BIGINT NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (id) REFERENCES question(id),
   FOREIGN KEY (task_id) REFERENCES lesson_task(id),
   FOREIGN KEY (term_id, lesson_id) REFERENCES lesson_term(term_id, lesson_id)
);

INSERT INTO _user (email, name, surname, date_of_birth, gender, password, theme_color, date_format) VALUES
('sinisa.rados@icloud.com', 'Siniša', 'Radoš', '1969-04-26', 'M', '$2b$12$KPgt.crUu5lYkRjBhJpqbuIQFzDjEY6vC/HI2HD4gUhpyCS3J9Ezq', 'SYSTEM', 'YYYY-MM-DD'),
('anamarija.tadic@hotmail.com', 'Anamarija', 'Tadić', '1968-02-21', 'F', '$2b$12$6EVH5ff29bLTQv5I9N6cKuTcB9ZLGiN.ntYBVSnVEzWgPNw2zX8q.', 'SYSTEM', 'YYYY-MM-DD'),
('kristina.rogoznica@icloud.com', 'Kristina', 'Rogoznica', '1982-09-15', 'F', '$2b$12$iTQDAuDTsMU.bptdGZYOXewYjmp0tZx/WWafS06u7Gq4qf/.S0l7.', 'SYSTEM', 'DD.MM.YYYY'),
('lovro.tadic@mail.com', 'Lovro', 'Tadić', '1980-11-23', 'M', '$2b$12$Sr4mW3UHgthELel7Lz6vQuw062Kqe5g6uj3XWAZ67KB6zctN8WRr.', 'SYSTEM', 'MM/DD/YYYY'),
('stjepan.soldo@icloud.com', 'Stjepan', 'Soldo', '2009-03-25', 'M', '$2b$12$DP.mdmHYchzwRmKXi6Y6POMmorKCo/cSv3aNn24FnigtUhia6927C', 'SYSTEM', 'YYYY-MM-DD'),
('matea.rogoznica@icloud.com', 'Matea', 'Rogoznica', '1980-11-15', 'F', '$2b$12$21AlN.RnzAitLsYekkrveeD8vzi/wTlF7YoJpp1hFx3Zc4Zdx8UKS', 'DARK', 'YYYY-MM-DD'),
('barbara.petrovic@yahoo.com', 'Barbara', 'Petrović', '2006-11-11', 'F', '$2b$12$qLW4NA8YrEgQTWtVSzBL8.0W2ewRPoAZsHvrEBvjcERsnVmG53l9m', 'DARK', 'MM/DD/YYYY'),
('mirjana.benkovic@mail.com', 'Mirjana', 'Benković', '2001-09-03', 'F', '$2b$12$aHFjIgtZIOwVzTgNTiSR5.k4QV.U0OqiT84puaWlijjPxA60e4LT6', 'LIGHT', 'YYYY-MM-DD'),
('karlo.tomic@outlook.com', 'Karlo', 'Tomić', '1998-01-30', 'M', '$2b$12$GS5j2la61UmPbIW2UL3gG.oGoJB9rEGX7ytE2PAztxS38M3inD2HG', 'SYSTEM', 'YYYY-MM-DD'),
('andrea.milic@aol.com', 'Andrea', 'Milić', '2004-01-10', 'F', '$2b$12$GhhOcQszexKVIBJsUOC35e88TyTCAl36PSK92iyJ/0OJ990VsI8vW', 'LIGHT', 'DD.MM.YYYY');

INSERT INTO lesson (lesson_name_cro, lesson_name_jpn, description_cro, description_jpn, difficulty) VALUES
('Upoznavanje', '自己紹介', 'Naučite kako se predstaviti i upoznati druge ljude.', '自己紹介や他の人と知り合う方法を学びます。', 1),
('Brojevi', '数字', 'Osnovni brojevi od 1 do 100 i kako ih koristiti.', '1から100までの基本的な数字とその使い方。', 1),
('Dani u tjednu', '曜日', 'Nazivi dana u tjednu i kako govoriti o rasporedu.', '曜日の名前とスケジュールについて話す方法。', 1),
('Hrana i piće', '食べ物と飲み物', 'Rječnik o hrani i piću, naručivanje u restoranu.', '食べ物と飲み物の語彙、レストランでの注文の仕方。', 2),
('Vrijeme i datum', '時間と日付', 'Kako pitati i reći vrijeme i datum.', '時間や日付の尋ね方と答え方。', 2),
('Glagoli i konjugacija', '動詞と活用', 'Uvod u osnovne glagole i njihovu konjugaciju.', '基本的な動詞とその活用の紹介。', 3),
('Putovanje', '旅行', 'Izrazi korisni za putovanje i navigaciju.', '旅行や道案内で役立つ表現。', 2),
('Kupovina', '買い物', 'Kako kupovati i pitati za cijene.', '買い物の仕方や値段の尋ね方。', 2),
('Pristojnost i formalnost', '丁寧語と敬語', 'Kako koristiti pristojan jezik u razgovoru.', '会話で丁寧語や敬語を使う方法。', 4),
('Kultura i običaji', '文化と習慣', 'Osnove japanske kulture i društvenih normi.', '日本の文化や社会的習慣の基本。', 5);

INSERT INTO task (task_name_cro, task_name_jpn) VALUES
('Upoznavanje', '自己紹介'),
('Brojevi', '数字'),
('Dani u tjednu', '曜日'),
('Hrana i piće', '食べ物と飲み物'),
('Vrijeme i datum', '時間と日付'),
('Glagoli i konjugacija', '動詞と活用'),
('Putovanje', '旅行'),
('Kupovina', '買い物'),
('Pristojnost i formalnost', '丁寧語と敬語'),
('Kultura i običaji', '文化と習慣'),
('Boje', '色'),
('Obitelj', '家族'),
('Lokacije i smjerovi', '場所と方向'),
('Emocije i osjećaji', '感情と気持ち');

INSERT INTO lesson_task(id, lesson_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

INSERT INTO standalone_task(id, difficulty) VALUES
(11, 1),
(12, 2),
(13, 3),
(14, 4);

INSERT INTO question (question_cro, question_jpn, hint_cro, hint_jpn, explanation_cro, explanation_jpn) VALUES
('Kako na japanskom reći "Zdravo"?', '「こんにちは」はクロアチア語で何と言いますか？', 'Pozdrav', '挨拶', 'Naučiš kako se pozdravlja tijekom dana.', '日中の挨拶の言い方を学びます。'),
('Kako reći "Moje ime je..." na japanskom?', '「わたしのなまえは…」はクロアチア語で何ですか？', 'Predstavljanje', '自己紹介', 'Izraz koji koristimo kad se predstavljamo.', '自己紹介の時に使う表現です。'),
('Koju frazu koristiš kad nekoga prvi put upoznaš?', '初対面のとき、どの挨拶を使いますか？', 'Upoznavanje', '初対面', 'Koristi se kada se prvi put susrećemo s nekim.', '初めて会う時に使います。'),
('Kako pitamo nekoga za ime na japanskom?', '名前を聞くとき、どう言いますか？', 'Ime', '名前', 'Učiš kako pitati osobu za ime.', '相手の名前の尋ね方を学びます。'),
('Kako pitaš nekoga odakle je?', '出身地を尋ねる時、何と言いますか？', 'Porijeklo', '出身', 'Pomaže ti naučiti pitati odakle je osoba.', '相手の出身地を尋ねる方法を学びます。'),
('Kako reći broj 0 na japanskom?', '数字の0は日本語で何と言いますか？', 'Broj', '数字', 'Naučiš brojanje od nule.', '数字の基本を学びます。'),
('Kako se kaže broj 1 na japanskom?', '「1」はクロアチア語で何ですか？', 'Prvi broj', '最初の数字', 'Početak brojanja.', '数字の最初です。'),
('Kako reći broj 2 na japanskom?', '「2」は日本語で何と言いますか？', 'Drugi broj', '2番目', 'Nastavak brojanja.', '数字の次に続きます。'),
('Kako se kaže broj 3 na japanskom?', '「3」は日本語で何ですか？', 'Često korišten', 'よく使う', 'Često korišten broj u svakodnevici.', '日常的によく使われる数字です。'),
('Koji broj ima dva izgovora, uključujući "shi"?', '「し」という発音を含む数字はどれですか？', 'Značenje smrti', '死の意味', 'Učiš zašto se neki izgovori izbjegavaju.', 'ある発音が避けられる理由を学びます。'),
('Kako se kaže "Ponedjeljak" na japanskom?', '「月曜日」はクロアチア語で何ですか？', 'Početak tjedna', '週の始まり', 'Ponedjeljak je prvi dan radnog tjedna.', '月曜日は週の最初の平日です。'),
('Koji dan slijedi nakon ponedjeljka?', '月曜日の次の日は何曜日ですか？', 'Nakon ponedjeljka', '月曜の次', 'Utorak je drugi dan u tjednu.', '火曜日は週の2日目です。'),
('Kako se zove dan koji je u sredini tjedna?', '週の真ん中の日は何曜日ですか？', 'Sredina tjedna', '週の真ん中', 'Srijeda dijeli tjedan na dva dijela.', '水曜日は週の中央にあります。'),
('Koji dan dolazi nakon srijede?', '水曜日の次の日は何曜日ですか？', 'Nakon srijede', '水曜の次', 'Četvrtak dolazi nakon srijede.', '木曜日は水曜日の次の日です。'),
('Koji dan je za većinu ljudi zadnji radni dan?', '多くの人にとって、最後の平日は何曜日ですか？', 'Zadnji radni dan', '最後の平日', 'Petak označava kraj radnog tjedna.', '金曜日は多くの人にとって週の終わりです。'),
('Kako se kaže "Voda" na japanskom?', '「水」はクロアチア語で何ですか？', 'Osnovno piće', '基本的な飲み物', 'Voda je osnovno piće koje je bezbojan tekućina.', '水は無色の液体で、基本的な飲み物です。'),
('Koje je glavno jelo u japanskoj kuhinji?', '日本料理の主食は何ですか？', 'Glavno jelo', '主食', 'Riža je osnovno jelo u japanskoj kuhinji.', 'ご飯は日本料理の主食です。'),
('Kako se kaže "Riba" na japanskom?', '「魚」はクロアチア語で何ですか？', 'Namirnica', '食材', 'Riba je često korištena u japanskoj kuhinji.', '魚は日本料理でよく使われる食材です。'),
('Koje je popularno japansko piće?', '日本で人気のある飲み物は何ですか？', 'Zeleni čaj', '緑茶', 'Čaj je popularno piće, a zeleni čaj je najčešći.', 'お茶は日本で人気があり、緑茶が一般的です。'),
('Kako se kaže "Juha od miso" na japanskom?', '「味噌汁」はクロアチア語で何ですか？', 'Japanska juha', '日本のスープ', 'Juha od miso je tradicionalna japanska juha od fermentirane paste.', '味噌汁は発酵した味噌を使った日本の伝統的なスープです。'),
('Kako se kaže "Danas" na japanskom?', '「今日」はクロアチア語で何ですか？', 'Danasnji dan', '現在の日', 'Danas označava trenutni dan.', '今日は現在の日を指します。'),
('Kako se kaže "Jučer" na japanskom?', '「昨日」はクロアチア語で何ですか？', 'Dan prije danas', '今日の前の日', 'Jučer je dan koji je prethodio današnjem.', '昨日は今日の前の日です。'),
('Kako se kaže "Sutra" na japanskom?', '「明日」はクロアチア語で何ですか？', 'Dan nakon danas', '今日の次の日', 'Sutra je dan koji dolazi nakon današnjeg.', '明日は今日の次の日です。'),
('Kako se zove razdoblje od sedam dana?', '7日間の期間は何ですか？', 'Sedam dana', '7日間', 'Tjedan je razdoblje od sedam dana.', '週は7日間の期間です。'),
('Kako se kaže "Mjesec" na japanskom?', '「月」はクロアチア語で何ですか？', 'Razdoblje od oko 30 dana', '約30日の期間', 'Mjesec je razdoblje od oko 30 dana ili naziv za lunarni ciklus.', '月は約30日の期間、または月の名前です。'),
('Kako se kaže "Raditi" na japanskom?', '「働く」はクロアチア語で何ですか？', 'Izvoditi aktivnosti', '活動を行う', 'Raditi znači izvoditi fizičke ili intelektualne aktivnosti.', '働くとは肉体的または知的な活動を行うことです。'),
('Kako se kaže "Jesti" na japanskom?', '「食べる」はクロアチア語で何ですか？', 'Uzimanje hrane', '食べ物を取る', 'Jesti znači uzimati hranu.', '食べるとは食べ物を取ることです。'),
('Kako se kaže "Piti" na japanskom?', '「飲む」はクロアチア語で何ですか？', 'Uzimanje tekućine', '液体を取る', 'Piti znači uzimati tekućinu.', '飲むとは液体を取ることです。'),
('Kako se kaže "Spavati" na japanskom?', '「寝る」はクロアチア語で何ですか？', 'Stanje mirovanja', '休むこと', 'Spavati znači biti u stanju mirovanja tijekom noći.', '寝るとは夜間に休むことです。'),
('Kako se kaže "Čitati" na japanskom?', '「読む」はクロアチア語で何ですか？', 'Istraživanje teksta', 'テキストを読む', 'Čitati znači istraživati tekst ili pisani materijal.', '読むとはテキストや書かれた資料を読むことです。'),
('Kako se kaže "Putovanje" na japanskom?', '「旅行」はクロアチア語で何ですか？', 'Putovanje od mjesta do mjesta', '場所から場所への移動', 'Putovanje znači kretanje od mjesta do mjesta u svrhu opuštanja, istraživanja ili poslovanja.', '旅行はリラックス、探検、またはビジネスの目的で場所から場所への移動です。'),
('Kako se kaže "Zrakoplov" na japanskom?', '「飛行機」はクロアチア語で何ですか？', 'Uređaj za letenje', '飛ぶ乗り物', 'Zrakoplov je uređaj koji leti i koristi se za putovanje na velike udaljenosti.', '飛行機は遠距離移動のために使われる飛行する乗り物です。'),
('Kako se kaže "Automobil" na japanskom?', '「自動車」はクロアチア語で何ですか？', 'Vozilo na kotačima', '車輪のついた乗り物', 'Automobil je vozilo na kotačima koje se koristi za putovanje po cesti.', '自動車は道路を走るために使われる車両です。'),
('Kako se kaže "Vlak" na japanskom?', '「電車」はクロアチア語で何ですか？', 'Sredstvo za prijevoz po željeznici', '鉄道の乗り物', 'Vlak je sredstvo za prijevoz na željezničkoj pruzi.', '電車は鉄道の上を走る交通手段です。'),
('Kako se kaže "Brod" na japanskom?', '「船」はクロアチア語で何ですか？', 'Plovni objekt', '海の乗り物', 'Brod je plovni objekt koji se koristi za putovanje po moru.', '船は海上を移動するための乗り物です。'),
('Kako se kaže "Kupovina" na japanskom?', '「買い物」はクロアチア語で何ですか？', 'Aktivnost kupnje', '購入活動', 'Kupovina je aktivnost kupnje proizvoda ili usluga.', '買い物は製品やサービスを購入する活動です。'),
('Kako se kaže "Trgovina" na japanskom?', '「店」はクロアチア語で何ですか？', 'Mjesto za kupovinu', '購入場所', 'Trgovina je mjesto gdje se prodaju proizvodi ili usluge.', '店は製品やサービスを販売する場所です。'),
('Kako se kaže "Novac" na japanskom?', '「お金」はクロアチア語で何ですか？', 'Sredstvo razmjene', '交換手段', 'Novac je sredstvo razmjene za kupovinu proizvoda ili usluga.', 'お金は製品やサービスを購入するための交換手段です。'),
('Kako se kaže "Cijena" na japanskom?', '「価格」はクロアチア語で何ですか？', 'Vrijednost izražena u novcu', '金銭的な価値', 'Cijena je vrijednost proizvoda ili usluge izražena u novčanoj jedinici.', '価格は製品やサービスの金銭的な価値です。'),
('Kako se kaže "Popust" na japanskom?', '「割引」はクロアチア語で何ですか？', 'Smanjenje cijene', '価格を下げる', 'Popust je smanjenje cijene proizvoda ili usluge.', '割引は製品やサービスの価格を下げることです。'),
('Kako se kaže "Pozdrav" na japanskom?', '「挨拶」はクロアチア語で何ですか？', 'Izraz pri susretu ili odlasku', '出会いや別れの言葉', 'Pozdrav je riječ ili izraz koji se koristi pri susretu ili odlasku od drugih.', '挨拶は人と会うときや別れるときに使う言葉や表現です。'),
('Kako se kaže "Molim" na japanskom?', '「お願いします」はクロアチア語で何ですか？', 'Ljubazni izraz za traženje', 'お願いの表現', 'Molim je ljubazni izraz za traženje nečega ili izražavanje želje.', 'お願いしますは何かをお願いする時に使う丁寧な表現です。'),
('Kako se kaže "Hvala" na japanskom?', '「ありがとう」はクロアチア語で何ですか？', 'Izraz zahvalnosti', '感謝の言葉', 'Hvala je izraz zahvalnosti.', 'ありがとうは感謝の気持ちを表す言葉です。'),
('Kako se kaže "Izvolite" na japanskom?', '「どうぞ」はクロアチア語で何ですか？', 'Izraz pri davanju nečega', '渡す時の表現', 'Izvolite je izraz koji se koristi prilikom davanja nečega nekoj osobi.', 'どうぞは何かを他の人に渡す時に使う表現です。'),
('Kako se kaže "Oprostite" na japanskom?', '「すみません」はクロアチア語で何ですか？', 'Izraz za ispriku ili privlačenje pažnje', '謝罪や注意を引く', 'Oprostite je izraz koji se koristi za ispriku ili privlačenje pažnje.', 'すみませんは謝罪や注意を引くために使う表現です。'),
('Kako se kaže "Kultura" na japanskom?', '「文化」はクロアチア語で何ですか？', 'Skup običaja i vjerovanja', '習慣や信仰の集まり', 'Kultura je skup običaja, vjerovanja, umjetnosti i ponašanja određene grupe ljudi.', '文化はある集団の習慣、信仰、芸術、行動の総体です。'),
('Kako se kaže "Običaji" na japanskom?', '「習慣」はクロアチア語で何ですか？', 'Uobičajeni načini ponašanja', '一般的な行動様式', 'Običaji su uobičajeni načini ponašanja unutar zajednice ili društva.', '習慣は社会や共同体の中での一般的な行動様式です。'),
('Kako se kaže "Festival" na japanskom?', '「祭り」はクロアチア語で何ですか？', 'Proslava s tradicijama', '伝統的なお祝い', 'Festival je proslava s tradicionalnim aktivnostima, glazbom i hranom.', '祭りは伝統的な活動や音楽、食べ物を伴うお祝いです。'),
('Kako se kaže "Kimono" na japanskom?', '「着物」はクロアチア語で何ですか？', 'Tradicionalna japanska odjeća', '日本の伝統的な衣装', 'Kimono je tradicionalna japanska odjeća nošena u posebnim prilikama.', '着物は特別な場で着られる日本の伝統的な衣装です。'),
('Kako se kaže "Japanska čajna ceremonija" na japanskom?', '「茶道」はクロアチア語で何ですか？', 'Tradicionalni ritual s čajem', 'お茶の儀式', 'Japanska čajna ceremonija je tradicionalni ritual pripreme i posluživanja čaja.', '茶道はお茶を点てて提供する日本の伝統的な儀式です。'),

('Kako se kaže "Crvena" na japanskom?', '「赤」はクロアチア語で何ですか？', 'Topla boja', '暖色', 'Crvena je topla boja koja se često povezuje s ljubavlju, strašću i opasnošću.', '赤は愛や情熱、危険と結びつけられる暖色です。'),
('Kako se kaže "Plava" na japanskom?', '「青」はクロアチア語で何ですか？', 'Hladna boja', '寒色', 'Plava je hladna boja koja simbolizira mir, stabilnost i povjerenje.', '青は平和や安定、信頼を象徴する寒色です。'),
('Kako se kaže "Žuta" na japanskom?', '「黄色」はクロアチア語で何ですか？', 'Svijetla boja', '明るい色', 'Žuta je svijetla boja povezana s radošću, energijom i oprezom.', '黄色は喜びやエネルギー、注意と関係する明るい色です。'),
('Kako se kaže "Zelena" na japanskom?', '「緑」はクロアチア語で何ですか？', 'Boja prirode', '自然の色', 'Zelena je boja prirode koja označava rast, svježinu i sklad.', '緑は自然を表し、成長や新鮮さ、調和を意味します。'),
('Kako se kaže "Crna" na japanskom?', '「黒」はクロアチア語で何ですか？', 'Tamna boja', '暗い色', 'Crna je tamna boja povezana s elegancijom, tajanstvenošću i tugom.', '黒は優雅さや神秘、悲しみと結びつけられる暗い色です。'),
('Kako se kaže "Bijela" na japanskom?', '「白」はクロアチア語で何ですか？', 'Boja čistoće', '純粋な色', 'Bijela je boja čistoće, nevinosti i novog početka.', '白は純粋さや無垢、新たな始まりを象徴する色です。'),
('Kako se kaže "Ružičasta" na japanskom?', '「桃色」はクロアチア語で何ですか？', 'Nježna boja', '優しい色', 'Ružičasta je nježna boja povezana s romantikom i nježnošću.', '桃色はロマンスや優しさを表す柔らかい色です。'),
('Kako se kaže "Siva" na japanskom?', '「灰色」はクロアチア語で何ですか？', 'Neutralna boja', '中立的な色', 'Siva je neutralna boja između crne i bijele, često označava formalnost ili tugu.', '灰色は黒と白の間の中間色で、格式や悲しみを表すことがあります。'),

('Kako se kaže "Majka" na japanskom?', '「母」はクロアチア語で何ですか？', 'Žena koja je rodila ili odgaja dijete', '子どもを産んだり育てたりする女性', 'Majka je žena koja je rodila ili odgaja dijete.', '母は子どもを産んだり育てたりする女性です。'),
('Kako se kaže "Otac" na japanskom?', '「父」はクロアチア語で何ですか？', 'Muškarac koji je otac djeteta', '子どもの父親である男性', 'Otac je muškarac koji je otac djeteta.', '父は子どもの父親である男性です。'),
('Kako se kaže "Brat" na japanskom?', '「兄弟」はクロアチア語で何ですか？', 'Muški član iste obitelji', '同じ親を持つ男性のきょうだい', 'Brat je muški član iste obitelji, sin istih roditelja.', '兄弟は同じ親を持つ男性のきょうだいです。'),
('Kako se kaže "Sestra" na japanskom?', '「姉妹」はクロアチア語で何ですか？', 'Ženska članica iste obitelji', '同じ親を持つ女性のきょうだい', 'Sestra je ženska članica iste obitelji, kći istih roditelja.', '姉妹は同じ親を持つ女性のきょうだいです。'),
('Kako se kaže "Baka" na japanskom?', '「祖母」はクロアチア語で何ですか？', 'Majka oca ili majke', '父または母の母親', 'Baka je majka oca ili majke.', '祖母は父または母の母親です。'),
('Kako se kaže "Djed" na japanskom?', '「祖父」はクロアチア語で何ですか？', 'Otac oca ili majke', '父または母の父親', 'Djed je otac oca ili majke.', '祖父は父または母の父親です。'),

('Kako se kaže "Desno" na japanskom?', '「右」はクロアチア語で何ですか？', 'Smjer prema desnoj strani tijela', '前を向いたときの体の右側の方向', 'Desno je smjer prema desnoj strani tijela kada gledaš naprijed.', '右は前を向いたときの体の右側の方向です。'),
('Kako se kaže "Lijevo" na japanskom?', '「左」はクロアチア語で何ですか？', 'Smjer prema lijevoj strani tijela', '前を向いたときの体の左側の方向', 'Lijevo je smjer prema lijevoj strani tijela kada gledaš naprijed.', '左は前を向いたときの体の左側の方向です。'),
('Kako se kaže "Ispred" na japanskom?', '「前」はクロアチア語で何ですか？', 'Prostor koji se nalazi ispred tebe', '自分や物の前方にある空間', 'Ispred je prostor koji se nalazi ispred tebe ili nečega.', '前は自分や物の前方にある空間です。'),
('Kako se kaže "Iza" na japanskom?', '「後ろ」はクロアチア語で何ですか？', 'Prostor koji se nalazi iza tebe', '自分や物の後方にある空間', 'Iza je prostor koji se nalazi iza tebe ili nečega.', '後ろは自分や物の後方にある空間です。'),
('Kako se kaže "Blizu" na japanskom?', '「近く」はクロアチア語で何ですか？', 'Lokacija koja nije daleko', '距離が短く、近い場所にある', 'Blizu je lokacija koja nije daleko, u neposrednoj blizini.', '近くは距離が短く、近い場所にあることを表します。'),

('Kako se kaže "Sretan" na japanskom?', '「嬉しい」はクロアチア語で何ですか？', 'Osjećaj radosti i zadovoljstva', '喜びや満足感を感じる気持ち', 'Sretan je osjećaj radosti i zadovoljstva.', '嬉しいは喜びや満足感を感じる気持ちです。'),
('Kako se kaže "Tužan" na japanskom?', '「悲しい」はクロアチア語で何ですか？', 'Osjećaj tuge zbog gubitka ili razočaranja', '喪失や失望によって感じる悲しみ', 'Tužan je osjećaj tuge zbog gubitka ili razočaranja.', '悲しいは喪失や失望によって感じる悲しみです。'),
('Kako se kaže "Ljut" na japanskom?', '「怒っている」はクロアチア語で何ですか？', 'Osjećaj bijesa ili frustracije', '不正や問題による怒りや苛立ち', 'Ljut je osjećaj bijesa ili frustracije zbog nepravde ili problema.', '怒っているは不正や問題による怒りや苛立ちを感じることです。'),
('Kako se kaže "Zaljubljen" na japanskom?', '「恋している」はクロアチア語で何ですか？', 'Osjećaj jake emocionalne privrženosti', '誰かに対する強い感情的な愛着', 'Zaljubljen je osjećaj jake emocionalne privrženosti prema nekome.', '恋しているは誰かに対する強い感情的な愛着です。'),
('Kako se kaže "Nervozan" na japanskom?', '「緊張している」はクロアチア語で何ですか？', 'Osjećaj tjeskobe ili napetosti', '不安や緊張を感じる状態', 'Nervozan je osjećaj tjeskobe ili napetosti u nepoznatim situacijama.', '緊張しているは不安や緊張を感じる状態です。'),
('Kako se kaže "Zbunjen" na japanskom?', '「混乱している」はクロアチア語で何ですか？', 'Stanje nesigurnosti ili nerazumijevanja', 'よく分からず、どうしていいか分からない状態', 'Zbunjen je stanje nesigurnosti ili nerazumijevanja.', '混乱しているはよく分からず、どうしていいか分からない状態です。'),
('Kako se kaže "Umoran" na japanskom?', '「疲れている」はクロアチア語で何ですか？', 'Fizički ili mentalni osjećaj iscrpljenosti', '身体的または精神的な疲れを感じる', 'Umoran je fizički ili mentalni osjećaj iscrpljenosti.', '疲れているは身体的または精神的な疲れを感じることです。');

INSERT INTO progress (lesson_id, email, term_num, question_num) VALUES
(1, 'sinisa.rados@icloud.com', 3, NULL),
(1, 'matea.rogoznica@icloud.com', 5, NULL),
(2, 'stjepan.soldo@icloud.com', 1, NULL),
(3, 'andrea.milic@aol.com', 6, NULL),
(3, 'sinisa.rados@icloud.com', 10, 2),
(4, 'matea.rogoznica@icloud.com', 3, NULL),
(5, 'karlo.tomic@outlook.com', 4, NULL),
(5, 'matea.rogoznica@icloud.com', 1, NULL),
(5, 'stjepan.soldo@icloud.com', 5, NULL),
(5, 'andrea.milic@aol.com', 8, NULL),
(6, 'lovro.tadic@mail.com', 3, NULL),
(7, 'karlo.tomic@outlook.com', 7, NULL),
(8, 'lovro.tadic@mail.com', 8, NULL),
(9, 'sinisa.rados@icloud.com', 4, NULL),
(9, 'andrea.milic@aol.com', 9, NULL),
(10, 'karlo.tomic@outlook.com', 10, 4),
(10, 'matea.rogoznica@icloud.com', 10, 3);