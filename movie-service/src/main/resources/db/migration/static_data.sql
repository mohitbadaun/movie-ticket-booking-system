-- Delete in order to avoid FK violations
DELETE FROM payments;
DELETE FROM booking_seats;
DELETE FROM bookings;
DELETE FROM show_seats;
DELETE FROM shows;
DELETE FROM seats;
DELETE FROM screens;
DELETE FROM cinemas;
DELETE FROM movies;
DELETE FROM users;
DELETE FROM languages;
DELETE FROM cities;


-- Insert static IDs (overriding default serials)
INSERT INTO cities (id, name, state) VALUES
(1, 'Delhi', 'Delhi'),
(2, 'Mumbai', 'Maharashtra'),
(3, 'Bangalore', 'Karnataka');

INSERT INTO languages (id, name) VALUES
(1, 'Hindi'),
(2, 'English'),
(3, 'Tamil'),
(4, 'Telugu');

INSERT INTO users (id, name, email, phone) VALUES
(1, 'Amit Sharma', 'amit.sharma@example.com', '9876543210'),
(2, 'Neha Verma', 'neha.verma@example.com', '9876501234'),
(3, 'Rahul Mehta', 'rahul.mehta@example.com', '9876512345'),
(4, 'Priya Singh', 'priya.singh@example.com', '9876523456');

INSERT INTO cinemas (id, name, address, city_id) VALUES
(1, 'PVR Saket', 'Saket, Delhi', 1),
(2, 'INOX Nehru Place', 'Nehru Place, Delhi', 1),
(3, 'PVR Phoenix', 'Lower Parel, Mumbai', 2),
(4, 'INOX Forum', 'Koramangala, Bangalore', 3);

INSERT INTO screens (id, cinema_id, name, seat_layout) VALUES
(1, 1, 'Screen 1', '{"rows":5,"cols":10}'),
(2, 1, 'Screen 2', '{"rows":4,"cols":8}'),
(3, 2, 'Screen 1', '{"rows":6,"cols":10}'),
(4, 3, 'Screen 1', '{"rows":5,"cols":10}'),
(5, 4, 'Screen 1', '{"rows":6,"cols":12}');

INSERT INTO seats (id, screen_id, seat_number, seat_type, base_price) VALUES
(1, 1, 'A1', 'VIP', 400), (2, 1, 'A2', 'VIP', 400), (3, 1, 'A3', 'Regular', 250), (4, 1, 'A4', 'Regular', 250),
(5, 1, 'A5', 'Regular', 250), (6, 1, 'A6', 'Regular', 250), (7, 1, 'A7', 'Regular', 250), (8, 1, 'A8', 'Regular', 250),
(9, 1, 'A9', 'Regular', 250), (10, 1, 'A10', 'Regular', 250);

INSERT INTO seats (id, screen_id, seat_number, seat_type, base_price) VALUES
(11, 2, 'B1', 'VIP', 400), (12, 2, 'B2', 'VIP', 400), (13, 2, 'B3', 'Regular', 250), (14, 2, 'B4', 'Regular', 250),
(15, 2, 'B5', 'Regular', 250), (16, 2, 'B6', 'Regular', 250), (17, 2, 'B7', 'Regular', 250), (18, 2, 'B8', 'Regular', 250),
(19, 2, 'B9', 'Regular', 250), (20, 2, 'B10', 'Regular', 250);

INSERT INTO movies (id, title, language_id, duration_minutes, genre, rating, release_date) VALUES
(1, 'Inception', 2, 148, 'Sci-Fi', 8.8, '2010-07-16'),
(2, 'Jawan', 1, 160, 'Action', 7.5, '2023-09-07'),
(3, 'RRR', 4, 180, 'Action', 8.5, '2022-03-25'),
(4, 'Avengers: Endgame', 2, 181, 'Action/Sci-Fi', 8.4, '2019-04-26'),
(5, '3 Idiots', 1, 170, 'Drama/Comedy', 8.4, '2009-12-25');

INSERT INTO shows (id, movie_id, screen_id, start_time, end_time) VALUES
(1, 1, 1, '2025-07-10 14:00:00', '2025-07-10 16:30:00'),
(2, 1, 1, '2025-07-10 18:00:00', '2025-07-10 20:30:00'),
(3, 1, 2, '2025-07-11 14:00:00', '2025-07-11 16:30:00'),
(4, 2, 1, '2025-07-10 10:00:00', '2025-07-10 12:30:00'),
(5, 2, 2, '2025-07-10 21:00:00', '2025-07-10 23:30:00');

INSERT INTO show_seats (show_id, seat_id, is_booked, final_price) VALUES
(1, 1, FALSE, 400), (1, 2, FALSE, 400), (1, 3, FALSE, 250), (1, 4, FALSE, 250),
(1, 5, FALSE, 250), (1, 6, FALSE, 250), (1, 7, FALSE, 250), (1, 8, FALSE, 250),
(1, 9, FALSE, 250), (1, 10, FALSE, 250);

INSERT INTO show_seats (show_id, seat_id, is_booked, final_price) VALUES
(2, 1, FALSE, 400), (2, 2, FALSE, 400), (2, 3, FALSE, 250), (2, 4, FALSE, 250),
(2, 5, FALSE, 250), (2, 6, FALSE, 250), (2, 7, FALSE, 250), (2, 8, FALSE, 250),
(2, 9, FALSE, 250), (2, 10, FALSE, 250);
