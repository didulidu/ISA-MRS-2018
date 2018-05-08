#registered users
insert into registered_user(name, lastname, username, password, email, type, address, telephone_number, registration_confirmed, id, avatar_url) values ('Petar', 'Peric', 'pera', '123', 'pera@gmail.com', 'RegisteredUser', "Kikinda, Random ulica 2", "12312312", true, 151, 'url');

#cinemas/theatres
insert into theatre(name, description, address, city, type, theatre_id, avatar_url) values ("Arena Cineplex", "Dosta dobar bioskop.", "Ko ce ga znati", "Novi Sad", "Cinema", 132, "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.mojnovisad.com%2Ffiles%2Fevent%2F0%2F4%2F9%2F22049%2F22049-arena-cineplex1.jpg&f=1");

#movies, plays
insert into `show`(title, genre, duration, price, average_rating, number_of_rates, show_id, theatre_theatre_id) values ("Deadpool", "Comedy/Action", "210", 350, 4.1, 0, 231, 132);

#projections
insert into projection (projection_id,price,`date`,hall_hall_id, show_show_id) values(10, 350,"8/5/2018 20:00", 5, 231);

#halls
insert into hall (hall_id, name, theatre_theatre_id) values (5, 1, 132);