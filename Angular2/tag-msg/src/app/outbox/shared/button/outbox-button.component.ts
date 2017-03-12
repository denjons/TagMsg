import { Component, Input } from '@angular/core';
import { Request } from '../../../core/requests/request.model';

@Component({
    selector:"outbox-button",
    templateUrl:"./outbox-button.component.html",
    styleUrls: ['./outbox-button.component.css']
})
export class OutboxButtonComponent{
    @Input() request: Request;
    constructor(){}
}