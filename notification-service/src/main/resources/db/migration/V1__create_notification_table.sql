create table notification(
    id UUID primary key default gen_random_uuid(),
    design_id UUID not null,
    property_id UUID not null ,
    recommendation_id UUID,
    recipient_email VARCHAR(100) ,
    event_type VARCHAR(50) check ( event_type in ('AI_RECOMMENDATION_READY','COMPLIANCE_FAILED') ) not null ,
    status varchar(10) check ( status in('SENT','FAILED') ) not null ,
    failure_reason VARCHAR ,
    created_at timestamp not null
);