import { Component, Input } from '@angular/core';
import { Request } from '../../../core/requests/request.model';

@Component({
    selector:"request-button",
    templateUrl:"./request-button.component.html",
    styleUrls: ['./request-button.component.css']
})
export class RequestButtonComponent{
    @Input() request: Request;
    constructor(){}
}