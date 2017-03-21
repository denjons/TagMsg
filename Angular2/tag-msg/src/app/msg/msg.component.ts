
import { Request } from '../core/requests/request.model';


import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector:"tm-msg",
    templateUrl:"./msg.component.html",
    styleUrls: ['./msg.component.css']
})
export class MSGComponent implements OnInit{

    request: Request;

    constructor(){
        //this.request = new Request();
    }

    ngOnInit(){

    }

}