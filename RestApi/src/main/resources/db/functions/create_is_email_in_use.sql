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