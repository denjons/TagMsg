import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Request } from '../requests/request.model';

@Component({
    selector:"tm-tags",
    templateUrl:"./tags.component.html",
    styleUrls: ['./tags.component.css']
})
export class TagsComponent{
    @Input() request: Request;

    constructor(){
    }

}