create table profiles
(
    profile_id uuid primary key default gen_random_uuid(),
    user_id    uuid unique                                        not null,
    username   varchar(32) check ( length(username) >= 5 ) unique not null,
    nickname   varchar(64)                                        not null,
    age        int check ( 14 <= age and age <= 150)              not null,
    photo_link varchar(512)
)