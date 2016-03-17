call addUser();
call addUser();


select id into @user1 from users limit 1;
select id into @user2 from users limit 1 offset 1;

call addTagsForUser(@user1, "japanese,finish,swedish");
