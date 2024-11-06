CREATE OR REPLACE FUNCTION find_post_by_uuid(post_uuid UUID)
RETURNS posts AS $$
DECLARE
post record;
BEGIN
SELECT *
INTO post
FROM posts p
WHERE p.uuid = post_uuid;
RETURN post;
END;
$$ LANGUAGE plpgsql;