create table public.wishlist (
    name varchar(50),
    user_id int, -- References user table
    wishlist_id SERIAL, -- Incremental ID number
    primary key (wishlist_id),
    foreign key (user_id) references public.users (id)
);
