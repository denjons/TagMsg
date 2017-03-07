insert into users (date) values(current_timestamp());
select max(id) into @id1 from users;
insert into users (date) values(current_timestamp());
select max(id) into @id2 from users;



call ADD_USER_TAG(@id1, 'tag1');
call ADD_USER_TAG(@id2, 'tag1');

CALL REMOVE_USER_TAG(@id1, 'tag1');

SELECT * FROM USER_TAG_RELATION;


-- SELECT * FROM USERS;

-- SELECT USER_EXISTS('82abd497-fde9-11e6-a066-00155d266400');

-- call RemoveTagForUser(@uuid1, 'tag2');

-- select * from tags;

-- select TagExists('tag2');

