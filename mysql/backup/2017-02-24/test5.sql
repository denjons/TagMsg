
/*
call addUser(); 
call addUser(); 
call addUser(); 
*/

select id into @user1 from Users limit 1; 
select id into @user2 from Users limit 1 offset 1;

select id into @user3 from Users limit 1 offset 2; 

/*
call addRequest(@user3, "some content", UUID(), "english");
call addRequest(@user3, "some content", UUID(),"japanese");
call addRequest(@user3, "some content", UUID(),"french");
call addRequest(@user3, "some content",UUID(),"spanish");
*/

select id into @req2 from getRequests limit 1 offset 1;

/*
call getEligibleRequestsForUser(@user2, 10, 0, @req2, null);
*/

/*
call addResponseToRequest(@user1, UUID(), "some response", @req2);
call addResponseToRequest(@user2, UUID(), "some response", @req2);
call addResponseToRequest(@user1, UUID(), "some response", @req2);
call addResponseToRequest(@user2, UUID(), "some response", @req2);
call addResponseToRequest(@user1, UUID(), "some response", @req2);
*/
/*
call addResponseToRequest(@user1, UUID(), "some response", @req2);
call addResponseToRequest(@user2, UUID(), "some response", @req2);
call addResponseToRequest(@user1, UUID(), "some response", @req2);
*/

select id into @resp from Responses limit 1 offset 1;

call getResponsesForRequest(@user2, @req2, 10, 0, @resp, null);

