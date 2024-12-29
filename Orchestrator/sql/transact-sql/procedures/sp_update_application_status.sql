CREATE OR ALTER PROCEDURE sp_update_application_status
    @application_ref UNIQUEIDENTIFIER,
    @status NVARCHAR(20)
AS
BEGIN
    UPDATE
        applications
    SET
        status = @status
    WHERE
        application_ref = @application_ref;
END