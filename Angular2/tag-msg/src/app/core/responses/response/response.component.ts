import { Component, OnInit, Input } from '@angular/core';

import { TMResponse } from '../shared/tm.response.model';
import { TMResponseService } from '../shared/tm.response.service';


@Component({
    selector: "tm-response",
    templateUrl: "./response.component.html",
    styleUrls: ['./response.component.css']
})
export class ResponseComponent{
    @Input() response: TMResponse;
}