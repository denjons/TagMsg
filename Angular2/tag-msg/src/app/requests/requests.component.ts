import { Component } from '@angular/core';

import { RequestService } from './shared/request.service';

@Component({
    template:"<router-outlet></router-outlet>",
    providers:[RequestService]
})
export class RequestsComponent{
    constructor(){}
}