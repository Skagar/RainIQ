create table tank_reading(
    id UUID primary key default gen_random_uuid(),
    device_id varchar(50) not null ,
    property_id UUID not null ,
    tank_level_percent NUMERIC(5,2) check ( tank_level_percent >= 0.00 and tank_level_percent <=100.00 ) not null ,
    recorded_at TIMESTAMP not null
);