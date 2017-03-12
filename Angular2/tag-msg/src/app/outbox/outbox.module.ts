import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


// routing
import { OutboxRoutingModule, routableComponents } from './outbox-routig.module';

// components
import { RequestButtonComponent } from '../requests/shared/button/request-button.component';

// services
import { RequestService } from '../core/requests/request.service'

// extensions
import '../core/rxjs-extensions';

@NgModule({
  declarations: [
    routableComponents, RequestButtonComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    OutboxRoutingModule
  ],
  providers: [RequestService]
})
export class OutboxModule { }