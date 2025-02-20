CREATE OR ALTER PROCEDURE sp_apply_for_room
    @room_id INT,
    @user_id NVARCHAR(50)
AS
BEGIN
    INSERT INTO applications (room_id, user_id, "status", application_date) -- needs to be in quotes, otherwise sql treats it as a keyword
    VALUES (@room_id, @user_id, 'pending', GETDATE());
END;