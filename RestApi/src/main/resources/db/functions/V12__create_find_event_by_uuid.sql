CREATE OR REPLACE FUNCTION find_event_by_uuid(event_uuid UUID)
RETURNS events AS $$
DECLARE
result events;
BEGIN
SELECT *
INTO result
FROM events
WHERE uuid = event_uuid
    LIMIT 1;
RETURN result;
END;
$$ LANGUAGE plpgsql;