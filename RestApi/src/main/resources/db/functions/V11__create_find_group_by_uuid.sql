CREATE OR REPLACE FUNCTION find_group_by_uuid(group_uuid UUID)
RETURNS groups AS $$
DECLARE
result groups;
BEGIN
SELECT *
INTO result
FROM groups
WHERE uuid = group_uuid
    LIMIT 1;
RETURN result;
END;
$$ LANGUAGE plpgsql;