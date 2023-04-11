USE tether;
SET @tether = 'tether';

CREATE TABLE IF NOT EXISTS `users`(
    `id` bigint(20)unsigned auto_increment,
    `username` varchar(100),
    `first_name` varchar(100),
    `last_name` varchar(100),
    `email` varchar(100),
    `password` varchar(100),
    `bio` varchar(1024) NULL
);
 SELECT count(*)
 INTO @exist
 FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = @tether
 and COLUMN_NAME = 'id'
 and COLLATION_NAME = 'username'
 and COLUMN_NAME = 'first_name'
 and COLUMN_NAME = 'last_name'
 and COLUMN_NAME = 'email'
 and COLUMN_NAME = 'password'
 and COLUMN_NAME= 'bio';

set @query = IF(@exist <= 0, CONCAT('ALTER TABLE ', @tether, '.`exam_tasks`  ADD COLUMN `user_id` bigint(20) NULL'),
                'select \'Column Exists\' status');

prepare stmt from @query;

EXECUTE stmt;

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48154', 'princeton', 'Kandice', 'Lunn', 'deetta_brown8132@apartments.com', 'fernando', 'versus focused expressed documentation promises');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48155', 'translate', 'Wally', 'Crumpton', 'migueltang@yahoo.com', '777777', 'garcia huge upc apparent jr');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48156', 'launched', 'Jacqui', 'Peebles', 'ena_lamar28112@hotmail.com', 'nicole', 'threat sleeps aye deferred dom');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48157', 'rpg', 'Arianne', 'Benjamin', 'natividad_whittington2@gmail.com', 'montana', 'dump presenting rugs hot doc');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48158', 'terminals', 'Sanda', 'Sepulveda', 'marjory.ocampo@generally.com', 'microsoft', 'ratios subscription clarke lit which');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48159', 'chat', 'Aiko', 'Clary', 'pedro-duckworth20@yahoo.com', 'george', 'studied wishlist cents freeware often');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48160', 'forge', 'Phylis', 'Lugo', 'dean-perkins@yahoo.com', 'sierra', 'coated gps gr booking xerox');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48161', 'audience', 'Lela', 'Whitmire', 'oniedurr57@yahoo.com', 'knight', 'atlas sunset stay strengthen elevation');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48162', 'millions', 'Lewis', 'Gamez', 'valarie71@hotmail.com', 'voodoo', 'demographic send plate pride headers');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48163', 'dan', 'Maryanna', 'Rosser', 'sydneywhitlow@zones.com', 'hamster', 'advise filters k creation statement');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48164', 'bicycle', 'Brittani', 'Pike', 'diane_finley692@operator.com', 'firebird', 'butterfly alcohol appreciated mileage atlanta');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48165', 'poverty', 'Kera', 'Hooker', 'clareorr90452@both.anquan', 'monica', 'theology sim develops teaching push');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48166', 'kingdom', 'Alene', 'David', 'johnathan-keys-cantrell@yahoo.com', 'steelers', 'recipe cw filme jury trailer');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48167', 'sunday', 'Dexter', 'Spillman', 'antone-macon@hotmail.com', 'firebird', 'recruiting navigation therefore toolbar manufacturer');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48168', 'compatible', 'Omer', 'Tolley', 'lynnacorbin56962@binary.com', 'october', 'showcase pool clerk rick american');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48169', 'eg', 'Cyrstal', 'Lund', 'codykeener@hotmail.com', '1q2w3e', 'verified musician goto clinics guess');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48170', 'casa', 'Edmond', 'Fowlkes', 'romona3211@controller.com', 'harvey', 'cord vpn was bookstore dat');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48171', 'everything', 'Adrian', 'Matteson', 'lorrettarash5690@hotmail.com', 'isabelle', 'taxes headers adrian anti lender');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48172', 'culture', 'Beaulah', 'Goodin', 'ravenparker26227@disclose.com', 'zzzzzz', 'extensive playback insurance nat dsl');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48173', 'incidence', 'Eusebia', 'Shifflett-Pettway', 'meghann99085@practical.com', 'walter', 'problem arena latin dive accept');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48174', 'encouraging', 'Francina', 'Lebron', 'larondairons89597@yahoo.com', 'guinness', 'revisions this york tops naval');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48175', 'thomas', 'Yahaira', 'Bradford', 'madeline.low370@hotmail.com', 'guitar', 'gratuit tech ri newcastle assign');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48176', 'excellent', 'Angelina', 'Escobar', 'judicurrie@gmail.com', 'vampire', 'using wallace berlin strap motherboard');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48177', 'curriculum', 'Stacia', 'Dayton', 'lyndialavender@gmail.com', 'nothing', 'belong apps draws ward jelsoft');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48178', 'cc', 'Julee', 'Baumann', 'shakira_andres9@gmail.com', 'angel', 'doom removable valley cnet scotland');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48179', 'manager', 'Shavonda', 'Burch', 'marc.wagner@hotmail.com', 'tennis', 'easter inspector trembl jeff channels');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48180', 'turn', 'Maura', 'Matthews', 'craig_espino@kinds.com', 'admin', 'expo eagle tracker lyrics raid');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48181', 'inkjet', 'Dayna', 'Bock', 'neal73@julie.com', 'hawaii', 'direct seems protocols colour madison');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48182', 'vietnam', 'Elenore', 'Barksdale', 'analisa-horne-ernst6429@hotmail.com', 'wesley', 'noted rid sphere gave permissions');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48183', 'danny', 'Celena', 'Collette', 'katenemeth379@appointed.com', 'asdfghjk', 'zip celtic songs bring soldiers');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48184', 'albany', 'Kent', 'Gamez', 'terese-pulliam02771@yahoo.com', 'rainbow', 'logged diseases hungary funds elimination');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48185', 'management', 'Pamala', 'Mcneal', 'johnette-cullen9345@gmail.com', 'corvette', 'databases womens gen lazy fingers');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48186', 'explaining', 'Farah', 'Donnell', 'debrah07725@yahoo.com', 'airborne', 'virtual brunette suffering pope xp');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48187', 'frame', 'Letty', 'Knudsen-Chisolm', 'marcie3115@gmail.com', 'master', 'depression thorough michel loading snap');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48188', 'harry', 'Rita', 'Bolling', 'nadine-vanwinkle9759@gmail.com', 'sweety', 'fiscal facial clearance expenses wearing');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48189', 'arnold', 'Samara', 'Mcclelland', 'leonarda-barham@gmail.com', 'doctor', 'une gave lion athens hip');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48190', 'aerospace', 'Yasuko', 'Windham', 'aurea_reddy66@creation.com', 'super', 'reasoning sells tonight penny dr');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48191', 'persons', 'Temika', 'Haag', 'orville-frantz54329@merge.kv.ua', 'william', 'deserve bias productive billy retro');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48192', 'copying', 'Vallie', 'Carbone', 'mila69053@download.oslo.no', 'patricia', 'depth discounts amateur mentor tx');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48193', 'du', 'Marilee', 'Felts', 'evelina_robins@careers.com', 'aaaaaa', 'fall nobody wear players annually');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48194', 'estate', 'Karolyn', 'Dewitt', 'joni9940@flu.com', 'westside', 'interventions panasonic resulting far schedule');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48195', 'snap', 'Willian', 'Gibbs', 'lita.maxwell846@buzz.com', 'chance', 'stores sally who thoughts reliance');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48196', 'self', 'Gregg', 'Martindale', 'harvey.yarborough87@frontpage.com', 'qweasd', 'discussions cdna eh coleman distinguished');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48197', 'sale', 'Shantell', 'Rodman', 'monserrate.shumaker@expo.com', 'manchester', 'any possession interaction ordered timber');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48198', 'fi', 'Vanessa', 'Dodge', 'chantelle26@yahoo.com', 'santiago', 'executive ou concerts ds registrar');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48199', 'deferred', 'Audie', 'Dugger', 'mason_anders8332@hotmail.com', 'alicia', 'results modeling promotions moves collaboration');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48200', 'rule', 'Etta', 'Belanger', 'liana-luckett63@assess.com', 'karina', 'outlets verify analyzed africa pretty');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48201', 'signal', 'Ricarda', 'Turpin', 'ronda8@yahoo.com', 'chris', 'cdna furthermore smart absolute venice');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48202', 'hose', 'Fidelia', 'Mireles', 'demetrice_richardson7782@excitement.trentinosud-tirol.it', 'jackie', 'nw img entities black speaking');

INSERT INTO users (id, username, first_name, last_name, email, password, bio)
VALUES ('48203', 'smilies', 'Chase', 'Rowe', 'muoi-mcleod@registrar.com', 'teacher', 'shaft placing sussex losses suggestion');
