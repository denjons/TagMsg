set @uuid1 = UUID();
set @uuid2 = UUID();

insert into users (uu_id, date) values(@uuid1, current_timestamp());
insert into users (uu_id, date) values(@uuid2, current_timestamp());

call ADD_USER_TAG(@uuid1, 'tag1');
call ADD_USER_TAG(@uuid2, 'tag1');

set @req1 = UUID();
set @req2 = UUID();
set @req3 = UUID();

call ADD_REQUEST(@uuid1, 'req 1', @req1);
call ADD_REQUEST(@uuid1, 'req 2', @req2);
call ADD_REQUEST(@uuid2, 'req 3', @req3);

call ADD_REQUEST_TAG(@req1, 'tag1');
call ADD_REQUEST_TAG(@req1, 'tag2');
call ADD_REQUEST_TAG(@req2, 'tag2');
call ADD_REQUEST_TAG(@req3, 'tag3');

call GET_USER_REQUESTS(@uuid1, 5, 0, @req2, null);

-- SELECT * FROM USERS;

-- SELECT USER_EXISTS('82abd497-fde9-11e6-a066-00155d266400');

-- call RemoveTagForUser(@uuid1, 'tag2');

-- select * from tags;

-- select TagExists('tag2');

