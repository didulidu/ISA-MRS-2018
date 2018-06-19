#registered users
insert into registered_user(name, lastname, username, password, email, type, address, telephone_number, registration_confirmed, id, avatar_url) values ('Petar', 'Peric', 'pera', '123', 'pera@gmail.com', 'RegisteredUser', "Kikinda, Random ulica 2", "12312312", true, 151, 'url');
insert into registered_user(name, lastname, username, password, email, type, address, telephone_number, registration_confirmed, id, avatar_url) values ('Vladimir', 'Antonic', 'vlada1', '321', 'vlada@gmail.com', 'RegisteredUser', "Adice, Random ulica 3", "12312312", true, 152, 'url');
insert into registered_user(name, lastname, username, password, email, type, address, telephone_number, registration_confirmed, id, avatar_url) values ('Marko', 'Balenovic', 'balenko', '111', 'balenko@gmail.com', 'RegisteredUser', "Novi Sad, Random ulica 4", "12312312", true, 153, 'url');

#theatre admins
insert into theater_admin_user(name, lastname, username, password, email, type, registration_confirmed, id) values ('Vladimir', 'Antonic', 'vlada', '1234', 'vlada@gmail.com', 'TheaterAndCinemaAdmin', true, 666);


#cinemas/theatres
insert into theatre(name, description, address, city, type, theatre_id, avatar_url, rate, owner_id) values ("Arena Cineplex", "Dosta dobar bioskop.", "Ko ce ga znati", "Novi Sad", "Cinema", 132, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.mojnovisad.com%2Ffiles%2Fevent%2F0%2F4%2F9%2F22049%2F22049-arena-cineplex1.jpg&f=1", 2.5,666);
insert into theatre(name, description, address, city, type, theatre_id, avatar_url, rate, owner_id) values ("CineStar", "Ovo je fensi bioskop", "Tamo neka", "Novi Sad", "Cinema", 133, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.mojnovisad.com%2Ffiles%2Fevent%2F0%2F4%2F9%2F22049%2F22049-arena-cineplex1.jpg&f=1",4.5,666);
insert into theatre(name, description, address, city, type, theatre_id, avatar_url, rate, owner_id) values ("Amfiteatar", "Ovo je fensi teatar", "Tamo neka druga", "Novi Sad", "Theatre", 134, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.mojnovisad.com%2Ffiles%2Fevent%2F0%2F4%2F9%2F22049%2F22049-arena-cineplex1.jpg&f=1",3.5,665);

#movies, plays
insert into `show`(title, genre, duration, average_rating, number_of_rates, show_id, theatre_theatre_id, posterurl, exist) values ("Deadpool", "Comedy/Action", "110", 4.1, 0, 231, 132, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fcdn.traileraddict.com%2Fcontent%2F20th-century-fox%2Fdeadpool-poster-9.jpg&f=1", true);
insert into `show`(title, genre, duration, average_rating, number_of_rates, show_id, theatre_theatre_id, posterurl, exist) values ("Blade Runner 2049", "SciFi/Drama", "130",  4.5, 10, 232, 132, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fs3.birthmoviesdeath.com%2Fimages%2Fmade%2FBR2049_Key_Art_(US)_-_8.24_1200_1851_81_s.jpg&f=1", true);
insert into `show`(title, genre, duration, average_rating, number_of_rates, show_id, theatre_theatre_id, posterurl, exist) values ("Good Will Hunting", "Drama", "120", 4.9, 2343, 233, 132, "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.QrpVypYWF-FUw7f0hqdrrQHaK9%26pid%3D15.1&f=1", true);


#projections
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id, exist) values(10, 350,"08/05/2018 20:00", 5, 231,true);
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id, exist) values(11, 450,"09/05/2018 20:00", 5, 231,true);
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id, exist) values(12, 400,"10/05/2018 20:00", 6, 231,true);
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id, exist) values(13, 250,"08/05/2018 20:00", 7, 231,true);
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id, exist) values(14, 350,"08/05/2018 20:00", 5, 231,true);
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id, exist) values(15, 300,"10/10/2018 19:00", 6, 233,true);


#halls
insert into hall (hall_id, name, theatre_theatre_id) values (5, 1, 132);
insert into hall (hall_id, name, theatre_theatre_id) values (6, 6, 132);
insert into hall (hall_id, name, theatre_theatre_id) values (7, 7, 132);

#seats
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1000, 1, 1, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1001, 2, 1, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1002, 3, 1, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1003, 1, 2, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1004, 2, 2, 5 );
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1005, 3, 2, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1006, 4, 2, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1007, 1, 3, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1008, 2, 3, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1009, 3, 3, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1010, 4, 3, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1011, 1, 4, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1012, 2, 4, 5);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1013, 3, 4, 5);

insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1014, 1, 1, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1015, 2, 1, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1016, 1, 2, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1017, 2, 2, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1018, 3, 2, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1019, 4, 2, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1020, 5, 2, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1021, 1, 3, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1022, 2, 3, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1023, 3, 3, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1024, 1, 4, 6);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id) values(1025, 2, 4, 6);





