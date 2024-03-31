create table public.wishlist (
    name varchar(50),
    id SERIAL, -- Incremental ID number
    user_id int, -- References user table
    primary key (id),
    foreign key (user_id) references public.users (id)
);
