/*
call addUser(); 
call addUser(); 

select id into @user1 from Users limit 1; 
select id into @user2 from Users limit 1 offset 1;

call addRequest(@user1, "some content", UUID(), "japanese");
call addRequest(@user1, "some content", UUID(), "japanese,english");
call addRequest(@user1, "some content", UUID(), "french");
call addRequest(@user1, "some content", UUID(), "english");
call addRequest(@user1, "some content", UUID(), "german");
call addRequest(@user1, "some content", UUID(), "japanese");

call getEligibleRequestsForUser(@user2, 10, 0)
*/

call addTagForUser(@user2, "japanese");
call getEligibleRequestsForUser(@user2, 10, 0);
