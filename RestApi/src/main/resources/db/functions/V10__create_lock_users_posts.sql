CREATE OR REPLACE FUNCTION lock_user_posts(user_uuid UUID)
RETURNS INTEGER AS $$
DECLARE
    rows_affected INTEGER;
BEGIN
    UPDATE posts
    SET is_locked = TRUE
    WHERE user_uuid = publisher_uuid AND is_locked = FALSE;
    GET DIAGNOSTICS rows_affected = ROW_COUNT;
    RETURN rows_affected;
END;
$$ LANGUAGE plpgsql;