import { Component, OnInit, Input } from '@angular/core';
import { TMResponseService } from '../shared/tm.response.service';
import { TMResponse } from '../shared/tm.response.model';

@Component({
    selector:"response-list",
    templateUrl:"./response-list.component.html",
    styleUrls: ['./response-list.component.css']
})
export class ResponsetListComponent implements OnInit{
    
    responses: TMResponse[];
    errorMessage: string;
    @Input() requestId : String;

    constructor(private responseService: TMResponseService){}

    ngOnInit(){
        console.log("initializing");
        this.responseService.getResponses(this.requestId).subscribe(
            responses => this.responses = responses,
            error => this.errorMessage = <any>error
        );
    }


}