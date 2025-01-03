-- Location Table
CREATE TABLE locations (
    location_id INT PRIMARY KEY IDENTITY(1,1),
    city NVARCHAR(100) NOT NULL,
    county NVARCHAR(100) NOT NULL,
    postcode NVARCHAR(20) NOT NULL
);

-- Room Details Table
CREATE TABLE room_details (
    details_id INT PRIMARY KEY IDENTITY(1,1),
    furnished BIT NOT NULL,
    live_in_landlord BIT NOT NULL,
    shared_with INT NOT NULL,
    bills_included BIT NOT NULL,
    bathroom_shared BIT NOT NULL
);

-- Amenities Table (Many-to-Many relationship with rooms)
CREATE TABLE amenities (
    amenity_id INT PRIMARY KEY IDENTITY(1,1),
    amenity_name NVARCHAR(50) NOT NULL UNIQUE
);

-- Languages Table (Many-to-Many relationship with rooms)
CREATE TABLE languages (
    language_id INT PRIMARY KEY IDENTITY(1,1),
    language_name NVARCHAR(50) NOT NULL UNIQUE
);

-- Rooms Table (Main table)
CREATE TABLE rooms (
    room_id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(200) NOT NULL,
    location_id INT FOREIGN KEY REFERENCES locations(location_id),
    details_id INT FOREIGN KEY REFERENCES room_details(details_id),
    price_per_month_gbp DECIMAL(10, 2) NOT NULL,
    availability_date DATE NOT NULL
);

-- Room-Amenities Junction Table
CREATE TABLE room_amenities (
    room_id INT FOREIGN KEY REFERENCES rooms(room_id),
    amenity_id INT FOREIGN KEY REFERENCES amenities(amenity_id),
    PRIMARY KEY (room_id, amenity_id)
);

-- Room-Languages Junction Table
CREATE TABLE room_languages (
    room_id INT FOREIGN KEY REFERENCES rooms(room_id),
    language_id INT FOREIGN KEY REFERENCES languages(language_id),
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

-- Room applications
CREATE TABLE applications (
    application_ref UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
    room_id INT NOT NULL,
    user_id NVARCHAR(50) NOT NULL,
    status NVARCHAR(20) NOT NULL,
    application_date DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);


