import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// modules
import { RequestModule } from '../core/requests/request.module';

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
    RequestModule,
    RequestsRoutingModule
  ],
  providers: [RequestService]
})
export class RequestsModule { }