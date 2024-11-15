CREATE OR REPLACE PROCEDURE userLeftGroup(user_uuid UUID, group_uuid UUID)
LANGUAGE plpgsql
AS $$
BEGIN
UPDATE groups_participants
SET left_at = CURRENT_TIMESTAMP
WHERE group_id = (SELECT g.id from groups g where g.uuid = group_uuid) AND
    account_id = (SELECT a.id from accounts a where a.uuid = user_uuid);
END;
$$;