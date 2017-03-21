
import { Request } from '../requests/request.model';


import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector:"msg-widget",
    templateUrl:"./msg-widget.component.html",
    styleUrls: ['./msg-widget.component.css']
})
export class MSGWidgetComponent implements OnInit{

    request: Request;

    constructor(){
        //this.request = new Request();
    }

    ngOnInit(){

    }

}