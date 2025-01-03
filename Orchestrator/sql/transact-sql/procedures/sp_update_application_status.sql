CREATE OR ALTER PROCEDURE sp_update_application_status
    @application_ref UNIQUEIDENTIFIER,
    @status NVARCHAR(20),
    @user_id NVARCHAR(50)
AS
BEGIN
    UPDATE
        applications
    SET
        status = @status
    WHERE
        application_ref = @application_ref AND
        user_id = @user_id;
END