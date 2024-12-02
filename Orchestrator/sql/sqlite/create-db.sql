-- Location Table
CREATE TABLE locations (
    location_id INTEGER PRIMARY KEY AUTOINCREMENT,
    city VARCHAR(100) NOT NULL,
    county VARCHAR(100) NOT NULL,
    postcode VARCHAR(20) NOT NULL
);

-- Room Details Table
CREATE TABLE room_details (
    details_id INTEGER PRIMARY KEY AUTOINCREMENT,
    furnished BOOLEAN NOT NULL,
    live_in_landlord BOOLEAN NOT NULL,
    shared_with INTEGER NOT NULL,
    bills_included BOOLEAN NOT NULL,
    bathroom_shared BOOLEAN NOT NULL
);

-- Amenities Table (Many-to-Many relationship with rooms)
CREATE TABLE amenities (
    amenity_id INTEGER PRIMARY KEY AUTOINCREMENT,
    amenity_name VARCHAR(50) NOT NULL UNIQUE
);

-- Languages Table (Many-to-Many relationship with rooms)
CREATE TABLE languages (
    language_id INTEGER PRIMARY KEY AUTOINCREMENT,
    language_name VARCHAR(50) NOT NULL UNIQUE
);

-- Rooms Table (Main table)
CREATE TABLE rooms (
    room_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(200) NOT NULL,
    location_id INTEGER REFERENCES locations(location_id),
    details_id INTEGER REFERENCES room_details(details_id),
    price_per_month_gbp DECIMAL(10, 2) NOT NULL,
    availability_date DATE NOT NULL
);

-- Room-Amenities Junction Table
CREATE TABLE room_amenities (
    room_id INTEGER REFERENCES rooms(room_id),
    amenity_id INTEGER REFERENCES amenities(amenity_id),
    PRIMARY KEY (room_id, amenity_id)
);

-- Room-Languages Junction Table
CREATE TABLE room_languages (
    room_id INTEGER REFERENCES rooms(room_id),
    language_id INTEGER REFERENCES languages(language_id),
    PRIMARY KEY (room_id, language_id)
);

-- Sample Data Insertion for Amenities
INSERT INTO amenities (amenity_name) VALUES 
('WiFi'), ('Heating'), ('Kitchen'), 
('Gym'), ('Laundry'), ('Garden'), ('Parking');

-- Sample Data Insertion for Languages
INSERT INTO languages (language_name) VALUES 
('English'), ('Spanish'), ('French'), 
('Mandarin'), ('Italian');