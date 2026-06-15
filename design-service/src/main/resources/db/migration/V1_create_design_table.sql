CREATE TABLE designs(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_email VARCHAR(100) NOT NULL ,
    location VARCHAR(100) NOT NULL ,
    area decimal(10,2) NOT NULL ,
    created_at TIMESTAMP default now(),
    updated_at TIMESTAMP
);