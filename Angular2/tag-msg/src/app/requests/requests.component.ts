import { Component } from '@angular/core';

import { RequestService } from '../core/requests/request.service';

@Component({
    template:"<router-outlet></router-outlet>",
    providers:[RequestService]
})
export class RequestsComponent{
    constructor(){}
}