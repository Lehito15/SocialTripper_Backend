CREATE OR REPLACE FUNCTION expire_post(post_uuid UUID)
RETURNS BOOLEAN AS $$
DECLARE
    rows_affected RECORD;
BEGIN
    UPDATE posts
    SET is_expired = TRUE
    WHERE post_uuid = uuid
    RETURNING * INTO rows_affected;
    RETURN COUNT(rows_affected) > 0;
END;
$$ LANGUAGE plpgsql;