
/*
	maps requests tags and users tags
    used by eligible view
*/
create or replace view UserRequestMap as
	select user, request, OfTag.tag as tag  from
	HasTags
	join
	OfTag 
    on HasTags.tag = OfTag.tag ;
	

/*
	finds all eligible users (users that have all the tags) for all requests
*/	
create or replace view eligibleForUserView1 as
	select user, request, count(tag) as tags
	from UserRequestMap
	group by user, request;    
    
create or replace view eligibleForUserView2 as
	select request, count(tag) as tags 
    from OfTag
    group by request;
    
create or replace view eligibleForUser as
	select user, view1.request as request from
	eligibleForUserView1 as view1
    join
    eligibleForUserView2 as view2
    on 
    view1.request = view2.request 
    AND 
    view1.tags = view2.tags
    order by user asc;
    

/* gets all requests for something
*/
create or replace view getRequestsView1 as 
	select * from Requests;

create or replace view getRequestsView2 as 
	select request, GROUP_CONCAT(tag SEPARATOR ',') as tags 
    from OfTag group by request;

create or replace view getRequests as
	select ReqTable.id as id, ReqTable.user, ReqTable.content, tags, date from
    getRequestsView1 as ReqTable
    join
    getRequestsView2 as TagTable
    on ReqTable.id = TagTable.request ;
    

    


