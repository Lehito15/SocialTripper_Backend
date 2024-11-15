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