create type category_color as enum ('blue', 'green', 'white');

create table categories (
                            category_id uuid primary key default gen_random_uuid(),
                            title varchar(32) unique not null,
                            color category_color not null default 'white'
);

create table routes (
                        route_id uuid primary key default gen_random_uuid(),
                        user_id uuid not null,
                        category_id uuid references categories(category_id) on delete set null,
                        country varchar(64) not null,
                        title varchar(32) not null,
                        description varchar(2048),
                        preview_photo_link varchar(512),
                        create_date timestamp default current_timestamp,
                        update_date timestamp default current_timestamp
);

create table points (
                        point_id uuid primary key default gen_random_uuid(),
                        route_id uuid references routes(route_id) on delete cascade,
                        title varchar(64) not null,
                        description varchar(2048),
                        coordinates varchar(128) not null,
                        photo_link varchar(512),
                        create_date timestamp default current_timestamp,
                        update_date timestamp default current_timestamp,
                        sort_order int not null check (sort_order >= 0)
);