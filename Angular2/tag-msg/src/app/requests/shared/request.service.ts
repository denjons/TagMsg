import { Injectable } from '@angular/core';
import { Request } from '../shared/request.model';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable'; 
import { TMURLS } from '../../core/urls';
import { TMHeader } from '../../core/headers';

import '../../common/rxjs-extensions';

@Injectable()
export class RequestService{

    requests : Request[];
    
    constructor(private http: Http){
        console.log("constructed");
    }

    getEligibleRequests(){
      TMHeader.headers.set(TMHeader.ATYHORIZATION,
      "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMiIsImlhdCI6MTQ4OTE0NTI5MSwic3ViIjoiL3VzZXIvY3JlYXRldXNlciIsImlzcyI6IlRNX1dJTERGTFlfNyJ9.U4Ysdhw07-pXvQlWLkD-qDs_NtyxDfmL3zlUeILRg-0");
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

    private handleError(error: Response) {
        console.error(error);
        let msg = `Error status code ${error.status} at ${error.url}`;
        return Observable.throw(msg);
    }

}