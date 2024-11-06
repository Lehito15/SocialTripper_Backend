CREATE OR REPLACE FUNCTION expire_user_posts(user_uuid UUID)
RETURNS INTEGER AS $$
DECLARE
    rows_affected INTEGER;
BEGIN
    UPDATE posts
    SET is_expired = TRUE
    WHERE user_uuid = publisher_uuid AND is_expired = FALSE;
    GET DIAGNOSTICS rows_affected = ROW_COUNT;
    RETURN COUNT(rows_affected);
END;
$$ LANGUAGE plpgsql;