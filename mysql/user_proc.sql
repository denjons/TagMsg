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
create procedure ADD_USER_TAG(user varchar(40), tag_name VARCHAR(50))
BEGIN
	declare 
    tag_id integer;
    declare 
    user_id integer;
	IF TAG_EXISTS(tag_name) = -1 then 
		insert into TAGS (tag) values (tag_name);
        set tag_id = TAG_EXISTS(tag_name);
        set user_id = USER_EXISTS(user);
        if(user_id > 0 && tag_id > 0) then
			insert into USER_TAG_RELATION (user, tag) values(user_id, tag_id);
		end if;
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