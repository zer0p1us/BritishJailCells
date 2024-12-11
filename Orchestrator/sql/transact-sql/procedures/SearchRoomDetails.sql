CREATE OR ALTER PROCEDURE SearchRoomDetails
    @SearchTerms NVARCHAR(MAX) = NULL,
    @Furnished BIT = NULL,
    @LiveInLandlord BIT = NULL,
    @SharedWith BIT = NULL,
    @BillsIncluded BIT = NULL,
    @BathroomShared BIT = NULL,
    @MaxPricePerMonth INT = NULL
AS
BEGIN
    DECLARE @SQL NVARCHAR(MAX); -- Query
    DECLARE @Term NVARCHAR(255);
    DECLARE @WhereClause NVARCHAR(MAX) = '';
    DECLARE @Columns NVARCHAR(MAX) = 'room_id, name, city, county, postcode, furnished, live_in_landlord, shared_with, bills_included, bathroom_shared, price_per_month_gbp, availability_date, amenities, languages';
    DECLARE @ColumnsList NVARCHAR(MAX);
    DECLARE @NextTerm NVARCHAR(255);

    -- Initial query
    SET @SQL = 'SELECT ' + @Columns + ' FROM vw_room_details WHERE 1=1';

    -- Add boolean conditions for true and false checks if params have been set
    IF @Furnished IS NOT NULL
    BEGIN
        SET @SQL = @SQL + ' AND furnished = ' + CAST(@Furnished AS NVARCHAR);
    END
    IF @LiveInLandlord IS NOT NULL
    BEGIN
        SET @SQL = @SQL + ' AND live_in_landlord = ' + CAST(@LiveInLandlord AS NVARCHAR);
    END
    IF @SharedWith IS NOT NULL
    BEGIN
        SET @SQL = @SQL + ' AND shared_with = ' + CAST(@SharedWith AS NVARCHAR);
    END
    IF @BillsIncluded IS NOT NULL
    BEGIN
        SET @SQL = @SQL + ' AND bills_included = ' + CAST(@BillsIncluded AS NVARCHAR);
    END
    IF @BathroomShared IS NOT NULL
    BEGIN
        SET @SQL = @SQL + ' AND bathroom_shared = ' + CAST(@BathroomShared AS NVARCHAR);
    END

    -- Add price condition 
    IF @MaxPricePerMonth IS NOT NULL 
    BEGIN
        SET @SQL = @SQL + ' AND price_per_month_gbp <= ' + CAST(@MaxPricePerMonth AS NVARCHAR); 
    END

    IF @SearchTerms IS NOT NULL
    BEGIN
        -- Process search terms
        WHILE LEN(@SearchTerms) > 0
        BEGIN
            IF CHARINDEX(',', @SearchTerms) > 0
            BEGIN
                SET @NextTerm = LEFT(@SearchTerms, CHARINDEX(',', @SearchTerms) - 1);
                SET @SearchTerms = RIGHT(@SearchTerms, LEN(@SearchTerms) - CHARINDEX(',', @SearchTerms));
            END
            ELSE
            BEGIN
                SET @NextTerm = @SearchTerms;
                SET @SearchTerms = '';
            END
            
            SET @WhereClause = @WhereClause + ' AND (';
            SET @ColumnsList = @Columns;
            
            WHILE LEN(@ColumnsList) > 0
            BEGIN
                IF CHARINDEX(',', @ColumnsList) > 0
                BEGIN
                    SET @Term = LEFT(@ColumnsList, CHARINDEX(',', @ColumnsList) - 1);
                    SET @ColumnsList = RIGHT(@ColumnsList, LEN(@ColumnsList) - CHARINDEX(',', @ColumnsList));
                END
                ELSE
                BEGIN
                    SET @Term = @ColumnsList;
                    SET @ColumnsList = '';
                END
                
                SET @WhereClause = @WhereClause + @Term + ' LIKE ''%' + @NextTerm + '%'' OR ';
            END
            
            -- Remove the last ' OR '
            SET @WhereClause = LEFT(@WhereClause, LEN(@WhereClause) - 3) + ')';
        END
    END

    SET @SQL = @SQL + @WhereClause;

    EXEC sp_executesql @SQL;
END
