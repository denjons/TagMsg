import { Component, Input } from '@angular/core';
import { Request } from '../../../core/requests/request.model';

@Component({
    selector:"outbox-button",
    templateUrl:"./request-button.component.html",
    styleUrls: ['./request-button.component.css']
})
export class OutboxButtonComponent{
    @Input() request: Request;
    constructor(){}
}