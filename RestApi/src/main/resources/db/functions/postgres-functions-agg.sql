CREATE INDEX posts_uuid_index ON posts USING btree (uuid);
CREATE INDEX groups_uuid_index ON groups USING btree (uuid);
CREATE INDEX events_uuid_index ON events USING btree (uuid);
CREATE INDEX accounts_uuid_index ON accounts USING btree (uuid);

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


CREATE OR REPLACE FUNCTION does_username_exist(username_input TEXT)
RETURNS BOOLEAN AS $$
BEGIN
RETURN EXISTS (
    SELECT 1
    FROM accounts a
    WHERE a.nickname = username_input
);
END;
$$ LANGUAGE plpgsql;


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


CREATE OR REPLACE FUNCTION is_email_in_use(email_input TEXT)
RETURNS BOOLEAN AS $$
BEGIN
RETURN EXISTS (
    SELECT 1
    FROM accounts a
    WHERE a.email = email_input
);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION is_phone_in_use(phone_input TEXT)
RETURNS BOOLEAN AS $$
BEGIN
RETURN EXISTS (
    SELECT 1
    FROM accounts a
    WHERE a.phone = phone_input
);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE userEndsFollowing(follower_uuid UUID, followed_uuid UUID)
LANGUAGE plpgsql
AS $$
BEGIN
UPDATE follows
SET following_to = CURRENT_TIMESTAMP
WHERE follower_id = (SELECT a.id from accounts a where a.uuid = follower_uuid) AND
    followed_id = (SELECT a.id from accounts a where a.uuid = followed_uuid) AND
    following_to IS NULL;
END;
$$;


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