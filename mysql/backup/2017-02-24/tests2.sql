


/*
	add user
*/
SET @id = 0;
SET @name = 'user';

set @u1 = concat(@name, @id) ;
call addUser(@u1 ) ;
set @id = @id + 1 ;

set @u2 = concat(@name, @id) ;
call addUser(@u2) ;
set @id = @id + 1 ;

set @u3 = concat(@name, @id) ;
call addUser(@u3) ;
set @id = @id + 1 ;


/*
	add tag for user
*/

set @tag1 = 'japanese' ;
set @tag2 = 'swedish' ;
set @tag3 = 'english' ;
set @tag4 = 'german' ;

call addTagForUser(@u1, @tag1);
call addTagForUser(@u1, @tag2);
call addTagForUser(@u1, @tag3);
call addTagForUser(@u1, @tag4);

call addTagForUser(@u2, @tag1);
call addTagForUser(@u2, @tag2);
call addTagForUser(@u2, @tag3);

call addTagForUser(@u3, @tag1);
call addTagForUser(@u3, @tag2);

/*
	remove tag for user
*/

call RemoveTagForUser(@u1, @tag4);
call RemoveTagForUser(@u1, @tag3);
call RemoveTagForUser(@u1, @tag2);

/*call addTagForUser(@u3, @tag4);*/
call addTagForUser(@u3, @tag3);

/*
		************** REQUETS *******************
*/
set @content_input = 'example request';
set @req1 = 'req1';
set @req2 = 'req2';
set @req3 = 'req3';
set @req4 = 'req4';

set @tags1 = 'japanese,swedish,english,german';
set @tags2 = 'japanese,swedish,english';
set @tags3 = 'japanese,swedish';
set @tags4 = 'japanese';

CALL addRequest(@u1, @content_input, @req1, @tags1) ;
CALL addRequest(@u1, @content_input, @req2, @tags2) ;
CALL addRequest(@u2, @content_input, @req3, @tags1) ;
CALL addRequest(@u3, @content_input, @req4, @tags4) ;

/*
	responses
*/
set @resp_input = 'example response';

set @resp1 = 'resp1';
set @resp2 = 'resp2';
set @resp3 = 'resp3';
set @resp4 = 'resp4';

CALL addResponseToRequest(@u1, @resp1, @resp_input, @req4) ;
CALL addResponseToRequest(@u2, @resp2, @resp_input, @req1) ;
