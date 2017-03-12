import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../requests/shared/request.service';
import { Request } from '../../requests/shared/request.model';

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