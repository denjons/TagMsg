/*
	adds user and returns new id 
    or 
    returns id for existing user
*/
DELIMITER //
create procedure createUser()
BEGIN
	set @uuid = UUID();
	INSERT INTO Users (uu_id, date) VALUES (@uuid, current_timestamp());
	SELECT * FROM Users WHERE Users.id = @uuid;
end //
DELIMITER ;



/*
		***************************** TAGS ********************************
*/

DELIMITER //
create procedure addTagIfNotExists(user varchar(40), tag_name VARCHAR(50))
BEGIN
	declare 
    tag_id integer;
    declare 
    user_id integer;
	IF TagExists(tag_name) = -1 then 
		insert into Tags (tag) values (tag_name);
        set tag_id = TagExists(tag_name);
        set user_id = userExists(user);
        if(user_id > 0 && tag_id > 0) then
			insert into usertagrelation (user, tag) values(user_id, tag_id);
		end if;
	END IF;
end //
DELIMITER ;


DELIMITER //
create procedure RemoveTagForUser(user VARCHAR(40), tag_name VARCHAR(250))
BEGIN
	DECLARE
    tag_id integer;
    DECLARE
    user_id integer;

    set tag_id = tagExists(tag);
    set user_id = tagExists(tag);
    
    if(tag_id > 0 && user_id > 0) then
		DELETE FROM UserTagRelation WHERE user = user_id AND tag = tag_id ;
    end if;
    
end //
DELIMITER ;