
/*
	maps requests tags and users tags
    used by eligible view
*/
create or replace view USER_REQUEST_VIEW as
	select user, request, REQUESTS_TAG_RELATION.tag as tag  from
	USER_TAG_RELATION
	join
	REQUESTS_TAG_RELATION 
    on USER_TAG_RELATION.tag = REQUESTS_TAG_RELATION.tag ;
	

/*
	finds all eligible users (users that have all the tags) for all requests
*/	
create or replace view ELIGIBLE_VIEW_1 as
	select user, request, count(tag) as tags
	from USER_REQUEST_VIEW
	group by user, request;    
    
create or replace view ELIGIBLE_VIEW_2 as
	select request, count(tag) as tags 
    from REQUESTS_TAG_RELATION
    group by request;
    
create or replace view ELIGIBLE_VIEW as
	select user, view1.request as request from
	ELIGIBLE_VIEW_1 as view1
    join
    ELIGIBLE_VIEW_2 as view2
    on 
    view1.request = view2.request 
    AND 
    view1.tags = view2.tags
    order by user asc;
    

/* gets all requests for something
*/
create or replace view REQUESTS_VIEW_1 as 
	select * from REQUESTS;

create or replace view REQUESTS_VIEW_2 as 
	select request, GROUP_CONCAT((SELECT TAG FROM TAGS WHERE ID = REQUESTS_TAG_RELATION.tag) SEPARATOR ',') as tags 
    from REQUESTS_TAG_RELATION group by request;

create or replace view REQUESTS_VIEW as
	select ReqTable.id as id, ReqTable.user, ReqTable.content, tags, date from
    REQUESTS_VIEW_1 as ReqTable
    join
    REQUESTS_VIEW_2 as TagTable
    on ReqTable.id = TagTable.request ;
    

    


