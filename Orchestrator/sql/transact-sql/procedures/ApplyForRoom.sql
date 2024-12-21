CREATE PROCEDURE ApplyForRoom
    @room_id INT,
    @user_id NVARCHAR(50)
AS
BEGIN
    INSERT INTO applications (room_id, user_id, "status") -- needs to be in quotes, otherwise sql treats it as a keyword
    VALUES (@room_id, @user_id, 'pending');
END;