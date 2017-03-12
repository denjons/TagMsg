import { Component, OnInit, Input } from '@angular/core';
import { Request } from '../request.model'; 
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector:"request",
    templateUrl:"./request.component.html",
    styleUrls: ['./request.component.css']
})
export class RequestComponent{
    @Input() request: Request;
    id: any;

    constructor(){}

}