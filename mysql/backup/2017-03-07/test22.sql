set @uuid1 = UUID();
set @uuid2 = UUID();


insert into users (uu_id, date) values(@uuid1, current_timestamp());
insert into users (uu_id, date) values(@uuid2, current_timestamp());

call ADD_USER_TAG(@uuid1, 'tag1');
call ADD_USER_TAG(@uuid2, 'tag1');

CALL REMOVE_USER_TAG(@uuid1, 'tag1');

SELECT * FROM USER_TAG_RELATION;


-- SELECT * FROM USERS;

-- SELECT USER_EXISTS('82abd497-fde9-11e6-a066-00155d266400');

-- call RemoveTagForUser(@uuid1, 'tag2');

-- select * from tags;

-- select TagExists('tag2');

