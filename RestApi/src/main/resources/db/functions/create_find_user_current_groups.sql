CREATE OR REPLACE FUNCTION find_user_current_groups(user_uuid UUID)
RETURNS SETOF groups
AS $$
BEGIN
RETURN QUERY
SELECT g.*
FROM accounts a
         JOIN groups_participants gp ON gp.account_id = a.id
         JOIN groups g ON g.id = gp.group_id
WHERE a.uuid = user_uuid
  AND gp.left_at IS NULL;
END;
$$ LANGUAGE plpgsql;