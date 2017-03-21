import { Component, OnInit, Input } from '@angular/core';
import { Request } from '../../core/requests/request.model';

@Component({
    selector:"outbox-list",
    templateUrl:"./outbox-list.component.html",
    styleUrls: ['./outbox-list.component.css']
})
export class OutboxListComponent{
    
    @Input() requests: Request[];
    errorMessage: string;

    constructor(){
    }

}