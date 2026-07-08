create table ai_recommendation(
id UUID primary key default gen_random_uuid(),
design_id UUID unique not null ,
 property_id UUID not null ,
recommended_tank_size_liters integer,
  recommended_pipe_spec varchar(100),
recommended_filtration_type varchar(200),
 estimated_cost_inr NUMERIC(15,2),
 estimated_annual_savings_inr NUMERIC(15,2),
comments TEXT,
status varchar(15) check ( status in ('GENERATED','FAILED') ),
  created_at TIMESTAMP
);