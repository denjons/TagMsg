import { Injectable } from '@angular/core';
import { Request } from './request.model';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable'; 
import { TMURLS } from '../urls';
import { TMHeader } from '../headers';

import '../../core/rxjs-extensions';

@Injectable()
export class RequestService{

    requests : Request[];
    
    constructor(private http: Http){
        console.log("constructed");
    }

    getEligibleRequests(){
      TMHeader.headers.set(TMHeader.ATYHORIZATION,
      "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0IiwiaWF0IjoxNDg5MjIzNzkyLCJzdWIiOiIvdXNlci9jcmVhdGV1c2VyIiwiaXNzIjoiVE1fV0lMREZMWV83In0.wW9YYV3WexzD7vFcaDBZLZlqt5KrcuyEfvgHi0KB4jA");
     // localStorage.getItem(web-token)

      return this.http
      .get(TMURLS.getEligibleRequestsURL(0,0),{ headers: TMHeader.headers })
      .map((response: Response) => <Request[]>response.json().requests)
      .do(requests => {this.requests = requests; console.log(requests)})
      .catch(this.handleError);
        
    }

    getRequest(reqid:any){
        console.log("get request "+reqid);
        return this.requests.find(request => request.id == reqid);
        
    }

    getRequestsForUser(){
        return this.http.get(TMURLS.getRequestsForUser(10,0),{ headers: TMHeader.headers })
            .map((response: Response) => <Request[]>response.json().requests)
            .do(requests => {this.requests = requests; console.log(requests)})
            .catch(this.handleError);
        }

    private handleError(error: Response) {
        console.error(error);
        let msg = `Error status code ${error.status} at ${error.url}`;
        return Observable.throw(msg);
    }

}