CREATE TABLE reviews(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    design_id UUID UNIQUE NOT NULL,
    officer_email VARCHAR(100),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING','APPROVED','REJECTED')),
    comments VARCHAR(255),
    reviewed_at TIMESTAMP,
    FOREIGN KEY (design_id) REFERENCES designs(id)

);