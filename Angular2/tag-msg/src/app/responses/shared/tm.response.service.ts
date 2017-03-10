import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable'; 
import { TMURLS } from '../../core/urls';
import { TMHeader } from '../../core/headers';
import { TMResponse } from './tm.response.model';


@Injectable()
export class TMResponseService{

    responses : TMResponse[];

    constructor(private http: Http){}

    getResponses(requestId: any){
        TMHeader.headers.set(TMHeader.ATYHORIZATION,
      "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMiIsImlhdCI6MTQ4OTE0NTI5MSwic3ViIjoiL3VzZXIvY3JlYXRldXNlciIsImlzcyI6IlRNX1dJTERGTFlfNyJ9.U4Ysdhw07-pXvQlWLkD-qDs_NtyxDfmL3zlUeILRg-0");
     
        return this.http.get(TMURLS.getResponses(10, 0), {headers : TMHeader.headers})
            .map((response: Response) => <TMResponse[]>response.json().responses)
            .do(responses => {this.responses = responses; 
            console.log(responses)})
            .catch(error => this.handleError(error));
    }

    private handleError(error: Response) {
        console.error(error);
        let msg = `Error status code ${error.status} at ${error.url}`;
        return Observable.throw(msg);
    }
}