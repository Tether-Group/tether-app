USE ymir_joseph; # TODO: REPLACE THIS WITH YOUR DB NAME
SET @myDB = 'ExamDB'; # TODO: REPLACE THIS WITH YOUR DB NAME

DROP TABLE IF EXISTS `exam_tasks`;
DROP TABLE IF EXISTS `exam_departments`;
DROP TABLE IF EXISTS `exam_users`;

CREATE TABLE `exam_departments` (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                    `name` varchar(255) NOT NULL,
                                    PRIMARY KEY (`id`)
);

CREATE TABLE `exam_users` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `email` varchar(255) NOT NULL,
                              `first_name` varchar(255) NOT NULL,
                              `last_name` varchar(255) NOT NULL,
                              `password` varchar(255) NOT NULL,
                              `username` varchar(255) NOT NULL,
                              PRIMARY KEY (`id`)
) ;

CREATE TABLE `exam_tasks` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `description` text NOT NULL,
                              `is_complete` bit(1) NOT NULL,
                              `name` varchar(255) NOT NULL,
                              `department_id` bigint(20) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              KEY `FKh3q54aq89r95548j6y5np2u0p` (`department_id`),
                              CONSTRAINT `FKh3q54aq89r95548j6y5np2u0p` FOREIGN KEY (`department_id`) REFERENCES `exam_departments` (`id`)
);

# # Alerts the table to add a user_id column in case none is yet present in exam_tasks table
SELECT count(*)
INTO @exist
FROM information_schema.columns
WHERE table_schema = @myDB
  and COLUMN_NAME = 'user_id'
  AND table_name = 'exam_tasks' LIMIT 1;

set @query = IF(@exist <= 0, CONCAT('ALTER TABLE ', @myDB, '.`exam_tasks`  ADD COLUMN `user_id` bigint(20) NULL'),
                'select \'Column Exists\' status');

prepare stmt from @query;

EXECUTE stmt;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE exam_tasks;
TRUNCATE exam_departments;
TRUNCATE exam_users;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO exam_users (email, first_name, last_name, password, username) VALUES
                                                                              ('john@email.com', 'John', 'Conner', 'goodpassword', 'juser'),
                                                                              ('cathy@email.com', 'Cathy', 'Conner', 'goodpassword', 'cuser'),
                                                                              ('fred@email.com', 'Fred', 'Conner', 'goodpassword', 'fuser');

INSERT INTO exam_departments (name)
VALUES ('research and development'),
       ('management'),
       ('human trials'),
       ('bionics'),
       ('robotics'),
       ('legal');


INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.

Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus.

Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.',
        FALSE, 'Little Group', 5, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.

Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.',
        TRUE, 'Thiel Group', 4, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo.

Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.

Sed ante. Vivamus tortor. Duis mattis egestas metus.', FALSE, 'Erdman, Kassulke and O''Hara', 4, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.

Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus.', TRUE, 'Douglas and Sons', 5, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum.', FALSE,
        'Walter and Sons', 4, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.

Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.

Fusce consequat. Nulla nisl. Nunc nisl.', FALSE, 'Rice and Sons', 2, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.

Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.',
        TRUE, 'Cummerata, Effertz and Daugherty', 2, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus.', TRUE, 'White Inc', 3, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.

Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.

Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.', TRUE,
        'O''Conner and Sons', 3, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi.

Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus.',
        FALSE, 'Skiles-Schiller', 1, 2);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.

Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.',
        TRUE, 'Jacobi-Gibson', 6, 2);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo.

Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.

Sed ante. Vivamus tortor. Duis mattis egestas metus.', FALSE, 'Predovic, Wisoky and Fay', 2, 2);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.

Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.',
        TRUE, 'Glover Inc', 1, 2);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.',
        FALSE, 'Gutmann, Bauch and Cartwright', 1, 2);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.',
        FALSE, 'Walsh-Gottlieb', 5, 2);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.

Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.', TRUE, 'Rohan-Okuneva', 2, 2);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus.

Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.

Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', FALSE, 'Will-Morissette', 3, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst.

Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', FALSE,
        'Shanahan and Sons', 2, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.

Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.', FALSE,
        'Lowe, Lang and Schmitt', 3, 1);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi.

Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus.',
        FALSE, 'Green-Doyle', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.

Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.

Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.', TRUE,
        'Beier Group', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In quis justo. Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet.

Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui.

Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.',
        FALSE, 'White, Volkman and Sanford', 5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.

Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.

Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum.', TRUE, 'Hayes-Johnson', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.

Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.',
        TRUE, 'Abshire, Schmitt and O''Connell', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.

Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.

Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum.', FALSE, 'Daugherty LLC', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.

Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.

Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.',
        TRUE, 'Armstrong Group', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.

Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.',
        TRUE, 'Labadie and Sons', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In congue. Etiam justo. Etiam pretium iaculis justo.

In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.', TRUE, 'Gleason-Fisher', 5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus.

Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.',
        TRUE, 'King, Rice and Okuneva', 2, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.

Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem.

Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.',
        FALSE, 'Wolf-Cummerata', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.

Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.',
        TRUE, 'Marquardt, Klocko and Wolff', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.

Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst.

Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', FALSE,
        'Wehner, Herzog and Fisher', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Proin leo odio, porttitor id, consequat in, consequat ut, nulla. Sed accumsan felis. Ut at dolor quis odio consequat varius.

Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi.

Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus.',
        FALSE, 'Nienow, Howe and Durgan', 5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh.', TRUE,
        'Lubowitz LLC', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo.

Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.

Sed ante. Vivamus tortor. Duis mattis egestas metus.', FALSE, 'Parker-Kuhic', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.

Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.',
        TRUE, 'Labadie-Daugherty', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.',
        FALSE, 'Emard-Doyle', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.

Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.', TRUE, 'Rath-O''Reilly', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus.

In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.

Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst.', TRUE, 'Adams, Lueilwitz and Rowe', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.

Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.', FALSE,
        'Nitzsche and Sons', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.

Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.',
        FALSE, 'Sporer, Bernier and Johnson', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.

Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.', FALSE, 'Leffler-Auer', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.

Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.', FALSE,
        'Armstrong, Satterfield and Konopelski', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.

Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.

Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.', FALSE,
        'Lockman-Jerde', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.

In congue. Etiam justo. Etiam pretium iaculis justo.

In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.', TRUE, 'Moen and Sons', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.', TRUE, 'Russel LLC',
        5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.

Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.', TRUE, 'Doyle LLC', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus.

Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.',
        FALSE, 'Heathcote, Erdman and Brakus', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Fusce consequat. Nulla nisl. Nunc nisl.', FALSE, 'Pouros Group', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.

Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.

Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh.', FALSE, 'Leffler-Lind', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.

Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.', FALSE, 'Stokes-Wunsch', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.

Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.

Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.', FALSE,
        'Harber Group', 2, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.

Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum.', TRUE, 'Thiel Inc', 5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam.',
        TRUE, 'Swaniawski-Dicki', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum.

Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.',
        FALSE, 'Mills, Bahringer and Heathcote', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem.',
        FALSE, 'Krajcik-Price', 5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.

Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.

Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.',
        TRUE, 'Reichel LLC', 5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In congue. Etiam justo. Etiam pretium iaculis justo.

In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.

Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi.', FALSE, 'Klein LLC', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.

Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.

Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem.',
        TRUE, 'Grant Group', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.

Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst.

Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', FALSE, 'Little LLC', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.

Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.', FALSE, 'Stanton Inc',
        2, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.

In congue. Etiam justo. Etiam pretium iaculis justo.

In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.', TRUE, 'Will-Romaguera', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.

Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum.

Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.',
        FALSE, 'Mann, Kunze and Oberbrunner', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum.', TRUE,
        'Russel, Crist and Harris', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.

Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.',
        TRUE, 'Schultz, Jenkins and Ward', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.',
        TRUE, 'Keeling LLC', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.',
        TRUE, 'Kerluke Group', 5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.', FALSE,
        'Block-Cronin', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi.

Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque.

Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus.', FALSE,
        'Ebert-Schaden', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.',
        TRUE, 'Bogisich, Connelly and Tillman', 2, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.',
        FALSE, 'Trantow-Mann', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum.

In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo.',
        TRUE, 'Brown-McGlynn', 5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In congue. Etiam justo. Etiam pretium iaculis justo.', TRUE, 'Stoltenberg-Romaguera', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.

Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.',
        FALSE, 'Runte, Heathcote and Waelchi', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.

Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.',
        FALSE, 'Yundt-Kris', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.

Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst.

Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', TRUE, 'Walker-Barton',
        6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui.

Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.

Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.', FALSE,
        'Huel, Weissnat and Boehm', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.

Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.

Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.',
        TRUE, 'Leuschke Inc', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.', FALSE,
        'Feeney, Langosh and Schaefer', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum.',
        FALSE, 'Hilll and Sons', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', FALSE,
        'Lubowitz-Simonis', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus.',
        TRUE, 'Bayer Group', 5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum.

In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo.

Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.',
        TRUE, 'Conroy-Metz', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.

Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.',
        TRUE, 'Klein, Jacobs and Larkin', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst.

Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', TRUE,
        'Kub, Koch and Boyle', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum.

Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.',
        TRUE, 'Heathcote-Hand', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.', FALSE,
        'Roberts and Sons', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.

Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.

Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus.', TRUE, 'Hills and Sons', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.', TRUE,
        'Klein, Bogisich and Schmeler', 2, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.

Sed ante. Vivamus tortor. Duis mattis egestas metus.

Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.',
        TRUE, 'Osinski and Sons', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', FALSE,
        'Pollich, Kihn and Zboncak', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.

Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.

Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.', TRUE,
        'Bechtelar LLC', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.

In congue. Etiam justo. Etiam pretium iaculis justo.

In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.', TRUE, 'Crooks-Kessler', 5, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.

Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.

Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.',
        FALSE, 'Crona Inc', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.',
        FALSE, 'Emmerich-Schuster', 4, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus.',
        FALSE, 'Harber-Hansen', 6, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.',
        FALSE, 'Kuhlman, Pfannerstill and Feeney', 1, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.',
        FALSE, 'Monahan, Bahringer and Schowalter', 3, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.

Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.

Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.',
        FALSE, 'Herzog-Lang', 2, 3);
INSERT INTO exam_tasks (description, is_complete, name, department_id, user_id)
VALUES ('Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem.

Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.

Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus.', FALSE, 'Price, Hudson and Von',
        4, 3);
