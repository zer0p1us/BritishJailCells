CREATE OR ALTER PROCEDURE UpdateApplicationStatus
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