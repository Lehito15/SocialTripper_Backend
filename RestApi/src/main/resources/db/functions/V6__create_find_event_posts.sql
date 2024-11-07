CREATE OR REPLACE FUNCTION find_event_posts(event_uuid UUID)
RETURNS SETOF posts AS $$
BEGIN
RETURN QUERY
SELECT p.*
FROM (SELECT id FROM events WHERE uuid = event_uuid) AS e
         LEFT JOIN events_posts ep ON e.id = ep.event_id
         LEFT JOIN posts p ON ep.post_id = p.id;
END;
$$ LANGUAGE plpgsql;