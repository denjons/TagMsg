import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../core/requests/request.service';
import { Request } from '../../core/requests/request.model';

@Component({
    selector:"outbox-list",
    templateUrl:"./outbox-list.component.html",
    styleUrls: ['./outbox-list.component.css']
})
export class OutboxListComponent implements OnInit{
    
    requests: Request[];
    errorMessage: string;

    constructor(private requestService: RequestService){
    }

    ngOnInit(){
        console.log("initializing");
        this.requestService.getRequestsForUser().subscribe(
            requests => this.requests = requests,
            error => this.errorMessage = <any>error
        );
    }


}