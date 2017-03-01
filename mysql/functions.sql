

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

DELIMITER //
create function getDateOfRequest(req_id VARCHAR(40)) returns timestamp
begin
	set @date = null;
    if(requestExists(req_id) = 1) then
		select date into @date from Requests where Requests.id = req_id;
	end if;
    return @date;
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

DELIMITER //
create function getDateOfResponse(resp_id VARCHAR(40)) returns timestamp
begin
	set @date = null;
    if(responseExists(resp_id) = 1) then
		select date into @date from Responses where Responses.id = resp_id;
	end if;
    return @date;
end //
DELIMITER ;

