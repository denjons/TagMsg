delete from USER_TAG_RELATION;
delete from requests_tag_relation;
delete from tags;
delete from responses;
delete from requests;
delete from users;

insert into users (date) values(current_timestamp());
select max(id) into @id1 from users;
insert into users (date) values(current_timestamp());
select max(id) into @id2 from users;


call ADD_USER_TAG(@id1, 'english');
call ADD_USER_TAG(@id1, 'japanese');
call ADD_USER_TAG(@id2, 'english');


call ADD_REQUEST(@id2, 'req 1', UUID());
select max(id) into @req1 from `REQUESTS`;
call ADD_REQUEST(@id2, 'req 2', UUID());
select max(id) into @req2 from `REQUESTS`;
call ADD_REQUEST(@id2, 'req 3', UUID());
select max(id) into @req3 from `REQUESTS`;

call ADD_REQUEST_TAG(@req1, 'english');
call ADD_REQUEST_TAG(@req1, 'german');
call ADD_REQUEST_TAG(@req2, 'english');
call ADD_REQUEST_TAG(@req3, 'english');

insert into Responses (uu_id, user, content, request) values('12121212-12121212-12121212-12121212', @id1, 'va saaa ruu?', @req1);
insert into Responses (uu_id, user, content, request) values('42121212-12121212-12121212-12121212', @id1, 'nante iwamasuka?', @req1);

call GET_RESPONSES_FOR_REQUESTS(@req1, 10, 0, null, null);


