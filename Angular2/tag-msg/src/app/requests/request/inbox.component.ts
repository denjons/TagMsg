import { Component, OnInit } from '@angular/core';
import { Request } from '../../core/requests/request.model'; 
import { RequestService } from '../../core/requests/request.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector:"request-inbox",
    templateUrl:"./inbox.component.html",
    styleUrls: ['./inbox.component.css']
})
export class InboxRequestComponent implements OnInit{
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