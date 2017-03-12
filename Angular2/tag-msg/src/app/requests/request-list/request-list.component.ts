import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../core/requests/request.service';
import { Request } from '../../core/requests/request.model';

@Component({
    selector:"request-list",
    templateUrl:"./request-list.component.html",
    styleUrls: ['./request-list.component.css']
})
export class RequestListComponent implements OnInit{
    
    requests: Request[];
    errorMessage: string;

    constructor(private requestService: RequestService){
    }

    ngOnInit(){
        console.log("initializing");
        this.requestService.getEligibleRequests().subscribe(
            requests => this.requests = requests,
            error => this.errorMessage = <any>error
        );
    }


}