CREATE TYPE user_role AS ENUM (
    'OWNER',
    'ARCHITECT',
    'MUNICIPAL_OFFICER',
    'INSPECTOR'
);
CREATE TABLE users(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL ,
    email VARCHAR(150) UNIQUE NOT NULL ,
    password VARCHAR(255),
    role user_role NOT NULL ,
    oauth_provider VARCHAR(50),
    created_at TIMESTAMP DEFAULT now()
);