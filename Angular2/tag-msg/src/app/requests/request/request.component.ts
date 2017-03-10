import { Component, OnInit } from '@angular/core';
import { Request } from '../shared/request.model'; 
import { RequestService } from '../shared/request.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector:"request",
    templateUrl:"./request.component.html",
    styleUrls: ['./request.component.css']
})
export class RequestComponent implements OnInit{
    request: Request;
    id: any;

    constructor(private requestService: RequestService,
    private route: ActivatedRoute,
    private router: Router){}

    ngOnInit(){
        this.id = this.route.snapshot.params['id'];
        this.request = this.requestService.getRequest(this.id);
    }
}