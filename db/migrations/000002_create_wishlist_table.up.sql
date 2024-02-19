create table public.wishlist (
    book_isbn varchar(14),
    user_id int, -- User database structure to be confirmed with other teammates.
    foreign key (book_isbn) references public.books (isbn)
    -- foreign key to users table, to be set at that time
);
