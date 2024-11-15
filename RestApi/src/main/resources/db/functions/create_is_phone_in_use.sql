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