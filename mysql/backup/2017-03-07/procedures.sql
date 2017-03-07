
/*
	adds user and returns new id 
    or 
    returns id for existing user
*/
DELIMITER //
create procedure CREATE_USER()
BEGIN
	set @uuid = UUID();
	INSERT INTO USERS (uu_id, date) VALUES (@uuid, current_timestamp());
	SELECT * FROM Users WHERE Users.id = @uuid;
end //
DELIMITER ;



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
create procedure ADD_USER_TAG(user varchar(40), tag_name VARCHAR(50))
BEGIN
	declare 
    tag_id integer;
    declare 
    user_id integer;
	call ADD_TAG(tag_name);
    set user_id = USER_EXISTS(user);
	set tag_id = TAG_EXISTS(tag_name);
	if(user_id > 0 && tag_id > 0) then
		insert into USER_TAG_RELATION (user, tag) values(user_id, tag_id);
	END IF;
end //
DELIMITER ;


DELIMITER //
create procedure REMOVE_USER_TAG(user VARCHAR(40), tag_name VARCHAR(50))
BEGIN
	DECLARE
    tag_id integer;
    DECLARE
    user_id integer;

    set tag_id = TAG_EXISTS(tag_name);
    set user_id = USER_EXISTS(user);
    
    if(tag_id > 0 && user_id > 0) then
		DELETE FROM USER_TAG_RELATION WHERE USER_TAG_RELATION.user = user_id AND USER_TAG_RELATION.tag = tag_id ;
    end if;
    
end //
DELIMITER ;
/*
		************************ REQUESTS *************************
*/

/*
	add request
*/
DELIMITER //
create procedure ADD_REQUEST(user VARCHAR(40), content_input TEXT, req_id VARCHAR(40))
begin

    DECLARE
    user_id integer;

    set user_id = USER_EXISTS(user);
    
	if(user_id = -1) then 
		signal sqlstate '20001';
	end if;
    
	if (REQUEST_EXISTS(req_id) = -1) then
		insert into REQUESTS (uu_id, user, content) values (req_id, user_id, content_input) ;
    else
		signal sqlstate '20005';
    end if ;
    
end//
DELIMITER ;


/*
		add tags for request
*/

-- drop procedure ADD_REQUEST_TAG;

DELIMITER //
create procedure ADD_REQUEST_TAG(req_id VARCHAR(40), tag_id VARCHAR(50))
begin
	DECLARE
    req_db_id integer;
    DECLARE
    tag_db_id integer;

    call add_tag(tag_id);
    
    set req_db_id = REQUEST_EXISTS(req_id);
    set tag_db_id = TAG_EXISTS(tag_id);
    
    IF(req_db_id = -1 || tag_db_id = -1) THEN
		signal sqlstate '20008';
	ELSEIF (REQUEST_HAS_TAG(req_db_id, tag_db_id) = -1) THEN
		insert into requests_tag_relation (request, tag) values (req_db_id, tag_db_id) ;
	ELSE
		signal sqlstate '20007';
	END IF ;
    
end//
DELIMITER ;


/*
	get requests for user
*/

-- drop procedure GET_USER_REQUESTS;

DELIMITER //
create procedure GET_USER_REQUESTS(userId VARCHAR(40), requestLimit int, requestOffset int, fromRequest VARCHAR(40), beforeRequest VARCHAR(40))
begin
	DECLARE
    user_db_id integer;
    
    set user_db_id = USER_EXISTS(userId);
    
	if(user_db_id = -1) then 
		signal sqlstate '20001';
	end if;
    
    set @startDate = GET_REQUEST_DATE(fromRequest);
    set @endDate = GET_REQUEST_DATE(beforeRequest);
    
	select * from REQUESTS_VIEW where user = user_db_id 
    AND (@startDate <= REQUESTS_VIEW.date OR @startDate IS NULL)
	AND (@endDate >= REQUESTS_VIEW.date OR @endDate IS NULL)
	order by date DESC limit requestLimit offset requestOffset;
    
end //
DELIMITER ;

/*
		get all requests a user is eligible for
*/
DELIMITER //
create procedure GET_ELIGIBLE_REQUESTS(userId VARCHAR(40), requestLimit int, requestOffset int, fromRequest VARCHAR(40), beforeRequest VARCHAR(40))
begin
	if(userExists(userId) = -1) then 
		signal sqlstate '20001';
	end if;
    
    set @startDate = GET_REQUEST_DATE(fromRequest);
    set @endDate = GET_REQUEST_DATE(beforeRequest);
    
    if(USER_HAS_TAGS(userId) = -1) then
		select * from REQUESTS_VIEW
        where userId != REQUESTS_VIEW.user
        AND (@startDate <= REQUESTS_VIEW.date OR @startDate IS NULL)
        AND (@endDate >= REQUESTS_VIEW.date OR @endDate IS NULL)
        order by date DESC limit requestLimit offset requestOffset;
    else
		select table1.request as id, REQUESTS_VIEW.user, REQUESTS_VIEW.content, REQUESTS_VIEW.tags as tags , REQUESTS_VIEW.date from
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


DELIMITER //
create procedure GET_REQUESTS_FROM_TAGS(tags TEXT, requestLimit int, requestOffset int, startDate TimeStamp, endDate Timestamp)
begin
	
    SET @tags = tags;
    SET @limit = requestLimit;
    SET @offset = requestOffset;
    SET @startDate = startDate;
    SET @endDate = endDate;
    
    SET @Query = concat(
    'select REQUESTS_VIEW.id, REQUESTS_VIEW.user, REQUESTS_VIEW.content, REQUESTS_VIEW.date, REQUESTS_VIEW.tags',
    ' from OfTag join REQUESTS_VIEW on',
    ' OfTag.tag in (?)',
    ' AND (? <= REQUESTS_VIEW.date OR ? IS NULL)',
    ' AND (? >= REQUESTS_VIEW.date OR ? IS NULL)',
    ' AND REQUESTS_VIEW.id = OfTag.request',
    ' limit ? offset ?;');
    
    PREPARE stmt FROM @query;
	EXECUTE stmt using 
    @tags, @startDate, @startDate, @endDate, @endDate, @limit, @offset;
    
end //
DELIMITER ;


/*
		add response for request
        updates if already exists
*/
DELIMITER //
create procedure ADD_RESPONSE_TO_REQUEST(responder VARCHAR(40), resp_id varchar(40), resp_content TEXT, req_id varchar(40))
begin
	/* if user does not exist*/
	declare user_db_id integer;
	declare req_db_id integer;

	set req_db_id = REQUEST_EXISTS(req_id);
	set user_db_id = USER_EXISTS(responder);
    
	if(req_db_id > 0) then
		if (RESPONSE_EXISTS(resp_id) = -1) then
			insert into RESPONSES (id, user, request, content) values (resp_id, responder, req_id, resp_content) ;
        else
			/* upadte response */
            update RESPONSES set content = resp_content where RESPONSES.id = req_db_id AND RESPONSES.user = user_db_id;
        end if;
    else
		/* if request does not exist*/
		signal sqlstate '20008';
    end if ;
end //
DELIMITER ;

/*
		get responses for request
*/
DELIMITER //
create procedure GET_RESPONSES_FOR_REQUESTS(user VARCHAR(40), reqId varchar(40), responseLimit int, responseOffset int, fromResponse VARCHAR(40), beforeResponse VARCHAR(40))
begin 

	declare req_db_id int;
	set @startDate = GET_RESPONSE_DATE(fromResponse);
    set @endDate = GET_RESPONSE_DATE(beforeResponse);
    set req_db_id = REQUEST_EXISTS(reqId);
    
	select * from RESPONSES where  RESPONSES.request = req_db_id
    AND (@startDate <= RESPONSES.date OR @startDate IS NULL)
	AND (@endDate >= RESPONSES.date OR @endDate IS NULL)
	order by date DESC limit responseLimit offset responseOffset;
end //
DELIMITER ;

