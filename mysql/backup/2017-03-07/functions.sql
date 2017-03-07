
DELIMITER //
create function USER_EXISTS(userId VARCHAR(40)) returns int
begin
	declare user_id int;
	select id into user_id from USERS where Users.uu_id = userId;
	if user_id > 0 then
		return user_id;
	else
		return -1;
	end if;
end //
DELIMITER ;


DELIMITER //
create function USER_HAS_TAG(userId VARCHAR(40), tag_name VARCHAR(50)) returns int
begin
	declare k int;
	select count(*) into k from HasTags where 
	userId = HasTags.user AND tag_name = HasTags.tag;
	if k > 0 then
		return 1;
	else
		return -1;
	end if;
end //
DELIMITER ;


DELIMITER //
create function USER_HAS_TAGS(userId VARCHAR(40)) returns int
begin
	declare k int;
	select count(*) into k from HasTags where 
	userId = HasTags.user;
	if k > 0 then
		return 1;
	else
		return -1;
	end if;
end //
DELIMITER ;




/*
		 ************************ tags *************************
*/

DELIMITER //
create function TAG_EXISTS(tag_name VARCHAR(50)) returns int
begin
	DECLARE tag_id int;
    select id into tag_id from TAGS where Tags.tag = tag_name ;
    if tag_id > 0 then 
		return tag_id ;
	else
		return -1 ;
    end if ;
    
end //
DELIMITER ;




/*
		************************requests *******************
*/

DELIMITER //
create function REQUEST_EXISTS(req_id VARCHAR(40)) returns int
begin
	declare db_id int;
    select id into db_id from REQUESTS where REQUESTS.uu_id = req_id ;
    if db_id > 0 then
		return db_id;
	else
		return -1;
	end if ;
end //
DELIMITER ;


DELIMITER //
create function REQUEST_HAS_TAG(req_id integer, tag_id integer) returns int
begin
	declare c int;
    select count(*) into c from requests_tag_relation where requests_tag_relation.request = req_id AND requests_tag_relation.tag = tag_id;
    if c > 0 then
		return 1;
	else
		return -1;
	end if ;
end //
DELIMITER ;

DELIMITER //
create function OWNER_OF_REQUEST(user VARCHAR(40), reqId VARCHAR(40)) returns int
begin
	declare c int;
	select count(*) into c from REQUESTS where REQUESTS.id = reqId AND REQUESTS.user = user;
    if c > 0 then
		return 1;
	else
		return -1;
	end if ;
end //
DELIMITER ;

DELIMITER //
create function GET_REQUEST_DATE(req_id VARCHAR(40)) returns timestamp
begin
	set @date = null;
    if(REQUEST_EXISTS(req_id) = 1) then
		select date into @date from REQUESTS where REQUESTS.uu_id = req_id;
	end if;
    return @date;
end //
DELIMITER ;

/*
		****************************** responses ******************************
*/
DELIMITER //
create function RESPONSE_EXISTS(resp_id VARCHAR(40)) returns int
begin
	declare db_id int;
    select id into db_id from RESPONSES where RESPONSES.uu_id = resp_id ;
    if db_id > 0 then
		return db_id;
	else
		return -1;
	end if ;
end //
DELIMITER ;

DELIMITER //
create function GET_RESPONSE_DATE(resp_id VARCHAR(40)) returns timestamp
begin
	set @date = null;
    if(RESPONSE_EXISTS(resp_id) = 1) then
		select date into @date from RESPONSES where RESPONSES.uu_id = resp_id;
	end if;
    return @date;
end //
DELIMITER ;

