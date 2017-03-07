delete from USER_TAG_RELATION;
delete from requests_tag_relation;
delete from tags;
delete from requests;
delete from responses;
delete from users;

insert into users (date) values(current_timestamp());
select max(id) into @id1 from users;
insert into users (date) values(current_timestamp());
select max(id) into @id2 from users;


call ADD_USER_TAG(@id1, 'tag1');
call ADD_USER_TAG(@id1, 'tag3');
call ADD_USER_TAG(@id2, 'tag1');


set @req1 = UUID();
set @req2 = UUID();
set @req3 = UUID();

call ADD_REQUEST(@id2, 'req 1', UUID());
select max(id) into @req1 from `REQUESTS`;
call ADD_REQUEST(@id2, 'req 2', UUID());
select max(id) into @req2 from `REQUESTS`;
call ADD_REQUEST(@id2, 'req 3', UUID());
select max(id) into @req3 from `REQUESTS`;

call ADD_REQUEST_TAG(@req1, 'tag1');
call ADD_REQUEST_TAG(@req1, 'tag2');
call ADD_REQUEST_TAG(@req2, 'tag1');
call ADD_REQUEST_TAG(@req3, 'tag1');


call `GET_ELIGIBLE_REQUESTS`(@id1, 5, 0, null, null);
call `GET_ELIGIBLE_REQUESTS`(@id2, 5, 0, null, null);