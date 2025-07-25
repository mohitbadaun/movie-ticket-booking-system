-- Drop all tables if exist in proper dependency order
DROP TABLE IF EXISTS payments, booking_seats, bookings, show_seats, seats, shows, screens, cinemas, movies, users, languages, cities CASCADE;

-- 1. City Table
CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    country VARCHAR(100) DEFAULT 'India'
);

-- 2. Language Table
CREATE TABLE languages (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- 3. User Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Cinema Table
CREATE TABLE cinemas (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT,
    city_id INT REFERENCES cities(id) ON DELETE CASCADE
);

-- 5. Screen Table
CREATE TABLE screens (
    id SERIAL PRIMARY KEY,
    cinema_id INT REFERENCES cinemas(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    seat_layout JSONB NOT NULL  -- layout structure in JSON
);

-- 6. Seat Table (static seat info per screen)
CREATE TABLE seats (
    id SERIAL PRIMARY KEY,
    screen_id INT REFERENCES screens(id) ON DELETE CASCADE,
    seat_number VARCHAR(10) NOT NULL, -- e.g. A1, B5
    seat_type VARCHAR(50) NOT NULL DEFAULT 'Regular',
    base_price DECIMAL(10,2) NOT NULL
);

-- 7. Movie Table
CREATE TABLE movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    language_id INT REFERENCES languages(id) ON DELETE SET NULL,
    duration_minutes INT,
    genre VARCHAR(100),
    rating DECIMAL(2,1),
    release_date DATE
);

-- 8. Show Table
CREATE TABLE shows (
    id SERIAL PRIMARY KEY,
    movie_id INT REFERENCES movies(id) ON DELETE CASCADE,
    screen_id INT REFERENCES screens(id) ON DELETE CASCADE,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL
);

-- 9. Show Seat Table (per show, seat status)
CREATE TABLE show_seats (
    id SERIAL PRIMARY KEY,
    show_id INT REFERENCES shows(id) ON DELETE CASCADE,
    seat_id INT REFERENCES seats(id) ON DELETE CASCADE,
    is_booked BOOLEAN DEFAULT FALSE,
    final_price DECIMAL(10,2) NOT NULL
);

-- 10. Booking Table
CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    show_id INT REFERENCES shows(id) ON DELETE CASCADE,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'PENDING', -- PENDING, CONFIRMED, CANCELLED
    total_amount DECIMAL(10,2)
);

-- 11. Booking Seats Table
CREATE TABLE booking_seats (
    id SERIAL PRIMARY KEY,
    booking_id INT REFERENCES bookings(id) ON DELETE CASCADE,
    show_seat_id INT REFERENCES show_seats(id) ON DELETE CASCADE
);

-- 12. Payments Table
CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    booking_id INT REFERENCES bookings(id) ON DELETE CASCADE,
    payment_reference VARCHAR(255), -- from Razorpay/Stripe etc.
    status VARCHAR(50) DEFAULT 'INITIATED', -- INITIATED, SUCCESS, FAILED
    payment_time TIMESTAMP
);



-- Sample Seed Data

-- Insert Cities
INSERT INTO cities (name, state) VALUES ('Delhi', 'Delhi'), ('Mumbai', 'Maharashtra');

-- Insert Languages
INSERT INTO languages (name) VALUES ('English'), ('Hindi'), ('Tamil');

-- Insert Sample User
INSERT INTO users (name, email, phone) VALUES ('John Doe', 'john@example.com', '9999999999');

