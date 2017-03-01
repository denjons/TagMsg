

/*
		**************************** USER **********************************
*/

/*
	adds user and returns new id 
    or 
    returns id for existing user
*/
DELIMITER //
create procedure addUser()
BEGIN
	set @uuid = UUID();
	INSERT INTO Users (id, date) VALUES (@uuid, current_timestamp());
	SELECT * FROM Users WHERE Users.id = @uuid;
end //
DELIMITER ;



/*
		***************************** TAGS ********************************
*/



DELIMITER //
create procedure addTagForUser(user VARCHAR(40), tags VARCHAR(250))
BEGIN
	DECLARE strLen    INT DEFAULT 0;
	DECLARE SubStrLen INT DEFAULT 0;
    
	if(userExists(user) = -1) then 
		signal sqlstate '20001';
	end if;
    
	IF tags IS NULL THEN
		signal sqlstate '20006';
	ELSE
		INSERT_LOOP:
		loop
			SET strLen = CHAR_LENGTH(tags);
			SET @tag = SUBSTRING_INDEX(tags, ',', 1);
            
			IF tags = '' THEN
			  LEAVE INSERT_LOOP;
			END IF;
            
            call addTagIfNotExists(@tag) ;
			IF(userHasTag(user, @tag) = -1) then
				INSERT INTO HasTags (user, tag) VALUES (user, @tag);
			END IF;
			
            
            SET SubStrLen = CHAR_LENGTH(SUBSTRING_INDEX(tags, ',', 1)) + 2;
			SET tags = MID(tags, SubStrLen, strLen);
            
		end loop INSERT_LOOP;
    END IF ;
end //
DELIMITER ;



DELIMITER //
create procedure addTagIfNotExists(tag_name VARCHAR(50))
BEGIN
	IF TagExists(tag_name) = -1 then 
		insert into Tags (tag) values (tag_name) ;
	END IF;
end //
DELIMITER ;

/*

removes tag and user from  Hastags if exists*/
/*
DELIMITER //
create procedure RemoveTagForUser(id VARCHAR(40), tag_name VARCHAR(50))
BEGIN
	IF(userHasTag(id, tag_name) = 1) then
		DELETE FROM HasTags WHERE id = HasTags.user AND tag_name = HasTags.tag ;
	ELSE
		 signal sqlstate '20004';
	 END IF;
end //
DELIMITER ;
*/

DELIMITER //
create procedure RemoveTagForUser(id VARCHAR(40), tags VARCHAR(250))
BEGIN
	DECLARE strLen    INT DEFAULT 0;
	DECLARE SubStrLen INT DEFAULT 0;
    
	if(userExists(id) = -1) then 
		signal sqlstate '20001';
	end if;
    
	IF tags IS NULL THEN
		signal sqlstate '20006';
	ELSE
		INSERT_LOOP:
		loop
			SET strLen = CHAR_LENGTH(tags);
			SET @tag = SUBSTRING_INDEX(tags, ',', 1);
            
			IF tags = '' THEN
			  LEAVE INSERT_LOOP;
			END IF;
            
			
			DELETE FROM HasTags WHERE id = HasTags.user AND @tag = HasTags.tag ;
			
            SET SubStrLen = CHAR_LENGTH(SUBSTRING_INDEX(tags, ',', 1)) + 2;
			SET tags = MID(tags, SubStrLen, strLen);
            
		end loop INSERT_LOOP;
    END IF ;
end //
DELIMITER ;

/*
		************************ REQUESTS *************************
*/

/*
		add request
*/

/*
CREATE TABLE IF NOT EXISTS Requests(
	id VARCHAR(40),
	user int,
	content TEXT,
	PRIMARY KEY(id),
	FOREIGN KEY (user) REFERENCES Users(id) 
); 

*/

/*
	add request
*/
DELIMITER //
create procedure addRequest(user_id VARCHAR(40), content_input TEXT, req_id VARCHAR(40), tags VARCHAR(250))
begin

	if(userExists(user_id) = -1) then 
		signal sqlstate '20001';
	end if;
    
	if (requestExists(req_id) = -1) then
		insert into Requests (id, user, content) values (req_id, user_id, content_input) ;
        CALL addTagsForRequest(req_id, tags) ;
    else
		signal sqlstate '20005';
    end if ;
end//
DELIMITER ;


/*
		add tags for request
*/

DELIMITER //
create procedure addTagsForRequest(req_id VARCHAR(40), tags VARCHAR(250))
begin
	DECLARE strLen    INT DEFAULT 0;
	DECLARE SubStrLen INT DEFAULT 0;
	IF tags IS NULL THEN
		signal sqlstate '20006';
	ELSE
		INSERT_LOOP:
		loop
			SET strLen = CHAR_LENGTH(tags);
			SET @tag = SUBSTRING_INDEX(tags, ',', 1);
            
			IF tags = '' THEN
			  LEAVE INSERT_LOOP;
			END IF;
            
			IF (requestHasTag(req_id, @tag) = -1) THEN
				call addTagIfNotExists(@tag) ;
				insert into OfTag (request, tag) values (req_id, @tag) ;
			ELSE
				signal sqlstate '20007';
                LEAVE INSERT_LOOP ;
			END IF ;
            
            SET SubStrLen = CHAR_LENGTH(SUBSTRING_INDEX(tags, ',', 1)) + 2;
			SET tags = MID(tags, SubStrLen, strLen);
            
		end loop INSERT_LOOP;
    END IF ;
end//
DELIMITER ;


/*
	get requests for user
*/
DELIMITER //
create procedure getRequestsFromUser(userId VARCHAR(40), requestLimit int, requestOffset int, fromRequest VARCHAR(40), beforeRequest VARCHAR(40))
begin
	if(userExists(userId) = -1) then 
		signal sqlstate '20001';
	end if;
    
    set @startDate = getDateOfRequest(fromRequest);
    set @endDate = getDateOfRequest(beforeRequest);
    
	select * from getRequests where user = userId 
    AND (@startDate <= getRequests.date OR @startDate IS NULL)
	AND (@endDate >= getRequests.date OR @endDate IS NULL)
	order by date DESC limit requestLimit offset requestOffset;
    
end //
DELIMITER ;

/*
		get all requests a user is eligible for
*/
DELIMITER //
create procedure getEligibleRequestsForUser(userId VARCHAR(40), requestLimit int, requestOffset int, fromRequest VARCHAR(40), beforeRequest VARCHAR(40))
begin
	if(userExists(userId) = -1) then 
		signal sqlstate '20001';
	end if;
    
    set @startDate = getDateOfRequest(fromRequest);
    set @endDate = getDateOfRequest(beforeRequest);
    
    if(userHasTags(userId) = -1) then
		select * from getRequests
        where userId != getRequests.user
        AND (@startDate <= getRequests.date OR @startDate IS NULL)
        AND (@endDate >= getRequests.date OR @endDate IS NULL)
        order by date DESC limit requestLimit offset requestOffset;
    else
		select table1.request as id, getRequests.user, getRequests.content, getRequests.tags as tags , getRequests.date from
		(select request from eligibleForUser where eligibleForUser.user = userId ) as table1
		join 
		getRequests 
		on 
		getRequests.id = table1.request AND NOT getRequests.user = userId
        AND (@startDate <= getRequests.date OR @startDate IS NULL)
        AND (@endDate >= getRequests.date OR @endDate IS NULL)
		ORDER BY getRequests.date DESC
		LIMIT requestLimit offset requestOffset;
    end if;
end //
DELIMITER ;  

/*
		get requests for tags
*/


DELIMITER //
create procedure getRequestsFromTags(tags TEXT, requestLimit int, requestOffset int, startDate TimeStamp, endDate Timestamp)
begin
	
    SET @tags = tags;
    SET @limit = requestLimit;
    SET @offset = requestOffset;
    SET @startDate = startDate;
    SET @endDate = endDate;
    
    SET @Query = concat(
    'select getRequests.id, getRequests.user, getRequests.content, getRequests.date, getRequests.tags',
    ' from OfTag join getRequests on',
    ' OfTag.tag in (?)',
    ' AND (? <= getRequests.date OR ? IS NULL)',
    ' AND (? >= getRequests.date OR ? IS NULL)',
    ' AND getRequests.id = OfTag.request',
    ' limit ? offset ?;');
    
    PREPARE stmt FROM @query;
	EXECUTE stmt using 
    @tags, @startDate, @startDate, @endDate, @endDate, @limit, @offset;
    
end //
DELIMITER ;








/*
DELIMITER //
create procedure getEligibleRequestsForUserAfterDate(userId VARCHAR(40), lastUpdate timestamp)
begin
	if(userExists(userId) = -1) then 
		signal sqlstate '20001';
	end if;
    select table1.request as id, getRequests.user, getRequests.content, getRequests.tags as tags , getRequests.date from
    (select request from eligibleForUser where eligibleForUser.user = userId ) as table1
    join 
    getRequests 
    on 
    getRequests.id = table1.request AND NOT getRequests.user = userId AND lastUpdate < getRequests.date;
end //
DELIMITER ;
*/

/*
		************************ RESPONSES ***************************
*/
/*
CREATE TABLE IF NOT EXISTS Responses(
	id VARCHAR(40),
	user int,
	request VARCHAR(40),
	date timestamp default current_timestamp,
	content TEXT,
	PRIMARY KEY(id),
	FOREIGN KEY (user) REFERENCES Users(id),
	FOREIGN KEY (request) REFERENCES Requests(id)
);
*/

/*
		add response for request
        updates if already exists
*/
DELIMITER //
create procedure addResponseToRequest(responder VARCHAR(40), resp_id varchar(40), resp_content TEXT, req_id varchar(40))
begin
	/* if user does not exist*/
	if(userExists(responder) = -1) then 
		signal sqlstate '20001';
	end if;
    
	if(requestExists(req_id) = 1) then
		if (responseExists(resp_id) = -1) then
			insert into Responses (id, user, request, content) values (resp_id, responder, req_id, resp_content) ;
        else
			/* upadte response */
            update Responses set content = resp_content where Responses.id = resp_id AND Responses.user = responder;
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
create procedure getResponsesForRequest(user VARCHAR(40), reqId varchar(40), responseLimit int, responseOffset int, fromResponse VARCHAR(40), beforeResponse VARCHAR(40))
begin 

	set @startDate = getDateOfResponse(fromResponse);
    set @endDate = getDateOfResponse(beforeResponse);
    
	select * from Responses where  Responses.request = reqId
    AND (@startDate <= Responses.date OR @startDate IS NULL)
	AND (@endDate >= Responses.date OR @endDate IS NULL)
	order by date DESC limit responseLimit offset responseOffset;
end //
DELIMITER ;

