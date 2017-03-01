/*
call addUser(); 
call addUser(); 
*/
select id into @user1 from Users limit 1; 
select id into @user2 from Users limit 1 offset 1;
/*
call addRequest(@user1, "some content", UUID(), "japanese");
call addRequest(@user1, "some content", UUID(), "japanese,english");
call addRequest(@user1, "some content", UUID(), "french");
call addRequest(@user1, "some content", UUID(), "english");
call addRequest(@user1, "some content", UUID(), "german");
call addRequest(@user1, "some content", UUID(), "japanese");

call getEligibleRequestsForUser(@user2, 10, 0)
*/



/*
call addTagForUser(@user2, "japanese");
*/
/*
call addRequest(@user2, "dont get this for user 2", UUID(), "japanese");
*/

/*
call getEligibleRequestsForUser(@user1, 10, 0);
*/
/*

select getRequests.date into @date from getRequests order by getRequests.date DESC limit 1 offset 2;

call getEligibleRequestsForUser(@user2, 10, 0, null, null);
*/

select id into @req from getRequests limit 1;

/*
call addResponseToRequest(@user2, UUID(), "some answer", @req);
call addResponseToRequest(@user2, UUID(), "another answer", @req);
call addResponseToRequest(@user2, UUID(), "a third answer", @req);
*/
/*
call addResponseToRequest(@user2, UUID(), "a fourth answer", @req);
call addResponseToRequest(@user2, UUID(), "a fitht answer", @req);
*/

select date into @date from Responses where Responses.request = @req limit 1 offset 0;

call getResponsesForRequest(@user1, @req, 10, 0, @date, null);



