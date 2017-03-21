import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MSGRoutingModule, routableComponents } from './msg-routig.module';
import { MSGWidgetModule } from '../core/msg/msg-widget.module';

//import { TagsModule } from '../tags/tags.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MSGRoutingModule,
    MSGWidgetModule
  ],
  exports:[routableComponents],
  declarations:[routableComponents]
})
export class MSGModule { }