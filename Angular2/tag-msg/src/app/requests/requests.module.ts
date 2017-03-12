import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// modules
import { ResponsesModule } from '../responses/responses.module';

// routing
import { RequestsRoutingModule, routableComponents } from './requests-routing.module';

// components
import { RequestButtonComponent } from './shared/button/request-button.component';

// services
import { RequestService } from '../core/requests/request.service';

// extensions
import '../core/rxjs-extensions';

@NgModule({
  declarations: [
    routableComponents, RequestButtonComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ResponsesModule,
    RequestsRoutingModule
  ],
  providers: [RequestService]
})
export class RequestsModule { }