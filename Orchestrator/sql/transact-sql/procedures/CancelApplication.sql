CREATE OR ALTER PROCEDURE CancelApplication
    @application_ref UNIQUEIDENTIFIER
AS
BEGIN
    UPDATE
        applications
    SET
        status = 'cancelled'
    WHERE
        application_ref = @application_ref;
END