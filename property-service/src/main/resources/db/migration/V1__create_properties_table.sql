create table properties(
     id UUID primary key default gen_random_uuid(),
    owner_email varchar(100) not null,
    address varchar(100) not null ,
    city varchar(50) not null ,
    state varchar(50) not null ,
    pincode varchar(10) not null ,
    area decimal(10,2) not null ,
    property_type varchar(20) not null check (property_type in  ('RESIDENTIAL', 'COMMERCIAL' )),
    status varchar(20)  not null default 'UNDER_REVIEW' check ( status in ('ACTIVE', 'INACTIVE','UNDER_REVIEW')),
    created_at TIMESTAMP default now(),
    updated_at TIMESTAMP
);