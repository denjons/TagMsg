import { NgModule } from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TmResponsetListComponent } from './response-list/response-list.component';
import { ResponseComponent } from './response/response.component';

import { TMResponseService } from './shared/tm.response.service';


@NgModule({
    imports: [
        CommonModule,
        FormsModule
    ],
    declarations: [ResponseComponent, TmResponsetListComponent],
    providers: [TMResponseService]
})
export class ResponsesModule{}