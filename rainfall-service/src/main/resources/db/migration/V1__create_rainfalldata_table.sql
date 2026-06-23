create table rainfall_data(
pincode varchar(6) primary key ,
latitude double precision,
longitude double precision,
avg_rainfall double precision,
last_fetched_at timestamp
);