#projectionregistered users
insert into registered_user(name, lastname, username, password, email, type, address, telephone_number, registration_confirmed, id, avatar_url) values ('Petar', 'Peric', 'pera', '123', 'pera@gmail.com', 'RegisteredUser', "Kikinda, Random ulica 2", "12312312", true, 151, 'url');
insert into theater_admin_user(name, lastname, username, password, email, type, registration_confirmed, id) values ('Vladimir', 'Antonic', 'vlada', '1234', 'vlada@gmail.com', 'TheaterAndCinemaAdmin', true, 666);

#cinemas/theatres
insert into theatre(name, description, address, city, type, theatre_id, avatar_url, rate, owner_id) values ("Arena Cineplex", "Dosta dobar bioskop.", "Ko ce ga znati", "Novi Sad", "Cinema", 132, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.mojnovisad.com%2Ffiles%2Fevent%2F0%2F4%2F9%2F22049%2F22049-arena-cineplex1.jpg&f=1", 2.5,666);
insert into theatre(name, description, address, city, type, theatre_id, avatar_url, rate, owner_id) values ("CineStar", "Ovo je fensi bioskop", "Tamo neka", "Novi Sad", "Cinema", 133, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.mojnovisad.com%2Ffiles%2Fevent%2F0%2F4%2F9%2F22049%2F22049-arena-cineplex1.jpg&f=1",4.5,666);
insert into theatre(name, description, address, city, type, theatre_id, avatar_url, rate, owner_id) values ("Amfiteatar", "Ovo je fensi teatar", "Tamo neka druga", "Novi Sad", "Theatre", 134, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.mojnovisad.com%2Ffiles%2Fevent%2F0%2F4%2F9%2F22049%2F22049-arena-cineplex1.jpg&f=1",3.5,665);

#movies, plays
insert into `show`(title, genre, duration, price, average_rating, number_of_rates, show_id, theatre_theatre_id, posterurl) values ("Deadpool", "Comedy/Action", "110", 350, 4.1, 0, 231, 132, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fcdn.traileraddict.com%2Fcontent%2F20th-century-fox%2Fdeadpool-poster-9.jpg&f=1");
insert into `show`(title, genre, duration, price, average_rating, number_of_rates, show_id, theatre_theatre_id, posterurl) values ("Blade Runner 2049", "SciFi/Drama", "130", 350, 4.5, 10, 232, 132, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fs3.birthmoviesdeath.com%2Fimages%2Fmade%2FBR2049_Key_Art_(US)_-_8.24_1200_1851_81_s.jpg&f=1");
insert into `show`(title, genre, duration, price, average_rating, number_of_rates, show_id, theatre_theatre_id, posterurl) values ("Good Will Hunting", "Drama", "120", 250, 4.9, 2343, 233, 132, "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.QrpVypYWF-FUw7f0hqdrrQHaK9%26pid%3D15.1&f=1");


#projections
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id) values(10, 350,"08/05/2018 20:00", 5, 231);
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id) values(11, 450,"09/05/2018 20:00", 5, 231);
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id) values(12, 400,"10/05/2018 20:00", 6, 231);
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id) values(13, 250,"08/05/2018 20:00", 7, 231);
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id) values(14, 350,"08/05/2018 20:00", 5, 231);

#halls
insert into hall (hall_id, name, theatre_theatre_id) values (5, 1, 132);
insert into hall (hall_id, name, theatre_theatre_id) values (6, 6, 132);
insert into hall (hall_id, name, theatre_theatre_id) values (7, 7, 132);

#seats
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1000, 1, 1, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1001, 2, 1, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1002, 3, 1, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1003, 1, 2, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1004, 2, 2, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1005, 3, 2, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1006, 4, 2, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1007, 1, 3, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1008, 2, 3, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1009, 3, 3, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1010, 4, 3, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1011, 1, 4, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1012, 2, 4, 5, true);
insert into seat (seat_id, chair_number, chair_row, hall_hall_id, available) values(1013, 3, 4, 5, true);



