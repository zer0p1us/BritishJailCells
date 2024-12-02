-- Insert Original Locations
INSERT INTO locations (city, county, postcode) VALUES
('Nottingham', 'Nottinghamshire', 'NG1 5AA'),
('London', 'Greater London', 'E1 6AN'),
('Manchester', 'Greater Manchester', 'M1 2AB'),
('Edinburgh', 'Midlothian', 'EH1 1BB'),
('Bristol', 'Bristol', 'BS1 5TR'),
-- Additional Locations
('Leeds', 'West Yorkshire', 'LS1 3AD'),
('Birmingham', 'West Midlands', 'B1 1TB'),
('Glasgow', 'Glasgow City', 'G1 2FF'),
('Cardiff', 'Cardiff', 'CF10 1DD'),
('Sheffield', 'South Yorkshire', 'S1 2BJ'),
('Newcastle', 'Tyne and Wear', 'NE1 7RU'),
('Liverpool', 'Merseyside', 'L1 8JQ');

-- Insert Original Room Details
INSERT INTO room_details (furnished, live_in_landlord, shared_with, bills_included, bathroom_shared) VALUES
(1, 0, 3, 1, 1),   -- Nottingham
(1, 0, 2, 0, 0),   -- London
(1, 1, 4, 1, 1),   -- Manchester
(1, 0, 1, 0, 0),   -- Edinburgh
(0, 1, 2, 1, 1),   -- Bristol
-- Additional Room Details
(1, 0, 2, 1, 0),   -- Leeds
(1, 1, 3, 1, 1),   -- Birmingham
(0, 0, 1, 0, 0),   -- Glasgow
(1, 0, 4, 1, 1),   -- Cardiff
(1, 1, 2, 1, 0),   -- Sheffield
(0, 0, 3, 0, 1),   -- Newcastle
(1, 0, 2, 1, 0);   -- Liverpool

-- Insert Original Rooms
INSERT INTO rooms (name, location_id, details_id, price_per_month_gbp, availability_date) VALUES
('Cozy Room in Shared House', 1, 1, 500, '2024-07-01'),
('Spacious Room in Modern Apartment', 2, 2, 850, '2024-08-15'),
('Affordable Room in Friendly House', 3, 3, 450, '2024-06-01'),
('Luxury Room in Central Location', 4, 4, 950, '2024-09-01'),
('Quiet Room in Suburban House', 5, 5, 600, '2024-05-15'),
-- Additional Rooms
('Modern En-suite in Professional House', 6, 6, 650, '2024-07-15'),
('Charming Room in Victorian House', 7, 7, 575, '2024-08-01'),
('Studio Room with Private Kitchen', 8, 8, 750, '2024-06-15'),
('Large Double in Student Area', 9, 9, 495, '2024-09-15'),
('Bright Room with City Views', 10, 10, 625, '2024-07-30'),
('Cozy Single in Friendly Flatshare', 11, 11, 480, '2024-08-20'),
('Modern Room in City Centre', 12, 12, 700, '2024-06-30');

-- Insert Original Room-Amenities Relationships
INSERT INTO room_amenities (room_id, amenity_id) VALUES
-- Nottingham room
(1, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(1, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Heating')),
(1, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Kitchen')),
-- London room
(2, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(2, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Gym')),
(2, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Laundry')),
-- Manchester room
(3, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(3, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Heating')),
(3, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Garden')),
-- Edinburgh room
(4, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(4, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Heating')),
(4, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Parking')),
-- Bristol room
(5, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(5, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Heating')),
(5, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Parking')),
-- Additional rooms amenities
(6, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(6, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Heating')),
(6, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Kitchen')),
(7, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(7, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Garden')),
(8, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(8, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Kitchen')),
(8, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Parking')),
(9, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(9, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Laundry')),
(10, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(10, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Heating')),
(11, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(11, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Kitchen')),
(12, (SELECT amenity_id FROM amenities WHERE amenity_name = 'WiFi')),
(12, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Heating')),
(12, (SELECT amenity_id FROM amenities WHERE amenity_name = 'Laundry'));

-- Insert Original Room-Languages Relationships
INSERT INTO room_languages (room_id, language_id) VALUES
-- Nottingham room (English, Spanish)
(1, (SELECT language_id FROM languages WHERE language_name = 'English')),
(1, (SELECT language_id FROM languages WHERE language_name = 'Spanish')),
-- London room (English, French)
(2, (SELECT language_id FROM languages WHERE language_name = 'English')),
(2, (SELECT language_id FROM languages WHERE language_name = 'French')),
-- Manchester room (English, Mandarin)
(3, (SELECT language_id FROM languages WHERE language_name = 'English')),
(3, (SELECT language_id FROM languages WHERE language_name = 'Mandarin')),
-- Edinburgh room (English)
(4, (SELECT language_id FROM languages WHERE language_name = 'English')),
-- Bristol room (English, Italian)
(5, (SELECT language_id FROM languages WHERE language_name = 'English')),
(5, (SELECT language_id FROM languages WHERE language_name = 'Italian')),
-- Additional rooms languages
(6, (SELECT language_id FROM languages WHERE language_name = 'English')),
(6, (SELECT language_id FROM languages WHERE language_name = 'French')),
(7, (SELECT language_id FROM languages WHERE language_name = 'English')),
(7, (SELECT language_id FROM languages WHERE language_name = 'Spanish')),
(8, (SELECT language_id FROM languages WHERE language_name = 'English')),
(9, (SELECT language_id FROM languages WHERE language_name = 'English')),
(9, (SELECT language_id FROM languages WHERE language_name = 'Mandarin')),
(10, (SELECT language_id FROM languages WHERE language_name = 'English')),
(10, (SELECT language_id FROM languages WHERE language_name = 'Italian')),
(11, (SELECT language_id FROM languages WHERE language_name = 'English')),
(12, (SELECT language_id FROM languages WHERE language_name = 'English')),
(12, (SELECT language_id FROM languages WHERE language_name = 'French'));
