import { Component } from '@angular/core';

import { TMResponse } from '../shared/tm.response.model';
import { TMResponseService } from '../shared/tm.response.service';


@Component({
    selector: "response",
    templateUrl: "./response.componenet.html",
    styleUrls: ['./response-list.component.css']
})
export class ResponseComponent{

    constructor(private responseService: TMResponseService){}
}