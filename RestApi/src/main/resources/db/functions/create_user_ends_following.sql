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