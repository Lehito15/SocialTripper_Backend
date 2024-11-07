CREATE OR REPLACE FUNCTION find_group_posts(group_uuid UUID)
RETURNS SETOF posts AS $$
BEGIN
    RETURN QUERY
    SELECT p.*
    FROM (SELECT id FROM groups WHERE uuid = group_uuid) AS g
    LEFT JOIN groups_posts gp ON g.id = gp.group_id
    LEFT JOIN posts p ON gp.post_id = p.id;
END;
$$ LANGUAGE plpgsql;