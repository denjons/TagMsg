

/*
		***************************** TAGS ********************************
*/

DELIMITER //
create procedure ADD_TAG(tag_name VARCHAR(50))
BEGIN
	IF TAG_EXISTS(tag_name)= -1 then 
		insert into TAGS (tag) values (tag_name);
	END IF;
end //
DELIMITER ;


DELIMITER //
create procedure ADD_USER_TAG(user_id integer, tag_name VARCHAR(50))
BEGIN
	declare 
    tag_id integer;
	call ADD_TAG(tag_name);
	set tag_id = TAG_EXISTS(tag_name);
	if(user_id > 0 && tag_id > 0) then
		insert into USER_TAG_RELATION (user, tag) values(user_id, tag_id);
	END IF;
end //
DELIMITER ;


DELIMITER //
create procedure REMOVE_USER_TAG(user_id VARCHAR(40), tag_name VARCHAR(50))
BEGIN
	DECLARE
    tag_id integer;

    set tag_id = TAG_EXISTS(tag_name);
    
    if(tag_id > 0 && user_id > 0) then
		DELETE FROM USER_TAG_RELATION WHERE USER_TAG_RELATION.user = user_id AND USER_TAG_RELATION.tag = tag_id ;
    end if;
    
end //
DELIMITER ;

/*
		************************ REQUESTS *************************
*/


/*
		add tags for request
*/

-- drop procedure ADD_REQUEST_TAG;

DELIMITER //
create procedure ADD_REQUEST_TAG(req_id integer, tag_id VARCHAR(50))
begin

    DECLARE
    tag_db_id integer;

    call add_tag(tag_id);
    
    set tag_db_id = TAG_EXISTS(tag_id);
    
    insert into requests_tag_relation (request, tag) values (req_id, tag_db_id) ;
	
    
end//
DELIMITER ;


/*
	get requests for user
*/

-- drop procedure GET_USER_REQUESTS;

DELIMITER //
create procedure GET_USER_REQUESTS(user_id integer, requestLimit int, requestOffset int, fromRequest VARCHAR(40), beforeRequest VARCHAR(40))
begin
    set @startDate = GET_REQUEST_DATE(fromRequest);
    set @endDate = GET_REQUEST_DATE(beforeRequest);
    
	select * from REQUESTS_VIEW where user = user_id 
    AND (@startDate <= REQUESTS_VIEW.date OR @startDate IS NULL)
	AND (@endDate >= REQUESTS_VIEW.date OR @endDate IS NULL)
	order by date DESC limit requestLimit offset requestOffset;
    
end //
DELIMITER ;

/*
		get all requests a user is eligible for
*/

-- drop procedure GET_ELIGIBLE_REQUESTS;

DELIMITER //
create procedure GET_ELIGIBLE_REQUESTS(userId integer, requestLimit int, requestOffset int, fromRequest VARCHAR(40), beforeRequest VARCHAR(40))
begin

    
    set @startDate = GET_REQUEST_DATE(fromRequest);
    set @endDate = GET_REQUEST_DATE(beforeRequest);
    
    if(USER_HAS_TAGS(userId) = -1) then
		select * from REQUESTS_VIEW
        where userId != REQUESTS_VIEW.user
        AND (@startDate <= REQUESTS_VIEW.date OR @startDate IS NULL)
        AND (@endDate >= REQUESTS_VIEW.date OR @endDate IS NULL)
        order by date DESC limit requestLimit offset requestOffset;
    else
		select table1.request as id, REQUESTS_VIEW.uu_id, REQUESTS_VIEW.user, REQUESTS_VIEW.content, REQUESTS_VIEW.tags as tags , REQUESTS_VIEW.date from
		(select request from ELIGIBLE_VIEW where ELIGIBLE_VIEW.user = userId ) as table1
		join 
		REQUESTS_VIEW 
		on 
		REQUESTS_VIEW.id = table1.request AND NOT REQUESTS_VIEW.user = userId
        AND (@startDate <= REQUESTS_VIEW.date OR @startDate IS NULL)
        AND (@endDate >= REQUESTS_VIEW.date OR @endDate IS NULL)
		ORDER BY REQUESTS_VIEW.date DESC
		LIMIT requestLimit offset requestOffset;
    end if;
end //
DELIMITER ;  

/*
		get requests for tags
*/

-- drop procedure GET_REQUESTS_FROM_TAGS;

DELIMITER //
create procedure GET_REQUESTS_FROM_TAGS(tags TEXT, requestLimit int, requestOffset int, startDate  VARCHAR(40))
begin
	
    SET @tags = tags;
    SET @limit = requestLimit;
    SET @offset = requestOffset;
	SET @startDate = startDate;  

    SET @Query = concat(
    'select REQUESTS_VIEW.id, REQUESTS_VIEW.user, REQUESTS_VIEW.uu_id, REQUESTS_VIEW.content, REQUESTS_VIEW.date, REQUESTS_VIEW.tags',
    ' from REQUESTS_TAG_RELATION join REQUESTS_VIEW on',
    ' REQUESTS_TAG_RELATION.tag in (?)',
   -- ' AND (? IS NULL OR ? <= REQUESTS_VIEW.date)',
    ' AND REQUESTS_VIEW.id = REQUESTS_TAG_RELATION.request',
    ' limit ? offset ?;');
    
    PREPARE stmt FROM @query;
	EXECUTE stmt using 
    @tags, /* @startDate, @startDate, */ @limit, @offset;
    
end //
DELIMITER ;


/*
		get responses for request
*/
-- drop procedure GET_RESPONSES_FOR_REQUESTS;
DELIMITER //
create procedure GET_RESPONSES_FOR_REQUESTS( reqId integer, responseLimit int, responseOffset int, afterResponse VARCHAR(40), beforeResponse VARCHAR(40))
begin 

	set @startDate = GET_RESPONSE_DATE(afterResponse);
    set @endDate = GET_RESPONSE_DATE(beforeResponse);
    
	select * from RESPONSES where  RESPONSES.request = reqId
    AND (@startDate <= RESPONSES.date OR @startDate IS NULL)
	AND (@endDate >= RESPONSES.date OR @endDate IS NULL)
	order by date DESC limit responseLimit offset responseOffset;
end //
DELIMITER ;

