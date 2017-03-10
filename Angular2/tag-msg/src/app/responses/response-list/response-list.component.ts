import { Component, OnInit } from '@angular/core';
import { TMResponseService } from '../shared/tm.response.service';
import { TMResponse } from '../shared/tm.response.model';

@Component({
    selector:"request-list",
    templateUrl:"./request-list.component.html",
    styleUrls: ['./request-list.component.css']
})
export class TmResponsetListComponent implements OnInit{
    
    responses: TMResponse[];
    errorMessage: string;
    request : String;

    constructor(private responseService: TMResponseService){}

    ngOnInit(){
        console.log("initializing");
        this.responseService.getResponses(this.request).subscribe(
            responses => this.responses = responses,
            error => this.errorMessage = <any>error
        );
    }


}