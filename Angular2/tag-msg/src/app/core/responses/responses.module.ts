import { NgModule } from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ResponsetListComponent } from './response-list/response-list.component';
import { ResponseComponent } from './response/response.component';

import { TMResponseService } from './shared/tm.response.service';


@NgModule({
    imports: [
        CommonModule,
        FormsModule
    ],
    exports:[
        ResponsetListComponent
    ],
    declarations: [ResponseComponent, ResponsetListComponent],
    providers: [TMResponseService]
})
export class ResponsesModule{

}