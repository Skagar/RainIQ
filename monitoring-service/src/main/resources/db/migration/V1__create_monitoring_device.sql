create table monitoring_device(
    id UUID primary key default gen_random_uuid(),
    device_id VARCHAR(50) unique not null ,
    property_id UUID unique  not null ,
    design_id UUID unique not null ,
    installed_by varchar(100) not null ,
    installed_at TIMESTAMP not null ,
    status VARCHAR(10) check ( status in ('ACTIVE','INACTIVE') ) not null
);