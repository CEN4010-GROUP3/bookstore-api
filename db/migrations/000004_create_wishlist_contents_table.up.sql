create table public.wishlist_contents (
    book_isbn varchar(14),
    wishlist_id int, -- User database structure to be confirmed with other teammates.
    foreign key (book_isbn) references public.books (isbn),
    foreign key (wishlist_id) references public.wishlist (id)
);
