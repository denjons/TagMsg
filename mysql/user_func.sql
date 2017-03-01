

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
create function userHasTag(userId VARCHAR(40), tag_name VARCHAR(50)) returns int
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
create function userHasTags(userId VARCHAR(40)) returns int
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


