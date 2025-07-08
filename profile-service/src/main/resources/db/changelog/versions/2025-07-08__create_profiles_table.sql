create table profiles (
    profile_id uuid primary key default gen_random_uuid(),
    email varchar(128) check ( length(email) >= 5 ) unique not null ,
    password varchar(512) check ( length(password) >= 6 ) not null ,
    username varchar(32) check ( length(username) >= 5 ) unique not null ,
    nickname varchar(32) not null ,
    age int check ( 14 <= age and age <= 150) not null ,
    photo_link varchar(512) ,
    role varchar(8) check ( role in ('USER', 'ADMIN') ) default 'USER' not null ,
    registration_date timestamp default current_timestamp not null
)