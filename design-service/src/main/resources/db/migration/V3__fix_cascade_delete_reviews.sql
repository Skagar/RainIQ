ALTER TABLE reviews DROP CONSTRAINT reviews_design_id_fkey;

ALTER TABLE reviews
    ADD CONSTRAINT reviews_design_id_fkey
        FOREIGN KEY (design_id) REFERENCES designs(id) ON DELETE CASCADE;