CREATE INDEX posts_uuid_index ON posts USING btree (uuid);
CREATE INDEX groups_uuid_index ON groups USING btree (uuid);
CREATE INDEX events_uuid_index ON events USING btree (uuid);
CREATE INDEX multimedia_uuid_index ON multimedia USING btree (uuid);
CREATE INDEX accounts_uuid_index ON accounts USING btree (uuid);
CREATE INDEX relations_uuid_index ON relations USING btree (uuid);