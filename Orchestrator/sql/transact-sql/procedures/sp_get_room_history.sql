CREATE OR ALTER PROCEDURE sp_get_room_history
    @room_id INT
AS
BEGIN
    SELECT 
        application_ref,
		room_id,
        user_id,
        status,
        application_date
    FROM 
        applications
    WHERE 
        room_id = @room_id
    ORDER BY
        application_date;
END