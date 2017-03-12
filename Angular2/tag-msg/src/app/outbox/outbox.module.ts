import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// modules
import { RequestModule } from '../core/requests/request.module';

// routing
import { OutboxRoutingModule, routableComponents } from './outbox-routig.module';

// components
import { OutboxButtonComponent } from './shared/button/outbox-button.component';

// services
import { RequestService } from '../core/requests/request.service'

// extensions
import '../core/rxjs-extensions';

@NgModule({
  declarations: [
    routableComponents, OutboxButtonComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RequestModule,
    OutboxRoutingModule
  ],
  providers: [RequestService]
})
export class OutboxModule { }