ALTER TABLE designs ADD COLUMN property_id UUID NOT NULL;
ALTER TABLE designs DROP CONSTRAINT IF EXISTS uq_user_email_location;