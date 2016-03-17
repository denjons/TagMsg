

/*
		************************* user ***************************
*/
DELIMITER //
create function userExists(userId VARCHAR(40)) returns int
begin
	declare k int;
	select count(*) into k from Users where Users.id = userId;
	if k > 0 then
		return 1;
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

/*
		 ************************ tags *************************
*/

DELIMITER //
create function TagExists(tag_name VARCHAR(50)) returns int
begin
	DECLARE c int;
    select count(*) into c from Tags where Tags.tag = tag_name ;
    if c > 0 then 
		return 1 ;
	else
		return -1 ;
    end if ;
    
end //
DELIMITER ;

/*
		************************requests *******************
*/

DELIMITER //
create function requestExists(req_id VARCHAR(40)) returns int
begin
	declare c int;
    select count(*) into c from Requests where Requests.id = req_id ;
    if c > 0 then
		return 1;
	else
		return -1;
	end if ;
end //
DELIMITER ;

DELIMITER //
create function requestHasTag(req_id VARCHAR(40), tag_name VARCHAR(50)) returns int
begin
	declare c int;
    select count(*) into c from OfTag where OfTag.request = req_id AND OfTag.tag = tag_name;
    if c > 0 then
		return 1;
	else
		return -1;
	end if ;
end //
DELIMITER ;

DELIMITER //
create function userIsOwnerOfRequest(user VARCHAR(40), reqId VARCHAR(40)) returns int
begin
	declare c int;
	select count(*) into c from Requests where Requests.id = reqId AND Requests.user = user;
    if c > 0 then
		return 1;
	else
		return -1;
	end if ;
end //
DELIMITER ;

/*
		****************************** responses ******************************
*/
DELIMITER //
create function responseExists(resp_id VARCHAR(40)) returns int
begin
	declare c int;
    select count(*) into c from Responses where Responses.id = resp_id ;
    if c > 0 then
		return 1;
	else
		return -1;
	end if ;
end //
DELIMITER ;



