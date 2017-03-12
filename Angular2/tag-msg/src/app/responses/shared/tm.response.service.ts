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
        TMHeader.headers.set(TMHeader.ATYHORIZATION,"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0IiwiaWF0IjoxNDg5MjIzNzkyLCJzdWIiOiIvdXNlci9jcmVhdGV1c2VyIiwiaXNzIjoiVE1fV0lMREZMWV83In0.wW9YYV3WexzD7vFcaDBZLZlqt5KrcuyEfvgHi0KB4jA");
        
        return this.http.get(TMURLS.getResponses(10, 0, requestId), {headers : TMHeader.headers})
            .map((response: Response) => <TMResponse[]>response.json().responses)
            .do(responses => {this.responses = responses; 
            console.log(responses)})
            .catch(error => this.handleError(error));
    }
    

    getResponse(respId: any){
       return this.responses.find(response => response.id == respId);
    }


    private handleError(error: Response) {
        console.error(error);
        let msg = `Error status code ${error.status} at ${error.url}`;
        return Observable.throw(msg);
    }
}