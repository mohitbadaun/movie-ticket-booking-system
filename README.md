## Movie Ticket Booking System

### Functional Requirements


###  Partner Module
- Partner Onboarding  
- Add Cinemas and Auditoriums  
- Define seating arrangements per auditorium  
- Add/Update movie shows for specific dates and time slots  
- Set pricing and seat categories (Gold, Silver, Premium, etc.)


###  Booking Module
- Search shows by:
  - Movie name
  - City
  - Actor/Actress
  - Date
  - Language
  - Genre
- View show details (timing, cinema, available seats, pricing)
- Select seats and book tickets
- View booking history (past and upcoming bookings)
- Notification system (email, SMS, WhatsApp)


###  Out of Scope (for current version)
- Seat Map
- Promo codes, offers, and discount handling  
- Add-on purchases (food, beverages, merchandise)  
- Modify or cancel tickets after booking


### Non Functional Requirements:
- High availability (99.9% uptime)
- Horizontal scalability
- Strong consistency for seat booking and payments
- Modular and maintainable code (follows SOLID, unit tests)
- Localization support (multi-language, timezone)

--
### Entitites:
1. User
2. Movie
3. Cinema
4. Show
5. Seat
6. Booking/Ticket
7. Payment

#### APIs

##### Browse APIs

<pre><em>GET api/movies
  {
  City,
  Language,
  Cinema,
  Time,
  Genre
}</em></pre>


<pre><em>GET api/cinema
  {
  City,
  movie
}</em></pre>

<pre><em>
GET /api/cinemas/{cinemaId}/shows?movieId=123&date=2025-07-08
</em></pre>

##### Booking APIs

<pre><em>
POST /api/bookings
  {
  "showId": "s_123",
  "seatIds": ["A1", "A2"]
}

Returns : booking_id, invoice
</em></pre>

<pre><em>
POST /api/bookings/{bookingId}/pay
POST /api/bookings/{bookingId}/verify-payment
</em></pre>

<pre><em>
GET /api/bookings/{bookingId}/status
GET /api/users/{userId}/bookings
</em></pre>

<pre><em>
Movies
GET /movies
→ List all movies (with optional filters: language, genre, rating, city)

GET /movies/{id}
→ Get detailed info of a movie by ID

GET /movies/{id}/shows
→ Get all upcoming shows for a movie (with optional filter: city, date)

</em></pre>

<pre><em>
Cinemas
GET /cinemas
→ List all cinemas (with optional filter: city)

GET /cinemas/{id}
→ Get details of a cinema by ID

</em></pre>

<pre><em>
Shows
GET /shows
→ List shows (optional filters: movie_id, cinema_id, date, city)

GET /shows/{id}
→ Get show details by show ID
</em></pre>

<pre><em>
</em></pre>

<pre><em>
</em></pre>


<pre><em>
</em></pre>
