CREATE OR ALTER VIEW vw_room_details AS 
SELECT 
    r.room_id,
    r.name,
    l.city,
    l.county,
    l.postcode,
    rd.furnished,
    rd.live_in_landlord,
    rd.shared_with,
    rd.bills_included,
    rd.bathroom_shared,
    r.price_per_month_gbp,
    r.availability_date,
    STUFF((
        SELECT ', ' + a2.amenity_name
        FROM room_amenities ra2
        JOIN amenities a2 ON ra2.amenity_id = a2.amenity_id
        WHERE ra2.room_id = r.room_id
        FOR XML PATH('')
    ), 1, 2, '') AS amenities,
    STUFF((
        SELECT ', ' + lg2.language_name
        FROM room_languages rl2
        JOIN languages lg2 ON rl2.language_id = lg2.language_id
        WHERE rl2.room_id = r.room_id
        FOR XML PATH('')
    ), 1, 2, '') AS languages
FROM 
    rooms r 
    JOIN locations l ON r.location_id = l.location_id 
    JOIN room_details rd ON r.details_id = rd.details_id