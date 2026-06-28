create table compliance_record(
    id UUID primary key default gen_random_uuid(),
    design_id UUID not null,
    property_id UUID not null,
    compliance_status varchar(10) not null check ( compliance_status in ('PASSED','FAILED')),
    reason varchar(200),
    calculated_capacity NUMERIC(10,2) not null ,
    recommended_area NUMERIC(10,2) not null ,
    checked_at timestamp not null
);