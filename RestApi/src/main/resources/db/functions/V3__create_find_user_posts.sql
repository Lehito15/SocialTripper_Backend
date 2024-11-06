CREATE OR REPLACE FUNCTION find_user_posts(user_uuid UUID)
RETURNS SETOF posts AS $$
BEGIN
RETURN QUERY
SELECT p.*
FROM (SELECT id FROM accounts WHERE uuid = user_uuid) AS a
         LEFT JOIN posts p ON a.id = p.publisher_id;
END;
$$ LANGUAGE plpgsql;