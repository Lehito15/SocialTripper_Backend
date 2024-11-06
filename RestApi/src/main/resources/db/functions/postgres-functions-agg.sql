CREATE INDEX posts_uuid_index ON posts USING btree (uuid);
CREATE INDEX groups_uuid_index ON groups USING btree (uuid);
CREATE INDEX events_uuid_index ON events USING btree (uuid);
CREATE INDEX multimedia_uuid_index ON multimedia USING btree (uuid);
CREATE INDEX accounts_uuid_index ON accounts USING btree (uuid);
CREATE INDEX relations_uuid_index ON relations USING btree (uuid);

CREATE OR REPLACE FUNCTION find_user_posts(user_uuid UUID)
RETURNS SETOF posts AS $$
BEGIN
RETURN QUERY
SELECT p.*
FROM (SELECT id FROM accounts WHERE uuid = user_uuid) AS a
         JOIN posts p ON a.id = p.publisher_id;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION find_personal_posts(user_uuid UUID)
RETURNS SETOF posts AS $$
BEGIN
RETURN QUERY
SELECT p.*
FROM (SELECT id FROM accounts WHERE uuid = user_uuid) AS a
         JOIN posts p ON a.id = p.publisher_id
         JOIN personal_posts pp ON p.id = pp.post_id;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION find_group_posts(group_uuid UUID)
RETURNS SETOF posts AS $$
BEGIN
RETURN QUERY
SELECT p.*
FROM (SELECT id FROM groups WHERE uuid = group_uuid) AS g
         JOIN groups_posts gp ON g.id = gp.group_id
         JOIN posts p ON gp.post_id = p.id;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION find_event_posts(event_uuid UUID)
RETURNS SETOF posts AS $$
BEGIN
RETURN QUERY
SELECT p.*
FROM (SELECT id FROM events WHERE uuid = event_uuid) AS e
         JOIN events_posts ep ON e.id = ep.event_id
         JOIN posts p ON ep.post_id = p.id;
END;
$$ LANGUAGE plpgsql;


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


CREATE OR REPLACE FUNCTION expire_user_posts(user_uuid UUID)
RETURNS INTEGER AS $$
DECLARE
rows_affected INTEGER;
BEGIN
UPDATE posts
SET is_expired = TRUE
WHERE user_uuid = publisher_uuid AND is_expired = FALSE;
GET DIAGNOSTICS rows_affected = ROW_COUNT;
RETURN COUNT(rows_affected);
END;
$$ LANGUAGE plpgsql;

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

CREATE OR REPLACE FUNCTION lock_user_posts(user_uuid UUID)
RETURNS INTEGER AS $$
DECLARE
rows_affected INTEGER;
BEGIN
UPDATE posts
SET is_locked = TRUE
WHERE user_uuid = publisher_uuid AND is_locked = FALSE;
GET DIAGNOSTICS rows_affected = ROW_COUNT;
RETURN rows_affected;
END;
$$ LANGUAGE plpgsql;

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