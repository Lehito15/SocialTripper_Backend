CREATE OR REPLACE PROCEDURE userLeftEvent(user_uuid UUID, event_uuid UUID)
LANGUAGE plpgsql
AS $$
BEGIN
UPDATE events_participants
SET left_at = CURRENT_TIMESTAMP
WHERE event_id = (SELECT e.id from events e where e.uuid = event_uuid) AND
    account_id = (SELECT a.id from accounts a where a.uuid = user_uuid);
END;
$$;